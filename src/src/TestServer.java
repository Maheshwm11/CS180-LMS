import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class TestServer {

    // Client receiver loop
    public static void main(String[] args) {
        ServerSocket serverSocket;
        try {
            // server listening for port 4242
            serverSocket = new ServerSocket(4243);
            serverSocket.setReuseAddress(true);

            // infinite client request
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client Connected");

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

            try {
                // client output stream
                pw = new PrintWriter(socket.getOutputStream(), true);

                // client input stream
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                Data data = new Data();
                String command = "";
                ArrayList<String> logins = data.getLoginFile();
                Boolean returned = false;
                Boolean loggedIn = true;

                do {
                    System.out.println("\n\n\ncurrent logins file");
                    for (int i = 0; i < logins.size(); i++) {
                        System.out.println(logins.get(i));
                    }
                    command = br.readLine();
                    String[] commandArray = command.split(";");
                    System.out.println("Received from client: " + command);

                    switch (commandArray[0]) {
                        case "login":
                            for (int i = 0; i < logins.size(); i++) {
                                if (commandArray[1].equals(logins.get(i).split(";")[0]) &&
                                        commandArray[2].equals(logins.get(i).split(";")[1])) {
                                    pw.write("accountMenu");
                                    pw.println();
                                    pw.flush();
                                    returned = true;
                                }
                            }
                            if (!returned) {
                                pw.write("login");
                                pw.println();
                                pw.flush();
                            }
                            break;
                        case "createAccount":
                            logins.add(String.format("%s;%s;%s", commandArray[1], commandArray[2],commandArray[3]));
                            break;
                        case "editAccount":
                            String role = "student";
                            for (int i = 0; i < logins.size(); i++) {
                                if (commandArray[1].equals(logins.get(i).split(";")[0]) &&
                                        commandArray[2].equals(logins.get(i).split(";")[1])) {
                                    role = logins.get(i).split(";")[2];
                                    logins.remove(i);
                                }
                            }
                            logins.add(String.format("%s;%s;%s", commandArray[3], commandArray[4], role));
                            break;
                        case "deleteAccount":
                            for (int i = 0; i < logins.size(); i++) {
                                if (commandArray[1].equals(logins.get(i).split(";")[0]) &&
                                        commandArray[2].equals(logins.get(i).split(";")[1])) {
                                    logins.remove(i);
                                }
                            }
                            break;
                    }
                } while (loggedIn);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
