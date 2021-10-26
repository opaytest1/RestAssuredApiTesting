package tests;

import java.util.List;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Test113_AuthorizationTest_NotWorking {
	
	@Test
	public void AuthorizationTest() {
		RestAssured.baseURI = "http://restapi.demoqa.com/authentication/CheckForAuthentication";
		RequestSpecification req = RestAssured.given();
		Response res = req.request(Method.GET, "/maps/api/place/nearbysearch/json?location=17.447194%2C78.503667&radius=2000&type=hospital&key=AIzaSyB7FxGbsStb6W1wBEiYyJc_KS6CmPH2UDc") ;
		
		System.out.println(res.getBody().asString());
		Headers headers1 = res.getHeaders();
		Headers headers2 = res.headers();
		
	}
}
