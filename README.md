# CS180-Project-4
Project 4 Group project for CS180 - Fall 2021 Team
Members:
- Joseph Lee
- Tiffany Kuang
- Colby Lanier
- Madhav Maheshwari

# How to Run and Compile

- The program should be run in IntelliJ
- Compile the following classes: Menu, Post, and Data
- Ignore every other class implemented
- To start the program, run the Menus.java file.

# Who did What?
- Menus: 
  - Login portion: Tiffany
  - Menu portion: Colby
    - Based off of methods written by Joseph
- Post:
  - Colby
- Data:
  - Madhav
- Test Cases:
  - Joseph
  - Tiffany

# Who submitted what?
- 

# Class Descriptions

- Menus Class: This is the user interface and primary logic center of the project. The main functionality of the file is
based off of interaction with a discussionPosts arrayList containing several post objects.
    - Testing SecondaryMenu method: ran the secondaryMenu in the MenuTestCases.java file to make sure the secondaryMenu properly    allows for the addition of comments without throwing any errors and allow one to add comments on top of other comments.
    
- Post Class: The post class is primarily a data class containing all relevant data to each post, comment, or subcomment.
    - Testing: ran the comments and getComments() method in the PostTestCases.java class to make sure that the comments properly add comments to an ArrayList and getComments returns the arrayList that has the stored comments, which are also Post objects. After 
These posts contain their own arrayList containing further post objects representing their comments section, as well as
two sister arrayLists containing the logins of the up and downvoters. These pieces of data are then manipulated by the
menus class.
- Data Class: 
    - Testing: Using the DataTestCases.java class, the createPostFile() adds Post objects to the Posts.txt file with the ArrayList that was given as a parameter. After using createPostFile, readPostFile() is called to read contents from Posts.txt file to make sure that the data persists. The setLoginFile() takes a parameter of ArrayList that contains strings with login info, and stores that login information into Login Details.txt. The getLoginFile() gets the login information from the Login Details.txt file so that method was called to make sure that data persists and the login info was updated properly.
