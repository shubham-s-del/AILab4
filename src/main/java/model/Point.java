package model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shubham.srivastava
 * netId: ss14687
 */
public class Point {
    List<Integer> point = new ArrayList<>();
    String key = "";

    public Point(List<Integer> point, String key) {
        this.point = point;
        this.key = key;
    }

    public List<Integer> getPoint() {
        return point;
    }

    public void setPoint(List<Integer> point) {
        this.point = point;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
