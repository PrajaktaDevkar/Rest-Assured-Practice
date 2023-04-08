import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class POJOTest {
	
	public static void main(String [] args) {
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick").header("content-Type","application/json")
		.body("{\r\n"
				+ "  \"accuracy\": 500,\r\n"
				+ "  \"name\": \"Frontline house\",\r\n"
				+ "  \"phone_number\": \"(+91)983 893 3937\",\r\n"
				+ "  \"address\": \"29,side layout,cohen 09\",\r\n"
				+ "  \"website\": \"http://google.com\",\r\n"
				+ "  \"language\": \"French-IN\",\r\n"
				+ "  \"types\": [\r\n"
				+ "    \"shoe park\",\r\n"
				+ "    \"shop\"\r\n"
				+ "  ],\r\n"
				+ "  \"location\": {\r\n"
				+ "    \"lat\": -38.383494,\r\n"
				+ "    \"lng\": 33.427362\r\n"
				+ "  }\r\n"
				+ "}")
		.when().post("/maps/api/place/add/json")
		.then().log().all().statusCode(200).extract().response().asString();
		System.out.println(response);
		JsonPath js=new JsonPath(response);
		String lan=js.getString("scope");
		System.out.println(lan);
	}

}
