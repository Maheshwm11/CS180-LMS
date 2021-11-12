import java.io.*;

public class AdminTestCases {
    public static void main(String[] args) {
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
        try {
            jpnsPost.comment("meancomment.txt", "jimBob25");
            mrsFuji.replyToStudent(jpnsPost, "meancomment.txt", "fujimountain2021", "JPNS101");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //TODO: test that it is correct
        /**
         * Beginning of importDiscussion test case
         *
         * expected:
         *
         *
         *
         * Result:
         *
         */
        try {
            File file = new File("Romanji.txt");
            mrsFuji.importDiscussion(file, "Romanji Lesson 1");
        } catch (IOException e) {
            System.out.println("An IOException was thrown!");
            e.printStackTrace();
        }

        //TODO: test that it is correct
        /**
         * Beginning of viewDashboard test case
         *
         * expected:
         *
         *
         *
         * Result:
         *
         */
        try {
            mrsFuji.viewDashBoard(jpnsPost);
        } catch (IOException e) {
            System.out.println("Could not print out the dashboard due to an IOException!");
            e.printStackTrace();
        }

        //TODO: test that it is correct
        /**
         * Beginning of sort test case
         *
         * expected:
         *
         *
         *
         * Result:
         *
         */
        try {
            mrsFuji.sort(jpnsPost);
            mrsFuji.viewDashBoard(jpnsPost);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //TODO: test that it is correct
        /**
         * Beginning of assignGrade test case
         *
         * expected:
         *
         *
         *
         * Result:
         *
         */
        try {
            User failingStudent = new User("badstudent1");
            mrsFuji.assignGrade(failingStudent, 0);
            System.out.println(failingStudent.getGrade());
        } catch (Exception e) {
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
    }
}
