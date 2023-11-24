package com.group10;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class LoginScreensController extends App{

//********************************************************************************************************
//**********************************Declaration of variables and methods**********************************
//********************************************************************************************************

@FXML
private AnchorPane rootPane;

@FXML
private TextField AdminPasswordTextField, AdminUserNameTextField, studentIDtxtField, SCPasswordTextField, SCUserNameTextField;
@FXML
private RadioButton FemaleRadioButton, MaleRadioButton, NoRadioButton, YesRadioButton;

// Variables to store General Questions answers
public String genderAnswer = "NULL";
public String nationalityAnswer = "NULL";

//Function to switch screens
public void switchScreen(String fxmlFileName) throws IOException{
    Pane newRoot = FXMLLoader.load(getClass().getResource(fxmlFileName));
    rootPane.getChildren().setAll(newRoot);
};

//A method used to check if the ID the user has entered is valid
public Boolean checkIfInt(String x) {

    //try catch code block using parseInt to detext if the string is only an integer
    try{
        Integer.parseInt(x);
    } catch (Exception e){
        return false; 
    }
    return true;   
}

// A method to initialize a new student into student login files
public void InitializeNewStudent(String inputID) throws IOException, org.json.simple.parser.ParseException{
            // ******************Read from file********************
            // Create file reader object called "readfile"
            FileReader readfile = new FileReader("src/main/resources/com/group10/StudentLogins.json");
            // Store all existing data into a new JSONObject
            JSONObject readdata = (JSONObject) new JSONParser().parse(readfile);
            // Store existing array data into new array object
            JSONArray readarr = (JSONArray) readdata.get("studentList");

            //********Creating the data to input into the array*******
            JSONObject inputData = new JSONObject();
            inputData.put("studentID" , inputID);
            inputData.put("answeredBefore","false");
            inputData.put("gender","NULL");
            inputData.put("isMalaysian","NULL");


            //append to jsonarray
            readarr.add(inputData); // Additional element added to array before writing
            readdata.put("studentList", readarr);

            // Write to file
            FileWriter writefile = new FileWriter("src/main/resources/com/group10/StudentLogins.json");
            writefile.write(readdata.toJSONString());
            writefile.flush(); //
            writefile.close(); // Closes filewriter

            // Reads out every element in json array
            for (Object option : readarr) {
                String output = option.toString();
                System.out.println(output);
            }
    }

    // A method used to find if the inputted studentID exists in the JSONFile
    public boolean searchStudent(String sampleStudentID) throws IOException, org.json.simple.parser.ParseException {

        boolean studentFound = false;

        // Insantiates file reader object called "readFile"
        FileReader readFile = new FileReader("src/main/resources/com/group10/StudentLogins.json");
        // Insantiates JSONobject "readData" to store all JSON file content
        JSONObject readData = (JSONObject) new JSONParser().parse(readFile);
        // Insantiates new Array to store the contents of the JSONArray
        JSONArray readArr = (JSONArray) readData.get("studentList");

        for (Object retrievedStudent : readArr) {     //loop to look through every element in the array. object "retrievedStudent" is used to store the content of each element in "readArr"
            
            String retrievedID = (((JSONObject)retrievedStudent).get("studentID")).toString();   //only retrieves the studentID of every element and converts to string
            
            if (sampleStudentID.equals(retrievedID)) {
                System.out.println("Student already exists in json file");
                studentFound = true;
            }
        }
        return studentFound;
    }

    // A method used to student has answered the general questions form before
    public boolean hasAnsweredBefore(String sampleStudentID) throws IOException, org.json.simple.parser.ParseException {

        boolean hasAnswered = false;
        // Insantiates file reader object called "readFile"
        FileReader readFile = new FileReader("src/main/resources/com/group10/StudentLogins.json");
        // Insantiates JSONobject "readData" to store all JSON file content
        JSONObject readData = (JSONObject) new JSONParser().parse(readFile);
        // Insantiates new Array to store the contents of the JSONArray
        JSONArray readArr = (JSONArray) readData.get("studentList");

        for (Object retrievedStudent : readArr) {     //loop to look through every element in the array. object "retrievedStudent" is used to store the content of each element in "readArr"
            
            //retrieves boolean value of whether the student has answered before
            boolean retrievedAnsweredBefore = Boolean.parseBoolean((((JSONObject)retrievedStudent).get("answeredBefore")).toString());
            String retrievedID = (((JSONObject)retrievedStudent).get("studentID")).toString();   //only retrieves the studentID of every element and converts to string
            
            
            if (retrievedAnsweredBefore == true && sampleStudentID.equals(retrievedID)) {
                System.out.println("Your student has answered before");
                hasAnswered = true;
            }
        }
        return hasAnswered;
    }

    // Method used to update the student file so we know they have just completed the General Questions form
    public static void updateStudentLogins(String sampleStudentID, String inputGender, String inputNationality) throws IOException, org.json.simple.parser.ParseException {

        // Creating a new file reader object to extract file data
        FileReader myFileReader = new FileReader("src/main/resources/com/group10/StudentLogins.json");
        // Store file data into new JSONObject
        JSONObject readFile = (JSONObject) new JSONParser().parse(myFileReader);
        // Store the Array within the file onto a new JSONArray object
        JSONArray readArray = (JSONArray) readFile.get("studentList");

        // Preparing values to jump-start while loop
        int counter = 0;        // Used to identify the index of the array
        JSONObject tempStudent = (JSONObject) (readArray).get(counter);         // Stores each individual element of the array
        String tempID = (tempStudent.get("studentID")).toString();          // Converts data to string

        while (!(tempID.equals(sampleStudentID))) {                 // Runs through each element in the array and checks if it's the specified one
            counter++;
            tempStudent = (JSONObject) (readArray).get(counter);
            tempID = (tempStudent.get("studentID")).toString();
        }
        
        // Updates the specified keys of the particular element object
        tempStudent.put("gender", inputGender);
        tempStudent.put("isMalaysian", inputNationality);
        tempStudent.put("answeredBefore", "true");

        // Overwrites the entire JSON file with the updated JSONObject
        FileWriter myFileWriter = new FileWriter("src/main/resources/com/group10/StudentLogins.json");
        myFileWriter.write(readFile.toJSONString());
        myFileWriter.flush();
        myFileWriter.close();

        System.out.println("StudentLogins has been updated");
    }

    // A method to write logged in student's id to a text file
    public static void writeToCurrentStudentFile(String inputText){
        try {
            FileWriter myWriter = new FileWriter("src/main/resources/com/group10/currentStudent.txt");      //Creates new filewriter object
            myWriter.write(inputText);          //stores logged in student's ID to file
            myWriter.close();                   //closes object
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    // A method to read from the current logged in text file ("currentStudent")
    public static String readFromCurrentStudentFile(){

        String data = "";
        try {
            File myObj = new File("src/main/resources/com/group10/currentStudent.txt");
            Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {        //reads every line of the textfile
            data = myReader.nextLine();
        }
        myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return data;
    }










//**************************************************************************************
//**********************************Home Page Controls**********************************
//**************************************************************************************

@FXML //Homepage to Admin login
void HP_to_AL(ActionEvent event) throws IOException {
    switchScreen("AdminLogin.fxml");
}

@FXML //Homepage to Survey Creator login
void HP_to_SCL(ActionEvent event) throws IOException {
    switchScreen("SurveyCreatorLogin.fxml");
}

@FXML //Homepage to Student Login
void HP_to_SL(ActionEvent event) throws IOException {
    switchScreen("StudentLogin.fxml");
}









//***********************************************************************************************
//**********************************Student Login Page Controls**********************************
//***********************************************************************************************

@FXML //Invalid Student login to Admin login
void ISL_to_AL(ActionEvent event) throws IOException {
    switchScreen("AdminLogin.fxml");
}

@FXML //Invalid Student login to Survey Creator login
void ISL_to_SCL(ActionEvent event) throws IOException {
    switchScreen("SurveyCreatorLogin.fxml");
}

@FXML //When the "Next" button is pressed in the Invalid Student login
void ISL_Next(ActionEvent event) throws IOException, ParseException {
    
    String inputID = studentIDtxtField.getText();//gets the text from the student field
    System.out.println("Inputted text: " + inputID);
    System.out.println(checkIfInt(inputID));

    if (checkIfInt(inputID) == false){      //checks if the input is a valid student id
        
        System.out.println("Not a valid student ID");
        
    } else {        // If valid, student can proceed
        
        writeToCurrentStudentFile(inputID); // Stores student ID in text file after successful login

        System.out.println("Student ID is valid");
        if (searchStudent(inputID) == true){        //if student id is valid, then it would check if this studentID is already in "StudentLogins"

            if (hasAnsweredBefore(inputID) == true) {   //checks if the student has already answered the General Questions form before
                switchScreen("PlaceholderScreen.fxml");     //Skips form if have
            } else {
                switchScreen("GeneralQuestions.fxml");      //Prompts user to answer it
            }

        } else {    //if student id wasn't found in "StudentLogins" JSON file, then it adds it

            InitializeNewStudent(inputID);
            System.out.println("New Student ID has been added");
            switchScreen("GeneralQuestions.fxml");
        }
    }

    System.out.println("**********************************");
}

@FXML //Student login to Admin login
void SL_to_AL(ActionEvent event) throws IOException {
    switchScreen("AdminLogin.fxml");
}

@FXML //Student login to Survey Creator login
void SL_to_SCL(ActionEvent event) throws IOException {
    switchScreen("SurveyCreatorLogin.fxml");
}

@FXML //When the "Next" button is pressed in the Student login
void SL_Next(ActionEvent event) throws IOException, ParseException {
    
    String inputID = studentIDtxtField.getText(); //gets the inputted text from the textfield
    System.out.println("Inputted text: " + inputID);
    System.out.println(checkIfInt(inputID));

    if (checkIfInt(inputID) == false){      //checks if the input is a valid student id
        
        System.out.println("Not a valid student ID");
        switchScreen("InvalidStudentLogin.fxml");
        
    } else {    //if valid, student can proceed

        writeToCurrentStudentFile(inputID); // Stores student ID in text file after successful login

        System.out.println("Student ID is valid");
        if (searchStudent(inputID) == true){        //if student id is valid, then it would check if this studentID is already in "StudentLogins"

            if (hasAnsweredBefore(inputID) == true) {   //checks if the student has already answered the General Questions form before
                switchScreen("PlaceholderScreen.fxml");     //Skips form if have
            } else {
                switchScreen("GeneralQuestions.fxml");      //Prompts user to answer it
            }

        } else {    //if student id wasn't found in "StudentLogins" JSON file, then it adds it

            InitializeNewStudent(inputID);
            System.out.println("New Student ID has been added");
            switchScreen("GeneralQuestions.fxml");
        }
    }

    System.out.println("**********************************");
}









//*********************************************************************************************
//**********************************Admin Login Page Controls**********************************
//*********************************************************************************************

@FXML //Admin login to Survey Creator login 
void AL_to_SCL(ActionEvent event) throws IOException {
    switchScreen("SurveyCreatorLogin.fxml");
}

@FXML //Admin login to Student login
void AL_to_SL(ActionEvent event) throws IOException {
    switchScreen("StudentLogin.fxml");
}

@FXML //When "Next" button is pressed in Admin Login
void AL_Next(ActionEvent event) throws IOException, ParseException {
    String inputUsername = AdminUserNameTextField.getText(); //stores inputted username
    String inputPassword = AdminPasswordTextField.getText(); //stores inputted password

    Reader reader = new FileReader("src/main/resources/com/group10/UserDetails.json");     //instantiates a new "reader" object to read through file
    JSONObject data = (JSONObject) new JSONParser().parse(reader);      //instantiates "data" object to store read data
    JSONArray arr = (JSONArray) data.get("userDetailsArray");      //instantiates "arr" as an array object to store the specified array

    System.out.println(inputUsername);
    System.out.println(inputPassword);

    boolean userFound = false;
    for (Object ques: arr) {    //for loop used to read each element of the array
        System.out.println(((JSONObject) ques).get("adminID"));
        String tempUsername = (((JSONObject) ques).get("username")).toString(); //stores the collected username from the array to a temp variable
        String tempPassword = (((JSONObject) ques).get("password")).toString(); //stores the collected password from the array to a temp variable

        boolean isAdmin = Boolean.valueOf((((JSONObject) ques).get("isAdmin")).toString()); //converts the collected value to a boolean

        System.out.println(tempUsername);
        System.out.println(tempPassword);

        if (isAdmin == true){
            if (inputUsername.equals(tempUsername) && inputPassword.equals(tempPassword)) {     //if statement used to check if username and password are correct
                System.out.println("Login successful");
                userFound = true;       //sets the userfound boolean to true if credentials are correct
            } else {
                System.out.println("Username or password did not match");
                
            }
        }
        System.out.println(userFound);
    }

    if (userFound == true){     //if statement outside of the for loop so it can switch screens depending if user was found
        switchScreen("PlaceholderScreen.fxml");
    } else {
        switchScreen("AdminLoginFailure.fxml");
    }
}

@FXML //Admin login failure to Survey Creator login
void ALF_to_SCL(ActionEvent event) throws IOException {
    switchScreen("SurveyCreatorLogin.fxml");
}

@FXML //Admin login failure to Student login
void ALF_to_SL(ActionEvent event) throws IOException {
    switchScreen("StudentLogin.fxml");
}

@FXML //When "Next" button is pressed in Admin login failure
void ALF_Next(ActionEvent event) throws IOException, ParseException {
    String inputUsername = AdminUserNameTextField.getText(); //stores inputted username
    String inputPassword = AdminPasswordTextField.getText(); //stores inputted password

    Reader reader = new FileReader("src/main/resources/com/group10/UserDetails.json");     //instantiates a new "reader" object to read through file
    JSONObject data = (JSONObject) new JSONParser().parse(reader);      //instantiates "data" object to store read data
    JSONArray arr = (JSONArray) data.get("userDetailsArray");      //instantiates "arr" as an array object to store the specified array

    System.out.println(inputUsername);
    System.out.println(inputPassword);

    boolean userFound = false;
    for (Object ques: arr) {    //for loop used to read each element of the array
        System.out.println(((JSONObject) ques).get("adminID"));
        String tempUsername = (((JSONObject) ques).get("username")).toString(); //stores the collected username from the array to a temp variable
        String tempPassword = (((JSONObject) ques).get("password")).toString(); //stores the collected password from the array to a temp variable

        boolean isAdmin = Boolean.valueOf((((JSONObject) ques).get("isAdmin")).toString()); //converts the collected value to a boolean

        System.out.println(tempUsername);
        System.out.println(tempPassword);

        if (isAdmin == true){
            if (inputUsername.equals(tempUsername) && inputPassword.equals(tempPassword)) {     //if statement used to check if username and password are correct
                System.out.println("Login successful");
                userFound = true;       //sets the userfound boolean to true if credentials are correct
            } else {
                System.out.println("Username or password did not match");
                
            }
        }
        System.out.println(userFound);
    }

    if (userFound == true){     //if statement outside of the for loop so it can switch screens depending if user was found
        switchScreen("PlaceholderScreen.fxml");
    }
}









//***************************************************************************************************
//**********************************General Questions Page Controls**********************************
//***************************************************************************************************

@FXML //General Questions to Admin login
void GQ_to_AL(ActionEvent event) throws IOException {
    switchScreen("AdminLogin.fxml");
}

@FXML //General Questions to Survey Creator login
void GQ_to_SCL(ActionEvent event) throws IOException {
    switchScreen("SurveyCreatorLogin.fxml");
}

@FXML //When "Submit" button is pressed in General Questions
void GQ_Submit(ActionEvent event) throws IOException, ParseException {

    String inputtedStudentID = readFromCurrentStudentFile();

    System.out.println(genderAnswer);
    System.out.println(nationalityAnswer);

    // if statement makes sure student doesn't leave any blanks
    if (genderAnswer == "NULL" || nationalityAnswer == "NULL"){
        System.out.println("Both questions must be answered");
        switchScreen("InvalidGeneralQuestions.fxml");       // If there are any blanks, bring to error propt screen
    } else {
        updateStudentLogins(inputtedStudentID, genderAnswer, nationalityAnswer);      //If answered correctly, update file and proceed
        switchScreen("PlaceholderScreen.fxml");
    }
}

@FXML //When "Submit" button is pressed in Invalid General Questions
void IGQ_Submit(ActionEvent event) throws IOException, ParseException {

    String inputtedStudentID = readFromCurrentStudentFile();

    System.out.println(genderAnswer);
    System.out.println(nationalityAnswer);

    // if statement makes sure student doesn't leave any blanks
    if (genderAnswer == "NULL" || nationalityAnswer == "NULL"){     // If there are any blanks, repeat 
        System.out.println("Both questions must be answered");
    } else {
        updateStudentLogins(inputtedStudentID, genderAnswer, nationalityAnswer);
        switchScreen("PlaceholderScreen.fxml");     //If answered correctly, update file and proceed
    }
}

@FXML //Invalid General Questions to Admin login
void IGQ_to_AL(ActionEvent event) throws IOException {
    switchScreen("AdminLogin.fxml");
}

@FXML //Invalid General Questions to Survey Creator login
void IGQ_to_SCL(ActionEvent event) throws IOException {
    switchScreen("SurveyCreatorLogin.fxml");
}

@FXML //Method to store student's selected gender
void GetGender(ActionEvent event) {

    if (MaleRadioButton.isSelected()){
        genderAnswer = MaleRadioButton.getText();
    } else if (FemaleRadioButton.isSelected()){
        genderAnswer = FemaleRadioButton.getText();
    }

}

@FXML //Method to store student's selected nationality
void GetNationality(ActionEvent event) {
    if (YesRadioButton.isSelected()){
        nationalityAnswer = YesRadioButton.getText();
    } else if (NoRadioButton.isSelected()){
        nationalityAnswer = NoRadioButton.getText();
    }
}









//************************************************************************************************
//**********************************Survey Creator login contols**********************************
//************************************************************************************************

@FXML //When "Next" button is pressed in Survey Creator login page
void SCL_Next(ActionEvent event) throws IOException, ParseException {
    String inputUsername = SCUserNameTextField.getText(); //stores inputted username
    String inputPassword = SCPasswordTextField.getText(); //stores inputted password

    Reader reader = new FileReader("src/main/resources/com/group10/UserDetails.json");     //instantiates a new "reader" object to read through file
    JSONObject data = (JSONObject) new JSONParser().parse(reader);      //instantiates "data" object to store read data
    JSONArray arr = (JSONArray) data.get("userDetailsArray");      //instantiates "arr" as an array object to store the specified array

    System.out.println(inputUsername);
    System.out.println(inputPassword);

    boolean userFound = false;
    for (Object ques: arr) {    //for loop used to read each element of the array
        System.out.println(((JSONObject) ques).get("creatorID"));
        String tempUsername = (((JSONObject) ques).get("username")).toString(); //stores the collected username from the array to a temp variable
        String tempPassword = (((JSONObject) ques).get("password")).toString(); //stores the collected password from the array to a temp variable

        System.out.println(tempUsername);
        System.out.println(tempPassword);

        if (inputUsername.equals(tempUsername) && inputPassword.equals(tempPassword)) {     //if statement used to check if username and password are correct
            System.out.println("Login successful");
            userFound = true;       //sets the userfound boolean to true if credentials are correct
        } else {
            System.out.println("Username or password did not match");
            
        }

        System.out.println(userFound);
    }

    if (userFound == true){     //if statement outside of the for loop so it can switch screens depending if user was found
        switchScreen("PlaceholderScreen.fxml");
    } else {
        switchScreen("InvalidSurveyCreatorLogin.fxml");
    }
}

@FXML //Survey Creator login to Admin login
void SCL_to_AL(ActionEvent event) throws IOException {
    switchScreen("AdminLogin.fxml");
}

@FXML //Survey Creator login to Student login
void SCL_to_SL(ActionEvent event) throws IOException {
    switchScreen("StudentLogin.fxml");
}

@FXML //When "Next" button is pressed in Invalid Survey Creator login page
void ISCL_Next(ActionEvent event) throws IOException, ParseException {
    String inputUsername = SCUserNameTextField.getText(); //stores inputted username
    String inputPassword = SCPasswordTextField.getText(); //stores inputted password

    Reader reader = new FileReader("src/main/resources/com/group10/UserDetails.json");     //instantiates a new "reader" object to read through file
    JSONObject data = (JSONObject) new JSONParser().parse(reader);      //instantiates "data" object to store read data
    JSONArray arr = (JSONArray) data.get("userDetailsArray");      //instantiates "arr" as an array object to store the specified array

    System.out.println(inputUsername);
    System.out.println(inputPassword);

    boolean userFound = false;
    for (Object ques: arr) {    //for loop used to read each element of the array
        System.out.println(((JSONObject) ques).get("creatorID"));
        String tempUsername = (((JSONObject) ques).get("username")).toString(); //stores the collected username from the array to a temp variable
        String tempPassword = (((JSONObject) ques).get("password")).toString(); //stores the collected password from the array to a temp variable

        System.out.println(tempUsername);
        System.out.println(tempPassword);

        if (inputUsername.equals(tempUsername) && inputPassword.equals(tempPassword)) {     //if statement used to check if username and password are correct
            System.out.println("Login successful");
            userFound = true;       //sets the userfound boolean to true if credentials are correct
        } else {
            System.out.println("Username or password did not match");
            
        }

        System.out.println(userFound);
    }

    if (userFound == true){     //if statement outside of the for loop so it can switch screens depending if user was found
        switchScreen("PlaceholderScreen.fxml");
    }
}

@FXML //Invalid Survey Creator login to Admin login
void ISCL_to_AL(ActionEvent event) throws IOException {
    switchScreen("AdminLogin.fxml");
}

@FXML //Invalid Survey Creator login to Student login
void ISCL_to_SL(ActionEvent event) throws IOException {
    switchScreen("StudentLogin.fxml");
}









//**************************************************************************************
//**********************************Placeholder screen**********************************
//**************************************************************************************

@FXML //The placeholder screen is just a screen that will be repurposed into bringing to the other forms when they're done
void GoBackToHomePage(ActionEvent event) throws IOException {
    switchScreen("HomePage.fxml");
}
}