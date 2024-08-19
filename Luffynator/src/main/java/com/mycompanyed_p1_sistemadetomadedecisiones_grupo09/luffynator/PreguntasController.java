/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompanyed_p1_sistemadetomadedecisiones_grupo09.luffynator;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
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
    
    private List<String> caminoActual = new ArrayList<>();

    private DecisionTree decisionTree;
    private NodeDecisionTree currentNode;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        decisionTree = GameManager.getInstance().getDecisionTree();
        currentNode = decisionTree.getRoot();

        if (currentNode == null) {
            throw new IllegalStateException("El árbol de decisiones no debería estar vacío en este punto.");
        }

        verificarArbol(currentNode); // Verifica la integridad del árbol

        preguntaLabel.setText(currentNode.getContent());

        siButton.setOnAction(e -> avanzar(true));
        noButton.setOnAction(e -> avanzar(false));
    }
    
    public void verificarArbol(NodeDecisionTree node) {
        if (node == null) return;

        if (node.getYesBranch() == null && node.getNoBranch() == null) {
            System.out.println("Nodo hoja encontrado: " + node.getContent());
        } else {
            System.out.println("Nodo interno encontrado: " + node.getContent());
        }

        if (node.getYesBranch() != null && node.getYesBranch().getRoot() != null) {
            verificarArbol(node.getYesBranch().getRoot());
        }
        if (node.getNoBranch() != null && node.getNoBranch().getRoot() != null) {
            verificarArbol(node.getNoBranch().getRoot());
        }
    }


    private void avanzar(boolean respuestaSi) {
       // Guardar la decisión en el camino
       caminoActual.add(respuestaSi ? "si" : "no");

       // Avanzar en el árbol según la respuesta
       NodeDecisionTree siguienteNodo = null;
       if (respuestaSi && currentNode.getYesBranch() != null) {
           siguienteNodo = currentNode.getYesBranch().getRoot();
       } else if (!respuestaSi && currentNode.getNoBranch() != null) {
           siguienteNodo = currentNode.getNoBranch().getRoot();
       }

       // Verificar si aún hay preguntas pendientes en el árbol
       if (siguienteNodo != null) {
           currentNode = siguienteNodo;

           if (currentNode.getYesBranch() == null && currentNode.getNoBranch() == null) {
               // Si llegamos a una hoja, preguntar si es el animal correcto
               preguntaLabel.setText("¿Es " + currentNode.getContent() + " el animal en el que estabas pensando?");
               siButton.setOnAction(e -> manejarRespuestaFinal(true));
               noButton.setOnAction(e -> manejarRespuestaFinal(false));
           } else {
               // Continuar con la siguiente pregunta
               preguntaLabel.setText(currentNode.getContent());
           }
       } else {
           // Si no hay más preguntas, manejar el caso donde debemos insertar un nuevo animal
           insertNewAnimalInNullNode(respuestaSi);
       }
    }



    private void manejarRespuestaFinal(boolean esCorrecto) {
        if (esCorrecto) {
            preguntaLabel.setText("¡Gracias! ¡El animal es " + currentNode.getContent() + "!");
        } else {
            insertNewAnimalInNullNode(false);
        }
        siButton.setDisable(true);
        noButton.setDisable(true);
    }


    private void insertNewAnimalInNullNode(boolean isLeft) {
        // Preguntar al usuario cuál era el animal en el que estaba pensando
        TextInputDialog animalDialog = new TextInputDialog();
        animalDialog.setTitle("Nuevo Animal");
        animalDialog.setHeaderText("No conozco ese animal.");
        animalDialog.setContentText("¿En qué animal estabas pensando?");
        Optional<String> nuevoAnimal = animalDialog.showAndWait();

        if (nuevoAnimal.isPresent()) {
            String animal = nuevoAnimal.get();

            // Crear un nuevo nodo con el animal y asignarlo al lado correspondiente
            if (isLeft) {
                currentNode.setYesBranch(new DecisionTree(new NodeDecisionTree(animal)));
            } else {
                currentNode.setNoBranch(new DecisionTree(new NodeDecisionTree(animal)));
            }

            // Informar al usuario que el árbol ha sido actualizado
            preguntaLabel.setText("¡Gracias! He aprendido sobre " + animal + "!");
            siButton.setDisable(true);
            noButton.setDisable(true);

            // Actualizar el archivo de respuestas
            escribirNuevoAnimalEnArchivo(animal);
        }
    }

    private void escribirNuevoAnimalEnArchivo(String nuevoAnimal) {
        LinkedList<String> caminoConComillas = new LinkedList<>(caminoActual);
        caminoConComillas.addFirst("\"" + nuevoAnimal + "\"");

        String lineaCSV = String.join(",", caminoConComillas);

        try (FileWriter writer = new FileWriter("src/main/java/archivos/ArchivoRespuestas.csv", true)) {
            writer.write("\n" + lineaCSV);
        } catch (IOException e) {
            e.printStackTrace();
        }

        caminoActual.clear();
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
