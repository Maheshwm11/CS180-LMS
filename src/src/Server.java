import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private static final Object GATEKEEPER = new Object();
    private static ArrayList<String> logins ;
    private static ArrayList<Post> discussionPosts;
    private static ArrayList<String> grades;
    private static ArrayList<String> activeUsers = new ArrayList<>();

    // Client receiver loop
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket;
        // server listening for port 4240
        serverSocket = new ServerSocket(4240);
        serverSocket.setReuseAddress(true);
        System.out.println("Server Idle");

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
                logins = data.getLoginFile();
                discussionPosts = data.readPostFile();
                grades = data.getGrades();
                boolean loggedIn = true;

                do {
                    boolean returned = false;
                    command = objectInputStream.readUTF();
                    if (!command.equals("getPosts")) {
                        System.out.println("Received from client: " + command);
                    }
                    commandArray = command.split(";");

                    // Logins
                    switch (commandArray[0]) {
                        case "logout" -> loggedIn = false;
                        case "login" -> {
                            synchronized (GATEKEEPER) {
                                for (String login : logins) {
                                    if (commandArray[1].equals(login.split(";")[0]) &&
                                            commandArray[2].equals(login.split(";")[1]) &&
                                            !activeUsers.contains(commandArray[1])) {
                                        objectOutputStream.writeUTF(login.split(";")[2]);
                                        activeUsers.add(login.split(";")[0]);
                                        returned = true;
                                    }
                                }
                            }
                            if (!returned) {
                                objectOutputStream.writeUTF("null");
                            }
                            objectOutputStream.flush();
                            synchronized (GATEKEEPER) {
                                data.setLoginFile(logins);
                            }
                        }
                        case "createAccount" -> {
                            synchronized (GATEKEEPER) {
                                for (String i : logins) {
                                    if (i.split(";")[0].equals(commandArray[1])) {
                                        objectOutputStream.writeUTF("duplicateName");
                                        returned = true;
                                    }
                                }
                            }
                            if (!returned) {
                                objectOutputStream.writeUTF("success");
                                synchronized (GATEKEEPER) {
                                    logins.add(String.format("%s;%s;%s", commandArray[1], commandArray[2], commandArray[3]));
                                    if (commandArray[3].equals("student")) {
                                        grades.add(String.format("%s;0", commandArray[1]));
                                    }
                                    data.setGrades(grades);
                                    data.setLoginFile(logins);
                                }
                            }
                            objectOutputStream.flush();
                        }
                        case "editAccount" -> {
                            String role = "student";
                            synchronized (GATEKEEPER) {
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
                        }
                        case "deleteAccount" -> {
                            synchronized (GATEKEEPER) {
                                for (int i = 0; i < logins.size(); i++) {
                                    if (commandArray[1].equals(logins.get(i).split(";")[0]) &&
                                            commandArray[2].equals(logins.get(i).split(";")[1])) {
                                        logins.remove(i);
                                    }
                                }
                                data.setLoginFile(logins);
                            }
                        }
                    }
                    // Menus
                    switch (commandArray[0]) {
                        case "buildCourseArray" -> {
                            ArrayList<String> courses = new ArrayList<>();
                            synchronized (GATEKEEPER) {
                                for (Post i : discussionPosts) {
                                    if (!courses.contains(i.getCourse())) {
                                        courses.add(i.getCourse());
                                    }
                                }
                            }
                            objectOutputStream.writeObject(courses);
                        }
                        case "seeGrade" -> {
                            synchronized (GATEKEEPER) {
                                for (String i : grades) {
                                    if (commandArray[1].equals(i.split(";")[0])) {
                                        objectOutputStream.writeUTF(i.split(";")[1]);
                                    }
                                }
                            }
                            objectOutputStream.flush();
                        }
                        case "getStudents" -> {
                            ArrayList<String> students = new ArrayList<>();
                            synchronized (GATEKEEPER) {
                                for (String i : logins) {
                                    if (i.split(";")[2].equals("student")) {
                                        students.add(i.split(";")[0]);
                                    }
                                }
                            }
                            objectOutputStream.writeObject(students);
                        }
                        case "gradeStudent" -> {
                            synchronized (GATEKEEPER) {
                                for (String i : grades) {
                                    if (commandArray[1].equals(i.split(";")[0]) && !returned) {
                                        grades.remove(i);
                                        grades.add(String.format("%s;%s", commandArray[1], commandArray[2]));
                                        returned = true;
                                    }
                                }
                                data.setGrades(grades);
                            }
                        }
                        case "newPost" -> {
                            synchronized (GATEKEEPER) {
                                discussionPosts.add(new Post(commandArray[1], commandArray[2], commandArray[3], null));
                                data.createPostFile(discussionPosts);
                            }
                        }
                        case "editPost" -> {
                            int ind = 0;
                            Post post = null;
                            try {
                                post = (Post) objectInputStream.readObject();
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                            Post parent = post.getParent();
                            post.setBodyText(commandArray[1]);
                            while (parent != null) {
                                for (int i = 0; i < parent.getComments().size(); i++) {
                                    if (parent.getComments().get(i).getPoster().equals(post.getPoster()) &&
                                            parent.getComments().get(i).getTimeStamp().equals(post.getTimeStamp())) {
                                        parent.getComments().remove(i);
                                        ind = i - 1;
                                    }
                                }
                                if (ind < 0) {
                                    ind = 0;
                                }
                                parent.commmentPostAt(post, ind);
                                post = parent;
                                parent = post.getParent();
                            }
                            synchronized (GATEKEEPER) {
                                for (int i = 0; i < discussionPosts.size(); i++) {
                                    if (discussionPosts.get(i).getPoster().equals(post.getPoster()) &&
                                            discussionPosts.get(i).getTimeStamp().equals(post.getTimeStamp())) {
                                        discussionPosts.remove(i);
                                        ind = i - 1;
                                    }
                                }
                                if (ind < 0) {
                                    ind = 0;
                                }
                                discussionPosts.add(ind, post);
                                data.createPostFile(discussionPosts);
                            }
                        }
                        case "deletePost" -> {
                            int ind = 0;
                            Post post = null;
                            boolean topLevel = true;
                            try {
                                post = (Post) objectInputStream.readObject();
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                            Post parent = post.getParent();

                            if (parent != null) {
                                for (int i = 0; i < parent.getComments().size(); i++) {
                                    if (parent.getComments().get(i).getPoster().equals(post.getPoster()) &&
                                            parent.getComments().get(i).getTimeStamp().equals(post.getTimeStamp())) {
                                        parent.getComments().remove(i);
                                        topLevel = false;
                                    }
                                }
                                post = parent;
                                parent = post.getParent();
                            }
                            while (parent != null) {
                                for (int i = 0; i < parent.getComments().size(); i++) {
                                    if (parent.getComments().get(i).getPoster().equals(post.getPoster()) &&
                                            parent.getComments().get(i).getTimeStamp().equals(post.getTimeStamp())) {
                                        parent.getComments().remove(i);
                                        ind = i - 1;
                                    }
                                }
                                if (ind < 0) {
                                    ind = 0;
                                }
                                parent.commmentPostAt(post, ind);
                                post = parent;
                                parent = post.getParent();
                            }
                            synchronized (GATEKEEPER) {
                                for (int i = 0; i < discussionPosts.size(); i++) {
                                    if (discussionPosts.get(i).getPoster().equals(post.getPoster()) &&
                                            discussionPosts.get(i).getTimeStamp().equals(post.getTimeStamp())) {
                                        discussionPosts.remove(i);
                                        ind = i - 1;
                                    }
                                }
                                if (!topLevel) {
                                    discussionPosts.add(ind, post);
                                }
                                data.createPostFile(discussionPosts);
                            }
                        }
                        case "newComment" -> {
                            int ind = 0;
                            Post post = null;
                            try {
                                post = (Post) objectInputStream.readObject();
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                            Post parent = post.getParent();
                            post.commentString(commandArray[1], commandArray[2]);
                            while (parent != null) {

                                for (int i = 0; i < parent.getComments().size(); i++) {
                                    if (parent.getComments().get(i).getPoster().equals(post.getPoster()) &&
                                            parent.getComments().get(i).getTimeStamp().equals(post.getTimeStamp())) {
                                        parent.getComments().remove(i);
                                        ind = i - 1;
                                    }
                                }
                                if (ind < 0) {
                                    ind = 0;
                                }
                                parent.commmentPostAt(post, ind);
                                post = parent;
                                parent = post.getParent();
                            }
                            synchronized (GATEKEEPER) {
                                for (int i = 0; i < discussionPosts.size(); i++) {
                                    if (discussionPosts.get(i).getPoster().equals(post.getPoster()) &&
                                            discussionPosts.get(i).getTimeStamp().equals(post.getTimeStamp())) {
                                        discussionPosts.remove(i);
                                        ind = i - 1;
                                    }
                                }
                                if (ind < 0) {
                                    ind = 0;
                                }
                                System.out.println("ind " + ind);
                                discussionPosts.add(ind, post);
                                data.createPostFile(discussionPosts);
                            }
                        }
                        case "curateIndex" -> {
                            Post post = null;
                            try {
                                post = (Post) objectInputStream.readObject();
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                            Post parent = post.getParent();
                            post.setCuratedIndex(Integer.parseInt(commandArray[1]));
                            while (parent != null) {
                                int ind = 0;
                                for (int i = 0; i < parent.getComments().size(); i++) {
                                    if (parent.getComments().get(i).getPoster().equals(post.getPoster()) &&
                                            parent.getComments().get(i).getTimeStamp().equals(post.getTimeStamp())) {
                                        parent.getComments().remove(i);
                                        ind = i;
                                    }
                                }
                                parent.commmentPostAt(post, ind);
                                post = parent;
                                parent = post.getParent();
                            }
                            synchronized (GATEKEEPER) {
                                for (int i = 0; i < discussionPosts.size(); i++) {
                                    if (discussionPosts.get(i).getPoster().equals(post.getPoster()) &&
                                            discussionPosts.get(i).getTimeStamp().equals(post.getTimeStamp())) {
                                        discussionPosts.remove(i);
                                    }
                                }
                                discussionPosts.add(post);
                                data.createPostFile(discussionPosts);
                            }
                        }
                    }
                } while (loggedIn);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
