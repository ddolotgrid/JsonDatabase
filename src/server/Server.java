package server;

import com.google.gson.Gson;
import server.database.Database;
import server.model.ClientRequest;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int PORT = 4000;
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final Gson gson = new Gson();
    private final Database database;
    private final CommandHandler commandHandler;
    private ServerSocket server;

    public Server(Database database) {
        this.database = database;
        this.commandHandler = new CommandHandler(database, this);
    }

    public void start() {
        try {
            server = new ServerSocket(PORT);
            while (!server.isClosed()) {
                Socket socket = server.accept();

                executorService.submit(() -> {
                    try (
                            DataInputStream input = new DataInputStream(socket.getInputStream());
                            DataOutputStream output = new DataOutputStream(socket.getOutputStream())
                    ) {
                        String msg = input.readUTF();
                        System.out.println("Received: " + msg);
                        ClientRequest clientRequest = gson.fromJson(msg, ClientRequest.class);

                        System.out.println(clientRequest.getKey());

                        String serverResponse = commandHandler.executeRequest(clientRequest);

                        output.writeUTF(serverResponse);
                        System.out.println("Sent: " + serverResponse);
                    } catch (IOException e) {
                        System.err.println("Client connection error: " + e.getMessage());
                    }
                });
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }

    public void shutdown() {
        executorService.shutdown();
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
