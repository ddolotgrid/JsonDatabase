package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Server {
//    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 4200;

    public Server() {
        try (ServerSocket server = new ServerSocket(PORT)) {

            while (true) {
                System.out.println("Server started!");
                try (Socket socket = server.accept();
                        DataInputStream input = new DataInputStream(socket.getInputStream());
                        DataOutputStream output = new DataOutputStream(socket.getOutputStream())
                ) {

                    String msg = input.readUTF();
                    System.out.println("Received: " + msg);
                    msg = "A record # 12 was sent!";
                    output.writeUTF(msg);
                    System.out.println("Sent: " + msg);

                }
            }
        } catch (IOException ieo) {

            System.out.println(ieo.getMessage());
        }


    }
}
