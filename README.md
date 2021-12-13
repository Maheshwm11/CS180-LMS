# CS180-Project-5
Project 4 Group project for CS180 - Fall 2021 Team
Members:
- Joseph Lee
- Tiffany Kuang
- Colby Lanier
- Madhav Maheshwari

# How to Run and Compile

- The program should be run in IntelliJ
- For the Menus class, click to wrench "Modify Run Configuration" button when you run the class, and then select "allow multiple instances" to allow multiple Menus classes to be run at the same time to test cocurrency.
- Compile the following classes: Menu, Post, and Data
- .txt files in Database folder are Grades.txt, Login Details.txt and Posts.txt.
- Ignore every other class implemented
- To start the program, run the Menus.java file.
- To create a course, you will have to create a discussion post.
- To create a post, go into any course and press 0. Creating and adding content happens in two steps. There, select create new discussion post and add a title and a course name of your choice. A new course name will create a new course for you. The changes will show up as required. This post will have no content.
- To add content to the previous post, go to that post and enter the index of that post. Select edit post, and you will be able to import files or write content into it.
- To add a comment though, creating and adding content happen in the same instance. File import option is available.
- To exit the program at any time, enter any course and press 0 for additional options. Press 2 to exit program.
- To clean the database, un-comment line 23 and 24 in Menus.java class and run the program. You may end the program normally or just hard-quit.

# Who did What?
- Client:
  - Colby & Madhav
  - Refresh portion: Colby
  - GUI: Colby & Joseph
- Server:
  - Tiffany
  - Colby
    - Used some outdated early implementation by Joseph
- Post (Project 4):
  - Colby
- Data (Project 4):
  - Madhav
- Test Cases:
  - Joseph
  - Tiffany

# Who submitted what?
- Tiffany : Submitted Report on Brightspace
- Joseph : Submitted Vocareum Workspace
- Colby : Submitted Video Presentation on Brightspace

# Class Descriptions

- Menus Class: This is the user interface and primary logic center of the project. The main functionality of the file is
  based off of interaction with a discussionPosts arrayList containing several post objects.
  -  Functionality: The menu starts off with asking for either one of the following: to login, to create an account, edit account, and delete account. If you delete an existing account, the login string in Login Details.txt is deleted. If you edit your account, your account string in the file is updated with what you put in. Creating account will prompt the user for a username and password. The username and password cannot contain a semicolon and will throw an error message if the username is already taken or if there is a semicolon. It will also ask if you are a student or teacher. When a user logins in, it will display a course list with all the courses so far, will say to type "all" to view all courses. When you type all, it will tell you who posted a certain discussion, when it was posted, and how many comments are under the main discussion (this does not include replies to other comments). You can enter 0 to view advanced options, and the menu will differ depending on whether you are a student or a teacher. Teachers will be able to grade student and create discussion posts, while students will not have those options. When creating a discussion post, it will ask for title of the post and to enter the course name. The teacher can then afterwards go to the post and add text contents for the post by either typing in a string or importing a file with the string contents. Students can import comments using both scanner inputs and files to either other comments or the main discussion post itself.
  - Testing SecondaryMenu method: ran the secondaryMenu in the MenuTestCases.java file to make sure the secondaryMenu properly    allows for the addition of comments without throwing any errors and allow one to add comments on top of other comments.

- Post Class: The post class is primarily a data class containing all relevant data to each post, comment, or subcomment.
  - Functionality: The post class creates post objects that holds an arrayList of comments, the author name, the course the post object belongs to, and a Post object that gives the allows the program to know who's comment section is being referenced. Besides the getter and setter methods for the fields of the object, the comment() method allows users to add a comment to a Post object. The comments themselves are Post objects as well. The toString() method prints out the current contents of the Post object.
  - Testing: ran the comments and getComments() method in the PostTestCases.java class to make sure that the comments properly add comments to an ArrayList and getComments returns the arrayList that has the stored comments, which are also Post objects. After
    These posts contain their own arrayList containing further post objects representing their comments section, as well as
    two sister arrayLists containing the logins of the up and downvoters. These pieces of data are then manipulated by the
    menus class.
- Data Class: The data class is the main source for File I/O for the program. Data is stored in plain .txt files.
  - Functionality: The data class uses the createPostFile() method to write the post object into the "Post.txt" file. The contents are not human-readable, but java can understand and successfully restore the digital arraylist used in the program. The readPostFile() is the conversion from .txt to the digital arraylist. The getLoginFile() and getGrades() function similarly, as a simple read methods that read from text files. The setLoginFile() and setGrades() serve as simple write methods which writes to text files.
  - Testing: Using the DataTestCases.java class, the createPostFile() adds Post objects to the Posts.txt file with the ArrayList that was given as a parameter. After using createPostFile, readPostFile() is called to read contents from Posts.txt file to make sure that the data persists. The setLoginFile() takes a parameter of ArrayList that contains strings with login info, and stores that login information into Login Details.txt. The getLoginFile() gets the login information from the Login Details.txt file so that method was called to make sure that data persists and the login info was updated properly.

# How do the classes Relate to One Another?
- The Menu class uses methods from the Data class to take data that the user puts into the menu and save it into files. For example, the setLoginFile() takes data that the user inputted and saves it into the Login Details.txt file so that the user will be able to login again after a session has ended.
- The Data and Menu classes use Post objects to create discussion posts and comments. For example, the createPostFile() method in the Data class takes an arrayList of post objects to create posts of the Post objects in the arrayList. The Menu classes uses methods in the Post class such as .setBodyText() to update the fields in a Post object and makes sure that those updates are saved into files.
