# Exceptions: Throwing, Catching and Custom Exceptions

**Big idea:** When a method hits a problem it can't fix, it **throws** an exception. The exception travels **back up the call chain** until a method that *can* do something about it **catches** it. Each method should catch only what it can actually fix. **Custom exceptions** give each kind of problem its own type, and can carry the data the catcher needs.

## The example

A user asks for `cake.png`. The search works in layers, and each layer calls the one below it:

```
Driver ──▶ LanguageManipulator ──▶ ExtensionManipulator ──▶ FileOpener
            cake, pastel,            cake.png, cake.jpg,      opens ONE file
            gateau, kuchen…          cake.gif
                    ◀──────── exceptions travel back UP ────────
```

`DemoSetup` creates an `images/` folder in the directory you run from (normally the repository root). It holds two files:

| File | Stands for |
|------|------------|
| `kuchen.gif` | a real image ("cake" in German). There's deliberately **no** `cake.png`. |
| `cookie.png` | an **empty** file, standing in for a corrupt image |

Both drivers then make the same three searches: `cake.png` (found via a translation), `pie.png` (doesn't exist anywhere) and `cookie.png` (exists, but is broken).

## Files

The same five classes appear in both versions. Only the way they report problems changes.

| File | Role |
|------|------|
| `FileOpener` | **Lowest level.** Opens exactly one file. It never recovers, it only reports (`throws`). |
| `ExtensionManipulator` | **Middle level.** Tries `.png`, `.jpg` and `.gif`. It **catches** a failure and **recovers** by trying the next extension. |
| `LanguageManipulator` | **Top search level.** Tries the original word, then translations. It has a **nested `try`/`catch`** inside a `catch`. |
| `Driver` | ▶ **Last stop.** Decides what to tell the user. |
| `OpenedImage` | A `record` holding the file name and bytes (Java 16+). |
| `DemoSetup` | Setup only. Creates the `images/` folder. |

| Version | Folder | Extra files |
|---------|--------|-------------|
| **v1: built-in exceptions only** | [`v1/`](v1/Driver.java) | none |
| **v2: custom exceptions** | [`v2/`](v2/Driver.java) | [`CorruptImageException`](v2/CorruptImageException.java), [`ImageNotFoundException`](v2/ImageNotFoundException.java) |

**Run [`v1/Driver`](v1/Driver.java) first, then [`v2/Driver`](v2/Driver.java),** and compare the third search.

## Key concepts

### The keywords

| Keyword | Meaning | Example |
|---------|---------|---------|
| `throw` | "Something went wrong. Stop here and report it." | `throw new CorruptImageException(fileName);` |
| `throws` | Part of a method's signature: "calling me might fail with this, so be ready" | `public OpenedImage open(String f) throws IOException` |
| `try` / `catch` | "Run this. If *that type* of exception happens, run this instead." | `ExtensionManipulator.find` |

### Catch it, or pass it up?

A method that calls something risky has two choices:

1. **Catch it**, if it can do something useful. `ExtensionManipulator` catches a missing file because trying the next extension might fix it.
2. **Declare it with `throws`** and let it pass up, if it can't. `FileOpener` has no idea what the caller wants, so it just reports.

### Checked vs. unchecked

| | Checked | Unchecked |
|---|---------|-----------|
| Extends | `Exception` (but not `RuntimeException`) | `RuntimeException` |
| Compiler forces you to catch or declare it? | **Yes** | No |
| Usually means | A problem from **outside** the program: a missing file, the network | A **bug**: `null`, a bad index |
| Examples | `IOException`, our two custom exceptions | `NullPointerException`, `ArrayIndexOutOfBoundsException` |

### v1 → v2: why write your own exceptions?

Look at the third search, `cookie.png`:

| | v1 (built-in) | v2 (custom) |
|---|---------------|-------------|
| `FileOpener` reports a broken file as… | a plain `IOException` | `CorruptImageException` |
| `ExtensionManipulator` catches… | **every** `IOException`, so "missing" and "broken" look the same | only `NoSuchFileException`, the one problem it can fix |
| The broken `cookie.png`… | is treated as missing, so the search keeps going and fails | passes straight up to the `Driver` |
| `Driver` tells the user | "could not find cookie.png" ❌ wrong | "Found cookie.png, but it's damaged" ✅ |
| `Driver` learns which files were tried by… | picking apart a message `String` | calling `e.getAttemptedFiles()` |

Two reasons to write a custom exception:

1. **A distinct type.** A `catch` block can target exactly that problem, so the `Driver` has one `catch` per problem, each with its own response.
2. **Extra data.** An exception is an object, so it can have fields and getters, like `getFileName()`, `getSearchTerm()` and `getAttemptedFiles()`.

To make one, `extends Exception` (checked) and pass a message to `super(...)`.

## Gotchas

- **Catching too much hides problems.** v1's `catch (IOException e)` swallows a broken file as if it were a missing one. Catch the **most specific** type you can actually handle.
- **When catch types are related, put the subclass first.** `NoSuchFileException` is a subclass of `IOException`. If `catch (IOException e)` came before `catch (NoSuchFileException e)`, the general block would catch everything and the specific one could never run. That's a compile error: *"exception NoSuchFileException has already been caught"*. The three catch blocks in [`v2/Driver.java`](v2/Driver.java) are unrelated types (our two custom exceptions extend `Exception`, not `IOException`), so their order there doesn't matter.
- **`images/` is created where you run from.** If you run from a different folder, a new `images/` appears there. The repository's `.gitignore` ignores it at the root.
- **`OpenedImage` is a `record`,** which needs Java 16 or newer. Accessors have no `get`: `image.fileName()`, `image.bytes()`.

## Try it

1. Run v1. Find the line where `cookie.png` fails with an `IOException` rather than a `NoSuchFileException`. Why does the search keep going afterwards?
2. In `v2/ExtensionManipulator.java`, add `catch (IOException e) { throw e; }` **before** `catch (NoSuchFileException e)` and read the compile error. Then try moving `catch (IOException e)` to the top in `v2/Driver.java`. Why does that one still compile?
3. Remove `throws CorruptImageException` from `v2/FileOpener.open`. Which lines stop compiling, and why? Would that happen if `CorruptImageException` extended `RuntimeException`?
4. Add `"pie"` to the `TRANSLATIONS` map in v2 (for example `"tarta"`, `"tarte"`, `"torte"`). Search for `pie.png` again. How long is the "Files tried" list? Which class built it?
5. Suppose `LanguageManipulator` caught `CorruptImageException` and kept trying translations. Would that be a good idea for `cookie.png`? Which layer *should* decide what to do about a broken file?
