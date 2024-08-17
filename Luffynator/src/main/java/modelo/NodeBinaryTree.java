/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author asala
 * @param <E>
 */
public class NodeBinaryTree {
    private String content;
    private BinaryTree left;
    private BinaryTree right;
    
    public NodeBinaryTree(String content){
        this.content = content;
        this.left = new BinaryTree();  // Inicializamos como un árbol vacío
        this.right = new BinaryTree(); // Inicializamos como un árbol vacío
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BinaryTree getLeft() {
        return left;
    }

    public void setLeft(BinaryTree left) {
        this.left = left;
    }

    public BinaryTree getRight() {
        return right;
    }

    public void setRight(BinaryTree right) {
        this.right = right;
    }
}
