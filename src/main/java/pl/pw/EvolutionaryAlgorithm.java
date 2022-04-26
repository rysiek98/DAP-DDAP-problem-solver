package pl.pw;

import com.google.common.collect.Lists;

import java.util.*;
import java.util.stream.Collectors;

public class EvolutionaryAlgorithm {

    protected Network network;

    private List<Chromosome> baseGeneration;
    private List<Chromosome> nextGeneration;

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
    private int numberOfCrossovers;

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
        this.nextGeneration = new ArrayList<>();
        this.maxComputationTime = 100;
        this.maxNumberOfGenerations = 100;
        this.maxNumberOfGenerationsWithNoImprovement = 10;
        this.maxNumberOfMutations = 10;
        this.numberOfCrossovers = 3;
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

        while (checkStopCriterion()) {


        }
    }

    public Chromosome computeDDAP() {

        Chromosome baseGenBestSolution = new Chromosome();
        Chromosome nextGenBestSolution = new Chromosome();

        endTime = System.currentTimeMillis() + maxComputationTime * 10;

        while (checkStopCriterion()) {
            System.out.println(currentGeneration);
            currentGeneration++;

            //maxTime w sec
            endTime = System.currentTimeMillis() + maxComputationTime * 10;

            //best solution
            baseGenBestSolution = findBestSolutionDDAP(baseGeneration);
            nextGeneration.add(baseGenBestSolution);

            //crossover
            for (int i = 0; i < numberOfCrossovers; i++) {
                crossover(i);
            }

            //mutation
            mutation();

            //next generation: best solution's cost
            nextGenBestSolution = findBestSolutionDDAP(nextGeneration);

            if (nextGenBestSolution.getCost() < baseGenBestSolution.getCost()) {
                currentGenerationsWithNoImprovement = 0;
            } else {
                currentGenerationsWithNoImprovement++;
            }

            baseGeneration = new ArrayList<>(nextGeneration);
            nextGeneration.clear();
        }

        System.out.print("Population after " + currentGeneration + " generations:");
        for (Chromosome c : baseGeneration) {
            System.out.print(c.getGens());
        }
        System.out.println();
        baseGeneration = fitnessFunction(baseGeneration);
        for (Chromosome c : baseGeneration) {
            System.out.print("cost: " + c.getCost() + " z: " + c.getZ() + "; ");
        }
        return nextGenBestSolution;
    }

    private Chromosome findBestSolutionDDAP(List<Chromosome> baseGeneration) {
        baseGeneration = fitnessFunction(baseGeneration);
        baseGeneration.sort(Comparator.comparing(Chromosome::getCost));
        return baseGeneration.get(0);
    }

    private void crossover(int round) {
        List<List<Integer>> gensA;
        List<List<Integer>> gensB;
        List<List<Integer>> childAGens = new ArrayList<>();
        List<List<Integer>> childBGens = new ArrayList<>();
        Chromosome parentA = baseGeneration.get(round);
        Chromosome parentB = baseGeneration.get(round + 1);
        int it = (int) Math.floor(parentA.getGens().size() / 2);
        gensA = parentA.getGens();
        gensB = parentB.getGens();

        for (int i = 0; i < it; i++) {
            childAGens.add(gensA.get(i));
            childBGens.add(gensB.get(i));
        }

        for (int i = it; i < gensA.size(); i++) {
            {
                childAGens.add(gensB.get(i));
                childBGens.add(gensA.get(i));
            }

            Chromosome childA = new Chromosome(childAGens);
            Chromosome childB = new Chromosome(childBGens);

            nextGeneration.add(childA);
            nextGeneration.add(childB);
        }
    }

    private void mutation() {
        if (Math.random() * 100 > 10) {
            List<Integer> tmpGen = new ArrayList<>();
            List<List<Integer>> gens = new ArrayList<>();
            int tmpValue = 0;
            int x = (int) Math.random() * baseGeneration.size();
            Chromosome mutant = baseGeneration.get(x);
            for (int i = 0; i < mutant.getGens().size(); i++) {
                tmpGen = new ArrayList<>();
                if (i % 2 == 0) {
                    for (int j = 0; j < mutant.getGens().get(i).size(); j++) {
                        if(mutant.getGens().get(i).get(j) > 0){
                            tmpGen.add(mutant.getGens().get(i).get(j)-1);
                            tmpValue++;
                        }else {
                            tmpGen.add(mutant.getGens().get(i).get(j)+tmpValue);
                            tmpValue = 0;
                        }
                    }
                    if(tmpValue != 0){
                        tmpGen.set(0,tmpValue+tmpGen.get(0));
                        tmpValue = 0;
                    }
                } else {
                    tmpGen = mutant.getGens().get(i);
                }
                gens.add(tmpGen);
            }
            mutant.setGens(gens);
            mutant = fitnessFunction(mutant);
            nextGeneration.add(mutant);
        }
    }

    public void generateStartPopulation() {
        List<List<List<Integer>>> allCombinations = new ArrayList<>();

        for (Demand d : network.getDemandList()) {
            allCombinations.add(getCombinations(d.getDemandVolume(), d.getNumberOfPaths()));
        }

        Chromosome chromosome;
        for (int i = 0; i < populationSize; i++) {

            chromosome = new Chromosome();
            List<List<Integer>> gens = new ArrayList<>();

            for (int j = 0; j < allCombinations.size(); j++) {
                int randomIndex = random.nextInt(allCombinations.get(j).size());
                gens.add(allCombinations.get(j).get(randomIndex));
            }
            chromosome.setGens(gens);
            if (baseGeneration.indexOf(chromosome) == -1) {
                baseGeneration.add(chromosome);
            } else {
                chromosome = null;
            }
        }

        System.out.println("Size: " + baseGeneration.size());
        System.out.print("Start population: ");
        for (Chromosome c : baseGeneration) {
            System.out.print(c.getGens());
        }
        System.out.println();
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

    public Chromosome fitnessFunction(Chromosome chromosomes) {
        return fitnessFunction(List.of(chromosomes)).get(0);
    }

    public List<Chromosome> fitnessFunction(List<Chromosome> chromosomes) {
        List<Double> solutionZValues = new ArrayList<>();
        double z = 0;
        double cost = 0;
        for (int i = 0; i < chromosomes.size(); i++) {
            cost = 0;
            z = 0;
            for (int demandId = 1; demandId <= network.getNumberOfDemands(); demandId++) {
                for (Path path : network.getDemandList().get(demandId - 1).getPathList()) {
                    int pathVolume = chromosomes.get(i).getGens().get(demandId - 1).get(path.getId() - 1);

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
            chromosomes.get(i).setCost(cost);
            chromosomes.get(i).setZ(z);
            solutionZValues.clear();
        }
        return chromosomes;
    }

    // returns true if the the algorithm should stop
    public boolean checkStopCriterion() {

        if (this.currentGeneration >= this.maxNumberOfGenerations) {
            return false;
        } else if (this.currentMutation >= this.maxNumberOfMutations) {
            return false;
        } else if (this.currentGenerationsWithNoImprovement >= this.maxNumberOfGenerationsWithNoImprovement) {
            return false;
        } else if (System.currentTimeMillis() >= this.endTime) {
            return false;
        } else {
            return true;
        }

    }
}
