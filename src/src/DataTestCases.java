public class DataTestCases {
    public static void main(String[] args) {
        /*
        Start of Data Class Test Cases
         */

        Data database = new Data();

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
            //database.createPostFile("1;1;r", "bober1;pswrd;student", "Hello!");
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
           // System.out.println(database.readFile("Calista.txt", "CS180-Project-4"));
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
            //database.setLoginFile("firstusername;pswrdcool;teacher");
            //database.setLoginFile("secondusername;12342323;student");

            //should print out contents of login Details.txt
            System.out.println(database.getLoginFile());
        } catch (Exception e) {
            System.out.println("There was either an error writing into login file or reading from it!");
            e.printStackTrace();
        }

        //TODO: test to see if it works properly
        /**
         * Beginning of rebuildArrays test case
         *
         *
         * expected:
         *
         *
         *
         * Result:
         *
         */
        try {
           // database.rebuildArrays();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There was an error rebuilding the arrayList!");
        }

        //TODO: test to see if it works properly
        /**
         * Beginning of getVoteFile and setVoteFile test case
         *
         *
         * expected:
         *
         *
         *
         * Result:
         *
         */
    }
}
