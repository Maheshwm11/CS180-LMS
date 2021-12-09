import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class TestServer {

    // Client receiver loop
    public static void main(String[] args) throws IOException{
        ServerSocket serverSocket;
        // server listening for port 4242
        serverSocket = new ServerSocket(4241);
        serverSocket.setReuseAddress(true);

        // infinite client request
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client Connected");

            // new thread object
            ClientHandler clientHandler = new ClientHandler(clientSocket);
            new Thread(clientHandler).start();
        }
    }

    private static class ClientHandler implements Runnable {
        Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }
        @Override
        public void run() {

            try {

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.flush();
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

                Data data = new Data();
                String command;
                String[] commandArray;
                ArrayList<String> logins = data.getLoginFile();
                ArrayList<Post> discussionPosts = data.readPostFile();
                boolean loggedIn = true;

                do {
                    boolean returned = false;
                    command = objectInputStream.readUTF();
                    System.out.println("Received from client: " + command);
                    commandArray = command.split(";");

                    // Logins
                    switch (commandArray[0]) {
                        case "login" -> {
                            for (String login : logins) {
                                if (commandArray[1].equals(login.split(";")[0]) &&
                                        commandArray[2].equals(login.split(";")[1])) {
                                    objectOutputStream.writeUTF(login.split(";")[2]);
                                    returned = true;
                                }
                            }
                            if (!returned) {
                                objectOutputStream.writeUTF("null");
                            }
                            objectOutputStream.flush();
                        }
                        case "createAccount" -> {
                            logins.add(String.format("%s;%s;%s", commandArray[1], commandArray[2],commandArray[3]));
                        }
                        case "editAccount" -> {
                            String role = "student";
                            for (int i = 0; i < logins.size(); i++) {
                                if (commandArray[1].equals(logins.get(i).split(";")[0]) &&
                                        commandArray[2].equals(logins.get(i).split(";")[1])) {
                                    role = logins.get(i).split(";")[2];
                                    logins.remove(i);
                                }
                            }
                            logins.add(String.format("%s;%s;%s", commandArray[3], commandArray[4], role));
                        }
                        case "deleteAccount" -> {
                            for (int i = 0; i < logins.size(); i++) {
                                if (commandArray[1].equals(logins.get(i).split(";")[0]) &&
                                        commandArray[2].equals(logins.get(i).split(";")[1])) {
                                    logins.remove(i);
                                }
                            }
                        }
                    }
                    // Menus
                    switch (commandArray[0]) {
                        case "buildCourseArray" -> {
                            ArrayList<String> courses = new ArrayList<>();
                            for (Post i : discussionPosts) {
                                if (!courses.contains(i.getCourse())) {
                                    courses.add(i.getCourse());
                                }
                            }
                            objectOutputStream.writeObject(courses);
                        }
                    }
                } while (loggedIn);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
