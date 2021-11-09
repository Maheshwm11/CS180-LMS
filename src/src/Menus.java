import java.util.ArrayList;
import java.util.Scanner;

public class Menus {
    private User user;
    private ArrayList<Post> discussionPosts;

    public Menus(User user, ArrayList discussionPosts) {
        this.user = user;
        this.discussionPosts = discussionPosts;
    }

    public void startMenus() {
        Scanner s = new Scanner(System.in);
        ArrayList<String> courses = new ArrayList<>();

        boolean loop = true;
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
                            }
                    }
                    break;
            }

        } while (loop);
    }
}
