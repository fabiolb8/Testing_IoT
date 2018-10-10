package dao;

import entity.*;

public interface ITestSuiteDAO {

	/**
	 * 
	 * @param testSuite
	 */
	void createTestSuite(TestSuite testSuite);

	/**
	 * 
	 * @param id
	 * @throws DAOException 
	 */
	TestSuite readTestSuite(int id) throws DAOException;

	/**
	 * 
	 * @param id
	 * @param testSuite
	 */
	void updateTestSuite(int id, TestSuite testSuite);

	/**
	 * 
	 * @param id
	 */
	void deleteTestSuite(int id);

}