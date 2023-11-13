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
    private Button search_btn;

    @FXML
    private Button refresh_btn;

    @FXML
    private TextField search_field;

    @FXML
    private TableColumn<Survey, Integer> surveyid_col;

    @FXML
    private AnchorPane surveylist_page;

    @FXML
    private TableView<Survey> surveylist_table;

    @FXML
    private TableColumn<Survey, String> surveytitle_col;

    @FXML
    private TableColumn<Survey, String> surveydetails_col;
    
    @FXML
    private void switchToSurveyForm(ActionEvent e) throws IOException {
        App.setRoot("surveyform");
    }

    @FXML
    public void onClick_logout_btn (ActionEvent e) throws IOException{
        App.setRoot("primary");
    }

    // @FXML
    // public void onClick_refresh_btn (ActionEvent e) throws IOException{
    //     Collection<SurveyData> list = Files.readAllLines(new File("surveylist.txt").toPath()).stream().map(line -> {
    //         String[] details = line.split("\t");
    //         SurveyData sd = new SurveyData();
    //         sd.setSurveyID(details[0]);
    //         sd.setSurveyTitle(details[1]);
    //         sd.setSurveyDetails(details[2]);
    //         sd.setCreatorName(details[3]);
    //         return sd;
    //     })
    //     .collect(Collectors.toList());

    //     ObservableList<SurveyData> details =FXCollections.observableArrayList(list);

    //     // TableView<SurveyData> surveylist_table = new TableView<>();
    //     // TableColumn<SurveyData, String> surveyid_col = new TableColumn<>();
    //     // TableColumn<SurveyData, String> surveytitle_col = new TableColumn<>();
    //     // TableColumn<SurveyData, String> surveydetails_col = new TableColumn<>();
    //     // TableColumn<SurveyData, String> creatorname_col = new TableColumn<>();

    //     // surveylist_table.getColumns().addAll(surveyid_col, surveytitle_col, surveydetails_col, creatorname_col);

    //     surveyid_col.setCellValueFactory(data -> data.getValue().surveyIDProperty());
    //     surveytitle_col.setCellValueFactory(data -> data.getValue().surveyTitleProperty());
    //     surveydetails_col.setCellValueFactory(data -> data.getValue().surveyDetailsProperty());
    //     creatorname_col.setCellValueFactory(data -> data.getValue().creatorNameProperty());

    //     surveylist_table.setItems(details);
    // }
    
    public void initialize(){
        surveyid_col.setCellValueFactory(new PropertyValueFactory<>("surveyID"));
        surveytitle_col.setCellValueFactory(new PropertyValueFactory<>("surveyTitle"));
        surveydetails_col.setCellValueFactory(new PropertyValueFactory<>("surveyDetails"));
        creatorname_col.setCellValueFactory(new PropertyValueFactory<>("creatorName"));

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
                    //System.out.println("item[0] = " + item0 + "\nitem[1] = " + item1 + "\nitem[2] = " + item2 + "\n");
                }
            }
        }

        return surveylist;
    }
    
}
