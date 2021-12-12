import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client extends JComponent implements Runnable {

    // Client functionality
    GameState gameState = GameState.LOGIN;
    ArrayList<Post> discussionPosts;
    int trueIndex;
    static Socket socket;
    static ObjectOutputStream objectOutputStream;
    static ObjectInputStream objectInputStream;
    String identifier = "";
    boolean adminPerms = false;
    boolean looped = false;

    // All GameStates
    JButton back;

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
    JComboBox<Object> courseDropDown;

    // ^ Teacher Specific Options
    JButton gradeStudent;
    JButton createNewDiscussionPost;

    // ^ Student Specific Options
    JLabel seeGrade;

    // GameState gradeMenu
    ArrayList<String> studentNames = new ArrayList<>();
    JComboBox<Object> studentDropDown;
    JSlider gradeSlider;
    JButton viewStudentPost;
    JButton confirmGrade;

    // GameState newPost
    JTextField courseChoice;
    JTextField bodyTextStringChoiceNP;
    JFileChooser bodyTextFileChoiceNP;
    JButton confirmPost;

    // GameState courseMenu
    JComboBox courseSelection;

    // GameState singlePost
    Post post;
    JButton comment;
    JComboBox<Object> commentsDropDown;

    // ^ Admin Options
    JButton editPost;
    JButton deletePost;

    // GameState editPost
    JTextField bodyTextStringChoiceEP;
    JFileChooser bodyTextFileChoiceEP;
    JButton confirmEdit;

    // GameState newComment
    JTextField bodyTextStringChoiceNC;
    JFileChooser bodyTextFileChoiceNC;
    JButton confirmComment;


    public static void main(String[] args) throws IOException {
        socket = new Socket("localHost",4240);
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.flush();
        objectInputStream = new ObjectInputStream(socket.getInputStream());

        SwingUtilities.invokeLater(new Client());
    }



    public void run() {

        refreshDiscussionPosts();

        // JFrame Declarations (LOGINS)
        JFrame displayLogin = new JFrame("displayLogin");
        JFrame displayEditAccount = new JFrame("displayEditAccount");
        JFrame displayAccountMenu = new JFrame("displayAccountMenu");

        // JFrame Declarations (MENUS)
        JFrame displayDiscussionForum = new JFrame("displayDiscussionForum");
        JFrame displayGradeMenu = new JFrame("displayGradeMenu");
        JFrame displayStudentPosts = new JFrame("studentPosts");
        JFrame displayNewPost = new JFrame("displayNewPost");

        // JFrame Declarations (Post Interaction and Management)
        JFrame displayPostPicker = new JFrame("postPicker");
        JFrame displaySinglePost = new JFrame("singlePost");
        JFrame displayNewComment = new JFrame("newComment");
        JFrame displayEditPost = new JFrame("editPost");

        ActionListener actionListenerLogin = e -> {
            // GameState login
            if (e.getSource() == login) {
                System.out.println("gaming");
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
                            gameState = GameState.ACCOUNT_MENU;
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
                            gameState = GameState.ACCOUNT_MENU;
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
                gameState = GameState.DISCUSSION_FORUM;
                run();
            }

            if (e.getSource() == editAccount) {
                displayAccountMenu.dispose();
                gameState = GameState.EDIT_ACCOUNT;
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
                gameState = GameState.LOGIN;
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
                gameState = GameState.LOGIN;
                run();
            }
        };

        ActionListener actionListenerDiscussionForum = e -> {

            // GameState introMenu

            if (e.getSource() == courseDropDown) {
                displayDiscussionForum.dispose();
                gameState = GameState.POST_PICKER;
                run();
            }

            if (e.getSource() == gradeStudent) {
                displayDiscussionForum.dispose();
                gameState = GameState.GRADE_MENU;
                run();
            }

            if (e.getSource() == createNewDiscussionPost) {
                displayDiscussionForum.dispose();
                gameState = GameState.NEW_POST;
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

            if (e.getSource() == viewStudentPost) {
                displayGradeMenu.dispose();
                gameState = GameState.STUDENT_POSTS;
                run();
            }
        };

        ActionListener actionListenerPostManager = e -> {
            // GameState newPost

            if (e.getSource() == confirmPost | e.getSource() == bodyTextFileChoiceNP) {
                try {
                    String post;
                    String bodyText = "";
                    if (e.getSource() == confirmPost) {
                        bodyText = bodyTextStringChoiceNP.getText();
                    } else {
                        File f = bodyTextFileChoiceNP.getSelectedFile();
                        BufferedReader bfr = new BufferedReader(new FileReader(f));
                        String line = bfr.readLine();
                        while (line != null) {
                            bodyText += " " + line;
                            line = bfr.readLine();
                        }
                    }
                    post = String.format("newPost;%s;%s;%s",
                            bodyText,usernameTextField.getText(),
                            courseChoice.getText());

                    if (bodyText.contains(";")) {
                        JOptionPane.showMessageDialog(null, "Body Text Cannot Contain a Semicolon (;)",
                                "New Post", JOptionPane.ERROR_MESSAGE);
                    } else if (courseChoice.getText().contains(";")) {
                        JOptionPane.showMessageDialog(null, "Course Cannot Contain a Semicolon (;)",
                                "New Post", JOptionPane.ERROR_MESSAGE);
                    } else if (courseChoice.getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "Insert a Course",
                                "New Post", JOptionPane.ERROR_MESSAGE);
                    }else if (bodyText.equals("")) {
                        JOptionPane.showMessageDialog(null, "Insert Body Text",
                                "New Post", JOptionPane.ERROR_MESSAGE);
                    } else {
                        objectOutputStream.writeUTF(post);
                        objectOutputStream.flush();

                        JOptionPane.showMessageDialog(null, "Post Created",
                                "Login", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            if (e.getSource() == courseSelection) {
                displayPostPicker.dispose();
                gameState = GameState.SINGLE_POST;
                run();
            }

            if (e.getSource() == comment) {
                displaySinglePost.dispose();
                gameState = GameState.NEW_COMMENT;
                run();
            }

            if (e.getSource() == editPost) {
                displaySinglePost.dispose();
                gameState = GameState.EDIT_POST;
                run();
            }

            if (e.getSource() == deletePost) {
                try {
                    objectOutputStream.writeUTF("deletePost");
                    objectOutputStream.flush();
                    objectOutputStream.writeObject(post);

                    displaySinglePost.dispose();
                    gameState = GameState.POST_PICKER;
                    run();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            if (e.getSource() == confirmComment | e.getSource() == bodyTextFileChoiceNC) {
                try {
                    String postString;
                    String bodyText = "";
                    if (e.getSource() == confirmComment) {
                        bodyText = bodyTextStringChoiceNC.getText();
                    } else {
                        File f = bodyTextFileChoiceNC.getSelectedFile();
                        BufferedReader bfr = new BufferedReader(new FileReader(f));
                        String line = bfr.readLine();
                        while (line != null) {
                            bodyText += " " + line;
                            line = bfr.readLine();
                        }
                    }
                    postString = String.format("newComment;%s;%s",
                            bodyText, usernameTextField.getText());

                    if (bodyText.contains(";")) {
                        JOptionPane.showMessageDialog(null, "Body Text Cannot Contain a Semicolon (;)",
                                "New Post", JOptionPane.ERROR_MESSAGE);
                    } else {
                        objectOutputStream.writeUTF(postString);
                        objectOutputStream.flush();
                        objectOutputStream.writeObject(post);

                        JOptionPane.showMessageDialog(null, "Post Edited",
                                "Login", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            if (e.getSource() == confirmEdit | e.getSource() == bodyTextFileChoiceEP) {
                try {
                    String postString;
                    String bodyText = "";
                    if (e.getSource() == confirmEdit) {
                        bodyText = bodyTextStringChoiceEP.getText();
                    } else {
                        File f = bodyTextFileChoiceEP.getSelectedFile();
                        BufferedReader bfr = new BufferedReader(new FileReader(f));
                        String line = bfr.readLine();
                        while (line != null) {
                            bodyText += " " + line;
                            line = bfr.readLine();
                        }
                    }
                    postString = String.format("editPost;%s",
                            bodyText);

                    if (bodyText.contains(";")) {
                        JOptionPane.showMessageDialog(null, "Body Text Cannot Contain a Semicolon (;)",
                                "New Post", JOptionPane.ERROR_MESSAGE);
                    }else if (bodyText.equals("")) {
                        JOptionPane.showMessageDialog(null, "Insert Body Text",
                                "New Post", JOptionPane.ERROR_MESSAGE);
                    } else {
                        objectOutputStream.writeUTF(postString);
                        objectOutputStream.flush();
                        objectOutputStream.writeObject(post);

                        JOptionPane.showMessageDialog(null, "Comment Created",
                                "Login", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            if (e.getSource() == commentsDropDown) {
                post = post.getComments().get(Integer.parseInt(commentsDropDown.getSelectedItem().toString().split(" ")[0]) - 1);
                displaySinglePost.dispose();
                run();
            }
        };

        ActionListener actionListenerBack = e -> {
            switch (gameState) {
                case LOGIN -> {
                    try {
                        displayLogin.dispose();
                        objectOutputStream.writeUTF("logout");
                        objectOutputStream.flush();
                        socket.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                case ACCOUNT_MENU -> {
                    displayAccountMenu.dispose();
                    gameState = GameState.LOGIN;
                    run();
                }
                case EDIT_ACCOUNT -> {
                    displayEditAccount.dispose();
                    gameState = GameState.ACCOUNT_MENU;
                    run();
                }
                case DISCUSSION_FORUM -> {
                    displayDiscussionForum.dispose();
                    gameState = GameState.ACCOUNT_MENU;
                    run();
                }
                case GRADE_MENU -> {
                    displayGradeMenu.dispose();
                    gameState = GameState.DISCUSSION_FORUM;
                    run();
                }
                case NEW_POST -> {
                    displayNewPost.dispose();
                    gameState = GameState.DISCUSSION_FORUM;
                    run();
                }
                case POST_PICKER -> {
                    displayPostPicker.dispose();
                    gameState = GameState.DISCUSSION_FORUM;
                    run();
                }
                case SINGLE_POST -> {
                    displaySinglePost.dispose();
                    gameState = GameState.POST_PICKER;
                    run();
                }
                case EDIT_POST -> {
                    displayEditPost.dispose();
                    gameState = GameState.SINGLE_POST;
                    run();
                }
                case NEW_COMMENT -> {
                    displayNewComment.dispose();
                    gameState = GameState.SINGLE_POST;
                    run();
                }
                case STUDENT_POSTS -> {
                    displayStudentPosts.dispose();
                    gameState = GameState.GRADE_MENU;
                    run();
                }
                default -> throw new IllegalStateException("Unexpected value: " + gameState);
            }
        };

        // Logins
        switch (gameState) {
            case LOGIN -> {
                adminPerms = false;
                System.out.println("GameState login");
                Container contentLogin = displayLogin.getContentPane();
                contentLogin.setLayout(new BoxLayout(contentLogin, BoxLayout.Y_AXIS));

                login = addButton(contentLogin, "Login");
                login.addActionListener(actionListenerLogin);

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

                teacher = addButton(createAccount, "Create Teacher Account");
                teacher.addActionListener(actionListenerLogin);

                student = addButton(createAccount, "Create Student Account");
                student.addActionListener(actionListenerLogin);

                contentLogin.add(createAccount);

                back = addButton(contentLogin, "Log Out");
                back.addActionListener(actionListenerBack);

                buildDisplay(displayLogin, 400, 175);
            }
            case ACCOUNT_MENU -> {
                System.out.println("GameState accountMenu");
                Container contentAccountMenu = displayAccountMenu.getContentPane();
                contentAccountMenu.setLayout(new BoxLayout(contentAccountMenu, BoxLayout.Y_AXIS));

                continueToProgram = addButton(contentAccountMenu, "Continue to Program");
                continueToProgram.addActionListener(actionListenerLogin);

                editAccount = addButton(contentAccountMenu,"Edit Account");
                editAccount.addActionListener(actionListenerLogin);

                deleteAccount = addButton(contentAccountMenu, "Delete Account");
                deleteAccount.addActionListener(actionListenerLogin);

                back = addButton(contentAccountMenu, "Back");
                back.addActionListener(actionListenerBack);

                buildDisplay(displayAccountMenu, 300, 150);
            }
            case EDIT_ACCOUNT -> {
                Container contentEditAccount = displayEditAccount.getContentPane();
                contentEditAccount.setLayout(new BoxLayout(contentEditAccount, BoxLayout.Y_AXIS));

                edit = addButton(contentEditAccount, "Confirm");
                edit.addActionListener(actionListenerLogin);

                newUsernameField = new JTextField(usernameTextField.getText());
                newUsernameField.setAlignmentX(Component.CENTER_ALIGNMENT);
                contentEditAccount.add(newUsernameField);

                newPasswordField = new JTextField(passwordTextField.getText());
                newPasswordField.setAlignmentX(Component.CENTER_ALIGNMENT);
                contentEditAccount.add(newPasswordField);

                back = addButton(contentEditAccount, "Back");
                back.addActionListener(actionListenerBack);

                buildDisplay(displayEditAccount, 300, 150);
            }
        }
        // Menus
        switch (gameState) {
            case DISCUSSION_FORUM -> {
                int height = 150;
                Container contentDiscussionForum = displayDiscussionForum.getContentPane();
                contentDiscussionForum.setLayout(new BoxLayout(contentDiscussionForum, BoxLayout.Y_AXIS));

                JLabel courseLabel = new JLabel("Choose a course to be displayed");
                courseLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                contentDiscussionForum.add(courseLabel);

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
                    gradeStudent = addButton(contentDiscussionForum, "Grade a Student");
                    gradeStudent.addActionListener(actionListenerDiscussionForum);

                    createNewDiscussionPost = addButton(contentDiscussionForum, "Create a new Discussion Forum");
                    createNewDiscussionPost.addActionListener(actionListenerDiscussionForum);
                } else {
                    try {
                        objectOutputStream.writeUTF(String.format("seeGrade;%s", usernameTextField.getText()));
                        objectOutputStream.flush();
                        System.out.println("Sent to Server: " + String.format("seeGrade;%s", usernameTextField.getText()));
                        seeGrade = new JLabel("Your Grade is: " + objectInputStream.readUTF());
                        seeGrade.setAlignmentX(Component.CENTER_ALIGNMENT);
                        contentDiscussionForum.add(seeGrade);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                back = addButton(contentDiscussionForum, "Back");
                back.addActionListener(actionListenerBack);

                buildDisplay(displayDiscussionForum, 300, height);
            }
            case GRADE_MENU -> {
                Container contentGradeMenu = displayGradeMenu.getContentPane();
                contentGradeMenu.setLayout(new BoxLayout(contentGradeMenu, BoxLayout.Y_AXIS));

                JLabel gradeLabel = new JLabel("Set Grade on Slider");
                gradeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
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

                confirmGrade = addButton(contentGradeMenu, "Confirm Grade Selection");
                confirmGrade.addActionListener(actionListenerDiscussionForum);

                viewStudentPost = addButton(contentGradeMenu, "View all Posts by this Student");
                viewStudentPost.addActionListener(actionListenerDiscussionForum);

                back = addButton(contentGradeMenu, "Back");
                back.addActionListener(actionListenerBack);

                buildDisplay(displayGradeMenu, 300, 175);
            }
            case STUDENT_POSTS -> {
                Container contentStudentPosts = displayStudentPosts.getContentPane();
                contentStudentPosts.setLayout(new BoxLayout(contentStudentPosts, BoxLayout.Y_AXIS));

                JPanel container = new JPanel();
                container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
                JScrollPane scrollPane = new JScrollPane(container,
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                contentStudentPosts.add(scrollPane);

                for (Post i : discussionPosts) {
                    for (Post ii : i.getComments()) {
                        if (ii.getPoster().equals(studentDropDown.getSelectedItem().toString())) {
                            container.add(new JLabel("Posted by: " + i.getPoster()));
                            boolean c = true;
                            String parentBodyText = i.getBodyText();
                            while (c) {
                                if (parentBodyText.length() > 35) {
                                    container.add(new JLabel(parentBodyText.substring(0,35)));
                                    parentBodyText = parentBodyText.substring(35);
                                } else {
                                    container.add(new JLabel(parentBodyText));
                                    c = false;
                                }
                            }
                            container.add(new JLabel("-----------------------------------"));
                            container.add(new JLabel("Student Response"));
                            c = true;
                            String bodyText = ii.getBodyText();
                            while (c) {
                                if (bodyText.length() > 35) {
                                    container.add(new JLabel(bodyText.substring(0,35)));
                                    bodyText = bodyText.substring(35);
                                } else {
                                    container.add(new JLabel(bodyText));
                                    c = false;
                                }
                            }
                            container.add(new JLabel("Posted at: " + ii.getTimeStamp()));
                        }
                    }
                }

                back = addButton(contentStudentPosts, "Back");
                back.addActionListener(actionListenerBack);

                buildDisplay(displayStudentPosts, 300, 500);
            }
            case NEW_POST -> {
                Container contentNewPost = displayNewPost.getContentPane();
                contentNewPost.setLayout(new BoxLayout(contentNewPost, BoxLayout.Y_AXIS));

                JLabel courseLabel = new JLabel("type the relevant course");
                courseLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                contentNewPost.add(courseLabel);

                courseChoice = new JTextField("");
                courseChoice.setAlignmentX(Component.CENTER_ALIGNMENT);
                contentNewPost.add(courseChoice);

                JLabel bodyTextLabel = new JLabel("Type or Insert .txt File");
                bodyTextLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                contentNewPost.add(bodyTextLabel);

                bodyTextStringChoiceNP = new JTextField();
                bodyTextStringChoiceNP.setAlignmentX(Component.CENTER_ALIGNMENT);
                contentNewPost.add(bodyTextStringChoiceNP);

                bodyTextFileChoiceNP = new JFileChooser();
                bodyTextFileChoiceNP.setAlignmentX(Component.CENTER_ALIGNMENT);
                bodyTextFileChoiceNP.addActionListener(actionListenerPostManager);
                contentNewPost.add(bodyTextFileChoiceNP);

                confirmPost = addButton(contentNewPost, "Post Using Text Field");
                confirmPost.addActionListener(actionListenerPostManager);

                back = addButton(contentNewPost, "Back");
                back.addActionListener(actionListenerBack);

                buildDisplay(displayNewPost, 700, 700);
            }
        }
        // Post Manager
        switch (gameState) {
            case POST_PICKER -> {

                looped = false;

                System.out.println("GameState postPicker");
                Container contentPostPicker = displayPostPicker.getContentPane();
                contentPostPicker.setLayout(new BoxLayout(contentPostPicker, BoxLayout.Y_AXIS));

                JLabel topLabel = new JLabel(courseDropDown.getSelectedItem().toString());
                topLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                contentPostPicker.add(topLabel);

                JLabel courseLabel = new JLabel("Choose a Post to See More Options");
                courseLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                contentPostPicker.add(courseLabel);

                JPanel container = new JPanel();
                container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
                JScrollPane scrollPane = new JScrollPane(container,
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                contentPostPicker.add(scrollPane);

                ArrayList<String> nums = new ArrayList<>();
                int curatedIndex = 0;
                for (int i = 0; i < discussionPosts.size(); i++) {
                    if (discussionPosts.get(i).getCourse().equals(courseDropDown.getSelectedItem().toString()) | courseDropDown.getSelectedItem().equals("all")) {
                        try {
                            objectOutputStream.writeUTF("curateIndex;" + curatedIndex);
                            objectOutputStream.flush();
                            objectOutputStream.writeObject(discussionPosts.get(i));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        curatedIndex++;
                        container.add(new JLabel("Posted by: " + discussionPosts.get(i).getPoster()));
                        if (identifier.equals("all")) {
                            container.add(new JLabel("Course: " + discussionPosts.get(i).getCourse()));
                        }
                        boolean c = true;
                        String bodyText = discussionPosts.get(i).getBodyText();
                        int len = bodyText.length();
                        if (len > 10) {
                            len = 10;
                        }
                        nums.add(String.format("%d (%s)", curatedIndex, bodyText.substring(0, len)));
                        while (c) {
                            if (bodyText.length() > 35) {
                                container.add(new JLabel(bodyText.substring(0,35)));
                                bodyText = bodyText.substring(35);
                            } else {
                                container.add(new JLabel(bodyText));
                                c = false;
                            }
                        }
                        container.add(new JLabel("Posted at: " + discussionPosts.get(i).getTimeStamp()));
                        container.add(new JLabel("Comments: " + discussionPosts.get(i).getComments().size()));
                        container.add(new JLabel(" "));
                        container.add(new JLabel(" "));
                    }
                }


                JPanel bottomPanel = new JPanel();

                back = addButton(bottomPanel, "Back");
                back.addActionListener(actionListenerBack);
                courseSelection = new JComboBox<>(nums.toArray());
                courseSelection.setAlignmentX(Component.CENTER_ALIGNMENT);
                courseSelection.addActionListener(actionListenerPostManager);
                bottomPanel.add(courseSelection);

                contentPostPicker.add(bottomPanel);

                buildDisplay(displayPostPicker, 300, 500);
            }
            case SINGLE_POST -> {

                if (!looped) {
                    for (int i = 0; i < discussionPosts.size(); i++) {
                        if (discussionPosts.get(i).getCuratedIndex() == (Integer.parseInt(courseSelection.getSelectedItem().toString().split(" ")[0])) - 1) {
                            trueIndex = i;
                            post = discussionPosts.get(i);
                        }
                    }
                }

                System.out.println(post.toString());

                looped = true;

                Container contentSinglePost = displaySinglePost.getContentPane();
                contentSinglePost.setLayout(new BoxLayout(contentSinglePost, BoxLayout.Y_AXIS));

                JLabel topLabel = new JLabel(courseDropDown.getSelectedItem().toString());
                topLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                contentSinglePost.add(topLabel);

                JPanel container = new JPanel();
                container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
                JScrollPane scrollPane = new JScrollPane(container,
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                contentSinglePost.add(scrollPane);
                container.add(new JLabel("Posted by: " + post.getPoster()));
                boolean c = true;
                String bodyText = post.getBodyText();
                while (c) {
                    if (bodyText.length() > 35) {
                        container.add(new JLabel(bodyText.substring(0,35)));
                        bodyText = bodyText.substring(35);
                    } else {
                        container.add(new JLabel(bodyText));
                        c = false;
                    }
                }
                container.add(new JLabel("Posted at: " + post.getTimeStamp()));
                container.add(new JLabel("---------"));
                container.add(new JLabel("Comments:"));

                for (int i = 0; i < post.getComments().size(); i++) {
                    container.add(new JLabel("Posted by: " + post.getComments().get(i).getPoster()));
                    c = true;
                    bodyText = post.getComments().get(i).getBodyText();
                    while (c) {
                        if (bodyText.length() > 35) {
                            container.add(new JLabel(bodyText.substring(0,35)));
                            bodyText = bodyText.substring(35);
                        } else {
                            container.add(new JLabel(bodyText));
                            c = false;
                        }
                    }
                    container.add(new JLabel("Posted at: " + post.getComments().get(i).getTimeStamp()));
                    container.add(new JLabel("Comments: " + post.getComments().get(i).getComments().size()));
                    container.add(new JLabel(" "));
                    container.add(new JLabel(" "));
                }

                JPanel bottomPanel = new JPanel();

                comment = addButton(bottomPanel, "Comment");
                comment.addActionListener(actionListenerPostManager);

                ArrayList<String> nums = new ArrayList<>();
                int len;
                for (int i = 0; i < post.getComments().size(); i++) {
                    len = post.getComments().get(i).getBodyText().length();
                    if (len > 10) {
                        len = 10;
                    }
                    nums.add(String.format("%d (%s)", (i + 1), post.getComments().get(i).getBodyText().substring(0, len)));
                }
                commentsDropDown = new JComboBox<>(nums.toArray());
                commentsDropDown.setAlignmentX(Component.CENTER_ALIGNMENT);
                commentsDropDown.addActionListener(actionListenerPostManager);
                bottomPanel.add(commentsDropDown);

                if (adminPerms | post.getPoster().equals(usernameTextField.getText())) {
                    editPost = addButton(bottomPanel, "Edit Post");
                    editPost.addActionListener(actionListenerPostManager);

                    deletePost = addButton(bottomPanel, "Delete Post");
                    deletePost.addActionListener(actionListenerPostManager);
                }

                back = addButton(bottomPanel, "Back");
                back.addActionListener(actionListenerBack);

                contentSinglePost.add(bottomPanel);

                buildDisplay(displaySinglePost, 300, 500);
            }
            case EDIT_POST -> {
                Container contentEditPost = displayEditPost.getContentPane();
                contentEditPost.setLayout(new BoxLayout(contentEditPost, BoxLayout.Y_AXIS));

                JLabel bodyTextLabel = new JLabel("Type or Insert .txt File");
                bodyTextLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                contentEditPost.add(bodyTextLabel);

                bodyTextStringChoiceEP = new JTextField();
                bodyTextStringChoiceEP.setAlignmentX(Component.CENTER_ALIGNMENT);
                contentEditPost.add(bodyTextStringChoiceEP);

                bodyTextFileChoiceEP = new JFileChooser();
                bodyTextFileChoiceEP.setAlignmentX(Component.CENTER_ALIGNMENT);
                bodyTextFileChoiceEP.addActionListener(actionListenerPostManager);
                contentEditPost.add(bodyTextFileChoiceEP);

                confirmEdit = addButton(contentEditPost, "Edit Using Text Field");
                confirmEdit.addActionListener(actionListenerPostManager);

                back = addButton(contentEditPost, "Back");
                back.addActionListener(actionListenerBack);

                buildDisplay(displayEditPost, 700, 700);
            }
            case NEW_COMMENT -> {
                Container contentNewComment = displayNewComment.getContentPane();
                contentNewComment.setLayout(new BoxLayout(contentNewComment, BoxLayout.Y_AXIS));

                JLabel bodyTextLabel = new JLabel("Type or Insert .txt File");
                bodyTextLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                contentNewComment.add(bodyTextLabel);

                bodyTextStringChoiceNC = new JTextField();
                bodyTextStringChoiceNC.setAlignmentX(Component.CENTER_ALIGNMENT);
                contentNewComment.add(bodyTextStringChoiceNC);

                bodyTextFileChoiceNC = new JFileChooser();
                bodyTextFileChoiceNC.setAlignmentX(Component.CENTER_ALIGNMENT);
                bodyTextFileChoiceNC.addActionListener(actionListenerPostManager);
                contentNewComment.add(bodyTextFileChoiceNC);

                confirmComment = addButton(contentNewComment, "Comment Using Text Field");
                confirmComment.addActionListener(actionListenerPostManager);

                back = addButton(contentNewComment, "Back");
                back.addActionListener(actionListenerBack);

                buildDisplay(displayNewComment, 700, 700);
            }
        }
    }

    // Utility Methods
    private static JButton addButton(Container content, String buttonText) {
        JButton button = new JButton(buttonText);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(button);
        return button;
    }

    private static void buildDisplay(JFrame display, int x, int y) {
        display.setSize(x, y);
        display.setLocationRelativeTo(null);
        display.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        display.setVisible(true);
    }

    private void refreshDiscussionPosts() {
        Data data = new Data();
        discussionPosts = data.readPostFile();
    }
}