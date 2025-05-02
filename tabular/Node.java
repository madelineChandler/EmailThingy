package tabular;
/*
 * Author: Madeline Chandler
 * Date: 4/29/2025
 * Purpose:  node.
 * This class is used to represent a node in the decision tree.
 */

public class Node {
    private String feature;
    private double threshold;
    private Node left;
    private Node right;
    private double impurity = 1.0;
    private String classification = null;

    public Node() {
        this.feature = null;
        this.threshold = 0.0;
        this.left = null;
        this.right = null;
    }

    public Node(String feature, double threshold) {
        this.feature = feature;
        this.threshold = threshold;
        this.left = null;
        this.right = null;
    }

    public Node predict(String email) {
        if (this.feature.equals("length")) {
            if (email.length() < this.threshold)
                return left;
            return right;
        } else if (this.feature.equals("avgWordLength")) {
            if (email.length() < this.threshold)
                return left;
            return right;
        }
        return null;
    }

    public String getFeature() {
        return feature;
    }

    public double getThreshold() {
        return threshold;
    }

    public double getImpurity() {
        return impurity;
    }

    public String getClassification() {
        return this.classification;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    /* @return true if node is empty - meaning it is the final (decision) node */
    public boolean isLeaf() {
        return (left==null && right==null);
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public void setImpurity(double impurity) {
        this.impurity = impurity;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public String toString() {
        if (feature.equalsIgnoreCase("length"))
            return "if " + feature + " < " + threshold;
        return "empty";
    }
}