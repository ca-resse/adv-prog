package com.group10;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class EditUserDetailsController implements Initializable{

    @FXML
    private Text AdminID;

    @FXML
    private Text CreatorID;

    @FXML
    private Text DOB;

    @FXML
    private TextField EmailTxtFld;

    @FXML
    private TextField FacultyTxtFld;

    @FXML
    private TextField FirstNameTxtFld;

    @FXML
    private Text Gender;

    @FXML
    private TextField LastNameTxtFld;

    @FXML
    private TextField PasswordTxtFld;

    @FXML
    private TextField PhoneNoTxtFld;

    @FXML
    private TextField UsernameTxtFld;

    @FXML
    private AnchorPane rootPane;

    @Override // To initialize the userdetails from the file onto the screen
    public void initialize (URL url, ResourceBundle rb){

        try (Reader fileReader = new FileReader("src/main/resources/com/group10/UserDetails.json")) {
            JSONObject readData = (JSONObject) new JSONParser().parse(fileReader);      //instantiates "readData" object to store read data
            JSONArray readArr = (JSONArray) readData.get("userDetailsArray");      //instantiates "readArr" as an array object to store the specified array
            

            for (Object user: readArr) {    //for loop used to read each element of the array
                System.out.println(((JSONObject) user).get("creatorID"));

                String viewedUser = "";     //Just to initialize the variable
                try {
                    // Creates new filereader object
                    File myObj = new File("src/main/resources/com/group10/viewedUser.txt");
                    Scanner myReader = new Scanner(myObj);
                    while (myReader.hasNextLine()) {        //reads every line of the textfile
                        viewedUser = myReader.nextLine();   // Stores read data into variable
                    }
                    myReader.close();
                } catch (FileNotFoundException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }

                // System.out.println(viewedUser);

                if (((((JSONObject) user).get("creatorID")).equals(viewedUser))){         //forgive me for the horrendous amount of brackets
                    System.out.println("Editing " + viewedUser);
                    // Once again, forgive me for this horrendous code block...

                    // Retrieves each data value of the specified user and displays it on the screen
                    JSONObject displayedUser = ((JSONObject) user);
                    AdminID.setText((displayedUser.get("adminID")).toString());
                    CreatorID.setText((displayedUser.get("creatorID")).toString());
                    DOB.setText((displayedUser.get("DOB")).toString());
                    Gender.setText((displayedUser.get("gender")).toString());
                }
            }
        
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

    }

    @FXML   // To update the details of the selected user
    void FinishEditing(ActionEvent event) throws IOException, ParseException {

        // Code block to store the inputted text to buffer variables
        String newFirstName = FirstNameTxtFld.getText();
        String newLastName = LastNameTxtFld.getText();
        String newUsername = UsernameTxtFld.getText();
        String newFaculty = FacultyTxtFld.getText();
        String newEmail = EmailTxtFld.getText();
        String newPhoneNo = PhoneNoTxtFld.getText();
        String newPassword = PasswordTxtFld.getText();

        // These booleans exist so that the if statement won't give me a headache
        Boolean isFNameEmpty = (newFirstName.equals(""));
        Boolean isLNameEmpty = (newLastName.equals(""));
        Boolean isUsernameEmpty = (newUsername.equals(""));
        Boolean isPasswordEmpty = (newPassword.equals(""));
        Boolean canProceed = (isFNameEmpty || isLNameEmpty || isUsernameEmpty || isPasswordEmpty);

        // If statement so that the user cannot leave the require fields empty
        if (canProceed) {
            System.out.println("Required fields cannot be empty");
        } else {

            System.out.println("Editing confirmed");

            String viewedUser = "";     //Just to initialize the variable
            try {
                // Creates new filereader object
                File myObj = new File("src/main/resources/com/group10/viewedUser.txt");
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {        //reads every line of the textfile
                    viewedUser = myReader.nextLine();   // Stores read data into variable
                }
                myReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            
            // Creating a new file reader object to extract file data
            FileReader myFileReader = new FileReader("src/main/resources/com/group10/UserDetails.json");
            // Store file data into new JSONObject
            JSONObject readFile = (JSONObject) new JSONParser().parse(myFileReader);
            // Store the Array within the file onto a new JSONArray object
            JSONArray readArray = (JSONArray) readFile.get("userDetailsArray");

            // Preparing values to jump-start while loop
            int arrPoint = 0;        // Used to identify the index of the array
            JSONObject tempUser = (JSONObject) (readArray).get(arrPoint);         // Stores each individual element object of the array
            String tempID = (tempUser.get("creatorID")).toString();          // Converts data to string

            // Runs through each element in the array and stops when it reaches the specified one
            while (!(tempID.equals(viewedUser))) {                 
                arrPoint++;
                tempUser = (JSONObject) (readArray).get(arrPoint);
                tempID = (tempUser.get("creatorID")).toString();
            }
            
            // Updates the specified keys of the particular element object
            tempUser.put("firstName", newFirstName);
            tempUser.put("lastName", newLastName);
            tempUser.put("username", newUsername);
            tempUser.put("faculty", newFaculty);
            tempUser.put("emailAddress", newEmail);
            tempUser.put("password", newPassword);
            tempUser.put("phoneNo", newPhoneNo);

            // Overwrites the entire JSON file with the updated JSONObject
            FileWriter myFileWriter = new FileWriter("src/main/resources/com/group10/UserDetails.json");
            myFileWriter.write(readFile.toJSONString());
            myFileWriter.flush();
            myFileWriter.close();

            System.out.println("UserDetails has been updated");

            // Switches back to view user details screen to see the changes
            App.setRoot("ViewUserDetails");
        }
    }

    @FXML
    void Logout(ActionEvent event) throws IOException {
        App.setRoot("PlaceholderScreen");
    }

    @FXML
    void ReturnToManageUsers(ActionEvent event) throws IOException {
        App.setRoot("ManageUsersList");
    }

}
