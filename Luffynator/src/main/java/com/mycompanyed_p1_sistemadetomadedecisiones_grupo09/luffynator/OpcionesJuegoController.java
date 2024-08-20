/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompanyed_p1_sistemadetomadedecisiones_grupo09.luffynator;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 * FXML Controller class
 *
 * @author asala
 */
public class OpcionesJuegoController implements Initializable {
    
    @FXML
    private ImageView luffyAmazed;
    
      
    @FXML
    private TextField numPreguntasTF; 
    
    @FXML 
    public void play(String fileName){
        MediaPlayerManager.getInstance().play(fileName);
    }
    
     @FXML
    public void volver() throws IOException{
        App.setRoot("inicio");
    }
    
    @FXML
    public void jugar() throws IOException {
        String numPreguntasText = numPreguntasTF.getText();
        int numPreguntas;
        try {
            numPreguntas = Integer.parseInt(numPreguntasText);
            if (numPreguntas < 0 || numPreguntas > 20) {
                throw new NumberFormatException("El número de preguntas debe estar entre 0 y 20.");
            }
        } catch (NumberFormatException e) {
            mostrarAlertaError("Número de Preguntas Inválido", "Por favor, ingrese un número de preguntas entre 0 y 20.");
            return;
        }

        // Configura el número de preguntas en GameManager
        GameManager.getInstance().setNumPreguntas(numPreguntas);

       // Cargar los datos del juego
       GameManager.getInstance().loadGameData("src/main/java/archivos/ArchivoPreguntas.csv", "src/main/java/archivos/ArchivoRespuestas.csv");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("loadingScreen.fxml"));
        Parent loadingRoot = loader.load();
        Scene currentScene = App.getPrimaryStage().getScene();
        currentScene.setRoot(loadingRoot);

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(2000);
                return null;
            }

            @Override
            protected void succeeded() {
                try {
                    App.setRoot("preguntas");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        new Thread(task).start();
    }
    
    private void mostrarAlertaError(String titulo, String mensaje) {
        Alert alerta = new Alert(AlertType.ERROR, mensaje, ButtonType.OK);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.showAndWait();
    }
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        play("music/おれの最高地点.mp3");
                
        Image image = new Image(getClass().getResourceAsStream("/imagenes/luffy amazed.png"));
        luffyAmazed.setImage(image);
        
    }    
}
