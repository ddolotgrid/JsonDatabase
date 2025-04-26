package server;

public class Response {

    private  String response;
    private String reason;
    private String value;


    public String getResponse() {
        return response;
    }

    public Response setResponse(String response) {
        this.response = response;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public Response setReason(String reason) {
        this.reason = reason;
        return this;
    }

    public String getValue() {
        return value;
    }

    public Response setValue(String value) {
        this.value = value;
        return this;
    }
}
