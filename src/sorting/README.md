# Sorting Objects with `Comparable`

**Big idea:** Java can sort numbers and `String`s because they already know how to compare themselves. Your own classes don't, until you tell Java what "comes before" means.

## Files

| File | Role |
|------|------|
| [`Book.java`](Book.java) | A book that **implements `Comparable<Book>`**. Its `compareTo` sorts by title, then by author. |
| [`BookDemo.java`](BookDemo.java) | ▶ Runs `Arrays.sort` on `Book` objects. This works. |
| [`BookWithoutComparable.java`](BookWithoutComparable.java) | The same data with **no `Comparable`**. Java can't order it. |
| [`SortWithoutComparableDemo.java`](SortWithoutComparableDemo.java) | ▶ Shows what goes wrong without `Comparable`, plus two ways to fix it. |

▶ = has a `main` method, so you can run it.

**Suggested order:** run `SortWithoutComparableDemo` first (the problem), then `BookDemo` (the solution).

## Key concepts

### The `compareTo` contract

`a.compareTo(b)` returns an `int`:

| Return value | Meaning |
|--------------|---------|
| negative | `a` comes **before** `b` |
| `0` | `a` and `b` are tied |
| positive | `a` comes **after** `b` |

Only the sign matters. `-1` and `-500` mean the same thing.

### Tie-breaking

`Book.compareTo` compares titles first. When two titles are equal (`result == 0`), it falls back to comparing authors. That's why `"Java Basics" by Adams` is printed before `"Java Basics" by Smith`.

### Without `Comparable`, two different failures

| Call | What happens | Why |
|------|--------------|-----|
| `Arrays.sort(array)` | **Compiles**, then the program **crashes** with a `ClassCastException` when it runs | `Arrays.sort(Object[])` accepts any array. Each element is cast to `Comparable` only while sorting. |
| `Collections.sort(list)` | **Compile error** | Its signature is `<T extends Comparable<? super T>>`, so the compiler checks the type first. |

### Two ways to fix it

1. **Give the class a natural order:** `implements Comparable<Book>` and write `compareTo`. This is what `Book` does. Use it when there's one obvious way to order the objects.
2. **Pass a `Comparator`:** `Arrays.sort(books, (b1, b2) -> ...)`. Use it when you can't change the class, or when you need several orderings. See [`../lambdas/comparators`](../lambdas/comparators/README.md).

## Try it

1. In `BookDemo`, uncomment `System.out.println(books[0].compareTo(books[1]));`. Predict whether the number will be negative, zero or positive, then run it.
2. Change `Book.compareTo` so it sorts by **author first**, then by title. How does the output change?
3. Make the order **reverse alphabetical**. (Hint: what happens if you swap `this` and `b`?)
4. In `SortWithoutComparableDemo`, uncomment `Arrays.sort(books);` in section 1 and run it. The program crashes. Read the error message. Which line of your code does it point to? Comment the line out again before moving on.
5. Uncomment `Collections.sort(bookList);`. Read the compiler's error message carefully. Can you find the words "Comparable" or "bounds"?
6. In step 3 of `SortWithoutComparableDemo`, the comparator only looks at titles, so "Smith" stays ahead of "Adams". Why? Change the lambda so it breaks ties by author.
7. Add a `year` field to `Book` and sort by year instead. Does your `compareTo` still work when two years are equal?
