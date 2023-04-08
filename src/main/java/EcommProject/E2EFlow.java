
package EcommProject;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

public class E2EFlow {
	
	public static void main(String[] args) {
		
		//In order to bypass SSL certification using relaxHTTPSValidation() in given()
		
		//Login Code
		RequestSpecification req=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
		.setContentType(ContentType.JSON).build();
		
		LoginRequest loginrequest=new LoginRequest();
		loginrequest.setUserEmail("prajaktadevkar1998@gmail.com");
		loginrequest.setUserPassword("*Secure#81@");
		
		RequestSpecification reqLogin=given().log().all().spec(req).body(loginrequest);
		
		LoginResponse loginResponse = reqLogin.when().post("/api/ecom/auth/login")
		.then().log().all().extract().response().as(LoginResponse.class);
		
		System.out.println(loginResponse.getToken());
		String token=loginResponse.getToken();
		System.out.println(loginResponse.getUserId());
		String userId=loginResponse.getUserId();
		
		//Add Product
		
		RequestSpecification addProductBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
		.addHeader("authorization", token).build();
		
		RequestSpecification reqAddProduct = given().log().all().spec(addProductBaseReq)
		.param("productName", "Laptop").param("productAddedBy", userId).param("productCategory", "fashion")
		.param("productSubCategory", "shirts").param("productPrice", "22356")
		.param("productDescription", "Lenova").param("productFor", "men")
		.multiPart("productImage",new File("C:\\Users\\praja\\OneDrive\\Desktop\\Photo.jpg"));
		
		String addProductResponse = reqAddProduct.when().post("/api/ecom/product/add-product")
		.then().log().all().assertThat().statusCode(201).extract().response().asString();
		
		JsonPath js= new JsonPath(addProductResponse);
		String productId=js.getString("productId");
		System.out.println(productId);
		
		//Create Order
		
		RequestSpecification createOrderBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("authorization", token)
		.setContentType(ContentType.JSON).build();
		
		OrderDetail orderDetail=new OrderDetail();
		orderDetail.setCountry("India");
		orderDetail.setProductId(productId);
		
		List<OrderDetail> orderDetailList = new ArrayList();
		orderDetailList.add(orderDetail);
		
		Orders orders=new Orders();
		orders.setOrders(orderDetailList);
		
		
		RequestSpecification createOrderReq=given().log().all().spec(createOrderBaseReq).body(orders);
		String responseAddOrder = createOrderReq.when().post("/api/ecom/order/create-order")
		.then().log().all().extract().response().asString();
		System.out.println(responseAddOrder);
		
		
		//Delete Order
		RequestSpecification deleteProdBaseReq = new RequestSpecBuilder()
		.setBaseUri("https://rahulshettyacademy.com").addHeader("authorization", token)
		.build();
		
		RequestSpecification deleteProdReq=given().log().all().spec(deleteProdBaseReq).pathParam("productId", productId);
		String deleteProductResponse=deleteProdReq.when().delete("/api/ecom/product/delete-product/{productId}")
		.then().log().all().extract().response().asString();
		
		JsonPath js1=new JsonPath(deleteProductResponse);
		Assert.assertEquals("Product Deleted Successfully", js1.getString("message"));
		
	}
	

}
