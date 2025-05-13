package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private static final int PORT = 4000;
    private static final String ADDRESS = "127.0.0.1";

    private final Request request;

    public Client(Request request) {
        this.request = request;
        System.out.println("Client started");
    }

    public void start() {
        try (
                Socket socket = new Socket(ADDRESS, PORT);
                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        ) {

            String jsonRequest = request.getRequest();
            System.out.println("Sent: " + jsonRequest);
            out.writeUTF(jsonRequest); // send request to server

            String msg = in.readUTF(); //recive msg back from server
            System.out.println("Received: " + msg);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
