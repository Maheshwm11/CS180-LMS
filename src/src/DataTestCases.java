public class DataTestCases {
    public static void main(String[] args) {
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
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There was an IOException somewhere!");
        }

        try {
            //should write "My first post!" to Kdog.txt that will be in teacher directory under Kdog folder
            database.writeFile("kdogiscool;password;teacher", "Kdog", "My first post!");
        } catch (Exception e) {
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
            database.createPostFile("1;1;r", "bober1;pswrd;student", "Hello!");
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
            //System.out.println(database.readFile("Calista.txt"));
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
    }
}
