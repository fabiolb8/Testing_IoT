package gui;

public class UIReportThread extends Thread {
	
	private String UInomeFileReport;
	private String nomeFIleReport;

	public UIReportThread(String UInomeFileReport) {
		this.UInomeFileReport=UInomeFileReport;
		this.nomeFIleReport=new String(UInomeFileReport+".txt");
	}
	
	@Override
	public void run() {
		super.run();
		
		System.out.println(nomeFIleReport);
		
	}
	
}
