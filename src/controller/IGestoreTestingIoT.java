package controller;

import java.io.FileNotFoundException;

public interface IGestoreTestingIoT {

	/**
	 * 
	 * @param id
	 * @throws FileNotFoundException 
	 */
	void eseguiTestSuite(int id) throws FileNotFoundException;

	void generaReport();

}