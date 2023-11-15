package com.example.questionform_labs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.ResourceBundle;

public class FormSurveyController implements Initializable {
    @FXML
    private VBox rootPane;
    @FXML
    private Label header;
    @FXML
    private VBox quesList;
    private JSONObject survData;

    public void back(ActionEvent event) throws IOException {
        Pane newRoot = FXMLLoader.load(getClass().getResource("/FXML/" + "EditSurvey" + ".fxml"));
        rootPane.getChildren().setAll(newRoot);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try (Reader reader = new FileReader("src/main/resources/JSON/Surveys/Survey_0003.json")) {
            survData = (JSONObject) new JSONParser().parse(reader);



        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        header.setText(survData.get("title").toString());
        JSONArray quesArr = (JSONArray) survData.get("questionsList");



        for (Object ques : quesArr) {
            try {
                FXMLLoader newQues = new FXMLLoader(getClass().getResource("/FXML/FormQuestion.fxml"));
                quesList.getChildren().add(newQues.load());
                FormQuestionController quesCtrl = newQues.getController();
                quesCtrl.initQues((JSONObject) ques);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

//        for (Object ques : quesArr) {
//            System.out.println(((JSONObject) ques).get("questionText"));
//        }
    }
}