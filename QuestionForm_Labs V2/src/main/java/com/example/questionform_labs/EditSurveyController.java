package com.example.questionform_labs;

import javafx.animation.*;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URL;
import java.util.*;

public class EditSurveyController implements Initializable{
    @FXML
    private Pane rootPane;
    @FXML
    private TextField survTitle;
    @FXML
    private TextArea survDesc;
    @FXML
    private VBox quesList;
    @FXML
    private Label saveIndicator;
    Queue<EditQuestionController> quesCtrlList = new LinkedList<>();

    public EditQuestionController addQuestion() throws IOException {
        FXMLLoader newQues = new FXMLLoader(getClass().getResource("/FXML/EditQuestion.fxml"));
        quesList.getChildren().add(newQues.load());

        EditQuestionController quesCtrl = newQues.getController();
        quesCtrl.setSurvCtrl(this); // Pass this EditSurveyController instance onto EditQuestionController to allow it to access this addQuestion function
        quesCtrlList.add(quesCtrl); // Add EditQuestionController to queue to reference when saving
        return quesCtrl; // Returns the EditQuestionController so its variables could be accessed
    }

    String survID = "0003";

    public void initSurv() throws IOException, ParseException {
        FileReader jsonFile = new FileReader("src/main/resources/JSON/Surveys/Survey_" + survID + ".json");
        JSONObject survData = (JSONObject) new JSONParser().parse(jsonFile);
        if (((JSONArray) survData.get("questionsList")).isEmpty()) {
            addQuestion();
            return;
        }
        survTitle.setText(survData.get("title").toString());
        survDesc.setText(survData.get("description").toString());

        JSONArray quesDataList = (JSONArray) survData.get("questionsList");
        for (Object quesData : quesDataList) {
            EditQuestionController quesCtrl = addQuestion();
            quesCtrl.setQuesText(((JSONObject) quesData).get("questionText").toString());
            quesCtrl.setQuesType(((JSONObject) quesData).get("questionType").toString());
            quesCtrl.setIsRequired(((JSONObject) quesData).get("isRequired").toString());

//            JSONArray optList = (JSONArray) ((JSONObject) quesData).get("answerOptions");
            JSONArray optList = (JSONArray) ((JSONObject) quesData).get("answerOptions");
            if (((JSONObject) quesData).get("questionType").equals("Multiple Choice")) {
                quesCtrl.clearOptList();
                for (Object opt : optList) {
                    quesCtrl.addOption().setOptText(opt.toString());
                }
            }
        }
    }

    public void save() throws IOException, ParseException {
        // Create a JSONObject to store info about the whole survey
        JSONObject survData = new JSONObject();
        survData.put("title", survTitle.getText());
        survData.put("description", survDesc.getText());

        // Create a JSONArray and populate with questions
        JSONArray quesDataList = new JSONArray();
        survData.put("questionsList", quesDataList);

        for (EditQuestionController quesCtrl : quesCtrlList) {
            // Create a JSONObject to store info about individual questions
            JSONObject quesData = new JSONObject();
            quesDataList.add(quesData);

            quesData.put("questionText", quesCtrl.getQuesText());
            quesData.put("questionType", quesCtrl.getQuesType());
            quesData.put("isRequired", quesCtrl.getIsRequired());

//            switch (quesCtrl.getQuesType()) {
//                case "Multiple Choice" -> {
//                    JSONArray optList = new JSONArray();
//                    quesData.put("answerOptions", optList);
//
//                    for (MultipleChoiceOptionController optCtrl: quesCtrl.optCtrlList) {
//                        optList.add(optCtrl.getOptText());
//                    }
//                }
//            }
            if (quesCtrl.getQuesType().equals("Multiple Choice")) {
                JSONArray optList = new JSONArray();
                quesData.put("answerOptions", optList);
                System.out.println(quesCtrl.optCtrlList.size());

                for (MultipleChoiceOptionController optCtrl: quesCtrl.optCtrlList) {
                    optList.add(optCtrl.getOptText());
                }
            }
        }

        // Write to file
        FileWriter jsonFile = new FileWriter("src/main/resources/JSON/Surveys/Survey_" + survID + ".json");
        jsonFile.write(survData.toJSONString());
        jsonFile.flush();
        jsonFile.close();

        saveAnim();
    };

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

    public void updateSurvList() throws IOException, ParseException {
        File survDir = new File("src/main/resources/JSON/Surveys");
        File[] survList = survDir.listFiles();
        for (File survEntry : survList) {
            FileReader reader = new FileReader(survEntry);
            JSONObject survData = (JSONObject) new JSONParser().parse(reader);
            String survTitle = survData.get("title").toString();
            String survDesc = survData.get("description").toString();
            System.out.printf("Title: %s | Descriptions: %s %n", survTitle, survDesc);
        }
    }

    public void switchScreen(String fileName) throws IOException {
        Pane newRoot = FXMLLoader.load(getClass().getResource("/FXML/" + fileName + ".fxml"));
        rootPane.getChildren().setAll(newRoot);
    }

    public void back(ActionEvent event) throws IOException {
        switchScreen("Home");
    }

    public void logout(ActionEvent event) throws IOException {
        switchScreen("FormSurvey");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        try {
            initSurv();
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
}