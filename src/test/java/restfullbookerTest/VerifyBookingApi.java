package restfullbookerTest;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.reporters.jq.Main;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class VerifyBookingApi {

	@Test
	public void verifyBookingRestFullBooker() {

		// booking

		RestAssured.baseURI = "https://restful-booker.herokuapp.com/";

		String response = given()
				.header("Content-Type", "application/json")
				.body("{\r\n" + "    \"firstname\" : \"Ashutosh\",\r\n" + "    \"lastname\" : \"Brown\",\r\n"
						+ "    \"totalprice\" : 111,\r\n" + "    \"depositpaid\" : true,\r\n"
						+ "    \"bookingdates\" : {\r\n" + "        \"checkin\" : \"2018-01-01\",\r\n"
						+ "        \"checkout\" : \"2019-01-01\"\r\n" + "    },\r\n"
						+ "    \"additionalneeds\" : \"Breakfast\"\r\n" + "}")
				.when().post("booking")
				.then().extract().response().asString();

		JsonPath jsp = new JsonPath(response);
		String bookingid = jsp.getString("bookingid");
		System.out.println(jsp.getString("bookingid"));
		

		String getResponse = given().header("Content-Type", "application/json")
				.when()
				.get("booking/" + bookingid)
				.then().extract().response().asString();

		JsonPath jsp2 = new JsonPath(getResponse);
		String lname = jsp2.getString("lastname");
		Assert.assertEquals(lname, "Brown");

		String key123 = given().header("Content-Type", "application/json")
				.body("{\r\n" + "    \"username\" : \"admin\",\r\n" + "    \"password\" : \"password123\"\r\n" + "}")
				.when().post("auth").then().extract().response().asPrettyString();
		
		JsonPath jsp3 = new JsonPath(key123);
		
		String token = jsp3.getString("token");

	
	
	
	//token
	
	String response3 = given().log().all().header("Content-Type", "application/json").header("Accept", "application/json").header("Cookie","token="+token )
	.body("{\r\n"
			+ "    \"firstname\" : \"James\",\r\n"
			+ "    \"lastname\" : \"Red\",\r\n"
			+ "    \"totalprice\" : 111,\r\n"
			+ "    \"depositpaid\" : true,\r\n"
			+ "    \"bookingdates\" : {\r\n"
			+ "        \"checkin\" : \"2018-01-01\",\r\n"
			+ "        \"checkout\" : \"2019-01-01\"\r\n"
			+ "    },\r\n"
			+ "    \"additionalneeds\" : \"Breakfast\"\r\n"
			+ "}")
	.when().put("booking/"+bookingid).then().log().all().extract().response().asString();
	
	JsonPath update = new JsonPath(response3);
	String updatedlname = update.getString("lastname");
	Assert.assertEquals(updatedlname, "Red");

	
	
		



	}

}
