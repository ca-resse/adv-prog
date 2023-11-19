package com.group10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


import java.io.IOException;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SurveyFormController {

   
      @FXML
    private Button addNewQuestion_btn;

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
    void onClick_create_btn(ActionEvent event) throws IOException {
        
        App.setRoot("surveylist");


    }

    
    @FXML
    void onClick_logout_btn(ActionEvent event) {

    }



}


