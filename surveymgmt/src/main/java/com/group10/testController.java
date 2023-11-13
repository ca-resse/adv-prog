package com.group10;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class testController {

    @FXML
    private Button test_btn;

    @FXML
    public void onClick_test_btn (ActionEvent e) throws IOException{
        App.setRoot("surveyform");
    }
    
}
