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

        //System.out.println("Witaj! Proszę podać ścieżkę bezwzględna do pliku z grafem: ");
        //System.out.println("Proszę wybrać plik z grafem");
        //System.out.println("1.net4");
        //System.out.println("2.net12_1");
        //System.out.println("3.net12_2");
        //pathId = scanner.nextInt();
        /*switch (pathId) {
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
        }*/
        try {
            linksData = Parser.readDataLinks(path);
            demandsData = Parser.readDataDemands(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        links = Parser.createLinks(linksData);
        demands = Parser.createDemands(demandsData, links);
        Network network = new Network(links, demands);

        /*System.out.println("Typ problemu: DDAP - wpisz 1, DAP - wpisz 0");
        problemType = scanner.nextInt();
        System.out.println("Problem ma być rozwiązany za pomocą algorytmu Ewolucyjnego: - wpisz 1 czy Brut Force - wpisz 0");
        methodType = scanner.nextInt();
        if (methodType == 1) {
            System.out.println("Podaj wielkość populaci: (tylko liczby naturalne)");
            population = scanner.nextInt();
            System.out.println("Podaj prawdopodobieństwo krzyżowania: (tylko liczby naturalne, max 100)");
            crossoverProbability = scanner.nextInt();
            System.out.println("Podaj prawdopodobieństwo mutacji: (tylko liczby naturalne, max 100)");
            mutationProbability = scanner.nextInt();
            System.out.println("Podaj liczbe generacji: (tylko liczby naturalne)");
            numberOfGeneration = scanner.nextInt();
            System.out.println("Podaj liczbe mutacji: (tylko liczby naturalne)");
            numberOfMutation = scanner.nextInt();
            System.out.println("Podaj czas trwania symulacji: (w sekunadach)");
            simulationTime = scanner.nextInt();
            System.out.println("Podaj maksymalna ilość generacji nie ulepszających wyniku: (liczby naturalne)");
            numberOfGenerationWithoutImprovment = scanner.nextInt();
            System.out.println("Podaj ziarno: (liczba całkowita)");
            seed = scanner.nextInt();

            EvolutionaryAlgorithm EA = new EvolutionaryAlgorithm(network, population, crossoverProbability, mutationProbability,
                    seed, numberOfGeneration, numberOfMutation, simulationTime, numberOfGenerationWithoutImprovment);
            EA.generateStartPopulation();

            if(problemType == 0) {
                EA.computeDAP();
            }else {
                EA.computeDDAP();
            }
        } else {
            BruteForceAlgorithm BA = new BruteForceAlgorithm(network);
            if(problemType == 0) {
                BA.computeDAP();
            }else {
                BA.computeDDAP();
            }
        }*/

        EvolutionaryAlgorithm EA = new EvolutionaryAlgorithm(network);
        EA.generateStartPopulation();
        //EA.computeDAP();
        //EA.computeDDAP();

        BruteForceAlgorithm BA = new BruteForceAlgorithm(network);
        BA.computeDAP();
        //BA.computeDDAP();

    }
}
