package com.group10;

import javafx.animation.*;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.JSONArray;
import org.json.JSONObject;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class EditSurveyController { // implements Initializable
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
    private JSONObject survData;
    private Queue<EditQuestionController> quesCtrlList = new LinkedList<>();

    private int survID;

    public EditSurveyController(int survID) throws IOException {
        this.survID = survID;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("C:\\Users\\Caresse Tan\\Desktop\\Repositry\\adv-prog\\surveymgmt\\src\\main\\resources\\com\\group10\\EditSurvey.fxml"));
        loader.setController(this);
        App.setRoot(loader.load());
        initSurv();
    }

    public EditQuestionController addQuestion() throws IOException {
        FXMLLoader newQues = new FXMLLoader(getClass().getResource("C:\\Users\\Caresse Tan\\Desktop\\Repositry\\adv-prog\\surveymgmt\\src\\main\\resources\\com\\group10\\EditQuestion.fxml"));
        quesList.getChildren().add(newQues.load());

        EditQuestionController quesCtrl = newQues.getController();
        quesCtrl.setSurvCtrl(this); // Pass this EditSurveyController instance onto EditQuestionController to allow it to access this addQuestion function
        quesCtrl.setQuesID(survData.getJSONArray("questionsList").length() + 1);
        quesCtrlList.add(quesCtrl); // Add EditQuestionController to queue to reference when saving
        return quesCtrl; // Returns the EditQuestionController so its variables could be accessed
    }

    public void initSurv() throws IOException {
//        System.out.println("Test");
////        FileReader jsonFile = new FileReader("src/main/resources/JSON/Surveys/Survey_" + survID + ".json");
////        survData = (JSONObject) new JSONParser().parse(jsonFile);
//        Parent root = FXMLLoader.load(getClass().getResource("/FXML/EditSurvey.fxml"));
////        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
////        Stage stage = primaryStage;
//        Scene scene = new Scene(root, 1000, 600);
//        stage.setScene(scene);
//        stage.show();

        String jsonRead = new String((Files.readAllBytes(Paths.get("C:\\Users\\Caresse Tan\\Desktop\\Repositry\\adv-prog\\Survey_" + survID + ".json"))));
        survData = new JSONObject(jsonRead);

//        if (((JSONArray) survData.get("questionsList")).isEmpty()) {
//            addQuestion();
//            return;
//        }
        survTitle.setText(survData.get("title").toString());
        survDesc.setText(survData.get("description").toString());

        JSONArray quesDataList = survData.getJSONArray("questionsList");
        for (Object quesData : quesDataList) {
            EditQuestionController quesCtrl = addQuestion();
            quesCtrl.setQuesID(((JSONObject) quesData).getInt("questionID"));
            quesCtrl.setQuesText(((JSONObject) quesData).get("questionText").toString());
            quesCtrl.setQuesType(((JSONObject) quesData).get("questionType").toString());
            quesCtrl.setIsRequired(((JSONObject) quesData).get("isRequired").toString());

            if (((JSONObject) quesData).get("questionType").equals("Multiple Choice")) {
                JSONArray optList = ((JSONObject) quesData).getJSONArray("answerOptions");
                quesCtrl.clearOptList();
                for (Object opt : optList) {
                    quesCtrl.addOption().setOptText(opt.toString());
                }
            } else if (((JSONObject) quesData).get("questionType").equals("Scale")) {
                quesCtrl.getScaleCtrl().setOptMin(((JSONObject) quesData).getString("optionMin"));
                quesCtrl.getScaleCtrl().setOptMax(((JSONObject) quesData).getString("optionMax"));
                quesCtrl.getScaleCtrl().setDivisions(((JSONObject) quesData).getInt("optionDivisions"));
            }
        }
    }

    public void save() throws IOException {
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
            quesDataList.put(quesData);

            quesData.put("questionID", quesCtrl.getQuesID());
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

                for (MultipleChoiceOptionController optCtrl: quesCtrl.getOptCtrlList()) {
                    optList.put(optCtrl.getOptText());
                }
            } else if (quesCtrl.getQuesType().equals("Scale")) {
                EditScaleController scaleCtrl = quesCtrl.getScaleCtrl();
                quesData.put("optionDivisions", scaleCtrl.getDivisions());
                quesData.put("optionMin", scaleCtrl.getOptMin());
                quesData.put("optionMax", scaleCtrl.getOptMax());
            }
        }

        // Write to file
        FileWriter jsonFile = new FileWriter("Survey_" + survID + ".json");
        jsonFile.write(survData.toString());
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

//    public void updateSurvList() throws IOException {
//        File survDir = new File("src/main/resources/JSON/Surveys");
//        File[] survList = survDir.listFiles();
//        for (File survEntry : survList) {
//            FileReader reader = new FileReader(survEntry);
//            JSONObject survData = (JSONObject) new JSONParser().parse(reader);
//            String survTitle = survData.get("title").toString();
//            String survDesc = survData.get("description").toString();
//            System.out.printf("Title: %s | Descriptions: %s %n", survTitle, survDesc);
//        }
//    }

    public void switchScreen(String fileName) throws IOException {
        Pane newRoot = FXMLLoader.load(getClass().getResource(fileName + ".fxml"));
        rootPane.getChildren().setAll(newRoot);
    }

    public void back(ActionEvent event) throws IOException {
        switchScreen("Home");
    }

    public void logout(ActionEvent event) throws IOException {
        new FormSurveyController(3, 2);
    }
}