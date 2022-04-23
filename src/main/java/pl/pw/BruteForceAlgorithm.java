package pl.pw;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BruteForceAlgorithm {

    protected Network network;
    private List<Solution> solutions;

    public BruteForceAlgorithm(Network network) {
        this.network = network;
        solutions = new ArrayList<>();
    }

    public void createAllSolutions() {

        List<List<List<Integer>>> allCombinations = getAllCombinations();

        for(List<List<Integer>> list : allCombinations) {
            Solution solution = new Solution(list);
            solutions.add(solution);
        }
    }

    public List<List<List<Integer>>> getAllCombinations() {

        List<List<List<Integer>>> allCombinations = new ArrayList<>();
//        int resultNewton = 1;

        for(Demand d : network.getDemandList()) {
            System.out.println(getCombinations(d.getDemandVolume(), d.getNumberOfPaths()));
            allCombinations.add(getCombinations(d.getDemandVolume(), d.getNumberOfPaths()));

//            Integer numberOfCombinations = calculateNewtonSymbol(d.getNumberOfPaths() + d.getDemandVolume() - 1, d.getDemandVolume());
//            resultNewton *= numberOfCombinations;
        }

        /// To check if number of solutions is correct ///

//        System.out.println(resultNewton);
//        System.out.println(Lists.cartesianProduct(allCombinations).size());

        return Lists.cartesianProduct(allCombinations);
    }

    // returns the all possible combinations of setting demand values for the paths
    public List<List<Integer>> getCombinations(Integer demandValue, Integer numberOfPaths) {
        List<List<Integer>> lists = new ArrayList();
        List<Integer> list = new ArrayList();

        List<List<List<Integer>>> bigList = new ArrayList<>();

        for (int i = 0; i <= demandValue; i++) {
            list.add(i);
        }

        //System.out.println(list);

        for (int i = 0; i < numberOfPaths; i++) {
            lists.add(list);
        }
       // System.out.println(lists);

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

    public List<Solution> getSolutions() {
        return solutions;
    }
}
