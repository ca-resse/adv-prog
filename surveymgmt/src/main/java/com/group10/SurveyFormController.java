package com.group10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

        FileWriter fw = new FileWriter(filePath, true);
        if (survey_id > 1){
            fw.write(survey_id.toString() + "\t" + surveytitle + "\t" + surveydetails + "\t" + creator_name + "\n");
        } else {
            System.out.println("Entry not added, error generating survey id.\n Check surveylist.txt");
        }
        fw.close();

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
