package gui;

import java.io.IOException;

import org.eclipse.swt.widgets.Display;

import boundary.BTester;
import controller.PersistanceException;

public class UIReportThread extends Thread {
	
	private String nomeFileReport;

	public UIReportThread(String UInomeFileReport) {
		this.nomeFileReport=new String(UInomeFileReport+".txt");
	}
	
	@Override
	public void run() {
		super.run();
		
		
		try {
		
			BTester tester = new BTester();
			tester.generaReport(nomeFileReport);
			
			Display.getDefault().asyncExec(() -> UI_TestingIoT.setVisibleLabelConsole(true));
			
			if(UIEseguiThread.getCountTSuiteEseguite()>0) {
			//if (!GestoreTestingIoT.getInstance().getListaTestSuite().isEmpty()) {
				Display.getDefault().asyncExec(() -> UI_TestingIoT.setLabelConsole("Report salvato correttamente"));
				Runtime.getRuntime().exec("notepad ./"+nomeFileReport);
			}
			else {
				Display.getDefault().asyncExec(() -> UI_TestingIoT.setLabelConsole("Report vuoto"));
			}
			
		}catch( PersistanceException e) {
			Display.getDefault().asyncExec(() -> UI_TestingIoT.setVisibleLabelConsole(true));
			Display.getDefault().asyncExec(() -> UI_TestingIoT.setLabelConsole(e.getMessage()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Display.getDefault().asyncExec(() -> UI_TestingIoT.setVisibleLabelConsole(true));
			Display.getDefault().asyncExec(() -> UI_TestingIoT.setLabelConsole("Impossibile aprire il report"));
		}
		
	}
	
}
