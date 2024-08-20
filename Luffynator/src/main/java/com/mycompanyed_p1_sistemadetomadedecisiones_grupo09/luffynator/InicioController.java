/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompanyed_p1_sistemadetomadedecisiones_grupo09.luffynator;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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
    private Circle cargarPregCheck;

    @FXML
    private Circle cargarRespCheck;
    
    @FXML
    private Button ComenzarButton;
    
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
        
        cargarPregCheck.setFill(Color.BLUE);
        cargarRespCheck.setFill(Color.BLUE);
        ComenzarButton.setDisable(false);
    }
    
    public void validarArchivoPreguntas() {
        try {
            List<String> lineasPreguntas = GameManager.readFile(GameManager.getInstance().getPreguntasFilePath());

            if (GameManager.getInstance().esFormatoValido(lineasPreguntas, null)) {
                cargarPregCheck.setFill(Color.GREEN);  // Verde si el archivo es válido
            } else {
                cargarPregCheck.setFill(Color.RED);    // Rojo si no es válido
            }
        } catch (IOException e) {
            cargarPregCheck.setFill(Color.RED);        // Rojo si hay un error al leer el archivo
        }
    }

    public void validarArchivoRespuestas() {
        try {
            List<String> lineasRespuestas = GameManager.readFile(GameManager.getInstance().getRespuestasFilePath());

            if (GameManager.getInstance().esFormatoValido(null, lineasRespuestas)) {
                cargarRespCheck.setFill(Color.GREEN);  // Verde si el archivo es válido
            } else {
                cargarRespCheck.setFill(Color.RED);    // Rojo si no es válido
            }
        } catch (IOException e) {
            cargarRespCheck.setFill(Color.RED);        // Rojo si hay un error al leer el archivo
        }
    }


    private void mostrarAlertaInfo(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION, mensaje, ButtonType.OK);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.showAndWait();
    }
    
    private void mostrarAlertaError(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR, mensaje, ButtonType.OK);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.showAndWait();
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
            validarArchivoPreguntas();
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
            validarArchivoRespuestas();
        }
    }


   
}
