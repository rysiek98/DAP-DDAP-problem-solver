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
    }

    public void computeDDAP() {

        float minCost = Float.MAX_VALUE;
        List<List<Integer>> bestSolution = new ArrayList<>();

        for(List<List<Integer>> solution : solutions) {
            float cost = 0;
            boolean newSolution = true;

            for(int demandId = 1; demandId <= network.getNumberOfDemands(); demandId++) {

                for(Path path : network.getDemandList().get(demandId -1).getPathList()) {

                    for(Link link : path.getLinkList()) {

                        if(newSolution) {
                            link.setUsedLambdas(0);
                            newSolution = false;
                        }
                        link.updateUsedLambdas(network.getDemandList().get(demandId -1).getDemandVolume());
                    }
                }
            }

            for(Link link : network.getLinkList())
            {
                cost += link.calculateCost();
            }

            if(cost< minCost) {
                minCost = cost;
                bestSolution = solution;
            }
        }

        System.out.println(minCost);
        System.out.println(bestSolution);
    }


    public void createAllSolutions() {

        /// Uncomment to check if number of solutions is correct ///

        List<List<List<Integer>>> allCombinations = new ArrayList<>();
//        int resultNewton = 1;

        for(Demand d : network.getDemandList()) {
            System.out.println(getCombinations(d.getDemandVolume(), d.getNumberOfPaths()));
            allCombinations.add(getCombinations(d.getDemandVolume(), d.getNumberOfPaths()));

//            Integer numberOfCombinations = calculateNewtonSymbol(d.getNumberOfPaths() + d.getDemandVolume() - 1, d.getDemandVolume());
//            resultNewton *= numberOfCombinations;
        }

//        System.out.println(resultNewton);
//        System.out.println(Lists.cartesianProduct(allCombinations).size());

        solutions = Lists.cartesianProduct(allCombinations);
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
        Integer result = 1;
        for (int i = 1; i <= k; i++)
            result = result * (n - i + 1) / i;
        return result;
    }

}
