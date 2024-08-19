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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 * FXML Controller class
 *
 * @author asala
 */
public class InicioController implements Initializable {
    
    @FXML
    private ImageView luffyInicio;
    
    @FXML 
    public void play(String fileName){
        MediaPlayerManager.getInstance().play(fileName);
    }

    @FXML
    public void comenzar() throws IOException{
        System.out.println("Comenzando....");
        App.setRoot("opcionesJuego");
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        play("music/One Piece OST - Nakama no Shirushi da! Sign Of Friendship.mp3");
        
                // Carga la imagen desde los recursos
        Image image = new Image(getClass().getResourceAsStream("/imagenes/Inicio Luffy.png"));
        luffyInicio.setImage(image);
        

    }
   
}
