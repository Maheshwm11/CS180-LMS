import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class Post {
    // core information
    private String poster;
    private String fileName;
    private File bodyText;

    // up and downvoting
    private int upVotes;
    private int downVotes;
    private ArrayList<String> upVoters;
    private ArrayList<String> downVoters;

    // comments
    private ArrayList<Post> comments;

    // misc info
    private String course;
    private ArrayList<String> tags;
    private Timestamp time;

    public Post(String filename, String poster, String course) {

        this.poster = poster;
        this.fileName = filename;
        this.bodyText = new File(filename);


        this.upVotes = 0;
        this.downVotes = 0;
        this.upVoters = new ArrayList<>();
        this.downVoters = new ArrayList<>();

        this.comments = new ArrayList<>();

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

    public String getFileName() {
        return fileName;
    }

    public File getBodyText() {
        return bodyText;
    }

    public void setBodyText(File bodyText) {
        this.bodyText = bodyText;
    }

    // up and downvoting

    public int getUpVotes() {
        return upVotes;
    }

    public int getDownVotes() {
        return downVotes;
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

    // comments

    public ArrayList<Post> getComments() {
        return comments;
    }

    public void comment(String filename, String poster) {
        comments.add(new Post(filename, poster, course));
    }

    public String toString() {
        return String.format(
                "%s:\n%s\nUV: %d DV: %d\nposted: %s\ncomments: %d",
                poster, parseBodyText(bodyText), upVotes, downVotes, time, comments.size()
        );
    }

    public String parseBodyText(File f) {
        String ret = "";
        try (BufferedReader bfr = new BufferedReader(new FileReader(f))) {
            String line = bfr.readLine();
            while (line != null) {
                ret += line;
                line = bfr.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    // misc info

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
