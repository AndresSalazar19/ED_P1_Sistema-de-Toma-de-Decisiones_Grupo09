/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tda;

/**
 *
 * @author asala
 */
public class Node {
        protected String data;
        protected Node prev;
        protected Node next;

        Node(String data) {
            this.data = data;
            this.prev = this.next = this;
        }
        
        public String getContent(){
            return data;
        }
    }