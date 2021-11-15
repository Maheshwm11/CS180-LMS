import java.io.*;
import java.util.ArrayList;
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
        // usernames and passwords should be formatted like %s;%s
        // as a result usernames should not be allowed to contain a ;
        // ; is replaceable with any given unicode
        Data data = new Data();
        ArrayList<String> logins = data.getLoginFile();
        Scanner s = new Scanner(System.in);
        //discussionPosts.add(new Post("Sample content", "Sample Poster", "Sample Course", "1;1"));
        //data.createPostFile(discussionPosts);
        discussionPosts = data.readPostFile();
        ArrayList<String> grades = data.getGrades();

        boolean loop;
        boolean loop1 = false;
        int choice = 0;
        String identification = "";
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
            do {
                System.out.println("Login (1), Make new Acct (2), Edit Account(3), Delete Acct (4)");
                try { //catches if not an integer
                    choice = Integer.parseInt(s.nextLine());

                    // if user doesn't input a valid option
                    if (choice == 1 || choice == 2 || choice == 3 || choice == 4) {
                        loop = false;
                        // if user doesn't input a valid option
                    } else {
                        System.out.println("Invalid input");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Enter a valid number! No letters are permitted!");
                }

            } while (loop);

            // implement a cancel system
            loop = true;
            switch (choice) {
                // logging into account
                case 1 -> {
                    do {
                        System.out.println("Enter your username");
                        username = s.nextLine();
                        if (username.equals("")) {
                            System.out.println("Username cannot be blank");
                        } else {
                            for (String value : logins) {
                                String[] login = value.split(";");
                                if (username.equals(login[0])) {
                                    loop = false;
                                    truePassword = login[1];
                                    if (login[2].equals("teacher")) {
                                        teacher = true;
                                    }
                                    identification = value;
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
                        } else {
                            System.out.println("Incorrect password. Please try again");
                        }
                    } while (loop);
                }

                // creating a New Account
                case 2 -> {
                    // create username for new account
                    newAccountLoop = false;
                    do {
                        System.out.println("Create your username");
                        username = s.nextLine();
                        if (username.equals("") || username.contains(";")) {
                            System.out.println("Username cannot be blank and cannot contain a " +
                                    "semicolon(;). Please enter a new username.");
                            newAccountLoop = true;
                        } else
                            newAccountLoop = false;
                        for (String value : logins) {
                            String[] login = value.split(";");
                            if (username.equals(login[0])) {
                                newAccountLoop = true;
                                System.out.println("Username is already taken. Please enter a new one");
                            }
                        }
                    } while (newAccountLoop);

                    // create password for new account
                    do {
                        System.out.println("Create your password");
                        password = s.nextLine();
                        // ensure username doesn't have any semicolons
                        if (password.contains(";") || password.equals("")) {
                            System.out.println("Password cannot be blank and cannot contain a " +
                                    "semicolon(;). Please enter a new password.");
                            newAccountLoop = true;
                        } else
                            newAccountLoop = false;
                    } while (newAccountLoop);
                    // save username and password into identification variable

                    // user identifies as either student or teacher
                    System.out.println("Are you a student or a teacher?");
                    role = s.nextLine().toLowerCase();
                    if (role.equals("teacher")) {
                        teacher = true;
                        identification = username + ";" + password + ";" + role;
                    } else
                        identification = username + ";" + password + ";" + role;
                    // adding username, password, and role into login arraylist
                    logins.add(identification);
                }

                // editing an account
                case 3 -> {
                    // enter username
                    do {
                        System.out.println("Enter your username");
                        username = s.nextLine();
                        if (username.equals("")) {
                            System.out.println("Username cannot be blank");
                        } else {
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
                        // ensure username doesn't have any semicolons
                        if (newUsername.equals("")) {
                            System.out.println("Usernames cannot be blank");
                            loop = true;
                        } else if (newUsername.contains(";")) {
                            System.out.println("Username can not contain a semicolon(;). Please enter a new username.");
                            loop = true;
                        }
                        for (String value : logins) {
                            String[] login = value.split(";");
                            if (newUsername.equals(login[0])) {
                                System.out.println("Username is already taken. Please enter a new one");
                                loop = true;
                                break;
                            } else
                                loop = false;
                        }
                    } while (loop);

                    // enter new password
                    do {
                        System.out.println("Create your new password");
                        newPassword = s.nextLine();
                        // ensure username doesn't have any semicolons
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
                }


                // deleting an account
                case 4 -> {
                    do {
                        System.out.println("Enter your username");
                        username = s.nextLine();
                        if (username.equals("")) {
                            System.out.println("Username cannot be blank");
                        } else {
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
                        }
                    } while (loop);
                    loop = true;
                    do {
                        System.out.println("Enter your password");
                        password = s.nextLine();
                        if (truePassword.equals(password)) {
                            System.out.println("Success!");
                            loop = false;
                            for (int i = 0; i < logins.size(); i++) {
                                if (logins.get(i).equals(username + ";" + password + ";student")
                                        || logins.get(i).equals(username + ";" + password + ";teacher")) {
                                    logins.remove(i);
                                }
                            }
                        } else {
                            System.out.println("Incorrect password. Please try again.");
                        }
                    } while (loop);
                    data.setLoginFile(logins);
                }
            }
        } while (choice == 4);
        data.setLoginFile(logins);
        // menus

        loop = true;
        String response;
        System.out.println("Success!");
        do {
            do {
                System.out.println("What course would you like to view? (type course name)\nType all to view all courses");
                System.out.println("Course list");
                ArrayList<String> courses = new ArrayList<>();
                for (int i = discussionPosts.size() - 1; 0 <= i; i--) {
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
                    System.out.println("Invalid choice");
                }
            } while (loop);
            loop = true;
            ArrayList<Post> curatedPosts = new ArrayList<>();

            for (int i = discussionPosts.size() - 1; 0 <= i; i--) {
                String course = discussionPosts.get(i).getCourse();
                if (response.equals(course) || response.equals("all")) {
                    curatedPosts.add(discussionPosts.get(i));
                }
            }

            do {
                for (int i = curatedPosts.size() - 1; 0 <= i; i--) {
                    System.out.println(i + 1 + ") " + curatedPosts.get(i).toString());
                }
                System.out.println("\n\nEnter the number of the post to view more details");
                System.out.println("Enter 0 to see advanced options");

                try {
                    choice = Integer.parseInt(s.nextLine());
                    if (choice == 0) {
                        System.out.println("0) Back to post");
                        System.out.println("1) Exit to course list");
                        System.out.println("2) Exit program");
                        if (teacher) {
                            System.out.println("3) Grade Student");
                            System.out.println("4) Create new discussionPost");
                        } else {
                            System.out.println("3) See your grade");
                        }
                        switch (Integer.parseInt(s.nextLine())) {
                            case 0:
                                break;
                            case 1:
                                System.out.println("Going back to the main course list...");
                                loop = false;
                                loop1 = true;
                                break;
                            case 2:
                                System.out.println("Exiting...");
                                loop = false;
                                loop1 = false;
                                break;
                            case 3:
                                if (teacher) {
                                    System.out.println("Pick a student");
                                    ArrayList<String> students = new ArrayList<>();
                                    for (int i = 0; i < logins.size(); i++) {
                                        String[] login = logins.get(i).split(";");
                                        if (login[2].equals("student")) {
                                            System.out.println(login[0]);
                                            students.add(logins.get(i) + ";" + i);
                                        }
                                    }
                                    boolean loop2 = true;
                                    String studentID = "";
                                    String student = "";
                                    do {
                                        student = s.nextLine();
                                        for (int i = 0; i < students.size(); i++) {
                                            if (students.get(i).split(";")[0].equals(student)) {
                                                loop2 = false;
                                                studentID = students.get(i);
                                            }
                                        }
                                    } while (loop2);

                                    System.out.println("All posts by " + student);
                                    for (int i = 0; i < discussionPosts.size(); i++) {
                                        for (int ii = 0; ii < discussionPosts.get(i).getComments().size(); ii++) {
                                            if (discussionPosts.get(i).getComments().get(ii).getPoster().equals(student)) {
                                                System.out.println(discussionPosts.get(i).getComments().get(ii).toString());
                                            }
                                        }
                                    }

                                    loop2 = true;
                                    int grade = 0;
                                    do {
                                        System.out.println("Enter a grade (0-100)");
                                        try {
                                            grade = Integer.parseInt(s.nextLine());
                                            if (grade < 0 || grade > 100) {
                                                System.out.println("Invalid integer");
                                            } else {
                                                loop2 = false;
                                            }
                                        } catch (NumberFormatException e) {
                                            System.out.println("Enter an integer");
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
                                            System.out.println("Your grade is " + grade[1]);
                                            once = false;
                                        }
                                    }
                                    if (once)
                                        System.out.println("You have not been graded yet.");
                                }
                                break;
                            case 4:
                                if (teacher) {
                                    System.out.println("Enter the title of the post");
                                    String filename = s.nextLine();
                                    System.out.println("Enter the course name");
                                    String course = s.nextLine();
                                    Post p = new Post(filename, username, course, (discussionPosts.size() + ";"));

                                    discussionPosts.add(p);
                                    if (course.equals(response) || response.equals("all")) {
                                        curatedPosts.add(p);
                                    }
                                } else {
                                    System.out.println("Permission not granted");
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
                } catch (NumberFormatException e) {
                    System.out.println("No letters are permitted!");
                }
            } while (loop);
        } while (loop1);
        data.createPostFile(discussionPosts);
    }

    public static void secondaryMenu(Post post, boolean teacher, String username) {
        Scanner s = new Scanner(System.in);
        boolean loop = true;
        System.out.println("What would you like to do with this post");
        do {
            System.out.println(post.toString());
            System.out.println("0) exit to all posts\n1) view comments\n2) leave a comment");
            if (teacher) {
                System.out.println("3) edit post");
            }
            switch (Integer.parseInt(s.nextLine())) {
                case 0:
                    loop = false;
                    break;
                case 1:
                    for (int i = post.getComments().size() - 1; 0 <= i; i--) {
                        System.out.println(i + 1 + ") " + post.getComments().get(i).toString());
                        System.out.print("\n");
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
                    System.out.println("Would you like to use a 1) string or a 2) file");
                    StringBuilder bodytext = new StringBuilder();
                    switch (Integer.parseInt(s.nextLine())) {
                        case 1:
                            System.out.println("Enter the comment. Write 'X' on a new line to end");
                            ArrayList<String> input1 = new ArrayList<>();
                            String line2 = s.nextLine();
                            while (line2 != null && !line2.equals("X")) {
                                input1.add(line2);
                                line2 = s.nextLine();
                            }
                            post.comment(String.join("\n", input1), username);
                            break;
                        case 2:
                            System.out.println("Enter the path to the file with the content without the name of the file");
                            System.out.println("For example, write 'C:\\USER\\DATA' if file is in DATA folder");
                            String dirName = s.nextLine();
                            File dir = new File(dirName);
                            if (!dir.exists()) {
                                System.out.println("Directory not found");
                                break;
                            }
                            System.out.println("Directory found!");
                            System.out.println("Enter the filename with '.txt' suffix. For example, write 'sample.txt'");
                            File f = new File(dir, s.nextLine());
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
                                post.comment(bodytext.toString(), username);
                            } else {
                                System.out.println("Invalid file name");
                            }
                            break;
                    }
                    break;
                case 3:
                    if (teacher) {
                        System.out.println("Would you like to use a 1) string or a 2) file");
                        bodytext = new StringBuilder();
                        switch (Integer.parseInt(s.nextLine())) {
                            case 1:
                                System.out.println("Enter the new body text. Write 'X' on a new line to end");
                                ArrayList<String> input1 = new ArrayList<>();
                                String line2 = s.nextLine();
                                while (line2 != null && !line2.equals("X")) {
                                    input1.add(line2);
                                    line2 = s.nextLine();
                                }
                                post.setBodyText(String.join("\n", input1));
                                break;
                            case 2:
                                System.out.println("Enter the path to the file with the content without the name of the file");
                                System.out.println("For example, write 'C:\\USER\\DATA' if file is in DATA folder");
                                String dirName = s.nextLine();
                                File dir = new File(dirName);
                                if (!dir.exists()) {
                                    System.out.println("Directory not found");
                                    break;
                                }
                                System.out.println("Directory found!");
                                System.out.println("Enter the filename with '.txt' suffix. For example, write 'sample.txt'");
                                File f = new File(dir, s.nextLine());
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
                                    System.out.println("Invalid file name");
                                }
                                break;
                        }
                    } else
                        System.out.println("Permission not granted");
                    break;
            }

        } while (loop);
    }
}
