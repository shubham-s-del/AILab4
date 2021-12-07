package model;

import driver.OutputWriter;
import utils.Distance;

import java.util.*;

/**
 * @author shubham.srivastava
 * netId: ss14687
 */
public class KMeans {

    List<Point> points = new ArrayList<>();


    Map<String, List<Double>> centroids = new TreeMap<>();


    public KMeans() {
    }

    public void handleInput(List<String> trainContent, List<String> c) {
        int cctr = 1;
        for (String s : c) {
            List<Double> centroid = new ArrayList<>();
            String[] tokens = s.split(",");
            for (String token : tokens) {
                centroid.add(Double.parseDouble(token));
            }
            centroids.put("C" + cctr, centroid);
            ++cctr;
        }

        // initial centroids have been added

        for (String line : trainContent) {

            String[] tokens = line.split(",");
            List<Integer> point = new ArrayList<>();
            for (int i = 0; i < tokens.length - 1; i++) {
                point.add(Integer.parseInt(tokens[i].trim()));
            }
            points.add(new Point(point, tokens[tokens.length - 1].trim()));
        }
        // points have been added

    }

    public void test(boolean isE2) {

        int numCenters = centroids.size();

        // centroid to new points map
        Map<String, List<Point>> clusters = new HashMap<>();


        // old version
        Map<String, List<Point>> lastState = new HashMap<>();

        boolean terminate = false;

        while (!terminate) {
            for (Point point : points) {

                String ind = getIndexOfNearestCentroid(point.getPoint(), centroids, isE2);
                assignToCluster(clusters, point, ind);
            }


            terminate = hasStateNotChanged(clusters, lastState);
            lastState = clusters;
            if (terminate) {
                break;
            }

            centroids = relocateCentroids(clusters, centroids);
            clusters = new HashMap<>();
        }

        for (String s:lastState.keySet()) {
            StringBuilder lineBuilder = new StringBuilder();
            lineBuilder.append(s + " = {");
            for (int j = 0; j < lastState.get(s).size(); j++) {
                lineBuilder.append(lastState.get(s).get(j).key);
                if (j != lastState.get(s).size() - 1) {
                    lineBuilder.append(",");
                }
            }
            lineBuilder.append("}");

            OutputWriter.getInstance().printDebugLine(lineBuilder.toString());
        }

        for(Map.Entry<String, List<Double>> e: centroids.entrySet()) {
            StringBuilder lineBuilder = new StringBuilder();
            lineBuilder.append(e.getKey());
            lineBuilder.append(" - ");
            lineBuilder.append("([ ");
            for (Double val: e.getValue()) {
                lineBuilder.append(val);
                lineBuilder.append(" ");
            }
            lineBuilder.append("])");
            OutputWriter.getInstance().printDebugLine(lineBuilder.toString());
        }

    }

    private Map<String, List<Double>> relocateCentroids(Map<String, List<Point>> clusters, Map<String, List<Double>> oldCentroids) {
        Map<String, List<Double>> newCentroids = new TreeMap<>();

        for (String s : oldCentroids.keySet()) {
            if (!clusters.containsKey(s)) {
                newCentroids.put(s, oldCentroids.get(s));
            }
        }
        for (Map.Entry<String, List<Point>> e : clusters.entrySet()) {
            List<Double> centroid = new ArrayList<>();
            List<Point> listOfPoints = e.getValue();

            if (listOfPoints == null || listOfPoints.isEmpty()) {

                continue;
            }

            if (listOfPoints != null && !listOfPoints.isEmpty()) {
                for (int k : listOfPoints.get(0).getPoint()) {
                    centroid.add((double) k);
                }
            }

            for (int j = 1; j < listOfPoints.size(); j++) {
                for (int k = 0; k < listOfPoints.get(j).getPoint().size(); k++) {
                    centroid.set(k, centroid.get(k) + listOfPoints.get(j).getPoint().get(k));
                }
            }

            for (int k = 0; k < centroid.size(); k++) {
                centroid.set(k, centroid.get(k) / listOfPoints.size());
            }

            newCentroids.put(e.getKey(), centroid);
        }
        return newCentroids;
    }

    private void assignToCluster(Map<String, List<Point>> clusters, Point point, String ind) {
        if (!clusters.containsKey(ind)) {
            clusters.put(ind, new ArrayList<>());
        }
        clusters.get(ind).add(point);
    }

    private String getIndexOfNearestCentroid(List<Integer> point, Map<String, List<Double>> centroids, boolean isE2) {

        String ind = "";
        Double minDist = Double.MAX_VALUE;

        List<Double> equivalentPoint = new ArrayList<>();
        for (Integer i : point) {
            equivalentPoint.add((double) i);
        }
        int i = 0;

        for (Map.Entry<String, List<Double>> e : centroids.entrySet()) {
            double dist = Distance.getDistDouble(equivalentPoint, e.getValue(), isE2);
            if (dist < minDist) {
                minDist = dist;
                ind = e.getKey();
            }
            ++i;
        }

        return ind;
    }

    private boolean hasStateNotChanged(Map<String, List<Point>> clusters, Map<String, List<Point>> lastState) {

        boolean res = true;

        for (Map.Entry<String, List<Point>> e : clusters.entrySet()) {
            if (!lastState.containsKey(e.getKey()) || e.getValue().size() != lastState.get(e.getKey()).size()) {
                res = false;
                break;
            } else {
                List<Point> pointsInLastState = lastState.get(e.getKey());

                Set<String> pointsInCluster = new HashSet<>();
                for (Point point : pointsInLastState) {
                    pointsInCluster.add(getSerializedPoint(point.getPoint()));
                }

                for (Point p : e.getValue()) {
                    String serKey = getSerializedPoint(p.getPoint());
                    if (!pointsInCluster.contains(serKey)) {
                        res = false;
                        break;
                    }
                }
            }
        }


        return res;

    }

    private String getSerializedPoint(List<Integer> p) {
        String key = "";
        for (Integer i : p) {
            key = key + i + ".";
        }
        return key;
    }
}
