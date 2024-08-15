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
    public void play(String fileName){
        MediaPlayerManager.getInstance().play(fileName);
    }
    
     @FXML
    public void volver() throws IOException{
        App.setRoot("inicio");
    }
    
    @FXML
    public void jugar() throws IOException {
        // Cargar la pantalla de carga
        FXMLLoader loader = new FXMLLoader(getClass().getResource("loadingScreen.fxml"));
        Parent loadingRoot = loader.load();

        // Obtener la escena actual
        Scene currentScene = App.getPrimaryStage().getScene(); // Asumiendo que puedes seguir usando getPrimaryStage
        currentScene.setRoot(loadingRoot);

        // Simula una tarea en segundo plano antes de cambiar la escena principal
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // Aquí puedes realizar tareas en segundo plano
                Thread.sleep(2000); // Simula un tiempo de carga
                return null;
            }

            @Override
            protected void succeeded() {
                try {
                    // Cambia a la escena principal después de la pantalla de carga
                    App.setRoot("preguntas");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        // Inicia la tarea en un nuevo hilo
        new Thread(task).start();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        play("music/おれの最高地点.mp3");
                
        Image image = new Image(getClass().getResourceAsStream("/imagenes/luffy amazed.png"));
        luffyAmazed.setImage(image);
        
    }    
}
