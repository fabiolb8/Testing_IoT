package dao;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import entity.Step;
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
			suite = (TestSuite) jaxbUnmarshaller.unmarshal( new File("./repository/"+"TS"+id+".xml") );
			
			
			suite.getListaTestCase().get(0).getListaStep().get(0);	//gestire oggetti null nel xml (?)
			
			
		} catch (JAXBException e) {
			// TODO Auto-generated catch block

			throw new DAOException("Impossibile leggere la suite "+id);
		} catch(NullPointerException e1) {
			throw new DAOException("XML incompleto");
		}
		    
        
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