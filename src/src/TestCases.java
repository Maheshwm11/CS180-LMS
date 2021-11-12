import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Arrays;

public class TestCases {
    public static void main(String[] args) {

       /*
        Start of Post Class Test Cases
        */
        Post newer = new Post("NewFile.txt", "Jake", "CS180");

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
            newer.setCourse("CS240");
            if (newer.getCourse().equals("CS240")) {
                System.out.println("Correct Output!");
            } else {
                System.out.println("Incorrect, Test Again!");
                System.out.println(newer.getCourse());
            }
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
        try {

            newer.comment("goodcomment.txt", "Jeff");
            newer.comment("meancomment.txt", "Amber");
            int counterWithComments = 0;

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

        Admin mrsFuji = new Admin("fujimountain2021");

        //TODO: test that it is correct
        /**
         * Beginning of editDiscussion() test case
         *
         * expected:
         *      "No Kanji for this week!"
         *
         *
         * Result:
         *
         */
        Post jpnsPost = new Post("Kanji.txt", "fujimountain2021", "JPNS101");
        try {
            mrsFuji.editDiscussion("No Kanji for this week!", "Kanji.txt", jpnsPost);
            //should replace current contents of Kanji.txt to "No Kanji for this week!"

            File f = new File(jpnsPost.getFileName());
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
        } catch (FileNotFoundException e) {
            System.out.println("The file to be edited can't be found!");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Reading input from newly edited file or closing the stream " +
                    "caused an error!");
            e.printStackTrace();
        }

        //TODO: test that it is correct
        //TODO: Check if discussion has actually been deleted
        /**
         * Beginning of deleteDiscussion() test case
         *
         * expected:
         *
         *
         *
         * Result:
         *
         */
        try {
            mrsFuji.deleteDiscussion(jpnsPost);
        } catch (Exception e) {
            System.out.println("Either the file couldn't be found or there was an issue with deletion!");
            e.printStackTrace();
        }

        //TODO: test that it is correct
        /**
         * Beginning of replyToStudent test case
         *
         * expected:
         *
         *
         *
         * Result:
         *
         */



        //TODO: methods that have not been implemented yet
        public void replyToStudent(Post post, String fileName, String poster, String course)
        public void importDiscussion(File file, String topicName) throws IOException
        public void viewDashBoard (Post post) throws IOException
        public void sort(Post post)
        public void assignGrade(User user, int newScore)

        /*
        Start of Data Class Test Cases
         */
            
        //TODO: test to see if it works properly
        /**
         * Beginning of writeFile test case
         * (Writes contents of file into a file in either a student or teacher directory)
         *
         * expected:
         *
         *
         *
         * Result:
         *
         */
        Data database = new Data();
        try {
            //should write "My first post!" to Calista.txt that will be in student directory under goober15 folder
            database.writeFile("goober15;password;student", "Calista", "My first post!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("There was an IOException somewhere!");
        }

        try {
            //should write "My first post!" to Kdog.txt that will be in teacher directory under Kdog folder
            database.writeFile("kdogiscool;password;teacher", "Kdog", "My first post!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("There was an IOException somewhere!");
        }

        //TODO: test to see if it works properly
        /**
         * Beginning of createPostFile test case
         * (Creates post file)
         *
         * expected:
         *
         *
         *
         * Result:
         *
         */
        try {
            database.createPostFile(1, "bober1;pswrd;student", "Hello!");
        } catch (Exception e) {
            System.out.println("There was an error creating the post!");
            e.printStackTrace();
        }

        //TODO: test to see if it works properly
        /**
         * Beginning of readFile test case
         * (Reads contents of file)
         *
         * expected:
         *
         *
         *
         * Result:
         *
         */
        try {
            System.out.println(database.readFile("Calista.txt"));
        } catch (Exception e) {
            System.out.println("There was an error reading from the file!");
            e.printStackTrace();
        }

        //TODO: test to see if it works properly
        /**
         * Beginning of getLoginFile and setLoginFile test case
         * (Reads contents of file)
         *
         * expected:
         *
         *
         *
         * Result:
         *
         */
        try {
            //should create Login Details.txt with param string as first line
            database.setLoginFile("firstusername;pswrdcool;teacher");
            database.setLoginFile("secondusername;12342323;student");

            //should print out contents of login Details.txt
            System.out.println(database.getLoginFile());
        } catch (Exception e) {
            System.out.println("There was either an error writing into login file or reading from it!");
            e.printStackTrace();
        }

        //TODO: test to see if it works properly
        /**
         * Beginning of getLoginFile and setLoginFile test case
         * (Reads contents of file)
         *
         * expected:
         *
         *
         *
         * Result:
         *
         */
        try {
            database.rebuildArrays();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There was an error rebuilding the arrayList!");
        }

        /*
        Start of Menus Class Test Cases
         */
        ArrayList<Post> masterPosts = new ArrayList<>();
        masterPosts.add(new Post("CS180.txt", "mrBob", "CS180"));

        Menus menu = new Menus();
        //TODO: test that it is correct
        /**
         * Beginning of setMasterPost and getMasterPost test case
         *
         * expected:
         *
         *
         *
         * Result:
         *
         */
        try {
            menu.setDiscussionPosts(masterPosts);
            System.out.println(Menus.getDiscussionPosts());
        } catch (Exception e) {
            System.out.println("Make sure that your set and getter methods don't cause an error!");
            e.printStackTrace();
        }

        //TODO: make sure test cases pass
        /**
         * Beginning of secondaryMenu() test case
         *
         * expected (1st output - Not Teacher):
         *
         *
         *
         * Result:
         *
         *
         * expected (2nd output - Is a teacher):
         *
         * Result:
         *
         */
        Post discreteMath = new Post("CS182.txt", "ProfessorB", "CS18200");
        try {
            menu.secondaryMenu(discreteMath, false, "jimBob25");
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

        /*
        Start of Login Class Test Cases
        */
    }
}
