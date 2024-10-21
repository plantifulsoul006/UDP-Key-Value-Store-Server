import java.io.*;
import java.net.*;
import java.util.Scanner;

public class UDPClient {
    private static final String SERVER_HOST = "127.0.0.1";
    private static final int SERVER_PORT = 2007;

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress address = InetAddress.getByName(SERVER_HOST);
            Scanner scanner = new Scanner(System.in);
            commandsMenu();

            while (scanner.hasNextLine()) {
                String inputLine = scanner.nextLine();
                byte[] sendData = inputLine.getBytes();

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, SERVER_PORT);
                socket.send(sendPacket);

                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);
                String response = new String(receivePacket.getData(), 0, receivePacket.getLength());

                System.out.println("\nServer response: " + response);

                if ("Goodbye!".equals(response)) {
                    break;
                }
                commandsMenu();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void commandsMenu() {
        System.out.println("\nAvailable commands:");
        System.out.println("GET <key>");
        System.out.println("PUT <key> <value>");
        System.out.println("DELETE <key>");
        System.out.println("KEYS");
        System.out.println("TABLE");
        System.out.println("QUIT");
        System.out.println("\nType a command: ");
    }
}
