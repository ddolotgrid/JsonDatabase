package client.request;

import com.beust.jcommander.JCommander;

public class RequestParser {
    public static Request parseArgs(String[] args){
        Request request = new Request();
        JCommander.newBuilder()
                .addObject(request)
                .build()
                .parse(args);
        return request;
    }
}
