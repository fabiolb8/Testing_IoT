package gui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Group;

import java.awt.EventQueue;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import boundary.BTester;
import controller.GestoreTestingIoT;
import controller.PersistanceException;
import entity.ConnectionException;

import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class UI_TestingIoT {

	protected Shell shell;
	private static Text testoEsegui,testoReport;
	private static Button GhostButton,bottoneEseguiTS,bottoneGeneraReport;
	private static Group groupEsegui,groupConsole,groupReport;
	private static Label labelEseguiTestSuite,lblConsole,labelReport,labelConsole;
	private static ProgressBar progressBarEseguiTS;
	private static List listConsole;
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
	private String risultato, UInomeFileReport;
	private static int numTSuiteEseguite;

	
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					UI_TestingIoT window = new UI_TestingIoT();
					window.open();
				} catch (Exception e) {
					e.printStackTrace();
				}	
			}
		});
		
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
		groupEsegui.setBounds(23, 10, 502, 282);
		
		bottoneEseguiTS = new Button(groupEsegui, SWT.NONE);
		
		bottoneEseguiTS.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				
				String testSuiteID=testoEsegui.getText();
				
				Thread esegui = new Thread(new Runnable() {
				
					@Override
					public void run() {
					
							if (!testSuiteID.isEmpty()) {
								try {
			
									BTester btester = new BTester();
									risultato = null;
			
									try {
										
										//UI update
										Display.getDefault().asyncExec(() -> labelConsole.setVisible(false));
										Display.getDefault().asyncExec(() -> progressBarEseguiTS.setVisible(true));
										Display.getDefault().asyncExec(() -> bottoneGeneraReport.setEnabled(false));
										Display.getDefault().asyncExec(() -> bottoneEseguiTS.setEnabled(false));
										risultato = btester.eseguiTestSuite(Integer.parseInt(testSuiteID));
										numTSuiteEseguite++;
			
										//Aggiorno label messaggio
										Display.getDefault().asyncExec(() -> labelConsole.setVisible(true));
										Display.getDefault().asyncExec(() -> labelConsole.setText("Test suite eseguita"));
			
										//Aggiungo il risultato alla list
										Timestamp timestamp = new Timestamp(System.currentTimeMillis());
										Display.getDefault().asyncExec(() -> listConsole.add("["+sdf.format(timestamp).toString() + "]: " + risultato +"\n"));
										
			
									} catch (PersistanceException p) {
										//f.printStackTrace();
										Display.getDefault().asyncExec(() ->labelConsole.setVisible(true));
										Display.getDefault().asyncExec(() ->labelConsole.setText(p.getMessage()));
										
									} catch (ConnectionException c) {
										//e.printStackTrace();
										Display.getDefault().asyncExec(() ->labelConsole.setVisible(true));
										Display.getDefault().asyncExec(() ->labelConsole.setText(c.getMessage()));
										
									} finally {
										
										Display.getDefault().asyncExec(() ->progressBarEseguiTS.setVisible(false));
										Display.getDefault().asyncExec(() ->bottoneGeneraReport.setEnabled(true));
										Display.getDefault().asyncExec(() ->bottoneEseguiTS.setEnabled(true));
			
									}
									
								} catch (NumberFormatException e2) {
			
									Display.getDefault().asyncExec(() ->labelConsole.setVisible(true));
									Display.getDefault().asyncExec(() ->labelConsole.setText("L'ID deve essere un numero"));
								}
							}
							else {
			
								Display.getDefault().asyncExec(() ->labelConsole.setVisible(true));
								Display.getDefault().asyncExec(() ->labelConsole.setText("Il campo ID è vuoto"));
							}
			
						
					}
				});	
				esegui.start();
				
				
			}
		});
		bottoneEseguiTS.setBounds(339, 169, 142, 35);
		bottoneEseguiTS.setText("Esegui Test Suite");
		
		labelEseguiTestSuite = new Label(groupEsegui, SWT.NONE);
		labelEseguiTestSuite.setBounds(20, 82, 307, 25);
		labelEseguiTestSuite.setText("Inserisci l'ID della test suite da eseguire");
		
		testoEsegui = new Text(groupEsegui, SWT.BORDER);
		testoEsegui.setBounds(339, 79, 142, 31);
		
		progressBarEseguiTS = new ProgressBar(groupEsegui, SWT.INDETERMINATE);
		progressBarEseguiTS.setBounds(339, 223, 142, 26);
		progressBarEseguiTS.setVisible(false);
		
		groupConsole = new Group(shell, SWT.NONE);
		groupConsole.setBounds(575, 10, 599, 579);
		
		listConsole = new List(groupConsole, SWT.BORDER | SWT.V_SCROLL);
		listConsole.setBounds(10, 75, 579, 420);
		
		lblConsole = new Label(groupConsole, SWT.NONE);
		lblConsole.setAlignment(SWT.CENTER);
		lblConsole.setBounds(10, 41, 579, 25);
		lblConsole.setText("Console");
		
		labelConsole = new Label(groupConsole, SWT.NONE);
		labelConsole.setAlignment(SWT.CENTER);
		labelConsole.setBounds(10, 517, 579, 25);
		labelConsole.setText("New Label");
		labelConsole.setVisible(false);
		
		groupReport = new Group(shell, SWT.NONE);
		groupReport.setBounds(21, 304, 502, 285);
		
		testoReport = new Text(groupReport, SWT.BORDER);
		testoReport.setLocation(342, 70);
		testoReport.setSize(151, 31);
		
		labelReport = new Label(groupReport, SWT.NONE);
		labelReport.setLocation(103, 73);
		labelReport.setSize(151, 25);
		labelReport.setText("Nome file di report");
		
		
		bottoneGeneraReport = new Button(groupReport, SWT.NONE);
	
		bottoneGeneraReport.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				
			    	
				labelConsole.setVisible(false);
				UInomeFileReport = testoReport.getText();
				
		        Thread report = new Thread(new Runnable() {

					@Override
					public void run() {

						if (!UInomeFileReport.isEmpty()) {
							if (UInomeFileReport.contains(".txt")) {
								Display.getDefault().asyncExec(() ->labelConsole.setVisible(true));
								Display.getDefault().asyncExec(() ->labelConsole.setText("Inserisci il nome del file senza estensione"));
							}
							else {
								try {
									
									
									Display.getDefault().asyncExec(() ->labelConsole.setVisible(true));
									
									if(numTSuiteEseguite>0) {
										GestoreTestingIoT gestore = GestoreTestingIoT.getInstance();
										gestore.generaReport(UInomeFileReport+".txt");
									
										
										Runtime.getRuntime().exec("notepad ./reports/"+UInomeFileReport+".txt");
										Display.getDefault().asyncExec(() ->labelConsole.setText("Report salvato correttamente"));
									}
									else {
										Display.getDefault().asyncExec(() ->labelConsole.setText("Report vuoto"));
									}
									
									
								} catch(PersistanceException p) {
									Display.getDefault().asyncExec(() ->labelConsole.setVisible(true));
									Display.getDefault().asyncExec(() ->labelConsole.setText(p.getMessage()));
									
								} catch (IOException i) {
									Display.getDefault().asyncExec(() ->labelConsole.setVisible(true));
									Display.getDefault().asyncExec(() ->labelConsole.setText("Impossibile aprire il report"));
								}
							}
						}
						else {
							Timestamp timestamp=new Timestamp(System.currentTimeMillis());
							sdf.format(timestamp);
							UInomeFileReport=new String("report_"+timestamp.toString().replace(':', '.'));
		
							
							try {
								
								
								Display.getDefault().asyncExec(() ->labelConsole.setVisible(true));
								
								//PAOLO
								if(numTSuiteEseguite>0) {
									GestoreTestingIoT gestore = GestoreTestingIoT.getInstance();
									gestore.generaReport(UInomeFileReport+".txt");
									
									Runtime.getRuntime().exec("notepad ./reports/"+UInomeFileReport+".txt");
									Display.getDefault().asyncExec(() ->labelConsole.setText("Report salvato correttamente"));
								}
								else {
									Display.getDefault().asyncExec(() ->labelConsole.setText("Report vuoto"));
								}
								
								
							} catch(PersistanceException p) {
								Display.getDefault().asyncExec(() ->labelConsole.setVisible(true));
								Display.getDefault().asyncExec(() ->labelConsole.setText(p.getMessage()));
								
							} catch (IOException i) {
								// TODO Auto-generated catch block
								Display.getDefault().asyncExec(() ->labelConsole.setVisible(true));
								Display.getDefault().asyncExec(() ->labelConsole.setText("Impossibile aprire il report"));
							}
						} 
						
					}});
		        report.start();
				
			        

		        }
		    
		    
		});
		
		bottoneGeneraReport.setLocation(342, 129);
		bottoneGeneraReport.setSize(151, 35);
		bottoneGeneraReport.setText("Genera Report");
	}
}

