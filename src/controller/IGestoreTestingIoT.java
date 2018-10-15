package controller;

import entity.ConnectionException;

public interface IGestoreTestingIoT {


	void eseguiTestSuite(int id) throws PersistanceException, ConnectionException;

	void generaReport(String nomeFile) throws PersistanceException;

}