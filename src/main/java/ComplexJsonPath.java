import org.testng.Assert;

import Body.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonPath {

	public static void main(String[] args) {
		
		JsonPath js= new JsonPath(Payload.coursePrice());
		//Print number of courses return by API
		int count=js.getInt("courses.size()");//size() is used to return the size of an array
		System.out.println("No of courses:"+count);
		
		//Print purchase Amount
		int totalAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println("Purchase Amount:"+totalAmount);
		
		//Title of first course
		String title = js.getString("courses[0].title");
		System.out.println("First course title:"+title);
		
		//Print all courses title and their respective prices
		for(int i=0;i<count;i++) {
			String alltitle = js.get("courses["+i+"].title");
			System.out.println("Course:"+alltitle);
			int allprice = js.getInt("courses["+i+"].price");
			System.out.println("Price:"+allprice);
		}
		
		//Print No of copies sold by RPA
		for(int i=0;i<count;i++) {
			String coursetitle = js.get("courses["+i+"].title");
			if(coursetitle.equalsIgnoreCase("RPA")){
			System.out.println("RPA copies:"+js.getInt("courses["+i+"].copies"));
			break;
			}
			
		}
		
		//Verify sum of all courses price matches with purchase amount
		int total=0;
		for(int i=0;i<count;i++) {
			int sumCopies = js.getInt("courses["+i+"].copies");
			int sumPrice = js.getInt("courses["+i+"].price");
			total=total+sumCopies*sumPrice;
		}
		System.out.println("Total Price:"+total);
		Assert.assertEquals(totalAmount, total,"Comaprion Failed");
	}

}
