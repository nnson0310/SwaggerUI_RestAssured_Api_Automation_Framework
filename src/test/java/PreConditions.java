import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;
import responses.User;

public class PreConditions {

    public static String CreateNewAccount(String username, String password) {
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

        return user.userID;
    }
}
