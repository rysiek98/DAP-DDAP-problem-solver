package main.java.pl.pw;

public class Link {

    private int id;
    private Node startNode;
    private Node endNode;
    private int numberOfModules;

    public Link(int id, Node startNode, Node endNode, int numberOfModules) {
        this.id = id;
        this.startNode = startNode;
        this.endNode = endNode;
        this.numberOfModules = numberOfModules;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Node getStartNode() {
        return startNode;
    }

    public void setStartNode(Node startNode) {
        this.startNode = startNode;
    }

    public Node getEndNode() {
        return endNode;
    }

    public void setEndNode(Node endNode) {
        this.endNode = endNode;
    }

    public int getNumberOfModules() {
        return numberOfModules;
    }

    public void setNumberOfModules(int numberOfModules) {
        this.numberOfModules = numberOfModules;
    }
}
