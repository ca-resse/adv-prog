import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class EditUserErrorController {

    @FXML
    private Button ErrorBtn;

     @FXML
    void switchBackToEditUserDetails(ActionEvent event) throws IOException {
       App.setRoot("edituser");
    }

}
