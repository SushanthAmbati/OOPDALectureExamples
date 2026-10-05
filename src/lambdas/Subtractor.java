package lambdas;

/**
 * Subtracts one number from another.
 *
 * @author Sushanth Ambati
 * @version 1.0
 */
public class Subtractor implements IntegerMath {
    /**
     * Subtracts b from a.
     *
     * @param a the first number
     * @param b the second number
     * @return the result
     */
    @Override
    public int operation(int a, int b) {
        return a - b;
    }
    
}
