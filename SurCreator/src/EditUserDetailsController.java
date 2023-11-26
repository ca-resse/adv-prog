import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

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

       //these if and else if statements are used to check if the input inside the textfields are empty and to prevent any numbers being entered in first and last name or faculty
    
        if(isEmpty(NewCreatorFirstName) == true || isInt(newScFname) == true){
        System.out.println("error");
        App.setRoot("EditUserError2");//brings the user to a another fxml that shows the error 
        }
        else if(isEmpty(NewCreatorLastName) == true || isInt(newScFname) == true){
        System.out.println("error");
        App.setRoot("EditUserError2");
        }
        else if(isEmpty(NewEmail) == true){
        System.out.println("error");
        App.setRoot("IncompleteEditUserError");// this fxml file is the same as EditUserErro2.fxml except that it does not have the number entered error as Emails contain number.
        }
        else if( isEmpty(NewFaculty) == true || isInt(newScFname) == true){
        System.out.println("error");
        App.setRoot("EditUserError2");
        }
        else if(isEmpty(NewPhoneNo) == true){
        System.out.println("error");
        App.setRoot("IncompleteEditUserError");
        }
        else if(isEmpty(NewUsername) == true){
        System.out.println("error");
        App.setRoot("IncompleteEditUserError");
        }
        else{ //if the new details are valid, they are updated into the Json file and the User is brought back to the user details page.

        //load Json array
        FileReader fr = new FileReader("UserDetail.json");
        JSONObject userObject = (JSONObject) new JSONParser().parse(fr);
        JSONArray array = (JSONArray) userObject.get("userDetailsArray"); 

        for (Object obj : array) {
            if((((JSONObject) obj).get("creatorID")).toString().equals(UserInfo.StoredSCiD)) {
            (((JSONObject) obj)).put("firstName", newScFname);
            (((JSONObject) obj)).put("lastName", newScLname);
            (((JSONObject) obj)).put("username", newScUname);
            (((JSONObject) obj)).put("emailAddress", newemail);
            (((JSONObject) obj)).put("faculty", newfaculty);
            (((JSONObject) obj)).put("phoneNo", newphoneno);

        
        //overwrites JSON file with updated details
        FileWriter fw = new FileWriter("UserDetail.json");
        fw.write(userObject.toJSONString());
        fw.flush();
        fw.close();

        System.out.println("User Details has been updated");
    }

  }
  App.setRoot("userdetails"); //switches back to userdetails.fxml
  
}
        
}
       
    
//this method is used to check if the details entered are empty
public boolean isEmpty(TextField detail) throws IOException, ParseException {
    if (detail.getText().equalsIgnoreCase("")) {
        System.out.println("error");
        return true;
    }
    else {
        return false;
    }

    }

//this method is used to check if the details entered contain a number as first names, last names and faculty do not need number
public boolean isInt(String detail)
{
    try
    {
        Integer.parseInt(detail);
        return true;
    } catch (NumberFormatException ex)
    {
        return false;
    }
}
}


