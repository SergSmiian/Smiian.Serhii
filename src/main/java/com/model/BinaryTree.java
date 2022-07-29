package com.model;


import com.model.vehicle.Vehicle;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;

public class BinaryTree<T extends Vehicle> {
    private Comparator<Vehicle> comparator;
    private Node<T> root;
    private int size;
    public BinaryTree(Comparator<Vehicle> comparator) {
        this.comparator = comparator;
    }

    public void add(T vehicle) {
        if (vehicle == null){
            throw new IllegalArgumentException("vehicle = null");
        }
        if (root == null) {
            root = new Node<T>(vehicle);
            size++;
            return;
        }
        doInsert(root, vehicle);
        size++;
    }
    public int getSize(){
        return size;
    }

    private Node<T> doInsert(Node<T> node, T vehicle) {
        if (node == null) {
            return new Node<T> (vehicle);
        }
        if (comparator.compare(node.vehicle, vehicle) > 0) {
            node.left = doInsert(node.left, vehicle);
        } else {
            node.right = doInsert(node.right, vehicle);
        }
        return node;
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(root);
        printFormat(root,  builder);
        return builder.toString();
    }
    private void printFormat(Node<T> node, StringBuilder builder) {
        if (node == null) {
            return ;
        }
        if(node.left != null){
            builder.append(node.left);
            printFormat(node.left, builder);
        }
        if(node.right != null){
            builder.append(node.right);
            printFormat(node.right, builder);
        }
    }
    public String getTreePresentation(){
        return root.getNodePresentation();
    }

    // Добавить возможность подсчета стоимости правой и левой ветки нод относительно корня
    public BigDecimal sumPrice (boolean leftPart){
        if (root == null){
            return BigDecimal.ZERO;
        }
       if (leftPart){
           return getPrice(root.left);
       }
       return getPrice(root.right);
    }
    private BigDecimal getPrice (Node<T> node){
        if(node == null){
            return BigDecimal.ZERO;
        }
        return node.vehicle.getPrice().add(getPrice(node.left)).add(getPrice(node.right));

    }
    private static class Node<T extends Vehicle> {
        T vehicle;
        BinaryTree.Node<T> left;
        BinaryTree.Node<T> right;

        Node(T vehicle) {
            this.vehicle = vehicle;
        }

        public String getNodePresentation() {
            StringBuilder builder = new StringBuilder();
            getNodePresentation("", this, false, builder);
            return builder.toString();
        }

        public void getNodePresentation(String prefix, Node<T> n, boolean isLeft, StringBuilder builder) {
            if (n != null) {
                builder.append (prefix + (isLeft ? "|-- " : "\\-- ") +
                        n.vehicle.getModel()+ " " +
                        n.vehicle.getPrice().setScale(2, RoundingMode.HALF_UP)).append("\n");
                getNodePresentation(prefix + (isLeft ? "|   " : "    "), n.left, true, builder);
                getNodePresentation(prefix + (isLeft ? "|   " : "    "), n.right, false, builder);
            }
        }
        @Override
        public String toString() {
            return "VehicleNode {" + "vehicle=" + vehicle + '\n';
        }
    }
}
