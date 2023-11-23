package com.group10;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class AnswerSurveyController {

    private Survey selectedSurvey;

    @FXML
    private Button back_btn;

    @FXML
    private Button logout_btn;

    @FXML
    private VBox questions_vbox;

    @FXML
    private AnchorPane answersurvey_page;

    @FXML
    private Label surveytitle_label;

    @FXML
    void onClick_logout_btn(ActionEvent event) throws IOException {
        App.setRoot("primary");
    }

    @FXML
    void onClick_back_btn(ActionEvent event) throws IOException {
        App.setRoot("studentsurveylist");
    }

    public void setSelectedSurvey (Survey survey){
        this.selectedSurvey = survey;
    }
    
    public void initialize(){
        // get survey title and set label
        System.err.println(this.selectedSurvey);
        String surveytitle = selectedSurvey.getSurveyTitle();
        surveytitle_label.setText(surveytitle);
    }

}
