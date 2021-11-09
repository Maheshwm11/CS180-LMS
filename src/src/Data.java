import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class Data {

    private ArrayList<String> courseName = new ArrayList<>();
    private ArrayList<String> forumName =  new ArrayList<>();
    private ArrayList<String> reply = new ArrayList<>();
    private ArrayList<String> comment = new ArrayList<>();

    public void writeFile(String userDetail, String identifier, String content) {
        Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
        String dirName = path.toString();
        //above block gets current working directory
        String[] user = userDetail.split(";");
        //gets info from userDetail string
        String userName = user[0];
        if (user[2].equals("student"))
            dirName += "/Database/Student/" + userName;
        else if (user[2].equals("teacher"))
            dirName += "/Database/Teacher/" + userName;
        //Used tutorial https://stackoverflow.com/questions/5797208/java-how-do-i-write-a-file-to-a-specified-directory
        File dir = new File (dirName);
        if (!dir.exists())
            dir.mkdir();
        File f = new File (dir, identifier);
        try (PrintWriter pw = new PrintWriter(new FileWriter(f))) {
            pw.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String createPostFile(int depth, String userDetail, String content) {
        //userDetail will be of the format userName;password;roleType
        // this will be present as the first line of every file to make sure that file is earmarked for that user
        String courseIndex = "";
        String forumIndex = "";
        String replyIndex = "";
        String commentIndex = "";
        //pathName will be c;d;r;c OR c;d;r OR c;d OR c
        // But the title of the file will be numbered (higher the number, the more recent the edit) like 1;1;1;1
        // means it was the first comment to the first reply to the first forum in the first course (first means the oldest added)
        switch (depth) {
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
        writeFile(userDetail, identifier, content);
        return identifier;
    }
    public void searchFile(String userDetails, )


    //TODO In first menu, display all no ";" file content (course names)
    // in second, display all one ";" file content (forum)
    // in third, display all two ";" file content (replies)
    // in fourth, display all three ";" file content (comments)
    // Build a system that can read already present files and rebuild arraylist upon startup
    // Build a system that can handles multiple users and attribute edits to users to call back on


}