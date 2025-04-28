package client;

import com.beust.jcommander.Parameter;

public class ArgsParser {

    @Parameter(names = {"-t"},description = "type of the request")
    protected String type;

    @Parameter(names = {"-k"},description = "index of the cell")
    String key;

    @Parameter(names = {"-v"},description = "message to save in the database")
    String value;

    @Parameter(names = {"-in"},description = "tells the path to file")
    String in;
}
