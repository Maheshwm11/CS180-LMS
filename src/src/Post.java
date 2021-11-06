import java.io.File;
import java.util.ArrayList;

public class Post {
    int upVotes;
    int downVotes;
    ArrayList<Post> comments;
    File post;

    public Post(String filename) {
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

    public void comment(String filename) {
        comments.add(new Post(filename));
    }
}
