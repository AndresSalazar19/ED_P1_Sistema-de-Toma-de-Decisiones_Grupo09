/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompanyed_p1_sistemadetomadedecisiones_grupo09.luffynator;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import tda.CircularDoublyLinkedList;
import static tda.CircularDoublyLinkedList.obtenerListaAnimales;
import tda.DecisionTree;

/**
 * FXML Controller class
 *
 * @author asala
 */
public class ConfiguracionController implements Initializable {

    @FXML
    private VBox animalListVBox;

    @FXML
    private Button volverButton;

    private CircularDoublyLinkedList listaAnimales;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarAnimales();
        System.out.println("Número de animales: " + listaAnimales.length()); // Verifica el número de animales

        // Recorrer los animales y agregarlos al VBox
        for (int i = 0; i < listaAnimales.length(); i++) {
            String animal = listaAnimales.get(i);
            System.out.println("Animal " + i + ": " + animal); // Imprime cada animal

            HBox animalBox = new HBox(10);  // Espaciado de 10 entre los elementos
            animalBox.setStyle("-fx-alignment: center-left;");

            // Crear y agregar el número del animal
            Label numeroLabel = new Label((i + 1) + ". ");
            numeroLabel.setStyle("-fx-font-size: 16px;");

            // Crear y agregar el nombre del animal
            Label animalLabel = new Label(animal);
            animalLabel.setStyle("-fx-font-size: 16px;");

            // Crear y agregar la imagen del animal
            ImageView animalImageView = new ImageView();
            animalImageView.setFitHeight(50);
            animalImageView.setFitWidth(50);
            File imageFile = new File("src/main/resources/imgAnimales/" + animal.toLowerCase() + ".jpg");
            if (imageFile.exists()) {
                animalImageView.setImage(new Image(imageFile.toURI().toString()));
            } else {
                // Imagen predeterminada si no se encuentra la imagen
                animalImageView.setImage(new Image(getClass().getResourceAsStream("/imgAnimales/unknown.jpg")));
            }

            // Crear y agregar el botón de editar imagen
            Button editarButton = new Button("Editar Imagen");
            editarButton.setOnAction(event -> {
                try {
                    editarImagen(animalLabel.getText(), animalImageView);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            // Agregar todos los componentes al HBox
            animalBox.getChildren().addAll(numeroLabel, animalLabel, animalImageView, editarButton);

            // Agregar el HBox al VBox
            animalListVBox.getChildren().add(animalBox);
        }
    }


    private void cargarAnimales() {
        DecisionTree arbolActual = GameManager.getInstance().getDecisionTree();
        listaAnimales = obtenerListaAnimales(arbolActual); 
    }

    private void editarImagen(String animal, ImageView animalImageView) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(volverButton.getScene().getWindow());
        if (selectedFile != null) {
            // Guardar la nueva imagen con el nombre del animal
            File destFile = new File("src/main/resources/imgAnimales/" + animal.toLowerCase() + ".jpg");
            Files.copy(selectedFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            // Actualizar la ImageView
            animalImageView.setImage(new Image(destFile.toURI().toString()));
        }
    }

    @FXML
    public void volver() throws IOException {
        App.setRoot("inicio");
    }
}