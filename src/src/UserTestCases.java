public class UserTestCases {
    public static void main(String[] args) {
        /*
        Start of User Class Test Cases
        */

        User jimBob = new User("jimBob25");

        /**
         * Beginning of getGrade() and setGrade() test case
         *
         * expected:
         *      The grade is correct!
         *
         *
         * Result: The grade is correct! (Passes Test Case)
         *
         */
        try {
            jimBob.setGrade(95); //should set grade to 95
            if(jimBob.getGrade() != 95) {
                System.out.println("The grade is not" + jimBob.getGrade());
            } else {
                System.out.println("The grade is correct!");
            }
        } catch (Exception e) {
            System.out.println("Make sure that setGrade and getGrade are " +
                    "implemented correctly!");
            e.printStackTrace();
        }

        //TODO: not tested yet
        /**
         * Beginning of getUsername() test case
         *
         * expected:
         *
         *
         * Result:
         *
         */
        try {
            if(jimBob.getUsername().equals("jimBob25")) {
                System.out.println("The username is correct!\n" + jimBob.getUsername());
            } else {
                System.out.println("The grade is not" + jimBob.getUsername());
            }
        } catch (Exception e) {
            System.out.println("Make sure that getUsername() is implemented properly!");
            e.printStackTrace();
        }

        //TODO: Make sure to test
        /**
         * Beginning of replyWithFile() test case
         *
         * expected:
         *
         *
         * Result:
         *
         */
        try {
            Post post = new Post("Project1.txt", "jimBob25", "CS180");
            jimBob.replyWithFile(post, "meancomment.txt");
            //should post meancomment.txt to post object

            System.out.println(post.getComments());
            //should print out contents of meancomment.txt
        } catch (Exception e) {
            System.out.println("Make sure there are no errors in either replyWithFile or " +
                    "getComments!");
            e.printStackTrace();
        }

        //TODO: implement test case when method is completely written
        /**
         * Beginning of replyWithString test case
         *
         * expected:
         *
         *
         * Result:
         *
         */
    }
}
