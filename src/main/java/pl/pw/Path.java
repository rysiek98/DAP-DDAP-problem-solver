package pl.pw;

import java.util.ArrayList;
import java.util.List;

public class Path {
    private int id;
    private List<Link> linkList;

    public Path(int id) {
        this.id = id;
        this.linkList = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public List<Link> getLinkList() {
        return linkList;
    }

    public void addLink(Link link) {
        linkList.add(link);
    }
}
