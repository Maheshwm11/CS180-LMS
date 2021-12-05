import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
/**
 * Project 04 - Menu
 * Program flow and integration class
 *
 * @author Colby, Tiffany, Joseph, Madhav
 * @version 14th November 2021
 */
public class Menus extends JComponent implements Runnable {
    Menus menus;
    JButton login;
    JButton newAccount;
    JButton edit;
    JButton delete;
    boolean invalidUsername = false;
    boolean invalidLogin = false;
    Data data = new Data();
    static boolean teacher;
    static String loginPopUp = "";

    //main screen widgets
    JButton logout;
    JButton all;
    TextField specificCourse;
    JButton specificBtn;
    JFrame mainFrame;
    ArrayList<String> courses = new ArrayList<>();
    String courseStuff = "";

    //close login frame
    JFrame closeLogin;

    //client socket
    static Socket socket;
    static BufferedReader inputStream;
    static PrintWriter outputStream;



    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Menus());

        //create socket to server
        try {
            Socket socket = new Socket("localhost", 4242);
            inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outputStream = new PrintWriter(socket.getOutputStream(), true);
            outputStream.flush();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "There was an error connecting to the server!",
                    "Discussion Board", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }
    }

    private static ArrayList<Post> discussionPosts = new ArrayList<>();

    public static ArrayList<Post> getDiscussionPosts() {
        return discussionPosts;
    }

    public void setDiscussionPosts(ArrayList<Post> discussionPosts) {
        Menus.discussionPosts = discussionPosts;
    }

    ActionListener actionListenerMain = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == logout) {
                mainFrame.dispose();
            }
            if (e.getSource() == specificBtn) {
                outputStream.println("specificBtn");
                int choice = 0;
                boolean loop = true;
                boolean loop1 = false;
                ArrayList<String> logins = data.getLoginFile();
                ArrayList<String> grades = data.getGrades();
                String identification = "";


                do {
                    String response = specificCourse.getText();
                    outputStream.println(response);
                    String validResponse = "";
                    try {
                        validResponse = inputStream.readLine();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Error!",
                                "Discussion Board", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (validResponse.equals("invalid choice")) {
                        JOptionPane.showMessageDialog(null, "Invalid choice.", "Discussion Board",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    do {
                        String output;
                        try {
                            output = inputStream.readLine();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Error!",
                                    "Discussion Board", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        output = output.replaceAll("_", "\n");

                        try {
                            String choiceString = JOptionPane.showInputDialog(null,
                                    output + "\n\nEnter the number of the post to view more details" +
                                            "\nEnter 0 to see advanced options", "Discussion Board",
                                    JOptionPane.PLAIN_MESSAGE);
                            if (choiceString == null) {
                                return;
                            }
                            choice = Integer.parseInt(choiceString);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Enter a number!",
                                    "Discussion Board", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        try {
                            String optionShower = "";
                            boolean invalidChoice = false;
                            int secondChoice = 0;
                            if (choice == 0) {
                                do {
                                    optionShower = "1) Exit to main menu";
                                    if (teacher) {
                                        optionShower = optionShower + "\n2) Grade Student\n3) Create new discussionPost";
                                    } else {
                                        optionShower = optionShower + "\n 2) See your grade";
                                    }
                                    try {
                                        String secondChoiceStr = "";
                                        secondChoiceStr = JOptionPane.showInputDialog(null,
                                                optionShower, "Discussion Board",
                                                JOptionPane.PLAIN_MESSAGE);
                                        if (secondChoiceStr == null) {
                                            return;
                                        }
                                        secondChoice = Integer.parseInt(secondChoiceStr);
                                        invalidChoice = false;
                                    } catch (NumberFormatException ex) {
                                        invalidChoice = true;
                                        JOptionPane.showMessageDialog(null, "Enter a number!",
                                                "Discussion Board", JOptionPane.ERROR_MESSAGE);
                                        return;
                                    }
                                } while (invalidChoice);

                                switch (secondChoice) {
                                    case 1:
                                        outputStream.println("1");
                                        JOptionPane.showMessageDialog(null,
                                                "Going back to the main menu...", "Discussion Board",
                                                JOptionPane.INFORMATION_MESSAGE);
                                        loop = false;
                                        loop1 = true;
                                        break;
                                    case 2:
                                        String printStudents = "";
                                        if (teacher) {
                                            outputStream.println("3_teacher");

                                            try {
                                                printStudents = inputStream.readLine();
                                                printStudents = printStudents.replaceAll("_", "\n");
                                            } catch (Exception ex) {
                                                return;
                                            }

                                            boolean loop2 = true;
                                            String studentID = "";
                                            String student = JOptionPane.showInputDialog(null,
                                                    "Pick a student:\n" + printStudents,
                                                    "Discussion Board", JOptionPane.INFORMATION_MESSAGE);
                                            outputStream.println(student);

                                            String allStudentPosts = "";
                                            try {
                                                allStudentPosts = inputStream.readLine();
                                                allStudentPosts = allStudentPosts.replaceAll("_", "\n");
                                            } catch (Exception ex) {
                                                return;
                                            }

                                            loop2 = true;
                                            int grade = 0;
                                            do {

                                                System.out.println("Enter a grade (0-100)");
                                                try {
                                                    grade = Integer.parseInt(JOptionPane.showInputDialog(null,
                                                            allStudentPosts + "Enter a grade (0-100)",
                                                            "Discussion Board", JOptionPane.PLAIN_MESSAGE));
                                                    if (grade < 0 || grade > 100) {
                                                        JOptionPane.showMessageDialog(null,
                                                                "Invalid integer", "Discussion Board",
                                                                JOptionPane.ERROR_MESSAGE);
                                                    } else {
                                                        loop2 = false;
                                                    }
                                                } catch (NumberFormatException ex) {
                                                    JOptionPane.showMessageDialog(null,
                                                            "Enter an integer", "Discussion Board",
                                                            JOptionPane.ERROR_MESSAGE);
                                                }
                                            } while (loop2);
                                            outputStream.println(grade);
                                        } else {
                                            outputStream.println("3_student");
                                            String showMessage = "";
                                            try {
                                                showMessage = inputStream.readLine();
                                            } catch (IOException ex) {
                                                return;
                                            }
                                            JOptionPane.showMessageDialog(null,
                                                    showMessage, "Discussion Board",
                                                    JOptionPane.INFORMATION_MESSAGE);
                                        }
                                        break;
                                    case 3:
                                        if (teacher) {
                                            outputStream.println("4");
                                            String filename = JOptionPane.showInputDialog(null,
                                                    "Enter the title of the post", "Discussion Board",
                                                    JOptionPane.INFORMATION_MESSAGE);
                                            if (filename == null) {
                                                return;
                                            }
                                            System.out.println("Enter the course name");
                                            String course = JOptionPane.showInputDialog(null,
                                                    "Enter the course name", "Discussion Board",
                                                    JOptionPane.INFORMATION_MESSAGE);
                                            String enterUsername = JOptionPane.showInputDialog(null,
                                                    "Enter your username", "Discussion Board",
                                                    JOptionPane.INFORMATION_MESSAGE);
                                            outputStream.println(filename);
                                            outputStream.println(course);
                                            outputStream.println(enterUsername);

                                            if (course == null) {
                                                return;
                                            }
                                        } else {
                                            JOptionPane.showMessageDialog(null,
                                                    "Permission not granted", "Discussion Board",
                                                    JOptionPane.ERROR_MESSAGE);
                                        }
                                        break;
                                    default:
                                        JOptionPane.showMessageDialog(null, "Invalid input",
                                                "Discussion Board", JOptionPane.ERROR_MESSAGE);
                                        break;
                                }
                            } else {
                                outputStream.println("else");
                                outputStream.println(choice);
                                int curatedPostsSize;
                                String userName;
                                ArrayList<Post> curatedPosts = new ArrayList<>();
                                for (int i = discussionPosts.size() - 1; 0 <= i; i--) {
                                    String course = discussionPosts.get(i).getCourse();
                                    if (response.equals(course) || response.equals("all")) {
                                        curatedPosts.add(discussionPosts.get(i));
                                    }
                                }
                                try {
                                    curatedPostsSize = Integer.parseInt(inputStream.readLine());
                                    userName = inputStream.readLine();
                                } catch (Exception ex) {
                                    return;
                                }
                                if (choice <= curatedPostsSize) {
                                    secondaryMenu(curatedPosts.get(choice - 1), teacher, userName);
                                } else {
                                    JOptionPane.showMessageDialog(null, "Invalid input",
                                            "Discussion Board", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "No letters are permitted!",
                                    "Discussion Board", JOptionPane.ERROR_MESSAGE);
                        }
                    } while (loop);
                } while (!loop1);
            }

            if (e.getSource() == all) {
                outputStream.println(all);
                int choice = 0;
                boolean loop = true;
                boolean loop1 = false;
                ArrayList<String> logins = data.getLoginFile();
                ArrayList<String> grades = data.getGrades();
                String identification = "";


                do {
                    for (int i = discussionPosts.size() - 1; 0 <= i; i--) {
                        String course = discussionPosts.get(i).getCourse();
                        if (!courses.contains(course)) {
                            courses.add(course);
                        }
                    }

                    ArrayList<Post> curatedPosts = new ArrayList<>();
                    for (int i = discussionPosts.size() - 1; 0 <= i; i--) {
                        String course = discussionPosts.get(i).getCourse();
                            curatedPosts.add(discussionPosts.get(i));
                    }
                    do {
                        ArrayList<String> numPlacer = new ArrayList<>();
                        String output = "";
                        for (int i = curatedPosts.size() - 1; i >= 0; i--) {
                            numPlacer.add(i + 1 + ") " + curatedPosts.get(i).toString());
                        }
                        for (String s : numPlacer) {
                            output = output + s + "\n";

                        }

                        try {
                            String choiceString = JOptionPane.showInputDialog(null,
                                    output + "\n\nEnter the number of the post to view more details" +
                                            "\nEnter 0 to see advanced options", "Discussion Board",
                                    JOptionPane.PLAIN_MESSAGE);
                            if (choiceString == null) {
                                return;
                            }
                            choice = Integer.parseInt(choiceString);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Enter a number!",
                                    "Discussion Board", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        try {
                            String optionShower = "";
                            boolean invalidChoice = false;
                            int secondChoice = 0;
                            if (choice == 0) {
                                do {
                                    optionShower = "0) Back to post\n1) Exit to course list\n2) Exit";
                                    if (teacher) {
                                        optionShower = optionShower + "\n3) Grade Student\n4) Create new discussionPost";
                                    } else {
                                        optionShower = optionShower + "\n 3) See your grade";
                                    }
                                    try {
                                        String secondChStr = JOptionPane.showInputDialog(null,
                                                optionShower, "Discussion Board",
                                                JOptionPane.PLAIN_MESSAGE);
                                        if (secondChStr == null) {
                                            return;
                                        }

                                        secondChoice = Integer.parseInt(secondChStr);
                                        invalidChoice = false;
                                    } catch (NumberFormatException ex) {
                                        invalidChoice = true;
                                        JOptionPane.showMessageDialog(null, "Enter a number!",
                                                "Discussion Board", JOptionPane.ERROR_MESSAGE);
                                        return;
                                    }
                                } while (invalidChoice);

                                switch (secondChoice) {
                                    case 0:
                                        break;
                                    case 1:
                                        JOptionPane.showMessageDialog(null,
                                                "Going back to the main course list...", "Discussion Board",
                                                JOptionPane.INFORMATION_MESSAGE);
                                        loop = false;
                                        loop1 = true;
                                        break;
                                    case 2:
                                        loop = false;
                                        loop1 = false;
                                        return;
                                    //break;
                                    case 3:
                                        String printStudents = "";
                                        if (teacher) {
                                            ArrayList<String> students = new ArrayList<>();
                                            for (int i = 0; i < logins.size(); i++) {
                                                String[] login = logins.get(i).split(";");
                                                if (login[2].equals("student")) {
                                                    printStudents = printStudents + login[0] + "\n";
                                                    students.add(logins.get(i) + ";" + i);
                                                }
                                            }

                                            boolean loop2 = true;
                                            String studentID = "";
                                            String student = JOptionPane.showInputDialog(null,
                                                    "Pick a student:\n" + printStudents,
                                                    "Discussion Board", JOptionPane.INFORMATION_MESSAGE);
                                            do {
                                                for (int i = 0; i < students.size(); i++) {
                                                    if (students.get(i).split(";")[0].equals(student)) {
                                                        loop2 = false;
                                                        studentID = students.get(i);
                                                    }
                                                }
                                            } while (loop2);

                                            String allStudentPosts = "All posts by " + student + "\n";
                                            for (int i = 0; i < discussionPosts.size(); i++) {
                                                for (int ii = 0; ii < discussionPosts.get(i).getComments().size(); ii++) {
                                                    if (discussionPosts.get(i).getComments().get(ii).
                                                            getPoster().equals(student)) {
                                                        allStudentPosts = allStudentPosts + discussionPosts.get(i).
                                                                getComments().get(ii).toString() + "\n";
                                                    }
                                                }
                                            }

                                            loop2 = true;
                                            int grade = 0;
                                            do {

                                                System.out.println("Enter a grade (0-100)");
                                                try {
                                                    grade = Integer.parseInt(JOptionPane.showInputDialog(null,
                                                            allStudentPosts + "Enter a grade (0-100)",
                                                            "Discussion Board", JOptionPane.PLAIN_MESSAGE));
                                                    if (grade < 0 || grade > 100) {
                                                        JOptionPane.showMessageDialog(null,
                                                                "Invalid integer", "Discussion Board",
                                                                JOptionPane.ERROR_MESSAGE);
                                                    } else {
                                                        loop2 = false;
                                                    }
                                                } catch (NumberFormatException ex) {
                                                    JOptionPane.showMessageDialog(null,
                                                            "Enter an integer", "Discussion Board",
                                                            JOptionPane.ERROR_MESSAGE);
                                                }
                                            } while (loop2);

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
                                        } else {
                                            boolean once = true;
                                            for (int i = 0; i < grades.size(); i++) {
                                                String[] grade = grades.get(i).split(";");
                                                String[] idBits = identification.split(";");
                                                if (idBits[0].equals(grade[0])) {
                                                    JOptionPane.showMessageDialog(null,
                                                            "Your grade is " + grade[1], "Discussion Board",
                                                            JOptionPane.INFORMATION_MESSAGE);
                                                    once = false;
                                                }
                                            }
                                            if (once)
                                                JOptionPane.showMessageDialog(null,
                                                        "You have not been graded yet", "Discussion Board",
                                                        JOptionPane.INFORMATION_MESSAGE);
                                        }
                                        break;
                                    case 4:
                                        if (teacher) {
                                            String filename = JOptionPane.showInputDialog(null,
                                                    "Enter the title of the post", "Discussion Board",
                                                    JOptionPane.INFORMATION_MESSAGE);
                                            if (filename == null) {
                                                return;
                                            }
                                            String course = JOptionPane.showInputDialog(null,
                                                    "Enter the course name", "Discussion Board",
                                                    JOptionPane.INFORMATION_MESSAGE);
                                            if (course == null) {
                                                return;
                                            }
                                            Post p = new Post(filename, loginPopUp, course, (discussionPosts.size() + ";"));

                                            discussionPosts.add(p);
                                            curatedPosts.add(p);
                                        } else {
                                            JOptionPane.showMessageDialog(null,
                                                    "Permission not granted", "Discussion Board",
                                                    JOptionPane.ERROR_MESSAGE);
                                        }
                                        break;
                                    default:
                                        JOptionPane.showMessageDialog(null, "Invalid input",
                                                "Discussion Board", JOptionPane.ERROR_MESSAGE);
                                        break;
                                }
                            } else {
                                if (choice <= curatedPosts.size()) {
                                    secondaryMenu(curatedPosts.get(choice - 1), teacher, loginPopUp);
                                } else {
                                    JOptionPane.showMessageDialog(null, "Invalid input",
                                            "Discussion Board", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "No letters are permitted!",
                                    "Discussion Board", JOptionPane.ERROR_MESSAGE);
                        }
                    } while (loop);
                } while (loop1);
                data.createPostFile(discussionPosts);
            }
        }
    };

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Menus menusMain = new Menus();
            ArrayList<String> logins = data.getLoginFile();
            String loginPassword = "";
            String truePassword = "";
            String identification = "";
            discussionPosts = data.readPostFile();
            ArrayList<String> grades = data.getGrades();
            String role;
            boolean loop1 = false;
            boolean loopAgain = false;
            boolean newAccountLoop = false;
            String newUsername = "";
            String newPassword = "";


            if (e.getSource() == login) {
                String usernameSuccess = "";
                do {
                    loginPopUp = JOptionPane.showInputDialog(null, "Enter your username",
                            "Discussion Board", JOptionPane.INFORMATION_MESSAGE);
                    invalidLogin = false;
                    if (loginPopUp == null) {
                        return;
                    }
                    if (loginPopUp.contains(";")) {
                        invalidLogin = true;
                        JOptionPane.showMessageDialog(null, "No ; are permitted " +
                                "in a username!", "Discussion Board", JOptionPane.ERROR_MESSAGE);
                    } else {
                        invalidLogin = true;
                        outputStream.println(loginPopUp + "_loginUsername");
                        try {
                            usernameSuccess = (String) inputStream.readLine();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "An unexpected error has " +
                                    "happened!", "Discussion Board", JOptionPane.ERROR_MESSAGE);
                            ex.printStackTrace();
                            return;
                        }
                        if (!usernameSuccess.equals("username success")) {
                            JOptionPane.showMessageDialog(null, "Username not found",
                                    "Discussion Board", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } while (!usernameSuccess.equals("username success"));

                do {
                    try {
                        loginPassword = JOptionPane.showInputDialog(null, "Enter your password",
                                "Discussion Board", JOptionPane.INFORMATION_MESSAGE);
                        if(loginPassword == null) {
                            return;
                        }
                        outputStream.println(loginPassword);
                        //outputStream.flush();
                        String passwordChecker = (String) inputStream.readLine();
                        if (passwordChecker.equals("password success")) {
                            invalidLogin = false;
                        } else {
                            invalidLogin = true;
                            JOptionPane.showMessageDialog(null, "Incorrect Password. " +
                                    "Please try again.", "Discussion Board", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception ex) {
                        invalidLogin = true;
                        JOptionPane.showMessageDialog(null, "There was an error in reading your " +
                                "password! Try again!", "Discussion Board", JOptionPane.ERROR_MESSAGE);
                    }
                } while (invalidLogin);

                do {
                    try {
                        JOptionPane.showMessageDialog(null, "Success!",
                                "Discussion Board", JOptionPane.INFORMATION_MESSAGE);
                        loopAgain = false;
                        /*
                        if(option == JOptionPane.YES_OPTION) {
                            loopAgain = false;
                            outputStream.println(JOptionPane.YES_OPTION);
                        } else {
                            outputStream.println(JOptionPane.NO_OPTION);
                            return;
                        }
                         */
                        String teacherOrNot;
                        try {
                            teacherOrNot = inputStream.readLine();
                        } catch (IOException ex) {
                            return;
                        }

                        if (teacherOrNot.equals("teacher is true")) {
                            teacher = true;
                        } else {
                            teacher = false;
                        }
                    } catch (NumberFormatException ex) {
                        loopAgain = true;
                        JOptionPane.showMessageDialog(null, "There was an unexpected formatting error! " +
                                "Please try again!", "Discussion Board", JOptionPane.ERROR_MESSAGE);
                    }
                } while (loopAgain);
                closeLogin.dispose();
                menusMain.mainScreen();
            }

            if (e.getSource() == newAccount) {
                //create username for new account
                newAccountLoop = false;
                do {
                    loginPopUp = JOptionPane.showInputDialog(null, "Create your username",
                            "Discussion Board", JOptionPane.INFORMATION_MESSAGE);
                    if (loginPopUp == null) {
                        return;
                    }
                    if (loginPopUp.equals("") || loginPopUp.contains(";")) {
                        newAccountLoop = true;
                        JOptionPane.showMessageDialog(null, "No ; are permitted " +
                                "in a username and usernames cannot be blank!",
                                "Discussion Board", JOptionPane.ERROR_MESSAGE);
                    } else {
                        newAccountLoop = false;
                    }

                    outputStream.println(loginPopUp + "_newAccount");
                    //outputStream.flush();
                    String userNameCreation;
                    try {
                        userNameCreation = (String) inputStream.readLine();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "There was an error reading from " +
                                "the server!", "Discussion Board", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (!userNameCreation.equals("username already exists")) {
                        newAccountLoop = false;
                    } else {
                        newAccountLoop = true;
                        JOptionPane.showMessageDialog(null, "Username is already taken. " +
                                "Please enter a new one", "Discussion Board", JOptionPane.ERROR_MESSAGE);
                    }
                } while (newAccountLoop);

                //create password for new account
                do {
                    try {
                        loginPassword = JOptionPane.showInputDialog(null, "Create your password",
                                "Discussion Board", JOptionPane.INFORMATION_MESSAGE);
                        if(loginPassword == null) {
                            return;
                        }
                        if (loginPassword.contains(";") || loginPassword.equals("")) {
                            newAccountLoop = true;
                            JOptionPane.showMessageDialog(null, "Password cannot be blank or " +
                                    "contain a semicolon(;) Try again",
                                    "Discussion Board", JOptionPane.ERROR_MESSAGE);
                        } else {
                            newAccountLoop = false;
                        }
                    } catch (Exception ex) {
                        newAccountLoop = true;
                        JOptionPane.showMessageDialog(null, "There was an error in creating a " +
                                "new password! Try again.", "Discussion Board", JOptionPane.ERROR_MESSAGE);
                    }
                } while (newAccountLoop);
                try {
                    outputStream.println(loginPassword);
                    //outputStream.flush();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "There was an issue sending your data" +
                            " to the server!", "Discussion Board", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                    return;
                }

                //user identifies as either student or teacher
                role = JOptionPane.showInputDialog(null, "Are you a student or a teacher?",
                        "Discussion Board", JOptionPane.INFORMATION_MESSAGE);
                if (role == null) {
                    return;
                } else {
                    role = role.toLowerCase();
                }
                try {
                    outputStream.println(role);
                    //outputStream.flush();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "There was an error sending data " +
                            "to the server!", "Discussion Board", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                    return;
                }

                if (role.equals("teacher")) {
                    teacher = true;
                } else {
                    teacher = false;
                }

                do {
                    try {
                        JOptionPane.showMessageDialog(null, "Success!", "Discussion Board",
                                JOptionPane.INFORMATION_MESSAGE);
                        loopAgain = false;
                        String teacherOrNot1;
                        try {
                            teacherOrNot1 = inputStream.readLine();
                        } catch (IOException ex) {
                            return;
                        }

                        if (teacherOrNot1.equals("teacher is true")) {
                            teacher = true;
                        } else {
                            teacher = false;
                        }
                    } catch (NumberFormatException ex) {
                        loopAgain = true;
                        JOptionPane.showMessageDialog(null, "There was an unexpected formatting error! " +
                                "Please try again!", "Discussion Board", JOptionPane.ERROR_MESSAGE);
                    }
                } while (loopAgain);
                closeLogin.dispose();
                menusMain.mainScreen();
            }

            if (e.getSource() == edit) {
                String continuer = "";
                do {
                    loginPopUp = JOptionPane.showInputDialog(null, "Enter your username",
                            "Discussion Board", JOptionPane.INFORMATION_MESSAGE);
                    invalidLogin = false;
                    if (loginPopUp == null) {
                        return;
                    }
                    if (loginPopUp.contains(";") || loginPopUp.equals("")) {
                        invalidLogin = true;
                        JOptionPane.showMessageDialog(null, "No ; or blanks are permitted " +
                                "in a username!", "Discussion Board", JOptionPane.ERROR_MESSAGE);
                    } else {
                        try {
                            outputStream.println(loginPopUp + "_edit");
                            //outputStream.flush();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Error sending data to server",
                                    "Discussion Board", JOptionPane.ERROR_MESSAGE);
                            ex.printStackTrace();
                            return;
                        }
                        try {
                            continuer = (String) inputStream.readLine();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "There was an error reading " +
                                    "data from the server.", "Discussion Board", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        if (continuer.equals("username not found")) {
                            invalidLogin = true;
                            JOptionPane.showMessageDialog(null, "Username not found",
                                    "Discussion Board", JOptionPane.ERROR_MESSAGE);
                        }

                    }
                } while (invalidLogin);
                invalidLogin = true;

                do {
                    try {
                        loginPassword = JOptionPane.showInputDialog(null, "Enter your password",
                                "Discussion Board", JOptionPane.INFORMATION_MESSAGE);
                        if(loginPassword == null) {
                            return;
                        }
                        try {
                            if (continuer.equals(loginPassword)) {
                                invalidLogin = false;
                            } else {
                                invalidLogin = true;
                                JOptionPane.showMessageDialog(null, "Incorrect Password. " +
                                        "Please try again.", "Discussion Board", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "There was an error " +
                                    "sending data to server.", "Discussion Board", JOptionPane.ERROR_MESSAGE);
                            ex.printStackTrace();
                            return;
                        }
                    } catch (Exception ex) {
                        invalidLogin = true;
                        JOptionPane.showMessageDialog(null, "There was an error in reading your " +
                                "password! Try again!", "Discussion Board", JOptionPane.ERROR_MESSAGE);
                    }
                } while (invalidLogin);

                //enter new username
                do {
                    newUsername = JOptionPane.showInputDialog(null, "Create your new username",
                            "Discussion Board", JOptionPane.INFORMATION_MESSAGE);
                    if (newUsername == null) {
                        return;
                    }
                    if (newUsername.contains(";") || newUsername.equals("")) {
                        invalidLogin = true;
                        JOptionPane.showMessageDialog(null, "No ; or blanks are permitted " +
                                "in a username! Try again.", "Discussion Board", JOptionPane.ERROR_MESSAGE);
                    } else {
                        outputStream.println(newUsername);
                        String validNewUsername;
                        try {
                            validNewUsername = (String) inputStream.readLine();
                            if (validNewUsername.equals("username already taken")) {
                                invalidLogin = true;
                                JOptionPane.showMessageDialog(null, "Username is already taken.",
                                        "Discussion Board", JOptionPane.ERROR_MESSAGE);
                            } else {
                                invalidLogin = false;
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "There was an error reading data " +
                                    "from server!", "Discussion Board", JOptionPane.ERROR_MESSAGE);
                            ex.printStackTrace();
                            return;
                        }
                    }
                } while (invalidLogin);

                //enter new password
                do {
                    newPassword = JOptionPane.showInputDialog(null, "Create your new" +
                            " password", "Discussion Board", JOptionPane.INFORMATION_MESSAGE);
                    if(newPassword == null) {
                        return;
                    }
                    if (newPassword.contains(";") || newPassword.equals("")) {
                        JOptionPane.showMessageDialog(null, "Password cannot be blank or " +
                                "contain semicolon(;). Please try again.", "Discussion Board",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } while (newPassword.contains(";") || newPassword.equals(""));
                try {
                    outputStream.println(newPassword);
                    //outputStream.flush();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "There was an error sending data " +
                            "to the server!", "Discussion Board", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                    return;
                }

                try {
                    JOptionPane.showMessageDialog(null, "Success! Account edited!",
                            "Discussion Board", JOptionPane.INFORMATION_MESSAGE);

                    String teacherOrNot2 = "";
                    try {
                        teacherOrNot2 = inputStream.readLine();
                    } catch (IOException ex) {
                        return;
                    }
                    if (teacherOrNot2.equals("teacher is true")) {
                        teacher = true;
                    } else {
                        teacher = false;
                    }
                } catch (NumberFormatException ex) {
                    loopAgain = true;
                    JOptionPane.showMessageDialog(null, "There was an unexpected formatting error! " +
                            "Please try again!", "Discussion Board", JOptionPane.ERROR_MESSAGE);
                }
                closeLogin.dispose();
                menusMain.mainScreen();
            }

            if (e.getSource() == delete) {
                String reader = "";
                do {
                    loginPopUp = JOptionPane.showInputDialog(null, "Enter your username",
                            "Discussion Board", JOptionPane.INFORMATION_MESSAGE);
                    if (loginPopUp == null) {
                        return;
                    }
                    if (loginPopUp.equals("")) {
                        invalidLogin = true;
                        JOptionPane.showMessageDialog(null, "Username cannot be blank.",
                                "Discussion Board", JOptionPane.ERROR_MESSAGE);
                    } else {
                        try {
                            outputStream.println(loginPopUp + "_delete");
                            //outputStream.flush();
                            reader = (String) inputStream.readLine();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "There was an error sending " +
                                    "data to the server!", "Discussion Board", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        if (!reader.equals("username found")) {
                            JOptionPane.showMessageDialog(null, "Username not found",
                                    "Discussion Board", JOptionPane.ERROR_MESSAGE);
                            invalidLogin = true;
                        } else {
                            invalidLogin = false;
                        }
                    }
                } while (invalidLogin);
                invalidLogin = true;

                do {
                    try {
                        loginPassword = JOptionPane.showInputDialog(null, "Enter your password",
                                "Discussion Board", JOptionPane.INFORMATION_MESSAGE);
                        if(loginPassword == null) {
                            return;
                        }
                        String pass = "";
                        try {
                            outputStream.println(loginPassword);
                            //outputStream.flush();
                            pass = (String) inputStream.readLine();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "There was an error sending " +
                                    "data to server.", "Discussion Board", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        if (pass.equals(loginPassword)) {
                            invalidLogin = false;
                            JOptionPane.showMessageDialog(null, "Success!",
                                    "Discussion Board", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            invalidLogin = true;
                            JOptionPane.showMessageDialog(null, "Incorrect Password. " +
                                    "Please try again.", "Discussion Board", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception ex) {
                        invalidLogin = true;
                        JOptionPane.showMessageDialog(null, "There was an error in reading your " +
                                "password! Try again!", "Discussion Board", JOptionPane.ERROR_MESSAGE);
                    }
                } while (invalidLogin);
            }
        }
    };

    public void run() {
        JFrame frame = new JFrame("Discussion Board");
        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());

        //center
        JPanel topPanel = new JPanel();
        login = new JButton("Click to Login");
        login.addActionListener(actionListener);
        newAccount = new JButton("Make New Acct");
        newAccount.addActionListener(actionListener);
        edit = new JButton("Edit Account");
        edit.addActionListener(actionListener);
        delete = new JButton("Delete Account");
        delete.addActionListener(actionListener);
        topPanel.add(login);
        topPanel.add(newAccount);
        topPanel.add(edit);
        topPanel.add(delete);
        content.add(topPanel, BorderLayout.CENTER);

        //

        //set visible
        //frame.setSize(900, 600);
        frame.setSize(700, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        closeLogin = frame;
        frame.setVisible(true);
    }

    //method that displays main screen GUI after login
    public void mainScreen() {
        mainFrame = new JFrame("Discussion Board");
        Container mainContent = mainFrame.getContentPane();
        mainContent.setLayout(new BorderLayout());

        String courseStuff = "";
        for (int i = discussionPosts.size() - 1; 0 <= i; i--) {
            String course = discussionPosts.get(i).getCourse();
            courseStuff = course + "\n";
            if (!courses.contains(course)) {
                courses.add(course);
            }
        }

        //center-north
        JPanel mainPanel = new JPanel();
        JLabel courseList = new JLabel("Welcome to Discussion Board!");
        mainPanel.add(courseList);
        mainContent.add(mainPanel, BorderLayout.CENTER);

        //center-south
        JPanel southPanelMain = new JPanel();
        logout = new JButton("Click to Logout");
        all = new JButton("View All Courses");
        specificCourse = new TextField(10);
        specificCourse.setText("Enter a course name here!");
        specificBtn = new JButton("Click to find course info");
        logout.addActionListener(actionListenerMain);
        all.addActionListener(actionListenerMain);
        specificBtn.addActionListener(actionListenerMain);
        southPanelMain.add(logout);
        southPanelMain.add(all);
        southPanelMain.add(specificCourse);
        southPanelMain.add(specificBtn);
        mainContent.add(southPanelMain, BorderLayout.SOUTH);

        //set visible
        mainFrame.setSize(700, 500);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setVisible(true);
    }

    public static void secondaryMenu(Post post, boolean teacher, String username) {
        boolean loop = true;
        JOptionPane.showMessageDialog(null, "Click ok to continue and select what you would " +
                "like to do with this post.", "Discussion Board", JOptionPane.INFORMATION_MESSAGE);
        do {
            int reader = 0;
            String printOptions = post.toString() + "\n0) exit to all posts\n1) view comments\n2) leave a comment";
            if (teacher) {
                printOptions = printOptions + "\n3) edit post";
            }
            String optionSelect = JOptionPane.showInputDialog(null, printOptions,
                    "Discussion Board", JOptionPane.INFORMATION_MESSAGE);
            if (optionSelect == null) {
                return;
            } else {
                try {
                    reader = Integer.parseInt(optionSelect);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Enter a number!",
                            "Discussion Board", JOptionPane.ERROR_MESSAGE);
                    break;
                }
            }
            switch (reader) {
                case 0:
                    loop = false;
                    break;
                case 1:
                    String commentList = "";
                    for (int i = post.getComments().size() - 1; 0 <= i; i--) {
                        commentList = commentList + i + 1 + ") " + post.getComments().get(i).toString() + "\n";
                    }

                    commentList = commentList + "\n\nWhat would you like to do with these replies";
                    commentList = commentList + "\n0) back\nor enter the number of the reply";
                    int choice = 0;
                    try {
                        choice = Integer.parseInt(JOptionPane.showInputDialog(null, commentList,
                                "Discussion Board", JOptionPane.INFORMATION_MESSAGE));
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Only numbers are permitted!",
                                "Discussion Board", JOptionPane.ERROR_MESSAGE);
                        break;
                    }

                    if (choice != 0) {
                        if (choice <= post.getComments().size()) {
                            secondaryMenu(post.getComments().get(choice - 1), teacher, username);
                        } else {
                            JOptionPane.showMessageDialog(null, "Invalid input",
                                    "Discussion Board", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    break;
                case 2:
                    int selection = 0;
                    boolean continuer = false;
                    do {
                        try {
                            String selections = JOptionPane.showInputDialog(null, "Would you like to use a " +
                                    "1) string or a 2) file", "Discussion Board", JOptionPane.INFORMATION_MESSAGE);
                            if (selections == null) {
                                return;
                            } else {
                                selection = Integer.parseInt(selections);
                            }
                        } catch (NumberFormatException e) {
                            continuer = true;
                            JOptionPane.showMessageDialog(null, "Only numbers are permitted!",
                                    "Discussion Board", JOptionPane.ERROR_MESSAGE);
                        }
                    } while (continuer);
                    StringBuilder bodytext = new StringBuilder();
                    switch (selection) {
                        case 1:
                            String line2 = JOptionPane.showInputDialog(null, "Enter the comment.",
                                    "Discussion Board", JOptionPane.PLAIN_MESSAGE);
                            ArrayList<String> input1 = new ArrayList<>();
                            input1.add(line2);
                            /*
                            System.out.println("Enter the comment. Write 'X' on a new line to end");
                            ArrayList<String> input1 = new ArrayList<>();
                            String line2 = s.nextLine();
                            while (line2 != null && !line2.equals("X")) {
                                input1.add(line2);
                                line2 = s.nextLine();
                            }
                             */
                            post.comment(String.join("\n", input1), username);
                            break;
                        case 2:
                            //System.out.println("Enter the path to the file with the " +
                                    //"content without the name of the file");
                            //System.out.println("For example, write 'C:\\USER\\DATA' if file is in DATA folder");
                            String dirName = JOptionPane.showInputDialog(null, "Enter the path" +
                                    " to the file with the content without the name of the file.\n For example, " +
                                    "write 'C:\\USER\\DATA' if file is in DATA folder.", "Discussion Board",
                                    JOptionPane.INFORMATION_MESSAGE);
                            File dir = new File(dirName);
                            if (!dir.exists()) {
                                JOptionPane.showMessageDialog(null, "Directory not found",
                                        "Discussion Board", JOptionPane.ERROR_MESSAGE);
                                break;
                            }
                            String file = JOptionPane.showInputDialog(null, "Directory found!" +
                                    "\nEnter the filename with '.txt' suffix. For example, write 'sample.txt'",
                                    "Discussion Board", JOptionPane.INFORMATION_MESSAGE);
                            File f = new File(dir, file);
                            if (f.exists()) {
                                try (BufferedReader bfr = new BufferedReader(new FileReader(f))) {
                                    String line = bfr.readLine();
                                    while (line != null) {
                                        bodytext.append(line).append("\n");
                                        line = bfr.readLine();
                                    }
                                } catch (IOException e) {
                                    JOptionPane.showMessageDialog(null, "There was an " +
                                            "IOException!", "Discussion Board", JOptionPane.ERROR_MESSAGE);
                                    e.printStackTrace();
                                }
                                post.comment(bodytext.toString(), username);
                            } else {
                                JOptionPane.showMessageDialog(null, "Invalid file name",
                                        "Discussion Board", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                    }
                    break;
                case 3:
                    if (teacher) {
                        int select = 0;
                        try {
                            String question = JOptionPane.showInputDialog(null, "Would you like" +
                                    " to use a 1) string or a 2) file", "Discussion Board",
                                    JOptionPane.QUESTION_MESSAGE);
                            if (question == null) {
                                break;
                            } else {
                                select = Integer.parseInt(question);
                            }
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Only numbers are permitted!",
                                    "Discussion Board", JOptionPane.ERROR_MESSAGE);
                            break;
                        }
                        bodytext = new StringBuilder();
                        switch (select) {
                            case 1:
                                String line2 = JOptionPane.showInputDialog(null, "Enter the body text.",
                                        "Discussion Board", JOptionPane.PLAIN_MESSAGE);
                                ArrayList<String> input1 = new ArrayList<>();
                                input1.add(line2);
                                post.setBodyText(String.join("\n", input1));
                                break;
                            /*
                            System.out.println("Enter the comment. Write 'X' on a new line to end");
                            ArrayList<String> input1 = new ArrayList<>();
                            String line2 = s.nextLine();
                            while (line2 != null && !line2.equals("X")) {
                                input1.add(line2);
                                line2 = s.nextLine();
                            }
                             */
                            case 2:
                                String dirName = JOptionPane.showInputDialog(null, "Enter the path" +
                                                " to the file with the content without the name of the file.\n For example, " +
                                                "write 'C:\\USER\\DATA' if file is in DATA folder.", "Discussion Board",
                                        JOptionPane.INFORMATION_MESSAGE);
                                File dir = new File(dirName);
                                if (!dir.exists()) {
                                    JOptionPane.showMessageDialog(null, "Directory not found",
                                            "Discussion Board", JOptionPane.ERROR_MESSAGE);
                                    break;
                                }
                                String file = JOptionPane.showInputDialog(null, "Directory found!" +
                                                "\nEnter the filename with '.txt' suffix. For example, write 'sample.txt'",
                                        "Discussion Board", JOptionPane.INFORMATION_MESSAGE);
                                File f = new File(dir, file);
                                if (f.exists()) {
                                    try (BufferedReader bfr = new BufferedReader(new FileReader(f))) {
                                        String line = bfr.readLine();
                                        while (line != null) {
                                            bodytext.append(line).append("\n");
                                            line = bfr.readLine();
                                        }
                                    } catch (IOException e) {
                                        JOptionPane.showMessageDialog(null, "An " +
                                                "IOException occured!", "Discussion Board", JOptionPane.ERROR_MESSAGE);
                                        e.printStackTrace();
                                    }
                                    post.setBodyText(bodytext.toString());
                                } else {
                                    JOptionPane.showMessageDialog(null, "Invalid file name",
                                            "Discussion Board", JOptionPane.ERROR_MESSAGE);
                                    //System.out.println("Invalid file name");
                                }
                                break;
                        }
                    } else
                        JOptionPane.showMessageDialog(null, "Permission not granted",
                                "Discussion Board", JOptionPane.ERROR_MESSAGE);
                        //System.out.println("Permission not granted");
                    break;
            }

        } while (loop);
    }
}
