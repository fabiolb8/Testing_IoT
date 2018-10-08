package entity;

public class SimulatoreContesto {

	private final String descrizione = "Simulatore";
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
	
	public int leggiAllarme() {
		
		Mbed mbed = Mbed.getInstance();

		return mbed.rilevaAllarme();
	}

	public int leggiVentilazione() {
		
		Mbed mbed = Mbed.getInstance();
		
		return mbed.rilevaVentilazione();
	}

	public void avviaSimulazione() {
		
		Mbed mbed = Mbed.getInstance();
		
		mbed.setUpRPCConnection();
		
		int[] input = new int[4];
		input[0]=getFumo();
		for (int i=1; i<=3; i++) {
			input[i] = temperature[i-1];
		}		
		
		mbed.inviaSegnali(input);
		
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