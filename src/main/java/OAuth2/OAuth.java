
package OAuth2;
import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourse;
import pojo.WebAutomation;

public class OAuth {
	
	public static void main(String[] args) throws InterruptedException {
		
		
		
		//Get Code (OTP)
		
		//System.setProperty("webdriver.chrome.driver", "C:\\Users\\praja\\OneDrive\\Documents\\Selenium\\ChromeDriver\\chromedriver.exe");
		
		//ChromeOptions option = new ChromeOptions();
		//option.addArguments("incognito");
		
		//WebDriver driver= new ChromeDriver();
		//driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php&state=verifyfidss");
		//driver.findElement(By.xpath("//input[@type='email']")).sendKeys("devkarprajakta1998");
		//driver.findElement(By.xpath("//input[@type='email']")).sendKeys(Keys.ENTER);
		//Thread.sleep(4000);
		//driver.findElement(By.xpath("//input[@type='password']")).sendKeys("*secure*");
		//driver.findElement(By.xpath("//input[@type='password']")).sendKeys(Keys.ENTER);
		//Thread.sleep(4000);
		//String url=driver.getCurrentUrl();
		
		String[] courseTitles= {"Selenium Webdriver Java","Cypress","Protractor"};
		
		String url="https://rahulshettyacademy.com/getCourse.php?state=verifyfidss&code=4%2F0AWtgzh5jprv-GIcHAMFiXLXKr70lB0dpf8IT5OZ41kktUU0uDS237WyWV7UtQVVLvTdKAg&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none";
		String partialcode=url.split("code=")[1];
		String code=partialcode.split("&scope")[0];
		System.out.println(code);
		
		//Access Token
		 String accessTokenResp = given().urlEncodingEnabled(false).log().all()
		 .queryParams("code", code)
		.queryParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParams("client_secret","erZOWM9g3UtwNRj340YYaK_W")
		.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
		.queryParams("grant_type", "authorization_code")
		.queryParams("state", "verifyfjdss")
		.queryParams("session_state", "ff4a89d1f7011eb34eef8cf02ce4353316d9744b..7eb8")
		.when().log().all().post("https://www.googleapis.com/oauth2/v4/token").asString();
		System.out.print("Access token:"+accessTokenResp);
		JsonPath js= new JsonPath(accessTokenResp);
		String accessToken=js.getString("access_token");
		
		
		//Actual Response
		String response=given().log().all().contentType("application/json").queryParam("access_token", accessToken)
		.when().get("https://rahulshettyacademy.com/getCourse.php")
		.then().extract().response().asString();
		System.out.println(response);
		
		//Pojo class
		
		GetCourse gc=given().queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON)
		.when().get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);
		
		System.out.println("Instructor:"+ gc.getInstructor());
		System.out.println("LinkedIn:"+gc.getLinkedIn());
		
		System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
		
		List<Api> apiCourses=gc.getCourses().getApi();
		for(int i=0;i<apiCourses.size();i++) {
			
			if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
			{
				System.out.println(apiCourses.get(i).getPrice());
			}
		}
		
		
		//Course name of webAutomation
		
		ArrayList a=new ArrayList();
		List<WebAutomation> webCourse=gc.getCourses().getWebAutomation();
		
		for(int i=0;i<webCourse.size();i++) {
			a.add(webCourse.get(i).getCourseTitle());
		}
		
		List<String> expectedList = Arrays.asList(courseTitles);
		
		Assert.assertTrue(a.equals(expectedList));
		
	}

}
