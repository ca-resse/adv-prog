package com.group10;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ResourceBundle;

public class EditQuestionController implements Initializable {
    @FXML
    private Pane rootPane;
    @FXML
    private TextArea quesText;
    @FXML
    private ChoiceBox<String> quesType;
    @FXML
    private ToggleButton isRequired;
    @FXML
    private VBox optList;
    private EditSurveyController survCtrl;
    private Queue<MultipleChoiceOptionController> optCtrlList = new LinkedList<>();
    private EditScaleController scaleCtrl;
    private int quesID = 1;

    public void setSurvCtrl(EditSurveyController survCtrl) {
        this.survCtrl = survCtrl;
    }

    public Queue<MultipleChoiceOptionController> getOptCtrlList() {
        return optCtrlList;
    }

    public EditScaleController getScaleCtrl() {
        return scaleCtrl;
    }

    public int getQuesID() {
        return quesID;
    }

    public void setQuesID(int quesID) {
        this.quesID = quesID;
    }

    public void addQues() throws IOException, IndexOutOfBoundsException {
        FXMLLoader newQues = new FXMLLoader(getClass().getResource("EditQuestion.fxml"));
        survCtrl.getQuesList().add(survCtrl.getQuesList().indexOf(rootPane) + 1, newQues.load());

        EditQuestionController newQuesCtrl = newQues.getController();
        newQuesCtrl.setSurvCtrl(survCtrl); // Pass this EditSurveyController instance onto EditQuestionController to allow it to access this addQuestion function
        LinkedList<Integer> quesIDList = new LinkedList<>();
        for (EditQuestionController quesCtrl : survCtrl.getQuesCtrlList()) {
            quesIDList.add(quesCtrl.getQuesID());
            System.out.println(quesIDList);
        }

//        int newQuesID = 1;
//        while (true) {
//            if (!quesIDList.contains(newQuesID)) break;
//            newQuesID++;
//        }
        for (int newQuesID = 1; quesIDList.contains(newQuesID); newQuesID++) {
            newQuesCtrl.setQuesID(newQuesID + 1);
        }
        survCtrl.getQuesCtrlList().add(survCtrl.getQuesCtrlList().indexOf(this) + 1, newQuesCtrl);
    }

    public void removeQues() {
        survCtrl.getQuesList().remove(rootPane);
        survCtrl.getQuesCtrlList().remove(this);
    }

    public void moveQuesUp() {
        System.out.println();
    }

    public void moveQuesDown() {
        System.out.println();
    }

    public String getQuesText() {
        return quesText.getText();
    }

    public void setQuesText(String newQuesText) {
        this.quesText.setText(newQuesText);
    }

    public String getQuesType() {
        return quesType.getValue();
    }

    public void setQuesType(String newQuesType) {
        this.quesType.setValue(newQuesType);
    }

    public String getIsRequired() {
        return isRequired.getText();
    }

    public void setIsRequired(String isRequired) {
        this.isRequired.setText(isRequired);
    }

    public void clearOptList() {
        optList.getChildren().clear();
        optCtrlList.clear();
    }

    public MultipleChoiceOptionController addOption() throws IOException {
        FXMLLoader newOpt = new FXMLLoader(getClass().getResource("MultipleChoiceOption.fxml"));
        optList.getChildren().add(newOpt.load());

        MultipleChoiceOptionController optCtrl = newOpt.getController();
        optCtrl.setQuesCtrl(this);
        optCtrlList.add(optCtrl);
        return optCtrl;
    }

    public void changeQuesType() throws IOException {
        switch(quesType.getValue()) {
            case "Multiple Choice":
                optList.getChildren().clear();
                addOption();
                break;
            case "Scale":
                FXMLLoader scale = new FXMLLoader(getClass().getResource("EditScale.fxml"));
                optList.getChildren().setAll((Node) scale.load());
                scaleCtrl = scale.getController();
                break;
            case "Polar": 
                optList.getChildren().setAll(new CheckBox());
                break;
            case "Open Ended": 
                optList.getChildren().setAll(new TextField());
                break;
        }
    }

    public void toggleRequired() {
        if (isRequired.isSelected()) {
            isRequired.setText("Required");
        } else {
            isRequired.setText("Optional");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        quesType.getItems().addAll("Multiple Choice", "Scale", "Polar", "Open Ended");
        quesType.setValue("Multiple Choice"); // This also triggers changeQuesType once
    }
}