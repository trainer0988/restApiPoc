package restfullbookerTest;

import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class ValidateJson {
	
public static void main(String[] args) {
	JsonPath response = new JsonPath(Data.demoResponse());
	String Total_Amount = response.getString("dashboard.Total_Amount");
	
	System.out.println(Total_Amount);
	
	
String iphone_price = response.getString("items[0].Price");
	
	System.out.println(iphone_price);
	
	
int totalItem = response.getInt("items.size()");
	
	System.out.println(totalItem);
	
	for(int i = 0;i<totalItem;i++)
	{
		String price = response.getString("items["+i+ "].Price");
		
		System.out.println(price);
		
		
	}
}




	
}
