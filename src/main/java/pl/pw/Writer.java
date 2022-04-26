package pl.pw;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Writer {

    public void writeDDAP(Network network, List<List<Integer>> bestSolutions) {
        setupNetwork(network, bestSolutions);
        try {
            FileWriter myWriter = new FileWriter("DDAP.txt");
            myWriter.write(printDDAPBF(network, bestSolutions));
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private String printDDAPBF(Network network, List<List<Integer>> solution) {
        String text = network.getLinkList().size() + "\n\n";
        for (Link link : network.getLinkList()) {
            text += link.getId() + " " + link.getUsedLambdas() + " " + link.getUsedLambdas() / link.getLambdas() + "\n";
        }
        text += "\n";
        text += network.getDemandList().size() + "\n\n";
        for (int i = 0; i < network.getDemandList().size(); i++) {
            text += network.getDemandList().get(i).getId() + " " + solution.get(i).stream().filter(x -> x != 0).count() + "\n";
            for (int j = 0; j < solution.get(i).size(); j++) {
                if (solution.get(i).get(j) != 0) {
                    text += j + 1 + " " + solution.get(i).get(j) + "\n";
                }
            }
            text += "\n";
        }
        return text;
    }

    private void setupNetwork(Network network, List<List<Integer>> solution) {
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
    }
}
