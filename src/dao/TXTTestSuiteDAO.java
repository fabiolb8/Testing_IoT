package dao;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import entity.*;

public class TXTTestSuiteDAO implements ITestSuiteDAO {

	private String nomeFile;
	
	public TXTTestSuiteDAO(String nomeFile) {

		this.nomeFile = nomeFile;
	}

	/**
	 * 
	 * @param testSuite
	 */
	public void print(TestSuite testSuite) throws DAOException{

		//TS1 : numtestok timestamp
		//TC1 : esito
		//TC2 : esito
		// _______
		
		FileOutputStream fileoutputstream = null;
		PrintStream printstream = null;
		
		try {
			fileoutputstream = new FileOutputStream(nomeFile,false);
			printstream = new PrintStream(fileoutputstream);
			
			printstream.println("TS"+testSuite.getId()+": " + testSuite.getDataEsecuzione() + " Numero test OK: " + testSuite.getNumTestOk() +" su " +testSuite.getListaTestCase().size());
			for (int i=0; i<testSuite.getListaTestCase().size(); i++) {
				
				TestCase test = testSuite.getListaTestCase().get(i);
				printstream.println("TC"+test.getId()+": " + test.getEsito().name());	
			}
			printstream.println("__________________________________________________________");
			
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block

			throw new DAOException("Impossibile generare il file di report");
		}
		
	}

	@Override
	public void createTestSuite(TestSuite testSuite) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TestSuite readTestSuite(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateTestSuite(int id, TestSuite testSuite) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteTestSuite(int id) {
		// TODO Auto-generated method stub
		
	}

}