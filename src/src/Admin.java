import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class Admin extends User {
    /*TODO:
    1. Be able to take account creation info and create a teacher object
    2. allow users to create, edit, delete account for themselves (probably not my job)
    3. teachers can create, edit, or delete disscusion boards
    4. teachers can reply to student responses on discussion board
    5. teachers can import a file with the discussion topic to create a new discussion forum.
    5. Voting
        Teachers can view a dashboard that lists the most popular replies to each forum by votes.
        Data will appear with the student's name and vote count.
        Teachers can choose to sort the dashboard.
    6. Grading
        Teachers can view all the replies for a specific student on one page and assign a point value to their work.
    Optional:  Allow Teachers to edit or remove posts made by students.
     */

    public Admin(String username) {
        super(username);
    }

    public void createDiscussion(String filename, String poster, String course) {
        Post newPost = new Post(filename, poster, course);
    }

    //change file contents to new String you input
    public void editDiscussion(String newContents, String fileName, Post postName) throws FileNotFoundException {
        File edit = postName.getPost();
        FileOutputStream fis = new FileOutputStream(edit);
        PrintWriter writer = new PrintWriter(fis);

        writer.println(newContents);
        writer.flush();
    }

    //deletes post with a certain topic name
    public void deleteDiscussion(Post post) throws FileNotFoundException, IOException {
        File delete = post.getPost();
        delete.delete();
    }

    public void replyToStudent(Post post, String fileName, String poster, String course) {
        post.comment(fileName, poster, course);
    }

    public void importDiscussion(File file, String topicName) throws IOException {
        FileReader fis = new FileReader(file);
        BufferedReader reader = new BufferedReader(fis);
        FileOutputStream fos = new FileOutputStream(topicName);
        PrintWriter writer = new PrintWriter(fos);

        while (true) {
            String input = reader.readLine();
            if(input == null) {
                break;
            } else {
                writer.println(input);
                writer.flush();
            }
        }
        writer.close();
    }

    public void viewDashBoard (Post post) {
        ArrayList<Post> comments = post.getComments();
        for(Post c : comments) {
            System.out.println(c);
        }
    }

    //sorted dashboard by most popular votes
    public void sort(Post post) {
        //Used a custom comparator: Gotten from -
        //StackOverflow: Sort an ArrayList based on an object field [duplicate]
        ArrayList<Post> comments = post.getComments();
        Collections.sort(comments,
                Comparator.comparingInt(Post::getUpVotes).reversed());
    }

    public ArrayList<Post> viewStudentReplies(Post post) {
        return post.getComments();
    }

    public void assignGrade(User user, int newScore) {
        user.setGrade(newScore);
    }


}
