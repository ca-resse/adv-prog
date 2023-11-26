import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class UserDetailsController implements Initializable {

    @FXML
    private Label CreatorFirstName;

    @FXML
    private Label CreatorID;

    @FXML
    private Label CreatorLastName;

    @FXML
    private Label Date_OB;

    @FXML
    private Label EmailAdress;

    @FXML
    private Label Faculty;

    @FXML
    private Label Gender;

    @FXML
    private Label PhoneNo;

    @FXML
    private Label Username;

    @FXML
   public void onClick_finish_btn(ActionEvent event) throws IOException  {
            
        App.setRoot("surveylist");
    }

    @FXML
    void switchToEditUserDetails(ActionEvent event) throws IOException {
       App.setRoot("edituser");
    }

   
 @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        System.out.println("start initialize");

        

        try (
        //loads the Json file
        FileReader fr = new FileReader("UserDetail.json")) {
            JSONObject userObject = (JSONObject) new JSONParser().parse(fr);
            JSONArray array = (JSONArray) userObject.get("userDetailsArray");
            
            //creats a for loop to go through the whole json file
            for (Object obj : array) {
            if((((JSONObject) obj).get("creatorID")).toString().equals(UserInfo.StoredSCiD)) {
            //gets the details from UserDetails.json file and then stores in the String variable
            String fname = (((JSONObject) obj).get("firstName")).toString();
            String lname = (((JSONObject) obj).get("lastName")).toString();
            String uname = (((JSONObject) obj).get("username")).toString();
            String ScID = (((JSONObject) obj).get("creatorID")).toString();
            String doB = (((JSONObject) obj).get("DOB")).toString();
            String fclty = (((JSONObject) obj).get("faculty")).toString();
            String gndr = (((JSONObject) obj).get("gender")).toString();
            String emaddress = (((JSONObject) obj).get("emailAddress")).toString();
            String phoneno = (((JSONObject) obj).get("phoneNo")).toString();
            System.out.println(fname + " " + lname + " " + uname );
   

            //sets the label text according to the details from UserDetails.json file
            CreatorFirstName.setText(fname);
            CreatorLastName.setText(lname);
            Username.setText(uname);
            CreatorID.setText(ScID);
            Date_OB.setText(doB);
            Faculty.setText(fclty);
            Gender.setText(gndr);
            EmailAdress.setText(emaddress);
            PhoneNo.setText(phoneno);
    
             }
            }

        }    
        catch (IOException | ParseException e) {
            e.printStackTrace();
        }

}
}

