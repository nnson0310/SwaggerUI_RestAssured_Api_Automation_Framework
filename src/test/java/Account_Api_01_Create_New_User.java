import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import api_models.responses.Common;
import api_models.responses.User;
import utils.DataFaker;

public class Account_Api_01_Create_New_User {

    private String username;
    private String password;

    @BeforeClass
    public void SetUp() {
        username = DataFaker.getInstance().getFullName();
        password = "Tomanyeuem@123";
    }

    @Test
    public void TC_01_Password_Must_Be_Correct_Format() {
        RestAssured.baseURI = "https://bookstore.toolsqa.com";
        RequestSpecification request = RestAssured.given();

        JSONObject requestParams = new JSONObject();
        requestParams.put("userName", "TOOLSQA-Test");
        requestParams.put("password", "123456");

        request.header("Content-Type", "application/json");
        request.body(requestParams.toJSONString());
        Response response = request.post("/Account/v1/User");
//        JsonPath jsonResponse = response.jsonPath();
//        System.out.println(jsonResponse.getString("message"));
        Common commonResponse = new Gson().fromJson(response.getBody().asString(), Common.class);

        Assert.assertEquals(response.getStatusCode(), 400);
        Assert.assertEquals(commonResponse.message, "Passwords must have at least one non alphanumeric character, one digit ('0'-'9'), one uppercase ('A'-'Z'), one lowercase ('a'-'z'), one special character and Password must be eight characters or longer.");
    }

    @Test
    public void TC_02_Create_User_With_Valid_Credentials() {
        RestAssured.baseURI = "https://bookstore.toolsqa.com";
        RequestSpecification request = RestAssured.given();

        JSONObject requestParams = new JSONObject();
        requestParams.put("userName", username);
        requestParams.put("password", password);

        request.header("Content-Type", "application/json");
        request.body(requestParams.toJSONString());
        Response response = request.post("/Account/v1/User");
        User user = new Gson().fromJson(response.getBody().asString(), User.class);

        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertNotNull(user.userID);
        Assert.assertEquals(user.username, username);
        Assert.assertEquals(user.books.length, 0);
    }

    @Test
    public void TC_03_Create_User_With_Exists_Username() {
        RestAssured.baseURI = "https://bookstore.toolsqa.com";
        RequestSpecification request = RestAssured.given();

        JSONObject requestParams = new JSONObject();
        requestParams.put("userName", username);
        requestParams.put("password", password);

        request.header("Content-Type", "application/json");
        request.body(requestParams.toJSONString());
        Response response = request.post("/Account/v1/User");
        Common commonResponse = new Gson().fromJson(response.getBody().asString(), Common.class);

        Assert.assertEquals(response.getStatusCode(), 406);
        Assert.assertEquals(commonResponse.message, "User exists!");
    }
}
