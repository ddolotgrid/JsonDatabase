package server;

public class CommandHandler {
    private final Database db;

    public CommandHandler(Database db) {
        this.db = db;
    }

    public void execute(String input) {
        String[] parts = input.trim().split("\\s+", 3);

        if (parts.length < 2) {
            System.out.println("ERROR");
            return;
        }

        String action = parts[0].toLowerCase();
        int index;

        try {
            index = Integer.parseInt(parts[1]) - 1;

            switch (action) {
                case "get" -> {
                    String value = db.get(index);
                    if("".equals(value)) throw new CustomException();
                    else System.out.println(value);
                }
                case "set" -> {
                    if (parts.length < 3) {
                        System.out.println("ERROR");
                        return;
                    }
                    db.set(index, parts[2]);
                    System.out.println("OK");
                }
                case "delete" -> {
                    db.delete(index);
                    System.out.println("OK");
                }
                default -> System.out.println("ERROR");
            }

        } catch (NumberFormatException | IndexOutOfBoundsException | CustomException e) {
            System.out.println("ERROR");
        }
    }

}

