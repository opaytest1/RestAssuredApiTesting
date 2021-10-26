package tests;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.ExcelUtil;

public class Test111_GoRest_Independent {
	RequestSpecification httpRequest;
	Response httpResponse;
	@Test(enabled=false)
	public void getAllUsers() {
		RestAssured.baseURI = "https://gorest.co.in/public";
		
		httpRequest = RestAssured.given();
		
		httpResponse = httpRequest.request(Method.GET, "/v1/users");
		System.out.println(httpResponse.getStatusCode());
		System.out.println(httpResponse.getStatusLine());
		System.out.println(httpResponse.getBody().asString());
	}
	
	@Test(enabled=false)
	public void getUserById() {
		RestAssured.baseURI = "https://gorest.co.in/public";
		RequestSpecification req = RestAssured.given();
		
		Response res = req.request(Method.GET, "/v1/users/1551") ;
		
		System.out.println("*********** Response Time1 ***********");
		System.out.println(res.getTime());
		
		System.out.println("*********** STATUS CODE ***********");
		System.out.println(res.statusCode());
		System.out.println("*********** body() ***********");
		System.out.println(res.body().asString());
		System.out.println("*********** getBody() ***********");
		System.out.println(res.getBody().asString());
		System.out.println("*********** headers() ***********");
		System.out.println(res.headers());
		System.out.println("*********** getHeaders() ***********");
		System.out.println(res.getHeaders());
		System.out.println("*********** Individual header ***********");
		System.out.println(res.header("Server"));
		System.out.println("*********** headers using forloop ***********");
		for(Header h : res.headers()) {
			System.out.println(h.getName() + " -- " + h.getValue());
		}
		System.out.println("*********** response JSON validation ***********");
		System.out.println((String)res.jsonPath().get("data.name"));
		System.out.println((String)res.jsonPath().get("meta"));
		
		Assert.assertTrue(res.getBody().asString().contains("Ballu"));
		Assert.assertEquals(res.getBody().asString().contains("Ballu"), true);

		System.out.println("*********** Response Time2 ***********");
		System.out.println(res.getTime());
	}
	
	@Test(enabled=false, dataProvider="createUserDataProvider")
	public void createUser(String name, String gender, String email, String status) {
		RestAssured.baseURI = "https://gorest.co.in/public";
		
		httpRequest = RestAssured.given();
		
		JSONObject payload = new JSONObject();
		payload.put("name", name);
		payload.put("gender", gender);
		payload.put("email", email);
		payload.put("status", status);
		
		httpRequest.header("Content-Type", "application/json");
		httpRequest.header("Authorization", "Bearer b3d1630903be28b4ee8291e4bfa29fc01ffd0bfe84b2aceeb4944d2dd7a741d5");
		httpRequest.body(payload.toJSONString());
		
		httpResponse = httpRequest.request(Method.POST, "/v1/users");
		
		System.out.println(httpResponse.getStatusCode());
		System.out.println(httpResponse.getBody().asString());
		
		System.out.println((String)httpResponse.jsonPath().get("data.name"));
		
	}
	
	@Test(enabled = true, dataProvider = "createUserDataProvider")
	public void testData(String name, String gender, String email, String status) {
		System.out.println(name + " - " + gender + " - " + email  + " - " +  status);
	}
	
	@DataProvider(name="createUserDataProvider")
	String[][] dataToCreateUser() throws IOException{
		String path = System.getProperty("user.dir")+ "/src/test/java/resources/testdata.xlsx";
		int rows = ExcelUtil.getRowCount(path, "Sheet1");
		int cols = ExcelUtil.getCellCount(path, "Sheet1", rows);
		String[][] data = new String[rows][cols];
		for(int i=1;i<=rows; i++) {
			for(int j=0;j<cols;j++) {
				data[i-1][j] = ExcelUtil.getCellData(path, "Sheet1", i, j) ;
			}
		}
//		String[][] data = {
//				{"Ballu Balram", "male", "ballu.balram@proq.com", "active"},
//				{"Lallu Prasad", "male", "lallu.prasad@proq.com", "inactive"},
//				{"Tallu Kumar", "male", "tallu.kumar@proq.com", "active"}
//				};
		return data;
	}
}
