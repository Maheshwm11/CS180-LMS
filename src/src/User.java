public class User {
    private String username;
    private int grade;

    public User(String username) {
        this.username = username;
        grade = 0;
    }

    public void replyWithFile(Post post, String filename) {
        post.comment(filename, username);
    }

    public void replyWithString(Post post, String input) {
        // TODO: figure this shit out idk
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
