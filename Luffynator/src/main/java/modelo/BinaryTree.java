/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author asala
 * @param <E>
 */
public class BinaryTree<E> {
    private NodeBinaryTree<E> root;

    public BinaryTree() {
        this.root = null;
    }

    public BinaryTree(E content) {
        this.root = new NodeBinaryTree<>(content);
    }
        
    public NodeBinaryTree<E> getRoot() {
        return root;
    }

    public void setRoot(NodeBinaryTree<E> root) {
        this.root = root;
    }

    public boolean isEmpty() {
        return this.root == null;
    }

    public void buildDecisionBinaryTree(LinkedList<String> preguntas, List<String[]> respuestas) {
        if (preguntas.isEmpty() || respuestas.isEmpty()) {
            throw new IllegalArgumentException("Las preguntas y respuestas no deben estar vacías.");
        }

        this.root = new NodeBinaryTree<>((E) preguntas.get(0));

        for (String[] respuesta : respuestas) {
            insert((NodeBinaryTree<String>) this.root, preguntas, respuesta, 0);
        }
    }

private void insert(NodeBinaryTree<String> node, LinkedList<String> preguntas, String[] respuestas, int level) {
        if (node == null) {
        throw new IllegalArgumentException("El nodo no puede ser nulo");
    }
        
    if (level == preguntas.size() - 1) {
        node.setContent(respuestas[0]);
    } else {
        if (respuestas[level + 1].equalsIgnoreCase("si")) {
            if (node.getLeft() == null) {
                // Crea un nuevo nodo izquierdo
                node.setLeft(new BinaryTree<>(preguntas.get(level + 1)));
            }
            insert(node.getLeft().getRoot(), preguntas, respuestas, level + 1);
        } else {
            if (node.getRight() == null) {
                // Crea un nuevo nodo derecho
                node.setRight(new BinaryTree<>(preguntas.get(level + 1)));
            }
            insert(node.getRight().getRoot(), preguntas, respuestas, level + 1);
        }
    }
}


}
