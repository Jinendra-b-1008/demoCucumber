package demo;

import io.restassured.RestAssured;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;


public class SpecBuilderTest {

	public static void main(String[] args)
	{
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
		AddPlace p = new AddPlace();
		p.setAccuracy(50);
		p.setAddress("29 side layout, cohen");
		p.setLanguage("French-In");
		p.setPhone_number("9620208444");
		p.setWebsite("https://rahulshettyacademy.com");
		p.setName("Jinendra B Y");
		List<String> mylist = new ArrayList<String>();
		mylist.add("shoe park");
		mylist.add("shop");
		
		Location l = new Location();
		l.setLat(-38.333);
		l.setLng(33.443);
		
		p.setLocation(l);   // we are injecting the location values(small Json) through the (main Json)creating the object of location class
		
		
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123")
		.setContentType(ContentType.JSON).build();
		
		
		ResponseSpecification resspec= new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
				RequestSpecification res = given().spec(req)
				.body(p);
				
				Response response = res.when().post("/maps/api/place/add/json")
				.then().spec(resspec).extract().response();
	
				String responseString = response.asString();
				System.out.println(responseString );
		
		
	}
}
