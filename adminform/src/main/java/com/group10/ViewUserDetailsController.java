package com.group10;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class ViewUserDetailsController implements Initializable{

    @FXML
    private Text AdminID;

    @FXML
    private Text CreatorID;

    @FXML
    private Text DOB;

    @FXML
    private Text EmailAddress;

    @FXML
    private Text Faculty;

    @FXML
    private Text FirstName;

    @FXML
    private Text Gender;

    @FXML
    private Text LastName;

    @FXML
    private Text Password;

    @FXML
    private Text Username;

    @FXML
    private Text PhoneNo;

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
                    System.out.println("Viewing " + viewedUser);
                    // Once again, forgive me for this horrendous code block...

                    // Retrieves each data value of the specified user and displays it on the screen
                    JSONObject displayedUser = ((JSONObject) user);
                    AdminID.setText((displayedUser.get("adminID")).toString());
                    CreatorID.setText((displayedUser.get("creatorID")).toString());
                    DOB.setText((displayedUser.get("DOB")).toString());
                    EmailAddress.setText((displayedUser.get("emailAddress")).toString());
                    Faculty.setText((displayedUser.get("faculty")).toString());
                    FirstName.setText((displayedUser.get("firstName")).toString());
                    Gender.setText((displayedUser.get("gender")).toString());
                    LastName.setText((displayedUser.get("lastName")).toString());
                    Password.setText((displayedUser.get("password")).toString());
                    Username.setText((displayedUser.get("username")).toString());
                    PhoneNo.setText((displayedUser.get("phoneNo")).toString());
                }
            }
        
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void EditUserDetails(ActionEvent event) throws IOException {
        App.setRoot("EditUserDetails");
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