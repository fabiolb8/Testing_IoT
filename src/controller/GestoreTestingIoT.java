package controller;

import java.util.ArrayList;
import java.util.List;

import dao.DAOException;
import dao.TXTTestSuiteDAO;
import dao.XMLTestSuiteDAO;
import entity.*;

public class GestoreTestingIoT implements IGestoreTestingIoT {

	/**
	 * 
	 * @param testSuite
	 */
	private static GestoreTestingIoT gestore = null;
	private List<TestSuite> listaTestSuite; 
	
	private GestoreTestingIoT(){
		
		listaTestSuite = new ArrayList<TestSuite>();
	}
	
	
	public static GestoreTestingIoT getInstance(){
		
		if(gestore==null){
			gestore = new GestoreTestingIoT();
		}
		return gestore;
	}
	
	public void aggiungiTestSuite(TestSuite testSuite) {
		listaTestSuite.add(testSuite);
	}

	@Override
	public void eseguiTestSuite(int id) throws PersistanceException, ConnectionException {
		// TODO Auto-generated method stub
		
		/*try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		XMLTestSuiteDAO xml_parser = new XMLTestSuiteDAO();
		TestSuite suite=null;
		try {
			
			suite = xml_parser.readTestSuite(id);
			suite.run();
			
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			throw new PersistanceException("Errore nel recupero della test suite "+id);
			
		} finally {
			aggiungiTestSuite(suite);
		}
	}

	@Override
	public void generaReport(String nomeFile) throws PersistanceException {

		TXTTestSuiteDAO report_printer = new TXTTestSuiteDAO(nomeFile);
		
		try {
			report_printer.printAll(listaTestSuite);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			throw new PersistanceException(e.getMessage());
		}
		
		
	}
	
	public TestSuite getSuiteCorrente() {
		
		TestSuite suite = listaTestSuite.get(listaTestSuite.size()-1);
		
		return suite;
	}


	public List<TestSuite> getListaTestSuite() {
		return listaTestSuite;
	} 
	
	

}