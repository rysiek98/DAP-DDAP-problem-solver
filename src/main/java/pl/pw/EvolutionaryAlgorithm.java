package pl.pw;

import com.google.common.collect.Lists;

import java.util.*;
import java.util.stream.Collectors;

public class EvolutionaryAlgorithm {

    protected Network network;

    private List<Chromosome> startPopulation;
    private List<Chromosome> baseGeneration;
    private List<Chromosome> nextGeneration;
    private List<Chromosome> bestSolutions;
    private Random generator;

    // input data
    private int populationSize;
    private float crossoverProbability;
    private float mutationProbability;
    private int seed;

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

    public EvolutionaryAlgorithm(Network network, int populationSize, float crossoverProbability,
                                 float mutationProbability, int seed, int maxNumberOfGenerations,
                                 int maxNumberOfMutations, int maxComputationTime,
                                 int maxNumberOfGenerationsWithNoImprovement) {
        this.network = network;
        this.populationSize = populationSize;
        this.crossoverProbability = crossoverProbability;
        this.mutationProbability = mutationProbability;
        this.seed = seed;
        this.maxNumberOfGenerations = maxNumberOfGenerations;
        this.maxNumberOfMutations = maxNumberOfMutations;
        this.maxComputationTime = maxComputationTime;
        this.maxNumberOfGenerationsWithNoImprovement = maxNumberOfGenerationsWithNoImprovement;
        this.currentGeneration = 0;
        this.currentMutation = 0;
        this.currentGenerationsWithNoImprovement = 0;
        this.generator = new Random(seed);
        this.startPopulation = new ArrayList<>();
        this.baseGeneration = new ArrayList<>();
        this.nextGeneration = new ArrayList<>();
        this.bestSolutions = new ArrayList<>();
        generateStartPopulation();
    }

    public long computeDAP() {

        long executionTime = System.currentTimeMillis();
        Chromosome baseGenBestSolution = new Chromosome();
        Chromosome nextGenBestSolution = new Chromosome();

        endTime = System.currentTimeMillis() + maxComputationTime * 1000;
        currentMutation = 0;
        currentGeneration = 0;
        currentGenerationsWithNoImprovement = 0;
        baseGeneration = new ArrayList<>(startPopulation);

        while (checkStopCriterion()) {

            nextGeneration.clear();
            currentGeneration++;

            // best solution
            baseGenBestSolution = findBestSolutionDAP(baseGeneration);
            nextGeneration.add(baseGenBestSolution);
            bestSolutions.add(baseGenBestSolution);

            // crossover
            int numberOfIterations;

            if (populationSize % 2 == 0) {
                numberOfIterations = populationSize;
            } else {
                numberOfIterations = populationSize - 1;
            }

            for (int i = 0; i < numberOfIterations; i += 2) {

                if (generator.nextInt(100) + 1 <= (double) crossoverProbability) {
                    crossover(baseGeneration.get(i), baseGeneration.get(i + 1));
                } else {
                    nextGeneration.add(baseGeneration.get(i));
                    nextGeneration.add(baseGeneration.get(i + 1));
                }
            }

            if (nextGeneration.size() > populationSize) {
                nextGeneration.remove(findWorstSolutionDAP(nextGeneration));
            }

            // mutation
            mutation();

            // next generation: best solution's cost
            nextGenBestSolution = findBestSolutionDAP(nextGeneration);

            if (nextGenBestSolution.getCost() < baseGenBestSolution.getCost()) {
                currentGenerationsWithNoImprovement = 0;
            } else {
                currentGenerationsWithNoImprovement++;
            }

            baseGeneration.clear();
            baseGeneration = new ArrayList<>(nextGeneration);
        }

        bestSolutions.add(nextGenBestSolution);
        executionTime = System.currentTimeMillis() - executionTime;

        Writer writer = new Writer();
        writer.write(network, nextGenBestSolution.getGens(), "Solution_EA_DAP");

        printBestSolutions(true);

        return executionTime;
    }

    public long computeDDAP() {
        long executionTime = System.currentTimeMillis();

        Chromosome baseGenBestSolution = new Chromosome();
        Chromosome nextGenBestSolution = new Chromosome();

        endTime = System.currentTimeMillis() + maxComputationTime * 1000;
        currentMutation = 0;
        currentGeneration = 0;
        currentGenerationsWithNoImprovement = 0;
        baseGeneration = new ArrayList<>(startPopulation);

        while (checkStopCriterion()) {

            nextGeneration.clear();
            currentGeneration++;

            // best solution
            baseGenBestSolution = findBestSolutionDDAP(baseGeneration);
            nextGeneration.add(baseGenBestSolution);
            bestSolutions.add(baseGenBestSolution);

            // crossover
            int numberOfIterations;

            if (populationSize % 2 == 0) {
                numberOfIterations = populationSize;
            } else {
                numberOfIterations = populationSize - 1;
            }

            for (int i = 0; i < numberOfIterations; i += 2) {

                if (generator.nextInt(100) + 1 <= (double) crossoverProbability) {
                    crossover(baseGeneration.get(i), baseGeneration.get(i + 1));
                } else {
                    nextGeneration.add(baseGeneration.get(i));
                    nextGeneration.add(baseGeneration.get(i + 1));
                }
            }

            if (nextGeneration.size() > populationSize) {
                nextGeneration.remove(findWorstSolutionDDAP(nextGeneration));
            }

            // mutation
            mutation();

            // next generation: best solution's cost
            nextGenBestSolution = findBestSolutionDDAP(nextGeneration);

            if (nextGenBestSolution.getCost() < baseGenBestSolution.getCost()) {
                currentGenerationsWithNoImprovement = 0;
            } else {
                currentGenerationsWithNoImprovement++;
            }

            baseGeneration.clear();
            baseGeneration = new ArrayList<>(nextGeneration);
        }

        bestSolutions.add(nextGenBestSolution);
        executionTime = System.currentTimeMillis() - executionTime;

        Writer writer = new Writer();
        writer.write(network, nextGenBestSolution.getGens(), "Solution_EA_DDAP");

        printBestSolutions(false);

        return executionTime;
    }

    private Chromosome findBestSolutionDDAP(List<Chromosome> generation) {
        generation = fitnessFunction(generation);
        generation.sort(Comparator.comparing(Chromosome::getCost));
        return generation.get(0);
    }

    private Chromosome findWorstSolutionDAP(List<Chromosome> generation) {
        generation = fitnessFunction(generation);
        generation.sort(Comparator.comparing(Chromosome::getZ));
        return generation.get(generation.size() - 1);
    }

    private Chromosome findBestSolutionDAP(List<Chromosome> generation) {
        generation = fitnessFunction(generation);
        generation.sort(Comparator.comparing(Chromosome::getZ));
        return generation.get(0);
    }

    private Chromosome findWorstSolutionDDAP(List<Chromosome> generation) {
        generation = fitnessFunction(generation);
        generation.sort(Comparator.comparing(Chromosome::getCost));
        return generation.get(generation.size() - 1);
    }

    private void crossover(Chromosome parentA, Chromosome parentB) {

        List<List<Integer>> gensA = new ArrayList<>();
        List<List<Integer>> gensB = new ArrayList<>();
        List<List<Integer>> childAGens = new ArrayList<>();
        List<List<Integer>> childBGens = new ArrayList<>();

        int it = (int) Math.floor(parentA.getGens().size() / 2);
        gensA = parentA.getGens();
        gensB = parentB.getGens();

        for (int i = 0; i < it; i++) {
            childAGens.add(gensA.get(i));
            childBGens.add(gensB.get(i));
        }

        Chromosome childA = null;
        Chromosome childB = null;

        for (int i = it; i < gensA.size(); i++) {
            {
                childAGens.add(gensB.get(i));
                childBGens.add(gensA.get(i));
            }

            childA = new Chromosome(childAGens);
            childB = new Chromosome(childBGens);
        }
        nextGeneration.add(childA);
        nextGeneration.add(childB);
    }

    private void mutation() {

        List<Chromosome> tmpGeneration = new ArrayList<>();

        for (int k = 0; k < populationSize; k++) {

            if (generator.nextInt(100) + 1 <= (double) mutationProbability) {

                currentMutation++;

                List<Integer> tmpGen = new ArrayList<>();
                List<List<Integer>> gens = new ArrayList<>();
                int tmpValue = 0;
                Chromosome mutant = nextGeneration.get(k);
                for (int i = 0; i < mutant.getGens().size(); i++) {
                    tmpGen = new ArrayList<>();
                    if (i % 2 == 0) {
                        for (int j = 0; j < mutant.getGens().get(i).size(); j++) {
                            if (mutant.getGens().get(i).get(j) > 0) {
                                tmpGen.add(mutant.getGens().get(i).get(j) - 1);
                                tmpValue++;
                            } else {
                                tmpGen.add(mutant.getGens().get(i).get(j) + tmpValue);
                                tmpValue = 0;
                            }
                        }
                        if (tmpValue != 0) {
                            tmpGen.set(0, tmpValue + tmpGen.get(0));
                            tmpValue = 0;
                        }
                    } else {
                        tmpGen = mutant.getGens().get(i);
                    }
                    gens.add(tmpGen);
                }
                mutant.setGens(gens);
                mutant = fitnessFunction(mutant);
                tmpGeneration.add(mutant);
            } else {
                tmpGeneration.add(nextGeneration.get(k));
            }
        }
        nextGeneration.clear();
        nextGeneration = new ArrayList<>(tmpGeneration);
    }

    public void generateStartPopulation() {
        List<List<List<Integer>>> allCombinations = new ArrayList<>();

        for (Demand d : network.getDemandList()) {
            allCombinations.add(getCombinations(d.getDemandVolume(), d.getNumberOfPaths()));
        }

        Chromosome chromosome;
        while (startPopulation.size() < populationSize) {

            chromosome = new Chromosome();
            List<List<Integer>> gens = new ArrayList<>();

            for (int j = 0; j < allCombinations.size(); j++) {
                int randomIndex = generator.nextInt(allCombinations.get(j).size());
                gens.add(allCombinations.get(j).get(randomIndex));
            }
            chromosome.setGens(gens);
            startPopulation.add(chromosome);
        }

        System.out.println("Size: " + startPopulation.size());
        System.out.print("Start population: ");
        for (Chromosome c : startPopulation) {
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

    public void printBestSolutions(boolean ifDAP) {
        for (int i = 0; i < bestSolutions.size(); i++) {
            System.out.println("Best solution in genetrion " + i);
            if (ifDAP == true) {
                System.out.println("z = " + bestSolutions.get(i).getZ());
            } else {
                System.out.println("Cost = " + bestSolutions.get(i).getCost());
            }
            System.out.println(bestSolutions.get(i).getGens());
        }
    }

    // returns true if the the algorithm should stop
    public boolean checkStopCriterion() {

        if (this.currentGeneration >= this.maxNumberOfGenerations) {
            System.out.println("\nStop criterion: max number of generations\n");
            return false;
        } else if (this.currentMutation >= this.maxNumberOfMutations) {
            System.out.println("\nStop criterion: max number of mutations\n");
            return false;
        } else if (this.currentGenerationsWithNoImprovement >= this.maxNumberOfGenerationsWithNoImprovement) {
            System.out.println("\nStop criterion: max number of generations with no improvement\n");
            return false;
        } else if (System.currentTimeMillis() >= this.endTime) {
            System.out.println("\nStop criterion: time limit\n");
            return false;
        } else {
            return true;
        }
    }
}
