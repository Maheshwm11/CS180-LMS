import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class TestServer {

    // Client receiver loop
    public static void main(String[] args) throws IOException {
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
                ArrayList<String> grades = data.getGrades();
                boolean loggedIn = true;

                do {
                    boolean returned = false;
                    command = objectInputStream.readUTF();
                    System.out.println("Received from client: " + command);
                    commandArray = command.split(";");

                    // Logins
                    switch (commandArray[0]) {
                        case "logout" -> {
                            loggedIn = false;
                        }
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
                            data.setLoginFile(logins);
                        }
                        case "createAccount" -> {
                            for (String i : logins) {
                                if (i.split(";")[0].equals(commandArray[1])) {
                                    objectOutputStream.writeUTF("duplicateName");
                                    returned = true;
                                }
                            }
                            if (!returned) {
                                objectOutputStream.writeUTF("success");
                                logins.add(String.format("%s;%s;%s", commandArray[1], commandArray[2],commandArray[3]));
                                if (commandArray[3].equals("student")) {
                                    grades.add(String.format("%s;0", commandArray[1]));
                                }
                                data.setGrades(grades);
                                data.setLoginFile(logins);
                            }
                            objectOutputStream.flush();
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
                            data.setLoginFile(logins);
                        }
                        case "deleteAccount" -> {
                            for (int i = 0; i < logins.size(); i++) {
                                if (commandArray[1].equals(logins.get(i).split(";")[0]) &&
                                        commandArray[2].equals(logins.get(i).split(";")[1])) {
                                    logins.remove(i);
                                }
                            }
                            data.setLoginFile(logins);
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
                        case "seeGrade" -> {
                            for (String i : grades) {
                                if (commandArray[1].equals(i.split(";")[0])) {
                                    objectOutputStream.writeUTF(i.split(";")[1]);
                                }
                            }
                            objectOutputStream.flush();
                        }
                        case "getStudents" -> {
                            ArrayList<String> students = new ArrayList<>();
                            for (String i : logins) {
                                if (i.split(";")[2].equals("student")) {
                                    students.add(i.split(";")[0]);
                                }
                            }
                            objectOutputStream.writeObject(students);
                        }
                        case "gradeStudent" -> {
                            for (String i : grades) {
                                if (commandArray[1].equals(i.split(";")[0]) && !returned) {
                                    grades.remove(i);
                                    grades.add(String.format("%s;%s", commandArray[1], commandArray[2]));
                                    returned = true;
                                }
                            }
                            data.setGrades(grades);
                        }
                        case "newPost" -> {
                            discussionPosts.add(new Post(commandArray[1], commandArray[2], commandArray[3]));
                            data.createPostFile(discussionPosts);
                        }
                        case "curatePosts" -> {

                        }
                    }
                } while (loggedIn);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
