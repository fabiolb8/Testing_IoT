package dao;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import entity.TestSuite;

public class XMLTestSuiteDAO implements ITestSuiteDAO {

	@Override
	public void createTestSuite(TestSuite testSuite) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TestSuite readTestSuite(int id) throws DAOException{
		// TODO Auto-generated method stub

		TestSuite suite = null;

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(TestSuite.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			     
			//We had written this file in marshalling example
			suite = (TestSuite) jaxbUnmarshaller.unmarshal( new File("TS"+id+".xml") );
		} catch (JAXBException e) {
			// TODO Auto-generated catch block

			throw new DAOException("Impossibile leggere la suite "+id);
		}
		    
		    /*
		    System.out.println(suite.getId());
	        System.out.println(suite.getDescrizione());
			
		    for(TestCase tc  : suite.getListaTestCase())
		    {
		        System.out.println(tc.getId());
		        System.out.println(tc.getDescrizione());
		        
		        for(Step s  : tc.getListaStep())
			    {
			        System.out.println(s.getNumero());
			        System.out.println(s.getInputFumo());
			        System.out.println(s.getInputTemperatura_zona1());
			        System.out.println(s.getInputTemperatura_zona2());
			        System.out.println(s.getInputTemperatura_zona3());
			        
			    }
		    }
		    */
        
		return suite;
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