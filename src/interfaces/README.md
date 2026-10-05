# Interfaces, Static Type vs. Dynamic Type

**Big idea:** An interface is a *contract*: a list of methods a class promises to provide. A variable's **static type** (the type it's declared as) decides which methods you're *allowed to call*. Its **dynamic type** (the type of the actual object) decides *which code runs*.

## Files

| File | Role |
|------|------|
| [`Notifier.java`](Notifier.java) | The interface. It has an abstract method `send`, a constant `MAX_LENGTH` and a `default` method `sendAll`. |
| [`EmailNotifier.java`](EmailNotifier.java) | Implements `Notifier`. It also has an extra method, `getInboxSize()`, that is **not** part of the interface. |
| [`SmsNotifier.java`](SmsNotifier.java) | Implements `Notifier`, cutting messages off at `MAX_LENGTH`. |
| [`NotifierDemo.java`](NotifierDemo.java) | ▶ Seven numbered sections. Each one matches a heading in the program's output. |

## Key concepts

```java
Notifier notifier = new EmailNotifier();
//  ^ static type     ^ dynamic type
```

| Section | Concept | Takeaway |
|---------|---------|----------|
| 1 | Dynamic dispatch | `notifier.send(...)` runs **`EmailNotifier`'s** `send`, even though the variable is declared as `Notifier`. |
| 2 | Static type limits calls | `notifier.getInboxSize()` won't compile. The compiler only knows the object is a `Notifier`. |
| 3 | Casting | `(EmailNotifier) notifier` narrows the static type. Check with `instanceof` first. |
| 4 | Reassignment | The same variable can point to a different implementation later. |
| 5 | Overriding vs. overloading | **Overriding** is chosen at runtime (dynamic type). **Overloading** is chosen at compile time (static type). |
| 6 | Polymorphism | One `Notifier[]` array can hold many implementations. Each one runs its own `send`. |
| 7 | `default` methods | `sendAll` is written once in the interface, and every implementer inherits it. |

### Interface members, without the boilerplate

| You write | Java treats it as |
|-----------|-------------------|
| `int MAX_LENGTH = 160;` | `public static final` |
| `void send(String m);` | `public abstract` |
| `default void sendAll(...) { }` | `public`, with a body that implementers inherit |

## Try it

1. Uncomment `notifier.getInboxSize();` in section 2. Read the compile error. Why does it fail when the object really *is* an `EmailNotifier`?
2. Change the first line to `Notifier notifier = new SmsNotifier();` and run it. Section 2 casts **without** checking, so it crashes with a `ClassCastException`. Comment out section 2's cast and run again. What does section 3's `instanceof` check protect you from?
3. Uncomment `Notifier n = new Notifier();` at the bottom. Why can't an interface be instantiated?
4. Create a `PushNotifier` that implements `Notifier`. Add it to the `channels` array in section 6. You shouldn't need to change any other code. That's the payoff of interfaces.
5. Override `sendAll` in `SmsNotifier` so it prints a header first. Which version does section 7 run now?
6. Send a message longer than 160 characters through `SmsNotifier`. Where does `MAX_LENGTH` come from, given that `SmsNotifier` never declares it?
