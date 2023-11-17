package com.example.questionform_labs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ResourceBundle;

public class EditQuestionController implements Initializable {
    @FXML
    private TextArea quesText;
    @FXML
    private ChoiceBox<String> quesType;
    @FXML
    private ToggleButton isRequired;
    @FXML
    private VBox optList;
    private EditSurveyController survCtrl;
    Queue<MultipleChoiceOptionController> optCtrlList = new LinkedList<>();

    public void setSurvCtrl(EditSurveyController survCtrl) {
        this.survCtrl = survCtrl;
    }

    public void addBtn() throws IOException {
        survCtrl.addQuestion();
    }

    public void removeBtn() {

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

    public void setIsRequired(String newIsRequired) {
        this.isRequired.setText(newIsRequired);
    }

    public void clearOptList() {
        optList.getChildren().clear();
        optCtrlList.clear();
    }

    public MultipleChoiceOptionController addOption() throws IOException {
        FXMLLoader newOpt = new FXMLLoader(getClass().getResource("/FXML/MultipleChoiceOption.fxml"));
        optList.getChildren().add(newOpt.load());

        MultipleChoiceOptionController optCtrl = newOpt.getController();
        optCtrl.setQuesCtrl(this);
        optCtrlList.add(optCtrl);
        return optCtrl;
    }

    public void changeQuesType() throws IOException {
        switch(quesType.getValue()) {
            case "Multiple Choice" -> {
                optList.getChildren().clear();
                addOption();
            }
            case "Scale" -> {
                Pane scale = FXMLLoader.load(getClass().getResource("/FXML/ScaleQuestion.fxml"));
                optList.getChildren().setAll(scale);
            }
            case "Polar" -> optList.getChildren().setAll(new CheckBox());
            case "Open Ended" -> optList.getChildren().setAll(new TextField());
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