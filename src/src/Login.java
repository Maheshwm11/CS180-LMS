import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Login {

    public static void main(String[] args) {
        // arraylists function as follows
        // outermost layer is the posts
        // middle layer is the comments
        // inner layer is replies to comments
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
        User user;

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
                    if (username.contains(";")) {
                        System.out.println("Username can not contain a semicolon(;). Please enter a new username.");
                    }
                } while(!username.contains(";"));
                // create password for new account
                System.out.println("Create your password");
                password = s.nextLine();
                identification = username + ";" + password + ";";
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
                logins.add(identification);
        }

        loop = true;
        do {
            System.out.println("Choose an option");
            System.out.println("1) view ");
        } while (loop);

        // dont touch
        Menus m = new Menus(user);
        m.startMenus();
    }
}
