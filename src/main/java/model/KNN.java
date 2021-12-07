package model;

import driver.OutputWriter;
import utils.Distance;

import java.util.*;

/**
 * @author shubham.srivastava
 * netId: ss14687
 */
public class KNN {

    List<List<Integer>> points = new ArrayList<>();
    List<String> classifiedAttributes = new ArrayList<>();

    public void handleInput(List<String> trainingData) {
        for (String line : trainingData) {

            String[] tokens = line.split(",");
            List<Integer> point = new ArrayList<>();
            for (int i = 0; i < tokens.length - 1; i++) {
                point.add(Integer.parseInt(tokens[i].trim()));
            }
            points.add(point);
            classifiedAttributes.add(tokens[tokens.length - 1].trim());
        }
    }

    public void test(List<String> testingData, boolean isE2, boolean isUnit, int k) {
        List<String> actualClasses = new ArrayList<>();
        Set<String> actualClassSet = new HashSet<>();

        List<String> predictedClass = new ArrayList<>();
        Set<String> predictedClassSet = new HashSet<>();
        for (String line : testingData) {
            String[] tokens = line.split(",");
            List<Integer> point = new ArrayList<>();
            for (int i = 0; i < tokens.length - 1; i++) {
                point.add(Integer.parseInt(tokens[i].trim()));
            }

            String actualClass = tokens[tokens.length - 1].trim();

            PriorityQueue<Integer> pq = new PriorityQueue<Integer>((a, b) -> {
                double dista = Distance.getDist(point, points.get(a), isE2);
                double distb = Distance.getDist(point, points.get(b), isE2);

                double distanceFactorA = dista;
                double distanceFactorB = distb;

//                if (isUnit) {
//                    distanceFactorA = dista;
//                    distanceFactorB = distb;
//                } else {
//                    distanceFactorA = 1 / Math.max(dista, 0.0001);
//                    distanceFactorB = 1 / Math.max(distb, 0.0001);
//                }

                if (distanceFactorA > distanceFactorB) {
                    return 1;
                } else {
                    return -1;
                }
            });

            for (int i = 0; i < points.size(); i++) {
                pq.add(i);
            }

            // priority queue must have indexes of points sorted by distance now.

            // identify classes of top k points now.
            Map<String, Double> classCount = new HashMap<>();
            for (int i = 0; i < k; i++) {
                if (!pq.isEmpty()) {
                    Integer index = pq.poll();
                    String cl = classifiedAttributes.get(index);
                    if (!classCount.containsKey(cl)) {
                        classCount.put(cl, 0.0);
                    }
                    if (isUnit) {
                        classCount.put(cl, classCount.get(cl) + 1);
                    } else {
                        classCount.put(cl, classCount.get(cl) + 1 / Math.max(0.0001, Distance.getDist(point, points.get(index), isE2)));
                    }
                }
            }

            double max = 0.0;
            String clPred = "";
            for (Map.Entry<String, Double> e : classCount.entrySet()) {
                if (e.getValue() > max) {
                    max = e.getValue();
                    clPred = e.getKey();
                }
            }

            actualClasses.add(actualClass);
            actualClassSet.add(actualClass);
            predictedClass.add(clPred);
            predictedClassSet.add(clPred);
            OutputWriter.getInstance().printDebugLine("want=" + actualClass + " got=" + clPred);
        }

        // compute precision and recall

        Set<String> finSet = new TreeSet<>();
        finSet.addAll(actualClassSet);
        finSet.addAll(predictedClassSet);

        for (String s : finSet) {
            // calculate precision
            int CintersectQ = 0;
            int Q = 0;
            int C = 0;
            for (int i = 0; i < predictedClass.size(); i++) {
                if (actualClasses.get(i).equalsIgnoreCase(predictedClass.get(i))
                        && actualClasses.get(i).equalsIgnoreCase(s)) {
                    CintersectQ++;
                }

                if (predictedClass.get(i).equalsIgnoreCase(s)) {
                    Q++;
                }

                if (actualClasses.get(i).equalsIgnoreCase(s)) {
                    C++;
                }
            }
            System.out.println("Label=" + s + " Precision=" +
                    CintersectQ + "/" + Q + " Recall=" + CintersectQ + "/" + C);
        }
    }
}
