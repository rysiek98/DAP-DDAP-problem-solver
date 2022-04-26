package pl.pw;

import java.util.List;

public class Chromosome {
    private double cost;
    private double z;
    private List<List<Integer>> gens;

    public Chromosome(List<List<Integer>> gens) {
        this.gens = gens;
        this.cost = 0.0;
        this.z = 0.0;
    }

    public Chromosome() {

        this.cost = 0.0;
        this.z = 0.0;
    }

    public double getCost() {
        return cost;
    }

    public double getZ() {
        return z;
    }

    public List<List<Integer>> getGens() {
        return gens;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setGens(List<List<Integer>> gens) {
        this.gens = gens;
    }


}
