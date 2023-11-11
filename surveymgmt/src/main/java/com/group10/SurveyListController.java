package com.group10;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class SurveyListController {
    
    @FXML
    private TableColumn<?, ?> creatorname_col;

    @FXML
    private Button logout_btn;

    @FXML
    private Button search_btn;

    @FXML
    private TextField search_field;

    @FXML
    private TableColumn<?, ?> surveyid_col;

    @FXML
    private TableView<?> surveylist_table;

    @FXML
    private TableColumn<?, ?> surveytitle_col;
    
}
