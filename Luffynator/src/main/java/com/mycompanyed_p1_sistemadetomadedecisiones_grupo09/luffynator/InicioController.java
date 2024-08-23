/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompanyed_p1_sistemadetomadedecisiones_grupo09.luffynator;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author asala
 */
public class InicioController implements Initializable {
    
    @FXML
    private ImageView luffyInicio;
    @FXML
    private ImageView gomuGomuNomi;
        
    @FXML 
    public void play(String fileName){
        MediaPlayerManager.getInstance().play(fileName);
    }

    @FXML
    public void comenzar() throws IOException{
        System.out.println("Comenzando....");
        App.setRoot("opcionesJuego");
    }

    @FXML
    public void configuracion() throws IOException{
        System.out.println("Comenzando....");
        App.setRoot("configuracion");
    }
    

    private void iniciarAnimacion() {
        // Configurar las posiciones iniciales de los ImageView
        gomuGomuNomi.setY(0);
        double height = gomuGomuNomi.getImage().getHeight();

        // Crear una animación simple usando un bucle de animación
        AnimationTimer animationTimer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (lastUpdate != 0) {
                    // Calcular el tiempo transcurrido en segundos desde la última actualización
                    double elapsedTime = (now - lastUpdate) / 1_000_000_000.0;

                    // Velocidad de la animación
                    double speed = 500; // pixeles por segundo

                    // Actualizar la posición de la imagen
                    gomuGomuNomi.setY(gomuGomuNomi.getY() + speed * elapsedTime);

                    // Si la imagen sale de la pantalla por la parte inferior, la reinicia desde arriba
                    if (gomuGomuNomi.getY() > height) {
                        gomuGomuNomi.setY(-height);
                    }
                }
                lastUpdate = now;
            }
        };

        animationTimer.start();
    }

        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        play("music/One Piece OST - Nakama no Shirushi da! Sign Of Friendship.mp3");
        
                // Carga la imagen desde los recursos
        Image image = new Image(getClass().getResourceAsStream("/imagenes/InicioLuffy.png"));
        luffyInicio.setImage(image);
        String preguntasFilePath = GameManager.getInstance().getPreguntasFilePath();
        String respuestasFilePath = GameManager.getInstance().getRespuestasFilePath();
        
        
        Image image2 = new Image(getClass().getResourceAsStream("/imagenes/gomuGomuAkumaNomi.png"));
        gomuGomuNomi.setImage(image2);
        
        iniciarAnimacion();

        try {
            GameManager.getInstance().loadGameData(preguntasFilePath, respuestasFilePath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }
    
        
    @FXML
    private void cambiarArchivoPreguntas() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        Stage stage = App.getPrimaryStage(); // Asegúrate de tener acceso al primaryStage
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            String currentPreguntasFilePath = selectedFile.getAbsolutePath();
            System.out.println("Archivo de preguntas seleccionado: " + currentPreguntasFilePath);

            // Actualiza el archivo de preguntas en GameManager
            GameManager.getInstance().loadGameData(currentPreguntasFilePath, GameManager.getInstance().getRespuestasFilePath());
        }
    }

    @FXML
    private void cambiarArchivoRespuestas() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        Stage stage = App.getPrimaryStage(); // Asegúrate de tener acceso al primaryStage
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            String currentRespuestasFilePath = selectedFile.getAbsolutePath();
            System.out.println("Archivo de respuestas seleccionado: " + currentRespuestasFilePath);

            // Actualiza el archivo de respuestas en GameManager
            GameManager.getInstance().loadGameData(GameManager.getInstance().getPreguntasFilePath(), currentRespuestasFilePath);
        }
    }


   
}
