package base;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.BeforeClass;
import org.testng.asserts.SoftAssert;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TestBase {
	public static RequestSpecification req;
	public static Response res;
	public String id = "1727"; //hardcoded for GET user by ID
	public Logger logger;
	public SoftAssert softAssert;
	
	@BeforeClass
	public void setup() {
		logger = Logger.getLogger("RestAPIProject");
		PropertyConfigurator.configure("log4j.properties");
		logger.setLevel(Level.DEBUG);
	}
}
