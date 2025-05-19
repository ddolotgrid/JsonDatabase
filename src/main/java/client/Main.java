package client;

import client.request.Request;
import client.request.RequestParser;
import com.beust.jcommander.ParameterException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) {
        try {
            Request request = RequestParser.parseArgs(args);
            Client client = new Client(request);
            client.start();
        } catch (ParameterException pe) {
            LOGGER.log(Level.SEVERE,"found empty or incorrect flag: correct format is -<flag> <value>\n" +
                    "-t = type\n -k = key\n -v value\n -in filename");
        }
    }
}
