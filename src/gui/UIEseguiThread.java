package gui;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.eclipse.swt.widgets.Display;

public class UIEseguiThread extends Thread{

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
	private Integer testSuiteID;
	private String nomeFileXML;
	
	public UIEseguiThread(Integer testSuiteID) {
		this.testSuiteID=testSuiteID;
		this.nomeFileXML=new String("TS"+testSuiteID.toString()+".xml");
	}
	
	
	@Override
	public void run() {
		super.run();
		
		try {
			Display.getDefault().asyncExec(() -> UI_TestingIoT.setEnableBottonEseguiTS(false));
			Display.getDefault().asyncExec(() -> UI_TestingIoT.setVisibleProgressBar(true));
			
			Thread.sleep(1000);
			Display.getDefault().asyncExec(() -> UI_TestingIoT.setEnableBottonEseguiTS(true));
			
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			String newLine=new String("Test Suite "+testSuiteID+" : "+sdf.format(timestamp).toString());
			Display.getDefault().asyncExec(() -> UI_TestingIoT.setVisibleLabelConsole(true));
			Display.getDefault().asyncExec(() -> UI_TestingIoT.setLabelConsole("Test Suite eseguita"));
			Display.getDefault().asyncExec(() -> UI_TestingIoT.addElementToList(newLine));
			Display.getDefault().asyncExec(() -> UI_TestingIoT.setVisibleProgressBar(false));
		
		} catch (Exception e) {
			e.printStackTrace();
			Display.getDefault().asyncExec(() -> UI_TestingIoT.setVisibleLabelConsole(true));
			Display.getDefault().asyncExec(() -> UI_TestingIoT.setLabelConsole("Esecuzione interrotta"));
		}
		
	}

}
