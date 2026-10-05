package lambdas;

/**
 * Adds two numbers. This is the "long way": a whole class just for a + b.
 * Compare it with the lambda (a, b) -> a + b.
 *
 * @author Sushanth Ambati
 * @version 1.0
 */
class Adder implements IntegerMath {
    /**
     * Adds a and b.
     *
     * @param a the first number
     * @param b the second number
     * @return the result
     */
    @Override
    public int operation(int a, int b) {
        return a + b;
    }
}