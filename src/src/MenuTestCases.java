import java.util.ArrayList;

public class MenuTestCases {
    public static void main(String[] args) {
        /*
        Start of Menus Class Test Cases
         */
        ArrayList<Post> masterPosts = new ArrayList<>();
        masterPosts.add(new Post("CS180.txt", "mrBob", "CS180", "1;1;1"));

        Menus menu = new Menus();
        //TODO: test that it is correct
        /**
         * Beginning of setMasterPost and getMasterPost test case
         *
         * expected:
         *
         * [mrBob:
         * CS180.txt
         * UV: 0 DV: 0
         * posted: (no set in stone time)
         * comments: 0]
         *
         *
         * Result: (passes test case)
         *
         * [mrBob:
         * CS180.txt
         * UV: 0 DV: 0
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

        //TODO: make sure test cases pass
        /**
         * Beginning of secondaryMenu() test case
         * Shows some menu options (Does not save any data)
         * (requires user input in terminal to test)
         *
         * expected (1st output - Not Teacher):
         *
         * What would you like to do with this post
         * ProfessorB:
         * CS182 stuff
         * UV: 0 DV: 0
         * posted: 2021-11-14 12:13:00.294
         * comments: 0
         * 0) exit to all posts
         * 1) view comments
         * 2) leave a comment
         * 2
         * enter the comment
         * Thank you for the help!
         * ProfessorB:
         * CS182 stuff
         * UV: 0 DV: 0
         * posted: 2021-11-14 12:13:00.294
         * comments: 1
         * 0) exit to all posts
         * 1) view comments
         * 2) leave a comment
         * 1
         * 0) jimBob25:
         * Thank you for the help!
         * UV: 0 DV: 0
         * posted: 2021-11-14 12:14:34.055
         * comments: 0
         *
         *
         *
         * What would you like to do with these replies
         * 0) back
         * or enter the number of the reply
         * 0
         * ProfessorB:
         * CS182 stuff
         * UV: 0 DV: 0
         * posted: 2021-11-14 12:13:00.294
         * comments: 1
         * 0) exit to all posts
         * 1) view comments
         * 2) leave a comment
         * 1
         * 0) jimBob25:
         * Thank you for the help!
         * UV: 0 DV: 0
         * posted: 2021-11-14 12:14:34.055
         * comments: 0
         *
         *
         *
         * What would you like to do with these replies
         * 0) back
         * or enter the number of the reply
         * 1
         * What would you like to do with this post
         * jimBob25:
         * Thank you for the help!
         * UV: 0 DV: 0
         * posted: 2021-11-14 12:14:34.055
         * comments: 0
         * 0) exit to all posts
         * 1) view comments
         * 2) leave a comment
         * 0
         * ProfessorB:
         * CS182 stuff
         * UV: 0 DV: 0
         * posted: 2021-11-14 12:13:00.294
         * comments: 1
         * 0) exit to all posts
         * 1) view comments
         * 2) leave a comment
         *
         *
         * Result: (passes test case)
         *
         * What would you like to do with this post
         * ProfessorB:
         * CS182 stuff
         * UV: 0 DV: 0
         * posted: 2021-11-14 12:13:00.294
         * comments: 0
         * 0) exit to all posts
         * 1) view comments
         * 2) leave a comment
         * 2
         * enter the comment
         * Thank you for the help!
         * ProfessorB:
         * CS182 stuff
         * UV: 0 DV: 0
         * posted: 2021-11-14 12:13:00.294
         * comments: 1
         * 0) exit to all posts
         * 1) view comments
         * 2) leave a comment
         * 1
         * 0) jimBob25:
         * Thank you for the help!
         * UV: 0 DV: 0
         * posted: 2021-11-14 12:14:34.055
         * comments: 0
         *
         *
         *
         * What would you like to do with these replies
         * 0) back
         * or enter the number of the reply
         * 0
         * ProfessorB:
         * CS182 stuff
         * UV: 0 DV: 0
         * posted: 2021-11-14 12:13:00.294
         * comments: 1
         * 0) exit to all posts
         * 1) view comments
         * 2) leave a comment
         * 1
         * 0) jimBob25:
         * Thank you for the help!
         * UV: 0 DV: 0
         * posted: 2021-11-14 12:14:34.055
         * comments: 0
         *
         *
         *
         * What would you like to do with these replies
         * 0) back
         * or enter the number of the reply
         * 1
         * What would you like to do with this post
         * jimBob25:
         * Thank you for the help!
         * UV: 0 DV: 0
         * posted: 2021-11-14 12:14:34.055
         * comments: 0
         * 0) exit to all posts
         * 1) view comments
         * 2) leave a comment
         * 0
         * ProfessorB:
         * CS182 stuff
         * UV: 0 DV: 0
         * posted: 2021-11-14 12:13:00.294
         * comments: 1
         * 0) exit to all posts
         * 1) view comments
         * 2) leave a comment
         *
         * expected (2nd output - Is a teacher):
         *
         * Result:
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
            menu.secondaryMenu(discreteMath, true, "ProfessorB");
        } catch (Exception e) {
            System.out.println("Make sure you input a valid input!");
            e.printStackTrace();
        }
    }
}
