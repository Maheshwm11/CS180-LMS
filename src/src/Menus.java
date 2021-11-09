import java.util.ArrayList;
import java.util.Scanner;

public class Menus {
    private User user;
    private ArrayList<Post> discussionPosts;

    public Menus(User user, ArrayList discussionPosts) {
        this.user = user;
        this.discussionPosts = discussionPosts;
    }

    public static void main(String[] args) {
        ArrayList<Post> discussionPosts = new ArrayList<>();
        // This should be imported from a file
        // usernames and passwords should be formatted like %s;%s
        // as a result usernames should not be allowed to contain a ;
        // ; is replaceable with any given unicode
        ArrayList<String> logins = new ArrayList<>();

        Scanner s = new Scanner(System.in);

        boolean loop = true;
        int choice = 0;
        String type = "";
        String identification = "";
        User user = new User("");

        do {
            System.out.println("Login (1) or Make new Acct (2)");
            choice = Integer.parseInt(s.nextLine());
            if (choice == 1 || choice == 2) {
                loop = false;
            } else {
                System.out.println("invalid input");
            }
        } while (loop);

        loop = true;
        String username;
        String password;
        String truePassword = "";
        boolean teacher = false;

        // implement a cancel system
        switch (choice) {
            // logging into account
            case 1:
                do {
                    System.out.println("Enter your username");
                    username = s.nextLine();
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
                            user = new User(username);
                        } else {
                            user = new Admin(username);
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
                } while(!username.contains(";"));
                // create password for new account
                System.out.println("Create your password");
                password = s.nextLine();
                // save username and password into identification variable
                identification = username + ";" + password + ";";
                // user identifies as either student or teacher
                System.out.println("Are you a student or a teacher?");
                type = s.nextLine();
                type = type.toLowerCase();
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

            switch (Integer.parseInt(s.nextLine())) {
                case 0:
                    System.out.println("0) exit");
                    if (user instanceof Admin) {
                        System.out.println("1) Create new discussionPost");
                    }
                    switch (Integer.parseInt(s.nextLine())) {
                        case 0:
                            System.out.println("Exiting");
                            loop = false;
                            break;
                        case 1:
                            if (user instanceof Admin) {
                                System.out.println("Enter the filename");
                                String filename = s.nextLine();
                                System.out.println("Enter the course");
                                String course = s.nextLine();
                            }
                    }
                    break;
            }

        } while (loop);
    }
}
