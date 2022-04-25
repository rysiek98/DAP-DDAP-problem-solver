package pl.pw;

import com.google.common.collect.Lists;

import java.util.*;
import java.util.stream.Collectors;

public class EvolutionaryAlgorithm {

    protected Network network;

    private List<List<List<Integer>>> baseGeneration;
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
    private long endTime;
    private int maxNumberOfGenerationsWithNoImprovement;

    // current state
    private int currentGeneration;
    private int currentMutation;
    private int currentGenerationsWithNoImprovement;

    // constructor for development purpose only!! // TODO remove this constructor later XD (Zaloze sie że zostanie do końca xd)
    public EvolutionaryAlgorithm(Network network) {
        this.network = network;
        this.random = new Random(10);
        this.populationSize = 20;
        this.baseGeneration = new ArrayList<>();
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

    public void computeDAP() {

        while(checkStopCriterion()) {



        }
    }

    public void computeDDAP() {

        List<List<Integer>> bestSolution = new ArrayList<>();

        while(checkStopCriterion()) {

            currentGeneration++;

            //maxTime w sec
            endTime = System.currentTimeMillis() + maxComputationTime * 1000;

            // TODO funkcja znajdująca najlepsze rozwiązanie findBestSolution() może zwracać tylko jedno, nawet jeśli jest więcej
            // List<List<Integer>> bestSolution = findBestSolutionDDAP(baseGeneration);
            int baseGenMinCost = 2137; // TODO base generation: best solution's cost

            nextGeneration.add(bestSolution);

            // TODO crossover

            // TODO mutation

            int nextGenMinCost = 2137; // TODO next generation: best solution's cost

            if(nextGenMinCost < baseGenMinCost) { currentGenerationsWithNoImprovement = 0; }
            else { currentGenerationsWithNoImprovement++; }


            baseGeneration = nextGeneration;
            nextGeneration.clear();
        }
        // return bestSolution;
    }


    public void generateStartPopulation() {
        List<List<List<Integer>>> allCombinations = new ArrayList<>();

        for(Demand d : network.getDemandList()) {
            allCombinations.add(getCombinations(d.getDemandVolume(), d.getNumberOfPaths()));
        }

        for(int i = 0; i < populationSize; i++) {

            List<List<Integer>> chromosome = new ArrayList<>();

            for(int j = 0; j < allCombinations.size(); j++) {
                int randomIndex = random.nextInt(allCombinations.get(j).size());
                chromosome.add(allCombinations.get(j).get(randomIndex));
            }
            if(baseGeneration.indexOf(chromosome) == -1) {
                baseGeneration.add(chromosome);
            }
        }

        System.out.println("Size: " + baseGeneration.size());
        System.out.println("Start population: " + baseGeneration);
        System.out.println(fitnessFunction());
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

    public List<List<Double>> fitnessFunction() {
        List<List<Double>> quality = new ArrayList<>();
        List<Double> costList = new ArrayList<>();
        List<Double> zList = new ArrayList<>();
        List<Double> solutionZValues = new ArrayList<>();
        double z = 0;
        double cost = 0;
        for (int i = 0; i < baseGeneration.size(); i++) {
            cost = 0;
            z = 0;
            for (int demandId = 1; demandId <= network.getNumberOfDemands(); demandId++) {
                for (Path path : network.getDemandList().get(demandId - 1).getPathList()) {
                    int pathVolume = baseGeneration.get(i).get(demandId - 1).get(path.getId() - 1);

                    for (Link link : path.getLinkList()) {
                        for (Link netLink : network.getLinkList()) {
                            if (link.getId() == netLink.getId()) {
                                netLink.updateUsedLambdas(pathVolume);
                            }
                        }
                    }
                }
            }

            for (Link link : network.getLinkList()) {
                solutionZValues.add(link.getUsedLambdas() - link.countCe());
                cost += link.calculateCost();
                link.setUsedLambdas(0);
            }
            z = Collections.max(solutionZValues);
            costList.add(cost);
            zList.add(z);
            solutionZValues.clear();
        }
        quality.add(costList);
        quality.add(zList);
        
        return quality;
    }

    // returns true if the the algorithm should stop
    public boolean checkStopCriterion() {

        if (this.currentGeneration >= this.maxNumberOfGenerations) { return true; }
        else if (this.currentMutation >= this.maxNumberOfMutations) { return true; }
        else if (this.currentGenerationsWithNoImprovement >= this.maxNumberOfGenerationsWithNoImprovement) { return true; }
        else if (System.currentTimeMillis() >= this.endTime) { return true; }
        else { return false; }

    }
}
