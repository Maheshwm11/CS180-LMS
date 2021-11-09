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

            String response = s.nextLine();

        } while (loop);
    }
}
