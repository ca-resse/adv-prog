package com.group10;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;

    @Override
    //  Creates a new instance of a stage
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("AdminViewSurveyList"));
        stage.setScene(scene);
        stage.show();
    }

    // Loads the scene
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    // Creates a new instance of Object FXMLLoader to load the resources
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}