package driver;

import model.KNN;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shubham.srivastava
 * netId: ss14687
 */
public class KNNDriver {

    public static void main(String[] args) {

        // k
        int k = 3;

        // is euclidean squared distance
        boolean isE2 = true;

        boolean isUnitW = false;

        File trainFile;

        File testFile;

        boolean isKnn = true;

        int i = 0;
        int kind = -1;
        int dind = -1;
        int trainInd = -1;
        int testInd = -1;

//        int modeInd = -1;

        // read params
        for (String s : args) {

            if (s.trim().equalsIgnoreCase("-k")) {
                kind = i + 1;
            }

            if (s.trim().equalsIgnoreCase("-d")) {
                dind = i + 1;
            }

            if (s.trim().equalsIgnoreCase("-unitw")) {
                isUnitW = true;
            }

            if (s.trim().equalsIgnoreCase("-train")) {
                trainInd = i + 1;
            }

            if (s.trim().equalsIgnoreCase("-test")) {
                testInd = i + 1;
            }

//            if (s.trim().equalsIgnoreCase("-mode")) {
//                modeInd = i+1;
//            }

            ++i;
        }

//        if (modeInd != -1) {
//            if (!args[modeInd].trim().equalsIgnoreCase("knn")) {
//                isKnn = false;
//            }
//        }

        if (kind != -1) {
            k = Integer.parseInt(args[kind].trim());
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
        testFile = new File(args[testInd].trim());

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


        List<String> testContent = new ArrayList<>();


        try {
            BufferedReader testBuf = new BufferedReader(new FileReader(testFile));
            String s = testBuf.readLine();
            while (s != null) {
                if (s.trim().isEmpty()) {
                    s = testBuf.readLine();
                } else {
                    testContent.add(s.trim());
                    s = testBuf.readLine();
                }
            }
        } catch (FileNotFoundException e) {
            OutputWriter.getInstance().printErrorLine("Trouble reading the train file passed to this program");
            throw new InternalError("Please try the whole thing again later");
        } catch (IOException e) {
            OutputWriter.getInstance().printDebugLine("Trouble reading lines from the file specified. Exiting now. Please try again later.");
            throw new InternalError("Please try the whole thing again later");
        }

        KNN model = new KNN();
        model.handleInput(trainContent);
        model.test(testContent, isE2, isUnitW, k);
    }

}