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


    private static class ClientHandler implements Runnable {
        Socket socket;
        public ClientHandler(Socket socket) {
            this.socket = socket;
        }
        @Override
        public void run() {
            PrintWriter pw;
            BufferedReader br;
            try {
                // client output stream
                pw = new PrintWriter(socket.getOutputStream(), true);

                // client input stream
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String input;
                while ((input = br.readLine()) != null) {
                    pw.flush();
                }
                pw.close();
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
