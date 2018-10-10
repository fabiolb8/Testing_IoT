package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import boundary.BTester;
import controller.PersistanceException;
import entity.ConnectionException;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Panel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.SwingConstants; 


public class UI_Testing_IoT extends JFrame {

	
	private static final long serialVersionUID = 1L;
	private ExecutorService service = Executors.newSingleThreadExecutor();
	private JPanel mainPanel;
	private JTextField textEsegui, textReport;
	private JLabel labelEsegui, labelReport, labelMessaggio, labelConsole;
	private Panel panelEseguiGenera, panelConsole;
	private JProgressBar progressBar;
	private JButton bottoneEsegui, bottoneReport;
	private JTextArea textArea;
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
	private static int counterTSuiteEseguite;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
		            //UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); 
					
					UI_Testing_IoT frame = new UI_Testing_IoT();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UI_Testing_IoT() {
		
		setResizable(false);
		setTitle("Sistema Testing IoT");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1100, 600);
		mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainPanel.setLayout(new BorderLayout(0, 0));
		setContentPane(mainPanel);
	
		
		//Panel
		panelConsole = new Panel();
		mainPanel.add(panelConsole, BorderLayout.EAST);
		panelEseguiGenera = new Panel();
		mainPanel.add(panelEseguiGenera, BorderLayout.WEST);
		
		
		//Label
		labelEsegui = new JLabel("Inserisci l'ID della test suite da eseguire");
		labelEsegui.setHorizontalAlignment(SwingConstants.CENTER);
		labelReport = new JLabel("Inserisci il nome del file di report");
		labelReport.setHorizontalAlignment(SwingConstants.CENTER);
		labelConsole = new JLabel("Console");
		labelConsole.setHorizontalAlignment(SwingConstants.CENTER);
		labelMessaggio = new JLabel("Default");
		labelMessaggio.setHorizontalAlignment(SwingConstants.CENTER);
		labelMessaggio.setVisible(false);
		
		
		//ProgressBar
		progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		progressBar.setVisible(false);
		
		
		//Text field
		textEsegui = new JTextField();
		textEsegui.setColumns(10);
		textReport = new JTextField();
		textReport.setColumns(10);
		
		//Lista?
		textArea = new JTextArea();

		
		//Bottoni
		bottoneReport = new JButton("Genera Report");
		bottoneReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				//Alfredo
				service.submit(new Runnable() {
		        public void run() {
		        	
		        
				labelMessaggio.setVisible(false);
				
				String UInomeFileReport=textReport.getText();
				if (!UInomeFileReport.isEmpty()) {
					if (UInomeFileReport.contains(".txt")) {
						labelMessaggio.setVisible(true);
						labelMessaggio.setText("Inserisci il nome del file senza estensione");
					}
					else {
						try {
							
							BTester tester = new BTester();
							tester.generaReport(UInomeFileReport+".txt");
							
							labelMessaggio.setVisible(true);
							
							//PAOLO
							if(getCounterTSuiteEseguite()>0) {
								Runtime.getRuntime().exec("notepad ./reports/"+UInomeFileReport+".txt");
								labelMessaggio.setText("Report salvato correttamente");
							}
							else {
								labelMessaggio.setText("Report vuoto");
							}
							
							
						} catch(PersistanceException p) {
							labelMessaggio.setVisible(true);
							labelMessaggio.setText(p.getMessage());
							
						} catch (IOException i) {
							// TODO Auto-generated catch block
							labelMessaggio.setVisible(true);
							labelMessaggio.setText("Impossibile aprire il report");
						}
					}
				}
				else {
					Timestamp timestamp=new Timestamp(System.currentTimeMillis());
					sdf.format(timestamp);
					UInomeFileReport=new String("report_"+timestamp.toString().replace(':', '.'));

					
					try {
						
						BTester tester = new BTester();
						tester.generaReport(UInomeFileReport+".txt");
						
						labelMessaggio.setVisible(true);
						
						//PAOLO
						if(getCounterTSuiteEseguite()>0) {
							Runtime.getRuntime().exec("notepad ./reports/"+UInomeFileReport+".txt");
							labelMessaggio.setText("Report salvato correttamente");
						}
						else {
							labelMessaggio.setText("Report vuoto");
						}
						
						
					} catch(PersistanceException p) {
						labelMessaggio.setVisible(true);
						labelMessaggio.setText(p.getMessage());
						
					} catch (IOException i) {
						// TODO Auto-generated catch block
						labelMessaggio.setVisible(true);
						labelMessaggio.setText("Impossibile aprire il report");
					}
				} 

			}
		    
		    //Alfredo
			});}
			
		});


		bottoneEsegui = new JButton("Esegui Test Suite");
		bottoneEsegui.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Alfredo
				service.submit(new Runnable() {
		        public void run() {

				String testSuiteID=textEsegui.getText();
				if (!testSuiteID.isEmpty()) {
					try {

						BTester btester = new BTester();
						String risultato = null;

						try {
							
							//UI update
							progressBar.setVisible(true);
							bottoneReport.setEnabled(false);
							bottoneEsegui.setEnabled(false);
							risultato = btester.eseguiTestSuite(Integer.parseInt(testSuiteID));
							counterTSuiteEseguite++;

							//Aggiorno label messaggio
							labelMessaggio.setVisible(true);
							labelMessaggio.setText("Test suite eseguita");

							//Aggiungo il risultato alla list
							Timestamp timestamp = new Timestamp(System.currentTimeMillis());
							textArea.append("["+sdf.format(timestamp).toString() + "]: " + risultato+"\n");
							

						} catch (PersistanceException p) {
							//f.printStackTrace();
							labelMessaggio.setVisible(true);
							labelMessaggio.setText(p.getMessage());
							
						} catch (ConnectionException c) {
							//e.printStackTrace();
							labelMessaggio.setVisible(true);
							labelMessaggio.setText(c.getMessage());
							
						} finally {
							
							progressBar.setVisible(false);
							bottoneReport.setEnabled(true);
							bottoneEsegui.setEnabled(true);

						}
						
					} catch (NumberFormatException e2) {

						labelMessaggio.setVisible(true);
						labelMessaggio.setText("L'ID deve essere un numero");
					}
				}
				else {

					labelMessaggio.setVisible(true);
					labelMessaggio.setText("Il campo ID è vuoto");
				}

			}
			
			//Alfredo
			});}
		});		
		
		
		//Gruppi per layout non preimpostato
		GroupLayout gl_panelEseguiGenera = new GroupLayout(panelEseguiGenera);
		gl_panelEseguiGenera.setHorizontalGroup(
			gl_panelEseguiGenera.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelEseguiGenera.createSequentialGroup()
					.addGroup(gl_panelEseguiGenera.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelEseguiGenera.createSequentialGroup()
							.addGap(31)
							.addGroup(gl_panelEseguiGenera.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelEseguiGenera.createSequentialGroup()
									.addComponent(labelEsegui, GroupLayout.PREFERRED_SIZE, 301, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(textEsegui, GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE))
								.addGroup(gl_panelEseguiGenera.createSequentialGroup()
									.addComponent(labelReport, GroupLayout.PREFERRED_SIZE, 256, GroupLayout.PREFERRED_SIZE)
									.addGap(63)
									.addGroup(gl_panelEseguiGenera.createParallelGroup(Alignment.LEADING)
										.addComponent(bottoneReport)
										.addComponent(textReport, GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)))))
						.addGroup(gl_panelEseguiGenera.createSequentialGroup()
							.addGap(350)
							.addGroup(gl_panelEseguiGenera.createParallelGroup(Alignment.LEADING)
								.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(bottoneEsegui, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
					.addContainerGap())
		);
		gl_panelEseguiGenera.setVerticalGroup(
			gl_panelEseguiGenera.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelEseguiGenera.createSequentialGroup()
					.addGap(111)
					.addGroup(gl_panelEseguiGenera.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panelEseguiGenera.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textEsegui, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelEseguiGenera.createSequentialGroup()
							.addGap(6)
							.addComponent(labelEsegui, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)))
					.addGap(56)
					.addComponent(bottoneEsegui, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addGap(30)
					.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addGap(61)
					.addGroup(gl_panelEseguiGenera.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panelEseguiGenera.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textReport))
						.addGroup(gl_panelEseguiGenera.createSequentialGroup()
							.addGap(6)
							.addComponent(labelReport, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)))
					.addGap(37)
					.addComponent(bottoneReport)
					.addContainerGap(104, Short.MAX_VALUE))
		);
		panelEseguiGenera.setLayout(gl_panelEseguiGenera);
				
		GroupLayout gl_panelConsole = new GroupLayout(panelConsole);
		gl_panelConsole.setHorizontalGroup(
			gl_panelConsole.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelConsole.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelConsole.createParallelGroup(Alignment.LEADING)
						.addComponent(labelConsole, GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
						.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 368, GroupLayout.PREFERRED_SIZE)
						.addComponent(labelMessaggio, GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panelConsole.setVerticalGroup(
			gl_panelConsole.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panelConsole.createSequentialGroup()
					.addGap(18)
					.addComponent(labelConsole, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 450, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(labelMessaggio)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panelConsole.setLayout(gl_panelConsole);
		
	}

	
	public static int getCounterTSuiteEseguite() {
		return counterTSuiteEseguite;
	}

		
}



