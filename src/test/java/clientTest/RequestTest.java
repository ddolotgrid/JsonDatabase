package clientTest;

import client.request.Request;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class RequestTest {
    private Request request;
    private static Path tempFile;

    @BeforeAll
    static void setUpAll() throws IOException {
        tempFile = Files.createTempFile("test_", ".txt");
        String jsonContent = "{\"type\":\"delete\",\"key\":\"username\"}";
        Files.writeString(tempFile, jsonContent);
    }

    @BeforeEach
    void setUp(){
        request = new Request();
    }
    @Test
    void shouldReturnJsonFromFieldsIfFileNameIsNull() {
        // given
        request.type = "get";
        request.key = "name";

        // when
        String json = request.getRequest();

        // then
        assertTrue(json.contains("\"type\":\"get\""));
        assertTrue(json.contains("\"key\":\"name\""));
    }
    @Test
    void shouldReturnJsonFromFile() throws IOException {
        // given
        String testPath = tempFile.getParent().toString() + "/";
        request.setFilePath(testPath);
        request.fileName = tempFile.getFileName().toString();

        // when
        String result = request.getRequest();

        // then
        assertTrue(result.contains("{\"type\":\"delete\",\"key\":\"username\"}"));
    }
    @Test
    void shouldHandleInvalidFile() throws IOException {
        // given
        request.setFilePath("invalidPath");
        request.fileName = tempFile.getFileName().toString();

        // when
        String result = request.getRequest();

        // then
        assertEquals(result,"{}");
    }




}