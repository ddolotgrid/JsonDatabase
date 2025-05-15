package serverTest;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import server.database.Database;
import server.database.JsonDatabase;
import server.database.FileHandler;
import server.model.Response;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JsonDatabaseTest implements DatabaseTest {

    @Mock
    private FileHandler fileHandler;

    private Database database;

    @BeforeEach
    public void setUp(){
        commonSetup(fileHandler);
        database = new JsonDatabase(fileHandler);
    }

    @Test
    void shouldFindPrimitive(){
        // given
        List<String> path = List.of("name");
        // when
        Response response = database.get(path);
        // then
        verify(fileHandler,times(1)).read();
        assertEquals("Elon Musk",response.value().getAsString());
        assertNull(response.reason());
        assertEquals("OK",response.response());
    }
    @Test
    void shouldFailToFindValue(){
        // given
        List<String> path = List.of("model");
        // when
        Response response = database.get(path);
        // then
        verify(fileHandler,times(1)).read();
        assertNull(response.value());
        assertEquals("No such key",response.reason());
        assertEquals("ERROR",response.response());
    }
    @Test
    void shouldFindNestedValueWithProperPath(){
        // given
        List<String> path = List.of("car","model");
        // when
        Response response = database.get(path);
        // then
        verify(fileHandler,times(1)).read();
        assertEquals("Tesla Roadster",response.value().getAsString());
        assertNull(response.reason());
        assertEquals("OK",response.response());
    }
    @Test
    void shouldUpdateNestedValueIfItIsPresent(){
        // given
        List<String> path = List.of("car","model");
        JsonPrimitive value = new JsonPrimitive("Audi a3");

        // when
        Response response = database.set(path,value);

        //then
        ArgumentCaptor<JsonObject> captor = ArgumentCaptor.forClass(JsonObject.class);
        verify(fileHandler).write(captor.capture());

        JsonObject savedJson = captor.getValue();
        JsonObject updatedCar = savedJson.getAsJsonObject("car");
        String actualCarModel = updatedCar.get("model").getAsString();

        assertNotNull(savedJson);
        assertEquals(value.getAsString(), actualCarModel);

        assertEquals("OK", response.response());
        assertNull(response.reason());
    }

    @Test
    void shouldCreateSimpleRecordIfItIsNotPresent(){
        // given
        List<String> path = List.of("company");
        JsonPrimitive value = new JsonPrimitive("SpaceX");

        // when
        Response response = database.set(path,value);

        //then
        ArgumentCaptor<JsonObject> captor = ArgumentCaptor.forClass(JsonObject.class);
        verify(fileHandler,times(1)).write(captor.capture());

        JsonObject savedJson = captor.getValue();

        assertTrue(savedJson.has(path.get(0)));
        assertEquals(value.getAsString(),savedJson.get(path.get(0)).getAsString());

        assertEquals("OK", response.response());
        assertNull(response.reason());
    }
    @Test
    void shouldCreateJsonRecordIfItIsNotPresent(){
        // given
        List<String> path = List.of("rocket");
        JsonObject value = new JsonObject();
        value.addProperty("name", "Falcon 9");
        value.addProperty("launches", "88");

        // when
        Response response = database.set(path,value);

        //then
        ArgumentCaptor<JsonObject> captor = ArgumentCaptor.forClass(JsonObject.class);
        verify(fileHandler,times(1)).write(captor.capture());

        JsonObject savedJson = captor.getValue();

        assertTrue(savedJson.has(path.get(0)));
        assertTrue(savedJson.get(path.get(0)).isJsonObject());

        assertEquals("OK", response.response());
        assertNull(response.reason());
    }
    @Test
    void shouldCreateNestedValues(){
        List<String> path = List.of("country","city");
        JsonPrimitive value = new JsonPrimitive("Wroclaw");

        // when
        Response response = database.set(path,value);

        //then
        ArgumentCaptor<JsonObject> captor = ArgumentCaptor.forClass(JsonObject.class);
        verify(fileHandler,times(1)).write(captor.capture());

        JsonObject savedJson = captor.getValue();
        savedJson.get(path.get(0));

        System.out.println(savedJson);

        assertTrue(savedJson.has(path.get(0)));
        JsonObject country = savedJson.get(path.get(0)).getAsJsonObject();
        assertTrue(country.has(path.get(1)));
        String city = country.get(path.get(1)).getAsString();
        assertEquals(value.getAsString(),city);

    }

    @Test
    void shouldDeleteSimpleValueIfExist(){
        // given
        List<String> path = List.of("name");
        // when
        Response response = database.delete(path);
        // then
        ArgumentCaptor<JsonObject> captor = ArgumentCaptor.forClass(JsonObject.class);
        verify(fileHandler,times(1)).write(captor.capture());

        JsonObject jsonAfterDeletion = captor.getValue();

        assertFalse(jsonAfterDeletion.has(path.get(0)));
        assertNull(response.reason());
        assertEquals("OK",response.response());
    }
    @Test
    void shouldDeleteNestedValueIfExist(){
        // given
        List<String> path = List.of("car","model");
        // when
        Response response = database.delete(path);
        // then
        ArgumentCaptor<JsonObject> captor = ArgumentCaptor.forClass(JsonObject.class);
        verify(fileHandler,times(1)).write(captor.capture());

        JsonObject jsonAfterDeletion = captor.getValue();

        assertTrue(jsonAfterDeletion.has(path.get(0)));
        JsonObject model = jsonAfterDeletion.get(path.get(0)).getAsJsonObject();
        assertFalse(model.has(path.get(1)));

        assertNull(response.reason());
        assertEquals("OK",response.response());
    }
    @Test
    void shouldFailToDeleteValue(){
        // given
        List<String> path = List.of("nonExistingKey");
        // when
        Response response = database.delete(path);
        // then
        assertNull(response.value());
        assertEquals("No such key",response.reason());
        assertEquals("ERROR",response.response());
    }
}