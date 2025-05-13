package client;
import com.beust.jcommander.Parameter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.JsonObject;

import java.io.FileReader;
import java.io.IOException;

public class Request {

    private static final String filePath = System.getProperty("user.dir") + "/src/client/data/";

    @Expose
    @Parameter(names = {"-t", "--type"}, description = "Type of the request")
    public String type;

    @Expose
    @Parameter(names = {"-k", "--key"}, description = "Record key")
    public String key;

    @Expose
    @Parameter(names = {"-v", "--value"}, description = "Value to add")
    public String value;

    @Parameter(names = {"-in", "--input-file"}, description = "File containing the request")
    public String fileName;

    public String getRequest() {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        if (fileName != null) {
            try (FileReader reader = new FileReader(filePath+fileName)) {
                JsonObject json = gson.fromJson(reader, JsonObject.class);
                return gson.toJson(json);
            } catch (IOException e) {
                System.out.println("Error reading file: " + e.getMessage());
                return "{}";
            }
        }
        return gson.toJson(this);
    }
}
