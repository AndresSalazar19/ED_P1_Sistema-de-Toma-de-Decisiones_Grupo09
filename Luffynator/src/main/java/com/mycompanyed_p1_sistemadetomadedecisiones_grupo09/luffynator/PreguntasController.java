/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompanyed_p1_sistemadetomadedecisiones_grupo09.luffynator;

import java.io.File;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import tda.*;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import static tda.CircularDoublyLinkedList.obtenerListaAnimales;


/**
 * FXML Controller class
 *
 * @author asala
 */
public class PreguntasController implements Initializable {
    @FXML
    private ImageView animalImageView;
    
    @FXML
    private VBox animalImageBox;
    
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
    private Button agregarAnimalButton;
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
                    // Si llegamos a una hoja, finalizarJuego
                    finalizarJuego(siguienteNodo);
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
               agregarAnimalButton.setVisible(true);
               agregarAnimalButton.setOnAction(e -> insertNewAnimalInNullNode(true));
        } else if (ultimoNodo.getYesBranch() == null && ultimoNodo.getNoBranch() == null) {
            preguntaLabel.setText("¡Adiviné! El animal es " + ultimoNodo.getContent() + "!");
            
            animalLabel.setText(ultimoNodo.getContent());
            String imagePath = "src/main/resources/imgAnimales/" + ultimoNodo.getContent().toLowerCase() + ".jpg";
            File imageFile = new File(imagePath);

            if (imageFile.exists()) {
                Image image = new Image(imageFile.toURI().toString());
                animalImageBox.setVisible(true);
                siButton.setVisible(false);
                noButton.setVisible(false);
                animalLabel.setVisible(true);
                animalImageView.setImage(image);
            } else {
                File unknownImageFile = new File("src/main/resources/imgAnimales/unknown.jpg");
                Image image = new Image(unknownImageFile.toURI().toString());
                animalImageView.setImage(image);
                animalImageBox.setVisible(true);
                siButton.setVisible(false);
                noButton.setVisible(false);
                animalLabel.setVisible(true);
                animalImageView.setImage(image);
            }
        } else {
            mostrarListaAnimales();
        }
    }



    private void insertNewAnimalInNullNode(boolean isLeft) {
        try {
            // Cargar la vista del diálogo para agregar animal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("agregarAnimal.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la vista cargada
            AgregarAnimalController controller = loader.getController();

            // Mostrar la ventana de diálogo
            Stage stage = new Stage();
            stage.setTitle("Agregar Nuevo Animal");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Obtener los datos ingresados en el diálogo
            Optional<String> nuevoAnimal = controller.getAnimalName();
            Optional<File> imagenArchivo = controller.getSelectedImageFile();

            // Verificar que se haya ingresado un nombre y seleccionado una imagen
            if (nuevoAnimal.isPresent() && imagenArchivo.isPresent()) {
                String animalName = nuevoAnimal.get();
                File selectedImageFile = imagenArchivo.get();

                // Crear un nuevo nodo con el nombre del animal y asignarlo al lado correspondiente
                if (isLeft) {
                    currentNode.setYesBranch(new DecisionTree(new NodeDecisionTree(animalName)));
                } else {
                    currentNode.setNoBranch(new DecisionTree(new NodeDecisionTree(animalName)));
                }

                // Guardar la imagen con el nombre del animal
                String destFileName = animalName.toLowerCase() + ".jpg";
                File destFile = new File("src/main/resources/imgAnimales/" + destFileName);
                Files.copy(selectedImageFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                // Informar al usuario que el árbol ha sido actualizado
                preguntaLabel.setText("¡Gracias! He aprendido sobre " + animalName + "!");
                siButton.setDisable(true);
                noButton.setDisable(true);

                // Actualizar el archivo de respuestas
                escribirNuevoAnimalEnArchivo(animalName);
            } else {
                System.out.println("No se ingresó un nombre o no se seleccionó una imagen.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostrarListaAnimales() {
        
        DecisionTree arbolActual = GameManager.getInstance().getDecisionTree();
        animales = obtenerListaAnimales(arbolActual); 
        
        if(animales.length() == 0){
            preguntaLabel.setText("Rayos, no se me ocurré ningún animal con esas características ");
            return;
        }
        if(animales.length() == 1){
            currentAnimalNode = animales.getHead(); 
            preguntaLabel.setText("jejejejej " + " adiviné  , aquí te van.");

            mostrarAnimalActual();                
            prevButton.setVisible(false);
            nextButton.setVisible(false);           
            return; 
        }

        currentAnimalNode = animales.getHead(); 
        preguntaLabel.setText("Se me ocurren " + animales.length() + " animales, aquí te van.");

        mostrarAnimalActual();
    }

    private void mostrarAnimalActual() {
        if (currentAnimalNode != null) {
            animalLabel.setText(currentAnimalNode.getContent());
            String imagePath = "src/main/resources/imgAnimales/" + currentAnimalNode.getContent().toLowerCase() + ".jpg";
            File imageFile = new File(imagePath);

            if (imageFile.exists()) {
                Image image = new Image(imageFile.toURI().toString());
                animalImageBox.setVisible(true);
                siButton.setVisible(false);
                noButton.setVisible(false);
                prevButton.setVisible(true);
                nextButton.setVisible(true);
                animalLabel.setVisible(true);
                animalImageView.setImage(image);
            } else {
                System.out.println("Imagen no encontrada: " + imagePath);
                animalImageView.setImage(null); 
            }
        }
    }
    
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
                System.out.println("Imagen no encontrada: " + imagePath);
                animalImageView.setImage(null); // Limpia la vista si no se encuentra la imagen
            }
        }
    }

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
                System.out.println("Imagen no encontrada: " + imagePath);
                animalImageView.setImage(null); // Opcional: Limpia la vista si no se encuentra la imagen
            }
        }
    }


    private void escribirNuevoAnimalEnArchivo(String nuevoAnimal) throws IOException {
        LinkedList<String> caminoConComillas = new LinkedList<>(caminoActual);
        caminoConComillas.addFirst(nuevoAnimal);

        String lineaCSV = String.join(",", caminoConComillas);

        String respuestasFilePath = GameManager.getInstance().getRespuestasFilePath();

        try (BufferedReader reader = new BufferedReader(new FileReader(respuestasFilePath))) {
            StringBuilder contenido = new StringBuilder();
            String linea;

            // Leer el contenido del archivo y eliminar líneas vacías
            while ((linea = reader.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    contenido.append(linea).append(System.lineSeparator());
                }
            }

            // Añadir la nueva línea al contenido
            contenido.append(lineaCSV);

            // Escribir todo el contenido de nuevo en el archivo
            try (FileWriter writer = new FileWriter(respuestasFilePath)) {
                writer.write(contenido.toString());
            }

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
