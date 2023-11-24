package com.group10;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class App extends Application {

    // //"start" function used for launcing the application
    // @Override
    // public void start(Stage primaryStage) {
  
    //     Parent root;
    //     try {
    //         root = FXMLLoader.load(getClass().getResource("src/main/resources/com/group10/HomePage.fxml"));
    //         Scene scene = new Scene(root);
    //         primaryStage.setScene(scene);
    //         primaryStage.show();
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }

    // public static void main(String[] args) {
    //     launch();
    // }

    // ABOVE IS DEFUNCT CODE, USED TO BE WORKING BUT NOW DOES NOT

    private static Scene scene;

    // "start" method used to launch the application on the homepages
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("HomePage"));
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}