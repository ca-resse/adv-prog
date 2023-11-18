package com.group10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class SurveyListController {

    @FXML
    private Button createSurvey_btn;

    @FXML
    private TableColumn<Survey, String> creatorname_col;

    @FXML
    private Button logout_btn;

    @FXML
    private Button refresh_btn;

    @FXML
    private Button search_btn;

    @FXML
    private TextField search_field;

    @FXML
    private TableColumn<Survey, String> surveydetails_col;

    @FXML
    private TableColumn<Survey, Integer> surveyid_col;

    @FXML
    private AnchorPane surveylist_page;

    @FXML
    private TableView<Survey> surveylist_table;

    @FXML
    private TableColumn<Survey, String> surveytitle_col;

    @FXML
    void onClick_logout_btn(ActionEvent event) {

    }

    @FXML
    void switchToSurveyForm(ActionEvent event) throws IOException {
        App.setRoot("surveyform");
    }
    
     public void initialize(){
        //sets the reference value for each respective column
        surveyid_col.setCellValueFactory(new PropertyValueFactory<>("surveyID")); //must match surveyID variable from the Survey class 
        surveytitle_col.setCellValueFactory(new PropertyValueFactory<>("surveyTitle")); //must match surveyTitle variable from the Survey class
        surveydetails_col.setCellValueFactory(new PropertyValueFactory<>("surveyDetails")); //must match surveyDetails variable from the Survey class
        creatorname_col.setCellValueFactory(new PropertyValueFactory<>("creatorName")); //must match creatorName variable from the Survey class

        // Load data from file and populate the TableView
        try {
            List<Survey> surveylist = readDataFromFile("surveylist.txt");
            surveylist_table.getItems().addAll(surveylist);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     private List<Survey> readDataFromFile(String filePath) throws IOException {
        List<Survey> surveylist = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Line: " + line);
                String[] parts = line.split("\\t");
                System.out.println("Parts length= " + parts.length);
                if (parts.length >= 4) {
                    String surveyid = parts[0].trim();
                    String surveytitle = parts[1].trim();
                    String surveydetails = parts[2].trim();
                    String creatorname = parts[3].trim();
                    surveylist.add(new Survey(Integer.parseInt(surveyid), surveytitle, creatorname, surveydetails));
                } else {
                    System.out.println("Index Out of bounds.");
                    for (int i = 0; i < parts.length; i++) {
                    String item = parts[i].trim();
                    System.out.println("item[" + i + "] = " + item);
                    }
                }
            }
        }

        return surveylist;
    }
}
