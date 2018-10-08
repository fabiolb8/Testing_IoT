package dao;

import javax.xml.bind.JAXBException;

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
	 * @throws JAXBException 
	 */
	TestSuite readTestSuite(int id) throws JAXBException;

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