package com.group10;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class StudentController {

    @FXML
    private TableColumn<Survey, String> creatorname_col;

    @FXML
    private Button logout_btn;

    @FXML
    private Button refresh_btn;

    @FXML
    private TableColumn<Survey, String> surveydetails_col;

    @FXML
    private TableColumn<Survey, Integer> surveyid_col;

    @FXML
    private AnchorPane surveylist_page;

    @FXML
    private TableView<Survey> surveylist_table;

    @FXML
    private TableColumn<Survey, String> surveytitle_col;

    @FXML
    void onClick_logout_btn(ActionEvent event) throws IOException {
        App.setRoot("primary");
    }

    @FXML
    public void onClick_refresh_btn (ActionEvent e) throws IOException {
        
        //refresh the tableview for updated contents
        surveylist_table.refresh();
    }

    public void initialize(){
        //sets the reference value for each respective column
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

                // Students should only be able to access surveys that are "started" and "not blocked".
                // Only surveys with isStarted set to true and isBlocked set to false will be shown in the students' tableview
                if ((boolean) surveyObject.get("isStarted") == true && (boolean) surveyObject.get("isBlocked") == false){
                    String surveyid = (String) surveyObject.get("survey_id");
                    String surveytitle = (String) surveyObject.get("survey_title");
                    String creatorname = (String) surveyObject.get("creator_name");
                    String surveydetails = (String) surveyObject.get("survey_details");
                    //Create new survey object
                    Survey survey = new Survey(Integer.parseInt(surveyid), surveytitle, creatorname, surveydetails);
                
                    // populate tableview with data from the survey object
                    surveylist_table.getItems().add(survey);
                }   
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        surveylist_table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Get selected survey object
                Survey selectedSurvey = surveylist_table.getSelectionModel().getSelectedItem();
                // for debugging
                System.out.println(selectedSurvey.getSurveyTitle());

                //Load the survey page scene and pass the survey object data
                FXMLLoader loader = new FXMLLoader(getClass().getResource("answerSurvey.fxml"));

                try {
                    Parent root = loader.load();

                    //Get the associated controller class
                    AnswerSurveyController controller = loader.getController();

                    //Pass data to controller
                    controller.setSelectedSurvey(selectedSurvey);

                    //Set the new scene
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();

                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e){
                    e.printStackTrace();
                    System.out.println("Failed to load.");
                }
            }
        });
    }
}
