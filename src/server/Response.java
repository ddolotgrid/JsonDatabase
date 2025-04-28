package server;

public class Response {
    private String response;
    private String value;
    private String reason;

    public Response setResponse(String response) {
        this.response = response;
        return this;
    }

    public Response setValue(String value) {
        this.value = value;
        return this;
    }

    public Response setReason(String reason) {
        this.reason = reason;
        return this;
    }

    public static Response ok() {
        return new Response().setResponse("OK");
    }

    public static Response okWithValue(String value) {
        return new Response().setResponse("OK").setValue(value);
    }

    public static Response error(String reason) {
        return new Response().setResponse("ERROR").setReason(reason);
    }
}
