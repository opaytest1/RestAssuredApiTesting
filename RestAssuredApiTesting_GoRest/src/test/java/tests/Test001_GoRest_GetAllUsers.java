package tests;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import base.TestBase;
import io.restassured.RestAssured;
import io.restassured.http.Method;

public class Test001_GoRest_GetAllUsers extends TestBase{
	
	@BeforeClass
	void sendRequest() {
		logger.info("******** Started Test001_GoRest_GetAllUsers ********");
		softAssert = new SoftAssert();
		RestAssured.baseURI = "https://gorest.co.in/public";
		req = RestAssured.given();
		res = req.request(Method.GET, "/v1/users");
	}
	
	@Test
	void getAllUsers() {
		logger.info("Verifying status code...");
		int statusCode = res.statusCode();
		logger.info("Status Code: " + statusCode);
		softAssert.assertEquals(statusCode, 200, "Status code verification");
		
		logger.info("Verifying response time...");
		long responseTime = res.time();
		logger.info("Response Time: " +responseTime);
//		if(responseTime>1000) logger.warn("Response time is more that 1 second");
		softAssert.assertTrue(responseTime<3000, "Response Time verification");
		
		softAssert.assertAll();
	}
	
	@AfterClass
	void tearDown() {
		logger.info("******** Stopped Test001_GoRest_GetAllUsers ********");
	}
}
