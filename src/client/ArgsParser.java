package client;

import com.beust.jcommander.Parameter;

public class ArgsParser {

    @Parameter(names = {"-t"},description = "type of the request")
    protected String type;

    @Parameter(names = {"-i"},description = "index of the cell")
    String index;

    @Parameter(names = {"-m"},description = "message to save in the database")
    String msg;
}
