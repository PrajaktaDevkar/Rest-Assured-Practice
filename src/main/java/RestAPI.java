import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import Body.Payload;
import Body.ReusableMethod;


public class RestAPI {

	public static void main(String[] args) {
		//given() - All input details
		//when() - Submit the API - Resource and HTTP method will go under this
		//then() - Validate the response
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
	String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(Payload.addPlace())
		.when().post("maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).body("scope",equalTo("APP"))
		.header("server","Apache/2.4.41 (Ubuntu)")
		.extract().response().asString();
		//Post (Add) place->Put (Update) place with new Address -> Get place to validate if New address is present in response
	
	System.out.println(response);
	JsonPath js=ReusableMethod.rawToJson(response);
	String placeId = js.getString("place_id");
	System.out.println(placeId);
	
	//Update Place
	
	String newAdd="70 Summer walk, USA";
	
	given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
	.body("{\r\n"
			+ "  \"place_id\": \""+placeId+"\",\r\n"
			+ "  \"address\": \""+newAdd+"\",\r\n"
			+ "  \"key\": \"qaclick123\"\r\n"
			+ "}")
	.when().put("maps/api/place/update/json")
	.then().log().all().assertThat().statusCode(200).body("msg",equalTo("Address successfully updated"));
		
	//Get Place
	
	String getPlaceResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
	.when().get("maps/api/place/get/json")
	.then().log().all().assertThat().statusCode(200).extract().response().asString();
	
	JsonPath js1=ReusableMethod.rawToJson(getPlaceResponse);
	String actualAdd=js1.getString("address");
	
	Assert.assertEquals(actualAdd, newAdd,"Comparison Failed");

	}

}