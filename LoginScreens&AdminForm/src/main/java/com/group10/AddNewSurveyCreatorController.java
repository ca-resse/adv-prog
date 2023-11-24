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

public class AddNewSurveyCreatorController implements Initializable{

    @FXML
    private Text AdminID;

    @FXML
    private Text CreatorID;

    @FXML
    private TextField DOBTxtFld;

    @FXML
    private TextField EmailTxtFld;

    @FXML
    private TextField FacultyTxtFld;

    @FXML
    private TextField FirstNameTxtFld;

    @FXML
    private TextField GenderTxtFld;

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

    @Override // To display what the new user's Creator ID will be on the screen
    public void initialize (URL url, ResourceBundle rb){

        // Initialize the variable to store what the next available id is
        int nextCreatorID = 0;

        // Try catch block dedicated for reading from JSON file and dumping into the table
        try (Reader fileReader = new FileReader("src/main/resources/com/group10/UserDetails.json")) {
            JSONObject readData = (JSONObject) new JSONParser().parse(fileReader);      //instantiates "readData" object to store read data
            JSONArray readArr = (JSONArray) readData.get("userDetailsArray");   //instantiates "readArr" object to store the JSONArray within file

            // For loop checks through every single element in array
            for (Object obj : readArr) {

                // Retrieving the creatorID of each element for comparison
                JSONObject indivDetail = (JSONObject) obj;
                int creatorID = Integer.parseInt((String) indivDetail.get("creatorID"));

                // Puts the larger creator ID into the variable
                if (creatorID > nextCreatorID) {
                    nextCreatorID = creatorID;
                }
            }

            nextCreatorID++; // Increments so we know what the actual next available ID is
            System.out.println(nextCreatorID);
            String StrNCID = Integer.toString(nextCreatorID); // Converts the ID integer into String to avoid complications when writing to text file
            System.out.println(StrNCID);
            CreatorID.setText(StrNCID);
            AdminID.setText("NULL");

            // Try-catch block that is used to store id of the user that is about to be viewed into the text file
            try {
                FileWriter myWriter = new FileWriter("src/main/resources/com/group10/nextCreatorID.txt");      //Creates new filewriter object
                myWriter.write(StrNCID);          // Stores the id of the creator about to be viewed
                myWriter.close();                   //closes object
                System.out.println("Successfully wrote to the file.");
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    
    }

    @FXML // Adds a new survey creator account into the json file upon clicking
    void ConfirmAdding(ActionEvent event) throws IOException, ParseException {

        String nextCreatorID = "";
        try {
            // Creates new filereader object
            File myObj = new File("src/main/resources/com/group10/nextCreatorID.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {        //reads every line of the textfile
                nextCreatorID = myReader.nextLine();   // Stores read data into variable
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        System.out.println(nextCreatorID);

        // Code block to store the inputted text to buffer variables
        String newFirstName = FirstNameTxtFld.getText();
        String newLastName = LastNameTxtFld.getText();
        String newUsername = UsernameTxtFld.getText();
        String newFaculty = FacultyTxtFld.getText();
        String newEmail = EmailTxtFld.getText();
        String newPhoneNo = PhoneNoTxtFld.getText();
        String newPassword = PasswordTxtFld.getText();
        String newDOB = DOBTxtFld.getText();
        String newGender = GenderTxtFld.getText();

        // Boolean variables so I won't have a headache in the if statement
        Boolean isFirstNameEmpty = (newFirstName.equals(""));
        Boolean isLastNameEmpty = (newLastName.equals(""));
        Boolean isUsernameEmpty = (newUsername.equals(""));
        Boolean isFacultyEmpty = (newFaculty.equals(""));
        Boolean isEmailEmpty = (newEmail.equals(""));
        Boolean isPhoneNoEmpty = (newPhoneNo.equals(""));
        Boolean isPasswordEmpty = (newPassword.equals(""));
        Boolean isDOBEmpty = (newDOB.equals(""));
        Boolean isGenderEmpty = (newGender.equals(""));
        Boolean thereExistsEmpty = (isFirstNameEmpty || isLastNameEmpty || isUsernameEmpty || isFacultyEmpty || isEmailEmpty || isPhoneNoEmpty || isPasswordEmpty || isDOBEmpty || isGenderEmpty);
        
        if (thereExistsEmpty){
            System.out.println("There is a text field empty");
        } else {
            System.out.println("New Survey Creator added");

            // ******************Read from file********************
            // Create file reader object called "readfile"
            FileReader readfile = new FileReader("src/main/resources/com/group10/UserDetails.json");
            // Store all existing data into a new JSONObject
            JSONObject readdata = (JSONObject) new JSONParser().parse(readfile);
            // Store existing array data into new array object
            JSONArray readarr = (JSONArray) readdata.get("userDetailsArray");

            //********Creating the object to be appended into the array*******
            JSONObject inputData = new JSONObject();
            inputData.put("firstName", newFirstName);
            inputData.put("lastName", newLastName);
            inputData.put("emailAddress", newEmail);
            inputData.put("password", newPassword);
            inputData.put("gender", newGender);
            inputData.put("DOB", newDOB);
            inputData.put("phoneNo", newPhoneNo);
            inputData.put("username", newUsername);
            inputData.put("faculty", newFaculty);
            inputData.put("adminID", "NULL");
            inputData.put("creatorID", nextCreatorID);
            inputData.put("isAdmin", "NULL");


            //append to jsonarray
            readarr.add(inputData); // Additional element added to array before writing
            readdata.put("userDetailsArray", readarr);

            // Write to file
            FileWriter writefile = new FileWriter("src/main/resources/com/group10/UserDetails.json");
            writefile.write(readdata.toJSONString());
            writefile.flush(); //
            writefile.close(); // Closes filewriter

            // Switches screen
            App.setRoot("NewCreatorPrompt");
        }
    
    }

    @FXML
    void Logout(ActionEvent event) throws IOException {
        App.setRoot("HomePage");
    }

    @FXML
    void ReturnToManageUsers(ActionEvent event) throws IOException {
        App.setRoot("ManageUsersList");
    }

}
