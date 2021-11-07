import java.util.Scanner;

public class Menus {
    private User user;

    public Menus(User user) {
        this.user = user;
    }

    public void startMenus() {
        Scanner s = new Scanner(System.in);


        boolean loop = true;
        do {
            System.out.println("pick");
            System.out.println("view posts");
            switch (s.nextLine()) {

            }
        } while (loop);
    }
}
