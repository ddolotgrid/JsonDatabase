package server;

public class CustomException extends RuntimeException {
    public CustomException() {
        super("ERROR");
    }
}
