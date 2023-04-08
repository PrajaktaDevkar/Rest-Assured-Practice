
package pojo;
import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class SerializeTest {
	public static void main(String[] args) {
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		AddPlace p=new AddPlace();
		
		p.setAccuracy(50);
		p.setAddress("29, side layout, cohen 09");
		p.setName("Frontline house");
		p.setLanguage("French-IN");
		p.setPhone_number("(+91) 983 893 3937");
		p.setWebsite("http://google.com");
		List<String> myList=new ArrayList<String>();
		myList.add("shoe park");
		myList.add("shop");
		p.setTypes(myList);
		Location l=new Location();
		l.setLat(-38.383494);
		l.setLag(33.427362);
		p.setLocation(l);
		
		Response res=given().queryParam("key", "qaclick123").body(p)
		.when().post("/maps/api/place/add/json")
		.then().log().all().statusCode(200).extract().response();
		String responseString=res.asString();
		System.out.println(responseString);
		
		
	}

}
