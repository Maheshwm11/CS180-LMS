import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class PostTestCases {
    public static void main(String[] args) {
        /*
        Start of Post Class Test Cases
        */
        Post newer = new Post("Placer text for example", "Jake", "CS180", "1;1;1");

        /**
         * Beginning of upVote and DownVote test cases
         *
         * expected:
         * 2
         * 0
         * 1
         * 2
         *
         * Result: (Passes test case)
         * 2
         * 0
         * 1
         * 1
         */
        try {
            newer.upVote("mynameisBob"); //should create mynameisBob in ArrayList (0)
            newer.upVote("mynameisJeff"); //should create mynameisJeff in ArrayList (1)

            System.out.println(newer.getUpVotes()); //should print out 2
            System.out.println(newer.getDownVotes()); //should print out 0

            newer.downVote("mynameisJeff"); //should create mynameisAmazing in downVote ArrayList

            System.out.println(newer.getDownVotes()); //should print out 1
            System.out.println(newer.getUpVotes()); //should print out 1
        } catch (Exception e) {
            System.out.println("There has either been an ArrayListOutOfBounds Exception or a Format Exception!");
            e.printStackTrace();
        }

        /**
         * Beginning of getComments() and comment() test cases
         *
         * expected:
         * This is very insightful! Good job!
         *
         * you are a big poo poo head.
         *
         *
         * Result: (Passes test case)
         * This is very insightful! Good job!
         *
         * you are a big poo poo head.
         */
        try {

            newer.comment("goodcomment.txt", "Jeff");
            newer.comment("meancomment.txt", "Amber");
            String[] textFiles = new String[2];
            textFiles[0] = "goodcomment.txt";
            textFiles[1] = "meancomment.txt";
            int counterWithComments = 0;

            for (Post c : newer.getComments()) {
                File f = new File(textFiles[counterWithComments]);
                FileReader fis = new FileReader(f);
                BufferedReader reader = new BufferedReader(fis);
                while (true) {
                    String input = reader.readLine();
                    if (input == null) {
                        break;
                    } else {
                        System.out.println(input);
                    }
                    System.out.println();
                }
                counterWithComments++;
            }
        } catch (Exception e) {
            System.out.println("Stream has most likely been closed!");
            e.printStackTrace();
        }

        /**
         * Beginning of toString() test cases
         *
         * expected:
         *
         * CS180:
         * Jake:
         * Placer text for example
         * UV: 1 DV: 1
         * Posted: (no set in stone time)
         * comments: 2
         *
         * Result: (Passes test case)
         *
         * CS180:
         * Jake:
         * Placer text for example
         * UV: 1 DV: 1
         * posted: 2021-11-14 11:53:48.224
         * comments: 2
         */
        try {
            System.out.println(newer.toString());
        } catch (Exception e) {
            System.out.println("Most likely the .printf(); statement has been used incorrectly");
        }
    }
}
