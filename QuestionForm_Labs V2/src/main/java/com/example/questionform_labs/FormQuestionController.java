package com.example.questionform_labs;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class FormQuestionController {
    @FXML
    private ChoiceBox<String> quesType;
    @FXML
    private Label quesText;
    @FXML
    private VBox optList;

    public void initQues(JSONObject quesJSON) {
        quesText.setText(quesJSON.get("questionText").toString());

        switch(quesJSON.get("questionType").toString()) {
            case "Multiple Choice" -> {
                ToggleGroup grp = new ToggleGroup();
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
            }
            case "Scale" -> {
//                Pane scale = FXMLLoader.load(getClass().getResource("/FXML/ScaleQuestion.fxml"));
//                optionsList.getChildren().setAll(scale);
            }
            case "Polar" -> optList.getChildren().add(new CheckBox());
            case "Open Ended" -> {
                TextField txt = new TextField();
                txt.setPromptText("Enter response");
                optList.getChildren().add(txt);
            }
        }
    }

}