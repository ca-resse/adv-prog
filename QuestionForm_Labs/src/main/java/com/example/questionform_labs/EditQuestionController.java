package com.example.questionform_labs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditQuestionController implements Initializable {
    @FXML
    private ChoiceBox<String> questionType;
    @FXML
    private VBox optionsList;
    private EditSurveyController survCtrl;

    public void setSurvCtrl(EditSurveyController survCtrl) {
        this.survCtrl = survCtrl;
    }

    public void addBtn() throws IOException {
        survCtrl.addQuestion();
    }

    public void removeBtn() {

    }

    public void changeQuestionType(ActionEvent event) throws IOException {
        switch(questionType.getValue()) {
            case "Multiple Choice" -> {
                optionsList.getChildren().clear();
                addOption();
            }
            case "Polar" -> optionsList.getChildren().setAll(new CheckBox());
            case "Scale" -> {
                Pane scale = FXMLLoader.load(getClass().getResource("/FXML/ScaleQuestion.fxml"));
                optionsList.getChildren().setAll(scale);
            }
            case "Open Ended" -> optionsList.getChildren().setAll(new TextField());
        }
    }

    public void requiredBtn(ActionEvent event) {
        ToggleButton btn = ((ToggleButton) event.getTarget());
        if (btn.isSelected()) {
            btn.setText("Required");
        } else {
            btn.setText("Optional");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        questionType.getItems().addAll("Multiple Choice", "Polar", "Scale", "Open Ended");
        questionType.setValue("Multiple Choice");
//        try {
//            addOption();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    public void addOption() throws IOException {
        FXMLLoader newOption = new FXMLLoader(getClass().getResource("/FXML/MultipleChoiceOption.fxml"));
        optionsList.getChildren().add(newOption.load());
        MultipleChoiceOptionController multiCtrl = newOption.getController();
        multiCtrl.setQuesCtrl(this);
    }
}