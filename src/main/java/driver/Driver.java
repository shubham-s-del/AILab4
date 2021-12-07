package driver;

/**
 * @author shubham.srivastava
 * netId: ss14687
 */
public class Driver {

    public static void main(String[] args) {
        int modeInd = -1;
        int i = 0;
        for (String s: args) {
            if (s.equalsIgnoreCase("-mode")) {
                modeInd = i+1;
            }
            ++i;
        }

        if (modeInd == -1) {
            // default knn
            KNNDriver.main(args);
        } else {
            if (args[modeInd].equalsIgnoreCase("knn")) {
                KNNDriver.main(args);
            } else {
                KMeansDriver.main(args);
            }
        }
    }

}
