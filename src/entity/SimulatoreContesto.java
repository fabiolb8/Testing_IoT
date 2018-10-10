package entity;

import utilities.Mbed;
import utilities.MbedException;

public class SimulatoreContesto {

	private final String descrizione = "Simulatore di un sistema di monitoraggio di fumo e ventilazione";
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
			allarme = mbed.rilevaAllarme();
		} catch (MbedException e) {
			// TODO Auto-generated catch block
			throw new ConnectionException("Errore di connessione");
		}
		
		return allarme;
	}

	public int leggiVentilazione() throws ConnectionException {
		
		Mbed mbed = Mbed.getInstance();
		int ventilazione = 0;
		
		try {
			ventilazione = mbed.rilevaVentilazione();
		} catch (MbedException e) {
			// TODO Auto-generated catch block
			throw new ConnectionException("Errore di connessione");
		}
		
		return ventilazione;
	}

	public void avviaSimulazione() throws ConnectionException {
		
		Mbed mbed = Mbed.getInstance();
		
		try {
			mbed.setUpRPCConnection();
			
			int[] input = new int[4];
			input[0]=getFumo();
			for (int i=1; i<=3; i++) {
				input[i] = temperature[i-1];
			}		
			
			mbed.inviaSegnali(input);
			
		} catch (MbedException e) {
			// TODO Auto-generated catch block
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