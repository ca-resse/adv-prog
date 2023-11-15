package com.example.questionform_labs;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.ResourceBundle;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
//        try (Reader reader = new FileReader("src/main/resources/JSON/Surveys/Survey_0001.json")) {
//            JSONObject data = (JSONObject) new JSONParser().parse(reader);
//            System.out.println(data.get("title"));
//        } catch (IOException | ParseException e) {
//            e.printStackTrace();
//        }


        // Standard-ass loading
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/EditSurvey.fxml"));
        Scene scene = new Scene(root, 1000, 600);

        // Load CSS
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        // Press "Esc" to close
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                Platform.exit();
            }
        });

        stage.setTitle("QuestionForm Labs");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}