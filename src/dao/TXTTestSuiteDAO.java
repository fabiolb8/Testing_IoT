package dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;

import entity.*;

public class TXTTestSuiteDAO implements ITestSuiteDAO {

	private String nomeFile;
	private FileOutputStream fileoutputstream = null;
	private PrintStream printstream = null;
	private File file= null;
	
	public TXTTestSuiteDAO(String nomeFile) {

		this.nomeFile = nomeFile;
		file=new File("./reports/"+nomeFile);
	}


	public void printAll(List<TestSuite> listaTestSuite) throws DAOException{
		
		try {
			
		if (file.createNewFile()) {
		
			fileoutputstream = new FileOutputStream(file,true);
			printstream = new PrintStream(fileoutputstream);
			
			for (int i=0; i<listaTestSuite.size(); i++) {
			
			printstream.println("TS"+listaTestSuite.get(i).getId()+": " + listaTestSuite.get(i).getDataEsecuzione() + " Numero test OK: " + 
								listaTestSuite.get(i).getNumTestOk() +" su " +listaTestSuite.get(i).getListaTestCase().size());
			printstream.println();
			
			for (int j=0; j<listaTestSuite.get(i).getListaTestCase().size(); j++) {
				
				TestCase test = listaTestSuite.get(i).getListaTestCase().get(j);
				String esitoTC=test.getEsito().name();
				printstream.println("  TC"+test.getId()+": " + esitoTC);
				
				if(esitoTC.compareTo("NEGATIVO")==0) {
					
					for(int k=0; k<test.getListaStep().size();k++) {
						Step step = test.getListaStep().get(k);
						printstream.println("	Step # "+step.getNumero());
						printstream.println("  	   Allarme Rilevato : "+step.getOutputAllarmeRilevato());
						printstream.println("	   Ventilazione Rilevato : "+step.getOutputventilazioneRilevato());
						printstream.println();
					}
					
					
				}
				
				printstream.println();
			}
			printstream.println("__________________________________________________________");
			

			
			
			}
			
			System.out.println("Report generato : "+" : nomeFile = "+nomeFile);
			
		}
		else {
			throw new DAOException("Questo report già esiste, provare con un altro nome");
		}
			
		} catch (IOException e1) {
			throw new DAOException("Impossibile generare il file di report");
		}
		
		
	}

	@Override
	public void createTestSuite(TestSuite testSuite) {
		
	}

	@Override
	public TestSuite readTestSuite(int id) {
		return null;
	}

	@Override
	public void updateTestSuite(int id, TestSuite testSuite) {
		
	}

	@Override
	public void deleteTestSuite(int id) {
		
	}

}