package gui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Group;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class UI_TestingIoT {

	protected Shell shell;
	private static Text textFieldID_TS,textField_NomeFileReport;
	private static Button GhostButton,bottoneEseguiTS,bottoneGeneraReport;
	private static Group groupEsegui,groupConsole,groupReport;
	private static Label labelEseguiTestSuite,lblConsole,labelReport,labelConsole;
	private static ProgressBar progressBarEseguiTS;
	private static List listConsole;
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");


	public static void main(String[] args) {
		try {
			UI_TestingIoT window = new UI_TestingIoT();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	
	protected void createContents() {
		shell = new Shell(SWT.SHELL_TRIM & ~SWT.RESIZE & SWT.TITLE | SWT.CLOSE | SWT.BORDER); 
		shell.setSize(1201, 655);
		shell.setText("Sistema Testing IoT");
		
		GhostButton = new Button(shell, SWT.NONE);
		GhostButton.setLocation(0, 0);
		GhostButton.setSize(1, 1);
		
		groupEsegui = new Group(shell, SWT.NONE);
		groupEsegui.setBounds(23, 10, 660, 282);
		
		bottoneEseguiTS = new Button(groupEsegui, SWT.NONE);
		
		bottoneEseguiTS.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
					setVisibleLabelConsole(false);
					
					String testSuiteID=textFieldID_TS.getText();
					if (!testSuiteID.isEmpty()) {
						try {
							
							UIEseguiThread t=new UIEseguiThread(Integer.parseInt(testSuiteID));
							t.start();
							
						} catch (NumberFormatException e2) {
							setVisibleLabelConsole(true);
							setLabelConsole("L'ID deve essere un numero");
						}
					}
					else {
						setVisibleLabelConsole(true);
						setLabelConsole("Il campo ID è vuoto");
					}
						
			}
		});
		bottoneEseguiTS.setBounds(431, 128, 142, 35);
		bottoneEseguiTS.setText("Esegui Test Suite");
		
		labelEseguiTestSuite = new Label(groupEsegui, SWT.NONE);
		labelEseguiTestSuite.setBounds(61, 76, 307, 25);
		labelEseguiTestSuite.setText("Inserisci l'ID della test suite da eseguire");
		
		textFieldID_TS = new Text(groupEsegui, SWT.BORDER);
		textFieldID_TS.setBounds(157, 130, 96, 31);
		
		progressBarEseguiTS = new ProgressBar(groupEsegui, SWT.INDETERMINATE);
		progressBarEseguiTS.setBounds(378, 197, 260, 26);
		progressBarEseguiTS.setVisible(false);
		
		groupConsole = new Group(shell, SWT.NONE);
		groupConsole.setBounds(699, 10, 470, 579);
		
		listConsole = new List(groupConsole, SWT.BORDER | SWT.V_SCROLL);
		listConsole.setBounds(10, 75, 450, 420);
		
		lblConsole = new Label(groupConsole, SWT.NONE);
		lblConsole.setAlignment(SWT.CENTER);
		lblConsole.setBounds(10, 41, 450, 25);
		lblConsole.setText("Console");
		
		labelConsole = new Label(groupConsole, SWT.NONE);
		labelConsole.setAlignment(SWT.CENTER);
		labelConsole.setBounds(10, 517, 450, 25);
		labelConsole.setText("New Label");
		labelConsole.setVisible(false);
		
		groupReport = new Group(shell, SWT.NONE);
		groupReport.setBounds(21, 304, 662, 285);
		
		textField_NomeFileReport = new Text(groupReport, SWT.BORDER);
		textField_NomeFileReport.setLocation(157, 124);
		textField_NomeFileReport.setSize(151, 31);
		
		labelReport = new Label(groupReport, SWT.NONE);
		labelReport.setLocation(157, 73);
		labelReport.setSize(151, 25);
		labelReport.setText("Nome file di report");
		
		bottoneGeneraReport = new Button(groupReport, SWT.NONE);
		
		bottoneGeneraReport.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				setVisibleLabelConsole(false);
				
				String UInomeFileReport=textField_NomeFileReport.getText();
				if (!UInomeFileReport.isEmpty()) {
					if (UInomeFileReport.contains(".txt")) {
						setVisibleLabelConsole(true);
						setLabelConsole("Inserisci il nome del file senza estensione");
					}
					else {
						UIReportThread t=new UIReportThread(UInomeFileReport);
						t.start();
					}
				}
				else {
					Timestamp timestamp=new Timestamp(System.currentTimeMillis());
					sdf.format(timestamp);
					UInomeFileReport=new String("report_"+timestamp.toString().replace(' ', '_'));
					
					UIReportThread t=new UIReportThread(UInomeFileReport);
					t.start();
				} 
				
				
				
				
				
			}
		});
		
		bottoneGeneraReport.setLocation(431, 122);
		bottoneGeneraReport.setSize(139, 35);
		bottoneGeneraReport.setText("Genera Report");

	}
	
	public static void setLabelReport(String text) {
		labelReport.setText(text);
	}
	
	public static void setVisibleProgressBar(boolean bool) {
		progressBarEseguiTS.setVisible(bool);
	}
	
	public static void setEnableBottonEseguiTS(boolean bool) {
		bottoneEseguiTS.setEnabled(bool);
	}
	
	public static void addElementToList(String newLine) {
		listConsole.add(newLine);
	}
	
	public static void setVisibleLabelConsole(boolean bool) {
		labelConsole.setVisible(bool);      
	}
	
	public static void setLabelConsole(String text) {
		labelConsole.setText(text);
	}
	
}

