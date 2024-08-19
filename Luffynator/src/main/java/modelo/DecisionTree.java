/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class DecisionTree {
    private NodeDecisionTree root;
    private List<String> elements;

    public DecisionTree() {}

    public DecisionTree(NodeDecisionTree root) {
        this.root = root;
    }

    public NodeDecisionTree getRoot() {
        return root;
    }

    public void setRoot(NodeDecisionTree root) {
        this.root = root;
    }
    
    // Método para establecer los elementos (preguntas)
    public void setElements(List<String> elements) {
        this.elements = elements;
    }

    public List<String> getElements() {
        return elements;
    }

    public boolean isEmpty() {
        return this.root == null;
    }


    // Método insert que añade nodos al árbol en base a las respuestas
    private void insert(NodeDecisionTree node, LinkedList<String> preguntas, String[] respuestas, int nivel) {
        if (node == null) {
            throw new IllegalStateException("El nodo actual es nulo en nivel " + nivel + ". Esto no debería ocurrir.");
        }

        // Si llegamos al final de las preguntas, asignamos el animal al nodo actual
        if (nivel >= preguntas.size()) {
            node.setContent(respuestas[0]); // guardamos el animal
            return;
        }

        String respuesta = respuestas[nivel + 1]; // Respuesta a la pregunta actual

        // Si la respuesta es "si", movemos a la rama izquierda
        if (respuesta.equalsIgnoreCase("si")) {
            if (node.getYesBranch() == null) {
                node.setYesBranch(new DecisionTree(new NodeDecisionTree(preguntas.get(nivel + 1))));
            }
            insert(node.getYesBranch().getRoot(), preguntas, respuestas, nivel + 1);
        } 
        // Si la respuesta es "no", movemos a la rama derecha
        else {
            if (node.getNoBranch() == null) {
                node.setNoBranch(new DecisionTree(new NodeDecisionTree(preguntas.get(nivel + 1))));
            }
            insert(node.getNoBranch().getRoot(), preguntas, respuestas, nivel + 1);
        }
    }


    public void updateTreeWithNewAnimal(NodeDecisionTree node, String newAnimal, String question, boolean isYes) {
        String currentAnimal = node.getContent();
        node.setContent(question);
        if (isYes) {
            node.setYesBranch(new DecisionTree(new NodeDecisionTree(newAnimal)));
            node.setNoBranch(new DecisionTree(new NodeDecisionTree(currentAnimal)));
        } else {
            node.setNoBranch(new DecisionTree(new NodeDecisionTree(newAnimal)));
            node.setYesBranch(new DecisionTree(new NodeDecisionTree(currentAnimal)));
        }
    }


}
