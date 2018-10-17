package dao;

import entity.*;

public interface ITestSuiteDAO {

	void createTestSuite(TestSuite testSuite);

	TestSuite readTestSuite(int id) throws DAOException;

	void updateTestSuite(int id, TestSuite testSuite);

	void deleteTestSuite(int id);

}