package com.group10;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.ResourceBundle;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class SurveyFormController implements Initializable {
    @FXML
    private Button addNewQuestion_btn;

    @FXML
    private AnchorPane createSurvey_form;

    @FXML
    private Button create_btn;

    @FXML
    private Label creator_id;

    @FXML
    private Label creator_name;

    @FXML
    private Button logout_btn;

    @FXML
    private TextArea newSurveyDetails;

    @FXML
    private TextField newSurveyTitle;

    @FXML
    private ListView<?> questionListView;

    @FXML
    private Label survey_id;

    @FXML
    public void onClick_create_btn (ActionEvent e) throws IOException{
        // To append new survey information to the list

        // The json file containing list of surveys and their respective data
        String filePath = "surveylist.json";
        // Generate a unique survey_id for each new entry based on previous surveyid
        Integer survey_id = getLastSurveyIDjson(filePath) + 1;
        // pending method to obtain creator_name
        String creator_name = "placeholder";
        String surveytitle = newSurveyTitle.getText().toString();
        String surveydetails = newSurveyDetails.getText().toString();
        boolean is_started = true;
        boolean is_blocked = false;

        // Create a JSON Object for new survey
        JSONObject survey = new JSONObject();
        survey.put("survey_id", survey_id.toString());
        survey.put("survey_title", surveytitle);
        survey.put("survey_details", surveydetails);
        survey.put("creator_name", creator_name);
        survey.put("isStarted", is_started);
        survey.put("isBlocked", is_blocked);

        // Read existing JSON array from file
        JSONParser parser = new JSONParser();
        
        File file = new File(filePath);

        if (!file.exists() || file.length() == 0){
            try (FileWriter emptyFile = new FileWriter("surveylist.json")) {
            // Write an empty JSON array "[]" into the file
            emptyFile.write("[]");
            System.out.println("surveylist.json created with an empty array");
            } catch (IOException e2){
                e2.printStackTrace();
            }
        }

        try (FileReader fr = new FileReader(filePath)){
            JSONArray existingSurveys = (JSONArray) parser.parse(fr);

            // Append new survey object to existing JSON array
            existingSurveys.add(survey);

            // Write updated JSON array back to surveylist.json
            try (FileWriter fw = new FileWriter(filePath)){
                fw.write(existingSurveys.toJSONString());
                System.out.println("New survey added.");
            } catch (IOException e3){
                e3.printStackTrace();
            }
        } catch (IOException|org.json.simple.parser.ParseException e1) {
            e1.printStackTrace();
        }
        App.setRoot("surveylist");
    }

    // Get last SurveyID from json file
    private int getLastSurveyIDjson (String filePath) throws IOException{
        int surveyid = 10000;

        JSONParser parser = new JSONParser();

        try (Reader reader = new FileReader(filePath)){
            JSONArray array = (JSONArray) parser.parse(reader);

            if (array.isEmpty() == false){
                JSONObject lastSurvey = (JSONObject) array.get(array.size() - 1);
                surveyid = Integer.parseInt((String) lastSurvey.get("survey_id"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return surveyid;
    }


    @FXML
    public void onClick_logout_btn (ActionEvent e) throws IOException{
        App.setRoot("primary");
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
    }
    
}
