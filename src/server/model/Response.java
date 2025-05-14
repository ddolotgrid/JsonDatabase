package server.model;

import com.google.gson.JsonElement;
import com.google.gson.annotations.Expose;

public record Response(@Expose String response, @Expose String reason, @Expose JsonElement value) {

    public static final Response EMPTY = builder()
            .setResponse("ERROR")
            .setReason("No such operation")
            .build();
    public static final Response OK = builder()
            .setResponse("OK")
            .build();
    public static final Response ERROR = builder()
            .setResponse("ERROR")
            .setReason("No such key")
            .build();

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