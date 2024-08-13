/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompanyed_p1_sistemadetomadedecisiones_grupo09.luffynator;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * FXML Controller class
 *
 * @author asala
 */
public class OpcionesJuegoController implements Initializable {

    @FXML 
    public void play(String fileName){
        MediaPlayerManager.getInstance().play(fileName);
    }
    
     @FXML
    public void volver() throws IOException{
        App.setRoot("inicio");
    }
    
    @FXML
    public void jugar(){
        System.out.println("jugar");
        // Lógica para cambiar de escena o realizar otra acción
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        play("music/おれの最高地点.mp3");
    }    
}
