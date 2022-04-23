package pl.pw;

public class Link {

    private int id;
    private Node startNode;
    private Node endNode;
    private int numberOfFibre;
    private float cost;
    private int lambdas;
    private  int usedLambdas;

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

    public int getUsedLambdas() { return usedLambdas; }

    public void setUsedLambdas(int usedLambdas) { this.usedLambdas = usedLambdas; }

    public  void updateUsedLambdas(int usedLambdas) { this.usedLambdas += usedLambdas; }

    public float calculateCost()
    {
        float usdeFibrePairs = usedLambdas/lambdas/2;   // TODO nie wiem czy tu dzielić przez 2, bo na slajdach jest fibre pair of cables
        return usdeFibrePairs * cost;
    }

}
