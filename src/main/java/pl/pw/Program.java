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
            linksData = Parser.readDataLinks("src/main/resources/net4.txt");
            demandsData = Parser.readDataDemands("src/main/resources/net4.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        links = Parser.createLinks(linksData);
        demands = Parser.createDemands(demandsData, links);
        Network network = new Network(links, demands);

        BruteForceAlgorithm bruteForceAlgorithm = new BruteForceAlgorithm(network);
        bruteForceAlgorithm.createAllSolutions();
        bruteForceAlgorithm.computeDDAP();

    }
}
