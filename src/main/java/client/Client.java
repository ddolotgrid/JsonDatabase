package client;

import client.request.Request;
import com.google.gson.JsonSyntaxException;
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
    }

    public void start() {
        try (
                Socket socket = new Socket(ADDRESS, PORT);
                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        ) {
            System.out.println("Client started");
            String jsonRequest = request.getRequest();
            System.out.println("Sent: " + jsonRequest);
            out.writeUTF(jsonRequest); // send request to server

            String msg = in.readUTF(); //receive msg back from server
            System.out.println("Received: " + msg);

        } catch (JsonSyntaxException jse) {
            LOGGER.log(Level.SEVERE, "Invalid JSON in input file: " + jse.getMessage());
        } catch (IllegalArgumentException iae) {
            LOGGER.log(Level.SEVERE, iae.getMessage());
        } catch (IOException ioe) {
            LOGGER.log(Level.SEVERE, "Client connection error: " + ioe.getMessage());
        }
    }
}
