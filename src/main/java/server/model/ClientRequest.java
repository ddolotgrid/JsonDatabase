package server.model;

import com.google.gson.JsonElement;
import lombok.Getter;

@Getter
public class ClientRequest {
    private String type;
    private JsonElement key;
    private JsonElement value;

}
