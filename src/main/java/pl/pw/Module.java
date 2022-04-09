package main.java.pl.pw;

public class Module {

    private float cost;
    private int numberOfLambdas;

    public Module(int cost, int numberOfLambdas) {
        this.cost = cost;
        this.numberOfLambdas = numberOfLambdas;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public int getNumberOfLambdas() {
        return numberOfLambdas;
    }

    public void setNumberOfLambdas(int numberOfLambdas) {
        this.numberOfLambdas = numberOfLambdas;
    }
}
