import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class EditUserDetailsController {

    @FXML
    private Label CreatorID;

    @FXML
    private Label Date_OB;

    @FXML
    private Label Gender;

    @FXML
    private TextField NewCreatorFirstName;

    @FXML
    private TextField NewCreatorLastName;

    @FXML
    private TextField NewEmail;

    @FXML
    private TextField NewFaculty;

    @FXML
    private TextField NewPhoneNo;

    @FXML
    private TextField NewUsername;

    @FXML
    void switchBackToUserDetails(ActionEvent event) throws IOException, ParseException {
        String newScFname = NewCreatorFirstName.getText().toString();
        String newScLname = NewCreatorLastName.getText().toString();
        String newemail = NewEmail.getText().toString();
        String newfaculty = NewFaculty.getText().toString();
        String newphoneno = NewPhoneNo.getText().toString();
        String newScUname = NewUsername.getText().toString();

        //load Json array
        //JSONParser jsonParser = new JSONParser();
        FileReader fr = new FileReader("UserDetails.json");
        JSONObject userObject = (JSONObject) new JSONParser().parse(fr);
        JSONArray array = (JSONArray) userObject.get("userDetailsArray"); 

        for (Object obj : array) {
        //JSONObject userObject = (JSONObject)obj;
       // Object obj = jsonParser.parse(fr);
        //JSONObject userObject = (JSONObject)obj;
        (((JSONObject) obj)).put("firstName", newScFname + "\n");
        (((JSONObject) obj)).put("lastName", newScLname + "\n");
        (((JSONObject) obj)).put("username", newScUname + "\n");
        (((JSONObject) obj)).put("emailAddress", newemail + "\n");
        (((JSONObject) obj)).put("faculty", newfaculty + "\n");
        (((JSONObject) obj)).put("phoneNo", newphoneno + "\n");

        
        //overwrites JSON file with updated details
        FileWriter fw = new FileWriter("UserDetails.json");
        fw.write(userObject.toJSONString());
        fw.flush();
        fw.close();

        System.out.println("User Details has been updated");
    }

        App.setRoot("userdetails");

    }

}

