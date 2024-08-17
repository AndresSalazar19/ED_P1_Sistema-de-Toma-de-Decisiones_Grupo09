/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class BinaryTree {
    private NodeBinaryTree root;

    public BinaryTree() {
        this.root = null;
    }

    public BinaryTree(String content) {
        this.root = new NodeBinaryTree(content);
    }
        
    public NodeBinaryTree getRoot() {
        return root;
    }

    public void setRoot(NodeBinaryTree root) {
        this.root = root;
    }

    public boolean isEmpty() {
        return this.root == null;
    }

    public void buildDecisionBinaryTree(LinkedList<String> preguntas, List<String[]> respuestas) {
        if (preguntas.isEmpty() || respuestas.isEmpty()) {
            throw new IllegalArgumentException("Las preguntas y respuestas no deben estar vacías.");
        }

        // nueva raiz asignada al arbol
        this.root = new NodeBinaryTree(preguntas.get(0));

        for (String[] respuesta : respuestas) {
            insert(this.root, preguntas, respuesta, 0);
        }
    }

    private void insert(NodeBinaryTree node, LinkedList<String> preguntas, String[] respuestas, int level) {
        if (node == null) {
            throw new IllegalArgumentException("El nodo no puede ser nulo");
        }

        if (level == preguntas.size() - 1) {
            node.setContent(respuestas[0]);
        } else {
            if (respuestas[level + 1].equalsIgnoreCase("si")) {
                if (node.getLeft().isEmpty()) {
                    node.setLeft(new BinaryTree(preguntas.get(level + 1)));
                }
                insert(node.getLeft().getRoot(), preguntas, respuestas, level + 1);
            } else {
                if (node.getRight().isEmpty()) {
                    node.setRight(new BinaryTree(preguntas.get(level + 1)));
                }
                insert(node.getRight().getRoot(), preguntas, respuestas, level + 1);
            }
        }
    }


    public void updateTreeWithNewAnimal(NodeBinaryTree node, String newAnimal, String question, boolean isYes) {
        String currentAnimal = node.getContent();
        node.setContent(question);
        if (isYes) {
            node.setLeft(new BinaryTree(newAnimal));
            node.setRight(new BinaryTree(currentAnimal));
        } else {
            node.setRight(new BinaryTree(newAnimal));
            node.setLeft(new BinaryTree(currentAnimal));
        }
    }


}
