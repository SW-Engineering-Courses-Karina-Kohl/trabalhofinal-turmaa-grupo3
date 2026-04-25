# Java Assignment: Style Guide Implementation

This project adheres to a subset of the **Google Java Style Guide**. The following conventions are enforced to ensure code readability, consistency, and maintainability.

## 1. Source File Structure
* **Indentation:** We use **2 spaces** for indentation (not tabs).
* **Column Limit:** Code is kept within a **100-character** limit per line to prevent horizontal scrolling.

## 2. Naming Conventions
The following naming patterns should be followed:

| Element | Style | Example |
| :--- | :--- | :--- |
| **Packages** | Lowercase, no underscores | `com.assignment.utils` |
| **Classes** | UpperCamelCase (Noun) | `StudentManager` |
| **Methods** | lowerCamelCase (Verb) | `calculateGrade()` |
| **Variables** | lowerCamelCase | `studentName` |
| **Constants** | CONSTANT_CASE | `MAX_CREDITS` |

## 3. Formatting & Braces
* **Braces are Mandatory:** Braces are used with `if`, `else`, `for`, `do`, and `while` statements, even when the body is empty or contains only a single statement.
* **Egyptian Brackets:** The opening brace is at the end of the line, and the closing brace is on a new line.
```java
// Correct usage of braces even for single statements
if (condition) {
  doSomething();
}

// Correct for empty blocks
while (condition) {}
```

## 4. Whitespace and Keywords
According to the Google Style Guide, a single ASCII space separates certain keywords from the following parenthesis and braces.
* **Keywords with Spaces:** A space is placed between the keyword and the opening parenthesis for the following: `if`, `else`, `for`, `switch`, `while`, and `catch`.
* **Open Braces:** A space always precedes the opening brace `{`.

  **Example:**
    ```java
    // Space after 'if', 'while', and before '{'
    if (condition) {
      while (test) {
        // code
      }
    } else {
      // code
    }
