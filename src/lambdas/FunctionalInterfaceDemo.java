package lambdas;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Why Java ships its own functional interfaces, building on IntegerMath.
 * Each numbered section in main matches a heading in the output and a row
 * in the README's "Built-in functional interfaces" section.
 *
 * @author Sushanth Ambati
 * @version 1.0
 */
public class FunctionalInterfaceDemo {

    /**
     * Runs the demo.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Calculator myApp = new Calculator();

        System.out.println("1. Recap: our own IntegerMath, shape (int, int) -> int");
        System.out.println("   40 + 2 = " + myApp.operateBinary(40, 2, (a, b) -> a + b));
        System.out.println("   20 - 10 = " + myApp.operateBinary(20, 10, (a, b) -> a - b));

        System.out.println("\n2. A new shape means inventing a new interface");
        System.out.println("   Is 8 even? " + myApp.checkNumber(8, n -> n % 2 == 0));
        System.out.println("   Is \"\" empty? " + myApp.checkText("", s -> s.isEmpty()));
        // IntegerCheck and TextCheck do the same job: "take something, answer true/false".
        
        
        
        
        
        // Java already ships ONE generic version of that shape: Predicate<T>.

        System.out.println("\n3. The standard shapes in java.util.function");

        // Predicate<T>: T -> boolean ("Is this okay?")
        Predicate<Integer> isEven = n -> n % 2 == 0;
        Predicate<String> isEmpty = s -> s.isEmpty();
        System.out.println("   Predicate: isEven.test(8) = " + isEven.test(8));
        System.out.println("   Predicate: isEmpty.test(\"hi\") = " + isEmpty.test("hi"));

        // Function<T, R>: T -> R ("Convert this")
        Function<String, Integer> length = s -> s.length();
        System.out.println("   Function: length.apply(\"lambda\") = " + length.apply("lambda"));

        // Consumer<T>: T -> void ("Do something with this")
        Consumer<String> shout = s -> System.out.println("   Consumer: " + s.toUpperCase() + "!");
        shout.accept("hello");

        // Supplier<R>: () -> R ("Give me one")
        Supplier<String> greeting = () -> "Hello from a Supplier";
        System.out.println("   Supplier: " + greeting.get());

        // NOTICE: the method name changes with the interface
        // (test, apply, accept, get). The interface supplies the name,
        // the lambda only supplies the body.

        // isEven.apply(8);
        // ^ Uncomment: COMPILE ERROR. Predicate's method is called test, not apply.

        // myApp.checkNumber(8, isEven);
        // ^ Uncomment: COMPILE ERROR. Same shape, but a Predicate<Integer> is not an
        //   IntegerCheck. Java matches interface TYPES, not shapes.

        System.out.println("\n4. The payoff: library methods already accept these");
        List<String> names = new ArrayList<>(List.of("Ann", "", "Bob", "", "Cy"));

        names.removeIf(s -> s.isEmpty());                         // removeIf takes a Predicate
        names.forEach(s -> System.out.println("   Name: " + s));  // forEach takes a Consumer
    }
}
