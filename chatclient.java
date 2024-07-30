import java.io.*;
import java.net.*;
import java.util.Scanner;

public class chatclient {
    public static void main(String[] args) {
        String serverAddress = "localhost"; // or the IP address of the server
        int port = 12345;

        try (Socket socket = new Socket(serverAddress, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.println("Connected to chat server");
            
            // Thread for receiving messages
            new Thread(() -> {
                String serverMessage;
                try {
                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println("Server: " + serverMessage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // Main thread for sending messages
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String clientMessage = scanner.nextLine();
                out.println(clientMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

