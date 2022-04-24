package pl.pw;

import java.io.IOException;
import java.util.List;

public class Program {

    public static void main(String args[]) {
        List<String> demandsData = null;
        List<String> linksData = null;
        List<Link> links = null;
        List<Demand> demands = null;
        try {
            linksData = Parser.readDataLinks("src/main/resources/net12_1.txt");
            demandsData = Parser.readDataDemands("src/main/resources/net12_1.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        links = Parser.createLinks(linksData);
        demands = Parser.createDemands(demandsData, links);
        Network network = new Network(links, demands);

<<<<<<< HEAD
        // BruteForceAlgorithm BA = new BruteForceAlgorithm(network);
        // BA.computeDDAP();

        EvolutionaryAlgorithm EA = new EvolutionaryAlgorithm(network);
        EA.generateStartPopulation();

=======
        BruteForceAlgorithm bruteForceAlgorithm = new BruteForceAlgorithm(network);
        bruteForceAlgorithm.computeDAP();
        bruteForceAlgorithm.computeDDAP();
>>>>>>> 161f57432675930e06d8a7804afcbc33d2934348
    }
}
