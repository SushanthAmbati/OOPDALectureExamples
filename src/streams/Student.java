package streams;

import java.util.ArrayList;
import java.util.List;

/**
 * A student with a name and a list of courses. Used by StreamsDemo to show
 * the difference between map (one value per student) and flatMap (many
 * values per student).
 *
 * @author Sushanth Ambati
 * @version 1.0
 */
public class Student {
    private String name;
    private List<String> courses;

    /**
     * Creates a student with no courses yet.
     *
     * @param name the student's name
     */
    public Student(String name) {
        this.name = name;
        this.courses = new ArrayList<>();
    }

    /**
     * Adds a course to this student's list.
     *
     * @param course the course name, e.g. "Java"
     */
    public void addCourse(String course) {
        courses.add(course);
    }

    // Returns ONE String, e.g. "Maya"
    /**
     * Gets the student's name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    // Returns ONE List<String>, e.g. [Java, Calculus, Physics]
    /**
     * Gets the student's courses.
     *
     * @return the list of course names (the student's own list, not a copy)
     */
    public List<String> getCourses() {
        return courses;
    }

    @Override
    public String toString() {
        return name + " " + courses;
    }
}
