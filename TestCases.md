# Test Cases
- Test Cases for Project 5, CS180-GLD (Fall 2021)

# Test Case 1 (Create account and Login) Student
- In top text box upon lauching program: TestCases
- In bottom text box upon launching program: PasswordCases
- Click "Create Student Account"
- (Takes to "Continue to Program", "Edit or delete account", or "back" button screen)
- Click "Contunue to Program"

- Expected Result: Creates "TestCases;PasswordCases;student" line in Login Details.txt and should be at main menu screen
- Test Status: Passed

# Test Case 2 (Create account and Login) Teacher
- Top text box: MasterAdminCases
- Bottom text box: MasterPasswordCases
- Click "Create Teacher Account"
- (Click "Continue to Program")

- Expected Result: Creates "MasterAdminCases;MasterPasswordCases;teacher" line in Login Details.txt and should be at main menu screen
- Test Status: Passed

# Test Case 3 (Delete Account)
- Login with username TestCases and password PasswordCases
- Click login and then click delete account
- Should be back and login screen

- Expected Result: at login screen with "TestCases;PasswordCases;student" line now gone from Login Details.txt
- Test Status: Passed

# Test Case 4 (Edit account)
- Login with MasterAdminCases and MasterPasswordCases
- Click on "Edit account"
- Type "MasterAdminEdit" and "MasterPasswordEdit"
- Click confirm 
- (Should be taken back to main menu)

- Expected Result: at login screen with "MasterAdminEdit;MasterPasswordEdit;teacher" in Login Details.txt file and "MasterAdminCases;MasterPasswordCases;teacher" gone from file.
- Test Status: Passed.

# Test Case 5 (Invalid Login)
- Attempt to login with "blah" and "blah"
- ("Username and password not recgonized" popup should appear)
- Click ok and return back to login

- Expect Result: pop up with error message saying login credentials are incorrect
- Test Status: Passed.

# Test Case 6 (Grading a student & Student Viewing Grade)
- Teacher logs into account and then clicks "Grade a Student"
- Select the student "Test" from the dropdown menu and then select "view all posts by a student"
- view all comments by student and then select grade you want from slider (Ex: 70).
- Click "Grade student"
- Log into "Test" student account
- Click "View your grade"
- It should say "Your grade is 70".

- Expected Result: Student "Test" sees the screen "Your grade is 70."
- Test Status: Passed

# Test Case 7 (Creating / Editing / Deleting Discussion Posts)
- 1. Creating
  - Log in with a teacher account
  - Click "create new DiscussionPost" button on main screen
  - Create a discussion post using the name "CS180" and typing "Post 1" in text box. Then click create post.
  - Go back to main menu and select "create new DiscussionPost" again.
  - Still use course name "CS180", but this time create bodytext by importing text file.
  - If you go to main menu, there should be a CS180 option in the dropdown, and if you select CS180, there should be two posts that show up

  - Test Status: Passed

- 2. Editing
  - Take one of the posts we just made and select it.
  - Then select "Edit post" button
  - Edit post by typing in new body text in box and click confirm.
  - Go to other post and this time edit bodytext by using file import of text file
  - Go back to list of all posts for that course. The two posts' bodytext should now be different

  - Test Status: Passed

- 3. Deleting 
  - Select one of two posts
  - Click "Delete post" button
  - It should take you back to posts list for that course
  - Post that was deleted is no longer in the list of posts

  - Test Status: Passed

# Test Case 8 (Commenting)
- Log in with any account (student or teacher).
- If there is no posts currently, login as a teacher and create a post
- Go to the post and leave a comment using just text box.
- Now leave another comment on the post using a text file.
- Leave a comment under another comment.

- Expected Result: Post should say "Comments: 2" and when selecting the comment with another comment underneath it, the comment details should say "Comments: 1"
- Test Status: Passed

# Test Case 9 (Live Updates)
- Log into the system as a teacher and go to "grade a student" button option and look at dropdown menu
- Leave teacher system as is and launch another client
- Create a student account on the new client and leave a comment on any discussionPost
- The student should automatically appear in the dropdown menu for the teacher with a live update refresh
- Teacher should select student and then click "View all post by student" to see the comment left by student
- Grade student
- Student should see automatically refreshed new grade in the main menu screen.

- Test Status: Passed.

# Test Case 10 (Concurrency and Live Updates w/ Grades and Posts)
- Log in with "MasterAdmin" and "MasterPassword" (or any other) teacher account
- Log in with any student user
- Create a discussionPost w/ course being CS180 and random text. (teacher)
- Add a comment to that post (teacher)
- Student should see course name now in dropdown menu
- Student select select that course and see the post and the comment
- Student adds comment to post
- Teacher goes back to main menu and selects "grade a student"
- Teacher should select student name from dropdown and select "View all posts by a student"
- Comment made earlier should appear.
- Grade the student
- Student's main screen should reload with new grade.

- Expected Result: Student has new grade and there should be a single post with a single comment (w/ both Student and Teacher client still running)
- Test Status: Passed
