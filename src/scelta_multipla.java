import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Menu;

public class scelta_multipla {
	
	private static int num_test_ok=0; //numero di test eseguiti OK
	protected static Shell shell;
	private String a0= new String();
	private String a1= new String();
	private String[] input = new String[2];
	private static Label label_esito, label_intestazione, label_numero_caso_test, label_a0, label_a0_cont, label_a1_cont, label_carica, label_progress_bar;
	private Integer i = 1;
	private boolean A0_checked, A1_checked= false;
	private static Combo combo;
	private Integer num_linee_caricate = 0;
	private static boolean primavolta = true;
	private static String[] input_a0 = new String[256];
	private static String[] input_a1 = new String[256];
	private static Integer id_car = 0;
	protected static ProgressBar progressBar;
	private Button bottone_esegui, salva_su_file, bottone_report;
	private static Button bottone_successivo_salva, reset;
	
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			scelta_multipla window = new scelta_multipla();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
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

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		
		//Shell
		shell = new Shell();
		shell.setSize(957, 876);
		shell.setText("Interfaccia test");
		
		//Crea o apri il file
		file();
		
		//Crea oggetti interfaccia
		createLabels();
		createButtons();
		createCheckbox();
		createCombo();
		createProgressBar();
		
		
	}
	
	public static void setLabelEsito(String esito) {
		
		label_esito.setText(esito);
	}
	
	public static void setLabelNumeroCasoTest(String esito) {
		
		label_numero_caso_test.setText(esito);
	}
	
	public static void setLabelProgressBar(String esito) {
		
		label_progress_bar.setText(esito);
	}
	
	public static void enableButtonSalvaSuccessivo(boolean abilita) {
		
		bottone_successivo_salva.setEnabled(abilita);
	}
	
	public static void enableCombo(boolean abilita) {
		
		combo.setEnabled(abilita);
	}

	public static void enableReset(boolean abilita) {
		
		reset.setEnabled(abilita);
	}
	
	
	private void file() {

		//Apro il file e il printstream
		FileOutputStream fileoutputstream = null;
		BufferedReader br = null;
		PrintStream printstream = null;
		String line = null;
		
		try {
			//File Output
			fileoutputstream = new FileOutputStream("./sequenze.txt",true);
			printstream = new PrintStream(fileoutputstream);
			//File Input
			br = new BufferedReader(new FileReader("./sequenze.txt"));
			line = br.readLine();
			
			
			if (line == null) {
				
				printstream.println("0dim");
			}
		
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	private void createLabels() {
		

		//Label numero casi di test
		label_numero_caso_test = new Label(shell, SWT.CENTER);
		label_numero_caso_test.setBounds(54, 70, 190, 46);
		label_numero_caso_test.setText("Inserisci la combinazione");
		
		//Label intestazione
		label_intestazione = new Label(shell, SWT.NONE);
		label_intestazione.setBounds(204, 22, 506, 25);
		label_intestazione.setText("Carica o seleziona quale pin asserire, salva e poi premi esegui.");
		
		//Label esito
		label_esito = new Label(shell, SWT.WRAP | SWT.CENTER);
		label_esito.setBounds(267, 419, 384, 83);
		label_esito.setText("In attesa della prima esecuzione.");
		
		//Label a0
		label_a0 = new Label(shell, SWT.NONE);
		label_a0.setText("Hai inserito 	A0: \n\n	 	A1:");
		label_a0.setBounds(88, 247, 196, 104);
		
		//Label per il contenuto "salvato" di a0
		label_a0_cont = new Label(shell, SWT.NONE);
		label_a0_cont.setBounds(323, 247, 224, 25);
		
		//Label per il contenuto "salvato" di a1
		label_a1_cont = new Label(shell, SWT.NONE);
		label_a1_cont.setBounds(323, 295, 242, 25);
		
		//Label carica
		label_carica = new Label(shell, SWT.NONE);
		label_carica.setBounds(788, 247, 81, 25);
		label_carica.setText("Carica");
		
		//Label progress bar
		label_progress_bar = new Label(shell, SWT.CENTER);
		label_progress_bar.setBounds(54, 419, 230, 25);
		
	}
	
	private void createButtons() {
		
		
		//Bottone Esegui e salva
		bottone_esegui = new Button(shell, SWT.NONE);
		bottone_esegui.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				//Gestione click
				setLabelEsito("In attesa...");
			
				input[0]=a0;
				input[1]=a1;
				
				try {
					
					/////////////////////////////////////////SE VUOI ESEGUIRE SENZA SCHEDA COMMENTA IL MAIN QUI
					setLabelNumeroCasoTest("Sequenza numero: \n" + "1");
					myThread m1=new myThread(input);
					m1.start();
					
					
					gui.main(input);
									
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				//"Pulizia"
				num_test_ok=0;
				i=0;
				bottone_esegui.setEnabled(false);
				salva_su_file.setEnabled(false);
				combo.setEnabled(false);
				bottone_successivo_salva.setEnabled(false);
				reset.setEnabled(false);
				progressBar.setSelection(0);
				progressBar.setMaximum(0);
			    scelta_multipla.setLabelProgressBar("0/"+input[0].length());

				
			}
		});
		bottone_esegui.setBounds(372, 378, 175, 35);
		bottone_esegui.setEnabled(false);
		bottone_esegui.setText("Esegui i test salvati");
	
		//Bottone salva
		salva_su_file = new Button(shell, SWT.NONE);
		salva_su_file.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				//Gestione click
				id_car++;
	            Integer vecchia_int = num_linee_caricate-1;
				FileOutputStream fileoutputstream = null;
				try {
					fileoutputstream = new FileOutputStream("./sequenze.txt",true);
				} catch (FileNotFoundException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				PrintStream printstream = new PrintStream(fileoutputstream);
				printstream.println(id_car + " " + a0 + " " + a1);
		    	
				//System.out.println("\nHo aggiunto la sequenza al file. Numero vecchio " + vecchia_int + " e nuovo " + num_linee_caricate);
				
				try {
		            Path path = Paths.get("./sequenze.txt");
		            Stream <String> lines = Files.lines(path);
					List <String> replaced = lines.map(line -> line.replace( vecchia_int.toString()+"dim" , num_linee_caricate.toString()+"dim"    )).collect(Collectors.toList());
		            Files.write(path, replaced);
		            lines.close();
		            //System.out.println("Find and Replace done!!!");
		        } catch (Exception e1) {
		        
		        	e1.printStackTrace();
		        }
				
				num_linee_caricate++;
				
				//Aggiorna combo
				if (id_car == 1) 
					combo.removeAll();
					
				input_a0[id_car] = a0;
		    	input_a1[id_car] = a1;
				combo.add(id_car.toString());
			}
		});
		salva_su_file.setBounds(728, 147, 175, 35);
		salva_su_file.setEnabled(false);
		salva_su_file.setText("Salva su file");
		
		//Bottone reset
		reset = new Button(shell, SWT.NONE);
		reset.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				//Bottoni
				bottone_esegui.setEnabled(false);
				salva_su_file.setEnabled(false);
				
				//Gestione click
				a0 = new String();
				a1 = new String();
				
				label_a0_cont.setText(" ");
				label_a1_cont.setText(" ");
				i = 1;
				label_numero_caso_test.setText("Inserisci la combinazione");
			}
		});
		reset.setBounds(728, 106, 175, 35);
		reset.setEnabled(false);
		reset.setText("Resetta sequenza");			
		
		//Bottone Salva
		bottone_successivo_salva = new Button(shell, SWT.NONE);
		bottone_successivo_salva.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				
				
				//Abilito bottoni
				reset.setEnabled(true);
				bottone_esegui.setEnabled(true);
				salva_su_file.setEnabled(true);
				progressBar.setVisible(false);
			    scelta_multipla.setLabelProgressBar(" ");

				
				//Gestione click
				if (A0_checked)
					a0 += '1';
				else 	
					a0 += '0';
					
				if (A1_checked)
					a1 += '1';
				else 	
					a1 += '0';
				
				label_a0_cont.setText(a0);
				label_a1_cont.setText(a1);
				
				i = i+1;
				System.out.println("A0 : "+a0+" , A1 : "+a1);;

				setLabelEsito("In attesa dell'esecuzione.");
				setLabelNumeroCasoTest("Sequenza numero: \n" + i.toString());				
			}
		});
		bottone_successivo_salva.setBounds(728, 65, 175, 35);
		bottone_successivo_salva.setText("Inserisci il successivo");
		
		
		//Bottone visualizza report
		bottone_report = new Button(shell, SWT.NONE);
		bottone_report.addSelectionListener(new SelectionAdapter() {
					
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				//Gestione click
				try {
					Runtime.getRuntime().exec("notepad ./report.txt");
				} catch (IOException i) {
					// TODO Auto-generated catch block
					i.printStackTrace();
				}
							
			}
		});
		bottone_report.setBounds(728, 378, 175, 35);
		bottone_report.setText("Visualizza report");
		
		
	}
	
	private void createCombo() {
	
		//Dropdown combo
		combo = new Combo(shell, SWT.NONE);		
		combo.setBounds(728, 275, 175, 33);
		
		//Inserimento da file
		try(BufferedReader br1 = new BufferedReader(new FileReader("./sequenze.txt"))) {
		    for(String line1; (line1 = br1.readLine()) != null; ) {
		        // process the line.
		    	
		    	if (primavolta) {
		    		
		    		//La prima volta prelevo il numero di linee
		    		primavolta = false;
		    		String sub[] = line1.split("d");
		    		num_linee_caricate = Integer.parseInt(sub[0])+1;
		    		System.out.println("Il numero di linee nel file è: " + (num_linee_caricate-1));
		    	}
		    	
		    	else {
		    		
		    		//Le altre volte salvo le stringhe in a0 o a1, separando la linea mediante gli spazi
			    	System.out.println(line1);
			    	String[] temp = line1.split(" ");
			    	id_car = Integer.parseInt(temp[0]);
			    	input_a0[id_car] = temp[1];
			    	input_a1[id_car] = temp[2];
			    	
			    	//System.out.println(id_car);
			    	//System.out.println(input_a0[id_car]);
			    	//System.out.println(input_a1[id_car]);
		    	}
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Imposta dropdown: conterrà in ogni nuovo item l'id della riga (se metti 1 da un errore... ci avevo già pensato)
		Integer i=0;
		Integer j=0;
		String items[] = null;
		
		if (id_car > 0) {
			items = new String[id_car];
			for (i=0; i<id_car; i++) {
				j = i+1;
				items[i] = j.toString();
			}
			combo.setItems(items);
		}
		else combo.setItems("Nessun elemento");
		
		combo.addSelectionListener(new SelectionAdapter() {
		      
			//Selezione dell'id dal dropdown
			public void widgetSelected(SelectionEvent e) {
				
				//Abilito bottoni
				reset.setEnabled(true);
				bottone_esegui.setEnabled(true);
				progressBar.setVisible(false);
			    scelta_multipla.setLabelProgressBar(" ");


				//salva_su_file.setEnabled(true);
		        
				Integer selezione = Integer.parseInt(combo.getText());
				//System.out.println("Hai selezionato l'id: " + selezione);
				
				a0 = input_a0[selezione];
				//System.out.println(a0);
				a1 = input_a1[selezione];
				//System.out.println(a1);
				
				label_a0_cont.setText(a0);
				label_a1_cont.setText(a1);
		    }
			
		});
		
		
	}
	
	private void createCheckbox() {
		

		
		//Checkbox A0
		Button A0 = new Button(shell, SWT.CHECK);
		A0.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				//Gestione checkbox
				Button btn = (Button) e.getSource();

				if (btn.getSelection())
					A0_checked=true;
				else
					A0_checked=false;
			}
		});
		A0.setBounds(284, 75, 134, 25);
		A0.setText("A0");

		//Checkbox A1
		Button A1 = new Button(shell, SWT.CHECK);
		A1.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				//Gestione checkbox
				Button btn = (Button) e.getSource();

				if (btn.getSelection())
					A1_checked=true;
				else
					A1_checked=false;
			}
		});
		A1.setText("A1");
		A1.setBounds(431, 75, 134, 25);
		
		
	}
	
	private void createProgressBar() {

		//Progress bar
        progressBar = new ProgressBar(shell, SWT.NONE);
		progressBar.setBounds(54, 378, 230, 35);
		progressBar.setVisible(false);
		
	}

	public static int getNum_test_ok() {
		return num_test_ok;
	}

	public static void increment_num_test_ok() {
		// TODO Auto-generated method stub
		num_test_ok++;
	}
}
