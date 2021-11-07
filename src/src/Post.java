import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class Post {
    private String poster;
    private int upVotes;
    private int downVotes;
    private ArrayList<Post> comments;
    private File post;
    private String course;
    private ArrayList<String> tags;
    private String fileName;

    public Post(String filename, String poster, String course) {
        this.poster = poster;
        this.upVotes = 0;
        this.downVotes = 0;
        this.comments = new ArrayList<>();
        this.post = new File(filename);
        this.fileName = filename;
        this.course = course;
        this.tags = new ArrayList<>();
    }

    public void printTimeStamp() {
        Date date = new Date();
        System.out.println(new Timestamp(date.getTime()));
    }
    
    public String getFileName() {
        return this.fileName;
    }

    public int getUpVotes() {
        return upVotes;
    }

    public String getCourse() {
        return course;
    }

    public int getDownVotes() {
        return downVotes;
    }

    public ArrayList<Post> getComments() {
        return comments;
    }

    public File getPost() {
        return post;
    }

    public void upVote() {
        upVotes++;
    }

    public void downVote() {
        downVotes++;
    }

    public void comment(String filename, String poster, String course) {
        comments.add(new Post(filename, poster, course));
    }

    public String toString() {
        return String.format(
                "%s:"
        );
    }
}
