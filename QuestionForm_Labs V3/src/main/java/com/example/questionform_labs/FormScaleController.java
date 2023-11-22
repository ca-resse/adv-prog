package com.example.questionform_labs;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import java.net.URL;
import java.util.ResourceBundle;

public class FormScaleController {
    @FXML
    private HBox optScale;
    @FXML
    private Label optMin;
    @FXML
    private Label optMax;
    ToggleGroup grp;

    public void initScale(int divs, String min, String max) {
        optMin.setText(min);
        optMax.setText(max);

        grp = new ToggleGroup();
        for (int i = 0; i <= divs; i++) {
            Region reg = new Region();
            reg.setPrefWidth(200);
            optScale.getChildren().add(reg);

            if (i == divs) continue;

            RadioButton btn = new RadioButton();
            btn.setText(Integer.toString(i + 1));
            btn.setToggleGroup(grp);
            optScale.getChildren().add(btn);
        }
    }
}