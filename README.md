# Mini DB – A tiny database from scratch

A simple key-value database implemented in Java for learning how databases work internally.

## Features
- Persistent storage in a single data file
- Commands: SET, GET, DELETE, LIST
- B+Tree-based index for fast lookups

## Build & Run
Prerequisites:
- Java 17+
- Maven

Run:
```bash
mvn compile exec:java -Dexec.mainClass="com.mujahidmariyam7089-prog.minidb.Main"
```

## Project structure
- `Database.java` – main DB logic
- `BPlusTree.java` – in-memory index
- `DiskManager.java` – file I/O
- `CommandParser.java` – simple CLI parser
- `Page.java` – fixed-size page abstraction (optional helper)