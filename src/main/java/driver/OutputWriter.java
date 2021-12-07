package driver;

/**
 * @author shubham.srivastava
 * netId: ss14687
 */
public class OutputWriter {


    private static OutputWriter instance = new OutputWriter();

    public OutputWriter() {
    }

    public static OutputWriter getInstance() {
        return instance;
    }

    public void printDebugLine(String s) {
        System.out.println(s);
    }

    public void printErrorLine(String s) {
        System.out.println(s);
    }
}
