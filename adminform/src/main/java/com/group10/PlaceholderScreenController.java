package com.group10;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class PlaceholderScreenController {

    @FXML
    private AnchorPane rootPane;

    @FXML
    void ReturnToSurveyList(ActionEvent event) throws IOException {
        App.setRoot("AdminViewSurveyList");
    }

}