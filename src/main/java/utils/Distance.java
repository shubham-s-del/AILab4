package utils;

import java.util.List;

/**
 * @author shubham.srivastava
 * netId: ss14687
 */
public class Distance {

    static double getE2Dist(List<Integer> point1, List<Integer> point2) {
        double s = 0;
        for (int i = 0; i < point1.size(); i++) {
            s += Math.pow((point1.get(i) - point2.get(i)), 2);
        }

        return s;
    }

    static double getManhDist(List<Integer> point1, List<Integer> point2) {
        double s = 0;
        for (int i = 0; i < point1.size(); i++) {
            s += Math.abs((point1.get(i) - point2.get(i)));
        }
        return s;
    }

    public static double getDist(List<Integer> point1, List<Integer> point2, boolean isE2) {
        if (isE2) {
            return getE2Dist(point1, point2);
        } else {
            return getManhDist(point1, point2);
        }
    }


    static double getE2DistDouble(List<Double> point1, List<Double> point2) {
        double s = 0;
        for (int i = 0; i < point1.size(); i++) {
            s += Math.pow((point1.get(i) - point2.get(i)), 2);
        }

        return s;
    }

    static double getManhDistDouble(List<Double> point1, List<Double> point2) {
        double s = 0;
        for (int i = 0; i < point1.size(); i++) {
            s += Math.abs((point1.get(i) - point2.get(i)));
        }
        return s;
    }

    public static double getDistDouble(List<Double> point1, List<Double> point2, boolean isE2) {
        if (isE2) {
            return getE2DistDouble(point1, point2);
        } else {
            return getManhDistDouble(point1, point2);
        }
    }

}
