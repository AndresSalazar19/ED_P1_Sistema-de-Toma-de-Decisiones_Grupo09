/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompanyed_p1_sistemadetomadedecisiones_grupo09.luffynator;

import java.io.File;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import tda.CircularDoublyLinkedList;
import static tda.CircularDoublyLinkedList.obtenerListaAnimales;
import tda.DecisionTree;
import tda.Node;
import tda.NodeDecisionTree;


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
    private ImageView animalImageView;
    
    @FXML
    private VBox animalImageBox;
    
    @FXML
    private Label preguntaLabel;
    @FXML
    private Label animalLabel;

    @FXML
    private Button prevButton;
    @FXML
    
    private Button nextButton;
    private DecisionTree decisionTree;
    private NodeDecisionTree currentNode;
    private Node currentAnimalNode;
    private CircularDoublyLinkedList animales;

    
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

        // Validar el número de preguntas ingresado por el usuario
        try {
            numPreguntas = Integer.parseInt(numPreguntasText);
            if (numPreguntas < 0 || numPreguntas > 20) {
                throw new NumberFormatException("El número de preguntas debe estar entre 0 y 20.");
            }
        } catch (NumberFormatException e) {
            mostrarAlertaError("Número de Preguntas Inválido", "Por favor, ingrese un número de preguntas entre 0 y 20.");
            return;
        }

        // Configurar el número de preguntas en GameManager
        GameManager.getInstance().setNumPreguntas(numPreguntas);

        // Obtener las rutas de archivo seleccionadas por el usuario
        String preguntasFilePath = GameManager.getInstance().getPreguntasFilePath();
        String respuestasFilePath = GameManager.getInstance().getRespuestasFilePath();

        // Cargar los datos del juego usando las rutas seleccionadas
        GameManager.getInstance().loadGameData(preguntasFilePath, respuestasFilePath);

        // Cargar la pantalla de carga
        FXMLLoader loader = new FXMLLoader(getClass().getResource("loadingScreen.fxml"));
        Parent loadingRoot = loader.load();
        Scene currentScene = App.getPrimaryStage().getScene();
        currentScene.setRoot(loadingRoot);

        // Crear una tarea para simular la carga
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // Simular la carga con un tiempo de espera
                Thread.sleep(2000);
                return null;
            }

            @Override
            protected void succeeded() {
                try {
                    // Una vez finalizada la "carga", cambiar a la pantalla de preguntas
                    App.setRoot("preguntas");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        // Iniciar la tarea en un nuevo hilo
        new Thread(task).start();
    }    
    private void mostrarAlertaError(String titulo, String mensaje) {
        Alert alerta = new Alert(AlertType.ERROR, mensaje, ButtonType.OK);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.showAndWait();
    }

    private void mostrarAnimalActual() {
        if (currentAnimalNode != null) {
            animalLabel.setText(currentAnimalNode.getContent());
            String imagePath = "src/main/resources/imgAnimales/" + currentAnimalNode.getContent().toLowerCase() + ".jpg";
            File imageFile = new File(imagePath);
            
            prevButton.setVisible(true);
            nextButton.setVisible(true);
            animalLabel.setVisible(true);
                
            if (imageFile.exists()) {
                Image image = new Image(imageFile.toURI().toString());
                animalImageView.setImage(image);
                animalImageBox.setVisible(true);
            } else {
                imagePath = "src/main/resources/imgAnimales/unknown.jpg";
                imageFile = new File(imagePath);
                Image image = new Image(imageFile.toURI().toString());
                animalImageView.setImage(image);
                animalImageBox.setVisible(true);
            }
        }
    }
    
    @FXML
    private void mostrarAnimalAnterior() {
        System.out.println("Mostrando animal anterior");
        if (currentAnimalNode != null) {
            currentAnimalNode = animales.getPrevious(currentAnimalNode);
            animalLabel.setText(currentAnimalNode.getContent());
            String imagePath = "src/main/resources/imgAnimales/" + currentAnimalNode.getContent().toLowerCase() + ".jpg";
            File imageFile = new File(imagePath);

            if (imageFile.exists()) {
                Image image = new Image(imageFile.toURI().toString());
                animalImageView.setImage(image);
            } else {
                imagePath = "src/main/resources/imgAnimales/unknown.jpg";
                imageFile = new File(imagePath);
                Image image = new Image(imageFile.toURI().toString());
                animalImageView.setImage(image);
            }
        }
    }
    
    @FXML
    private void mostrarAnimalSiguiente() {
        System.out.println("Mostrando animal siguiente");

        if (currentAnimalNode != null) {
            currentAnimalNode = animales.getNext(currentAnimalNode);
            animalLabel.setText(currentAnimalNode.getContent());
            String imagePath = "src/main/resources/imgAnimales/" + currentAnimalNode.getContent().toLowerCase() + ".jpg";
            File imageFile = new File(imagePath);

            if (imageFile.exists()) {
                Image image = new Image(imageFile.toURI().toString());
                animalImageView.setImage(image);
            } else {
                imagePath = "src/main/resources/imgAnimales/unknown.jpg";
                imageFile = new File(imagePath);
                Image image = new Image(imageFile.toURI().toString());
                animalImageView.setImage(image);
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        play("music/おれの最高地点.mp3");
                
        Image image = new Image(getClass().getResourceAsStream("/imagenes/luffy amazed.png"));
        luffyAmazed.setImage(image);
        
        DecisionTree arbolActual = GameManager.getInstance().getDecisionTree();
        animales = obtenerListaAnimales(arbolActual);
        currentAnimalNode = animales.getHead(); 
        mostrarAnimalActual();

    }    
}
