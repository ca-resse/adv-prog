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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class UserDetailsController {

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

  

    public void initialize(UserInfo userinfo) {
        System.out.println("start initialize");
        System.out.println(userinfo.getCreatorfname());
        CreatorFirstName.setText("Hello" + userinfo.getCreatorfname());
        CreatorLastName.setText(userinfo.getCreatorlname());
        Username.setText(userinfo.getCreatorUname());
        CreatorID.setText(userinfo.getCreatoriD());
        Date_OB.setText(userinfo.getDateOB());
        Faculty.setText(userinfo.getfaclty());
        Gender.setText(userinfo.getgenDer());
        EmailAdress.setText(userinfo.getemaldress());
        PhoneNo.setText(userinfo.getphonNo());
        
    }

    public void LoadDetails() throws IOException, ParseException {
        //load Json array
        //JSONParser jsonParser = new JSONParser();
        FileReader fr = new FileReader("UserDetails.json");
        JSONObject userObject = (JSONObject) new JSONParser().parse(fr);
        JSONArray array = (JSONArray) userObject.get("userDetailsArray"); 

        for (Object obj : array) {
        //String Suname = (((JSONObject) obj).get("username")).toString();
        //if (Suname  == ) {
            System.out.println("Username is the same");

        
                String fname = (((JSONObject) obj).get("firstName")).toString();
                String lname = (((JSONObject) obj).get("lastName")).toString();
                String uname = (((JSONObject) obj).get("username")).toString();
                String ScID = (((JSONObject) obj).get("creatorID")).toString();
                String doB = (((JSONObject) obj).get("DOB")).toString();
                String fclty = (((JSONObject) obj).get("faculty")).toString();
                String gndr = (((JSONObject) obj).get("gender")).toString();
                String emaddress = (((JSONObject) obj).get("emailAddress")).toString();
                String phoneno = (((JSONObject) obj).get("phoneNo")).toString();

                UserInfo userinfo = new UserInfo(fname, lname, uname, ScID, doB, fclty, gndr, emaddress, phoneno);
                System.out.println(fname);

                System.out.println("before initialize");
                initialize(userinfo);
               // CreatorFirstName.setText(fname);
               /*   CreatorLastName.setText(userinfo.getCreatorlname());
                Username.setText(userinfo.getCreatorUname());
                CreatorID.setText(userinfo.getCreatoriD());
                Date_OB.setText(userinfo.getDateOB());
                Faculty.setText(userinfo.getfaclty());
                Gender.setText(userinfo.getgenDer());
                EmailAdress.setText(userinfo.getemaldress());
                PhoneNo.setText(userinfo.getphonNo());
                */

               // }  else {
                //  System.out.println("LoadDetails failed");
                //}
        }
    }


                

    

    
       
    

    

    @FXML
    void onClick_edituserdetails_btn(ActionEvent event) {
        /*//Update the JSON data with modified survey
            for (Object obj : array) {
                JSONObject survObject = (JSONObject) obj;
                survObject.put("firstName", );
                    
                    break;
                }
            }
            /* 
            //Rewrite JSON file with updated data
            try (FileWriter fw = new FileWriter(filePath)) {
                fw.write(array.toJSONString());
                System.out.println("surveylist.json updated successfully.");
            }
        } catch (IOException|ParseException e){
            e.printStackTrace();
        } 
    }
    */

}

 

}
