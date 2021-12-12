import java.util.ArrayList;
/**
 * DataTestCases
 *
 * Tests the methods of the Data class
 *
 * @author Joseph Lee, LC5
 *
 * @version 11/14/21
 *
 */
public class DataTestCases {
    public static void main(String[] args) {
        /*
        Start of Data Class Test Cases
         */

        Data database = new Data();


        /**
         * Beginning of createPostFile and readPostFile test case
         * (Creates post file and then reads contents from post file)
         * Passed Test Case: Contents of Posts.txt cannot be decoded and read in an understandable way, but it does
         * store and it correctly shows the data in the file with the readPostFile method.
         * Note: format may look a bit wonky, but this will not show up in any menu on the terminal, so it passes
         * test case as the stored data is correct
         *
         * expected:
         *
         * [Course: CS180
         * Poster Name: Jake
         *
         * Placer text for example
         *
         * Posted: 2021-11-14 16:49:21.748
         * Comments: 0
         * , Course: CS180
         * Poster Name: Jake1
         *
         * Placer text for example1
         *
         * Posted: 2021-11-14 16:49:21.748
         * Comments: 0
         * , Course: CS180
         * Poster Name: Jake2
         *
         * Placer text for example2
         *
         * Posted: 2021-11-14 16:49:21.748
         * Comments: 0
         * , Course: CS180
         * Poster Name: Jake3
         *
         * Placer text for example3
         *
         * Posted: 2021-11-14 16:49:21.748
         * Comments: 0
         * ]
         *
         *
         * Result: (passes test case)
         *
         * [Course: CS180
         * Poster Name: Jake
         *
         * Placer text for example
         *
         * Posted: 2021-11-14 16:49:21.748
         * Comments: 0
         * , Course: CS180
         * Poster Name: Jake1
         *
         * Placer text for example1
         *
         * Posted: 2021-11-14 16:49:21.748
         * Comments: 0
         * , Course: CS180
         * Poster Name: Jake2
         *
         * Placer text for example2
         *
         * Posted: 2021-11-14 16:49:21.748
         * Comments: 0
         * , Course: CS180
         * Poster Name: Jake3
         *
         * Placer text for example3
         *
         * Posted: 2021-11-14 16:49:21.748
         * Comments: 0
         * ]
         *
         */
        try {
            //Post newer = new Post("Placer text for example", "Jake", "CS180", "1;1;1");
            //Post newer1 = new Post("Placer text for example1", "Jake1", "CS180", "1;1;1");
            //Post newer2 = new Post("Placer text for example2", "Jake2", "CS180", "1;1;1");
            //Post newer3 = new Post("Placer text for example3", "Jake3", "CS180", "1;1;1");
            ArrayList<Post> posts = new ArrayList<>();
            //posts.add(newer);
            //posts.add(newer1);
            //posts.add(newer2);
            //posts.add(newer3);

            database.createPostFile(posts);

            //reads contents from posts.txt file
            System.out.println(database.readPostFile());
        } catch (Exception e) {
            System.out.println("There was an error creating the post!");
            e.printStackTrace();
        }

        /**
         * Beginning of getLoginFile and setLoginFile test case
         * (Reads contents of file)
         *
         * expected:
         *
         * [MasterAdmin;MasterPassword;teacher, jimBobbies;12345;student, amykim42;bobbypins;teacher,
         * stubbu;panthers;teacher, panther12;adfasdf;student, firstusername;pswrdcool;teacher,
         * secondusername;12342323;student]
         *
         *
         * Result: (passes test case)
         *
         * [MasterAdmin;MasterPassword;teacher, jimBobbies;12345;student, amykim42;bobbypins;teacher,
         * stubbu;panthers;teacher, panther12;adfasdf;student, firstusername;pswrdcool;teacher,
         * secondusername;12342323;student]
         *
         * Login Details.txt Contents: (passes test case)
         *
         * MasterAdmin;MasterPassword;teacher
         * jimBobbies;12345;student
         * amykim42;bobbypins;teacher
         * stubbu;panthers;teacher
         * panther12;adfasdf;student
         * firstusername;pswrdcool;teacher
         * secondusername;12342323;student
         */
        try {
            ArrayList<String> strings = new ArrayList<>();
            String first = "MasterAdmin;MasterPassword;teacher";
            String second = "jimBobbies;12345;student";
            String third = "amykim42;bobbypins;teacher";
            String fourth = "stubbu;panthers;teacher";
            String fifth = "panther12;adfasdf;student";
            String sixth = "firstusername;pswrdcool;teacher";
            String seventh = "secondusername;12342323;student";
            strings.add(first);
            strings.add(second);
            strings.add(third);
            strings.add(fourth);
            strings.add(fifth);
            strings.add(sixth);
            strings.add(seventh);
            //should set login details.txt file to store arraylist strings
            database.setLoginFile(strings);

            //should print out contents of login Details.txt
            System.out.println(database.getLoginFile());
        } catch (Exception e) {
            System.out.println("There was either an error writing into login file or reading from it!");
            e.printStackTrace();
        }

        /**
         * Beginning of getGrades and setGrades test case
         * (Reads contents of Grades.txt file)
         *
         * expected:
         *
         * [Student;84, Test;60]
         *
         *
         *
         * Result: (passes test case)
         *
         * [Student;84, Test;60]
         *
         *
         * Login Details.txt Contents: (passes test case)
         *
         * Student;84
         * Test;60
         *
         */
        try {
            ArrayList<String> grades = new ArrayList<>();
            String first = "Student;84";
            String second = "Test;60";
            grades.add(first);
            grades.add(second);
            //should set Grades.txt file to store grades strings
            database.setGrades(grades);

            //should print out contents of Grades.txt
            System.out.println(database.getGrades());
        } catch (Exception e) {
            System.out.println("There was either an error writing into grades file or reading from it!");
            e.printStackTrace();
        }

    }
}
