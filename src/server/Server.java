package server;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.gson.Gson;
import server.command.CommandHandler;
import server.database.Database;
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
    private final Gson gson = new Gson();
    private final CommandHandler commandHandler;
    private ServerSocket server;
    private static final Logger logger = Logger.getLogger(Server.class.getName());

    public Server(Database database) {
        this.executorService = Executors.newCachedThreadPool(
                new ThreadFactoryBuilder().setNameFormat("client-handler-%d").build());
        this.commandHandler = new CommandHandler(database, this);
    }

    public void start() {
        try {
            server = new ServerSocket(PORT);
            while (!server.isClosed()) {
                Socket socket = server.accept();

                executorService.submit(() -> {
                    logger.info(Thread.currentThread().getName());
                    try (
                            DataInputStream input = new DataInputStream(socket.getInputStream());
                            DataOutputStream output = new DataOutputStream(socket.getOutputStream())
                    ) {
                        String msg = input.readUTF();
                        System.out.println("Received: " + msg);
                        ClientRequest clientRequest = gson.fromJson(msg, ClientRequest.class);

                        Response serverResponse = commandHandler.executeRequest(clientRequest);
                        String jsonServerResponse = gson.toJson(serverResponse);
                        output.writeUTF(jsonServerResponse);
                        System.out.println("Sent: " + jsonServerResponse);
                    } catch (IOException e) {
                        logger.log(Level.SEVERE,"Client connection error");
                    }
                });
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE,"Server error" + e.getMessage());
        }
    }

    public void shutdown() {
        logger.info("Shutting down server.");
        executorService.shutdown();
        try {
            server.close();
            logger.log(Level.INFO,"Server closed successfully.");
        } catch (IOException e) {
            logger.log(Level.WARNING,"Failed to shutdown server.");
        }
    }
}
