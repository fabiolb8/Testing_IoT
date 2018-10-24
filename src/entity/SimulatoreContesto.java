package entity;

import utilities.Mbed;
import utilities.MbedException;

public class SimulatoreContesto {
	//pattern singleton
	private final String descrizione = "Simulatore di un sistema di monitoraggio dei livelli di fumo e temperatura";
	private final String deviceTarget = "STM32F401RE_Nucleo";
	private int fumo;
	private int temperature[];
	private static SimulatoreContesto simulatore = null;
	
	private SimulatoreContesto(){
		temperature = new int[3];
	}
	
	public static SimulatoreContesto getInstance(){
		
		if(simulatore==null){
			simulatore = new SimulatoreContesto();
		}
		return simulatore;
	}
	
	public int leggiAllarme() throws ConnectionException {
		
		Mbed mbed = Mbed.getInstance();
		int allarme = 0;
		try {
			//rilevo stato allarme
			allarme = mbed.rilevaAllarme();
		} catch (MbedException e) {
			throw new ConnectionException("Errore di connessione");
		}
		return allarme;
	}

	public int leggiVentilazione() throws ConnectionException {
		Mbed mbed = Mbed.getInstance();
		int ventilazione = 0;
		try {
			//rilevo stato ventilazione
			ventilazione = mbed.rilevaVentilazione();
		} catch (MbedException e) {
			throw new ConnectionException("Errore di connessione");
		}
		return ventilazione;
	}

	public void avviaSimulazione() throws ConnectionException {
		
		Mbed mbed = Mbed.getInstance();
		try {
			//stabilisco connessione con board
			mbed.setUpRPCConnection();
			//adatto gli input
			int[] input = new int[4];
			input[0]=this.fumo;
			for (int i=1; i<=3; i++) {
				input[i]=this.temperature[i-1];
			}	
			//imposto gli input della simulazione
			mbed.inviaSegnali(input);
		} catch (MbedException e) {
			throw new ConnectionException("Errore di connessione");
		}
	}

	public int getFumo() {
		return fumo;
	}

	public void setFumo(int fumo) {
		this.fumo = fumo;
	}

	public int[] getTemperature() {
		return temperature;
	}

	public void setTemperature(int temp1, int temp2, int temp3) {
		this.temperature[0] = temp1;
		this.temperature[1] = temp2;
		this.temperature[2] = temp3;

	}

	public String getDescrizione() {
		return descrizione;
	}

	public String getDeviceTarget() {
		return deviceTarget;
	}
	
	

}