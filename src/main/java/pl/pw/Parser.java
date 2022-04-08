package main.java.pl.pw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
                if (demands) {
                    data.add(line);
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
        ///TODO
        return demands;
    }

}
