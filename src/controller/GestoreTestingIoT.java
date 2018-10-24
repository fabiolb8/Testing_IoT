package controller;
import java.util.ArrayList;
import java.util.List;
import dao.DAOException;
import dao.TXTTestSuiteDAO;
import dao.XMLTestSuiteDAO;
import entity.*;

public class GestoreTestingIoT implements IGestoreTestingIoT {
	//pattern singleton

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
	
	private void aggiungiTestSuite(TestSuite testSuite) {
		if(testSuite!= null)
		listaTestSuite.add(testSuite);
	}

	@Override
	public void eseguiTestSuite(int id) throws PersistanceException, ConnectionException {
		
		XMLTestSuiteDAO xml_parser = new XMLTestSuiteDAO();
		TestSuite suite=null;
		try {	
			//recupero della test suite dal file XML
			suite = xml_parser.readTestSuite(id);
			//esecuzione test suite
			suite.run();
		} catch (DAOException e) {
			throw new PersistanceException("Errore nel recupero della test suite "+id);	
		} finally {
			aggiungiTestSuite(suite);
		}
	}

	@Override
	public void generaReport(String nomeFile) throws PersistanceException {

		TXTTestSuiteDAO report_printer = new TXTTestSuiteDAO(nomeFile);
		try {
			//generazione report
			report_printer.printAll(listaTestSuite);
		} catch (DAOException e) {
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