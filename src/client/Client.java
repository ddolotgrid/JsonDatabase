package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 4200;

    public Client(){
        System.out.println("Client started!");
        try(
                Socket socket = new Socket(ADDRESS,PORT);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                ) {

            String msg = "Give me a record # 12";
            System.out.println("Sent: " + msg);
            output.writeUTF(msg);
            msg = input.readUTF();
            System.out.println("Received: " + msg);



        }
        catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }

    }
}
