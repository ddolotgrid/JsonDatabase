package client;
import com.beust.jcommander.JCommander;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class Main {

    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 4200;

    public static void main(String[] args) throws IOException {
        Gson json = new Gson();

        ArgsParser arguments = new ArgsParser();
        JCommander.newBuilder()
                .addObject(arguments)
                .build()
                .parse(args);


        if(arguments.in != null){
            FileHandler fileHandler = new FileHandler();
            String requestFromFile = fileHandler.getRequest();

        }

        Socket socket = new Socket(ADDRESS, PORT);
        System.out.println("Client started!");
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out  = new DataOutputStream(socket.getOutputStream());



        String msg = json.toJson(arguments);
        System.out.println("Sent: " + msg);
        out.writeUTF(msg); // send request to server

        msg = in.readUTF(); //recive msg back from server
        System.out.println("Received: " + msg);


    }
}



