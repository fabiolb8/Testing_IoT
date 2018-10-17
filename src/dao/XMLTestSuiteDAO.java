package dao;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import entity.TestSuite;

public class XMLTestSuiteDAO implements ITestSuiteDAO {

	
	@Override
	public void createTestSuite(TestSuite testSuite) {
		
	}

	@Override
	public TestSuite readTestSuite(int id) throws DAOException{
		TestSuite suite = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(TestSuite.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			suite = (TestSuite) jaxbUnmarshaller.unmarshal( new File("./repository/"+"TS"+id+".xml") );
			if (suite.getListaTestCase().isEmpty()) {
				throw new DAOException("XML incompleto");
			}
			for(int i=0;i<suite.getListaTestCase().size();i++) {
				if(suite.getListaTestCase().get(i).getListaStep().isEmpty()) {
					throw new DAOException("XML incompleto");
				}
			}
		} catch (JAXBException e) {
			throw new DAOException("Impossibile leggere la suite "+id);
		}
		return suite;
	}

	
	public void updateTestSuite(int id, TestSuite testSuite) {
		
	}

	public void deleteTestSuite(int id) {
		
	}
}