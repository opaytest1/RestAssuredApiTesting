package tests;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.TestBase;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.ExcelUtil;
import utils.RandomUserData;

public class Test005_GoRest_DeleteUser extends TestBase{
	
	String[] nameAndUser = RandomUserData.userNameAndEmail();
	String name = nameAndUser[0];
	String email = nameAndUser[1];
	String gender = RandomUserData.gender();
	String status = RandomUserData.status();

	@BeforeClass
	public void createUser() {
		logger.info("******** Started DELETE user test ********");
		logger.info("Started user creation");
		RestAssured.baseURI = "https://gorest.co.in/public";
		req = RestAssured.given();
		JSONObject payload = new JSONObject();
		payload.put("name", name);
		payload.put("gender", gender);
		payload.put("email", email);
		payload.put("status", status);
		req.header("Content-Type", "application/json");
		req.header("Authorization", "Bearer b3d1630903be28b4ee8291e4bfa29fc01ffd0bfe84b2aceeb4944d2dd7a741d5");
		req.body(payload.toJSONString());
		res = req.request(Method.POST, "/v1/users");
		try{
			Thread.sleep(5000);
		}catch(InterruptedException ie) {
			ie.printStackTrace();
		}
		logger.info("Completed user creation");
	}
	
	@Test
	public void deleteUserTest() {
		logger.info("Verifying DELETE user");
		int id = res.jsonPath().get("data.id");
		req = RestAssured.given();
		req.header("Authorization", "Bearer b3d1630903be28b4ee8291e4bfa29fc01ffd0bfe84b2aceeb4944d2dd7a741d5");
		res = req.request(Method.DELETE, "/v1/users/"+id);
		if((res.statusCode() == 204) && (res.body().asString().equals(""))){
			logger.info("deleteUserTest Success!");
			logger.info("Status code: " + res.statusCode());
		}
		logger.info("Verified DELETE user");
	}
	
	@AfterClass
	public void tearDown() {
		logger.info("******** Completed DELETE user test ********");
	}
}
