# CS180-Project-4
Project 4 Group project for CS180 - Fall 2021 Team
Members:
- Joseph Lee
- Tiffany Kuang
- Colby Lanier
- Madhav Maheshwari

This is a team project. You must contact your team members on Campuswire to verify your participation status by Friday November 5th. Failure to do so will result in removal from the team and a 0 on both Project 4 and Project 5. There is no option to complete the project individually. We highly recommend that you start early and communicate with your team members far before the deadline. Peer reviews will have a significant impact on your score. 

Start early! 

Contact Head TA Kedar Abhyankar if you have any concerns. 

Description
Project 4 is the first part of a two part project. Both Project 4 and Project 5 are team projects focusing on the same topics. You'll be working on the solution with your team over the next two weeks. In Project 4, we'll establish the foundational implementation requirements and core functionality. Project 5 will expand upon that foundation with new features. Not only are these team projects, but they are also an opportunity to explore every aspect of software development independently. Think of it like the capstone for your CS 18000 experience. 

This project is worth 11% of your final grade. We recommend that you take it, along with the other projects in the class, very seriously. 

This is an open-ended project. Your team will select one of the three implementation prompts to focus on. These prompts have implementation requirements, as well as optional features. You must implement the required features. Implementing optional features may earn you bonus points, depending on their complexity and polish. Bonus points are capped at the maximum possible earned points for the project (that is, your score will not exceed 100, regardless of bonus points earned). Note: The optional features we list are only suggestions. If you have ideas about other features to implement, feel free to do so. 

Each of the implementation prompts is a limited form of a learning management system. They are all similar, but the differences should allow you to specialize in what aspects of the course interest you. For Project 4, you'll be implementing an application that utilizes the keyboard (System.in) for input and the screen (System.out) for output.

Note: Several features of your implementation involve users interacting with one another. For Project 4, you can assume that users are using the same machine, logging into and out of accounts sequentially. That is, only one user accesses the application at a time. 

Lastly, this project will be manually graded after submission. We'll post scores by the Monday following the due date to ensure you have adequate time to consider feedback while working on Project 5. 

The requirements for each option are grouped into two categories: Core and Selections. Each team must implement the core requirements, and select the appropriate number of selective requirements based on the number of students in their team. Teams with 5 members should select 3 selective requirements, 4 member teams should select 2, and 3 member teams should select 1. Leftover requirements will count towards the "optional" requirements for extra credit. 

Note: 5 points of your grade is based on Coding Style.  You will need to update the Starter Code to follow the standards described on Brightspace. Use the "Run" button to check your Coding Style without using a submission. 

Team Work Expectations
There will be 3, 4, or 5 team members on each team. You will be placed into a chat group on Campuswire with your team. We expect each member of every team to contribute to the project. You are permitted to divide the work in any way you see fit as long as responsibilities are evenly distributed and every team member contributes to the project source code. You will be required to document your contributions to the project in the planning document. 

Each team member must send a message to your Campuswire chat group by the Friday (November 5th) after the project releases at 11:59PM EDT. Failure to do so will result in a 0 on both Project 4 and Project 5 for that team member. There are no exceptions to this policy. 

We recommend using the Campuswire chat functionality to communicate with your team. Using this feature will make it easier for course staff to support your team (you can ask us to answer questions via the @ function) and helps us mediate any potential disputes. Regardless of your choice of communication tool, you must send a message to the Campuswire chat by the Friday (November 5th) after the project releases at 11:59PM EDT to be graded. 

Any team member who fails to contribute will receive a 0 on the project.

Be aware that team collaboration is limited to the members of your team. You should not be sharing code with individuals outside of your team. Remember to follow the course academic honesty policy. If you have concerns about whether or not something is okay, just ask us. 

To simplify collaboration, you must make use of a Code Repository on Gitlab or Github. It will make sharing code, tracking changes, and debugging significantly easier. However, keep in mind that any repository you use must be private, with access limited only to members of your team. Code made available publicly is academic dishonesty. You will be required to submit a copy of your repository on Vocareum by cloning it into your work folder. 

Note: Every team member must commit to the repository. A lack of commits may be used as evidence that a team member did not participate. 

We reserve the right to modify your grade based on participation. You may receive a 0 for not contributing at all, or you may receive a 75% deduction on your team score for only contributing superficial content. If you wait until the last minute to work on the assignment, you will receive a 0 or a significant deduction as well. These cases will be judged on a case-by-case basis using evidence provided by teams. 

Your team will share a workspace on Vocareum. Only one team member needs to submit. 

In the event of a team disagreement, dispute, or lack of participation from any individual, you should contact the course coordinators as soon as possible. We can only help if we are aware of the situation. 

Role Distribution
Every team member needs to work on this project. Before you begin coding, we highly recommend that you meet and identify roles for everyone. For example: 

Role 1: Develop menus and application control flow.
Role 2: Develop log in details and user permissions. 
Role 3: Develop option specific requirements. 
Role 4: Develop option specific requirements. 
Role 5: Develop data persistence solutions and test cases. 
You can define the roles in any way that your team prefers. Two team members may work on the same topic, as we note above. Alternatively, you may find it simpler to have everyone complete a specific task separately. Communication will be key! 

Remember, this is a team project. Your project score will reflect your contribution to the team. 

Implementation Restrictions
Before describing each of the options, we want to note several key restrictions on your project implementations. 

First, all Project 4 input and output should be handled via the keyboard (System.in) and screen (System.out), just as with all of your previous projects. There is no GUI for this project. 

Second, all data must persist between program runs. If a user creates an account and then closes the application, they should still be able to log in when running it again. 

Third, all work must be your own. Copying code from the internet is academic dishonesty. Any team member who is caught copying code will receive a 0 on the project and the other team members will be given an opportunity to continue without them. 

Keep these in mind while designing your solution! 

Common Features
While the specifics of each implementation will be related to the option you select, there are two common features for every project. 

Roles
There will be two defined roles in the application: Teacher and Student.
Teachers will be able to create courses and edit content within them. 
Students will be able to access the available courses and submit responses. 
Users will select their role  while creating an account, with permissions associated with their actions accordingly. 
Permissions details will be noted in the option requirements. 
Courses
Each application must support multiple courses.
Teachers will be able to create as many courses as they like.
Students can access any course that has been created. 
_________________________________________________________________________________________________________________________________________________________________________________

 # Option 1
The first option is to implement a learning management system discussion board. These discussion boards allow instructors to post prompts and students replying with their responses. 

Looking for an example? Navigate to the Brightspace discussion forums!

Reminder: You can assume that only one user is accessing the application at a time. A user might log in, make a post, then log out. Another user on the same machine can then log in, view the original user's post, and make one of their own. 

Your implementation must have the following: 
Core
Data must persist regardless of whether or not a user is connected. If a user disconnects and reconnects, their data should still be present. 
Descriptive errors should appear as appropriate. For example, if someone tries to log in with an invalid account. The application should not crash under any circumstances. 
Users can create, edit, and delete accounts for themselves.
The attributes you collect as part of account creation are up to you. 
Users should be required to either create an account or sign in before gaining access to the application. 
Whichever identifier you maintain for the user must be unique. 
During account creation, a user will specify their role. 
Discussion Forums
Any number of discussion forums may be added to a course. 
The forum topic must be listed at the top of the forum page. 
Replies will be listed below the topic with the newest appearing first. 
Comments on replies will appear beneath each reply. 
All created text content must display a timestamp, including the forum topic and any replies. 
Teachers
Teachers can create, edit, and delete discussion forums.
Teachers can reply to student responses. 
Students
Students can create replies to discussion forums. 
Students can reply to other students in their posts. 
Selections
File imports.
All file imports must occur as a prompt to enter the file path.  
Teachers can import a file with the discussion topic to create a new discussion forum. 
Students can import a file with their reply to a forum to post a response. 
Voting
Teachers can view a dashboard that lists the most popular replies to each forum by votes.
Data will appear with the student's name and vote count. 
Teachers can choose to sort the dashboard.
Students can vote on replies with an upvote button.
Students should only be able to vote once. 
Grading
Teachers can view all the replies for a specific student on one page and assign a point value to their work. 
Students can view the scores they have received for the forum. 
Optional Features: 
Add emoji responses to posts and replies. Display the emoji on the post with a count of how many of each are present. 
Add polling features to posts. Students can select one of the options and a dashboard displays the results. 
Allow Teachers to edit or remove posts made by students. 

_________________________________________________________________________________________________________________________________________________________________________________

# Option 2
The second option is to implement a learning management system quiz tool. Online quizzes allow teachers to present information in a variety of formats to evaluate student progress. 

Looking for an example? Navigate to the Brightspace quizzes!

Reminder: You can assume that only one user is accessing the application at a time. A teacher might log in, create a quiz, then log out. A student could then log in and complete the quiz. 

Your implementation must have the following: 
Core
Data must persist regardless of whether or not a user is connected. If a user disconnects and reconnects, their data should still be present. 
Descriptive errors should appear as appropriate. For example, if someone tries to log in with an invalid account. The application should not crash under any circumstances. 
Users can create, edit, and delete accounts for themselves.
The attributes you collect as part of account creation are up to you. 
Users should be required to either create an account or sign in before gaining access to the application. 
Whichever identifier you maintain for the user must be unique. 
Quizzes
Any number of quizzes can be added to a course. 
Quizzes will have one or more multiple choice questions. 
Quiz questions will appear on the same page in the order they are added to the quiz. 
Teachers
Teachers can create, edit, and delete quizzes. 
Teachers can view student submissions for the quiz. 
Students
Students can take any created quiz. Students can select their responses to each question. 
After completing a quiz, students can submit it. Each submission must be timestamped. 
Selections
File imports.
All file imports must occur as a prompt to enter the file path.  
Teachers can import a file with the quiz title and quiz questions to create a new quiz. 
Students can attach a file to any question as a response. 
Randomization
Teachers can choose to randomize the order of questions and the order of potential options for a question.
Students will receive a different order with every attempt. 
Grading
Teachers can view the quiz submissions for individual students and assign point values to each answer. 
Students can view their graded quizzes, with the points for each individual question and their total score listed. 
Optional Features: 
Teachers can create questions with different formats than multiple choice. Select two from the following list (fill in the blank, dropdown, matching, true / false). Students can respond to the question. 
Teachers can create a question pool wherein the questions select for the quiz are a random subset of the larger pool. Students will receive a different subset with every attempt. 
Teachers can grant specific students alternate forms of access to the quiz in the form of different deadlines and/or extended time. Both features must be present. The individual student who receives those changes will have them present in the quiz when they take it. 
_________________________________________________________________________________________________________________________________________________________________________________

# Option 3
The third option is to implement a learning management system content distributor. Most learning management systems allow teachers to share course content with students. 

Looking for an example? Navigate to the Brightspace content tab!

Reminder: You can assume that only one user is accessing the application at a time. A teacher might log in, add a file, then log out. A student could then log in and review the file. 

Your implementation must have the following: 
Core
Data must persist regardless of whether or not a user is connected. If a user disconnects and reconnects, their data should still be present. 
Descriptive errors should appear as appropriate. For example, if someone tries to log in with an invalid account. The application should not crash under any circumstances. 
Users can create, edit, and delete accounts for themselves.
The attributes you collect as part of account creation are up to you. 
Users should be required to either create an account or sign in before gaining access to the application. 
Whichever identifier you maintain for the user must be unique. 
Content
Content shared with students will include three types of material: text, files, and links. 
Text will be pages within the LMS created by the teacher that students can view.
Files will be files uploaded to the LMS by the teacher that students can download. 
Links will be external links that students can paste into their browsers. 
Content will be organized into modules by topic. 
Each module will have a title and 0 or more associated materials.
Modules will be listed in the order they are created. 
Teachers
Teachers can create, edit, and delete modules.
Teachers can create, edit, and delete course material. For text material, they can create and edit text within the learning management systems. 
Teachers can view a list of which students have accessed a specific material and the most recent time they did so. 
Students
Students can view modules and material. For text material, the text is displayed within the learning management system. 
When students view a content item, their access timestamp is updated. 
Selections
File imports
Teachers can import course content by uploading a file with the module title, the type of and order of material it contains, and the content of that material. For file uploads, this will be a reference to an already uploaded file.  
Students can download the module in a similar format. 
Access restrictions
Teachers can set access restrictions for materials that require materials to be viewed in a particular order. For example, Material 2 can require that Material 1 be viewed prior to access.
Students must must the requirements of the restrictions to access the material. 
 Grading
Teachers can view a list of all the content completed by a specific student and assign a point value to their work. 
Students can view their scores and any missing content they did not review. 
Optional Features: 
Label content added since the last session for that user as new. 
Teachers can set custom visibility by creating a start date and end date for specific materials. Outside of that range, students cannot access the material. 
Allow teachers to create to-do lists linked to specific materials, with students completing the list and checking off each item. The list should mark items as completed or todo. 
Testing
There are no public test cases for this project. Each implementation will look different, and we do not want to restrict your creativity in any way. 

However, you are expected to write your own custom test cases, specific to your team's implementation. We've given you examples of what this looks like in all your previous assignments.  Here's an example from one of the Homework assignments: 

@Test(timeout = 1000)
public void testExpectedOne() {
    // Set the input        
    // Separate each input with a newline (\n). 
    String input = "Line One\nLine Two\n"; 

    // Pair the input with the expected result
    String expected = "Insert the expected output here" 

    // Runs the program with the input values
    // Replace TestProgram with the name of the class with the main method
    receiveInput(input);
    TestProgram.main(new String[0]);

    // Retrieves the output from the program
    String stuOut = getOutput();

    // Trims the output and verifies it is correct. 
    stuOut = stuOut.replace("\r\n", "\n");
    assertEquals("Error message if output is incorrect, customize as needed",
                    expected.trim(), stuOut.trim());

}
Alternatively, you can choose to create a main method to accomplish a similar goal. Simply call each method being tested and save the result, then compare it to an expected value. You should use pair each class created in your solution with a corresponding testing class. 

Your test cases should do the following: 

Each requirement must have a test verifying it functions properly.
Each requirement should have an error test to verify it does not crash when receiving invalid input. 
Hint: For the requirements that verify data persists between runs, you can check the file contents in the test case. 
When designing your implementation, be sure to use methods appropriately. It will be challenging to design test cases for overly complex methods. 

Project 5 Preview
Project 5 is a new iteration of Project 4. You'll be improving your solution with new features and making it more accessible to users. 

There are three core improvements included in Project 5: 

Concurrency - Your program will support concurrent use by multiple users. 
Network IO - Your program will support networked use with a server and client. 
GUI - Your program will have a fully-featured GUI that allows users to perform all the tasks they could do in the command line. 
You don't have to worry about any of these yet, but you should keep them in mind while working on Project 4. The design decisions you make now will affect how easy it is to implement these features later!

Review and Planning Report
Your team must submit a project review and planning document for Project 4. In it, you'll consider lessons learned from Project 4 and document how you will approach Project 5. 

Part One
Part One is a minimum of 500 words and requires the following: 

Describing your project and the functionality you implemented. Include both required and optional features, along with descriptions of each. 
Part Two
Each team member must write a minimum of 300 words on the following:

A minimum of 200 words describing your contributions to Project 4. 
A minimum of 100 words on what you would do differently, if anything, if you were given the opportunity to start over again. If you would do something differently, note how you will apply that knowledge in Project 5. If you would not do anything differently, explain why. 
Each team member's section should be labelled with that individual's name. 
Part Three
Part Three is a minimum of 400 words and requires the following: 

A minimum of 200 words describing your collaboration strategy for Project 5, including how work will be assigned and how team members will communicate. 
A minimum of 100 words containing a list of tasks you believe will be necessary to complete Project 5, along with the individuals assigned to complete them and when they should be done. You may use a list or paragraph for this section. 
 For example, "Develop Server class" - Completed by 07/26/2021 - Student One. 
Note: There must be an even distribution of work among the team members.
A minimum of 100 words describing your conflict mitigation strategy. What will you do to prevent team conflict? How will you handle disagreements if they do arise? 
Note: Be sure your document does not have any major grammar or structural errors. You are not required to adhere to any writing style guide (for example, APA), but your document should be organized following the guidelines listed above.  You can use the following structure for a general outline: 

A cover page with the report title and all team member names listed
A section labelled "Part 1" on a new page with the information described above (including as many pages as necessary). 
A section labelled "Part 2" on a new page with the information described above (including as many pages as necessary). 
A section labelled "Part 3" on a new page with the information described above (including as many pages as necessary). 
We recommend using a 12 point font such as Times New Roman and double spacing. All section and subsection labels should be bolded. 
Documentation
Your project must follow Coding Style (it will be 5 points of your solution grade). You should also document your code with comments explaining the functionality you implement. Not only will your teammates appreciate it if they have to debug, but you will also make it simpler to explain why you chose to implement it that way if asked during the presentation. 

Additionally, your project will need to have a README document. This document will include the following:

Instructions on how to compile and run your project.
A list of who submitted which parts of the assignment on Brightspace and Vocareum. 
For example: Student 1 - Submitted Report on Brightspace. Student 2 - Submitted Vocareum workspace.
A detailed description of each class. This should include the functionality included in the class, the testing done to verify it works properly, and its relationship to other classes in the project. 
Submit
Here's a breakdown of grading: 

All solution code, test cases, and documentation must be submitted on Vocareum by cloning the repository. (80 points). All required files should be included in the repository. 
A written report must be submitted via Brightspace. (10 points).
The peer evaluation form must be submitted via CATME. (5 points).
Coding Style (5 points)
