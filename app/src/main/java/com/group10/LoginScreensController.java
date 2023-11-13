package com.group10;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class LoginScreensController extends App{

@FXML
private AnchorPane rootPane;

@FXML
private TextField AdminPasswordTextField, AdminUserNameTextField, studentIDtxtField, SCPasswordTextField, SCUserNameTextField;
@FXML
private RadioButton FemaleRadioButton, MaleRadioButton, NoRadioButton, YesRadioButton;
@FXML
private ToggleGroup GenderRButton, NationalityRButton;
@FXML
private Text StudentID;

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
void ISL_Next(ActionEvent event) throws IOException {
    
    String inputID = studentIDtxtField.getText();//gets the text from the student field
    System.out.println("Inputted text: " + inputID);
    System.out.println(checkIfInt(inputID));

    //checks if the input is a valid student id
    if (checkIfInt(inputID) == false){
        
        System.out.println("Not a valid student ID");
        
    } else {
        System.out.println("Student ID is valid");
    };

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
void SL_Next(ActionEvent event) throws IOException {
    
    String inputID = studentIDtxtField.getText(); //gets the inputted text from the textfield
    System.out.println("Inputted text: " + inputID);
    System.out.println(checkIfInt(inputID));

    //checks if the input is a valid student id
    if (checkIfInt(inputID) == false){

        //switches to different screen
        switchScreen("InvalidStudentLogin.fxml");

        System.out.println("Not a valid student ID");
        
    } else {
        System.out.println("Student ID is valid");
    };

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
void GQ_Submit(ActionEvent event) {

}

@FXML //When "Submit" button is pressed in Invalid General Questions
void IGQ_Submit(ActionEvent event) {

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

}

@FXML //Method to store student's selected nationality
void GetNationality(ActionEvent event) {

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