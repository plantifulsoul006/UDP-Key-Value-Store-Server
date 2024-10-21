import java.io.*;
import java.net.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Map;

public class UDPServer {
    private static final int PORT = 2007;
    private static Map<String, String> keyValueMap = new ConcurrentHashMap<>();
    private static final int THREAD_POOL_SIZE = 10;
    private static ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            System.out.println("UDP Server is running on port " + PORT);
            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                executorService.execute(() -> handlePacket(socket, packet));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

    private static void handlePacket(DatagramSocket socket, DatagramPacket packet) {
        try {
            String inputLine = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Received from client: " + inputLine);
            String response = handleCommand(inputLine);

            byte[] sendData = response.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, packet.getAddress(), packet.getPort());
            socket.send(sendPacket);
            System.out.println("Responded to client: " + response);
        } catch (IOException e) {
            System.err.println("Error handling packet: " + e.getMessage());
        }
    }

    private static String handleCommand(String inputLine) {
        String[] tokens = inputLine.trim().split("\\s+", 3);
        if (tokens.length < 1) return "Invalid command format.";

        String command = tokens[0];
        switch (command) {
            case "GET":
                return get(tokens);
            case "PUT":
                return put(tokens);
            case "DELETE":
                return delete(tokens);
            case "KEYS":
                return keys();
            case "TABLE":
                return table();
            case "QUIT":
                return "Goodbye!";
            default:
                return "Invalid command. Please try again.";
        }
    }

    private static String get(String[] tokens) {
        if (tokens.length != 2) return "Invalid GET command format";
        String key = tokens[1];
        String value = keyValueMap.get(key);
        return value != null ? value : "Key not found";
    }

    private static String put(String[] tokens) {
        if (tokens.length != 3) return "Invalid PUT command format";
        String key = tokens[1];
        String value = tokens[2];
        if (key.length() > 10 || value.length() > 10) {
            return "Key or value exceeds maximum size of 10 characters";
        }
        keyValueMap.put(key, value);
        return "Key-value pair added/updated";
    }

    private static String delete(String[] tokens) {
        if (tokens.length != 2) return "Invalid DELETE command format";
        String key = tokens[1];
        if (keyValueMap.remove(key) != null) {
            return "Key deleted";
        } else {
            return "Key not found";
        }
    }

    private static String keys() {
        if (keyValueMap.isEmpty()) {
            return "No keys available";
        }
        StringBuilder keysList = new StringBuilder("Keys:\n");
        keyValueMap.keySet().forEach(k -> keysList.append(k).append("\n"));
        return keysList.toString();
    }

    private static String table() {
        if (keyValueMap.isEmpty()) {
            return "No keys or values available";
        }
        StringBuilder entries = new StringBuilder();
        entries.append(String.format("%n"));
        entries.append(String.format("%n"));
        entries.append(String.format("+--------+------------+%n"));
        entries.append(String.format("| Key    | Value      |%n"));
        entries.append(String.format("+--------+------------+%n"));
        keyValueMap.forEach((k, v) -> entries.append(String.format("| %-6s | %-10s |%n", k, v)));
        entries.append(String.format("+--------+------------+%n"));
        return entries.toString();
    }
}
