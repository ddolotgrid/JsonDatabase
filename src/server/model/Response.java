package server.model;


import com.google.gson.JsonElement;
import com.google.gson.annotations.Expose;


public class Response {

    public static final Response EMPTY = builder()
            .setResponse("OK")
            .setReason("No such operation")
            .build();
    public static final Response OK_VALUE = builder()
            .setResponse("OK")
            .setValue(builder().value)
            .build();
    public static final Response OK = builder()
            .setResponse("OK")
            .build();
    public static final Response ERROR = builder()
            .setResponse("ERROR")
            .setReason("No such key")
            .build();

    @Expose
    private final String response;
    @Expose
    private final String reason;
    @Expose
    private final JsonElement value;

    public Response(String response, String reason, JsonElement value) {
        this.response = response;
        this.reason = reason;
        this.value = value;
    }

    public String getResponse() {
        return response;
    }

    public String getReason() {
        return reason;
    }

    public JsonElement getValue() {
        return value;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private String response;
        private String reason;
        private JsonElement value;

        public Builder setResponse(String response) {
            this.response = response;
            return this;
        }

        public Builder setReason(String reason) {
            this.reason = reason;
            return this;
        }

        public Builder setValue(JsonElement value) {
            this.value = value;
            return this;
        }

        public Response build() {
            return new Response(this.response, this.reason, this.value);
        }
    }
}
