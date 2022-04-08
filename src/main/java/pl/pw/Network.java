package main.java.pl.pw;

import java.util.List;

public class Network {

    private List<Link> linkList;
    private List<Demand> demandList;


    public Network() { }

    public Network(List<Link> linkList, List<Demand> demandList) {
        this.linkList = linkList;
        this.demandList = demandList;
    }
}
