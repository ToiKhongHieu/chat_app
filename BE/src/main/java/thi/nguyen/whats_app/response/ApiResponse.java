package thi.nguyen.whats_app.response;

public class ApiResponse {

    private String message;
    private Boolean status;

    public ApiResponse() {
    }

    public ApiResponse(String message, Boolean status) {
        this.message = message;
        this.status = status;
    }
}
