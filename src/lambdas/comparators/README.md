# Comparators: Many Ways to Sort the Same Objects

**Big idea:** `Comparable` gives a class **one** natural order. A `Comparator` is a separate object (often a lambda) that describes **another** order. You can have as many comparators as you like.

> Start with [`../../sorting`](../../sorting/README.md) if you haven't seen `Comparable` yet.

## Files

| File | Role |
|------|------|
| [`Book.java`](Book.java) | Natural order (`compareTo`): title, then author. It also holds four `Comparator` lambdas and a `Map` of them, keyed by name. |
| [`BookDemo.java`](BookDemo.java) | ▶ Sorts a `List<Book>` in different ways. Sections 2–4 are commented out. Uncomment them one at a time. |

## Key concepts

| | `Comparable<T>` | `Comparator<T>` |
|---|---|---|
| Where it lives | Inside the class (`implements`) | Anywhere: a field, a variable or an inline lambda |
| Method | `a.compareTo(b)` | `c.compare(a, b)` |
| How many | One per class | As many as you want |
| Used by | `list.sort(null)`, `Arrays.sort(arr)` | `list.sort(c)`, `Arrays.sort(arr, c)` |

The map keys in `Book.comparators` are: `yearAsc`, `yearDesc`, `author` and `yearDescTitle`.

## Gotchas

- **A misspelled key fails silently.** `Book.comparators.get("sortByYearDesc")` returns `null`, because the key is `"yearDesc"`. Then `list.sort(null)` quietly uses the **natural order** instead of crashing. If a sort "doesn't work", print the comparator first.
- **Subtraction comparators can overflow.** `b1.getPublicationYear() - b2.getPublicationYear()` is fine for years, but for very large or very negative `int`s, the subtraction can wrap around and flip the sign. The safe versions are `Integer.compare(x, y)` or `Comparator.comparingInt(Book::getPublicationYear)`.

## Try it

1. Uncomment section 2 (`bookSet.sort(null)`). Which method does Java call to sort?
2. Uncomment section 3. It loops over every comparator. Notice that `bookSet` is cleared and refilled each time. What would happen without that?
3. Uncomment section 4 and pick a comparator at the prompt. Then type a name that doesn't exist.
4. Add a `sortByTitleLength` comparator, put it in the map, and pick it from the menu.
5. Rewrite `sortByYearDescTitle` with the built-in helpers:
   `Comparator.comparingInt(Book::getPublicationYear).reversed().thenComparing(Book::getTitle)`.
   Does it give the same output?
