package com.group10;

import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
import javafx.util.Duration;
//import org.json.simple.parser.ParseException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Queue;

public class FormSurveyController {
    @FXML
    private Pane rootPane;
    @FXML
    private Label title;
    @FXML
    private Label desc;
    @FXML
    private VBox quesList;
    @FXML
    private Label saveIndicator;
    private int survID;
    private int respID;
    Queue<FormQuestionController> quesCtrlList = new LinkedList<>();

    public FormSurveyController(int survID, int respID) throws IOException {
        this.survID = survID;
        this.respID = respID;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/group10/FXML/FormSurvey.fxml"));
        loader.setController(this);
        App.getScene().setRoot(loader.load());
        initSurv();
    }

    public void back(ActionEvent event) throws IOException {
        new EditSurveyController(3);
    }

    public void initSurv() throws IOException {
        // Reads data from JSON files
        String survRead = new String((Files.readAllBytes(Paths.get("src/main/resources/JSON/Surveys/Survey_" + String.format("%04d", survID) + ".json"))));
        JSONObject survData = new JSONObject(survRead);
        String respRead = new String((Files.readAllBytes(Paths.get("src/main/resources/JSON/Responses/Response_" + String.format("%04d", survID) + ".json"))));
        JSONArray respData = new JSONArray(respRead);

        title.setText(survData.get("title").toString());
        desc.setText(survData.get("description").toString());

        // Populates quesList VBox with FormQuestions
        JSONArray quesArr = (JSONArray) survData.get("questionsList");
        for (Object ques : quesArr) {
            FXMLLoader newQues = new FXMLLoader(getClass().getResource("/com/group10/FXML/FormQuestion.fxml"));
            quesList.getChildren().add(newQues.load());

            FormQuestionController quesCtrl = newQues.getController();
            quesCtrl.initQues((JSONObject) ques);
            quesCtrlList.add(quesCtrl);
        }
    }

    public void save() throws IOException {
//        Reader jsonRead = new FileReader("src/main/resources/JSON/Surveys/Survey_" + String.format("%04d", survID) + ".json");
//        JSONArray responsesList = (JSONArray) new JSONParser().parse(jsonRead);
        String jsonRead = new String((Files.readAllBytes(Paths.get("src/main/resources/JSON/Responses/Response_" + String.format("%04d", survID) + ".json"))));
        JSONArray responsesList = new JSONArray(jsonRead);
        JSONObject response = new JSONObject();
        response.put("respondentID", respID);

        // Create a JSONArray for all the answers from the respondent
        JSONArray answersList = new JSONArray();
        response.put("answersList", answersList);
        for (FormQuestionController questCtrl : quesCtrlList) {
            JSONObject answer = new JSONObject();
            answer.put("questionID", questCtrl.getQuesID());
            answer.put("answer", questCtrl.getAnswer());

            answersList.put(answer);
        }
        for (int i = 0; i < responsesList.length(); i++) {
            if (responsesList.getJSONObject(i).get("respondentID").equals(respID)) {
                responsesList.put(i, response);
//                return;
            }
//            responsesList.put(response);
        }

        FileWriter jsonWrite = new FileWriter("src/main/resources/JSON/Responses/Response_" + String.format("%04d", survID) + ".json");
        jsonWrite.write(responsesList.toString());
        jsonWrite.flush();
        jsonWrite.close();

        saveAnim();
    }

    boolean isAnimFinished = true;
    public void saveAnim() { // Saved indicator animation
        TranslateTransition moveIn = new TranslateTransition();
        moveIn.setByX(-65);
        moveIn.setDuration(Duration.millis(300));

        TranslateTransition moveOut = new TranslateTransition();
        moveOut.setByX(65);
        moveOut.setDuration(Duration.millis(1000));

        PauseTransition pause = new PauseTransition(Duration.millis(3000));
        SequentialTransition seq = new SequentialTransition(saveIndicator, moveIn, pause, moveOut);

        if (isAnimFinished) {
            seq.play();
            isAnimFinished = false;
            seq.setOnFinished(e -> isAnimFinished = true);
        }
    }

    public void submit() throws IOException {
        save();
    }
}