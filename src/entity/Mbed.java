package entity;

import java.util.Enumeration;

import org.mbed.RPC.DigitalIn;
import org.mbed.RPC.DigitalOut;
import org.mbed.RPC.SerialRxTxRPC;
import org.mbed.RPC.mbed;

import gnu.io.CommPortIdentifier;

public class Mbed {

	private final String[] pin_input = {"a0_tx","a1_tx","a2_tx","a3_tx"};
	private final String[] pin_output = {"a4_tx","a5_tx"};
    
	private static mbed mbed_connection;
    private DigitalOut a0;
    private DigitalOut a1;
    private DigitalOut a2;
    private DigitalOut a3;
    private DigitalIn a4;
    private DigitalIn a5;

	private static Mbed mbed_instance = null;
	
	private Mbed(){}
	
	public static Mbed getInstance(){
		
		if(mbed_instance==null){
			mbed_instance = new Mbed();
		}
		return mbed_instance;
	}

	public void setUpRPCConnection() {
	
		//String porta = getPortName();
		String porta = "COM3";

		
        //Create an mbed object for communication over serial
        mbed_connection = new SerialRxTxRPC(porta, 9600);
        System.out.println("mbed RPC: ho creato la connessione seriale");
        
        //Create new Digital Outputs/Input on the mbed
        a0 = new DigitalOut(mbed_connection, pin_input[0]);
        a1 = new DigitalOut(mbed_connection, pin_input[1]);
        a2 = new DigitalOut(mbed_connection, pin_input[2]);
        a3 = new DigitalOut(mbed_connection, pin_input[3]);
        a4 = new DigitalIn(mbed_connection, pin_output[0]);
        a5 = new DigitalIn(mbed_connection, pin_output[1]);
        System.out.println("mbed RPC: inizializzazione pin input e output");
		
	}
	
	private String getPortName() {
		
	     CommPortIdentifier serialPortId = null;
	     //static CommPortIdentifier sSerialPortId;
	     Enumeration enumComm = null;
	     //SerialPort serialPort;

	     enumComm = CommPortIdentifier.getPortIdentifiers();
	     while (enumComm.hasMoreElements()) {
	        serialPortId = (CommPortIdentifier) enumComm.nextElement();
	        if(serialPortId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
	         System.out.println(serialPortId.getName());
	       }
	     }

      return serialPortId.getName();		
	}

	/**
	 * 
	 * @param input
	 */
	public void inviaSegnali(int input[]) {
		
		/*
		for (int i=0; i<input.length; i++) {
			
			System.out.println("input " + i + " =" + input[i]);
		}*/
		
		a0.write(input[0]);
		a1.write(input[1]);
		a2.write(input[2]);
		a3.write(input[3]);

	}

	public int rilevaAllarme() {
		
		int a4_value = a4.read();
		System.out.println("Allarme rilevato: " + a4_value);

		
		return a4_value;
		//return 0;
	}

	public int rilevaVentilazione() {
		
		
		int a5_value = a5.read();
		System.out.println("Ventilazione rilevata: " + a5_value);

		mbed_connection.delete();
		
		return a5_value;
		//return 0;
	}

}