import java.io.*;
import java.nio.file.FileSystems;
import java.util.ArrayList;
/**
 * Project 04 - Data
 * <p>
 * Data storage class
 *
 * @author Madhav Maheshwari
 * @version 14th November 2021
 */
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

    public ArrayList<String> getGrades() {
        ArrayList<String> grades = new ArrayList<>();
        File dir = new File(dirName);
        File f = new File (dir, "Grades.txt");

        try (BufferedReader bfr = new BufferedReader(new FileReader(f))) {
            String line = bfr.readLine();
            while (line != null) {
                grades.add(line);
                line = bfr.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return grades;
    }

    public void setGrades(ArrayList<String> grades) {
        File dir = new File(dirName);
        File f = new File (dir, "Grades.txt");

        try (PrintWriter pw = new PrintWriter(new FileWriter(f))) {
            for (int i = 0; i < grades.size(); i++) {
                pw.write(grades.get(i));
                pw.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}