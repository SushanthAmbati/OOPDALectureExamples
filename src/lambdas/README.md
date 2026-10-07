# Understanding Lambdas

**Big idea:** A lambda is a short way to create *an object that implements a functional interface*, which is an interface with exactly one abstract method. The interface supplies the method's **name and signature**. The lambda supplies its **body**. `(a, b) -> a * b` does the same job as writing a whole `Multiplier implements IntegerMath` class.

## Files

| File | Role |
|------|------|
| [`IntegerMath.java`](IntegerMath.java) | The functional interface. It has one method: `int operation(int a, int b)`. |
| [`Adder.java`](Adder.java), [`Subtractor.java`](Subtractor.java) | The "long way": full classes that implement `IntegerMath`. |
| [`IntegerCheck.java`](IntegerCheck.java), [`TextCheck.java`](TextCheck.java) | Two more home-made functional interfaces, `(int) -> boolean` and `(String) -> boolean`. Together they show the repetition problem. |
| [`Calculator.java`](Calculator.java) | `operateBinary(a, b, op)` accepts **any** `IntegerMath` and doesn't know which one it got. `checkNumber` and `checkText` do the same for the two check interfaces. |
| [`LambdasDemo.java`](LambdasDemo.java) | ▶ Compares named classes with lambdas. |
| [`FunctionalInterfaceDemo.java`](FunctionalInterfaceDemo.java) | ▶ Shows why `java.util.function` exists: `Predicate`, `Function`, `Consumer`, `Supplier`. |
| [`comparators/`](comparators/README.md) | A practical use: sorting with lambdas. |

## From class to lambda

All four of these produce an `IntegerMath` that multiplies:

```java
// 1. A named class (like Adder / Subtractor)
class Multiplier implements IntegerMath {
    public int operation(int a, int b) { return a * b; }
}
IntegerMath m1 = new Multiplier();

// 2. An anonymous class: no name, defined right where it's used
IntegerMath m2 = new IntegerMath() {
    public int operation(int a, int b) { return a * b; }
};

// 3. A lambda stored in a variable
IntegerMath m3 = (a, b) -> a * b;

// 4. A lambda passed directly as an argument
myApp.operateBinary(40, 2, (a, b) -> a * b);
```

## How a lambda actually works

Four rules:

1. **A lambda can only be used where a functional interface is expected.**
2. **The interface provides the method name and signature.**
3. **The lambda provides the body.**
4. **Together they form an object. Calling the interface method runs the lambda's body.**

### Each side supplies half

| Supplied by | What | In this example |
|-------------|------|-----------------|
| The interface (`IntegerMath`) | the method **name** and **signature** | `operation`, shape `(int, int) -> int` |
| The lambda | the method **body** | `a + b` |

The compiler combines the two halves into one object. Its `operation` method has the interface's signature and the lambda's body.

### Tracing one call

```java
myApp.operateBinary(40, 2, (a, b) -> a + b);

// Inside Calculator:
public int operateBinary(int a, int b, IntegerMath op) {
    return op.operation(a, b);   // runs the lambda's body with a = 40, b = 2  →  42
}
```

1. `operateBinary` expects an `IntegerMath`, which is a functional interface, so a lambda is allowed here (rule 1).
2. The compiler builds an object whose `operation(int a, int b)` (rule 2) has the body `a + b` (rule 3).
3. `op` refers to that object. `op.operation(40, 2)` runs the body and returns `42` (rule 4).

### A common misconception

> ❌ "The abstract method *gets* its method from the lambda."

The abstract method `int operation(int a, int b);` is only a **declaration**: a name and a signature with no body. It doesn't fetch or receive anything. A better way to say it:

> ✅ "The lambda body **becomes the implementation** of the abstract method, in an object of that interface type."

Also, the lambda **doesn't** define the method's name. `(a, b) -> a + b` never says `operation`. That name always comes from the interface.

### The signature explains the "magic"

- **Why can `(a, b)` leave out the types?** The compiler reads them from `operation(int a, int b)`. Both are `int`.
- **Why is there no `return`?** A single-expression body returns its value automatically. A block body needs `return`: `(a, b) -> { return a + b; }`.
- **Why must the shape match?** The lambda has to fit the signature exactly. Each of these is a **compile error** for `IntegerMath`:

  ```java
  IntegerMath bad1 = (a) -> a + 1;           // one parameter, but operation takes two
  IntegerMath bad2 = (a, b, c) -> a + b + c; // three parameters, but operation takes two
  IntegerMath bad3 = (a, b) -> "sum";        // returns a String, but operation returns int
  ```

### Where can a lambda go?

Anywhere the **expected type** is a functional interface, not only on the right of an `=`:

| Place | Example |
|-------|---------|
| Assigned to a variable | `IntegerMath mul = (a, b) -> a * b;` |
| Passed as an argument | `myApp.operateBinary(40, 2, (a, b) -> a + b);` |
| Returned from a method | `static IntegerMath pickOp() { return (a, b) -> a - b; }` |

A lambda by itself has no type, so `var f = (a, b) -> a + b;` **doesn't compile**. Java needs a target type to know which interface it is.

### Check yourself

1. **True or false:** "The lambda `(a, b) -> a + b` defines a method named `operation`."
2. In `IntegerMath op = (a, b) -> a * b;`, how does the compiler know that `a` and `b` are `int`s?
3. Will `IntegerMath half = (a) -> a / 2;` compile? Why or why not?
4. Is `Calculator.operateBinary` aware that it was given a lambda rather than an `Adder`? Does it need to be?

<details>
<summary>Answers</summary>

1. **False.** The name `operation` comes from the interface. The lambda only supplies the body.
2. From the abstract method's signature, `int operation(int a, int b)`. The target type `IntegerMath` tells the compiler which signature to use.
3. **No.** `operation` takes two parameters, so a one-parameter lambda doesn't match the shape.
4. **No, and no.** It only knows it has an `IntegerMath` and calls `op.operation(a, b)`. Whether that object came from a class or a lambda doesn't matter.

</details>

## Built-in functional interfaces (`java.util.function`)

Run [`FunctionalInterfaceDemo`](FunctionalInterfaceDemo.java). Its numbered sections match the steps below.

1. **Recap.** `IntegerMath` describes one shape, `(int, int) -> int`.
2. **The repetition problem.** To ask "is this number even?" we need a *different* shape, `(int) -> boolean`, so we invent `IntegerCheck`. Then "is this string empty?" needs `(String) -> boolean`, so we invent `TextCheck`. Both say "take something, answer true/false". Only the type changes, and `Calculator` grows a new method each time.
3. **Java already has these shapes.** The `java.util.function` package provides a generic interface for each common shape. Your lambda doesn't change. Only the interface type you assign it to does.
4. **The payoff.** Library methods already accept these types, so your lambdas plug straight in: `list.removeIf(...)` takes a `Predicate`, and `list.forEach(...)` takes a `Consumer`.

### The common functional interfaces

| Interface | Description | Functional method |
|-----------|-------------|-------------------|
| `Runnable` | An operation that accepts **no** input arguments and **returns no result**. | `void run()` |
| `Supplier<R>` | An operation that accepts **no** input arguments and **returns an object of type `R`**. | `R get()` |
| `Consumer<T>` | An operation that accepts a **single** input argument and **returns no result**. | `void accept(T t)` |
| `BiConsumer<T, U>` | An operation that accepts **two** input arguments and **returns no result**. | `void accept(T t, U u)` |
| `Function<T, R>` | A function that accepts **one** argument and **produces a result**. | `R apply(T t)` |
| `BiFunction<T, U, R>` | A function that accepts **two** arguments and **produces a result**. | `R apply(T t, U u)` |
| `Predicate<T>` | A predicate (**`boolean`**-valued function) of **one** argument. | `boolean test(T t)` |
| `BiPredicate<T, U>` | A predicate (**`boolean`**-valued function) of **two** arguments. | `boolean test(T t, U u)` |

`Runnable` lives in `java.lang`, so it needs no import. All the others are in `java.util.function`. The **Bi-** versions are the same idea with a second argument. `Predicate<T>` is the generic replacement for our `IntegerCheck` and `TextCheck`.

Notice that the **method name changes with the interface**: `run`, `get`, `accept`, `apply`, `test`. This is rule 2 again. The interface supplies the name, and the lambda only supplies the body. The same lambda `n -> n % 2 == 0` can become an `IntegerCheck` (call `.test`) or a `Predicate<Integer>` (also `.test`), depending on its target type.

### Gotchas

- **Same shape doesn't mean same type.** A `Predicate<Integer>` can't be passed where an `IntegerCheck` is expected, even though both are `int -> boolean` in spirit. Java matches interface **types**, not shapes. (A lambda written *inline* works for either, because its type comes from where it's used.)
- **Generics need wrapper types.** It's `Predicate<Integer>`, not `Predicate<int>`. Java converts between `int` and `Integer` for you (autoboxing).
- **Calling the wrong method name** fails to compile: `isEven.apply(8)` is an error because `Predicate`'s method is `test`.

## Try it

1. Write a **division** operation two ways: as a class, and as a lambda. Pass each one to `operateBinary`.
2. Add a second abstract method to `IntegerMath`. What happens to every lambda in the project? Why?
3. Write a lambda with a block body: `(a, b) -> { int r = a % b; return r; }`. When do you need braces and `return`?
4. Store several lambdas in a `java.util.Map<String, IntegerMath>` (for example `"+"`, `"-"`, `"*"`), then look one up by symbol. This is the same pattern `comparators/Book.java` uses.
5. In `FunctionalInterfaceDemo`, uncomment `isEven.apply(8);` and read the error. Then uncomment `myApp.checkNumber(8, isEven);`. Why does it fail, when `myApp.checkNumber(8, n -> n % 2 == 0)` works?
6. Replace `IntegerCheck` and `TextCheck` with `Predicate<Integer>` and `Predicate<String>` in `Calculator`. Can you now delete both interfaces? Can `checkNumber` and `checkText` become one generic method?
7. Write a `Function<Integer, String>` that turns a number into `"even"` or `"odd"`, and a `Supplier<Integer>` that returns a random die roll from 1 to 6.
