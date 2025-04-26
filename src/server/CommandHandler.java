package server;

import server.commands.*;

//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.gson.Gson;
//import com.google.gson.JsonObject;
//
//import java.util.InputMismatchException;
//
public class CommandHandler {

    private final Database db;

    public CommandHandler(Database db){
        this.db = db;
    }

    public String executeRequest(JsonModel request){
        Command command = switch (request.getType().toLowerCase()) {
            case "get" -> new GetCommand(db,request.getKey());
            case "set" -> new SetCommand(db,request.getKey(),request.getValue());
            case "delete" -> new DeleteCommand(db, request.getKey());
            case "exit" -> () -> "exit";
            default -> throw new RuntimeException();
        };

        return command.execute();

    }







}
//    private final Database db;
//    private static Gson responseJson = new Gson();
//    private static ObjectMapper om = new ObjectMapper();
//
//    public CommandHandler(Database db) {
//        this.db = db;
//    }
//
//    public String execute(String input) {
//        String[] parts = input.trim().split("\\s+", 3);
//
//        if (parts.length < 2) {
//            System.out.println("ERROR");
//            return "ERROR";
//        }
//
//        String action = parts[0].toLowerCase();
//        int index;
//
//        try {
//            index = Integer.parseInt(parts[1]) - 1;
//            switch (action) {
//                case "get" -> {
//                    String value = db.get(index);
//                    if("".equals(value)) throw new CustomException();
//                    else{
////                        System.out.println(value);
//
//                        return om.createObjectNode()
//                                .put("response", "OK")
//                                .put("value", value)
//                                .toString();
//                    }
//                }
//                case "set" -> {
//                    if (parts.length < 3) {
//                        return "ERROR";
//                    }
//                    db.set(index, parts[2]);
//                        return om.createObjectNode()
//                            .put("response", "OK")
//                            .toString();
//
//                }
//                case "delete" -> {
//                    db.delete(index);
//                    return om.createObjectNode()
//                            .put("response", "OK")
//                            .toString();
//
//                }
//                default -> {
//                    return "ERROR";
//                }
//            }
//
//        }
//        catch (CustomException ce){
//            return om.createObjectNode()
//                    .put("response","ERROR")
//                    .put("reason","No such key")
//                    .toString();
//
//        }
//        catch (IndexOutOfBoundsException | NumberFormatException e){
//
//        }
//        return "";
//    }




