package lambdas;

/**
 * Takes one String and answers true or false.
 *
 * This is IntegerCheck again with String in place of int. Writing a new
 * interface for every type does not scale, which is why Java provides the
 *
 * @author Sushanth Ambati
 * @version 1.0
 */
interface TextCheck {
    /**
     * Checks s.
     *
     * @param s the text to check
     * @return true if s passes the check
     */
    boolean test(String s);
}
