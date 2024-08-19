/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompanyed_p1_sistemadetomadedecisiones_grupo09.luffynator;

import tda.*;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import static tda.CircularDoublyLinkedList.obtenerListaAnimales;


/**
 * FXML Controller class
 *
 * @author asala
 */
public class PreguntasController implements Initializable {

    @FXML
    private Label preguntaLabel;
  @FXML
    private Label animalLabel;
    @FXML
    private Button siButton;

    @FXML
    private Button noButton;
    @FXML
    private Button prevButton;

    @FXML
    private Button nextButton;
    
    private List<String> caminoActual = new ArrayList<>();
    private int contadorPreguntas = 0;

    private DecisionTree decisionTree;
    private NodeDecisionTree currentNode;
    private Node currentAnimalNode;
    private CircularDoublyLinkedList animales;

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
        
        prevButton.setOnAction(e -> mostrarAnimalAnterior());
        nextButton.setOnAction(e -> mostrarAnimalSiguiente());
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
       contadorPreguntas++;
       // Guardar la decisión en el camino
       caminoActual.add(respuestaSi ? "si" : "no");

       // Avanzar en el árbol según la respuesta
       NodeDecisionTree siguienteNodo = null;
       if (respuestaSi && currentNode.getYesBranch() != null) {
           GameManager.getInstance().setDecisionTree(currentNode.getYesBranch());
           siguienteNodo = currentNode.getYesBranch().getRoot();
       } else if (!respuestaSi && currentNode.getNoBranch() != null) {
           GameManager.getInstance().setDecisionTree(currentNode.getNoBranch());
           siguienteNodo = currentNode.getNoBranch().getRoot();
       }

     // Verificar si se alcanzó el número máximo de preguntas
        if (contadorPreguntas >= GameManager.getInstance().getNumPreguntas()) {
            finalizarJuego(siguienteNodo);
        } else {
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
                finalizarJuego(null);
            }
        }
    }

    private void finalizarJuego(NodeDecisionTree ultimoNodo) {
        if (ultimoNodo == null) {
            // No existe un animal en el árbol para este camino
            insertNewAnimalInNullNode(true); // Opción a agregar un nuevo animal
        } else if (ultimoNodo.getYesBranch() == null && ultimoNodo.getNoBranch() == null) {
            // Existe un único animal
            preguntaLabel.setText("¡Gracias! El animal es " + ultimoNodo.getContent() + "!");
            siButton.setDisable(true);
            noButton.setDisable(true);
        } else {
            // Existen varios animales, mostrar la lista de animales
            mostrarListaAnimales();
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

    
    private void mostrarListaAnimales() {
        DecisionTree arbolActual = GameManager.getInstance().getDecisionTree();
        animales = obtenerListaAnimales(arbolActual); // Aquí se asegura la asignación correcta a la variable de clase
        currentAnimalNode = animales.getHead(); // Obtener el primer nodo para inicializar la navegación
        preguntaLabel.setText("Se encontraron " + animales.length() + " animales, aquí está la lista.");

        // Mostrar el primer animal en la lista
        if (currentAnimalNode != null) {
            animalLabel.setText(currentAnimalNode.getContent());
        }

        // Configurar la visibilidad de los botones
        siButton.setVisible(false);
        noButton.setVisible(false);
        prevButton.setVisible(true);
        nextButton.setVisible(true);
    }
    
    private void mostrarAnimalAnterior() {
        System.out.println("Mostrando animal anterior");
        if (currentAnimalNode != null) {
            currentAnimalNode = animales.getPrevious(currentAnimalNode);
            animalLabel.setText(currentAnimalNode.getContent());
        }
    }

    private void mostrarAnimalSiguiente() {
        System.out.println("Mostrando animal siguiente");

        if (currentAnimalNode != null) {
            currentAnimalNode = animales.getNext(currentAnimalNode);
            animalLabel.setText(currentAnimalNode.getContent());
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
