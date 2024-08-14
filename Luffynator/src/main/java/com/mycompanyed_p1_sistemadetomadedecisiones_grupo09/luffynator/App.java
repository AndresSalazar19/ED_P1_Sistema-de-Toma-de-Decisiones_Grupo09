package com.mycompanyed_p1_sistemadetomadedecisiones_grupo09.luffynator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Stage primaryStage; // Variable estática para el Stage principal



    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage; // Asignar el Stage principal a la variable estática
        scene = new Scene(loadFXML("inicio"), 800, 600);
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

    public static Stage getPrimaryStage() {
        return primaryStage; // Método para obtener el Stage principal
    }
        
    public static void main(String[] args) {
        launch();
    }

}