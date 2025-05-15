package server.model;

import com.google.gson.JsonElement;


public record Response(String response,String reason,JsonElement value) {

    public static final Response EMPTY = builder()
            .response("ERROR")
            .reason("No such operation")
            .build();
    public static final Response OK = builder()
            .response("OK")
            .build();
    public static final Response ERROR = builder()
            .response("ERROR")
            .reason("No such key")
            .build();

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private String response;
        private String reason;
        private JsonElement value;

        public Builder response(String response) {
            this.response = response;
            return this;
        }

        public Builder reason(String reason) {
            this.reason = reason;
            return this;
        }

        public Builder value(JsonElement value) {
            this.value = value;
            return this;
        }

        public Response build() {
            return new Response(this.response, this.reason, this.value);
        }
    }
}