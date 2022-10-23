import api_endpoints.UserEndPoints;
import api_models.requests.Authorization;
import api_models.responses.Common;
import api_models.responses.User;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.DataFaker;

public class Account_Api_04_Separate_EndPoints_From_Test_Layer {

    private String username;
    private String password;
    private String invalidPassword;

    @BeforeClass
    public void SetUp() {
        username = DataFaker.getInstance().getFullName();
        password = "Tomanyeuem@123";
        invalidPassword = "tomanyeuem";
    }

    @Test
    public void TC_01_Password_Must_Be_Correct_Format() {
        Response response = UserEndPoints.createNewUser(new Authorization(username, invalidPassword));
        Common commonResponse = new Gson().fromJson(response.getBody().asString(), Common.class);

        System.out.println(response.getBody().asString());

        Assert.assertEquals(response.getStatusCode(), 400);
        Assert.assertEquals(commonResponse.message, "Passwords must have at least one non alphanumeric character, one digit ('0'-'9'), one uppercase ('A'-'Z'), one lowercase ('a'-'z'), one special character and Password must be eight characters or longer.");
    }

    @Test
    public void TC_02_Create_User_With_Valid_Credentials() {
        Response response = UserEndPoints.createNewUser(new Authorization(username, password));
        User user = new Gson().fromJson(response.getBody().asString(), User.class);

        System.out.println(response.getBody().asString());

        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertNotNull(user.userID);
        Assert.assertEquals(user.username, username);
        Assert.assertEquals(user.books.length, 0);
    }

    @Test
    public void TC_03_Create_User_With_Exists_Username() {
        Response response = UserEndPoints.createNewUser(new Authorization(username, password));
        Common commonResponse = new Gson().fromJson(response.getBody().asString(), Common.class);

        System.out.println(response.getBody().asString());

        Assert.assertEquals(response.getStatusCode(), 406);
        Assert.assertEquals(commonResponse.message, "User exists!");
    }
}
