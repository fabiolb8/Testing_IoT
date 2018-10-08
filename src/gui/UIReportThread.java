package gui;

import org.eclipse.swt.widgets.Display;

import boundary.BTester;

public class UIReportThread extends Thread {
	
	private String nomeFileReport;

	public UIReportThread(String UInomeFileReport) {
		this.nomeFileReport=new String(UInomeFileReport+".txt");
	}
	
	@Override
	public void run() {
		super.run();
		
		BTester tester = new BTester();
		tester.generaReport(nomeFileReport);
		
		Display.getDefault().asyncExec(() -> UI_TestingIoT.setVisibleLabelConsole(true));
		Display.getDefault().asyncExec(() -> UI_TestingIoT.setLabelConsole("Report salvato correttamente"));

		
	}
	
}
