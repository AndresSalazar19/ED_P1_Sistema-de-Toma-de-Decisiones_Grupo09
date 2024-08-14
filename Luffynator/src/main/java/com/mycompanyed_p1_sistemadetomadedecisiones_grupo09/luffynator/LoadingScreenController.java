/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompanyed_p1_sistemadetomadedecisiones_grupo09.luffynator;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;

/**
 * FXML Controller class
 *
 * @author asala
 */
public class LoadingScreenController implements Initializable {

    @FXML
    private Circle loadingCircle;

    @FXML
    private Label loadingLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Crear animación de rotación
    RotateTransition rotateTransition = new RotateTransition(new javafx.util.Duration(2000), loadingCircle);
    rotateTransition.setByAngle(360);
    rotateTransition.setCycleCount(RotateTransition.INDEFINITE);
    rotateTransition.play();

    }

    public void setLoadingMessage(String message) {
        loadingLabel.setText(message);
    }    
    
}
