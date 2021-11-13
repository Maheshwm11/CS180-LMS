//import java.util.ArrayList;
//
//public class MenuTestCases {
//    public static void main(String[] args) {
//        /*
//        Start of Menus Class Test Cases
//         */
//        ArrayList<Post> masterPosts = new ArrayList<>();
//        masterPosts.add(new Post("CS180.txt", "mrBob", "CS180"));
//
//        Menus menu = new Menus();
//        //TODO: test that it is correct
//        /**
//         * Beginning of setMasterPost and getMasterPost test case
//         *
//         * expected:
//         *
//         *
//         *
//         * Result:
//         *
//         */
//        try {
//            menu.setMasterPosts(masterPosts);
//            System.out.println(Menus.getMasterPosts());
//        } catch (Exception e) {
//            System.out.println("Make sure that your set and getter methods don't cause an error!");
//            e.printStackTrace();
//        }
//
//        //TODO: make sure test cases pass
//        /**
//         * Beginning of secondaryMenu() test case
//         *
//         * expected (1st output - Not Teacher):
//         *
//         *
//         *
//         * Result:
//         *
//         *
//         * expected (2nd output - Is a teacher):
//         *
//         * Result:
//         *
//         */
//        Post discreteMath = new Post("CS182.txt", "ProfessorB", "CS18200");
//        try {
//            menu.secondaryMenu(discreteMath, false, "jimBob25");
//        } catch (Exception e) {
//            System.out.println("Make sure you input a valid input!");
//            e.printStackTrace();
//        }
//
//        try {
//            menu.secondaryMenu(discreteMath, true, "ProfessorB");
//        } catch (Exception e) {
//            System.out.println("Make sure you input a valid input!");
//            e.printStackTrace();
//        }
//    }
//}
