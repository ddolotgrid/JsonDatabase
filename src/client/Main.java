package client;

import com.beust.jcommander.JCommander;

public class Main {

    public static void main(String[] args) {
        Request request = new Request();
        JCommander.newBuilder().addObject(request).build().parse(args);

        Client client = new Client(request);
        client.start();
    }
}
