import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class Post {
    private String poster;
    private String fileName;
    private File post;

    private int upVotes;
    private int downVotes;

    private ArrayList<String> upVoters;
    private ArrayList<String> downVoters;
    private ArrayList<Post> comments;

    private String course;
    private ArrayList<String> tags;

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
        //used TimeStamp method from: mkyong.com "How to get current timestamps in Java"
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

    public boolean upVote(String username) {
        if (!upVoters.contains(username)) {
            upVoters.add(username);
            upVotes++;
            if (downVoters.contains(username)) {
                downVoters.remove(username);
                downVotes--;
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean downVote(String username) {
        if (!downVoters.contains(username)) {
            downVoters.add(username);
            downVotes++;
            if (upVoters.contains(username)) {
                upVoters.remove(username);
                upVotes--;
            }
            return true;
        } else {
            return false;
        }
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
