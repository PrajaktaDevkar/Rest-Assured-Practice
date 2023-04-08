import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

import Body.Payload;
import Body.ReusableMethod;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

//The Json which remains static through out the api we will store it in the document and read the date 
// with Files.readAllBytes().

public class JsonParametrization {
	
	public static void main(String[] args) throws IOException {
		//given() - All input details
		//when() - Submit the API - Resource and HTTP method will go under this
		//then() - Validate the response
		//Content of file to String ->Content of file converted into byte -> byte data to String
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
	String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(new String(Files.readAllBytes(Paths.get("C:\\Users\\praja\\OneDrive\\Documents\\JsonFormatterAPI.json"))))
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
