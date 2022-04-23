package pl.pw;

public class Program {

    public static void main(String args[]) {
//        List<String> demandsData = null;
//        List<String> linksData = null;
//        List<Link> links = null;
//        List<Demand> demands = null;
//        try {
//            linksData = Parser.readDataLinks("src/main/resources/net12_1.txt");
//            demandsData = Parser.readDataDemands("src/main/resources/net12_1.txt");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        links = Parser.createLinks(linksData);
//        demands = Parser.createDemands(demandsData, links);
//        Network network = new Network(links, demands);

        BruteForceAlgorithm bruteForceAlgorithm = new BruteForceAlgorithm();
        System.out.print(bruteForceAlgorithm.getCombinations(5, 3));


    }
}
