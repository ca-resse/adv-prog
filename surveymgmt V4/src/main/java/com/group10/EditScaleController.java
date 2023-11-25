package com.group10;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import java.net.URL;
import java.util.ResourceBundle;

public class EditScaleController implements Initializable {
    @FXML
    private HBox optScale;
    @FXML
    private TextField optMin;
    @FXML
    private TextField optMax;
    @FXML
    private ChoiceBox divisions;
    int divs;

    public String getOptMin() {
        return optMin.getText();
    }

    public void setOptMin(String optMin) {
        this.optMin.setText(optMin);
    }

    public String getOptMax() {
        return optMax.getText();
    }

    public void setOptMax(String optMax) {
        this.optMax.setText(optMax);
    }

    public int getDivisions() {
        return (int) divisions.getValue();
    }

    public void setDivisions(int divisions) {
        this.divisions.setValue(divisions);
    }

    public void divideScale() {
        divs = (int) divisions.getValue();
        optScale.getChildren().clear();
        ToggleGroup grp = new ToggleGroup();

        for (int i = 0; i <= divs; i++) {
            Region reg = new Region();
            reg.setPrefWidth(200);
            optScale.getChildren().add(reg);

            if (i == divs) continue;

            RadioButton btn = new RadioButton();
            btn.setToggleGroup(grp);
            optScale.getChildren().add(btn);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        divisions.getItems().addAll(2, 3, 4, 5, 6, 7, 8, 9, 10);
        divisions.setValue(5); // This also triggers changeQuesType once
    }

}