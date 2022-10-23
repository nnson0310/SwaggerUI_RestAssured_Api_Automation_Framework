package api_endpoints;

import api_models.requests.Authorization;
import api_routes.UserRoutes;
import commons.GlobalConstants;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UserEndPoints {

    private RequestSpecification request;

    public UserEndPoints() {
        RestAssured.baseURI = GlobalConstants.baseUrl;
        request = RestAssured.given();
        request.header("Content-Type", "application/json");
    }

    public Response createNewUser(Authorization authorization) {
        request.body(authorization);
        return request.post(UserRoutes.createUserRoute());
    }
}
