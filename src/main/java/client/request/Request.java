package client.request;
import com.beust.jcommander.Parameter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;
import java.io.FileReader;
import java.io.IOException;

@Getter
@Setter
public class Request {

    private String filePath = System.getProperty("user.dir") + "/src/main/java/client/data/";
    private static final Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();

    @Expose
    @Parameter(names = {"-t", "--type"}, description = "Type of the request")
    private String type;

    @Expose
    @Parameter(names = {"-k", "--key"}, description = "Record key")
    private String key;

    @Expose
    @Parameter(names = {"-v", "--value"}, description = "Value to add")
    private String value;

    @Parameter(names = {"-in", "--input-file"}, description = "File containing the request")
    private String fileName;

    public String getRequest() throws IOException,IllegalArgumentException{
        if(fileName != null){
            try (FileReader reader = new FileReader(filePath+fileName)) {
                JsonObject json = gson.fromJson(reader, JsonObject.class);
                if(json == null || json.entrySet().isEmpty()) throw new IllegalArgumentException();
                return gson.toJson(json);
            }
        }
        return gson.toJson(this);
    }
}
