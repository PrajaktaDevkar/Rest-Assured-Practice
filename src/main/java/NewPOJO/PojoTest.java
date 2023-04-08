
package NewPOJO;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;
import io.restassured.RestAssured;
import io.restassured.response.Response;


public class PojoTest {
	public static void main(String [] args) {
		RestAssured.baseURI="https://rahulshettyacademy.com";
		Place p=new Place();
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
		Loc l=new Loc();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		p.setLocation(l);
		
		Response res=given().queryParam("key", "qaclick123").body(p)
				.when().post("/maps/api/place/add/json")
				.then().log().all().statusCode(200).extract().response();
				String responseString=res.asString();
				System.out.println(responseString);
				
		
	}

}
