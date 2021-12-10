# Test Cases
- Test Cases for Project 5, CS180-GLD (Fall 2021)

# Test Case 1 (Create account and Login) Student
- In top text box upon lauching program: Test
- In bottom text box upon launching program: Password
- Click "Create Student Account"
- (Takes to "Continue to Program", "Edit or delete account", or "back" button screen)
- Click "Contunue to Program"

- Expected Result: Creates "Test;Password;student" line in Login Details.txt and should be at main menu screen
- Test Status: Passed

# Test Case 2 (Create account and Login) Teacher
- Top text box: MasterAdmin
- Bottom text box: MasterPassword
- Click "Create Teacher Account"
- (Click "Continue to Program")

- Expected Result: Creates "MasterAdmin;MasterPassword;teacher" line in Login Details.txt and should be at main menu screen
- Test Status: Passed

# Test Case 3 (Delete Account)
- Login with username Test and password Password
- Click login and then click delete account
- Should be back and login screen

- Expected Result: at login screen with "Test;Password;student" line now gone from Login Details.txt
- Test Status: Passed

# Test Case 4 (Edit account)
- Login with MasterAdmin and MasterPassword
- Click on "Edit account"
- Type "MasterAdminEdit" and "MasterPasswordEdit"
- Click confirm 
- (Should be taken back to main menu)

- Expected Result: at login screen with "MasterAdminEdit;MasterPasswordEdit;teacher" in Login Details.txt file and "MasterAdmin;MasterPassword;teacher" gone from file.
- Test Status: Passed.

# Test Case 5 (Invalid Login)
- Attempt to login with "blah" and "blah"
- ("Username and password not recgonized" popup should appear)
- Click ok and return back to login

- Expect Result: pop up with error message saying login credentials are incorrect
- Test Status: Passed.
