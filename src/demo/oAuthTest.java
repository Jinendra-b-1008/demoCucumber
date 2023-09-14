package demo;
import static io.restassured.RestAssured.given;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourse;
import pojo.WebAutomation;


public class oAuthTest {

	public static void main(String[] args) throws MalformedURLException {
		
		//Deserialization
		
// code link - https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php&state=verifyjds

		//WebDriver driver = new ChromeDriver();
//		ChromeOptions chromeOptions = new ChromeOptions();
//		chromeOptions.addArguments("---remote-allow----");
//		//WebDriverManager.chromedriver().setup();
//		
//		WebDriver driver = new ChromeDriver(chromeOptions);
//		driver.get("https://www.google.com/");
		
//		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Jinendra\\Downloads\\chromedriver-win64 (1)\\chromedriver-win64\\chromedriver.exe");
//		ChromeOptions options = new ChromeOptions();
//		options.setBinary("C:\\Users\\Jinendra\\Downloads\\chrome-win64\\chrome-win64\\chrome.exe");
//		WebDriver driver = new ChromeDriver(options);
//		driver.manage().window().maximize();
//		
//		driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
//		
//		
//		driver.findElement(By.cssSelector("input[type=email]")).sendKeys("srinath19830");
//		driver.findElement(By.cssSelector("input[type=email]")).sendKeys(Keys.ENTER);
//		driver.findElement(By.cssSelector("input[type=password]")).sendKeys("Jinendra@1008");
//		driver.findElement(By.cssSelector("input[type=password]")).sendKeys(Keys.ENTER);
		//String url = driver.getCurrentUrl();
		
		
		String url = "https://rahulshettyacademy.com/getCourse.php?state=verifyjds&code=4%2F0Adeu5BXFNde3uBdVN09S2Z_y8mmaVeAuQTCAvbCQT4dxY_-7SSMYJY7Sel5HkBMtfwe4bg&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none";
		String partialCode = url.split("code=")[1];
		String code = partialCode.split("&scope")[0];
		System.out.println(code);
		
		String accessTokenResponse = given().urlEncodingEnabled(false)
		.queryParam("code", code)
		.queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
		.queryParam("grant_type", "authorization_code")
		.when().log().all()
		.post("https://www.googleapis.com/oauth2/v4/token").asString();
		
		JsonPath js = new JsonPath(accessTokenResponse);
		String accessToken =  js.getString("access_token");
		
		
		GetCourse gc = given().queryParam("access_token", accessToken)
				.expect().defaultParser(Parser.JSON)
				.when()
				.get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);
				
				System.out.println(gc.getInstructor());
				System.out.println(gc.getLinkedIn());
				System.out.println(gc.getExpertise());
				System.out.println(gc.getServices());
				
				System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
				
				List<Api> apiCourses = gc.getCourses().getApi();
				for(int i=0 ;  i<apiCourses.size(); i++)
				{
					if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
					{
						System.out.println(apiCourses.get(i).getPrice());
					}
				}
				

				String[] courseTitles = {"Selenium Webdriver Java", "Cypress", "Protractor"};    // actual array
				ArrayList <String> a = new ArrayList<String>();
				
				List<WebAutomation> w = gc.getCourses().getWebAutomation();	
				for(int i=0; i<w.size();i++)
				{
					a.add(w.get(i).getCourseTitle());						
				}
				
				List<String> expectedLists = Arrays.asList(courseTitles);
				Assert.assertTrue(a.equals(expectedLists));
			
				
//		.when().log().all()
//		.get("https://rahulshettyacademy.com/getCourse.php").asString();
//		System.out.println(response);

//		.expect().defaultParser(Parser.JSON)
//		.when()
//		.get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);
//		
//		System.out.println(gc.getInstructor());
//		System.out.println(gc.getLinkedIn());
				
	}

}
