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
    }
}
