/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompanyed_p1_sistemadetomadedecisiones_grupo09.luffynator;

/**
 *
 * @author asala
 */

import modelo.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class GameManager {
    private static GameManager instance;
    private int numPreguntas;
    private BinaryTree decisionTree;

    // Constructor privado para evitar la instanciación directa
    private GameManager() {}

    // Método para obtener la instancia única de GameManager
    public static  GameManager getInstance() {
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

    public BinaryTree getDecisionTree() {
        return decisionTree;
    }
    
    public void setDecisionTree(BinaryTree decisionTree) {
        this.decisionTree = decisionTree;
    }
        
    public void loadGameData(String preguntasFilePath, String respuestasFilePath) throws IOException {
        LinkedList<String> preguntas = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(preguntasFilePath))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
                preguntas.add(linea);
            }
        }

        List<String[]> respuestas = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(respuestasFilePath))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                System.out.println(linea);
                respuestas.add(partes);
            }
        }

        decisionTree = new BinaryTree();
        decisionTree.buildDecisionBinaryTree(preguntas, respuestas);
        this.decisionTree = decisionTree;
    }
}