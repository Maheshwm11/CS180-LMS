import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicReference;

public class TestClient extends JComponent implements Runnable {

    // Client functionality
    static Socket socket;
    static BufferedReader inputStream;
    static PrintWriter outputStream;

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


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new TestClient());

        //create socket to server
        try {
            socket = new Socket("localHost",4243);
            inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outputStream = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "There was an error connecting to the server!",
                    "Discussion Board", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }
    }

    public void run() {


        // JFrame Declarations
        JFrame displayLogin = new JFrame("Login");
        JFrame displayCreateAccount = new JFrame("Role Selection");
        JFrame displayEditAccount = new JFrame("Account Editor");
        JFrame displayAccountMenu = new JFrame("Login 2");
        JFrame displayDiscussionForum = new JFrame("Discussion Forum");


        ActionListener actionListenerLogin = e -> {
            // TODO: edit acc > del acc > login > brick
            if (e.getSource() == login) {
                if (!usernameTextField.getText().contains(";") && !passwordTextField.getText().contains(";")) {
                    System.out.println("Sending Data to Server");
                    outputStream.write(String.format("login;%s;%s", usernameTextField.getText(), passwordTextField.getText()));
                    outputStream.println();
                    outputStream.flush();
                    System.out.println("Data Sent");
                    try {
                        gameState = inputStream.readLine();
                        if (gameState.equals("login")) {
                            JOptionPane.showMessageDialog(null, "Username and password not recognized",
                                    "Login", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Login Successful",
                                    "Login", JOptionPane.INFORMATION_MESSAGE);
                            displayLogin.dispose();
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
                System.out.println("Sending Data to Server");
                if (e.getSource() == teacher) {
                    outputStream.write(String.format("createAccount;%s;%s;teacher", username, password));
                } else {
                    outputStream.write(String.format("createAccount;%s;%s;student", username, password));
                }
                outputStream.println();
                outputStream.flush();
                System.out.println("Data Sent");
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
                outputStream.write(String.format("deleteAccount;%s;%s", usernameTextField.getText(), passwordTextField.getText()));
                outputStream.println();
                outputStream.flush();

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
                System.out.println("!!!!!!!!: " + newPasswordField.getText());
                outputStream.write(String.format("editAccount;%s;%s;%s;%s",usernameTextField.getText(), passwordTextField.getText(),
                        newUsernameField.getText(), newPasswordField.getText()));
                outputStream.println();
                outputStream.flush();

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

        switch (gameState) {
            case "login":
                System.out.println("GameState login");

                Container contentLogin = displayLogin.getContentPane();
                contentLogin.setLayout(new BoxLayout(contentLogin, BoxLayout.Y_AXIS));

                login = new JButton("Login");
                login.addActionListener(actionListenerLogin);
                login.setAlignmentX(Component.CENTER_ALIGNMENT);
                contentLogin.add(login);

                if (usernameTextField == null) {
                    usernameTextField = new JTextField("username");
                } else {
                    usernameTextField = new JTextField(usernameTextField.getText());
                }
                usernameTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
                contentLogin.add(usernameTextField);

                if (passwordTextField == null) {
                    passwordTextField = new JTextField("password");
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
                break;
            case "createAccount":
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

                displayCreateAccount.setSize(300,150);
                displayCreateAccount.setLocationRelativeTo(null);
                displayCreateAccount.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                displayCreateAccount.setVisible(true);
                break;
            case "accountMenu":
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
                break;
            case "editAccount":
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
}
