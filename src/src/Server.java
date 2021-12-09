import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {

    private static ArrayList<Post> discussionPosts = new ArrayList<>();

    public static ArrayList<Post> getDiscussionPosts() {
        return discussionPosts;
    }

    public void setDiscussionPosts(ArrayList<Post> discussionPosts) {
        Server.discussionPosts = discussionPosts;
    }

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
        //fields
        boolean invalidUsername = false;
        boolean invalidLogin = false;
        Data data = new Data();
        static {
            Data data2 = new Data();
            discussionPosts = data2.readPostFile();
        }
        boolean teacher;
        Menus menusMain = new Menus();
        ArrayList<String> logins =  data.getLoginFile();
        String loginPassword = "";
        String truePassword = "";
        String identification = "";
        ArrayList<String> grades = data.getGrades();
        String role;
        boolean loop1 = false;
        boolean loopAgain =  false;
        boolean newAccountLoop = false;
        String newUsername = "";
        String newPassword = "";
        boolean yOrN = true;
        ArrayList<String> courses = new ArrayList<>();
        String courseStuff = "";
        String userName;

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

                do {
                    input = br.readLine();
                    String stuff = "";
                    if (input.contains("_")) {
                        int index = input.indexOf("_");
                        stuff = input.substring(0, index);
                        input = input.substring(index + 1);
                    }
                    // login string inputs
                    switch (input) {
                        case "loginUsername":
                            String failed = "";
                            int loginCounter = 0;
                            boolean loginLoop = true;
                            do {
                                if (loginCounter == 0) {
                                    for (String value : logins) {
                                        String[] login = value.split(";");
                                        if (stuff.equals(login[0])) {
                                            userName = stuff;
                                            failed = "success";
                                            pw.println("username success");
                                            truePassword = login[1];
                                            if (login[2].equals("teacher")) {
                                                teacher = true;
                                            } else {
                                                teacher = false;
                                            }
                                            identification = value;
                                        }
                                    }
                                    if (!failed.equals("success")) {
                                        loginLoop = true;
                                        pw.println("username failed");
                                    } else {
                                        loginLoop = false;
                                    }
                                    loginCounter++;
                                } else {
                                    String readerLogin = br.readLine();
                                    String stuff2 = "";
                                    if (readerLogin.contains("_")) {
                                        int index = input.indexOf("_");
                                        stuff2 = readerLogin.substring(0, index);
                                        readerLogin = readerLogin.substring(index + 1);
                                    }
                                    for (String value : logins) {
                                        String[] login = value.split(";");
                                        if (stuff2.equals(login[0])) {
                                            userName = stuff2;
                                            failed = "success";
                                            pw.println("username success");
                                            truePassword = login[1];
                                            if (login[2].equals("teacher")) {
                                                teacher = true;
                                            } else {
                                                teacher = false;
                                            }
                                            identification = value;
                                        }
                                    }
                                    if (!failed.equals("success")) {
                                        loginLoop = true;
                                        pw.println("username failed");
                                    } else {
                                        loginLoop = false;
                                    }
                                }
                            } while (loginLoop);

                            String passwordChecker = "";
                            do {
                                try {
                                    passwordChecker = br.readLine();
                                } catch (IOException e) {
                                    System.out.println("There was an error reading the line!");
                                    e.printStackTrace();
                                    break;
                                }
                                if (!truePassword.equals("") && truePassword.equals(passwordChecker)) {
                                    passwordChecker = "password success";
                                    loginLoop = false;
                                } else {
                                    passwordChecker = "password failed";
                                    loginLoop = true;
                                }
                                pw.println(passwordChecker);
                            } while (loginLoop);

                            if (teacher) {
                                pw.println("teacher is true");
                            } else {
                                pw.println("teacher is false");
                            }
                            yOrN = false;
                            /*
                            int yesOrNo = Integer.parseInt(br.readLine());
                            if (yesOrNo == JOptionPane.YES_OPTION) {
                                yOrN = false;
                            } else {
                                yOrN = true;
                            }
                             */
                            break;
                        case "newAccount":
                            int counter = 0;
                            newAccountLoop = false;
                            do {
                                if (counter == 0) {
                                    for (String value : logins) {
                                        String[] login = value.split(";");
                                        if (stuff.equals(login[0])) {
                                            newAccountLoop = true;
                                            pw.println("username already exists");
                                        } else {
                                            newAccountLoop = false;
                                            pw.println("passed");
                                        }
                                    }
                                    counter++;
                                } else {
                                    stuff = br.readLine();
                                    String stuff2 = "";
                                    if (stuff.contains("_")) {
                                        int index = input.indexOf("_");
                                        stuff = stuff.substring(0, index);
                                    }
                                    for (String value : logins) {
                                        String[] login = value.split(";");
                                        if (stuff.equals(login[0])) {
                                            newAccountLoop = true;
                                            pw.println("username already exists");
                                        } else {
                                            newAccountLoop = false;
                                            pw.println("passed");
                                        }
                                    }
                                }
                            } while (newAccountLoop);

                            String passwordNew = (String) br.readLine();
                            String role = (String) br.readLine();

                            identification = stuff + ";" + passwordNew + ";" + role;
                            userName = stuff;
                            logins.add(identification);
                            data.setLoginFile(logins);
                            System.out.println(logins);
                            if (role.equals("teacher")) {
                                pw.println("teacher is true");
                            } else {
                                pw.println("teacher is false");
                            }
                            yOrN = false;
                            /*
                            int yeSOrNo = Integer.parseInt(br.readLine());
                            if (yeSOrNo == JOptionPane.YES_OPTION) {
                                yOrN = false;
                            } else {
                                yOrN = true;
                            }
                             */
                            break;
                        case "edit":
                            int editCounter = 0;
                            String loginPopUp = stuff;
                            invalidLogin = true;
                            do {
                                if (editCounter == 0) {
                                    for (String value : logins) {
                                        String[] login = value.split(";");
                                        if (loginPopUp.equals(login[0])) {
                                            invalidLogin = false;
                                            truePassword = login[1];
                                            if (login[2].equals("teacher")) {
                                                teacher = true;
                                            } else {
                                                teacher = false;
                                            }
                                            identification = value;
                                        }
                                    }
                                    if (invalidLogin) {
                                        pw.println("username not found");
                                    } else {
                                        pw.println(truePassword);
                                    }
                                    editCounter++;
                                } else {
                                    loginPopUp = br.readLine();
                                    if (loginPopUp.contains("_")) {
                                        int index = loginPopUp.indexOf("_");
                                        loginPopUp = loginPopUp.substring(0, index);
                                    }
                                    for (String value : logins) {
                                        String[] login = value.split(";");
                                        if (loginPopUp.equals(login[0])) {
                                            invalidLogin = false;
                                            truePassword = login[1];
                                            if (login[2].equals("teacher")) {
                                                teacher = true;
                                            } else {
                                                teacher = false;
                                            }
                                            identification = value;
                                        }
                                    }
                                    if (invalidLogin) {
                                        pw.println("username not found");
                                    } else {
                                        pw.println(truePassword);
                                    }
                                }
                            } while (invalidLogin);

                            //create new username and password
                            int counterNew = 0;
                            do {
                                newUsername = br.readLine();
                                if (counterNew == 0) {
                                    counterNew++;
                                    for (String value : logins) {
                                        String[] login = value.split(";");
                                        if (newUsername.equals(login[0])) {
                                            pw.println("username already taken");
                                            System.out.println("Username is already taken. Please enter a new one");
                                            invalidLogin = true;
                                            break;
                                        } else
                                            userName = newUsername;
                                            pw.println("valid username");
                                            invalidLogin = false;
                                    }
                                } else {
                                    //newUsername = br.readLine();
                                    for (String value : logins) {
                                        String[] login = value.split(";");
                                        if (newUsername.equals(login[0])) {
                                            pw.println("username already taken");
                                            System.out.println("Username is already taken. Please enter a new one");
                                            invalidLogin = true;
                                            break;
                                        } else
                                            userName = newUsername;
                                            pw.println("valid username");
                                            invalidLogin = false;
                                    }
                                }
                            } while (invalidLogin);
                            newPassword = (String) br.readLine();

                            //replace old login with new login
                            for (int i = 0; i < logins.size(); i++) {
                                if (teacher) {
                                    if (logins.get(i).equals(loginPopUp + ";" + truePassword + ";teacher")) {
                                        logins.remove(i);
                                        identification = newUsername + ";" + newPassword + ";teacher";
                                        logins.add(identification);
                                    }
                                } else {
                                    if (logins.get(i).equals(loginPopUp + ";" + truePassword + ";student")) {
                                        logins.remove(i);
                                        identification = newUsername + ";" + newPassword + ";student";
                                        logins.add(identification);
                                    }
                                }
                            }
                            data.setLoginFile(logins);
                            System.out.println(logins);

                            if (teacher) {
                                pw.println("teacher is true");
                            } else {
                                pw.println("teacher is false");
                            }

                            yOrN = false;
                            break;
                        case "delete":
                            //logins = data.getLoginFile();
                            int counterDelete = 0;
                            do {
                                if (counterDelete == 0) {
                                    for (String value : logins) {
                                        String[] login = value.split(";");
                                        if (stuff.equals(login[0])) {
                                            invalidLogin = false;
                                            pw.println("username found");
                                            truePassword = login[1];
                                        } else {
                                            invalidLogin = true;
                                        }
                                    }
                                    if (invalidLogin) {
                                        pw.println("username not found");
                                    }
                                    counterDelete++;
                                } else {
                                    String secondChance = br.readLine();
                                    if (secondChance.contains("_")) {
                                        int index = secondChance.indexOf("_");
                                        secondChance = secondChance.substring(0, index);
                                    }
                                    for (String value : logins) {
                                        String[] login = value.split(";");
                                        if (secondChance.equals(login[0])) {
                                            invalidLogin = false;
                                            pw.println("username found");
                                            truePassword = login[1];
                                        } else {
                                            invalidLogin = true;
                                        }
                                    }
                                    if (invalidLogin) {
                                        pw.println("username not found");
                                    }
                                }
                            } while (invalidLogin);
                            int secondChancePassword = 0;
                            do {
                                if (secondChancePassword == 0) {
                                    String password = (String) br.readLine();
                                    if (truePassword.equals(password)) {
                                        invalidLogin = false;
                                        pw.println(truePassword);
                                        for (int i = 0; i < logins.size(); i++) {
                                            if (logins.get(i).equals(stuff + ";" + password + ";student")
                                                    || logins.get(i).equals(stuff + ";" + password + ";teacher")) {
                                                logins.remove(i);
                                            }
                                        }
                                    } else {
                                        pw.println("invalid");
                                        invalidLogin = true;
                                    }
                                    secondChancePassword++;
                                } else {
                                    String password2 = (String) br.readLine();
                                    if (truePassword.equals(password2)) {
                                        invalidLogin = false;
                                        pw.println(truePassword);
                                        for (int i = 0; i < logins.size(); i++) {
                                            if (logins.get(i).equals(stuff + ";" + password2 + ";student")
                                                    || logins.get(i).equals(stuff + ";" + password2 + ";teacher")) {
                                                logins.remove(i);
                                            }
                                        }
                                    } else {
                                        pw.println("invalid");
                                        invalidLogin = true;
                                    }
                                }
                            } while (invalidLogin);
                            data.setLoginFile(logins);
                            System.out.println(logins);
                            yOrN = true;
                            break;
                    }
                } while (yOrN);

                //beginning of main screen
                boolean looper = true;
                do {
                    String whichButton = br.readLine();

                    switch (whichButton) {
                        case "specificBtn":
                            //beginning of handling "view specific course" button press
                            int choiceViewSpec = 0;
                            boolean loopViewSpec = true;
                            boolean loopViewSpec1 = false;
                            ArrayList<String> loginsViewSpec = data.getLoginFile();
                            ArrayList<String> gradesViewSpec = data.getGrades();
                            String identificationViewSpec = "";

                            for (int i = discussionPosts.size() - 1; 0 <= i; i--) {
                                String course = discussionPosts.get(i).getCourse();
                                if (!courses.contains(course)) {
                                    courses.add(course);
                                }
                            }
                            String response = br.readLine();

                            if (!courses.contains(response)) {
                                pw.println("invalid choice");
                                break;
                            } else {
                                pw.println("valid choice");
                            }

                            ArrayList<Post> curatedPosts = new ArrayList<>();
                            for (int i = discussionPosts.size() - 1; 0 <= i; i--) {
                                String course = discussionPosts.get(i).getCourse();
                                if (response.equals(course) || response.equals("all")) {
                                    curatedPosts.add(discussionPosts.get(i));
                                }
                            }
                            do {
                                ArrayList<String> numPlacer = new ArrayList<>();
                                String output = "";
                                for (int i = curatedPosts.size() - 1; i >= 0; i--) {
                                    numPlacer.add(i + 1 + ") " + curatedPosts.get(i).toString());
                                }
                                for (String s : numPlacer) {
                                    output = output + s + "_";
                                    output = output.replaceAll("\n", "_");
                                }
                                pw.println(output);

                                String optionSelection = br.readLine();

                                switch (optionSelection) {
                                    case "1":
                                        loopViewSpec = false;
                                        looper = true;
                                        break;
                                    case "3_teacher":
                                        String printStudents = "";
                                        ArrayList<String> students = new ArrayList<>();
                                        for (int i = 0; i < logins.size(); i++) {
                                            String[] login = logins.get(i).split(";");
                                            if (login[2].equals("student")) {
                                                printStudents = printStudents + login[0] + "_";
                                                students.add(logins.get(i) + ";" + i);
                                            }
                                        }
                                        pw.println(printStudents);
                                        boolean loop2 = true;
                                        String studentID = "";
                                        String student = br.readLine();
                                        do {
                                            for (int i = 0; i < students.size(); i++) {
                                                if (students.get(i).split(";")[0].equals(student)) {
                                                    loop2 = false;
                                                    studentID = students.get(i);
                                                }
                                            }
                                        } while (loop2);

                                        String allStudentPosts = "All posts by " + student + "_";
                                        for (int i = 0; i < discussionPosts.size(); i++) {
                                            for (int ii = 0; ii < discussionPosts.get(i).getComments().size(); ii++) {
                                                if (discussionPosts.get(i).getComments().get(ii).
                                                        getPoster().equals(student)) {
                                                    allStudentPosts = allStudentPosts + discussionPosts.get(i).
                                                            getComments().get(ii).toString() + "_";
                                                    allStudentPosts = allStudentPosts.replaceAll("\n", "_");
                                                }
                                            }
                                        }
                                        pw.println(allStudentPosts);

                                        int grade = Integer.parseInt(br.readLine());
                                        String[] idBits = studentID.split(";");
                                        studentID = idBits[0] + ";" + grade;
                                        boolean once = true;
                                        for (int i = 0; i < grades.size(); i++) {
                                            String[] grade1 = grades.get(i).split(";");
                                            if (idBits[0].equals(grade1[0])) {
                                                grades.set(i, studentID);
                                                once = false;
                                            }
                                        }
                                        if (once)
                                            grades.add(studentID);
                                        data.setGrades(grades);
                                        break;
                                    case "3_student":
                                        boolean once1 = true;
                                        String grade2 = "";
                                        for (int i = 0; i < grades.size(); i++) {
                                            String[] grade1 = grades.get(i).split(";");
                                            String[] idBits1 = identification.split(";");
                                            if (idBits1[0].equals(grade1[0])) {
                                                grade2 = "Your grade is " + grade1[1];
                                                once1 = false;
                                            }
                                        }
                                        if (once1) {
                                            grade2 = "You have not been graded yet";
                                        }
                                        pw.println(grade2);
                                        break;
                                    case "4":
                                        String fileName = br.readLine();
                                        String course = br.readLine();
                                        String username = br.readLine();
                                        Post p = new Post(fileName, username, course, (discussionPosts.size() + ";"));
                                        discussionPosts.add(p);
                                        if (course.equals(response) || response.equals("all")) {
                                            curatedPosts.add(p);
                                        }
                                        break;
                                    case "else":
                                        int choice = Integer.parseInt(br.readLine());
                                        pw.println(curatedPosts.size());
                                        pw.println(userName);
                                        //TODO: secondary menu continuation

                                        /*
                                        boolean secondaryMenuLoop = true;
                                        do {

                                        } while (secondaryMenuLoop);

                                         */
                                }
                            } while (!loopViewSpec);
                            data.createPostFile(discussionPosts);
                            break;
                        case "all":
                            break;

                    }
                } while (looper);


                /*
                while ((input = br.readLine()) != null) {
                    pw.flush();
                }
                 */
                pw.close();
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
