public class User {
    private String username;
    private int grade;

    public User(String username) {
        this.username = username;
        grade = 0;
    }

    public void reply(Post post, String filename, String poster) {
        post.comment(filename, poster, post.getCourse());
    }

    // TODO: upvotes or downvotes only once per user


    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
