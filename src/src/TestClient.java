import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class TestClient extends JComponent implements Runnable {

    // Client functionality
    static Socket socket;
    static ObjectOutputStream objectOutputStream;
    static ObjectInputStream objectInputStream;
    String identifier = "";
    boolean adminPerms = false;

    // All GameStates
    JButton back;
    String gameState = "login";

    // GameState login
    JButton login;
    JTextField usernameTextField;
    JTextField passwordTextField;

    // GameState accountMenu
    JButton continueToProgram;
    JButton editAccount;
    JButton deleteAccount;
    JPanel createAccount;
    JButton teacher;
    JButton student;

    // GameState editAccount
    JButton edit;
    JTextField newUsernameField;
    JTextField newPasswordField;

    // GameState discussionForum
    ArrayList<Post> curatedPosts = new ArrayList<>();
    JLabel courseChoice;
    JComboBox<Object> courseDropDown;

    // ^ Teacher Specific Options
    JButton gradeStudent;
    JButton createNewDiscussionPost;

    // ^ Student Specific Options
    JButton seeGrade;

    // GameState gradeMenu
    ArrayList<String> studentNames = new ArrayList<>();
    JComboBox<Object> studentDropDown;
    JLabel gradeLabel;
    JSlider gradeSlider;
    JButton confirmGrade;


    public static void main(String[] args) throws IOException {
        socket = new Socket("localHost",4241);
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.flush();
        objectInputStream = new ObjectInputStream(socket.getInputStream());

        SwingUtilities.invokeLater(new TestClient());
    }



    public void run() {

        // JFrame Declarations (LOGINS)
        JFrame displayLogin = new JFrame("Login");
        JFrame displayEditAccount = new JFrame("Account Editor");
        JFrame displayAccountMenu = new JFrame("Login 2");

        // JFrame Declarations (MENUS)
        JFrame displayDiscussionForum = new JFrame("Discussion Forum");
        JFrame displayGradeMenu = new JFrame("Grading Menu");
        JFrame secondaryMenu = new JFrame(identifier);


        // Login Action Listeners
        ActionListener actionListenerLogin = e -> {
            // GameState login
            if (e.getSource() == login) {
                if (!usernameTextField.getText().contains(";") && !passwordTextField.getText().contains(";")) {
                    try {
                        objectOutputStream.writeUTF(String.format("login;%s;%s", usernameTextField.getText(), passwordTextField.getText()));
                        objectOutputStream.flush();
                        System.out.println("Sent to Server: " + String.format("login;%s;%s", usernameTextField.getText(), passwordTextField.getText()));
                        String reply = objectInputStream.readUTF();

                        System.out.println("Received from server: " + reply);
                        if (reply.equals("null")) {
                            JOptionPane.showMessageDialog(null, "Username and password not recognized",
                                    "Login", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            if (reply.equals("teacher")) {
                                adminPerms = true;
                            }
                            JOptionPane.showMessageDialog(null, "Login Successful",
                                    "Login", JOptionPane.INFORMATION_MESSAGE);
                            displayLogin.dispose();
                            gameState = "accountMenu";
                            run();
                        }
                    } catch (IOException er) {
                        er.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Username and password cannot contain a semicolon (;)",
                            "Login", JOptionPane.ERROR_MESSAGE);
                }
            }

            if (e.getSource() == teacher | e.getSource() == student) {
                try {
                    String role = "student";
                    if (e.getSource() == teacher) {
                        role = "teacher";
                        adminPerms = true;
                    }
                    if (!usernameTextField.getText().contains(";") && !passwordTextField.getText().contains(";")) {
                        try {
                            objectOutputStream.writeUTF(String.format("createAccount;%s;%s;%s",
                                    usernameTextField.getText(), passwordTextField.getText(), role));
                            objectOutputStream.flush();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        System.out.println("Sent to server: " + String.format("createAccount;%s;%s;%s",
                                usernameTextField.getText(), passwordTextField.getText(), role));

                        if (objectInputStream.readUTF().equals("success")) {
                            displayLogin.dispose();
                            gameState = "accountMenu";
                            run();
                        } else {
                            JOptionPane.showMessageDialog(null, "That Username is Already in Use",
                                    "Login", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Username and password cannot contain a semicolon (;)",
                                "Login", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            // GameState accountMenu

            if (e.getSource() == continueToProgram) {
                displayAccountMenu.dispose();
                gameState = "discussionForum";
                run();
            }

            if (e.getSource() == editAccount) {
                displayAccountMenu.dispose();
                gameState = "editAccount";
                run();
            }

            if (e.getSource() == deleteAccount) {
                try {
                    objectOutputStream.writeUTF(String.format("deleteAccount;%s;%s", usernameTextField.getText(), passwordTextField.getText()));
                    objectOutputStream.flush();
                    System.out.println("Sent to Server: " + String.format("deleteAccount;%s;%s", usernameTextField.getText(), passwordTextField.getText()));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                displayAccountMenu.dispose();
                gameState = "login";
                run();
            }

            if (e.getSource() == edit) {
                try {
                    objectOutputStream.writeUTF(String.format("editAccount;%s;%s;%s;%s",usernameTextField.getText(), passwordTextField.getText(),
                            newUsernameField.getText(), newPasswordField.getText()));
                    objectOutputStream.flush();
                    System.out.println("Sent to Server: " + String.format("editAccount;%s;%s;%s;%s",usernameTextField.getText(), passwordTextField.getText(),
                            newUsernameField.getText(), newPasswordField.getText()));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                displayEditAccount.dispose();
                gameState = "login";
                run();
            }

            if (e.getSource() == back) {
                displayEditAccount.dispose();
                gameState = "login";
                run();
            }
        };

        ActionListener actionListenerDiscussionForum = e -> {

            // GameState introMenu

            if (e.getSource() == courseDropDown) {
                try {
                    objectOutputStream.writeUTF(String.format("curatePosts;%s", courseDropDown.getSelectedItem()));
                    objectOutputStream.flush();
                    System.out.println(String.format("curatePosts;%s", courseDropDown.getSelectedItem()));
                    identifier = (String) courseDropDown.getSelectedItem();
                    curatedPosts = (ArrayList<Post>) objectInputStream.readObject();
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }

            if (e.getSource() == seeGrade) {
                try {
                    objectOutputStream.writeUTF(String.format("seeGrade;%s", usernameTextField.getText()));
                    objectOutputStream.flush();
                    System.out.println("Sent to Server: " + String.format("seeGrade;%s", usernameTextField.getText()));

                    JOptionPane.showMessageDialog(null, "Your Grade is: " + objectInputStream.readUTF(),
                            "Grade", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            if (e.getSource() == gradeStudent) {
                displayDiscussionForum.dispose();
                gameState = "gradeMenu";
                run();
            }

            if (e.getSource() == createNewDiscussionPost) {
                displayDiscussionForum.dispose();
                gameState = "newPost";
                run();
            }

            // GameState gradeMenu

            if (e.getSource() == confirmGrade) {
                try {
                    objectOutputStream.writeUTF(String.format("gradeStudent;%s;%s",
                            studentDropDown.getSelectedItem(), gradeSlider.getValue()));
                    objectOutputStream.flush();

                    JOptionPane.showMessageDialog(null, "Grade Updated to: " + gradeSlider.getValue(),
                            "Login", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };

        ActionListener actionListenerBack = e -> {
            switch (gameState) {
                case "login" -> {
                    try {
                        displayLogin.dispose();
                        objectOutputStream.writeUTF("logout");
                        objectOutputStream.flush();
                        socket.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                case "accountMenu" -> {
                    displayAccountMenu.dispose();
                    gameState = "login";
                    run();
                }
                case "editAccount" -> {
                    displayEditAccount.dispose();
                    gameState = "accountMenu";
                    run();
                }
                case "discussionForum" -> {
                    displayDiscussionForum.dispose();
                    gameState = "accountMenu";
                    run();
                }
                case "gradeMenu" -> {
                    displayGradeMenu.dispose();
                    gameState = "discussionForum";
                    run();
                }
            }
        };

        // Logins
        switch (gameState) {
            case "login" -> {
                adminPerms = false;
                System.out.println("GameState login");
                Container contentLogin = displayLogin.getContentPane();
                contentLogin.setLayout(new BoxLayout(contentLogin, BoxLayout.Y_AXIS));

                login = new JButton("Login");
                login.addActionListener(actionListenerLogin);
                login.setAlignmentX(Component.CENTER_ALIGNMENT);
                contentLogin.add(login);

                if (usernameTextField == null) {
                    usernameTextField = new JTextField("Test");
                } else {
                    usernameTextField = new JTextField(usernameTextField.getText());
                }
                usernameTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
                usernameTextField.setHorizontalAlignment(JTextField.CENTER);
                contentLogin.add(usernameTextField);
                if (passwordTextField == null) {
                    passwordTextField = new JTextField("Password");
                } else {
                    passwordTextField = new JTextField(passwordTextField.getText());
                }
                passwordTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
                passwordTextField.setHorizontalAlignment(JTextField.CENTER);
                contentLogin.add(passwordTextField);

                createAccount = new JPanel();

                teacher = new JButton("Create Teacher Account");
                teacher.addActionListener(actionListenerLogin);
                teacher.setAlignmentX(Component.CENTER_ALIGNMENT);
                createAccount.add(teacher);

                student = new JButton("Create Student Account");
                student.addActionListener(actionListenerLogin);
                student.setAlignmentX(Component.CENTER_ALIGNMENT);
                createAccount.add(student);

                contentLogin.add(createAccount);

                back = new JButton("Exit Program");
                back.setAlignmentX(Component.CENTER_ALIGNMENT);
                back.addActionListener(actionListenerBack);
                contentLogin.add(back);

                displayLogin.setSize(400, 175);
                displayLogin.setLocationRelativeTo(null);
                displayLogin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                displayLogin.setVisible(true);
            }
            case "accountMenu" -> {
                System.out.println("GameState accountMenu");
                Container contentAccountMenu = displayAccountMenu.getContentPane();
                contentAccountMenu.setLayout(new BoxLayout(contentAccountMenu, BoxLayout.Y_AXIS));

                continueToProgram = new JButton("Continue to Program");
                continueToProgram.addActionListener(actionListenerLogin);
                continueToProgram.setAlignmentX(Component.CENTER_ALIGNMENT);
                contentAccountMenu.add(continueToProgram);

                editAccount = new JButton("Edit Account");
                editAccount.addActionListener(actionListenerLogin);
                editAccount.setAlignmentX(Component.CENTER_ALIGNMENT);
                contentAccountMenu.add(editAccount);

                deleteAccount = new JButton("Delete Account");
                deleteAccount.addActionListener(actionListenerLogin);
                deleteAccount.setAlignmentX(Component.CENTER_ALIGNMENT);
                contentAccountMenu.add(deleteAccount);

                back = new JButton("Back to Login");
                back.addActionListener(actionListenerBack);
                back.setAlignmentX(Component.CENTER_ALIGNMENT);
                contentAccountMenu.add(back);

                displayAccountMenu.setSize(300, 150);
                displayAccountMenu.setLocationRelativeTo(null);
                displayAccountMenu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                displayAccountMenu.setVisible(true);
            }
            case "editAccount" -> {
                Container contentEditAccount = displayEditAccount.getContentPane();
                contentEditAccount.setLayout(new BoxLayout(contentEditAccount, BoxLayout.Y_AXIS));

                edit = new JButton("Confirm Grade");
                edit.addActionListener(actionListenerLogin);
                edit.setAlignmentX(Component.CENTER_ALIGNMENT);
                contentEditAccount.add(edit);

                newUsernameField = new JTextField(usernameTextField.getText());
                newUsernameField.setAlignmentX(Component.CENTER_ALIGNMENT);
                contentEditAccount.add(newUsernameField);

                newPasswordField = new JTextField(passwordTextField.getText());
                newPasswordField.setAlignmentX(Component.CENTER_ALIGNMENT);
                contentEditAccount.add(newPasswordField);

                back = new JButton("Back");
                back.addActionListener(actionListenerBack);
                back.setAlignmentX(Component.CENTER_ALIGNMENT);
                contentEditAccount.add(back);

                displayEditAccount.setSize(300, 150);
                displayEditAccount.setLocationRelativeTo(null);
                displayEditAccount.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                displayEditAccount.setVisible(true);
            }
        }
        // Menus
        switch (gameState) {
            case "discussionForum" -> {
                int height = 150;
                Container contentDiscussionForum = displayDiscussionForum.getContentPane();
                contentDiscussionForum.setLayout(new BoxLayout(contentDiscussionForum, BoxLayout.Y_AXIS));

                courseChoice = new JLabel("Choose a course to be displayed");
                courseChoice.setAlignmentX(Component.CENTER_ALIGNMENT);
                contentDiscussionForum.add(courseChoice);

                try {
                    objectOutputStream.writeUTF("buildCourseArray");
                    objectOutputStream.flush();
                    ArrayList<String> courses = (ArrayList<String>) objectInputStream.readObject();
                    courses.add(0, "all");
                    courseDropDown = new JComboBox<>(courses.toArray());
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                courseDropDown.setAlignmentX(Component.CENTER_ALIGNMENT);
                courseDropDown.addActionListener(actionListenerDiscussionForum);
                contentDiscussionForum.add(courseDropDown);

                if (adminPerms) {
                    height = 175;
                    gradeStudent = new JButton("Grade Student");
                    gradeStudent.setAlignmentX(Component.CENTER_ALIGNMENT);
                    gradeStudent.addActionListener(actionListenerDiscussionForum);
                    contentDiscussionForum.add(gradeStudent);

                    createNewDiscussionPost = new JButton("Create a new Discussion Post");
                    createNewDiscussionPost.setAlignmentX(Component.CENTER_ALIGNMENT);
                    createNewDiscussionPost.addActionListener(actionListenerDiscussionForum);
                    contentDiscussionForum.add(createNewDiscussionPost);
                } else {
                    seeGrade = new JButton("View Your Grade");
                    seeGrade.setAlignmentX(Component.CENTER_ALIGNMENT);
                    seeGrade.addActionListener(actionListenerDiscussionForum);
                    contentDiscussionForum.add(seeGrade);
                }

                back = new JButton("Back");
                back.setAlignmentX(Component.CENTER_ALIGNMENT);
                back.addActionListener(actionListenerBack);
                contentDiscussionForum.add(back);

                displayDiscussionForum.setSize(300, height);
                displayDiscussionForum.setLocationRelativeTo(null);
                displayDiscussionForum.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                displayDiscussionForum.setVisible(true);
            }
            case "gradeMenu" -> {
                Container contentGradeMenu = displayGradeMenu.getContentPane();
                contentGradeMenu.setLayout(new BoxLayout(contentGradeMenu, BoxLayout.Y_AXIS));

                gradeLabel = new JLabel("Set Grade on Slider");
                contentGradeMenu.add(gradeLabel);

                gradeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 70);
                gradeSlider.setMajorTickSpacing(10);
                gradeSlider.setMinorTickSpacing(5);
                gradeSlider.setPaintTicks(true);
                gradeSlider.setPaintLabels(true);
                contentGradeMenu.add(gradeSlider);

                try {
                    objectOutputStream.writeUTF("getStudents");
                    objectOutputStream.flush();
                    ArrayList<String> studentNames = (ArrayList<String>) objectInputStream.readObject();
                    studentDropDown = new JComboBox<>(studentNames.toArray());
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                studentDropDown.setAlignmentX(Container.CENTER_ALIGNMENT);
                contentGradeMenu.add(studentDropDown);

                confirmGrade = new JButton("Confirm Grade Selection");
                confirmGrade.setAlignmentX(Container.CENTER_ALIGNMENT);
                confirmGrade.addActionListener(actionListenerDiscussionForum);
                contentGradeMenu.add(confirmGrade);

                back = new JButton("back");
                back.setAlignmentX(Container.CENTER_ALIGNMENT);
                back.addActionListener(actionListenerBack);
                contentGradeMenu.add(back);

                displayGradeMenu.setSize(300, 175);
                displayGradeMenu.setLocationRelativeTo(null);
                displayGradeMenu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                displayGradeMenu.setVisible(true);
            }
            case "courseDisplay" -> {

            }
        }
    }
}