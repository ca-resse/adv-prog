import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class SurveyListController implements Initializable{

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

    @FXML TableView<Survey> surveylist_table;

    @FXML
    private TableColumn<Survey, String> surveyTitle;

    ObservableList<Survey> initialData() {
        Survey s1 = new Survey("test Survey");
        return FXCollections.observableArrayList(s1);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        surveyTitle.setCellValueFactory(new PropertyValueFactory<Survey, String>("surveyTitle"));

        surveylist_table.setItems(initialData());


    }
    

    public void addSurveytoList(String titlename) throws IOException {
    
        
            Survey newData = new Survey(titlename);
            surveylist_table.getItems().add(newData);
        
    }

    public void testprint(String word) {
           System.out.println(word);
    }
    

    @FXML
    void switchToSurveyForm(ActionEvent event) throws IOException {
        App.setRoot("surveyform");
    }

    @FXML
    public void onClick_logout_btn (ActionEvent e) throws IOException{
        App.setRoot("primary");
    }

  
   

    
}