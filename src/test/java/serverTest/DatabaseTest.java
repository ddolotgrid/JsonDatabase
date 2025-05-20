package serverTest;

import com.google.gson.JsonObject;
import server.database.FileHandler;

import static org.mockito.BDDMockito.given;

public interface DatabaseTest {
    default JsonObject mockJson(){
        JsonObject person = new JsonObject();

        person.addProperty("name", "Elon Musk");

        JsonObject car = new JsonObject();
        car.addProperty("model", "Tesla Roadster");
        car.addProperty("year", "2018");
        person.add("car", car);

        return person;
    }

    default void commonSetup(FileHandler fileHandler) {
        given(fileHandler.read()).willReturn(mockJson());
    }
}
