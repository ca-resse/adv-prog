package com.group10;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class FormQuestionController {
    @FXML
    private ChoiceBox<String> quesType;
    @FXML
    private Label quesText;
    @FXML
    private VBox optList;
    private JSONObject quesJSON;
    private Control answerField;
    private int quesID = 0;

    public int getQuesID() {
        return quesID;
    }

    ToggleGroup grp;
    CheckBox chk;
    TextField txt;
    FormScaleController scaleCtrl;
    public void initQues(JSONObject quesJSON) throws IOException {
        this.quesJSON = quesJSON;
        quesID = quesJSON.getInt("questionID");
        quesText.setText(quesJSON.getString("questionText"));

        switch(quesJSON.getString("questionType")) {
            case "Multiple Choice": 
                grp = new ToggleGroup();
                for (Object opt : (JSONArray) quesJSON.get("answerOptions")) {
                    RadioButton btn = new RadioButton();
                    btn.setToggleGroup(grp);

                    if (opt.toString().equals("Other")) {
                        HBox box =  new HBox();
                        box.setSpacing(5);

                        TextField txt = new TextField();
                        txt.setPromptText("Other");
                        txt.setPrefWidth(600);

                        box.getChildren().addAll(btn, txt);
                        optList.getChildren().add(box);
                    } else {
                        btn.setText(opt.toString());
                        optList.getChildren().add(btn);
                    }
                }
            
            case "Scale":
                FXMLLoader scale = new FXMLLoader(getClass().getResource("/FXML/FormScale.fxml"));
                optList.getChildren().setAll((Node) scale.load());

                scaleCtrl = scale.getController();
                int divs = quesJSON.getInt("optionDivisions");
                String min = quesJSON.getString("optionMin");
                String max = quesJSON.getString("optionMax");
                scaleCtrl.initScale(divs, min, max);
            
            case "Polar":
                chk = new CheckBox();
                optList.getChildren().add(chk);
            
            case "Open Ended":
                txt = new TextField();
                txt.setPromptText("Enter response");
                optList.getChildren().add(txt);
            
        }
    }

    public String getAnswer() {
        String answer = "";
        switch(quesJSON.get("questionType").toString()) {
            case "Multiple Choice":
                if (grp.getSelectedToggle() != null)
                    answer = ((RadioButton) grp.getSelectedToggle()).getText();
                else answer = "";
            
            case "Scale":
                if (scaleCtrl.grp.getSelectedToggle() != null)
                    answer = ((RadioButton) scaleCtrl.grp.getSelectedToggle()).getText();
                else answer = "";
            
            case "Polar": 
                answer = String.valueOf(chk.isSelected());

            case "Open Ended":
                answer = txt.getText();
        }
        return answer;
    }
}