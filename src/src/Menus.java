import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Menus {
    private static ArrayList<Post> discussionPosts = new ArrayList<>();

    public static ArrayList<Post> getDiscussionPosts() {
        return discussionPosts;
    }

    public void setDiscussionPosts(ArrayList<Post> discussionPosts) {
        this.discussionPosts = discussionPosts;
    }

    public static void main(String[] args) {
        //discussionPosts.add(new Post("src/src/1.txt", "gamer", "gaming"));
        // This should be imported from a file
        // usernames and passwords should be formatted like %s;%s
        // as a result usernames should not be allowed to contain a ;
        // ; is replaceable with any given unicode
        ArrayList<String> logins = new ArrayList<>();
        logins.add("gamer;semicolon;teacher");

        Scanner s = new Scanner(System.in);

        boolean loop = true;
        int choice = 0;
        String type = "";
        String identification = "";
        User user = new User("");

        loop = true;
        String password = "";
        String username = "";
        String truePassword = "";
        boolean teacher = false;

        do {
            System.out.println("Login (1) or Make new Acct (2)");
            choice = Integer.parseInt(s.nextLine());
            if (choice == 1 || choice == 2) {
                loop = false;
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
                    System.out.println("Enter your username, leave blank to exit");
                    username = s.nextLine();
                    if (username.equals("")) {
                        break;
                    }
                    for (int i = 0; i < logins.size(); i++) {
                        String[] login = logins.get(i).split(";");
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
                do {
                    System.out.println("Enter your password");
                    password = s.nextLine();
                    if (truePassword.equals(password)) {
                        loop = false;
                        System.out.println("Success");
                        if (teacher) {
                            user = new Admin(username);
                        } else {
                            user = new User(username);
                        }
                    }
                } while (loop);
                break;

            // creating a new account
            case 2:
                // create username for new account
                do {
                    System.out.println("Create your username");
                    username = s.nextLine();
                    // ensure username doesnt have any semicolons
                    if (username.contains(";")) {
                        System.out.println("Username can not contain a semicolon(;). Please enter a new username.");
                    }
                    if (username.equals("")) {
                        System.out.println("Usernames cannot be blank");
                    }
                } while(!username.contains(";") && !username.equals(""));
                // create password for new account
                do {
                    System.out.println("Create your password");
                    password = s.nextLine();
                    // ensure username doesnt have any semicolons
                    if (password.contains(";")) {
                        System.out.println("Password can not contain a semicolon(;). Please enter a new password.");
                    }
                    if (password.equals("")) {
                        System.out.println("Password cannot be blank");
                    }
                } while(!password.contains(";") && !password.equals(""));
                // save username and password into identification variable
                identification = username + ";" + password + ";";
                // user identifies as either student or teacher
                System.out.println("Are you a student or a teacher?");
                type = s.nextLine().toLowerCase();
                switch (type) {
                    case "student":
                        identification += "student";
                        user = new User(username);
                        break;
                    case "teacher":
                        identification += "teacher";
                        user = new Admin(username);
                        break;
                }
                // adding username, password, and role into login arraylist
                logins.add(identification);
        }

        // menus

        ArrayList<String> courses = new ArrayList<>();

        loop = true;
        String response = "";
        do {
            System.out.println("What course would you like to view\ntype all to view all courses");
            System.out.println("Course list");

            for (int i = 0; i < discussionPosts.size(); i++) {
                String course = discussionPosts.get(i).getCourse();
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

        for (int i = 0; i < discussionPosts.size(); i++) {
            String course = discussionPosts.get(i).getCourse();
            if (response.equals(course) || response.equals("all")) {
                curatedPosts.add(discussionPosts.get(i));
            }
        }

        do {
            for (int i = 0; i < curatedPosts.size(); i++) {
                System.out.println(curatedPosts.get(i).toString());
            }
            System.out.println("\n\nEnter the number of the post to view more details");
            System.out.println("Enter 0 to see advanced options");

            choice = Integer.parseInt(s.nextLine());
            switch (choice) {
                case 0:
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
                                curatedPosts.add(new Post(filename, username, course));

                            } else {
                                System.out.println("Invalid input");
                            }
                            break;
                        default:
                            System.out.println("Invalid input");
                            break;
                    }
                    break;
                default:
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
            System.out.println("0) view comments\n1) leave a comment");
            if (teacher) {
                System.out.println("2) edit post");
            }
            switch (Integer.parseInt(s.nextLine())) {
                case 0:
                    for (int i = 0; i < post.getComments().size(); i++) {
                        System.out.println(post.getComments().get(i).toString());
                    }
                    System.out.println("\n\nWhat would you like to do with these replies");
                    System.out.println("0) back\nor enter the number of the reply");
                    int choice = Integer.parseInt(s.nextLine());
                    switch (choice) {
                        case 0:
                            break;
                        default:
                            if (choice <= post.getComments().size()) {
                                secondaryMenu(post.getComments().get(choice - 1), teacher, username);
                            } else {
                                System.out.println("Invalid input");
                            }
                    }
                    break;
                case 1:
                    System.out.println("enter the filename containing the comment");
                    post.comment(s.nextLine(), username);
                    break;
                case 2:
                    if (teacher) {
                        System.out.println("enter the filename containing the update");
                        post.setBodyText(new File(s.nextLine()));
                    }
                    break;
            }

        } while (loop);
    }
}
