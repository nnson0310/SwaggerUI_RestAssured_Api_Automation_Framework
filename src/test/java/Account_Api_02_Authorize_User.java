import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import responses.Common;
import responses.Token;
import utils.DataFaker;

public class Account_Api_02_Authorize_User {

    private String username;
    private String password;

    @BeforeClass
    public void SetUp() {
        username = DataFaker.getInstance().getFullName();
        password = "Tomanyeuem@123";

        PreConditions.CreateNewAccount(username, password);
    }

    @Test
    public void TC_01_Authorize_User_Not_Have_Token() {
        RestAssured.baseURI = "https://bookstore.toolsqa.com";
        RequestSpecification request = RestAssured.given();

        JSONObject requestParams = new JSONObject();
        requestParams.put("userName", username);
        requestParams.put("password", password);

        request.header("Content-Type", "application/json");
        request.body(requestParams.toJSONString());
        Response response = request.post("/Account/v1/Authorized");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.getBody().asString(), "false");
    }

    @Test
    public void TC_02_Generate_Token_For_Valid_Credentials() {
        RestAssured.baseURI = "https://bookstore.toolsqa.com";
        RequestSpecification request = RestAssured.given();

        JSONObject requestParams = new JSONObject();
        requestParams.put("userName", username);
        requestParams.put("password", password);

        request.header("Content-Type", "application/json");
        request.body(requestParams.toJSONString());
        Response response = request.post("/Account/v1/GenerateToken");
        Token tokenResponse = new Gson().fromJson(response.getBody().asString(), Token.class);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertNotNull(tokenResponse.token);
        Assert.assertNotNull(tokenResponse.expires);
        Assert.assertEquals(tokenResponse.status, "Success");
        Assert.assertEquals(tokenResponse.result, "User authorized successfully.");
    }

    @Test
    public void TC_03_Fail_To_Generate_Token_For_Invalid_Credentials() {
        RestAssured.baseURI = "https://bookstore.toolsqa.com";
        RequestSpecification request = RestAssured.given();

        JSONObject requestParams = new JSONObject();
        requestParams.put("userName", "thuha");
        requestParams.put("password", "tomanyeuem123");

        request.header("Content-Type", "application/json");
        request.body(requestParams.toJSONString());
        Response response = request.post("/Account/v1/GenerateToken");
        Token tokenResponse = new Gson().fromJson(response.getBody().asString(), Token.class);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertNull(tokenResponse.token);
        Assert.assertNull(tokenResponse.expires);
        Assert.assertEquals(tokenResponse.status, "Failed");
        Assert.assertEquals(tokenResponse.result, "User authorization failed.");
    }

    @Test
    public void TC_04_Authorize_User() {
        RestAssured.baseURI = "https://bookstore.toolsqa.com";
        RequestSpecification request = RestAssured.given();

        JSONObject requestParams = new JSONObject();
        requestParams.put("userName", username);
        requestParams.put("password", password);

        request.header("Content-Type", "application/json");
        request.body(requestParams.toJSONString());
        Response response = request.post("/Account/v1/Authorized");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.getBody().asString(), "true");
    }

    @Test
    public void TC_05_Authorize_User_Not_Exist() {
        RestAssured.baseURI = "https://bookstore.toolsqa.com";
        RequestSpecification request = RestAssured.given();

        JSONObject requestParams = new JSONObject();
        requestParams.put("userName", username);
        requestParams.put("password", "tomanyeuem");

        request.header("Content-Type", "application/json");
        request.body(requestParams.toJSONString());
        Response response = request.post("/Account/v1/Authorized");

        Common commonResponse = new Gson().fromJson(response.getBody().asString(), Common.class);

        Assert.assertEquals(response.getStatusCode(), 404);
        Assert.assertEquals(commonResponse.message, "User not found!");
    }
}
