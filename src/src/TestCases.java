import java.io.*;
import java.nio.Buffer;

public class TestCases {
    public static void main(String[] args) {
        /*
        Start of User Class Test Cases
        */
        
        User jimBob = new User("jimBob25");

        /**
         * Beginning of getGrade() and setGrade() test case
         *
         * expected: 95
         *
         *
         * Result: 95 (Passes Test Case)
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
        
        /*
        Start of Admin Class Test Cases
        */

        //TODO: test that it is correct
        /**
         * Beginning of editDiscussion() test case
         *
         * expected:
         *
         *
         * Result:
         *
         */

        /*
        Start of Post Class Test Cases
        */
        Post newer = new Post("NewFile.txt", "Jake", "CS180");

        /**
         * Beginning of getCourse Test case
         *
         * expected output: Correct Output!
         * if incorrect output: Incorrect, Test Again!
         *                      "Name of course"
         * result: Correct Output! (passes test case)
         */
        if(newer.getCourse().equals("CS180")) {
            System.out.println("Correct Output!");
        } else {
            System.out.println("Incorrect, Test Again!");
            System.out.println(newer.getCourse());
        }

        /**
         * Beginning of getFileName Test case
         *
         * expected output: Correct Output!
         * if incorrect output: Incorrect, Test Again!
         *                      "Name of fileName"
         * result: Correct Output! (passes test case)
         */
        if(newer.getFileName().equals("NewFile.txt")) {
            System.out.println("Correct Output!");
        } else {
            System.out.println("Incorrect, Test Again!");
            System.out.println(newer.getFileName());
        }

        /**
         * Beginning of getPoster Test case
         *
         * expected output: Jake is the correct poster name!
         * error output: This is not the poster name!
         *               "Name of poster that was from getPoster"
         * result: Jake is the correct poster name! (passes test case)
         */
        if(newer.getPoster().equals("Jake")) {
            System.out.println(newer.getPoster() + " is the correct poster name!");
        } else {
            System.out.println("This is not the poster name!");
            System.out.println(newer.getPoster());
        }

        //TODO: check if this test case passes
        /**
         * Beginning of setPoster Test case
         *
         * expected output: Bob is the correct poster name!
         * error output: This is not the poster name!
         *               "Name of poster that was from setPoster"
         * result:
         */
        newer.setPoster("Bob");
        if(newer.getPoster().equals("Bob")) {
            System.out.println(newer.getPoster() + " is the correct poster name!");
        } else {
            System.out.println("This is not the poster name!");
            System.out.println(newer.getPoster());
        }

        //TODO: check if testcases pass
        /**
         * Beginning of BodyTest Test cases for setBodyText and getBodyText
         *
         * expected:
         * This should add to the BodyTextTests.txt file
         * If it doesn't, there has been an issue
         *
         * In BodyTextTests.txt file contents:
         * This should add to the BodyTextTests.txt file
         * If it doesn't, there has been an issue
         *
         * Result:
         *
         */
        try {
            File tester = new File("setBodyText.txt");
            newer.setBodyText(tester);
            FileReader fis = new FileReader(tester);
            BufferedReader reader = new BufferedReader(fis);

            while (true) {
                String input = reader.readLine();
                if (input == null) {
                    break;
                } else {
                    System.out.println(input);
                }
            }
        } catch (Exception e) {
            System.out.println("There has been an exception!");
            e.printStackTrace();
        }

        //TODO: check if testcases pass
        /**
         * Beginning of upVote and DownVote test cases
         *
         * expected:
         *
         *
         * Result:
         *
         */
        try {
            newer.upVote("mynameisBob"); //should create mynameisBob in ArrayList (0)
            newer.upVote("mynameisJeff"); //should create mynameisJeff in ArrayList (1)

            System.out.println(newer.getUpVotes()); //should print out 2
            System.out.println(newer.getDownVotes()); //should print out 0

            newer.downVote("mynameisAmazing"); //should create mynameisAmazing in downVote ArrayList

            System.out.println(newer.getDownVotes()); //should print out 1
            System.out.println(newer.getUpVotes()); //should print out 1
        } catch (Exception e) {
            System.out.println("There has either been an ArrayListOutOfBounds Exception or a Format Exception!");
            e.printStackTrace();
        }

        //TODO: test that it is correct
        /**
         * Beginning of getComments() and comment() test cases
         *
         * expected:
         *
         *
         * Result:
         *
         */
        newer.comment("goodcomment.txt", "Jeff");
        newer.comment("meancomment.txt", "Amber");
        int counterWithComments = 0;

        try {
            for (Post c : newer.getComments()) {
                System.out.println("Post #" + counterWithComments);
                File f = new File(c.getFileName());
                FileReader fis = new FileReader(f);
                BufferedReader reader = new BufferedReader(fis);

                while (true) {
                    String input = reader.readLine();
                    if (input == null) {
                        break;
                    } else {
                        System.out.println(input);
                    }
                }
                reader.close();
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //TODO: test that it is correct
        /**
         * Beginning of toString() test case
         *
         * expected:
         *      Jake
         *      This should add to the BodyTextTests.txt file
         *      If it doesn't, there has been an issue
         *      UV: 1
         *      DV: 1
         *      Posted: *the live time of when posted, (no set in stone time)*
         *
         *
         * Result:
         *
         */
        try {
            System.out.println(newer.toString());
            
        } catch (Exception e) {
            System.out.println("Most likely to .printf(); statement has been used incorrectly");
            e.printStackTrace();
        }


        /*
        Start of Login Class Test Cases
        */
    }
}
