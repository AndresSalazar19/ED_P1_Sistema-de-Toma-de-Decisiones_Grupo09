/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab05heapssalazar;

import java.util.Comparator;

/**
 *
 * @author asala
 * @param <E>
 */

public class Heap<E> {
    private Comparator<E> f;
    private E[] arreglo;
    private int MAX = 100;
    private int efectivo;
    private boolean isMax;

    public Heap(int tmax, Comparator<E> f, boolean isMax) {
        this.MAX = tmax;
        this.isMax = isMax;
        this.f = f;
        this.arreglo = (E[]) new Object[MAX];
        this.efectivo = 0;
    }

    private int getIzq(int pos) {
        return 2 * pos + 1;
    }

    private int getDer(int pos) {
        return 2 * pos + 2;
    }

    private int getPadre(int pos) {
        return (pos - 1) / 2;
    }

    private boolean isValidIndex(int pos) {
        return pos >= 0 && pos < efectivo;
    }

    private void intercambiar(int i, int j) {
        E temp = arreglo[i];
        arreglo[i] = arreglo[j];
        arreglo[j] = temp;
    }

    private void ajustar(int posRaiz) {
        int posOrden;
        if (this.isMax) {
            posOrden = getPosMayor(posRaiz);
        } else {
            posOrden = getPosMenor(posRaiz);
        }
        if (posOrden != posRaiz) {
            intercambiar(posRaiz, posOrden);
            ajustar(posOrden);
        }
    }

    private int getPosMayor(int pos) {
        int posIzq = getIzq(pos);
        int posDer = getDer(pos);
        int posMayor = pos;

        if (isValidIndex(posIzq) && f.compare(arreglo[posIzq], arreglo[posMayor]) > 0) {
            posMayor = posIzq;
        }

        if (isValidIndex(posDer) && f.compare(arreglo[posDer], arreglo[posMayor]) > 0) {
            posMayor = posDer;
        }

        return posMayor;
    }

    private int getPosMenor(int pos) {
        int posIzq = getIzq(pos);
        int posDer = getDer(pos);
        int posMenor = pos;

        if (isValidIndex(posIzq) && f.compare(arreglo[posIzq], arreglo[posMenor]) < 0) {
            posMenor = posIzq;
        }

        if (isValidIndex(posDer) && f.compare(arreglo[posDer], arreglo[posMenor]) < 0) {
            posMenor = posDer;
        }

        return posMenor;
    }

    public void makeHeap(E[] array) {
        this.efectivo = array.length;
        if (efectivo > MAX) {
            throw new IllegalStateException("El tamaño del arreglo excede la capacidad del Heap");
        }

        System.arraycopy(array, 0, this.arreglo, 0, efectivo);

        for (int i = efectivo / 2 - 1; i >= 0; i--) {
            ajustar(i);
        }
    }

    public E desencolar() {
        if (isEmpty()) {
            return null;
        }

        E maxValue = this.arreglo[0];
        intercambiar(0, this.efectivo - 1);
        this.efectivo--;
        ajustar(0);
        return maxValue;
    }

    public void encolar(E e) {
        if (this.efectivo == MAX) {
            MAX *= 2;
            E[] nuevoArreglo = (E[]) new Object[MAX];
            System.arraycopy(arreglo, 0, nuevoArreglo, 0, this.efectivo);
            arreglo = nuevoArreglo;
        }
        if (e != null) {
            arreglo[this.efectivo] = e;
            int posActual = this.efectivo;
            this.efectivo++;
            
            // Ajuste hacia arriba
            while (posActual > 0 && (isMax ? f.compare(arreglo[posActual], arreglo[getPadre(posActual)]) > 0
                                           : f.compare(arreglo[posActual], arreglo[getPadre(posActual)]) < 0)) {
                intercambiar(posActual, getPadre(posActual));
                posActual = getPadre(posActual);
            }
        }
    }

    public E getRaiz() {
        if (efectivo == 0) {
            throw new IllegalStateException("Heap vacío");
        }
        return arreglo[0];
    }

    public int size() {
        return efectivo;
    }

    public boolean isEmpty() {
        return efectivo == 0;
    }

    public void print() {
        for (int i = 0; i < efectivo; i++) {
            System.out.print(arreglo[i] + " ");
        }
        System.out.println();
    }
}
