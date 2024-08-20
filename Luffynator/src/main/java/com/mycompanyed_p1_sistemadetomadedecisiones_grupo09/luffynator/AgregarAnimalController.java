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
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author asala
 */
public class AgregarAnimalController {

    @FXML
    private TextField animalTextField;

    @FXML
    private ImageView animalImageView;

    @FXML
    private Button addImageButton;

    @FXML
    private Button saveButton;

    private File selectedImageFile;

    @FXML
    private void handleAddImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"));
        selectedImageFile = fileChooser.showOpenDialog(addImageButton.getScene().getWindow());
        if (selectedImageFile != null) {
            Image image = new Image(selectedImageFile.toURI().toString());
            animalImageView.setImage(image);
            animalImageView.setVisible(true);
        }
    }

    @FXML
    private void handleSaveAnimal() {
        String animalName = animalTextField.getText().trim();

        if (animalName.isEmpty() || selectedImageFile == null) {
            // Mostrar alerta al usuario
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Datos incompletos");
            alert.setContentText("Debe ingresar un nombre y seleccionar una imagen.");
            alert.showAndWait();
            return;
        }

        try {
            // Copiar la imagen al directorio del proyecto
            File destFile = new File("src/main/resources/imgAnimales/" + animalName.toLowerCase() + ".jpg");
            Files.copy(selectedImageFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            // Cerrar la ventana
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancel() {
        // Cerrar la ventana sin guardar
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    // Método para obtener el nombre del animal
    public Optional<String> getAnimalName() {
        String animalName = animalTextField.getText().trim();
        return animalName.isEmpty() ? Optional.empty() : Optional.of(animalName);
    }

    // Método para obtener el archivo de la imagen seleccionada
    public Optional<File> getSelectedImageFile() {
        return Optional.ofNullable(selectedImageFile);
    }
}