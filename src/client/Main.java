package client;

import com.beust.jcommander.JCommander;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Main {

    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 4200;

    public static void main(String[] args) throws IOException {

        ArgsParser arguments = new ArgsParser();

        JCommander.newBuilder()
                .addObject(arguments)
                .build()
                .parse(args);


        Socket socket = new Socket(ADDRESS, PORT);
        System.out.println("Client started!");
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out  = new DataOutputStream(socket.getOutputStream());
        String msg;
        if(arguments.type.equals("set")){
            msg = arguments.type + " " + arguments.index + " " +arguments.msg;

        }
        else {
            msg = arguments.type + " " + arguments.index;
        }

        System.out.println(String.join(" ","Sent: " ,arguments.type,arguments.index));



        out.writeUTF(msg); // send request to server

        msg = in.readUTF();

        System.out.println("Received: " + msg);


    }
}



