import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class PostTestCases {
    public static void main(String[] args) {
        /*
        Start of Post Class Test Cases
        */
        //Post newer = new Post("NewFile.txt", "Jake", "CS180");

        //TODO: test if it works
        /**
         * Beginning of getCourse and setCourse Test case
         *
         * expected output: Correct Output!
         * if incorrect output: Incorrect, Test Again!
         *                      "Name of course"
         * result:
         */

        try {
            //newer.setCourse("CS240");
           // if (newer.getCourse().equals("CS240")) {
                System.out.println("Correct Output!");
           // } else {
                System.out.println("Incorrect, Test Again!");
               // System.out.println(newer.getCourse());
           // }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Make sure that setCourse changes the course of post and getCourse retrieves correct " +
                    "field!");
        }

        /**
         * Beginning of getFileName Test case
         *
         * expected output: Correct Output!
         * if incorrect output: Incorrect, Test Again!
         *                      "Name of fileName"
         * result: Correct Output! (passes test case)
         */
        //if(newer.getFileName().equals("NewFile.txt")) {
            System.out.println("Correct Output!");
        //} else {
            System.out.println("Incorrect, Test Again!");
            //System.out.println(newer.getFileName());
        }

        /**
         * Beginning of getPoster Test case
         *
         * expected output: Jake is the correct poster name!
         * error output: This is not the poster name!
         *               "Name of poster that was from getPoster"
         * result: Jake is the correct poster name! (passes test case)
         */
        //if(newer.getPoster().equals("Jake")) {
            //System.out.println(newer.getPoster() + " is the correct poster name!");
        //} else {
           // System.out.println("This is not the poster name!");
            //System.out.println(newer.getPoster());
        //}

        //TODO: check if this test case passes
        /**
         * Beginning of setPoster Test case
         *
         * expected output: Bob is the correct poster name!
         * error output: This is not the poster name!
         *               "Name of poster that was from setPoster"
         * result:
         */
        //newer.setPoster("Bob");
        //if(newer.getPoster().equals("Bob")) {
           // System.out.println(newer.getPoster() + " is the correct poster name!");
        //} else {
           //System.out.println("This is not the poster name!");
            //System.out.println(newer.getPoster());
        //}

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
        //try {
            //File tester = new File("setBodyText.txt");
            //newer.setBodyText(tester);
            //FileReader fis = new FileReader(tester);
            //BufferedReader reader = new BufferedReader(fis);

            //while (true) {
               // String input = reader.readLine();
               // if (input == null) {
                  //  break;
                //} else {
                  //  System.out.println(input);
               // }
            //}
        //} catch (Exception e) {
            //System.out.println("There has been an exception!");
            //e.printStackTrace();
        //}

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
       // try {
           // newer.upVote("mynameisBob"); //should create mynameisBob in ArrayList (0)
           // newer.upVote("mynameisJeff"); //should create mynameisJeff in ArrayList (1)

            //System.out.println(newer.getUpVotes()); //should print out 2
           // System.out.println(newer.getDownVotes()); //should print out 0

            //newer.downVote("mynameisAmazing"); //should create mynameisAmazing in downVote ArrayList

           // System.out.println(newer.getDownVotes()); //should print out 1
            //System.out.println(newer.getUpVotes()); //should print out 1
        //} catch (Exception e) {
           // System.out.println("There has either been an ArrayListOutOfBounds Exception or a Format Exception!");
           // e.printStackTrace();
        //}

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
        //try {

           // newer.comment("goodcomment.txt", "Jeff");
            //newer.comment("meancomment.txt", "Amber");
            //int counterWithComments = 0;

           //for (Post c : newer.getComments()) {
                //System.out.println("Post #" + counterWithComments);
                //File f = new File(c.getFileName());
                //FileReader fis = new FileReader(f);
                //BufferedReader reader = new BufferedReader(fis);

                //while (true) {
                   // String input = reader.readLine();
                    //if (input == null) {
                       // break;
                    //} else {
                       // System.out.println(input);
                    }
               // }
               // reader.close();
               // System.out.println();
            //}
        //} catch (Exception e) {
          //  e.printStackTrace();
       // }

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
       // try {
           // System.out.println(newer.toString());

        //} catch (Exception e) {
         //   System.out.println("Most likely to .printf(); statement has been used incorrectly");
         //   e.printStackTrace();
       // }

        //TODO: test that it is correct
        /**
         * Beginning of parseBodyText() test case
         *
         * expected:
         *      This should add to the BodyTextTests.txt file
         *      If it doesn't, there has been an issue
         *
         *
         *
         * Result:
         *
         */
       // try {
          // File f = new File("setBodyText.txt");
           // System.out.println(newer.parseBodyText(f));
       // } catch (Exception e) {
         //   System.out.println("Could not read file contents!");
       //     e.printStackTrace();
       // }
   // }
//}
