package com.group10;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 * Test commit
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

        // Collection<SurveyData> list = Files.readAllLines(new File("surveylist.txt").toPath()).stream().map(line -> {
        //     String[] details = line.split("\t");
        //     SurveyData sd = new SurveyData();
        //     sd.setSurveyID(details[0]);
        //     sd.setSurveyTitle(details[1]);
        //     sd.setSurveyDetails(details[2]);
        //     sd.setCreatorName(details[3]);
        //     return sd;
        // })
        // .collect(Collectors.toList());

        // ObservableList<SurveyData> details =FXCollections.observableArrayList(list);

        scene = new Scene(loadFXML("surveylist"), 640, 480);
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

    public static void main(String[] args) {
        launch();
    }

}