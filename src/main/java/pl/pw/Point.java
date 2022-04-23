package pl.pw;

import java.util.Objects;

public class Point {

    private Integer demandId;
    private Integer pathId;

    public Point(Integer demandId, Integer pathId) {
        this.demandId = demandId;
        this.pathId = pathId;
    }

    public Integer getDemandId() {
        return demandId;
    }

    public Integer getPathId() {
        return pathId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Objects.equals(demandId, point.demandId) &&
                Objects.equals(pathId, point.pathId);
    }
}