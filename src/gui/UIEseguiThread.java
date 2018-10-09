package gui;

import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.eclipse.swt.widgets.Display;

import boundary.BTester;

public class UIEseguiThread extends Thread{

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
	private Integer testSuiteID;
	private static int countTSuiteEseguite;
	
	public UIEseguiThread(Integer testSuiteID) {
		this.testSuiteID=testSuiteID;
	}
	
	
	@Override
	public void run() {
		super.run();
		
		
		try {
			Display.getDefault().asyncExec(() -> UI_TestingIoT.setEnableBottonEseguiTS(false));
			Display.getDefault().asyncExec(() -> UI_TestingIoT.setVisibleProgressBar(true));
		
			
			BTester tester = new BTester();
			String esito_suite=tester.eseguiTestSuite(testSuiteID);
			countTSuiteEseguite+=1;
			
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
			Display.getDefault().asyncExec(() -> UI_TestingIoT.setEnableBottonEseguiTS(true));
			Display.getDefault().asyncExec(() -> UI_TestingIoT.setVisibleLabelConsole(true));
			Display.getDefault().asyncExec(() -> UI_TestingIoT.setLabelConsole("Test Suite eseguita"));
			Display.getDefault().asyncExec(() -> UI_TestingIoT.addElementToList("["+sdf.format(timestamp).toString() + "]: " + esito_suite));
			Display.getDefault().asyncExec(() -> UI_TestingIoT.setVisibleProgressBar(false));
			
			
		} catch (FileNotFoundException f) {
			//f.printStackTrace();
			Display.getDefault().asyncExec(() -> UI_TestingIoT.setVisibleProgressBar(false));
			Display.getDefault().asyncExec(() -> UI_TestingIoT.setEnableBottonEseguiTS(true));
			Display.getDefault().asyncExec(() -> UI_TestingIoT.setVisibleLabelConsole(true));
			Display.getDefault().asyncExec(() -> UI_TestingIoT.setLabelConsole("Test Suite non esistente"));
			
		} catch (Exception e) {
			//e.printStackTrace();
			Display.getDefault().asyncExec(() -> UI_TestingIoT.setVisibleLabelConsole(true));
			Display.getDefault().asyncExec(() -> UI_TestingIoT.setLabelConsole("Esecuzione interrotta"));
		}
		finally {
			Display.getDefault().asyncExec(() -> UI_TestingIoT.setEnableBottonReport(true));
		}
		
	}
	
	
	public static int getCountTSuiteEseguite() {
		return countTSuiteEseguite;
	}

}
