package com.group10;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MultipleChoiceOptionController {
    @FXML
    private TextField optText;
    private EditQuestionController quesCtrl;

    public void setQuesCtrl(EditQuestionController quesCtrl) {
        this.quesCtrl = quesCtrl;
    }

    public String getOptText() {
        return optText.getText();
    }

    public void setOptText(String newOptText) {
        this.optText.setText(newOptText);
    }

    public void addBtn() throws IOException {
        quesCtrl.addOption();
    }
}