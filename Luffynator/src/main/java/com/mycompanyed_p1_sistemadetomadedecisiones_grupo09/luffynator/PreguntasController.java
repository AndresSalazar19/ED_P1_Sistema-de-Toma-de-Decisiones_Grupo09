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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import modelo.*;

/**
 * FXML Controller class
 *
 * @author asala
 */
public class PreguntasController implements Initializable {

    @FXML
    private Label preguntaLabel;

    @FXML
    private Button siButton;

    @FXML
    private Button noButton;

    private BinaryTree<String> decisionTree;
    private NodeBinaryTree<String> currentNode;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Cargar el árbol de decisiones desde GameManager
        decisionTree = GameManager.getInstance().getDecisionTree();
        currentNode = decisionTree.getRoot();
        
        System.out.println("EA EA EA" );
        // Mostrar la primera pregunta
        if (currentNode != null) {
            preguntaLabel.setText(currentNode.getContent());
        }

        // Configurar las acciones de los botones
        siButton.setOnAction(e -> avanzar(true));
        noButton.setOnAction(e -> avanzar(false));
    }

    private void avanzar(boolean respuestaSi) {
        // Mover al siguiente nodo basado en la respuesta del usuario
        if (respuestaSi && currentNode.getLeft() != null) {
            currentNode = currentNode.getLeft().getRoot();
        } else if (!respuestaSi && currentNode.getRight() != null) {
            currentNode = currentNode.getRight().getRoot();
        } else {
            // Si no hay más preguntas, mostrar el resultado
            preguntaLabel.setText("¡El animal es: " + currentNode.getContent() + "!");
            siButton.setDisable(true);
            noButton.setDisable(true);
            return;
        }

        // Actualizar el texto de la pregunta
        preguntaLabel.setText(currentNode.getContent());
    }

    @FXML
    private void volver() {
        // Lógica para volver a la pantalla anterior
        try {
            App.setRoot("opcionesJuego");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
