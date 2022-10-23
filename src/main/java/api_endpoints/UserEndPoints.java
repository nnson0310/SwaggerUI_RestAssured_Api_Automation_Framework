package api_endpoints;

import api_models.requests.Authorization;
import commons.GlobalConstants;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UserEndPoints {

    public static Response createNewUser(Authorization authorization) {
        RestAssured.baseURI = GlobalConstants.baseUrl;
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(authorization);

        return request.post("/Account/v1/User");
    }
}
