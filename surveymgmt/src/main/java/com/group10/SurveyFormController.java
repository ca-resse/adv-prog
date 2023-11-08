package com.group10;

import java.net.URL;
import java.util.ResourceBundle;

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
    private Button addNewSurvey_btn;

    @FXML
    private AnchorPane createSurvey_form;

    @FXML
    private Label creater_name;

    @FXML
    private Label creator_id;

    @FXML
    private TextArea newSurveyDetails;

    @FXML
    private TextField newSurveyTitle;

    @FXML
    private ListView<?> questionListView;

    @FXML
    private Label survey_id;

    private Survey survey;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
    }
    
}
