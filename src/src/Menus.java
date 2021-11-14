import java.io.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Menus {
    private static ArrayList<Post> discussionPosts = new ArrayList<>();

    public static ArrayList<Post> getDiscussionPosts() {
        return discussionPosts;
    }

    public void setDiscussionPosts(ArrayList<Post> discussionPosts) {
        Menus.discussionPosts = discussionPosts;
    }

    public static void main(String[] args) {
        //discussionPosts.add(new Post("src/src/1.txt", "gamer", "gaming"));
        // This should be imported from a file
        // usernames and passwords should be formatted like %s;%s
        // as a result usernames should not be allowed to contain a ;
        // ; is replaceable with any given unicode
        Data data = new Data();
        ArrayList<String> logins = data.getLoginFile();
        Scanner s = new Scanner(System.in);

        boolean loop;
        int choice;
        String identification;
        String role;
        String newUsername = "";
        String newPassword = "";

        boolean newAccountLoop = false;
        loop = true;
        String password;
        String username = "";
        String truePassword = "";
        boolean teacher = false;

        // ask user what they want to do
        do {
            System.out.println("Login (1), Make new Acct (2), Edit Account(3), Delete Acct (4)");
            choice = Integer.parseInt(s.nextLine());

            // if user doesn't input a valid option
            if (choice == 1 || choice == 2 || choice == 3 || choice == 4) {
                loop = false;
                // if user doesn't input a valid option
            } else {
                System.out.println("invalid input");
            }
        } while (loop);

        // implement a cancel system
        loop = true;
        switch (choice) {
            // logging into account
            case 1:
                do {
                    System.out.println("Enter your username");
                    username = s.nextLine();
                    if (username.equals("")) {
                        System.out.println("Username cannot be blank");
                    }
                    else {
                        for (String value : logins) {
                            String[] login = value.split(";");
                            if (username.equals(login[0])) {
                                loop = false;
                                truePassword = login[1];
                                if (login[2].equals("teacher")) {
                                    teacher = true;
                                }
                            }
                        }
                        if (loop) {
                            System.out.println("Username not found");
                        }
                    }
                } while (loop);

                loop = true;
                do {
                    System.out.println("Enter your password");
                    password = s.nextLine();
                    if (!truePassword.equals("") && truePassword.equals(password)) {
                        loop = false;
                        System.out.println("Success");
                    } else {
                        System.out.println("Incorrect password. Please try again");
                    }
                } while (loop);
                break;

            // creating a new account
            case 2:
                // create username for new account
                do {
                    newAccountLoop = false;
                    do {
                        System.out.println("Create your username, leave blank to exit");
                        username = s.nextLine();
                        if (username.equals("")) {
                            break;
                        }
                        // ensure username doesnt have any semicolons
                        if (username.contains(";")) {
                            System.out.println("Username can not contain a semicolon(;). Please enter a new username.");
                        }
                    } while (username.contains(";"));

                    // create password for new account
                    do {
                        System.out.println("Create your password");
                        password = s.nextLine();
                        // ensure username doesnt have any semicolons
                        if (password.contains(";")) {
                            System.out.println("Password can not contain a semicolon(;). Please enter a new password.");
                        }
                        if (password.equals("")) {
                            newAccountLoop = true;
                        }
                    } while (password.contains(";") || password.equals(""));
                } while (newAccountLoop);
                // save username and password into identification variable

                // user identifies as either student or teacher
                System.out.println("Are you a student or a teacher?");
                role = s.nextLine().toLowerCase();
                if (role.equals("teacher")) {
                    teacher = true;
                }
                identification = username + ";" + password + ";" + role;
                // adding username, password, and role into login arraylist
                logins.add(identification);
                break;

            // editing an account
            case 3:
                // enter username
                do {
                    System.out.println("Enter your username, leave blank to exit");
                    username = s.nextLine();
                    if (username.equals("")) {
                        break;
                    }
                    for (String value : logins) {
                        String[] login = value.split(";");
                        if (username.equals(login[0])) {
                            loop = false;
                            truePassword = login[1];
                            if (login[2].equals("teacher")) {
                                teacher = true;
                            }
                        }
                    }
                    if (loop) {
                        System.out.println("Username not found");
                    }
                } while (loop);

                loop = true;

                // enter password
                do {
                    System.out.println("Enter your password");
                    password = s.nextLine();
                    if (truePassword.equals(password)) {
                        loop = false;
                    } else {
                        System.out.println("Incorrect password. Please try again.");
                    }
                } while (loop);

                // enter new username
                do {
                    System.out.println("Create your new username");
                    newUsername = s.nextLine();
                    // ensure username doesnt have any semicolons
                    if (newUsername.contains(";")) {
                        System.out.println("Username can not contain a semicolon(;). Please enter a new username.");
                    }
                    if (newUsername.equals("")) {
                        System.out.println("Usernames cannot be blank");
                    }
                } while (newUsername.contains(";") || newUsername.equals(""));

                // enter new password
                do {
                    System.out.println("Create your new password");
                    newPassword = s.nextLine();
                    // ensure username doesnt have any semicolons
                    if (newPassword.contains(";")) {
                        System.out.println("Password can not contain a semicolon(;). Please enter a new password.");
                    }
                    if (newPassword.equals("")) {
                        System.out.println("Password cannot be blank. Please enter a new password.");
                    }
                } while (newPassword.contains(";") || newPassword.equals(""));

                // replacing old login with new login
                for (int i = 0; i < logins.size(); i++) {
                    if (teacher) {
                        if (logins.get(i).equals(username + ";" + password + ";teacher")) {
                            logins.remove(i);
                            identification = newUsername + ";" + newPassword + ";teacher";
                            logins.add(identification);
                        }
                    } else {
                        if (logins.get(i).equals(username + ";" + password + ";student")) {
                            logins.remove(i);
                            identification = newUsername + ";" + newPassword + ";student";
                            logins.add(identification);
                        }
                    }
                }
                break;


            // deleting an account
            case 4:
                do {
                    System.out.println("Enter your username, leave blank to exit");
                    username = s.nextLine();
                    if (username.equals("")) {
                        break;
                    }
                    for (String value : logins) {
                        String[] login = value.split(";");
                        if (username.equals(login[0])) {
                            loop = false;
                            truePassword = login[1];
                        }
                    }
                    if (loop) {
                        System.out.println("Username not found");
                    }
                } while (loop);

                loop = true;
                do {
                    System.out.println("Enter your password");
                    password = s.nextLine();
                    if (truePassword.equals(password)) {
                        loop = false;
                        for (int i = 0; i < logins.size(); i++) {
                            if (logins.get(i).equals(username + ";" + password + ";student")
                                    || logins.get(i).equals(username + ";" + password + ";teacher")) {
                                logins.remove(i);
                            }
                        }
                        System.out.println("Success");
                    } else {
                        System.out.println("Incorrect password. Please try again.");
                    }
                } while (loop);
                break;
        }

        // menus

        ArrayList<String> courses = new ArrayList<>();

        loop = true;
        String response;
        do {
            System.out.println("What course would you like to view\ntype all to view all courses");
            System.out.println("Course list");

            for (Post discussionPost : discussionPosts) {
                String course = discussionPost.getCourse();
                if (!courses.contains(course)) {
                    courses.add(course);
                    System.out.println(course);
                }
            }

            response = s.nextLine();
            if (courses.contains(response) || response.equals("all")) {
                loop = false;
            } else {
                System.out.println("invalid choice");
            }
        } while (loop);
        loop = true;
        ArrayList<Post> curatedPosts = new ArrayList<>();

        for (Post discussionPost : discussionPosts) {
            String course = discussionPost.getCourse();
            if (response.equals(course) || response.equals("all")) {
                curatedPosts.add(discussionPost);
            }
        }

        do {
            for (Post curatedPost : curatedPosts) {
                System.out.println(curatedPost.toString());
            }
            System.out.println("\n\nEnter the number of the post to view more details");
            System.out.println("Enter 0 to see advanced options");

            choice = Integer.parseInt(s.nextLine());
            if (choice == 0) {
                System.out.println("0) Back");
                System.out.println("1) Exit");
                if (teacher) {
                    System.out.println("2) Create new discussionPost");
                }
                switch (Integer.parseInt(s.nextLine())) {
                    case 0:
                        break;
                    case 1:
                        System.out.println("Exiting...");
                        loop = false;
                        break;
                    case 2:
                        if (teacher) {
                            System.out.println("Enter the filename");
                            String filename = s.nextLine();
                            System.out.println("Enter the course");
                            String course = s.nextLine();
                            Post p = new Post(filename, username, course, (discussionPosts.size() + ";"));

                            discussionPosts.add(p);
                            if (course.equals(response)) {
                                curatedPosts.add(p);
                            }
                        } else {
                            System.out.println("Invalid input");
                        }
                        break;
                    default:
                        System.out.println("Invalid input");
                        break;
                }
            } else {
                if (choice <= curatedPosts.size()) {
                    secondaryMenu(curatedPosts.get(choice - 1), teacher, username);
                } else {
                    System.out.println("Invalid input");
                }
            }

        } while (loop);
    }

    public static void secondaryMenu(Post post, boolean teacher, String username) {
        Scanner s = new Scanner(System.in);
        boolean loop = true;
        System.out.println("What would you like to do with this post");
        do {
            System.out.println(post.toString());
            System.out.println("1) view comments\n2) leave a comment");
            if (teacher) {
                System.out.println("3) edit post");
            }
            switch (Integer.parseInt(s.nextLine())) {
                case 0:
                    loop = false;
                    break;
                case 1:
                    for (int i = 0; i < post.getComments().size(); i++) {
                        System.out.println(post.getComments().get(i).toString());
                    }
                    System.out.println("\n\nWhat would you like to do with these replies");
                    System.out.println("0) back\nor enter the number of the reply");
                    int choice = Integer.parseInt(s.nextLine());
                    if (choice != 0) {
                        if (choice <= post.getComments().size()) {
                            secondaryMenu(post.getComments().get(choice - 1), teacher, username);
                        } else {
                            System.out.println("Invalid input");
                        }
                    }
                    break;
                case 2:
                    System.out.println("enter the filename containing the comment");
                    post.comment(s.nextLine(), username);
                    break;
                case 3:
                    if (teacher) {
                        System.out.println("would you like to use a 1) string or a 2) file");
                        StringBuilder bodytext = new StringBuilder();
                        switch (Integer.parseInt(s.nextLine())) {
                            case 1:
                                System.out.println("Enter the new bodytext");
                                post.setBodyText(s.nextLine());
                            case 2:
                                System.out.println("enter the filename containing the update");
                                File f = new File(s.nextLine());
                                if (f.exists()) {
                                    try (BufferedReader bfr = new BufferedReader(new FileReader(f))) {
                                        String line = bfr.readLine();
                                        while (line != null) {
                                            bodytext.append(line).append("\n");
                                            line = bfr.readLine();
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    post.setBodyText(bodytext.toString());
                                } else {
                                    System.out.println("invalid file name");
                                }
                        }
                    }
                    break;
            }

        } while (loop);
    }
}
