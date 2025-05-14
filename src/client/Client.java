package client;

import client.request.Request;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    private static final int PORT = 4000;
    private static final String ADDRESS = "127.0.0.1";
    private static final Logger LOGGER = Logger.getLogger(Client.class.getName());

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

            String msg = in.readUTF(); //receive msg back from server
            System.out.println("Received: " + msg);

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,"Client connection error" + e.getMessage());
        }

    }
}
