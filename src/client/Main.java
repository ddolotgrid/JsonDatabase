package client;

import client.request.Request;
import client.request.RequestParser;

public class Main {
    public static void main(String[] args) {
        Request request = RequestParser.parseArgs(args);
        Client client = new Client(request);
        client.start();
    }
}
