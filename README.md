# Object-Oriented Programming and Data Abstraction

This repository contains Java examples discussed during the Object-Oriented Programming and Data Abstraction course. Feel free to explore and work through these examples at any time during the course to strengthen your understanding.

More examples will be added soon!

## Topics

Work through these roughly in order. Each folder has its own README with the big idea, what every file does, and **Try it** exercises.

| # | Topic | Folder | Start by running |
|---|-------|--------|------------------|
| 1 | Interfaces, static vs. dynamic type, polymorphism | [`src/interfaces`](src/interfaces/README.md) | `NotifierDemo` |
| 2 | Sorting your own objects with `Comparable` | [`src/sorting`](src/sorting/README.md) | `SortWithoutComparableDemo`, then `BookDemo` |
| 3 | Functional interfaces and lambdas | [`src/lambdas`](src/lambdas/README.md) | `LambdasDemo` |
| 4 | Many orderings with `Comparator` lambdas | [`src/lambdas/comparators`](src/lambdas/comparators/README.md) | `BookDemo` |

### How to use each example

1. **Read the folder's README.** Begin with the big idea and the files table.
2. **Run the demo** (any file with a `main` method). Compare its output with the README.
3. **Break it on purpose.** Look for comments like `// ^ Uncomment: COMPILE ERROR`. Uncomment the line, predict what will happen, then run it.
4. **Do the "Try it" exercises.** Experiment freely. You can always get the original back with `git checkout -- src/`, or by downloading the repository again.

## Getting Set Up

1. Install a Java Development Kit (JDK). Use the version specified by your instructor; if no version is specified, JDK 17 or newer is recommended. In a terminal, check that Java is available by running:

	```sh
	java --version
	javac --version
	```

2. Install [Visual Studio Code](https://code.visualstudio.com/) and these extensions from the Extensions view:
	- **Extension Pack for Java**
	- **Code Runner** by **Jun Han**

3. Clone this repository, or download it from GitHub. To clone it from a terminal:

	```sh
	git clone https://github.com/SushanthAmbati/OOPDALectureExamples.git
	```

4. In VS Code, open the repository's `OOPDALectureExamples` folder using **File > Open Folder**. Open the repository root, not just the `src` folder, and wait for Java support to finish loading.

5. Open `src/interfaces/NotifierDemo.java`. Select **Run** above the `main` method to compile and run the example. Its output will appear in the VS Code terminal.

## Project Folders

- `src/` contains the Java examples. Explore them, run them, and make your own changes as you work through the course.
- `lib/` is for external `.jar` dependencies when an example needs them.
- `bin/` contains compiled `.class` files. These are generated output; edit the source files in `src/` instead.
- `docs/` contains notes for maintainers. [`docs/ADDING_EXAMPLES.md`](docs/ADDING_EXAMPLES.md) explains the naming conventions and README template used for each topic.

Other examples: `java -cp bin interfaces.NotifierDemo`, `java -cp bin lambdas.comparators.BookDemo`.

## Troubleshooting

If VS Code cannot find Java, confirm that both `java --version` and `javac --version` work in a new terminal. Then open the Command Palette and run **Java: Configure Java Runtime** to check the JDK selected by VS Code.
