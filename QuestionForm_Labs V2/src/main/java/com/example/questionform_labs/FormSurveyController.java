package com.example.questionform_labs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ResourceBundle;

public class FormSurveyController implements Initializable {
    @FXML
    private VBox rootPane;
    @FXML
    private Label header;
    @FXML
    private VBox quesList;
    Queue<FormQuestionController> quesCtrlList = new LinkedList<>();

    public void back(ActionEvent event) throws IOException {
        Pane newRoot = FXMLLoader.load(getClass().getResource("/FXML/" + "EditSurvey" + ".fxml"));
        rootPane.getChildren().setAll(newRoot);
    }

    int survID = 3;
    int respondentID = 2;
    public void initSurv() throws IOException, ParseException {
        Reader jsonRead = new FileReader("src/main/resources/JSON/Surveys/Survey_" + String.format("%04d", survID) + ".json");
        JSONObject survData = (JSONObject) new JSONParser().parse(jsonRead);
        header.setText(survData.get("title").toString());

        JSONArray quesArr = (JSONArray) survData.get("questionsList");
        for (Object ques : quesArr) {
            FXMLLoader newQues = new FXMLLoader(getClass().getResource("/FXML/FormQuestion.fxml"));
            quesList.getChildren().add(newQues.load());

            FormQuestionController quesCtrl = newQues.getController();
            quesCtrl.initQues((JSONObject) ques);
            quesCtrlList.add(quesCtrl);
        }
    }

    public void save() throws IOException, ParseException {
        Reader jsonRead = new FileReader("src/main/resources/JSON/Surveys/Survey_" + String.format("%04d", survID) + ".json");
        JSONArray responsesList = (JSONArray) new JSONParser().parse(jsonRead);
        for (Object response : responsesList) {
            ((JSONObject) response).get("");
        }
    }

    public void submit() throws IOException, ParseException {
        save();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            initSurv();
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
}