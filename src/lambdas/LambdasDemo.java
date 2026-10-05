package lambdas;
/**
 * From "regular method" to "lambda" in four steps, same behavior each time.
 *
 * @author Sushanth Ambati
 * @version 1.0
 */
public class LambdasDemo {

    /**
     * Runs the demo.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Calculator myApp = new Calculator();
        Adder adder = new Adder();
        Subtractor sub = new Subtractor();

        // STEP 1: regular method call
        System.out.println("Step 1: " + adder.operation(40, 2));
        System.out.println("Subtract:"+ sub.operation(40, 2));


        IntegerMath mul = (a, b) -> a * b;
        System.out.println("Multiply:"+ myApp.operateBinary(40, 2, (a, b) -> a * b));
        //System.out.println("Step 2:" + myApp.operateBinary(50, 5, (a, b) -> a-b));
    }
}