/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author asala
 * @param <E>
 */

public class BinaryTree<E>{
    private NodeBinaryTree<E> root;
    
    public BinaryTree(){
        this.root = null;
    }
    
    public NodeBinaryTree<E> getRoot(){
        return root;
    }
    
    public BinaryTree(E e){
        this.root = new NodeBinaryTree<>(e);
    }
    
    public BinaryTree(NodeBinaryTree<E> root){
        this.root = root;
    }

    public boolean isEmpty(){
        return this.root == null;
    }
    
    public boolean isLeaf(){
        if(!this.isEmpty()){
            return this.root.getLeft() == null && this.root.getRight() == null;
        }
        return false;
    }

    public int countLeavesRecursive(){
        if (this.isEmpty()){
            return 0;
        } else if(this.isLeaf()){
            return 1;
        } else {
            int leftLeaves = 0;
            int rightLeaves = 0;
            
            if (this.root.getLeft() != null){
                leftLeaves = this.root.getLeft().countLeavesRecursive();
            }
            
            if (this.root.getRight() != null){
                rightLeaves = this.root.getRight().countLeavesRecursive();
            }
            
            return leftLeaves + rightLeaves;
        }
        
    }
    
    public int countLeavesIterative(){
        int leaves = 0;
        Stack<BinaryTree<E>> s = new Stack();
        
        if (!this.isEmpty()){
            s.push(this);
            
            while (!s.isEmpty()){
                BinaryTree<E> tree = s.pop();
                if (tree.isLeaf()){
                    leaves++;
                }
                if (tree.getRoot().getLeft() != null){
                    s.push(tree.getRoot().getLeft());
                }
                
                if (tree.getRoot().getRight() != null){
                    s.push(tree.getRoot().getRight());
                }                
                
            }
            
        }
        return leaves;
    }

    
    public int countNodesRecursive() {
        if (this.isEmpty()) {
            return 0;
        } else {
            int leftCount = 0;
            int rightCount = 0;

            if (this.root.getLeft() != null) {
                leftCount = this.root.getLeft().countNodesRecursive();
            }

            if (this.root.getRight() != null) {
                rightCount = this.root.getRight().countNodesRecursive();
            }

            return 1 + leftCount + rightCount; // 1 for the root node
        }
    }

    public int countNodesIterative() {
        int count = 0;
        Stack<BinaryTree<E>> s = new Stack<>();

        if (!this.isEmpty()) {
            s.push(this);

            while (!s.isEmpty()) {
                BinaryTree<E> tree = s.pop();
                count++;

                if (tree.getRoot().getLeft() != null) {
                    s.push(tree.getRoot().getLeft());
                }

                if (tree.getRoot().getRight() != null) {
                    s.push(tree.getRoot().getRight());
                }
            }
        }

        return count;
    }

    public int countDescendantsRecursive(){
        if(this.isEmpty()){
            return 0;
        } else if(this.isLeaf()){
            return 0;
        } else{
            int count = 0;
            if(this.root.getLeft() != null){
                count += 1 + this.root.getLeft().countDescendantsRecursive();
            }
            if(this.root.getRight() != null){
                count += 1 + this.root.getRight().countDescendantsRecursive();
            }
            return count;
        }
    }
    
    public int countDescendantsIterative(){
        int count = 0;
        Stack<BinaryTree<E>> s = new Stack();
        if(!this.isEmpty()){
            s.push(this);
            while(!s.isEmpty()){
                BinaryTree<E> tree = s.pop();
                if(tree.getRoot().getLeft()!=null){
                    s.push(tree.getRoot().getLeft());
                    count++;
                } if(tree.getRoot().getRight()!=null){
                    s.push(tree.getRoot().getRight());
                    count++;
                }                
            }
        }
        return count;
    }
    
    public NodeBinaryTree<E> findParentRecursive(NodeBinaryTree<E> nodo) {
        // Verificar si el árbol está vacío o el nodo es nulo
        if (this.isEmpty() || nodo == null) {
            return null;
        }

        // Verificar si el nodo actual es el padre del nodo dado
        if ((this.root.getLeft() != null && this.root.getLeft().getRoot() == nodo) ||
            (this.root.getRight() != null && this.root.getRight().getRoot() == nodo)) {
            return this.root;
        }

        // Buscar en el subárbol izquierdo
        NodeBinaryTree<E> parent = null;
        if (this.root.getLeft() != null) {
            parent = this.root.getLeft().findParentRecursive(nodo);
        }

        // Si no se encontró en el subárbol izquierdo, buscar en el subárbol derecho
        if (parent == null && this.root.getRight() != null) {
            parent = this.root.getRight().findParentRecursive(nodo);
        }

        return parent; // Retorna el padre encontrado o null si no se encuentra
    }


    public NodeBinaryTree<E> findParentIterative(NodeBinaryTree<E> nodo) {
        // Verificar si el árbol está vacío o el nodo es nulo
        if (this.isEmpty() || nodo == null) {
            return null;
        }

        Queue<BinaryTree<E>> queue = new LinkedList<>();
        queue.add(this);

        while (!queue.isEmpty()) {
            BinaryTree<E> currentTree = queue.poll();
            NodeBinaryTree<E> currentNode = currentTree.getRoot();

            // Verificar si el nodo actual es el padre del nodo dado
            if ((currentNode.getLeft() != null && currentNode.getLeft().getRoot() == nodo) ||
                (currentNode.getRight() != null && currentNode.getRight().getRoot() == nodo)) {
                return currentNode;
            }

            // Agregar los hijos a la cola para seguir buscando
            if (currentNode.getLeft() != null) {
                queue.add(currentNode.getLeft());
            }
            if (currentNode.getRight() != null) {
                queue.add(currentNode.getRight());
            }
        }

        return null; // Retorna null si no se encuentra el nodo padre
    }

   
    public int countLevelsRecursive() {
        if (this.isEmpty()) {
            return 0;
        } else {
            int leftLevel = 0;
            int rightLevel = 0;

            if (this.root.getLeft() != null) {
                leftLevel = this.root.getLeft().countLevelsRecursive();
            }

            if (this.root.getRight() != null) {
                rightLevel = this.root.getRight().countLevelsRecursive();
            }

            return 1 + Math.max(leftLevel, rightLevel);
        }
    }
    
    public int countLevelsIterative() {
        if (this.isEmpty()) {
            return 0;
        }

        Queue<BinaryTree<E>> queue = new LinkedList<>();
        queue.add(this);
        int levels = 0;

        while (!queue.isEmpty()) {
            int levelSize = queue.size(); // Número de nodos en el nivel actual
            for (int i = 0; i < levelSize; i++) {
                BinaryTree<E> currentTree = queue.poll();
                if (currentTree.getRoot().getLeft() != null) {
                    queue.add(currentTree.getRoot().getLeft());
                }
                if (currentTree.getRoot().getRight() != null) {
                    queue.add(currentTree.getRoot().getRight());
                }
            }
            levels++;
        }

        return levels;
    }
    
    public boolean isLeftyRecursive() {
        if (this.isEmpty() || this.isLeaf()) {
            return true;
        } else {
            int leftDescendants = 0;
            int totalDescendants = this.countDescendantsRecursive();

            
            if (this.root.getLeft() != null) {
                leftDescendants = 1 + this.root.getLeft().countDescendantsRecursive();
            }

            if (leftDescendants < Math.round((totalDescendants + 1) / 2.0f)) {
                return false;
            }

            if (this.root.getLeft() != null && !this.root.getLeft().isLeftyRecursive()) {
                return false;
            }
            if (this.root.getRight() != null && !this.root.getRight().isLeftyRecursive()) {
                return false;
            }

            return true;
        }
    }
    
    public boolean isLeftyIterative() {
        if (this.isEmpty() || this.isLeaf()) {
            return true;
        }

        Stack<BinaryTree<E>> s = new Stack<>();
        s.push(this);

        while (!s.isEmpty()) {
            BinaryTree<E> currentTree = s.pop();
            int totalDescendants = currentTree.countDescendantsIterative();
            int leftDescendants = 0;

            if (currentTree.getRoot().getLeft() != null) {
                leftDescendants = 1 + currentTree.getRoot().getLeft().countDescendantsIterative();
                s.push(currentTree.getRoot().getLeft());
            }

            if (currentTree.getRoot().getRight() != null) {
                s.push(currentTree.getRoot().getRight());
            }

            if (leftDescendants < (totalDescendants + 1) / 2) {
                return false;
            }
        }

        return true;
    }

    public boolean isIdenticalRecursive(BinaryTree<E> tree) {
        if (this.isEmpty() && (tree == null || tree.isEmpty())) {
            return true;
        } else if (this.isEmpty() || tree == null || tree.isEmpty()) {
            return false;
        }

        if (!this.root.getContent().equals(tree.getRoot().getContent())) {
            return false;
        }

        boolean leftIdentical = true;
        boolean rightIdentical = true;

        if (this.root.getLeft() != null) {
            leftIdentical = this.root.getLeft().isIdenticalRecursive(tree.root.getLeft());
        } else if (tree.root.getLeft() != null) {
            leftIdentical = false;
        }

        if (this.root.getRight() != null) {
            rightIdentical = this.root.getRight().isIdenticalRecursive(tree.root.getRight());
        } else if (tree.root.getRight() != null) {
            rightIdentical = false;
        }

        return leftIdentical && rightIdentical;
    }

    public boolean isIdenticalIterative(BinaryTree<E> tree){
        if (this.isEmpty() && (tree == null || tree.isEmpty())) {
            return true;
        } else if (this.isEmpty() || tree == null || tree.isEmpty()) {
            return false;
        } else{
            Stack<BinaryTree<E>> stack1 = new Stack<>();
            Stack<BinaryTree<E>> stack2 = new Stack<>();
            stack1.push(this);
            stack2.push(tree);

            while (!stack1.isEmpty() && !stack2.isEmpty()) {
                BinaryTree<E> currentTree1 = stack1.pop();
                BinaryTree<E> currentTree2 = stack2.pop();

                // Compara los contenidos de las raíces actuales
                if (!currentTree1.getRoot().getContent().equals(currentTree2.getRoot().getContent())) {
                    return false;
                }

                // Añade los hijos izquierdos a las pilas
                if (currentTree1.getRoot().getLeft() != null && currentTree2.getRoot().getLeft() != null) {
                    stack1.push(currentTree1.getRoot().getLeft());
                    stack2.push(currentTree2.getRoot().getLeft());
                } else if (currentTree1.getRoot().getLeft() != null || currentTree2.getRoot().getLeft() != null) {
                    return false; // Uno de los árboles tiene un hijo izquierdo mientras que el otro no
                }

                // Añade los hijos derechos a las pilas
                if (currentTree1.getRoot().getRight() != null && currentTree2.getRoot().getRight() != null) {
                    stack1.push(currentTree1.getRoot().getRight());
                    stack2.push(currentTree2.getRoot().getRight());
                } else if (currentTree1.getRoot().getRight() != null || currentTree2.getRoot().getRight() != null) {
                    return false; // Uno de los árboles tiene un hijo derecho mientras que el otro no
                }
            }

            // Ambas pilas deberían estar vacíos si los árboles son idénticos
            return stack1.isEmpty() && stack2.isEmpty();
        }
    }


    public int countNodesWithOnlyChildRecursive(){
        if(!this.isEmpty() && !this.isLeaf()){
            int nodos = 0;
            if((this.root.getLeft() == null) ^ (this.root.getRight() == null)) {
                nodos++;
            }
            if(this.root.getLeft() != null){
                nodos += this.root.getLeft().countNodesWithOnlyChildRecursive();
            }
            if(this.root.getRight() != null){
                nodos += this.root.getRight().countNodesWithOnlyChildRecursive();
            }
            return nodos;
        }
        return 0;
    }
    
    public int countNodesWithOnlyChildIterative(){
        if(!this.isEmpty()){
            Stack<BinaryTree<E>> s = new Stack<>();
            int nodos=0;
            s.push(this);
            while(!s.isEmpty()){
                BinaryTree<E> tree = s.pop();
                if((tree.getRoot().getLeft()==null) ^ (tree.getRoot().getRight()==null)){
                    nodos++;
                }
                if(tree.getRoot().getLeft()!=null){
                    s.push(tree.getRoot().getLeft());
                }
                if(tree.getRoot().getRight()!=null){
                    s.push(tree.getRoot().getRight());
                }
            }
            return nodos;
        }
        return 0;
    }

    public boolean isHeightBalancedRecursive(){
      if (this.isEmpty()) {
          return true; // Retorna verdadero ya que un árbol vacío está balanceado
      }

      // Aplicamos recursividad para comprobar que los arboles izquierdo y derecho estan balanceados
      boolean leftBalanced = (this.root.getLeft() == null) || this.root.getLeft().isHeightBalancedRecursive();
      boolean rightBalanced = (this.root.getRight() == null) || this.root.getRight().isHeightBalancedRecursive();

      int levelLeft = 0;
      int levelRight = 0;

      if (this.root.getLeft() != null) {
          levelLeft = this.root.getLeft().countLevelsIterative();
      }

      if (this.root.getRight() != null) {
          levelRight = this.root.getRight().countLevelsIterative();
      }

      // Verificamos si los niveles son diferentes
      if (Math.abs(levelLeft - levelRight) > 1) {
          return false;
      }
      return leftBalanced && rightBalanced;
    }

    public boolean isHeightBalancedIterative(){
        if(!this.isEmpty()){
            Queue<BinaryTree<E>> copia = new LinkedList<>();
            copia.offer(this);
            while(!copia.isEmpty()){
               BinaryTree<E> tree = copia.poll();
               int levelLeft=0;
               int levelRight=0;
               if(tree.getRoot().getLeft()!=null){
                   levelLeft = tree.getRoot().getLeft().countLevelsIterative();
                   copia.offer(tree.getRoot().getLeft());
               }
               if(tree.getRoot().getRight()!=null){
                   levelRight = tree.getRoot().getRight().countLevelsIterative();
                   copia.offer(tree.getRoot().getRight());
               }
               if (Math.abs(levelLeft - levelRight) > 1){
                   return false;
               }
            }
            return true;
        }
        return true;
    }    
    
    
}
