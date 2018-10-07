import org.eclipse.swt.widgets.Display;
import org.mbed.RPC.*;
import java.io.*;
import java.sql.Timestamp;

public class HelloWorld implements mbedRPC{
	
	private FileOutputStream fileoutputstream = new FileOutputStream("report.txt",true);
    private PrintStream printstream = new PrintStream(fileoutputstream);
    private DigitalOut a0;
    private DigitalOut a1;
    private DigitalIn a5;
    private static mbed mbed;
    private static Integer par1;
	private static Integer par2;
	private static int i;
	private String args[];
    
    public void my_main() throws IOException {
    	
    	
    	if (args.length == 2) {
		    
			System.out.println("Ho ricevuto il parametro A0 e il parametro A1");
			
			setUpRPC();
			
		    for (i=0; i<args[0].length(); i++) {
		        	
		        par1 =  Character.getNumericValue(args[0].charAt(i));
		        par2 =  Character.getNumericValue(args[1].charAt(i));
		        
		        try {					
		        	
		        	flash(par1,par2,i);
		        	
					Display.getDefault().syncExec(() -> scelta_multipla.progressBar.setMaximum(args[0].length()));
				    Display.getDefault().syncExec(() -> scelta_multipla.progressBar.setSelection(i+1));
				    Display.getDefault().syncExec(() -> scelta_multipla.setLabelProgressBar(i+1 + "/" + args[0].length()));
				    
				    System.out.println(i);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		     }
		        
		    mbed.delete();
		    System.out.println("main: completed");
		}
		
    	else System.out.println("Non ho ricevuto il numero corretti di parametri.");

    }
    
    public HelloWorld(String[] input) throws IOException{ 
    	
    	args = input;        
    }
    
    public void setUpRPC() { 
    	
    	System.out.println("mbed RPC: inizializzo");
    	
        //Create an mbed object for communication over serial
        mbed = new SerialRxTxRPC("COM3", 9600);
        System.out.println("mbed RPC: ho creato la connessione seriale");
        
        //Create new Digital Outputs/Input on the mbed
        a0 = new DigitalOut(mbed, "a0_tx");
        a1 = new DigitalOut(mbed, "a1_tx");
        a5 = new DigitalIn(mbed, "a5_tx");
        System.out.println("mbed RPC: inizializzazione pin input e output");
    	
    }
    
    public void flash(int pin_a0, int pin_a1, int i) throws Exception{
        
      
    	System.out.println("mbed RPC: comunico con la " + i + " volta...");
    	
    	//Creazione intestazione caso test
    	
    	Timestamp ts=new Timestamp(System.currentTimeMillis());
    	printstream.println("Test: " + ts );
    	printstream.println(new String("Input A0 = " + pin_a0 + " e A1 = " + pin_a1));
    	
    	
    	//Comunicazione con la scheda: output
    	
    	//wait(5000);
    	a0.write(pin_a0);
     	a1.write(pin_a1);
     	wait(3000);
     	
     	
     	//Comunicazione con la scheda: input e terminazione caso test
     	
    	if(a5.read()==1){
     		
     		if (pin_a0 == pin_a1 && pin_a0 == 1) {
	     		//scelta_multipla.setLabel("Test " + i + ": esito OK");
     			//scelta_multipla.setLabel("Test " + i + ": esito OK");
     			scelta_multipla.increment_num_test_ok();
     			printstream.println(new String("Output atteso: 1, output rilevato: 1. Allarme attivato correttamente." ));
     		}
	     	else {
	     		//scelta_multipla.setLabel("Test " + i + ": esito non OK");
	     		printstream.println(new String("Output atteso: 0, output rilevato: 1. Allarme attivato erroneamente." ));	
	     	}
     	}
     	
     	else{
     		
     		if (pin_a0 == pin_a1 && pin_a0 == 1) {
	     		//scelta_multipla.setLabel("Test " + i + ": esito non OK");
	     		printstream.println(new String("Output atteso: 1, output rilevato: 0. Allarme non attivato erroneamente." ));
     		}
     		
     		else {
	     		//scelta_multipla.setLabel("Test " + i + ": esito OK");
     			scelta_multipla.increment_num_test_ok();
	     		printstream.println(new String("Output atteso: 0, output rilevato: 0. Allarme non attivato correttamente." ));
     		}
     		
     		
     	}
     	printstream.println("-------------");
     	
    	    	
    	
    	System.out.println("mbed RPC: comunicazione completata");
     	
     	        
    }
    
    
    //A function to create a wait
    public static void wait (int n){
        long startTime,currentTime;
        startTime =System.currentTimeMillis();
        do{
            currentTime = System.currentTimeMillis();
        }
        while ((currentTime - startTime) < n);
    }


}