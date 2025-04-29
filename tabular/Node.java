package tabular;
/*
 * Author:
 * Date: 4/29/2025
 * Purpose:  node.
 * This class is used to represent a node in the decision tree.
 * It is used to store the feature and the threshold (likely-hood to ... i forget, i'm eepy) for the node.
 * note: <ArrayList>Node will be used in the Model class to store the model tree.
 */

public class Node {
    private String feature;
    private double threshold;
    private Node left;
    private Node right;

    public Node(String feature, double threshold) {
        this.feature = feature;
        this.threshold = threshold;
        this.left = null;
        this.right = null;
    }

    public String getFeature() {
        return feature;
    }

    public double getThreshold() {
        return threshold;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }
}