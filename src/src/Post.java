import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class Post implements Serializable {
    // core information
    private String poster;
    private String bodyText;

    // up and downvoting
    Data data = new Data();
    private ArrayList<String> upVoters;
    private ArrayList<String> downVoters;

    // comments
    private ArrayList<Post> comments;

    // misc info
    private String identifier;
    private String course;
    private ArrayList<String> tags;
    private Timestamp time;

    public Post(String bodyText, String poster, String course, String identifier) {

        this.poster = poster;
        this.bodyText = bodyText;

        this.upVoters = new ArrayList<>();
        this.downVoters = new ArrayList<>();

        this.comments = new ArrayList<>();

        this.identifier = identifier;
        this.course = course;
        this.tags = new ArrayList<>();
        this.time = new Timestamp(new Date().getTime());
    }

    // core information

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getBodyText() {
        return bodyText;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }

    // up and downvoting

    public int getUpVotes() {
        return upVoters.size();
    }

    public int getDownVotes() {
        return downVoters.size();
    }

    public String getIdentifier() {
        return identifier;
    }

    public boolean upVote(String username) {
        if (!upVoters.contains(username)) {
            upVoters.add(username);
            if (downVoters.contains(username)) {
                downVoters.remove(username);
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean downVote(String username) {
        if (!downVoters.contains(username)) {
            downVoters.add(username);
            if (upVoters.contains(username)) {
                upVoters.remove(username);
            }
            return true;
        } else {
            return false;
        }
    }

    // comments

    public ArrayList<Post> getComments() {
        return comments;
    }

    public void comment(String filename, String poster) {
        comments.add(new Post(filename, poster, course, (identifier + ";" + comments.size())));
    }

    public String toString() {
        return String.format(
                "\n%s:\n%s:\n%s\nUV: %d DV: %d\nposted: %s\ncomments: %d",
                course, poster, bodyText, upVoters.size(), downVoters.size(), time, comments.size()
        );
    }

    // misc info

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
