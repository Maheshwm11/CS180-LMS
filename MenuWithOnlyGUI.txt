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

    //client socket
    static Socket socket;
    static ObjectInputStream inputStream;
    static ObjectOutputStream outputStream;




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
                    String response = specificCourse.getText();
                    if (!courses.contains(response)) {
                        JOptionPane.showMessageDialog(null, "Invalid choice.", "Discussion Board",
                                JOptionPane.ERROR_MESSAGE);
                        return;
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
                                            System.out.println("Enter the course name");
                                            String course = JOptionPane.showInputDialog(null,
                                                    "Enter the course name", "Discussion Board",
                                                    JOptionPane.INFORMATION_MESSAGE);
                                            Post p = new Post(filename, loginPopUp, course, (discussionPosts.size() + ";"));
                                            if (course == null) {
                                                return;
                                            }

                                            discussionPosts.add(p);
                                            if (course.equals(response) || response.equals("all")) {
                                                curatedPosts.add(p);
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

            if (e.getSource() == all) {
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
            //String loginPopUp = "";
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
                            JOptionPane.showMessageDialog(null, "Username not found",
                                    "Discussion Board", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } while (invalidLogin);

                do {
                    try {
                        loginPassword = JOptionPane.showInputDialog(null, "Enter your password",
                                "Discussion Board", JOptionPane.INFORMATION_MESSAGE);
                        if(loginPassword == null) {
                            return;
                        }
                        if (!truePassword.equals("") && truePassword.equals(loginPassword)) {
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
            }

            do {
                try {
                    int option = JOptionPane.showConfirmDialog(null, "Success! Would you like " +
                                    "to continue?",
                            "Discussion Board", JOptionPane.YES_NO_OPTION);
                    if(option == JOptionPane.YES_OPTION) {
                        loopAgain = false;
                    } else {
                        return;
                    }
                } catch (NumberFormatException ex) {
                    loopAgain = true;
                    JOptionPane.showMessageDialog(null, "There was an unexpected formatting error! " +
                            "Please try again!", "Discussion Board", JOptionPane.ERROR_MESSAGE);
                }
            } while (loopAgain);
            menusMain.mainScreen();

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
                    for (String value : logins) {
                        String[] login = value.split(";");
                        if (loginPopUp.equals(login[0])) {
                            newAccountLoop = true;
                            JOptionPane.showMessageDialog(null, "Username is already taken. " +
                                    "Please enter a new one", "Discussion Board", JOptionPane.ERROR_MESSAGE);
                        }
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

                //user identifies as either student or teacher
                role = JOptionPane.showInputDialog(null, "Are you a student or a teacher?",
                        "Discussion Board", JOptionPane.INFORMATION_MESSAGE);
                if (role == null) {
                    return;
                } else {
                    role = role.toLowerCase();
                }

                if (role.equals("teacher")) {
                    teacher = true;
                    identification = loginPopUp + ";" + loginPassword + ";" + role;
                } else {
                    teacher = false;
                    identification = loginPopUp + ";" + loginPassword + ";" + role;
                }
                logins.add(identification);
                data.setLoginFile(logins);

                do {
                    try {
                        int option = JOptionPane.showConfirmDialog(null, "Success! Would you like " +
                                        "to continue?",
                                "Discussion Board", JOptionPane.YES_NO_OPTION);
                        if(option == JOptionPane.YES_OPTION) {
                            loopAgain = false;
                        } else {
                            return;
                        }
                    } catch (NumberFormatException ex) {
                        loopAgain = true;
                        JOptionPane.showMessageDialog(null, "There was an unexpected formatting error! " +
                                "Please try again!", "Discussion Board", JOptionPane.ERROR_MESSAGE);
                    }
                } while (loopAgain);
            }

            if (e.getSource() == edit) {
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
                        invalidLogin = true;
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
                        if (truePassword.equals(loginPassword)) {
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
                    }
                    for (String value : logins) {
                        String[] login = value.split(";");
                        if (newUsername.equals(login[0])) {
                            System.out.println("Username is already taken. Please enter a new one");
                            invalidLogin = true;
                            break;
                        } else
                            invalidLogin = false;
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

                //replace old login with new login
                for (int i = 0; i < logins.size(); i++) {
                    if (teacher) {
                        if (logins.get(i).equals(loginPopUp + ";" + loginPassword + ";teacher")) {
                            logins.remove(i);
                            identification = newUsername + ";" + newPassword + ";teacher";
                            logins.add(identification);
                        }
                    } else {
                        if (logins.get(i).equals(loginPopUp + ";" + loginPassword + ";student")) {
                            logins.remove(i);
                            identification = newUsername + ";" + newPassword + ";student";
                            logins.add(identification);
                        }
                    }
                }
                data.setLoginFile(logins);

                try {
                    JOptionPane.showMessageDialog(null, "Success! Account edited!",
                            "Discussion Board", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    loopAgain = true;
                    JOptionPane.showMessageDialog(null, "There was an unexpected formatting error! " +
                            "Please try again!", "Discussion Board", JOptionPane.ERROR_MESSAGE);
                }

            }
            if (e.getSource() == delete) {
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
                        for (String value : logins) {
                            String[] login = value.split(";");
                            if (loginPopUp.equals(login[0])) {
                                invalidLogin = false;
                                truePassword = login[1];
                            }
                        }
                        if (invalidLogin) {
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
                        if (truePassword.equals(loginPassword)) {
                            invalidLogin = false;
                            JOptionPane.showMessageDialog(null, "Success!",
                                    "Discussion Board", JOptionPane.ERROR_MESSAGE);
                            for (int i = 0; i < logins.size(); i++) {
                                if (logins.get(i).equals(loginPopUp + ";" + loginPassword + ";student")
                                        || logins.get(i).equals(loginPopUp + ";" + loginPassword + ";teacher")) {
                                    logins.remove(i);
                                }
                            }
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
                data.setLoginFile(logins);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Menus());

        //create socket to server
        try {
            socket = new Socket("localhost", 4242);
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "There was an error connecting to the server!",
                    "Discussion Board", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }
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
