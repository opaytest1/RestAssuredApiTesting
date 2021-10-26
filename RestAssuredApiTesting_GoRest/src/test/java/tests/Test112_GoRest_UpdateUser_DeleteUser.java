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

public class Test112_GoRest_UpdateUser_DeleteUser extends TestBase{
	
	
	String[] nameAndUser = RandomUserData.userNameAndEmail();
	String name = nameAndUser[0];
	String email = nameAndUser[1];
	String updatedEmail = nameAndUser[2];
	String gender = RandomUserData.gender();
	String status = RandomUserData.status();
	
	
//	@BeforeClass
//	public void test11() {
//		System.out.println(name + " - " + email + " - " + gender + " - " + status);
//	}
	
	@BeforeClass
	public void createUser() {
		logger.info("******** Started user creation ********");
		RestAssured.baseURI = "https://gorest.co.in/public";
		System.out.println("Request before setup: " + req);
		req = RestAssured.given();
		JSONObject payload = new JSONObject();
		System.out.println("Payload before setup: " + payload.toJSONString());
		payload.put("name", name);
		payload.put("gender", gender);
		payload.put("email", email);
		payload.put("status", status);
		System.out.println("Payload after setup: " + payload.toJSONString());
		req.header("Content-Type", "application/json");
		req.header("Authorization", "Bearer b3d1630903be28b4ee8291e4bfa29fc01ffd0bfe84b2aceeb4944d2dd7a741d5");
		req.body(payload.toJSONString());
		System.out.println("Request after setup: " + req);
		res = req.request(Method.POST, "/v1/users");
		try{
			Thread.sleep(5000);
		}catch(InterruptedException ie) {
			ie.printStackTrace();
		}
		logger.info("******** Completed user creation ********");
	}
	
	@Test
	public void createUserTest() {
		logger.info("Verifying user creation");
		System.out.println(res.getStatusCode());
		System.out.println("Response after POST: " + res.getBody().asString());
		System.out.println((String)res.jsonPath().get("data.id"));
		System.out.println((String)res.jsonPath().get("data.name"));
		System.out.println((String)res.jsonPath().get("data.email"));
		System.out.println((String)res.jsonPath().get("data.status"));
		System.out.println((String)res.jsonPath().get("data.gender"));
		logger.info("Verified user creation");
	}
	
	@Test(dependsOnMethods = {"createUserTest"})
	public void updateUserTest() {
		logger.info("Verifying UPDATE user");
		System.out.println((String)res.jsonPath().get("data.id"));
		int id = res.jsonPath().get("data.id");
		req = RestAssured.given();
		
		JSONObject payload = new JSONObject();
		System.out.println("Payload before update setup: " + payload.toJSONString());
		payload.put("name", name);
		payload.put("gender", gender);
		payload.put("email", updatedEmail);
		payload.put("status", status);
		System.out.println("Payload after update setup: " + payload.toJSONString());
		req.header("Content-Type", "application/json");
		req.header("Authorization", "Bearer b3d1630903be28b4ee8291e4bfa29fc01ffd0bfe84b2aceeb4944d2dd7a741d5");
		req.body(payload.toJSONString());
		System.out.println("Response before update: " + res.asString());
		res = req.request(Method.PUT, "/v1/users/"+id);
		System.out.println("Response after update: " + res.asString());
		System.out.println(res.statusCode());
		System.out.println((String)res.jsonPath().get("data.id"));
		System.out.println((String)res.jsonPath().get("data.name"));
		System.out.println((String)res.jsonPath().get("data.email"));
		System.out.println((String)res.jsonPath().get("data.status"));
		System.out.println((String)res.jsonPath().get("data.gender"));
		logger.info("Verified UPDATE user");
	}
	
	@Test(dependsOnMethods = { "updateUserTest", "createUserTest" })
	public void deleteUser() {
		logger.info("Verifying DELETE user");
		req = RestAssured.given();
		req.header("Content-Type", "application/json");
		req.header("Authorization", "Bearer b3d1630903be28b4ee8291e4bfa29fc01ffd0bfe84b2aceeb4944d2dd7a741d5");
		int id = res.jsonPath().get("data.id");
		System.out.println("Response before delete: " + res.asString());
		res = req.request(Method.DELETE, "/v1/users/" + id);
		System.out.println("Response after delete: " + res.asString());
		System.out.println(res.statusCode());
		if((res.asString().equals("")) && (res.statusCode()==204)){
			System.out.println("Delete successful!");
		}
		logger.info("Verified DELETE user");
	}
	
	@AfterClass
	public void tearDown() {
		logger.info("******** Completed DELETE user test ********");
	}
}
