package clientTest;

import client.request.Request;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


class RequestTest {

    private Request request;

    private final String testFilePath = System.getProperty("user.dir") + "/src/test/resources/";

    @BeforeEach
    void setUp(){
        request = new Request();
        request.setType("get");
        request.setFilePath(testFilePath);
    }

    @Test
    void shouldHandleCommandLineArgs() throws IOException{
        // given
        request.setKey("person");
        request.setValue("value");

        // when
        String actualJson = request.getRequest();
        String expectedJson = "{\"type\":\"get\",\"key\":\"person\",\"value\":\"value\"}";

        // then
        assertEquals(
                JsonParser.parseString(expectedJson),
                JsonParser.parseString(actualJson)
        );
    }
    @Test
    void shouldHandleJsonFileArgs() throws IOException{
        // given
        request.setFileName("test-nestedValues.json");
        // when
        String json = request.getRequest();

        // then
        assertTrue(json.contains("\"age\":25"));
        assertTrue(json.contains("\"weight\":88"));
    }
    @ParameterizedTest
    @ValueSource(strings = {"test-emptyFile","test-blankFile"})
    void shouldHandleEmptyFiles(String file){
        // given
        request.setFileName(file+".json");

        assertThrows(IllegalArgumentException.class, () -> {
           request.getRequest();
        });
    }

    @Test
    void shouldHandleInvalidFormat(){
        // given
        request.setFileName("test-invalidFormatFile.json");

        assertThrows(JsonSyntaxException.class, () -> {
            request.getRequest();
        });
    }
}