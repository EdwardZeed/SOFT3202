package week5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        BufferedReader reader;
        ArrayList<String> roomStrings = new ArrayList<>();
        ArrayList<String> itemStrings = new ArrayList<>();

        List<List<String>> playerInventory = new ArrayList<>();
        List<String> allowedDirections = new ArrayList<>(Arrays.asList("NORTH", "SOUTH", "EAST", "WEST"));
        int playerRoom;

        try {
            reader = new BufferedReader(new FileReader("rooms.dat"));
            String state = "none";
            while (reader.ready()) {
                String nextLine = reader.readLine();
                if (nextLine.charAt(0) == '#') {
                    state = nextLine.substring(1);
                    continue;
                }
                switch (state) {
                    case "ROOMS" -> roomStrings.add(nextLine);
                    case "ITEMS" -> itemStrings.add(nextLine);
                    default -> throw new LevelFileException("Invalid or missing level file #header");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        Map<Integer, List<List<String>>> roomItems = new HashMap<>();
        Map<Integer, Map<String, Integer>> roomExits = new HashMap<>();
        for (String roomString: roomStrings) {
            String[] tokens = roomString.split("\\|");
            int id = Integer.parseInt(tokens[0]);

            if (roomExits.containsKey(id)) throw new LevelFileException("Duplicate room ID");
            if (roomItems.containsKey(id)) throw new LevelFileException("Duplicate room ID");

            Map<String, Integer> exits = new HashMap<>();
            List<List<String>> items = new ArrayList<>();
            roomExits.put(id, exits);
            roomItems.put(id, items);

            String exitString = tokens[1];

            String[] exitTokens = exitString.split(" ");
            if (!"EXITS".equals(exitTokens[0])) throw new LevelFileException("Malformed row - expected EXITS");

            for (int i = 1; i < exitTokens.length; ++i) {
                String[] exitDirectionTokens = exitTokens[i].split("=");
                exits.put(exitDirectionTokens[0], Integer.parseInt(exitDirectionTokens[1]));
            }
        }
        for (Map<String, Integer> exits: roomExits.values()) {
            for (int exitTarget: exits.values()) {
                if (!roomExits.containsKey(exitTarget)) throw new LevelFileException("Exit room ID undefined: " + exitTarget);
                if (!roomItems.containsKey(exitTarget)) throw new LevelFileException("Exit room ID undefined: " + exitTarget);
            }
        }

        for (String itemString: itemStrings) {
            String[] tokens = itemString.split("\\|");
            int roomID = Integer.parseInt(tokens[0]);
            String name = tokens[1];
            String[] keywords = tokens[2].toLowerCase().split(" ");

            if (!roomItems.containsKey(roomID)) throw new LevelFileException("Invalid item room");

            List<String> item = new ArrayList<>();
            item.add(name);
            item.addAll(Arrays.asList(keywords));

            roomItems.get(roomID).add(item);
        }
        playerRoom = 1;

        System.out.println("Starting game");
        System.out.println("Commands are:");
        System.out.println("\tlook");
        System.out.println("\tmove <direction>");
        System.out.println("\tget <item>");
        System.out.println("\tinventory");
        System.out.println("\tquit");

        Scanner scanner = new Scanner(System.in);

        String input = "";


        while (!"quit".equals(input)) {
            input = scanner.nextLine();
            String[] commandTokens = input.split(" ");

            switch (commandTokens[0]) {
                case "look":
                    look(playerRoom, roomItems, roomExits);
                    break;
                case "move":
                    playerRoom = move(allowedDirections, playerRoom, roomExits, commandTokens);
                    break;
                case "get":
                    get(playerInventory, playerRoom, roomItems, scanner, commandTokens);
                    break;
                case "inventory":
                    inventory(playerInventory);
                case "quit":
                    break;
                default:
                    System.out.println("Command not recognised.");
                    break;
            }
        }
    }

    private static void look(int playerRoom, Map<Integer, List<List<String>>> roomItems, Map<Integer, Map<String, Integer>> roomExits) {
        StringBuilder sb = new StringBuilder();

        sb.append("You see an empty room.\n");
        if (roomItems.get(playerRoom).size() > 0) {
            sb.append("You can see some things here:\n");
            for (List<String> item: roomItems.get(playerRoom)) {
                sb.append("\t");
                sb.append(item.get(0));
                sb.append("\n");
            }
        }
        sb.append("\nExits:");
        if (roomExits.get(playerRoom).size() == 0) {
            sb.append(" NONE");
        }
        for (String direction: roomExits.get(playerRoom).keySet()) {
            sb.append(" ");
            sb.append(direction);
        }

        System.out.println(sb);
        return;
    }

    private static void inventory(List<List<String>> playerInventory) {
        StringBuilder sb;
        if (playerInventory.size() == 0) System.out.println("There is nothing in your inventory.\n");

        sb = new StringBuilder();

        sb.append("You are carrying the following items:\n");
        for (List<String> item: playerInventory) {
            sb.append("\t");
            sb.append(item.get(0));
            sb.append("\n");
        }

        System.out.println(sb);
        return;
    }

    private static int move(List<String> allowedDirections, int playerRoom, Map<Integer, Map<String, Integer>> roomExits, String[] commandTokens) {
        if (commandTokens.length < 2) {
            System.out.println("Move in what direction?");
            return playerRoom;
        }
        String direction = commandTokens[1].toUpperCase();
        if(!allowedDirections.contains(direction)) {
            System.out.println("Direction " + commandTokens[1] + " not found. Try 'look'ing to see the available exits.");
            return playerRoom;
        }
        int targetRoomID;
        try {
            targetRoomID = roomExits.get(playerRoom).get(direction);
        } catch (NullPointerException ignored) {
            System.out.println("No exit in that direction. Try 'look'ing to see the available exits.");
            return playerRoom;
        }

        playerRoom = targetRoomID;
        System.out.println("You exit to the " + commandTokens[1]);
        return playerRoom;
    }

    private static void get(List<List<String>> playerInventory, int playerRoom, Map<Integer, List<List<String>>> roomItems, Scanner scanner, String[] commandTokens) {
        if (commandTokens.length < 2) {
            System.out.println("Get what item?");
            return;
        }
        String[] keywords = Arrays.copyOfRange(commandTokens, 1, commandTokens.length);

        List<List<String>> matches = getMatchingItems(playerRoom, roomItems, keywords);

        String searched = String.join(" ", keywords);
        if (matches.size() == 0) {
            char firstChar = Character.toLowerCase(searched.charAt(0));

            boolean vowel = 'a' == firstChar ||'e' == firstChar ||'i' == firstChar ||'o' == firstChar ||'u' == firstChar;

            System.out.println("You can't see " + (vowel?"an ":"a ") + searched + " here. Try 'look'ing to see what is around you.");
            return;
        }
        if (matches.size() == 1) {
            getItem(roomItems, playerRoom, matches.get(0), playerInventory);
            return;
        }
        // more than 1 match
        int matchIndex = 0;
        System.out.println("More than 1 matching " + searched + " found. Please select an item by number, or c to cancel.");
        for (int i = 0; i < matches.size(); ++i) {
            System.out.println("\t[" + (i+1) + "] " + matches.get(i).get(0));
        }
        while (matchIndex == 0) {
            String inputIndex = scanner.nextLine();
            if ("c".equals(inputIndex)) {
                matchIndex = -1;
                continue;
            }
            try {
                matchIndex = Integer.parseInt(inputIndex);
            } catch (NumberFormatException ignored) {
                System.out.println("Input not recognised - enter a number, or c to cancel.");
            }

            if (matchIndex < 1 || matchIndex > matches.size()) {
                matchIndex = 0;
                System.out.println("Number not found, please try again.");
            }
        }

        if (-1 == matchIndex) {
            System.out.println("Cancelling");
            return;
        }

        List<String> matchedItem = matches.get(--matchIndex);

        getItem(roomItems, playerRoom, matchedItem, playerInventory);
        return;
    }

    private static List<List<String>> getMatchingItems(int playerRoom, Map<Integer, List<List<String>>> roomItems, String[] keywords) {
        List<List<String>> matches = new ArrayList<>();

        for (List<String> item: roomItems.get(playerRoom)) {
            boolean matchingItem = true;
            for (String term: keywords) {
                if (!item.contains(term.toLowerCase())) {
                    matchingItem = false;
                    break;
                }
            }
            if (matchingItem) {
                matches.add(item);
            }
        }
        return matches;
    }

    private static void getItem(Map<Integer, List<List<String>>> roomItems, int playerRoom, List<String> matchedItem, List<List<String>> playerInventory) {
        roomItems.get(playerRoom).remove(matchedItem);
        playerInventory.add(matchedItem);
        System.out.println("You got a " + matchedItem.get(0));
    }

    private static class LevelFileException extends RuntimeException {
        public LevelFileException(String msg) {
            super(msg);
        }
    }
}