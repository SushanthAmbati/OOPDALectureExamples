package lambdas;

/**
 * Takes one int and answers true or false.
 *
 * Compare it
 * with TextCheck: the only difference is the parameter type. Java already
 * ships one generic version of this shape, java.util.function.Predicate.
 *
 * @author Sushanth Ambati
 * @version 1.0
 */
interface IntegerCheck {
    /**
     * Checks n.
     *
     * @param n the number to check
     * @return true if n passes the check
     */
    boolean test(int n);
}
