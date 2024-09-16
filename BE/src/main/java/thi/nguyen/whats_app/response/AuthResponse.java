package thi.nguyen.whats_app.response;

public class AuthResponse {

    private String jwt;
    private Boolean isAuth;

    public AuthResponse() {
    }

    public AuthResponse(String jwt, Boolean isAuth) {
        this.jwt = jwt;
        this.isAuth = isAuth;
    }
}
