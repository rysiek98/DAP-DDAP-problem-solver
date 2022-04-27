package pl.pw;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Program {

    public static void main(String args[]) {
        List<String> demandsData = null;
        List<String> linksData = null;
        List<Link> links = null;
        List<Demand> demands = null;
        String path = "src/main/resources/net4.txt";
        int pathId = 0;
        int methodType = 0;
        int problemType = 0;
        int population = 0;
        int numberOfGeneration = 0;
        int numberOfMutation = 0;
        int simulationTime = 0;
        int mutationProbability = 0;
        int crossoverProbability = 0;
        int seed = 0;
        int numberOfGenerationWithoutImprovment = 0;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Witaj! Proszę wybrać plik z grafem: (domyślny plik net4)");
        System.out.println("1.net4");
        System.out.println("2.net12_1");
        System.out.println("3.net12_2");
        pathId = scanner.nextInt();
        switch (pathId) {
            case 2: {
                path = "src/main/resources/net12_1.txt";
            }
            break;
            case 3: {
                path = "src/main/resources/net12_2.txt";
            }
            break;
            default:
                break;
        }
        try {
            linksData = Parser.readDataLinks(path);
            demandsData = Parser.readDataDemands(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        links = Parser.createLinks(linksData);
        demands = Parser.createDemands(demandsData, links);
        Network network = new Network(links, demands);

        System.out.println("Typ problemu: DDAP - wpisz 1, DAP - wpisz 0");
        problemType = scanner.nextInt();
        System.out.println("Problem ma być rozwiązany za pomocą algorytmu Ewolucyjnego: - wpisz 1 czy Brut Force - wpisz 0");
        methodType = scanner.nextInt();
        if (methodType == 1) {
            System.out.println("Podaj wielkość populaci: (tylko liczby całkowite)");
            population = scanner.nextInt();
            System.out.println("Podaj prawdopodobieństwo krzyżowania: (tylko liczby całkowite, 0 do 100)");
            crossoverProbability = scanner.nextInt();
            System.out.println("Podaj prawdopodobieństwo mutacji: (tylko liczby całkowite, 0 do 100)");
            mutationProbability = scanner.nextInt();
            System.out.println("Podaj liczbe generacji: (tylko liczby całkowite)");
            numberOfGeneration = scanner.nextInt();
            System.out.println("Podaj liczbe mutacji: (tylko liczby całkowite)");
            numberOfMutation = scanner.nextInt();
            System.out.println("Podaj czas trwania symulacji: (w sekunadach)");
            simulationTime = scanner.nextInt();
            System.out.println("Podaj maksymalna ilość generacji nie ulepszających wyniku: (tylko liczby całkowite)");
            numberOfGenerationWithoutImprovment = scanner.nextInt();
            System.out.println("Podaj ziarno: (tylko liczby całkowite)");
            seed = scanner.nextInt();

            EvolutionaryAlgorithm EA = new EvolutionaryAlgorithm(network, population, crossoverProbability, mutationProbability,
                    seed, numberOfGeneration, numberOfMutation, simulationTime, numberOfGenerationWithoutImprovment);
            EA.generateStartPopulation();

            if (problemType == 0) {
                System.out.println("Czas wykonania obliczeń: "+EA.computeDAP()/1000.0+"s");
            } else {
                System.out.println("Czas wykonania obliczeń: "+EA.computeDDAP()/1000.0+"s");
            }
        } else {
            BruteForceAlgorithm BA = new BruteForceAlgorithm(network);
            if (problemType == 0) {
                System.out.println("Czas wykonania obliczeń: "+BA.computeDAP()/1000.0+"s");
            } else {
                System.out.println("Czas wykonania obliczeń: "+BA.computeDDAP()/1000.0+"s");
            }
        }
    }
}
