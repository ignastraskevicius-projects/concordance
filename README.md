# Concordance

A program that generates a concordance for a text document. Concordance being an alphabetical list of all word occurrences, labeled with word frequencies and locations (sentence number).

## Set up

### Requirements
* Java 17

### 1. Build

./mvnw clean install

### 2. Run

java -jar api/target/api-1.0-SNAPSHOT-jar-with-dependencies.jar api/concordance.input

#### Usage

Input file is called 'concordance.input' located at 'api' module root

Output file will be named 'concordance.output' located at the same directory once program finishes its execution
