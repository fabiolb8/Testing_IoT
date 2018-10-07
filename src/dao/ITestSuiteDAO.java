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
	 */
	TestSuite readTestSuite(int id);

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