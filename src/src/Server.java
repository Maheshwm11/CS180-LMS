import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        ServerSocket serverSocket;
        try {
            // server listening for port 4242
            serverSocket = new ServerSocket(4242);
            serverSocket.setReuseAddress(true);

            // infinite client request
            while (true) {
                Socket clientSocket = serverSocket.accept();

                // new thread object
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
