package demo;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojo.AddPlace;
import pojo.Location;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;


public class serializeTest {

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
		
				Response res = given().log().all().queryParam("key", "qaclick123")
				.body(p)
				.when( ).post("/maps/api/place/add/json")
				.then().assertThat().statusCode(200).extract().response();
	
				String responseString = res.asString();
				System.out.println(responseString );
		
		
	}
}
