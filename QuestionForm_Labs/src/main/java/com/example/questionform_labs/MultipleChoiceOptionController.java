package com.example.questionform_labs;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MultipleChoiceOptionController {
    @FXML
    private AnchorPane rootPane;
    private EditQuestionController quesCtrl;

    public void setQuesCtrl(EditQuestionController quesCtrl) {
        this.quesCtrl = quesCtrl;
    }

    public void addBtn() throws IOException {
        quesCtrl.addOption();
    }
}