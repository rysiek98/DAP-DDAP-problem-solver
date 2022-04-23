package pl.pw;


import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BruteForceAlgorithm {

    protected Network network;

//    public BruteForceAlgorithm(Network network) {
//        this.network = network;
//    }

    public Network getNetwork() {
        return network;
    }


    public List<List<Integer>> getCombinations(Integer sum, Integer numberOfElements) {
        List<List<Integer>> lists = new ArrayList();
        List<Integer> list = new ArrayList();

        for (int i = 0; i <= sum; i++) {
            list.add(i);
        }

        for (int i = 0; i < numberOfElements; i++) {
            lists.add(list);
        }

        return Lists.cartesianProduct(lists).stream()
                .filter(product -> sum.equals(product.stream().mapToInt(Integer::intValue).sum()))
                .collect(Collectors.toList());
    }


    private int calculateNewtonSymbol(int n, int k) {
        Integer result = 1;
        for (int i = 1; i <= k; i++)
            result = result * (n - i + 1) / i;
        return result;
    }

}
