package com.group10;

import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * JavaFX App
 * Test commit
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

        scene = new Scene(loadFXML("surveylist"), 1000, 600);
        //scene = new Scene(loadFXML("studentsurveylist"), 640, 480);

        // Load CSS
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        // Press "Esc" to close
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                Platform.exit();
            }
        });

        stage.setScene(scene);
        stage.setTitle("Taylor's Survey Management System");
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    static Scene getScene() {
        return scene;
    }

    public static void main(String[] args) {
        launch();
    }
    
}