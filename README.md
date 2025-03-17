# My DB Engine

**MyDBEngine** is a custom SQL database engine built using **Java** and **ANTLR**. It provides a robust and efficient solution for database management, with support for advanced features like lexical and semantic analysis.

## Features

- **CRUD Operations**: Supports Create, Read, Update, and Delete functionalities.
- **Query Support**:
  - Filtration
  - Sorting
  - Aggregate Functions (e.g., COUNT, SUM, AVG, etc.)
- **Table Management**:
  - Create and truncate tables.
  - Support for various data types.
- **Indexing**:
  - Oct-Tree multi-dimensional indexes for optimized query performance.

## Installation

1. Clone the repository:
 ```bash
   git clone https://github.com/AbdelrahmanRewaished/My-DB-Engine.git
   cd My-DB-Engine
 ```
2. Build the project using Maven:

```bash
mvn clean install
```

3. Run the application:

```bash
java -jar target/mydbengine.jar
```

## Usage
1. Load the database engine and execute SQL queries.

2. Perform CRUD operations, sorting, and apply filters on your datasets.

3. Create and manage tables with ease using the provided SQL syntax.

4. Leverage oct-tree indexes for enhanced query efficiency.

## Example
```bash
CREATE TABLE students (
    id INT,
    name VARCHAR(50),
    age INT,
    gpa FLOAT
);

INSERT INTO students (id, name, age, gpa) VALUES (1, 'Alice', 20, 3.9);

SELECT * FROM students WHERE age > 18 ORDER BY gpa DESC;
```


