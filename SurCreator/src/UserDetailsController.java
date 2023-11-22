import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class UserDetailsController {

    @FXML
    private TextField CreatorFirstName;

    @FXML
    private TextField CreatorLastName;

    @FXML
    private TextField EmailAdress;

    @FXML
    private TextField Faculty;

    @FXML
    private TextField PhoneNo;

    @FXML
    private TextField Username;

    @FXML
   public void onClick_finish_btn(ActionEvent event) throws IOException {
        //creating a Json object 
        JSONObject UserDetails = new JSONObject();
        UserDetails.put("Creator First Name", CreatorFirstName.getText());
        UserDetails.put("Creator Last Name", CreatorLastName.getText());
        UserDetails.put("Username",Username.getText());
        UserDetails.put("Faculty",Faculty.getText());
        UserDetails.put("Email Adress",EmailAdress.getText());
        UserDetails.put("Phone No", PhoneNo.getText());        

        //using filewriter to store the input inside userdetails.json
        FileWriter fw = new FileWriter("userdetails.json");
        fw.write(UserDetails.toString());
        fw.close();

        App.setRoot("surveylist");
    }

    @FXML
    void onClick_edituserdetails_btn(ActionEvent event) {
      
    }




    
    
    



}
