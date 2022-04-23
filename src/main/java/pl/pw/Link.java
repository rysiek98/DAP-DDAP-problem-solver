package pl.pw;

public class Link {

    private int id;
    private Node startNode;
    private Node endNode;
    private int numberOfFibre;
    private float cost;
    private int lambdas;

    public Link(int id) {
        this.id = id;
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

    public int getNumberOfFibre() {
        return numberOfFibre;
    }

    public void setNumberOfFibre(int numberOfFibre) {
        this.numberOfFibre = numberOfFibre;
    }

    public float getCost() {
        return cost;
    }

    public int getLambdas() {
        return lambdas;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public void setLambdas(int lambdas) {
        this.lambdas = lambdas;
    }
}
