package boundary;

import java.io.FileNotFoundException;

import controller.GestoreTestingIoT;

public class BTester {

	/**
	 * 
	 * @param id
	 * @throws FileNotFoundException 
	 */
	public String eseguiTestSuite(int id) throws FileNotFoundException {
		
		GestoreTestingIoT gestore = GestoreTestingIoT.getInstance();
		gestore.eseguiTestSuite(id);
		int numOK = gestore.getSuiteCorrente().getNumTestOk();
		int numTOT = gestore.getSuiteCorrente().getListaTestCase().size();
		
		String newLine=new String("Test Suite "+ id + ". Numero di test OK/TOT = " + numOK + "/" + numTOT);
		
		return newLine;
	}

	public void generaReport(String nomeFile) {

		GestoreTestingIoT gestore = GestoreTestingIoT.getInstance();
		gestore.generaReport(nomeFile);
		
	}

}