import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

public class Data {
    //Throughout the program, the word "comment" means reply to a reply to a forum post in a course
    //and just "reply" means reply to a forum post in a course
    private ArrayList<String> courseName = new ArrayList<>();
    private ArrayList<String> forumName = new ArrayList<>();
    private ArrayList<String> reply = new ArrayList<>();
    private ArrayList<String> comment = new ArrayList<>();


    public void rebuildArrays() {
        Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
        ArrayList<String> logins = getLoginFile();

        for (int i = 0; i < logins.size(); i++) {
            String dirName = path.toString();
            String[] details = logins.get(i).split(";");
            String userName = details[0];
            if (details[2].equals("student"))
                dirName += "/Database/Student/" + userName;
            else if (details[2].equals("teacher"))
                dirName += "/Database/Teacher/" + userName;
            File dir = new File(dirName);
            if (dir.exists()) {
                File[] directoryList = dir.listFiles();
                if (directoryList != null) {
                    Arrays.sort(directoryList);
                    for (File test : directoryList) {
                        String name = test.getName();
                        String content = readFile(name, dirName);
                        name = name.substring(0, name.length() - 4);
                        //gets name of file without .txt

                        String[] array = name.split(";");
                        switch (array.length) {
                            case 1 -> //file is a course title
                                    courseName.add(Integer.parseInt(array[0]) - 1, content);
                            case 2 -> //file is a forum post
                                    forumName.add(Integer.parseInt(array[1]) - 1, content);
                            case 3 -> //file is a reply
                                    reply.add(Integer.parseInt(array[2]) - 1, content);
                            case 4 -> //file is a comment
                                    comment.add(Integer.parseInt(array[3]) - 1, content);
                        }
                    }
                }
            }
        }
    }

    public String createPostFile(String numberedPathname, String userDetail, String content) {
        //userDetail will be of the format userName;password;roleType
        String courseIndex;
        String forumIndex;
        String replyIndex;
        String commentIndex;
        String identifier = "";
        //numbered pathName will be the path to the content. For example, reply to first forumPost of first course
        //will be "1;1;r". We don't care about the index of r because the program can automatically find that.
        // But the title of the file will be numbered (higher the number, the more recent the edit) like 1;1;1;1
        // means it was the first comment to the first reply to the first forum in the first course (first means the oldest added)
        String[] numbers = numberedPathname.split(";");
        switch (numbers.length) {
            case 1 -> { //initializing a course (title)
                courseName.add(content);
                courseIndex = String.valueOf(courseName.size());
                identifier = courseIndex;
            }
            case 2 -> { //just a forum post
                forumName.add(content);
                courseIndex = numbers[0];
                forumIndex = String.valueOf(forumName.size());
                identifier = courseIndex + ";" + forumIndex;
            }
            case 3 -> { //reply to a post;
                reply.add(content);
                courseIndex = numbers[0];
                forumIndex = numbers[1];
                replyIndex = String.valueOf(reply.size());
                identifier = courseIndex + ";" + forumIndex + ";" + replyIndex;
            }
            case 4 -> { //comment to a reply on a post
                comment.add(content);
                courseIndex = numbers[0];
                forumIndex = numbers[1];
                replyIndex = numbers[2];
                commentIndex = String.valueOf(comment.size());
                identifier = courseIndex + ";" + forumIndex + ";" + replyIndex + ";" + commentIndex;
            }
        }
        writeFile(userDetail, identifier, content);
        return identifier;
    }

    public String readFile(String name, String dirName) {
        File dir = new File(dirName);
        ArrayList<String> list = new ArrayList<>();
        File f = new File(dir, name);
        try (BufferedReader bfr = new BufferedReader(new FileReader(f))) {
            String line = bfr.readLine();
            while (line != null) {
                list.add(line);
                line = bfr.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.join("\n", list);
    }

    public void writeFile(String userDetail, String identifier, String content) {
        Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
        String dirName = path.toString();
        //above block gets current working directory
        String[] user = userDetail.split(";");
        //gets info from userDetail string
        String userName = user[0];
        if (user[2].equals("student"))
            dirName += "/Database/Student/" + userName;
        else if (user[2].equals("teacher"))
            dirName += "/Database/Teacher/" + userName;
        //Used tutorial https://stackoverflow.com/questions/5797208/java-how-do-i-write-a-file-to-a-specified-directory
        File dir = new File (dirName);
        if (!dir.exists())
            dir.mkdir();
        identifier += ".txt";
        File f = new File (dir, identifier);
        try (PrintWriter pw = new PrintWriter(new FileWriter(f))) {
            pw.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getCourseName() {
        return courseName;
    }
    public ArrayList<String> getForumName() {
        return forumName;
    }
    public ArrayList<String> getReply() {
        return reply;
    }
    public ArrayList<String> getComment() {
        return comment;
    }

    public ArrayList<String> getLoginFile() {
        ArrayList<String> logins = new ArrayList<>();

        Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
        String dirName = path.toString();
        dirName += "/Database";
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

    public void setLoginFile(String identifier) {
        //identifier here is userName;password;role
        Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
        String dirName = path.toString();
        dirName += "/Database";
        File dir = new File(dirName);
        File f = new File (dir, "Login Details.txt");

        try (PrintWriter pw = new PrintWriter(new FileWriter(f))) {
            pw.write(identifier);
            pw.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<String> getVoteFile(String numberedPathname, String typeOfVote) {
        //numbered pathName will be the path to the content. For example, reply to first forumPost of first course
        //will be "1;1;1"
        typeOfVote = typeOfVote.toLowerCase();
        Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
        String dirName = path.toString();
        //above block gets current working directory
        dirName += "/Database/Votes/" + numberedPathname;
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

    public void setVoteFile( String numberedPathname, String typeOfVote, ArrayList<String> voteFile) {
        //numbered pathName will be the path to the content. For example, reply to first forumPost of first course
        //will be "1;1;1"
        typeOfVote = typeOfVote.toLowerCase();
        Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
        String dirName = path.toString();
        //above block gets current working directory
        dirName += "/Database/Votes/" + numberedPathname;
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