import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class SurveyFormController{

    private static final ActionEvent ActionEvent = null;

    @FXML
    private Button addNewQuestion_btn;

    @FXML
    private Button create_btn;

    @FXML
    private Label creator_id;

    @FXML
    private Label creator_name;

    @FXML
    private Button logout_btn;

    @FXML
    private TextArea newSurveyDetails;

    @FXML
    private TextField newSurveyTitle;

    @FXML
    private ListView<?> questionListView;

    @FXML
    private Label survey_id;

    @FXML
    void onClick_create_btn(ActionEvent event) throws IOException, org.json.simple.parser.ParseException {


        JSONObject obj = new JSONObject();

        //creating a Json object 
        obj.put("Survey Title", newSurveyTitle.getText());
        obj.put("Survey Details", newSurveyDetails.getText());

        //using filewriter to store the input inside surveylist.json
        FileWriter fw = new FileWriter("surveylist.json");
        fw.write(obj.toString());
        fw.close();

        //creating a Json parser to read from surveylist.json file
        JSONParser jsonParse = new JSONParser();

        JSONObject jsonObject = (JSONObject) jsonParse.parse(new FileReader("surveylist.json"));
        
        String titlename = (String) jsonObject.get("Survey Title");

        SurveyListController sController = new SurveyListController();
        sController.addSurveytoList(titlename);


        /*BufferedReader reader = new BufferedReader(new FileReader("surveylist.json"));
        String Stitledata;
        while ((Stitledata = reader.readLine()) != null) {
            SurveyListController sController = new SurveyListController();
             sController.addSurveytoList(Stitledata);
        }
        reader.close();
       */
        App.setRoot("surveylist");
       

    }

  


    // method to return the survey_id from the last line in the text file
    //private int getLastSurveyID{

    //}

    

    @FXML
    public void onClick_logout_btn (ActionEvent e) throws IOException{
        App.setRoot("primary");
    }

    /*@Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
    }
   */ 
}


    //1) store all your inputs into a Json file
    //2) get the surveytitle and Id and add it to surveylist 
    //3) make sure the survey details and questions are stored in the surveylist inside survey title
    //4) create several surveylists with varying surveyIDs 
    //5) add search filter so you can find the survey you are looking for
    //6) edit the survey by accessing the same JSon file 
    //7) delete surveys 

