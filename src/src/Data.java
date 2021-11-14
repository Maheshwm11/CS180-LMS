import java.io.*;
import java.nio.file.FileSystems;
import java.util.ArrayList;

public class Data implements Serializable {
    private String dirName = FileSystems.getDefault().getPath("").toAbsolutePath() + "/Database";
    //above block gets current working directory
    //Used tutorial https://stackoverflow.com/questions/5797208/java-how-do-i-write-a-file-to-a-specified-directory

    public void createPostFile(ArrayList<Post> posts) {
        File dir = new File (dirName);
        File f = new File (dir, "Posts.txt");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f))) {
            oos.writeObject(posts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Post> readPostFile() {
        File dir = new File(dirName);
        ArrayList<Post> posts = new ArrayList<>();
        File f = new File(dir, "Posts.txt");
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            posts = (ArrayList<Post>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return posts;
    }

    public ArrayList<String> getLoginFile() {
        ArrayList<String> logins = new ArrayList<>();
        File dir = new File(dirName);
        File f = new File (dir, "Login Details.txt");

        try (BufferedReader bfr = new BufferedReader(new FileReader(f))) {
            String line = bfr.readLine();
            while (line != null) {
                logins.add(line);
                line = bfr.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logins;
    }

    public void setLoginFile(ArrayList<String> logins) {
        File dir = new File(dirName);
        File f = new File (dir, "Login Details.txt");

        try (PrintWriter pw = new PrintWriter(new FileWriter(f))) {
            for (int i = 0; i < logins.size(); i++) {
                pw.write(logins.get(i));
                pw.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<String> getVoteFile(String identifier, String typeOfVote) {
        //numbered pathName will be the path to the content. For example, reply to first forumPost of first course
        //will be "1;1;1"
        typeOfVote = typeOfVote.toLowerCase();
        dirName += "/Votes/" + identifier;
        File dir = new File(dirName);
        if (!dir.exists())
            return null;
        //if no such file exists, it will return null as no votes have been given yet
        ArrayList<String> list = new ArrayList<>();
        File f = new File(dir, typeOfVote + ".txt");
        try (BufferedReader bfr = new BufferedReader(new FileReader(f))) {
            String line = bfr.readLine();
            while (line != null) {
                list.add(line);
                line = bfr.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void setVoteFile(String identifier, String typeOfVote, ArrayList<String> voteFile) {
        //numbered pathName will be the path to the content. For example, reply to first forumPost of first course
        //will be "1;1;1"
        typeOfVote = typeOfVote.toLowerCase();
        dirName += "/Votes/" + identifier;
        File dir = new File(dirName);
        if (!dir.exists())
            dir.mkdir();
        File f = new File (dir, typeOfVote + ".txt");
        try (PrintWriter pw = new PrintWriter(new FileWriter(f))) {
            for (int i = 0; i < voteFile.size(); i++) {
                pw.write(voteFile.get(i));
                pw.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}