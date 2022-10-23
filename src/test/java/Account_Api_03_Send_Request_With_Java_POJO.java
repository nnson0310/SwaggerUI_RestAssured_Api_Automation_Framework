import api_models.requests.Authorization;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.DataFaker;

public class Account_Api_03_Send_Request_With_Java_POJO {

    private String userID;
    private String username;
    private String password;

    @BeforeClass
    public void SetUp() {
        username = DataFaker.getInstance().getFullName();
        password = "Tomanyeuem@123";

        userID = PreConditions.CreateNewAccount(username, password);
    }

    @Test
    public void TC_01_Authorize_User_Not_Have_Token() {
        RestAssured.baseURI = "https://bookstore.toolsqa.com";
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");

//         Method 1: Send request with json object converted as json string
//        JSONObject requestParams = new JSONObject();
//        requestParams.put("userName", username);
//        requestParams.put("password", password);

//        Method 2: This method is same as above
//        request.body("{ \"userName\":\"" + username + "\", \"password\":\"" + password + "\"}");

// Method 3: Map json request to Java POJO and serialize it internally in body request (Rest Assured do it for us)
        Authorization authorization = new Authorization(username, password);
        request.body(authorization);
        Response response = request.log().all().post("/Account/v1/Authorized");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.getBody().asString(), "false");
    }
}
