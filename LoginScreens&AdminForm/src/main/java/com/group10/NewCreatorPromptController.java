package com.group10;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class NewCreatorPromptController {

    @FXML
    private AnchorPane rootPane;

    @FXML
    void Logout(ActionEvent event) throws IOException {
        App.setRoot("HomePage");
    }

    @FXML
    void ReturnToManageUsers(ActionEvent event) throws IOException {
        App.setRoot("ManageUsersList");
    }

}