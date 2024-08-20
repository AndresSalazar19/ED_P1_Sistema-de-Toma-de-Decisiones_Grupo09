/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tda;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
