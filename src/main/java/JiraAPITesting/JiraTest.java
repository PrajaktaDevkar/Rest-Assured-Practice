package JiraAPITesting;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import Body.ReusableMethod;

public class JiraTest {
	
	public static void main(String[] args) {
		
		RestAssured.baseURI="http://localhost:8081";
		
		
		//Seesion Authentiation
		String seesionResponse=given().log().all().header("Content-Type","application/json")
		.body("{\r\n"
				+ "  \"username\": \"devkarprajakta1998\",\r\n"
				+ "  \"password\": \"*secure*\"\r\n"
				+ "}")
		.when().post("/rest/auth/1/session")
		.then().log().all().extract().response().asString();
		System.out.println(seesionResponse);
		JsonPath js = ReusableMethod.rawToJson(seesionResponse);
		String sessionValue = js.getString("session.value");
		String session= js.getString("session.name")+"="+sessionValue;
		System.out.println(session);
		
		
		//Create a issue
		given().log().all().header("Content-Type","application/json").header("JSESSIONID",sessionValue)
		.body(" {\r\n"
				+ "  \"fields\": {\r\n"
				+ "    \"project\": {\r\n"
				+ "      \"key\": \"RES\"\r\n"
				+ "    },\r\n"
				+ "    \"summary\": \"REST ye merry gentlemen.\",\r\n"
				+ "    \"description\": \"Creating of an issue using project keys and issue type names using the REST API\",\r\n"
				+ "    \"issuetype\": {\r\n"
				+ "      \"name\": \"Bug\"\r\n"
				+ "    }\r\n"
				+ "  }\r\n"
				+ "}")
		.when().post("/rest/api/2/issue/")
		.then().log().all();
		
		
	}



}
