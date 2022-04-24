package pl.pw;

public class Link {

    private int id;
    private Node startNode;
    private Node endNode;
    private int numberOfFibre;
    private double cost;
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

    public double getCost() {
        return cost;
    }

    public int getLambdas() {
        return lambdas;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setLambdas(int lambdas) {
        this.lambdas = lambdas;
    }

    public int getUsedLambdas() { return usedLambdas; }

    public void setUsedLambdas(int usedLambdas) { this.usedLambdas = usedLambdas; }

    public  void updateUsedLambdas(int usedLambdas) { this.usedLambdas = this.usedLambdas + usedLambdas; }

    public double calculateCost()
    {
        double usdeFibrePairs = Math.ceil(Double.valueOf(this.usedLambdas)/Double.valueOf(this.lambdas));   // TODO nie wiem czy tu dzielić przez 2, bo na slajdach jest fibre pair of cables
        return usdeFibrePairs * cost;
    }

    public double countCe() {
        return lambdas * numberOfFibre;
    }

}
