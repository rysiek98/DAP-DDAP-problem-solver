import java.util.List;

public class Demand {

    private int id;
    private Node startNode;
    private Node endNode;
    private int demandVolume;
    private List<Path> pathList;


    public Demand(int id, Node startNode, Node endNode, int demandVolume, List<Path> pathList) {
        this.id = id;
        this.startNode = startNode;
        this.endNode = endNode;
        this.demandVolume = demandVolume;
        this.pathList = pathList;
    }

    public int getId() {
        return id;
    }

    public Node getStartNode() {
        return startNode;
    }

    public Node getEndNode() {
        return endNode;
    }

    public int getDemandVolume() {
        return demandVolume;
    }

    public List<Path> getPathList() {
        return pathList;
    }
}
