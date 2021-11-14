import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class PostTestCases {
    public static void main(String[] args) {
        /*
        Start of Post Class Test Cases
        */
        //Post newer = new Post("NewFile.txt", "Jake", "CS180", "1;1;1");


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
//      }
    }
}