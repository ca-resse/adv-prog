package com.example.questionform_labs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditSurveyController implements Initializable{
    @FXML
    private VBox rootPane;
    @FXML
    private VBox questionsList;

    public void addQuestion() throws IOException {
        FXMLLoader newQuestion = new FXMLLoader(getClass().getResource("/FXML/QuestionEdit.fxml"));
        questionsList.getChildren().add(newQuestion.load());
        EditQuestionController quesCtrl = newQuestion.getController();
        quesCtrl.setSurvCtrl(this);
    }

    public void switchScreen(String fileName) throws IOException {
        Pane newRoot = FXMLLoader.load(getClass().getResource("/FXML/" + fileName + ".fxml"));
        rootPane.getChildren().setAll(newRoot);
    }

    public void back(ActionEvent event) throws IOException {
        switchScreen("Home");
    }

    public void logout(ActionEvent event) throws IOException {
        switchScreen("Login");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        try {
            addQuestion();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}