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
            LOGGER.log(Level.SEVERE, "Found empty or incorrect flag.\n" +
                    "Usage:\n" +
                    "  Either provide a JSON request file:\n" +
                    "    -in <filename>              (Name of request file eg. 'input.json')\n" +
                    "  Or provide command-line parameters:\n" +
                    "    -t <type>                   (Required: Request type: get, set, delete)\n" +
                    "    -k <key>                    (Required: Key or nested key path)\n" +
                    "    -v <value>                  (Optional: Value to set, only used with -t set)\n");

        }
    }
}
