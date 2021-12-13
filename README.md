# CS180-Project-5
Project 5 Group project for CS180 - Fall 2021 Team
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

- Client Class: The client class is what allows users to connect to the server and make changes to the discussion board and login.
  - Functionality: The client class sends a server request to the Server class and creates an object input and output stream to send data to the Server. In addition, all GUI functionality is gone on the Client class, which is run on the EDT thread. The client uses actionListeners to wait on user input and sends that data to the server. The server then sends something back to the input stream, which allows the Client class to respond accordingly. In addition, the Client class uses a timer to know when there are changes to made to the discussion board, and updates the client's GUI to allow for live updates. 
- Server Class: The server class is responsible for dealing with any information gets sent from the Client class and making updates to any changes in data. 
  - Functionality: The Server class has a serverSocket that accepts a socket from the Client class. In addition, the .accept() method is in a while loop, which means it can accept multiple users in the same instance. The Server class waits for the Client to send information to it, which it then uses to update the files in the Database folder and send the appropriate data back to the Client class.
- GameState Enum: Primary sorting mechanism in the Client class
  - Functionality: Client uses the GameState enum to decide which GUI JFrame is displayed on the screen.

- Post Class: The post class is primarily a data class containing all relevant data to each post, comment, or subcomment.
  - Functionality: The post class creates post objects that holds an arrayList of comments, the author name, the course the post object belongs to, and a Post object that gives the allows the program to know who's comment section is being referenced. Besides the getter and setter methods for the fields of the object, the comment() method allows users to add a comment to a Post object. The comments themselves are Post objects as well. The toString() method prints out the current contents of the Post object.
 
- Data Class: The data class is the main source for File I/O for the program. Data is stored in plain .txt files.
  - Functionality: The data class uses the createPostFile() method to write the post object into the "Post.txt" file. The contents are not human-readable, but java can understand and successfully restore the digital arraylist used in the program. The readPostFile() is the conversion from .txt to the digital arraylist. The getLoginFile() and getGrades() function similarly, as a simple read methods that read from text files. The setLoginFile() and setGrades() serve as simple write methods which writes to text files.

# How do the classes Relate to One Another?
- The Server class accepts sockets from the Client class, which connects to Client to the Server class. In addition, the Server class updates data by using methods in the Data class, and in some instances, uses Post objects in conjunction with the Data class methods to properly update the Posts.txt file. Lastly, the Server makes modifications to the Login Details.txt and Grades.txt files by using methods in the Data class.
- The Data and Client classes use Post objects to create discussion posts and comments. For example, the createPostFile() method in the Data class takes an arrayList of post objects to create posts of the Post objects in the arrayList. The Client class takes Post objects that the Server class has sent to make modifications client-side.
- The Client.java class uses the GameState enum to decide which JFrame will appear for the client.
