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

public class AdminViewSurveyListController implements Initializable {

    @FXML
    private TableColumn<SurveysClass, Boolean> Block_col;

    @FXML
    private TableColumn<SurveysClass, String> CreatorName_col;

    @FXML
    private TableColumn<SurveysClass, Boolean> Running_col;

    @FXML
    private TableColumn<SurveysClass, String> SurveyID_col;

    @FXML
    private TableView<SurveysClass> SurveyTable;

    @FXML
    private TableColumn<SurveysClass, String> SurveyTitle_col;

    @FXML
    private AnchorPane rootPane;

    // Method for the table to display the info on the screen
    public void TableDump() {
        // Try catch block dedicated for reading from JSON file and dumping into the table
        try (Reader fileReader = new FileReader("src/main/resources/com/group10/surveylist.json")) {
            JSONArray readArr = (JSONArray) new JSONParser().parse(fileReader);      //instantiates "readData" object to store read data
            
            // For loop checks through one element, stores it into table, then moves on to the next element
            for (Object obj : readArr) {

                //Initializing buffer variables to dump onto table
                JSONObject indivSurvey = (JSONObject) obj;
                Boolean BlockStat = (Boolean) indivSurvey.get("isBlocked");
                Boolean StartStat = (Boolean) indivSurvey.get("isStarted");
                String surveyID = (String) indivSurvey.get("survey_id");
                String surveyTitle = (String) indivSurvey.get("survey_title");
                String creatorName = (String) indivSurvey.get("creator_name");

                // Instantiates a new buffer object to dump into table
                SurveysClass user = new SurveysClass(BlockStat, StartStat, surveyTitle, surveyID, creatorName);
                
                // Appends the buffer object onto the tableview
                SurveyTable.getItems().add(user);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Override   // A method to dump all the data into the table
    public void initialize (URL url, ResourceBundle rb){

        // Sets the references for each column
        Block_col.setCellValueFactory(new PropertyValueFactory<SurveysClass,Boolean>("blockedStatus"));
        Running_col.setCellValueFactory(new PropertyValueFactory<SurveysClass,Boolean>("startedStatus"));
        SurveyID_col.setCellValueFactory(new PropertyValueFactory<SurveysClass,String>("surveyID"));
        SurveyTitle_col.setCellValueFactory(new PropertyValueFactory<SurveysClass,String>("surveyTitle"));
        CreatorName_col.setCellValueFactory(new PropertyValueFactory<SurveysClass,String>("creatorName"));

        // Dumps all the info into the table using "TableDump" method
        TableDump();
    }

    @FXML
    void Logout(ActionEvent event) throws IOException {
        App.setRoot("HomePage");
    }

    @FXML   // Changes the blocked status of the survey
    void BlockSurvey(ActionEvent event) throws IOException, ParseException {
        
        String tempID = "";         // tempID will be used to identify which survey needs to be edited
        Boolean retrievedStatus = false;

        // Upon clicking "Block" button, copy data retrieved from table to the new object
        SurveysClass selectedSurvey = SurveyTable.getSelectionModel().getSelectedItem();
        if (selectedSurvey != null){
            retrievedStatus = selectedSurvey.getBlockedStatus();
            tempID = selectedSurvey.getSurveyID();
            System.out.println(tempID + " has been selected");
        }

        // If statement to invert the Boolean value
        if (retrievedStatus) {
            retrievedStatus = false;
        } else {
            retrievedStatus = true;
        }

        //Load JSON Array
        String filePath = "src/main/resources/com/group10/surveylist.json";
        JSONParser parser = new JSONParser();
        try (FileReader fr = new FileReader(filePath)){
            JSONArray array = (JSONArray) parser.parse(fr);

            //Update the JSON data with modified survey
            for (Object obj : array) {
                JSONObject survObject = (JSONObject) obj;

                // System.out.println("survObject: " + survObject);
                // System.out.println("tempID: " + tempID);
                // System.out.println("Get Survey ID: " + survObject.get("survey_id"));

                if (tempID.equals(survObject.get("survey_id"))) {
                    survObject.put("isBlocked", retrievedStatus);
                    System.out.println(survObject);
                    break;

                }
            }

            //Rewrite JSON file with updated data
            try (FileWriter fw = new FileWriter(filePath)) {
                fw.write(array.toJSONString());
                System.out.println("surveylist.json updated successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        
        selectedSurvey.SetBlockedStatus(retrievedStatus); // Updates that particular cell of the table
        SurveyTable.refresh(); // Refreshes table
    }

    @FXML
    void SwitchToManageUsers(ActionEvent event) throws IOException {
        App.setRoot("ManageUsersList");
    }

    @FXML
    void ViewSurvey(ActionEvent event) {
        System.out.println("View Survey");
    }

}