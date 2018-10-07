
import java.io.IOException;

import org.eclipse.swt.widgets.Display;

public class myThread extends Thread {
	private String[] input;
	
	public  myThread(String[] in){
		input=in;
		
		
	}
	public void run(){
		System.out.println("ciao ciao");
		
		Display.getDefault().asyncExec(() -> scelta_multipla.progressBar.setVisible(true));
		
		try {
			
			//DA FARE SINGLETON
			HelloWorld hello = new HelloWorld(input);
			hello.my_main();
			//HelloWorld.wait(10000);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Display.getDefault().syncExec(() -> scelta_multipla.setLabelEsito("Numero di test totali = "+input[0].length()+"\nNumero di test OK = "+scelta_multipla.getNum_test_ok()));	
		Display.getDefault().syncExec(() -> scelta_multipla.enableButtonSalvaSuccessivo(true));
		Display.getDefault().syncExec(() -> scelta_multipla.enableCombo(true));
		Display.getDefault().syncExec(() -> scelta_multipla.enableReset(true));
		
	}

}
