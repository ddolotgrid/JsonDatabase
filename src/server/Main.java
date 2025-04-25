package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    private static final int PORT = 4200;
    public static void main(String[] args) throws IOException {
//        Database db = new Database();
//        CommandHandler handler = new CommandHandler(db);
//        Scanner scanner = new Scanner(System.in);
//

        ServerSocket server = new ServerSocket(PORT);
        System.out.println("Server started!");
        Socket socket = server.accept();
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        String msg = in.readUTF();
        System.out.println("Received: " + msg);
        msg = "A record # 12 was sent!";
        out.writeUTF(msg);
        System.out.println("Sent: " + msg);


    }






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


