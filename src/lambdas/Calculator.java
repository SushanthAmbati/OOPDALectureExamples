package lambdas;

/**
 * Runs whatever operation it's given, without knowing or caring which one.
 *
 * @author Sushanth Ambati
 * @version 1.0
 */
class Calculator {
    /**
     * Applies op to a and b.
     *
     * @param a  the first number
     * @param b  the second number
     * @param op what to do with them: an Adder, a lambda, or anything else
     *           that implements IntegerMath
     * @return whatever op returns
     */
    // It has NO idea what op does. It only knows: "op has an operation(a, b)".
    public int operateBinary(int a, int b, IntegerMath op) {
        return op.operation(a, b);
    }
}