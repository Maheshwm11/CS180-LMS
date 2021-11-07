public class User {
    private String username;

    public User(String username) {
        this.username = username;
    }

    public void reply(Post post, String filename, String poster) {
        post.comment(filename, poster);
    }

    // TODO: upvotes or downvotes only once per user

}
