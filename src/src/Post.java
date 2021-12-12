import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
/**
 * Project 04 - Post
 * Backend class for storing content
 *
 * @author Colby
 * @version 14th November 2021
 */
public class Post implements Serializable {
    // core information
    private String poster;
    private String bodyText;
    Post parent;

    // comments
    private ArrayList<Post> comments;

    // misc info
    private String course;
    private Timestamp time;
    int curatedIndex;

    public Post(String bodyText, String poster, String course, Post parent) {
        this.poster = poster;
        this.bodyText = bodyText;
        this.comments = new ArrayList<>();
        this.course = course;
        this.time = new Timestamp(new Date().getTime());
        this.parent = parent;
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

    public String getTimeStamp() {
        return String.format("%s", time);
    }

    public Post getParent() {
        return parent;
    }

    public void setCuratedIndex(int curatedIndex) {
        this.curatedIndex = curatedIndex;
    }

    public int getCuratedIndex() {
        return curatedIndex;
    }

    // comments
    public ArrayList<Post> getComments() {
        return comments;
    }

    public void commentString(String bodyText, String poster) {
        comments.add(new Post(bodyText, poster, course, this));
    }

    public void commmentPost(Post post) {
        comments.add(post);
    }

    public String toString() {
        return String.format(
                "Course: %s\nPoster Name: %s\n\n%s\n\nPosted: %s\nComments: %d\n",
                course, poster, bodyText, time, comments.size()
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
