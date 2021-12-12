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
- Test Status: 

# Test Case 7 (Creating / Editing / Deleting Discussion Posts)
-

# Test Case 8 (Commenting)
-

# Test Case 9 (Live Updates)
-
