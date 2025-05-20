package server;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.gson.Gson;
import server.command.CommandHandler;
import server.database.JsonDatabase;
import server.model.ClientRequest;
import server.model.Response;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    private static final int PORT = 4000;
    private final ExecutorService executorService;
    private final Gson GSON = new Gson();
    private final CommandHandler commandHandler;
    private ServerSocket server;
    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    public Server(JsonDatabase database) {
        this.executorService = Executors.newCachedThreadPool(
                new ThreadFactoryBuilder().setNameFormat("client-handler-%d").build());
        this.commandHandler = new CommandHandler(database, this);
    }

    public void start() {
        try {
            server = new ServerSocket(PORT);
            System.out.println("Server started!");
            while (!server.isClosed()) {
                Socket socket = server.accept();

                executorService.submit(() -> {
                    LOGGER.info(Thread.currentThread().getName());
                    try (
                            DataInputStream input = new DataInputStream(socket.getInputStream());
                            DataOutputStream output = new DataOutputStream(socket.getOutputStream())
                    ) {
                        String msg = input.readUTF();
                        System.out.println("Received: " + msg);
                        ClientRequest clientRequest = GSON.fromJson(msg, ClientRequest.class);

                        Response serverResponse = commandHandler.executeRequest(clientRequest);
                        String jsonServerResponse = GSON.toJson(serverResponse);
                        output.writeUTF(jsonServerResponse);
                        System.out.println("Sent: " + jsonServerResponse);
                    } catch (IOException e) {
                        LOGGER.log(Level.SEVERE,"Client connection error");
                    }
                });
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,"Server error" + e.getMessage());
        }
    }

    public void shutdown() {
        LOGGER.info("Shutting down server.");
        executorService.shutdown();
        try {
            server.close();
            LOGGER.log(Level.INFO,"Server closed successfully.");
        } catch (IOException e) {
            LOGGER.log(Level.WARNING,"Failed to shutdown server.");
        }
    }
}
