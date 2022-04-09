package main.java.pl.pw;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Parser {

    public static List<String> readDataLinks(String filePath) throws IOException {

        List<String> data = new ArrayList<>();
        String line = null;
        BufferedReader bufferedReader = null;

        try {
            File file = new File(filePath);
            FileReader fileReader = new FileReader(file, StandardCharsets.UTF_8);
            bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                if (line.equals("-1"))
                    break;

                String[] lineElements = line.split(" ");
                for (int i = 0; lineElements.length > i; i++) {
                    if (!Objects.equals(lineElements[i], " ") && !Objects.equals(lineElements[i], "")) {
                        data.add(lineElements[i]);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error occurred while parsing file.");
            e.printStackTrace();
        } finally {
            bufferedReader.close();
        }
        return data;
    }

    public static List<String> readDataDemands(String filePath) throws IOException {

        List<String> data = new ArrayList<>();
        String line = null;
        BufferedReader bufferedReader = null;

        try {
            File file = new File(filePath);
            FileReader fileReader = new FileReader(file, StandardCharsets.UTF_8);
            bufferedReader = new BufferedReader(fileReader);
            boolean demands = false;

            while ((line = bufferedReader.readLine()) != null) {
                if (line.equals("-1")) {
                    demands = true;
                }
                else if (demands) {
                    if (line.trim().length() != 0 )     // skipping empty line
                    {
                        data.add(line);
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error occurred while parsing file.");
            e.printStackTrace();
        } finally {
            bufferedReader.close();
        }
        return data;
    }

    public static List<Link> createLinks(List<String> data) {
        List<Link> links = new ArrayList<>();
        int id = 0;
        for (int i = 1; i < data.size(); i += 5) {
            links.add(new Link(id));
            links.get(id).setStartNode(new Node(Integer.parseInt(data.get(i))));
            links.get(id).setEndNode(new Node(Integer.parseInt(data.get(i + 1))));
            links.get(id).setNumberOfFibre(Integer.parseInt(data.get(i + 2)));
            links.get(id).setCost(Float.parseFloat(data.get(i + 3)));
            links.get(id).setLambdas(Integer.parseInt(data.get(i + 4)));
            ++id;
        }
        return links;
    }

    public static List<Demand> createDemands(List<String> data){
        List<Demand> demands = new ArrayList<>();

        int numberOfDemands = Integer.parseInt(data.get(0));
        int listIter = 0;

        for (int demandId = 0; demandId < numberOfDemands; demandId++) {

            demands.add(new Demand(demandId));

            System.out.println("Demand id: " + demandId);

            listIter++; // line with demand details
            String[] demandDetails = data.get(listIter).split(" ");

            // setting demand details
            demands.get(demandId).setStartNode(new Node(Integer.parseInt(demandDetails[0])));
            demands.get(demandId).setEndNode(new Node(Integer.parseInt(demandDetails[1])));
            demands.get(demandId).setDemandVolume(Integer.parseInt(demandDetails[2]));

            listIter++;
            int numberOfPaths = Integer.parseInt(data.get(listIter));


            for (int i = 0; i < numberOfPaths; i++) {

                listIter++;
                Path path = new Path(i);

                String[] splittedLine = data.get(listIter).split(" ");
                String[] pathLinkList = Arrays.copyOfRange(splittedLine, 1, splittedLine.length);

                for (int j = 0; j < pathLinkList.length; j++) {
                    System.out.print(pathLinkList[j]);
                }
                System.out.println();
                // TODO add links to the path   path.addLink(link)

                demands.get(demandId).addPath(path);
            }
        }

        return demands;
    }

}
