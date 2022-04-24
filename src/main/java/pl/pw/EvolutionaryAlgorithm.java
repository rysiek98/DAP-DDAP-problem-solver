package pl.pw;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class EvolutionaryAlgorithm {

    protected Network network;

    private List<List<List<Integer>>> startPopulation;
    private List<List<List<Integer>>> nextGeneration;

    // input data
    private int populationSize;
    private float crossoverProbability;
    private float mutationProbability;
    private Random random;

    // computation limits
    private int maxNumberOfGenerations;
    private int maxNumberOfMutations;
    private int maxComputationTime;
    private int maxNumberOfGenerationsWithNoImprovement;

    // current state
    private int currentGeneration;
    private int currentMutation;
    private int currentGenerationsWithNoImprovement;

    // constructor for development purpose only!! // TODO remove this constructor later XD
    public EvolutionaryAlgorithm(Network network) {
        this.network = network;
        this.random = new Random(10);
        this.populationSize = 20;
    }

    public EvolutionaryAlgorithm(Network network, int populationSize, float crossoverProbability,
                                 float mutationProbability, int seed, int maxNumberOfGenerations,
                                 int maxNumberOfMutations, int maxComputationTime,
                                 int maxNumberOfGenerationsWithNoImprovement) {
        this.network = network;
        this.populationSize = populationSize;
        this.crossoverProbability = crossoverProbability;
        this.mutationProbability = mutationProbability;
        this.random = new Random(seed);
        this.maxNumberOfGenerations = maxNumberOfGenerations;
        this.maxNumberOfMutations = maxNumberOfMutations;
        this.maxComputationTime = maxComputationTime;
        this.maxNumberOfGenerationsWithNoImprovement = maxNumberOfGenerationsWithNoImprovement;
        this.currentGeneration = 0;
        this.currentMutation = 0;
        this.currentGenerationsWithNoImprovement = 0;
    }

    public void generateStartPopulation() {
        List<List<List<Integer>>> allCombinations = new ArrayList<>();
        List<List<List<Integer>>> startPopulation = new ArrayList<>();

        for(Demand d : network.getDemandList()) {
            allCombinations.add(getCombinations(d.getDemandVolume(), d.getNumberOfPaths()));
        }

        for(int i = 0; i < populationSize; i++) {

            List<List<Integer>> chromosome = new ArrayList<>();

            for(int j = 0; j < allCombinations.size(); j++) {
                int randomIndex = random.nextInt(allCombinations.get(j).size());
                chromosome.add(allCombinations.get(j).get(randomIndex));
            }
            startPopulation.add(chromosome);
        }

        System.out.println("Size: " + startPopulation.size());
        System.out.println("Start population: " + startPopulation);
    }

    public List<List<Integer>> getCombinations(Integer demandValue, Integer numberOfPaths) {
        List<List<Integer>> lists = new ArrayList();
        List<Integer> list = new ArrayList();

        for (int i = 0; i <= demandValue; i++) {
            list.add(i);
        }

        for (int i = 0; i < numberOfPaths; i++) {
            lists.add(list);
        }

        return Lists.cartesianProduct(lists).stream()
                .filter(product -> demandValue.equals(product.stream().mapToInt(Integer::intValue).sum()))
                .collect(Collectors.toList());
    }
}
