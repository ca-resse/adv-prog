package com.group10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        surveyid_col.setCellValueFactory(new PropertyValueFactory<>("surveyID")); //must match surveyID variable from the Survey class 
        surveytitle_col.setCellValueFactory(new PropertyValueFactory<>("surveyTitle")); //must match surveyTitle variable from the Survey class
        surveydetails_col.setCellValueFactory(new PropertyValueFactory<>("surveyDetails")); //must match surveyDetails variable from the Survey class
        creatorname_col.setCellValueFactory(new PropertyValueFactory<>("creatorName")); //must match creatorName variable from the Survey class

        // // Load data from file and populate the TableView
        // try {
        //     List<Survey> surveylist = readDataFromFile("surveylist.txt");
        //     surveylist_table.getItems().addAll(surveylist);
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }

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
                //Create new survey object
                Survey survey = new Survey(Integer.parseInt(surveyid), surveytitle, creatorname, surveydetails);
            
                // populate tableview with data from the survey object
                surveylist_table.getItems().add(survey);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private List<Survey> readDataFromFile(String filePath) throws IOException {
        List<Survey> surveylist = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Line: " + line);
                String[] parts = line.split("\\t");
                System.out.println("Parts length= " + parts.length);
                if (parts.length >= 4) {
                    String surveyid = parts[0].trim();
                    String surveytitle = parts[1].trim();
                    String surveydetails = parts[2].trim();
                    String creatorname = parts[3].trim();
                    surveylist.add(new Survey(Integer.parseInt(surveyid), surveytitle, creatorname, surveydetails));
                } else {
                    System.out.println("Index Out of bounds.");
                    for (int i = 0; i < parts.length; i++) {
                    String item = parts[i].trim();
                    System.out.println("item[" + i + "] = " + item);
                    }
                }
            }
        }

        return surveylist;
    }
    
}
