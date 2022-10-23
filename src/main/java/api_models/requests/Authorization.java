package api_models.requests;

public class Authorization {
    public String userName;

    public String password;

    public Authorization(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
