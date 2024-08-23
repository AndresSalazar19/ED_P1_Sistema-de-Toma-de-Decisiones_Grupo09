/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompanyed_p1_sistemadetomadedecisiones_grupo09.luffynator;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import tda.*;
import static tda.CircularDoublyLinkedList.obtenerListaAnimales;


/**
 *
 * @author asala
 */

public class GameManager {
    private static GameManager instance;
    private int numPreguntas;
    private DecisionTree decisionTree;
    private String preguntasFilePath = "src/main/java/archivos/ArchivoPreguntas.csv";
    private String respuestasFilePath = "src/main/java/archivos/ArchivoRespuestas.csv";

    private GameManager() {}

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public int getNumPreguntas() {
        return numPreguntas;
    }

    public void setNumPreguntas(int numPreguntas) {
        this.numPreguntas = numPreguntas;
    }

    public DecisionTree getDecisionTree() {
        return decisionTree;
    }

    public void setDecisionTree(DecisionTree decisionTree) {
        this.decisionTree = decisionTree;
    }
    
    public String getPreguntasFilePath() {
        return preguntasFilePath;
    }

    public String getRespuestasFilePath() {
        return respuestasFilePath;
    }
    
    public void setPreguntasFilePath(String preguntasFilePath) {
        this.preguntasFilePath = preguntasFilePath;
    }

    public void setRespuestasFilePath(String respuestasFilePath) {
        this.respuestasFilePath = respuestasFilePath;
    }

    public void loadGameData(String preguntasFilePath, String respuestasFilePath) throws IOException {
        this.preguntasFilePath = preguntasFilePath;
        this.respuestasFilePath = respuestasFilePath;
        List<String> preguntas = readFile(preguntasFilePath);
        List<String> respuestas = readFile(respuestasFilePath);
        decisionTree = buildDecisionTree(preguntas, respuestas);
        
        CircularDoublyLinkedList animales = obtenerListaAnimales(decisionTree);
        animales.display();
    }

    public static List<String> readFile(String filePath) throws IOException {
        List<String> lineas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                // Eliminar cualquier punto y coma adicional al final de cada línea
                linea = linea.replaceAll(";+\\s*$", "");
                lineas.add(linea);
            }
        }
        return lineas;
    }




    public static DecisionTree buildDecisionTree(List<String> questions, List<String> answers) {
        DecisionTree decisionTree = new DecisionTree();
        decisionTree.setRoot(new NodeDecisionTree(questions.get(0))); // La primera pregunta es la raíz

        // Recorremos cada respuesta para construir el árbol
        for (String answer : answers) {
            String[] sepAnswer = answer.split(",");
            String element = sepAnswer[0]; // El animal (e.g., "gato")

            NodeDecisionTree current = decisionTree.getRoot();

            for (int i = 1; i < sepAnswer.length; i++) {
                // Determina la dirección de la rama según la respuesta
                if (sepAnswer[i].equalsIgnoreCase("si")) {
                    if (current.getYesBranch() == null) {
                        if (i == sepAnswer.length - 1) {
                            // Si estamos en la última respuesta, creamos una hoja con el animal
                            current.setYesBranch(new DecisionTree(new NodeDecisionTree(element)));
                        } else {
                            // Si no es la última respuesta, creamos un nodo intermedio
                            current.setYesBranch(new DecisionTree(new NodeDecisionTree(questions.get(i))));
                        }
                    }
                    current = current.getYesBranch().getRoot();
                } else {
                    if (current.getNoBranch() == null) {
                        if (i == sepAnswer.length - 1) {
                            // Si estamos en la última respuesta, creamos una hoja con el animal
                            current.setNoBranch(new DecisionTree(new NodeDecisionTree(element)));
                        } else {
                            // Si no es la última respuesta, creamos un nodo intermedio
                            current.setNoBranch(new DecisionTree(new NodeDecisionTree(questions.get(i))));
                        }
                    }
                    current = current.getNoBranch().getRoot();
                }
            }
        }

        return decisionTree;
    }



}
