package com.group10;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class SurveyListController {

    @FXML
    private Button createSurvey_btn;

    @FXML
    private TableColumn<Survey, String> blocked_col;

    @FXML
    private TableColumn<Survey, String> started_col;

    @FXML
    private TableColumn<Survey, String> creatorname_col;

    @FXML
    private Button logout_btn;

    @FXML
    private Button search_btn;

    @FXML
    private Button refresh_btn;

    @FXML
    private TextField search_field;

    @FXML
    private TableColumn<Survey, Integer> surveyid_col;

    @FXML
    private AnchorPane surveylist_page;

    @FXML
    private TableView<Survey> surveylist_table;

    @FXML
    private TableColumn<Survey, String> surveytitle_col;

    @FXML
    private TableColumn<Survey, String> surveydetails_col;
    
    @FXML
    private void switchToSurveyForm(ActionEvent e) throws IOException {
        App.setRoot("surveyform");
    }

    @FXML
    public void onClick_logout_btn (ActionEvent e) throws IOException{
        App.setRoot("primary");
    }
    
    public void initialize(){
        //sets the reference value for each respective column
        blocked_col.setCellValueFactory(new PropertyValueFactory<>("isBlocked")); //must match isStarted variable from the Survey class
        started_col.setCellValueFactory(new PropertyValueFactory<>("isStarted")); //must match isStarted variable from the Survey class
        surveyid_col.setCellValueFactory(new PropertyValueFactory<>("surveyID")); //must match surveyID variable from the Survey class 
        surveytitle_col.setCellValueFactory(new PropertyValueFactory<>("surveyTitle")); //must match surveyTitle variable from the Survey class
        surveydetails_col.setCellValueFactory(new PropertyValueFactory<>("surveyDetails")); //must match surveyDetails variable from the Survey class
        creatorname_col.setCellValueFactory(new PropertyValueFactory<>("creatorName")); //must match creatorName variable from the Survey class

        // Read the JSON Array to populate tableview
        JSONParser parser = new JSONParser();
        try(FileReader fr = new FileReader("surveylist.json")){
            JSONArray array = (JSONArray) parser.parse(fr);
            
            // Loop through the array and add data to Tableview
            for (Object obj : array){
                JSONObject surveyObject = (JSONObject) obj;
                String surveyid = (String) surveyObject.get("survey_id");
                String surveytitle = (String) surveyObject.get("survey_title");
                String creatorname = (String) surveyObject.get("creator_name");
                String surveydetails = (String) surveyObject.get("survey_details");
                boolean isBlocked = (boolean) surveyObject.get("isBlocked");
                boolean isStarted = (boolean) surveyObject.get("isStarted");
                //Create new survey object
                Survey survey = new Survey(Integer.parseInt(surveyid), surveytitle, creatorname, surveydetails);
            
                // set isBlocked and isStarted status from the json object.
                survey.setIsBlocked(isBlocked);
                survey.setIsStarted(isStarted);

                // populate tableview with data from the survey object
                surveylist_table.getItems().add(survey);
                
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        surveylist_table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Get selected survey object
                Survey selectedSurvey = surveylist_table.getSelectionModel().getSelectedItem();
                try {
                    App.setRoot("secondary");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Page not found.");
                }
            }
        });
    }
    
}
