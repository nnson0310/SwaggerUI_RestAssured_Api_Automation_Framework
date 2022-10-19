import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Demo {

    @Test
    public void TC_01_Get_Books() {
        RequestSpecification httpRequest = RestAssured.given().auth().basic("postman", "password");
        Response response = httpRequest.get("https://postman-echo.com/basic-auth");
        JsonPath jsonResponse = new JsonPath(response.asString());
        System.out.println(jsonResponse.getString("authenticated"));
        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
