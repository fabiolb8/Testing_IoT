package controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

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
	public void eseguiTestSuite(int id) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
		XMLTestSuiteDAO xml_parser = new XMLTestSuiteDAO();
		TestSuite suite=null;
		try {
			
			suite = xml_parser.readTestSuite(id);
			
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			throw new FileNotFoundException();
		}
		
		
		try {
	
			suite.run();
		
		}catch(Exception e) {
			e.printStackTrace();
			//throw new SuiteException();
		}
		finally {
			aggiungiTestSuite(suite);
		}
		
		
		
	}

	@Override
	public void generaReport(String nomeFile) {

		TXTTestSuiteDAO txt_report_printer = new TXTTestSuiteDAO(nomeFile);
		
		for (int i=0; i<listaTestSuite.size(); i++)
			txt_report_printer.print(listaTestSuite.get(i));
		
		
	}
	
	public TestSuite getSuiteCorrente() {
		
		TestSuite suite = listaTestSuite.get(listaTestSuite.size()-1);
		
		return suite;
	}


	public List<TestSuite> getListaTestSuite() {
		return listaTestSuite;
	} 
	
	

}