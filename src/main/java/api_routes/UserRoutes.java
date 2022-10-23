package api_routes;

public class UserRoutes {
    public static final String ACCOUNT_BASE_URI = "/Account/V1";

    public static String createUserRoute() {
        return ACCOUNT_BASE_URI + "/User";
    }
}
