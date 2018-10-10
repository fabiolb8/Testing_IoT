package controller;

import entity.ConnectionException;

public interface IGestoreTestingIoT {

	/**
	 * 
	 * @param id
	 * @throws PersistanceException 
	 * @throws ConnectionException 
	 */
	void eseguiTestSuite(int id) throws PersistanceException, ConnectionException;

	void generaReport(String nomeFile) throws PersistanceException;

}