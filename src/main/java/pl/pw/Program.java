package main.java.pl.pw;

import java.io.IOException;
import java.util.List;

public class Program {

    public static void main(String args[])
    {
        try {
            List<String> data = Parser.readDataLinks("src/main/resources/net4.txt");
            for(int i =0; i < data.size(); i++){
                System.out.println(data.get(i));
            }
            System.out.println(Parser.createLinks(data).size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
