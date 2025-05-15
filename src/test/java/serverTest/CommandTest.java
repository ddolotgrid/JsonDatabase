package serverTest;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import org.junit.jupiter.api.Test;
import server.command.Command;
import server.model.Response;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommandTest {

    private final Command command = new Command() {
        @Override
        public Response execute() {
            return null;
        }
    };

    @Test
    void shouldParseKeyIfPrimitive() {
        JsonElement key = new JsonPrimitive("person");
        List<String> result = command.parseKey(key);
        assertEquals(List.of("person"), result);
    }

    @Test
    void shouldParseKeyIfJsonArray() {
        JsonArray keyArray = new JsonArray();
        keyArray.add("person");
        keyArray.add("name");
        List<String> result = command.parseKey(keyArray);
        assertEquals(List.of("person", "name"), result);
    }
}
