package lambdas;

// The contract: any object given to operateBinary must have this ONE method.
/**
 * Takes two ints and gives back one int.
 *
 * It has exactly one abstract method, so it's a functional interface, and a
 * lambda can be used anywhere an IntegerMath is expected. The interface
 * supplies the name and signature of operation; the lambda supplies the body.
 *
 * @author Sushanth Ambati
 * @version 1.0
 */
interface IntegerMath {
    /**
     * Combines a and b into one result.
     *
     * @param a the first number
     * @param b the second number
     * @return the result
     */
    int operation(int a, int b);
}