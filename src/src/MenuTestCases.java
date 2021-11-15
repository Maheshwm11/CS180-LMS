import java.util.ArrayList;
import java.util.Scanner;

/**
 * Menu Test Cases
 *
 * Test Cases for the Menu Class
 *
 * @author Joseph Lee, LC5
 *
 * @version 11/14/21
 *
 */
public class MenuTestCases {
    public static void main(String[] args) {
        /*
        Start of Menus Class Test Cases
         */
        ArrayList<Post> masterPosts = new ArrayList<>();
        masterPosts.add(new Post("CS180.txt", "mrBob", "CS180", "1;1;1"));

        Menus menu = new Menus();
        
        /**
         * Beginning of setMasterPost and getMasterPost test case
         *
         * expected:
         *
         * [mrBob:
         * CS180.txt
         * posted: (no set in stone time)
         * comments: 0]
         *
         *
         * Result: (passes test case)
         *
         * [mrBob:
         * CS180.txt
         * posted: 2021-11-14 11:59:46.648
         * comments: 0]
         *
         */
        try {
            menu.setDiscussionPosts(masterPosts);
            System.out.println(menu.getDiscussionPosts());
        } catch (Exception e) {
            System.out.println("Make sure that your set and getter methods don't cause an error!");
            e.printStackTrace();
        }

        
        /**
         * Beginning of secondaryMenu() test case
         * Shows some menu options (Does not save any data)
         * (requires user input in terminal to test)
         *
         * expected (1st output - Not Teacher):
         * Result: same as expected (passes test case)
         *
         * What would you like to do with this post
         * CS18200:
         * ProfessorB:
         * CS182 stuff
         * posted: 2021-11-14 16:07:53.445
         * comments: 0
         * 0) exit to all posts
         * 1) view comments
         * 2) leave a comment
         * 2
         * Enter the comment. Write 'X' on a new line and press 'ENTER' to end
         * Thank you for the help!
         * X
         * CS18200:
         * ProfessorB:
         * CS182 stuff
         * posted: 2021-11-14 16:07:53.445
         * comments: 1
         * 0) exit to all posts
         * 1) view comments
         * 2) leave a comment
         * 1
         * 1) CS18200:
         * jimBob25:
         * Thank you for the help!
         * posted: 2021-11-14 16:08:04.83
         * comments: 0
         *
         *
         *
         * What would you like to do with these replies
         * 0) back
         * or enter the number of the reply
         * 0
         * CS18200:
         * ProfessorB:
         * CS182 stuff
         * posted: 2021-11-14 16:07:53.445
         * comments: 1
         * 0) exit to all posts
         * 1) view comments
         * 2) leave a comment
         * 1
         * 1) CS18200:
         * jimBob25:
         * Thank you for the help!
         * posted: 2021-11-14 16:08:04.83
         * comments: 0
         *
         *
         *
         * What would you like to do with these replies
         * 0) back
         * or enter the number of the reply
         * 1
         * What would you like to do with this post
         * CS18200:
         * jimBob25:
         * Thank you for the help!
         * posted: 2021-11-14 16:08:04.83
         * comments: 0
         * 0) exit to all posts
         * 1) view comments
         * 2) leave a comment
         *
         *
         * expected (2nd output - Is a teacher):
         *
         * What would you like to do with this post
         * CS18200:
         * ProfessorB:
         * CS182 stuff
         * posted: 2021-11-14 16:31:58.241
         * comments: 0
         * 0) exit to all posts
         * 1) view comments
         * 2) leave a comment
         * 3) edit post
         * 3
         * Would you like to use a 1) string or a 2) file
         * 1
         * Enter the new body text. Write 'X' on a new line to end
         * the new edited thing
         * X
         * CS18200:
         * ProfessorB:
         * the new edited thing
         * posted: 2021-11-14 16:31:58.241
         * comments: 0
         * 0) exit to all posts
         * 1) view comments
         * 2) leave a comment
         * 3) edit post
         * 3
         * Would you like to use a 1) string or a 2) file
         * 2
         * Enter the path to the file with the content without the name of the file
         * For example, write 'C:/USER/DATA' if file is in DATA folder
         * C:\Users\josep\Desktop\mews
         * Directory found!
         * Enter the filename with '.txt' suffix. For example, write 'sample.txt'
         * mews.txt
         * CS18200:
         * ProfessorB:
         * mews
         * asdfasd
         * asdf
         * asdfas
         * d
         * 12312
         * 24324
         *
         * $$
         *
         * posted: 2021-11-14 16:31:58.241
         * comments: 0
         * 0) exit to all posts
         * 1) view comments
         * 2) leave a comment
         * 3) edit post
         * 2
         * Enter the comment. Write 'X' on a new line and press 'ENTER' to end
         * this is my first comment!
         * X
         * CS18200:
         * ProfessorB:
         * mews
         * asdfasd
         * asdf
         * asdfas
         * d
         * 12312
         * 24324
         *
         * $$
         *
         * posted: 2021-11-14 16:31:58.241
         * comments: 1
         * 0) exit to all posts
         * 1) view comments
         * 2) leave a comment
         * 3) edit post
         * 1
         * 1) CS18200:
         * ProfessorB:
         * this is my first comment!
         * posted: 2021-11-14 16:33:04.611
         * comments: 0
         *
         *
         *
         * What would you like to do with these replies
         * 0) back
         * or enter the number of the reply
         * 1
         * What would you like to do with this post
         * CS18200:
         * ProfessorB:
         * this is my first comment!
         * posted: 2021-11-14 16:33:04.611
         * comments: 0
         * 0) exit to all posts
         * 1) view comments
         * 2) leave a comment
         * 3) edit post
         * 2
         * Enter the comment. Write 'X' on a new line and press 'ENTER' to end
         * Second comment
         * X
         * CS18200:
         * ProfessorB:
         * this is my first comment!
         * posted: 2021-11-14 16:33:04.611
         * comments: 1
         * 0) exit to all posts
         * 1) view comments
         * 2) leave a comment
         * 3) edit post
         * 1
         * 1) CS18200:
         * ProfessorB:
         * Second comment
         * posted: 2021-11-14 16:33:23.611
         * comments: 0
         *
         *
         *
         * What would you like to do with these replies
         * 0) back
         * or enter the number of the reply
         * 0
         * CS18200:
         * ProfessorB:
         * this is my first comment!
         * posted: 2021-11-14 16:33:04.611
         * comments: 1
         * 0) exit to all posts
         * 1) view comments
         * 2) leave a comment
         * 3) edit post
         * 0
         * CS18200:
         * ProfessorB:
         * mews
         * asdfasd
         * asdf
         * asdfas
         * d
         * 12312
         * 24324
         *
         * $$
         *
         * posted: 2021-11-14 16:31:58.241
         * comments: 1
         * 0) exit to all posts
         * 1) view comments
         * 2) leave a comment
         * 3) edit post
         *
         * Result: same as above (passes test case)
         *
         */
        Post discreteMath = new Post("CS182 stuff", "ProfessorB", "CS18200", "1;2;1");
        try {
            //menu.secondaryMenu(discreteMath, false, "jimBob25");
        } catch (Exception e) {
            System.out.println("Make sure you input a valid input!");
            e.printStackTrace();
        }

        try {
            Scanner s = new Scanner(System.in);
            menu.secondaryMenu(s, discreteMath, true, "ProfessorB");
        } catch (Exception e) {
            System.out.println("Make sure you input a valid input!");
            e.printStackTrace();
        }
    }
}
