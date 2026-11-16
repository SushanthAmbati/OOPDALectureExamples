package streams;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * map vs. flatMap on a list of students, then a few questions that flatMap
 * makes easy. Each numbered section in main matches a heading in the output
 * and a row in the README's "Key concepts" table.
 *
 * @author Sushanth Ambati
 * @version 1.0
 */
public class StreamsDemo {

    /**
     * Runs the demo.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        // ---------- Build the data ----------
        Student maya = new Student("Maya");
        maya.addCourse("Java");
        maya.addCourse("Calculus");
        maya.addCourse("Physics");

        Student leo = new Student("Leo");
        leo.addCourse("Java");
        leo.addCourse("History");

        Student priya = new Student("Priya");
        priya.addCourse("Calculus");
        priya.addCourse("Java");
        priya.addCourse("Art");

        List<Student> students = new ArrayList<>();
        students.add(maya);
        students.add(leo);
        students.add(priya);

        System.out.println("All students: " + students);

        // map: one student in, ONE thing out.
        // getName() returns a String, so map gives a Stream<String>.
        System.out.println("\n1. map(s -> s.getName())");
        List<String> names = students.stream()
                .map(s -> s.getName())
                .collect(Collectors.toList());
        System.out.println("   type:   Stream<String>");
        System.out.println("   result: " + names + "   (3 students -> 3 names)");

        // map with getCourses(): still ONE thing out, but that thing is a whole list.
        // getCourses() returns a List<String>, so map gives a Stream<List<String>>.
        System.out.println("\n2. map(s -> s.getCourses())");
        List<List<String>> schedules = students.stream()
                .map(s -> s.getCourses())
                .collect(Collectors.toList());
        System.out.println("   type:   Stream<List<String>>");
        System.out.println("   result: " + schedules + "   (3 students -> 3 lists)");

        // flatMap: one student in, MANY things out.
        // The lambda must return a Stream, so we call .stream() on the list.
        // flatMap then merges all those small streams into ONE Stream<String>.
        System.out.println("\n3. flatMap(s -> s.getCourses().stream())");
        Stream<String> courseStream = students.stream()
                .flatMap(s -> s.getCourses().stream());
        List<String> allCourses = courseStream.collect(Collectors.toList());
        System.out.println("   type:   Stream<String>");
        System.out.println("   result: " + allCourses + "   (3 students -> 8 courses)");

        // courseStream.count();
        // ^ Uncomment: CRASHES with an IllegalStateException. A stream can only be
        //   used once, and collect(...) above already used it up.

        // Now that every course is in ONE stream, we can ask questions about all of them.
        System.out.println("\n4. Questions about ALL courses");
        List<String> distinctCourses = students.stream()
                .flatMap(s -> s.getCourses().stream())
                .distinct()
                .collect(Collectors.toList());
        System.out.println("   Courses offered: " + distinctCourses);

        long javaCount = students.stream()
                .flatMap(s -> s.getCourses().stream())
                .filter(course -> course.equals("Java"))
                .count();
        System.out.println("   Students taking Java: " + javaCount);
    }
}
