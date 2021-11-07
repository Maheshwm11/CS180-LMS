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

        boolean loop = true;
        do {
            System.out.println("What course would you like to view\ntype all to view all courses");
            System.out.println("Course list");

            String response = s.nextLine();
            for (int i = 0; i < discussionPosts.size(); i++) {
                String course = discussionPosts.get(i).getCourse();
            }
        } while (loop);
    }
}
