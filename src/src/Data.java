import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Data {

    private ArrayList<String> courseName = new ArrayList<>();
    private ArrayList<String> forumName =  new ArrayList<>();
    private ArrayList<String> reply = new ArrayList<>();
    private ArrayList<String> comment = new ArrayList<>();

    public void writeFile(String identifier, String content) {
        File f = new File(identifier);
        try (PrintWriter pw = new PrintWriter(new FileWriter(f))) {
            pw.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String createPostFile(String pathName, String content) {
        String courseIndex = "";
        String forumIndex = "";
        String replyIndex = "";
        String commentIndex = "";
        //pathName will be of the format courseName;forumName;reply;comment
        // But it will be numbered (higher the number, the more recent the edit) like 1;1;1;1
        // means it was the first comment to the first reply to the first forum in the first course (first means the oldest added)
        String[] locator = pathName.split(";");
        switch (locator.length) {
            case 1: //initializing a course (title)
                courseName.add(content);
                courseIndex = String.valueOf(courseName.size());
                break;
            case 2: //just a forum post
                forumName.add(content);
                forumIndex = String.valueOf(forumName.size());
                break;
            case 3: //reply to a post;
                reply.add(content);
                replyIndex = String.valueOf(reply.size());
                break;
            case 4: //comment to a reply on a post
                comment.add(content);
                commentIndex = String.valueOf(comment.size());
                break;
            default: //improper formatting
                break;
        }
        String identifier = courseIndex + ";" + forumIndex + ";" + replyIndex + ";" + commentIndex;
        writeFile(identifier, content);
        return identifier;
    }

    //TODO In first menu, display all no ";" file content (course names)
    // in second, display all one ";" file content (forum)
    // in third, display all two ";" file content (replies)
    // in fourth, display all three ";" file content (comments)
    // Build a system that can read already present files and rebuild arraylist upon startup
    // Build a system that can handles multiple users and attribute edits to users to call back on


}