package com.group10;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
    private Button startClose_btn;

    @FXML
    private Button EditSurvey_btn;

    @FXML
    private Button deleteSurvey_btn;

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

    @FXML
    public void onClick_startClose_btn (ActionEvent e) throws IOException{
        System.out.println("Start/Close button clicked.");
        Survey selectedSurvey = surveylist_table.getSelectionModel().getSelectedItem();
        if (selectedSurvey != null){
            if (selectedSurvey.getIsStarted() == true){
                selectedSurvey.setIsStarted(false);
                System.out.println(selectedSurvey.getSurveyTitle() + " - started status set to false: " + selectedSurvey.getIsStarted());
                updateSurveyListJSON(selectedSurvey);
            } else {
                selectedSurvey.setIsStarted(true);
                System.out.println(selectedSurvey.getSurveyTitle() + " - started status set to true: " + selectedSurvey.getIsStarted());
                updateSurveyListJSON(selectedSurvey);
            }
        }
        // Refresh the tableview for updated contents
        surveylist_table.refresh();
    }

    @FXML
    // To access survey editor page
    public void onClick_editSurvey_btn (ActionEvent e) throws IOException{
        System.out.println("Edit Survey button clicked.");
        Survey selectedSurvey = surveylist_table.getSelectionModel().getSelectedItem();
        //int surveyid = selectedSurvey.getSurveyID();
        int surveyid = 10003;
        new EditSurveyController(surveyid);
    }

    @FXML
    public void onClick_deleteSurvey_btn (ActionEvent e) throws IOException{
        System.out.println("Delete Survey button clicked.");
    }

    public void createSurvey() {
//        for (int newSurvID = 1;; newSurvID++) {
//            if (newSurvID)
//        }
//        File newSurv = new File("src/main/resources/com/group10/JSON/Surveys/Survey_");
    }

    private void updateSurveyListJSON(Survey selectedSurvey){
        //Load JSON Array
        String filePath = "src/main/resources/com/group10/JSON/surveylist.json";
        JSONParser parser = new JSONParser();
        try (FileReader fr = new FileReader(filePath)){
            JSONArray array = (JSONArray) parser.parse(fr);

            //Update the JSON data with modified survey
            for (Object obj : array) {
                JSONObject survObject = (JSONObject) obj;
                String surveyID = (String) survObject.get("survey_id");
                if (Integer.parseInt(surveyID) == selectedSurvey.getSurveyID()) {
                    System.out.println("isStarted:" + selectedSurvey.getIsStarted());
                    survObject.put("isStarted", selectedSurvey.getIsStarted());
                    
                    break;
                }
            }
            //Rewrite JSON file with updated data
            try (FileWriter fw = new FileWriter(filePath)) {
                fw.write(array.toJSONString());
                System.out.println("surveylist.json updated successfully.");
            }
        } catch (IOException|ParseException e){
            e.printStackTrace();
        }
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
        try(FileReader fr = new FileReader("src/main/resources/com/group10/JSON/surveylist.json")){
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
                System.out.println("You have selected: " + selectedSurvey.getSurveyTitle());
                // try {
                //     App.setRoot("secondary");
                // } catch (IOException e) {
                //     e.printStackTrace();
                //     System.out.println("Page not found.");
                // }
            }
        });
    }
    
}
