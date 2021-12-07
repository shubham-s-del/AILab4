package driver;

import model.KMeans;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shubham.srivastava
 * netId: ss14687
 */
public class KMeansDriver {

    public static void main(String[] args) {
        // is euclidean squared distance
        boolean isE2 = true;

        File trainFile;


        int i = 0;

        int dind = -1;
        int trainInd = -1;


//        int modeInd = -1;

        List<String> centroids = new ArrayList<>();
        // read params
        for (String s : args) {

            if (s.trim().equalsIgnoreCase("-d")) {
                dind = i + 1;
            }

            if (s.trim().equalsIgnoreCase("-data")) {
                trainInd = i + 1;
            }

            if (s.contains(",")) {
                centroids.add(s.trim());
            }

            ++i;
        }

        if (dind != -1) {
            String d = args[dind].trim();
            if (d.equalsIgnoreCase("e2")) {
                isE2 = true;
            } else {
                isE2 = false;
            }
        }

        trainFile = new File(args[trainInd].trim());

        List<String> trainContent = new ArrayList<>();

        try {
            BufferedReader trainBuf = new BufferedReader(new FileReader(trainFile));
            String s = trainBuf.readLine();
            while (s != null) {
                if (s.trim().isEmpty()) {
                    s = trainBuf.readLine();
                } else {
                    trainContent.add(s.trim());
                    s = trainBuf.readLine();
                }
            }

        } catch (FileNotFoundException e) {
            OutputWriter.getInstance().printErrorLine("Trouble reading the train file passed to this program");
            throw new InternalError("Please try the whole thing again later");
        } catch (IOException e) {
            OutputWriter.getInstance().printDebugLine("Trouble reading lines from the file specified. Exiting now. Please try again later.");
            throw new InternalError("Please try the whole thing again later");
        }

        KMeans model = new KMeans();
        model.handleInput(trainContent, centroids);
        model.test(isE2);
    }

}
