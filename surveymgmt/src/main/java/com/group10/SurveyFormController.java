package com.group10;

import java.io.File;
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
        StringBuilder sb = new StringBuilder();
        sb.append(newSurveyTitle.getText().toString() + "\n");
        sb.append(newSurveyDetails.getText().toString() + "\n");

        File file = new File("surveylist.txt");
        FileWriter fw = new FileWriter(file);
        fw.write(sb.toString());
        fw.close();

        App.setRoot("secondary");
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
