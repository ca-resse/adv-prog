package com.example.questionform_labs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ScaleQuestionController implements Initializable {
    @FXML
    private HBox optionScale;
    @FXML
    private TextField divisions;

    public void scaleDivisions() {
        optionScale.getChildren().clear();
        ToggleGroup grp = new ToggleGroup();
        for (int i = 0; i < Integer.parseInt(divisions.getText()); i++) {
            RadioButton btn = new RadioButton();
            btn.setToggleGroup(grp);
            optionScale.getChildren().add(btn);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        scaleDivisions();
    }

}