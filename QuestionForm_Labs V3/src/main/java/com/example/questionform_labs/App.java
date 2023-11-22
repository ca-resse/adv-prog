package com.example.questionform_labs;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class App extends Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException, ParseException {
//        try (Reader reader = new FileReader("src/main/resources/JSON/Surveys/Survey_0001.json")) {
//            JSONObject data = (JSONObject) new JSONParser().parse(reader);
//            System.out.println(data.get("title"));
//        } catch (IOException | ParseException e) {
//            e.printStackTrace();
//        }

//        JSONArray arr = new JSONArray();
//        arr.put("test1");
//        arr.put("test2");
//        arr.put("test3");
//        arr.put(1, "test4");
//        System.out.println(arr);


        // Standard loading
//        Parent root = FXMLLoader.load(getClass().getResource("/FXML/EditSurvey.fxml"));
//        Scene scene = new Scene(root, 1000, 600);
        scene = new Scene(new Pane(), 1000, 600);

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

        new EditSurveyController(4);
    }

    public static void setRoot(Parent root) throws IOException {
//        FXMLLoader loader = new FXMLLoader(App.class.getResource("/FXML/" + fileName + ".fxml"));
        scene.setRoot(root);
    }

    static void test(String txt) {
        System.out.println(txt);
    }

    public static void main(String[] args) {
        launch();
    }
}