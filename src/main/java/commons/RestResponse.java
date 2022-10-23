package commons;

import io.restassured.response.Response;

import java.lang.reflect.Type;

public class RestResponse<T> implements IRestResponse {

    private T responseType;
    private Response response;
    private Exception e;

    public RestResponse(Class<T> responseType, Response response) {
        this.response = response;
        try {
            this.responseType = responseType.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("The Response Java POJO must have a default constructor");
        }
    }

    @Override
    public T getBody() {
        try {
            responseType = response.getBody().as((Type) responseType.getClass());
        } catch (Exception e) {
            this.e = e;
        }
        return responseType;
    }

    @Override
    public String getContent() {
        return response.getBody().asString();
    }

    @Override
    public int getStatusCode() {
        return response.getStatusCode();
    }

    @Override
    public boolean isSuccessful() {
        int statusCode = getStatusCode();
        if (statusCode >= 200 && statusCode <= 206) {
            return true;
        }
        return false;
    }

    @Override
    public String getStatusDescription() {
        return response.getStatusLine();
    }

    @Override
    public Response getResponse() {
        return response;
    }

    @Override
    public Exception getException() {
        return e;
    }
}
