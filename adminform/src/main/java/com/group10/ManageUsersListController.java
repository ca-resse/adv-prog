package com.group10;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.ResourceBundle;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class ManageUsersListController implements Initializable {

    @FXML
    private TableColumn<UsersClass, String> AdminID_col;

    @FXML
    private TableColumn<UsersClass, String> CreatorID_col;

    @FXML
    private TableColumn<UsersClass, String> CreatorName_col;

    @FXML
    private TableView<UsersClass> UserTable;

    @FXML
    private TableColumn<UsersClass, String> Username_col;

    @FXML
    private AnchorPane rootPane;

    @Override   // A method to initialize all the data into the table
    public void initialize (URL url, ResourceBundle rb){

        // Sets the reference values for each respective column
        CreatorName_col.setCellValueFactory(new PropertyValueFactory<UsersClass,String>("SCName"));
        Username_col.setCellValueFactory(new PropertyValueFactory<UsersClass,String>("username"));
        CreatorID_col.setCellValueFactory(new PropertyValueFactory<UsersClass,String>("creatorID"));
        AdminID_col.setCellValueFactory(new PropertyValueFactory<UsersClass,String>("adminID"));

        // Try catch block dedicated for reading from JSON file and dumping into the table
        try (Reader fileReader = new FileReader("src/main/resources/com/group10/UserDetails.json")) {
            JSONObject readData = (JSONObject) new JSONParser().parse(fileReader);      //instantiates "readData" object to store read data
            JSONArray readArr = (JSONArray) readData.get("userDetailsArray");   //instantiates "readArr" object to store the JSONArray within file

            // For loop checks through one element, stores it into table, then moves on to the next element
            for (Object obj : readArr) {

                //Initializing buffer variables to dump onto table
                JSONObject indivDetail = (JSONObject) obj;
                String firstName = (String) indivDetail.get("firstName");
                String lastName = (String) indivDetail.get("lastName");
                String creatorName = firstName + " " + lastName;
                String username = (String) indivDetail.get("username");
                String creatorID = (String) indivDetail.get("creatorID");
                String adminID = (String) indivDetail.get("adminID");

                // Instantiates a new buffer object to dump into table
                UsersClass user = new UsersClass(creatorName, username, creatorID, adminID);
                
                // Appends the buffer object onto the tableview
                UserTable.getItems().add(user);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void AddNewSurveyCreator(ActionEvent event) throws IOException {
        App.setRoot("AddNewSurveyCreator");
    }

    @FXML
    void ViewUser(ActionEvent event) throws IOException {
        System.out.println("View User Clicked");
        
        // Upon clicking "View User" button, copy data retrieved table to the new object
        UsersClass selectedUser = UserTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null){
            System.out.println(selectedUser.getSCName() + " has been selected");
        }

        // Try-catch block that is used to store id of the user that is about to be viewed into the text file
        try {
            FileWriter myWriter = new FileWriter("src/main/resources/com/group10/viewedUser.txt");      //Creates new filewriter object
            myWriter.write(selectedUser.getCreatorID());          // Stores the id of the creator about to be viewed
            myWriter.close();                   //closes object
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        // Goes to the View User Details screen
        App.setRoot("ViewUserDetails");

    }

    @FXML
    void Logout(ActionEvent event) throws IOException {
        App.setRoot("PlaceholderScreen");
    }

    @FXML
    void ReturnToSurveyList(ActionEvent event) throws IOException {
        App.setRoot("AdminViewSurveyList");
    }
}