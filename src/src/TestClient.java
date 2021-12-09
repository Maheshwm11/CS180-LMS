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
    JButton createAccount;

    // GameState accountMenu
    JButton continueToProgram;
    JButton editAccount;
    JButton deleteAccount;

    // GameState createAccount
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
        JFrame displayCreateAccount = new JFrame("Role Selection");
        JFrame displayEditAccount = new JFrame("Account Editor");
        JFrame displayAccountMenu = new JFrame("Login 2");

        // JFrame Declarations (MENUS)
        JFrame displayDiscussionForum = new JFrame("Discussion Forum");
        JFrame secondaryMenu = new JFrame(identifier);


        // Login Action Listeners
        ActionListener actionListenerLogin = e -> {
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

            if (e.getSource() == createAccount) {
                if (!usernameTextField.getText().contains(";") && !passwordTextField.getText().contains(";")) {
                    displayLogin.dispose();
                    gameState = "createAccount";
                    run();
                } else {
                    JOptionPane.showMessageDialog(null, "Username and password cannot contain a semicolon (;)",
                            "Login", JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        ActionListener actionListenerCreateAccount = e -> {
            if (e.getSource() != back) {
                String username = usernameTextField.getText();
                String password = passwordTextField.getText();
                if (e.getSource() == teacher) {
                    adminPerms = true;
                    try {
                        objectOutputStream.writeUTF(String.format("createAccount;%s;%s;teacher", username, password));
                        objectOutputStream.flush();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    System.out.println("Sent to server: " + String.format("createAccount;%s;%s;teacher", username, password));
                } else {
                    try {
                        objectOutputStream.writeUTF(String.format("createAccount;%s;%s;student", username, password));
                        objectOutputStream.flush();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    System.out.println("Sent to server: " + String.format("createAccount;%s;%s;student", username, password));
                }
                displayCreateAccount.dispose();
                gameState = "accountMenu";
            } else {
                displayCreateAccount.dispose();
                gameState = "login";
            }
            run();
        };

        ActionListener actionListenerAccountMenu = e -> {
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

            if (e.getSource() == back) {
                displayAccountMenu.dispose();
                gameState = "login";
                run();
            }
        };

        ActionListener actionListenerEditAccount = e -> {
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
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            if (e.getSource() == back) {
                displayDiscussionForum.dispose();
                gameState = "login";
                run();
            }
        };

        // Logins
        switch (gameState) {
            case "login" -> {
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
                contentLogin.add(usernameTextField);
                if (passwordTextField == null) {
                    passwordTextField = new JTextField("Password");
                } else {
                    passwordTextField = new JTextField(passwordTextField.getText());
                }
                passwordTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
                contentLogin.add(passwordTextField);

                createAccount = new JButton("Create Account");
                createAccount.addActionListener(actionListenerLogin);
                createAccount.setAlignmentX(Component.CENTER_ALIGNMENT);
                contentLogin.add(createAccount);

                displayLogin.setSize(300, 150);
                displayLogin.setLocationRelativeTo(null);
                displayLogin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                displayLogin.setVisible(true);
            }
            case "createAccount" -> {
                System.out.println("GameState createAccount");
                Container contentCreateAccount = displayCreateAccount.getContentPane();
                contentCreateAccount.setLayout(new BoxLayout(contentCreateAccount, BoxLayout.Y_AXIS));

                teacher = new JButton("I am a Teacher");
                teacher.addActionListener(actionListenerCreateAccount);
                teacher.setAlignmentX(Component.CENTER_ALIGNMENT);
                contentCreateAccount.add(teacher);

                student = new JButton("I am a Student");
                student.addActionListener(actionListenerCreateAccount);
                student.setAlignmentX(Component.CENTER_ALIGNMENT);
                contentCreateAccount.add(student);

                back = new JButton("Back to Login");
                back.addActionListener(actionListenerCreateAccount);
                back.setAlignmentX(Component.CENTER_ALIGNMENT);
                contentCreateAccount.add(back);

                displayCreateAccount.setSize(300, 150);
                displayCreateAccount.setLocationRelativeTo(null);
                displayCreateAccount.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                displayCreateAccount.setVisible(true);
            }
            case "accountMenu" -> {
                System.out.println("GameState accountMenu");
                Container contentAccountMenu = displayAccountMenu.getContentPane();
                contentAccountMenu.setLayout(new BoxLayout(contentAccountMenu, BoxLayout.Y_AXIS));

                continueToProgram = new JButton("Continue to Program");
                continueToProgram.addActionListener(actionListenerAccountMenu);
                continueToProgram.setAlignmentX(Component.CENTER_ALIGNMENT);
                contentAccountMenu.add(continueToProgram);

                editAccount = new JButton("Edit Account");
                editAccount.addActionListener(actionListenerAccountMenu);
                editAccount.setAlignmentX(Component.CENTER_ALIGNMENT);
                contentAccountMenu.add(editAccount);

                deleteAccount = new JButton("Delete Account");
                deleteAccount.addActionListener(actionListenerAccountMenu);
                deleteAccount.setAlignmentX(Component.CENTER_ALIGNMENT);
                contentAccountMenu.add(deleteAccount);

                back = new JButton("Back to Login");
                back.addActionListener(actionListenerAccountMenu);
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

                edit = new JButton("Confirm");
                edit.addActionListener(actionListenerEditAccount);
                edit.setAlignmentX(Component.CENTER_ALIGNMENT);
                contentEditAccount.add(edit);

                newUsernameField = new JTextField(usernameTextField.getText());
                newUsernameField.setAlignmentX(Component.CENTER_ALIGNMENT);
                contentEditAccount.add(newUsernameField);

                newPasswordField = new JTextField(passwordTextField.getText());
                newPasswordField.setAlignmentX(Component.CENTER_ALIGNMENT);
                contentEditAccount.add(newPasswordField);

                back = new JButton("Back");
                back.addActionListener(actionListenerEditAccount);
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

                back = new JButton("Logout");
                back.setAlignmentX(Component.CENTER_ALIGNMENT);
                back.addActionListener(actionListenerDiscussionForum);
                contentDiscussionForum.add(back);

                displayDiscussionForum.setSize(300, 150);
                displayDiscussionForum.setLocationRelativeTo(null);
                displayDiscussionForum.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                displayDiscussionForum.setVisible(true);
            }
            case "courseDisplay" -> {

            }
        }
    }
}