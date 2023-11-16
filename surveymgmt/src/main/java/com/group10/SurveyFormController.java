package com.group10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
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

        String filePath = "surveylist.txt";
        // Generate a unique survey_id for each new entry
        Integer survey_id = getLastSurveyID(filePath) + 1;
        // pending method to obtain creator_name
        String creator_name = "placeholder";
        String surveytitle = newSurveyTitle.getText().toString();
        String surveydetails = newSurveyDetails.getText().toString();
        boolean is_started = true;
        boolean is_blocked = false;

        FileWriter file = new FileWriter(filePath, true);
        if (survey_id > 1){
            file.write(survey_id.toString() + "\t" + surveytitle + "\t" + surveydetails + "\t" + creator_name + "\n");
        } else {
            System.out.println("Entry not added, error generating survey id.\n Check surveylist.txt");
        }
        file.close();

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
        try (FileReader fr = new FileReader("surveylist.json")){
            JSONArray existingSurveys = (JSONArray) parser.parse(fr);

            // Append new survey object to existing JSON array
            existingSurveys.add(survey);

            // Write updated JSON array back to surveylist.json
            try (FileWriter fw = new FileWriter("surveylist.json")){
                fw.write(existingSurveys.toJSONString());
                System.out.println("New survey added.");
            } catch (IOException e2){
                e2.printStackTrace();
            }
        } catch (IOException|org.json.simple.parser.ParseException e1) {
            e1.printStackTrace();
        }
        App.setRoot("surveylist");
    }

    // method to return the survey_id from the last line in the text file
    private int getLastSurveyID(String filePath) throws IOException{
        int surveyid = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Line: " + line);
                String[] parts = line.split("\\t");
                System.out.println("Parts length= " + parts.length);
                if (parts.length >= 4) {
                    surveyid = Integer.parseInt(parts[0].trim());
                } else {
                    System.out.println("Error in text file format.");
                    for (int i = 0; i < parts.length; i++) {
                    String item = parts[i].trim();
                    System.out.println("item[" + i + "] = " + item);
                    }
                }
            }
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
