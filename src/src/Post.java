import java.io.File;
import java.util.ArrayList;

public class Post {
    private String poster;
    private int upVotes;
    private int downVotes;
    private ArrayList<Post> comments;
    private File post;

    public Post(String filename, String poster) {
        this.poster = poster;
        this.upVotes = 0;
        this.downVotes = 0;
        this.comments = new ArrayList<>();
        this.post = new File(filename);
    }

    public int getUpVotes() {
        return upVotes;
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

    public void comment(String filename, String poster) {
        comments.add(new Post(filename, poster));
    }

    public String toString() {
        return String.format(

        );
    }
}
