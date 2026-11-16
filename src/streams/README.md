# Streams: `map` vs. `flatMap`

**Idea:** A **stream** is a pipeline that data flows through: a **source** (`list.stream()`), then any number of **intermediate** steps (`map`, `filter`, `distinct`, …), then **one terminal** step that produces a result (`collect`, `count`, …). Each step takes a lambda. `map` turns each element into **one** new thing. `flatMap` turns each element into **many** things and merges them all into a single stream.

> Streams are built from the functional interfaces in [`../lambdas`](../lambdas/README.md#built-in-functional-interfaces-javautilfunction): `filter` takes a `Predicate` and `map` takes a `Function`.

## Files

| File | Role |
|------|------|
| [`Student.java`](Student.java) | A name and a `List<String>` of courses. `getName()` returns **one** `String`; `getCourses()` returns **one** `List`. |
| [`StreamsDemo.java`](StreamsDemo.java) | ▶ Three students, eight course enrollments. Its numbered sections match **Key concepts** below. |

## Key concepts

The data:

| Student | Courses |
|---------|---------|
| Maya | Java, Calculus, Physics |
| Leo | Java, History |
| Priya | Calculus, Java, Art |

| # | Pipeline | Stream type | Result |
|---|----------|-------------|--------|
| 1 | `.map(s -> s.getName())` | `Stream<String>` | `[Maya, Leo, Priya]`: 3 students → 3 names |
| 2 | `.map(s -> s.getCourses())` | `Stream<List<String>>` | `[[Java, Calculus, Physics], [Java, History], [Calculus, Java, Art]]`: 3 students → 3 **lists** |
| 3 | `.flatMap(s -> s.getCourses().stream())` | `Stream<String>` | `[Java, Calculus, Physics, Java, History, Calculus, Java, Art]`: 3 students → 8 courses |
| 4 | `.flatMap(...).distinct()` / `.flatMap(...).filter(c -> c.equals("Java")).count()` | `Stream<String>`, then `long` | `[Java, Calculus, Physics, History, Art]` / `3` |

### `map` or `flatMap`?

Look at what the lambda returns:

| The lambda returns… | Use | You get |
|---------------------|-----|---------|
| one value (`String`, `int`, `Student`, …) | `map` | a stream of those values |
| a collection, as a **stream** (`list.stream()`) | `flatMap` | one stream of all their elements, merged |

Section 2 is the "wrong" one on purpose. `map` with `getCourses()` *works*, but you get a stream of lists, so you can't `filter` or `distinct` individual courses. `flatMap` **flattens** the lists into one stream of courses. That's what makes section 4 possible.

### The three parts of a pipeline

```java
long javaCount = students.stream()                  // 1. source
        .flatMap(s -> s.getCourses().stream())      // 2. intermediate: Stream<Student> -> Stream<String>
        .filter(course -> course.equals("Java"))    //    intermediate: keeps only "Java"
        .count();                                   // 3. terminal: produces the answer
```

| Step | Examples in the demo | Returns | Takes a… |
|------|----------------------|---------|----------|
| Source | `students.stream()` | a `Stream` | |
| Intermediate | `map`, `flatMap`, `filter`, `distinct` | another `Stream` | `Function` (`map`, `flatMap`) or `Predicate` (`filter`) |
| Terminal | `collect(Collectors.toList())`, `count()` | a result: a `List`, a `long`, … | |

Nothing flows through the pipeline until the **terminal** step runs. A stream doesn't change the original list. `students` still has its three students afterwards.

## Gotchas

- **A stream can only be used once.** After `courseStream.collect(...)`, calling `courseStream.count()` crashes with an `IllegalStateException`. To ask another question, start again from `students.stream()`, as section 4 does.
- **`flatMap`'s lambda must return a `Stream`, not a `List`.** `flatMap(s -> s.getCourses())` doesn't compile. Add `.stream()`.
- **"Students taking Java" really counts *enrollments*.** It gives 3 because each student lists Java once. If Leo's list had Java twice, it would say 4. Try-it #4 fixes this.
- **`getCourses()` returns the student's own list,** not a copy. Code that calls `s.getCourses().add(...)` changes the student. (Streams never do this, because they don't modify their source.)

## Try it

1. Uncomment `courseStream.count();` in section 3, predict what happens, then run it.
2. Replace `s -> s.getName()` with the **method reference** `Student::getName`. Do the same for `getCourses`. Does the output change?
3. Add `.sorted()` to the `distinctCourses` pipeline. Where in the pipeline can it go?
4. Add a second `leo.addCourse("Java");` and rerun. Then count **students** taking Java without `flatMap`: `students.stream().filter(s -> s.getCourses().contains("Java")).count()`. Why do the two numbers differ now?
5. Write a pipeline that gives the **names** of everyone taking Calculus. (Hint: `filter` first, then `map`.)
6. Change `flatMap(s -> s.getCourses().stream())` to `flatMap(s -> s.getCourses())` and read the compile error.
