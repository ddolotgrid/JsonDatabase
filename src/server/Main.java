package server;

import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    private static final int PORT = 4200;
    public static void main(String[] args) throws IOException {
        Database db = new Database();
        CommandHandler handler = new CommandHandler(db);
        Gson json = new Gson();
//        handler.execute("xadasdasd");

        boolean keepListening = true;
        System.out.println("Server started!");
        try (ServerSocket server = new ServerSocket(PORT)) {

            while (keepListening) {

                try (Socket socket = server.accept();
                     DataInputStream input = new DataInputStream(socket.getInputStream());
                     DataOutputStream output = new DataOutputStream(socket.getOutputStream())
                ) {

                    String msg = input.readUTF(); // request from client
                    System.out.println("Received: " + msg);

                    JsonModel jsonModel = json.fromJson(msg, JsonModel.class);

//                    String strToExecute = String.join(" ",jsonModel.getType(),jsonModel.getKey(),jsonModel.getValue());
//                    System.out.println(strToExecute);

                    String serverResponse = handler.executeRequest(jsonModel);
                    output.writeUTF(serverResponse);
                    if("exit".equals(serverResponse)){
                        keepListening = false;
                    }

                    System.out.println("Sent: " + serverResponse);

                }
            }
        } catch (IOException ieo) {

            System.out.println(ieo.getMessage());
        }


    }








//        ServerSocket server = new ServerSocket(PORT);
//        System.out.println("Server started!");
//        Socket socket = server.accept();
//        DataInputStream in = new DataInputStream(socket.getInputStream());
//        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
//        String msg = in.readUTF();
//        System.out.println("Received: " + msg);
//        msg = "A record # 12 was sent!";
//        out.writeUTF(msg);
//        System.out.println("Sent: " + msg);


    }








//        System.out.println("Server started. Type commands or 'exit' to quit.");
//
//        while (true) {
//            String query = scanner.nextLine().trim();
//
//            if (query.equalsIgnoreCase("exit")) break;
//
//            handler.execute(query);
//        }


