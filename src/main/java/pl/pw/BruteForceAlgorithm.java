package pl.pw;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BruteForceAlgorithm {

    protected Network network;
    private List<List<List<Integer>>> solutions;

    public BruteForceAlgorithm(Network network) {
        this.network = network;
        solutions = new ArrayList<>();
        try {
            createAllSolutions();
        }
        catch (Exception e) {
            System.out.println("EXCEPTION: Too big network fo BFA");
            solutions.clear();
        }
    }

    public void computeDDAP() {

        if(!solutions.isEmpty()) {
            System.out.println("BRUTE FORCE DDAP");

            double minCost = Double.MAX_VALUE;
            List<List<List<Integer>>> bestSolutions = new ArrayList<>();

            for (List<List<Integer>> solution : solutions) {
                double cost = 0;

                for (Link link : network.getLinkList()) {
                    link.setUsedLambdas(0);
                }

                for (int demandId = 1; demandId <= network.getNumberOfDemands(); demandId++) {

                    for (Path path : network.getDemandList().get(demandId - 1).getPathList()) {

                        int pathVolume = solution.get(demandId - 1).get(path.getId() - 1);

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
                    cost += link.calculateCost();
                }

                if (cost == minCost) {
                    bestSolutions.add(solution);
                } else if (cost < minCost) {
                    minCost = cost;
                    bestSolutions.clear();
                    bestSolutions.add(solution);
                }

                // TODO write solution and cost to file
            }

            System.out.println("Number of best solutions: " + bestSolutions.size());
            System.out.println("List of best solutions:");
            for (List<List<Integer>> solution : bestSolutions) {
                System.out.println(solution);
            }
            System.out.println("Minimal cost: " + minCost);
        }
    }


    public void createAllSolutions() {

        List<List<List<Integer>>> allCombinations = new ArrayList<>();
        int resultNewton = 1;

        for(Demand d : network.getDemandList()) {
            allCombinations.add(getCombinations(d.getDemandVolume(), d.getNumberOfPaths()));

            long numberOfCombinations = calculateNewtonSymbol(d.getNumberOfPaths() + d.getDemandVolume() - 1, d.getDemandVolume());
            resultNewton *= numberOfCombinations;
        }

        solutions = Lists.cartesianProduct(allCombinations);

        System.out.println("BRUTE FORCE ALGORITHM: creating all possible solutions");
        System.out.println("Created number of solutions: " + solutions.size());
        System.out.println("Newton symbol result: " + resultNewton + "\n");
    }

    // returns the all possible combinations of setting demand values for the paths
    public List<List<Integer>> getCombinations(Integer demandValue, Integer numberOfPaths) {
        List<List<Integer>> lists = new ArrayList();
        List<Integer> list = new ArrayList();

        List<List<List<Integer>>> bigList = new ArrayList<>();

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

    private int calculateNewtonSymbol(int n, int k) {
        int result = 1;
        for (int i = 1; i <= k; i++)
            result = result * (n - i + 1) / i;
        return result;
    }

}
