
import Body.Payload;
import Body.ReusableMethod;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.LinkedList;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Dynamicjson {
	
	
	@Test(dataProvider="BooksData")
	public void addBook(String isbn,String asile) {
		
		RestAssured.baseURI="http://216.10.245.166";
		String resp = given().log().all().header("Content-Type","application/json")
		.body(Payload.addBook(isbn,asile))
		.when().post("/Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200).
		extract().response().asString();
		System.out.println(resp);
		JsonPath js = ReusableMethod.rawToJson(resp);
		 String id=js.getString("ID");
	
	}
	
	
	@DataProvider(name="BooksData")
	public Object[][] getData(){
		return new Object[][] {{"8","djf"},{"0","kjjf"},{"8","jhf"}};
	}
	

}
