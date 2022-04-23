package pl.pw;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Solution {

    private Float cost;
    private Map<Point, Integer> mapOfValues;
    private List<Integer> capacitiesOfLinks;
    private Integer numberOfLinksWithExceededCapacity;

    public Solution(Map<Point, Integer> mapOfValues) {
        this.mapOfValues = mapOfValues;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public Map<Point, Integer> getMapOfValues() {
        return mapOfValues;
    }

    public List<Integer> getCapacitiesOfLinks() {
        return capacitiesOfLinks;
    }

    public void setCapacitiesOfLinks(List<Integer> capacitiesOfLinks) {
        this.capacitiesOfLinks = capacitiesOfLinks;
    }

    public Integer getNumberOfLinksWithExceededCapacity() {
        return numberOfLinksWithExceededCapacity;
    }

    public void setNumberOfLinksWithExceededCapacity(Integer numberOfLinksWithExceededCapacity) {
        this.numberOfLinksWithExceededCapacity = numberOfLinksWithExceededCapacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Solution that = (Solution) o;
        return Objects.equals(cost, that.cost) &&
                Objects.equals(mapOfValues, that.mapOfValues) &&
                Objects.equals(capacitiesOfLinks, that.capacitiesOfLinks) &&
                Objects.equals(numberOfLinksWithExceededCapacity, that.numberOfLinksWithExceededCapacity);
    }

    public int getNumberOfGenes() {
        return mapOfValues.entrySet().stream()
                .map(entry -> entry.getKey().getDemandId())
                .collect(Collectors.toSet())
                .size();
    }

    public Map<Point, Integer> getGene(int geneId) {
        Map<Point, Integer> gene = new HashMap<>();
        for (Map.Entry<Point, Integer> entry : mapOfValues.entrySet()) {
            if (entry.getKey().getDemandId().equals(geneId)) {
                gene.put(entry.getKey(), entry.getValue());
            }
        }
        return gene;
    }
}


