/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.lab05heapssalazar;

import java.util.Comparator;

/**
 *
 * @author asala
 */

public class Main {
    public static void main(String[] args) {
        Comparator<Integer> comparator = (a, b) -> a.compareTo(b);
        Heap<Integer> maxHeap = new Heap<>(10, comparator, true);
        
        // Encolar elementos
        maxHeap.encolar(1);
        maxHeap.encolar(4);
        maxHeap.encolar(15);
        maxHeap.encolar(20);
        maxHeap.encolar(10);


        System.out.println("Heap impreso de acuerdo a su indice:");
        maxHeap.print();

        // Extraer y ordenar los elementos del heap utilizando desencolar
        System.out.println("Heap ordenado de mayor a menor:");
        while (!maxHeap.isEmpty()) {
            System.out.print(maxHeap.desencolar() + " ");
        }
        
        

        // Crear un arreglo desordenado
        Integer[] array = {3, 5, 2, 4, 1};

        // Pasar el arreglo al método makeHeap para construir el Max-Heap
        System.out.println("Arreglo original:");
        for (int num : array) {
            System.out.print(num + " ");
        }
        System.out.println();
        
        // Hacemos el heap a partir del arreglo dado
        maxHeap.makeHeap(array);
        
        // Imprimir el heap construido internamente
        System.out.println("Heap después de makeHeap:");
        maxHeap.print();

        maxHeap.desencolar();
        System.out.println("Despues de desencolar");
        maxHeap.print();
    }
}
