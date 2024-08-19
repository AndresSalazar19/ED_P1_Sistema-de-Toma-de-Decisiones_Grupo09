/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tda;

/**
 *
 * @author asala
 */

import java.util.LinkedList;
import java.util.Queue;

public class CircularDoublyLinkedList {
    private Node head;

    public void append(String data) {
        Node newNode = new Node(data);

        if (head == null) {
            head = newNode;
        } else {
            Node tail = head.prev;
            tail.next = newNode;
            newNode.prev = tail;
            newNode.next = head;
            head.prev = newNode;
        }
    }

        public Node getNext(Node currentNode) {
        if (currentNode == null) {
            return null;
        }
        return currentNode.next;
    }
        public Node getHead(){
            return head;
        }
        
    public Node getPrevious(Node currentNode) {
        if (currentNode == null) {
            return null;
        }
        return currentNode.prev;
    }
    
    public void display() {
        if (head == null) {
            System.out.println("List is empty.");
            return;
        }

        Node temp = head;
        do {
            System.out.print(temp.data + " <-> ");
            temp = temp.next;
        } while (temp != head);
        System.out.println(temp.data);  // Print the last element pointing to head
    }
    

    public static CircularDoublyLinkedList obtenerListaAnimales(DecisionTree tree) {
        CircularDoublyLinkedList listaAnimales = new CircularDoublyLinkedList();
        System.out.println("OBTENIENDO ANIMALES");

        if (tree == null || tree.getRoot() == null) {
            System.out.println("ES NULLLLLL");
            return listaAnimales;
        }

        Queue<NodeDecisionTree> queue = new LinkedList<>();
        System.out.println("ME ARRECHO" + tree.getRoot().getContent());
        queue.add(tree.getRoot());
        int count = 1;

        while (!queue.isEmpty()) {
            System.out.println("FAAAA " + count);
            count += 1;
            NodeDecisionTree currentNode = queue.poll();

            // Verificar si es una hoja
            boolean isLeaf = (currentNode.getYesBranch() == null || currentNode.getYesBranch().isEmpty()) &&
                             (currentNode.getNoBranch() == null || currentNode.getNoBranch().isEmpty());

            if (isLeaf) {
                listaAnimales.append(currentNode.getContent());
                System.out.println(currentNode.getContent() + " Fue agregado a la lista");
            }

            if (currentNode.getYesBranch() != null && !currentNode.getYesBranch().isEmpty()) {
                queue.add(currentNode.getYesBranch().getRoot());
            }

            if (currentNode.getNoBranch() != null && !currentNode.getNoBranch().isEmpty()) {
                queue.add(currentNode.getNoBranch().getRoot());
            }
        }
        return listaAnimales;
    }
    public int length() {
        if (head == null) {
            return 0;
        }

        int length = 0;
        Node temp = head;

        do {
            length++;
            temp = temp.next;
        } while (temp != head);

        return length;
    }

}