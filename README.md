## 🎨 Style Guide Implementation

This project follows a subset of **Google's Java Style Guide**. Please adhere to the following formatting standards:

**Indentation:** Use **2 spaces** for indentation (do not use tabs). 
**Column Limit:** Keep lines under **100 characters** to prevent horizontal scrolling.


**Naming Conventions:**

`packages`: lowercase, no underscores (e.g., `com.assignment.utils`).

`classes`: `UpperCamelCase` and typically a noun (e.g., `StudentManager`).
 
`methods`: `lowerCamelCase` and typically a verb (e.g., `calculateGrade()`).
 
`variables`: `lowerCamelCase` (e.g., `studentName`).
 
`constants`: `CONSTANT_CASE` (e.g., `MAX_CREDITS`).

**Braces:**
* Mandatory even when statements are empty or contain only a single line (applicable to `if`, `else`, `for`, `do`, and `while`).

* The opening brace must be at the end of the line (preceded by a space), and the closing brace must start on a new line.

**Parentheses:** A space must be placed between the keyword and the opening parenthesis for `if`, `else`, `for`, `switch`, `while`, and `catch` statements.

---

## 🌿 Version Control Guidelines

### 1. Start Your Work

Before writing any code, always ensure your local `main` branch is entirely up-to-date with the remote repository:
```bash
git checkout main
git pull origin main
```
### 2. Feature & Bug Fix Branches

Create a descriptive branch for your isolated task. Use the following prefixes:

**Features:** `feature/short-description` 
 
**Bugs:** `bugfix/short-description` 
Switch to your new branch using:
```bash
git checkout -b feature/your-feature-name
```

### 3. Committing Code
Use the **imperative mode** in your commit messages (e.g., `"add user login"` instead of `"added"` or `"adding"`):
```bash
git add .
git commit -m "add feature description here"
```

### 4. Syncing with Main (Avoid Conflicts)
If other team members have merged code into `main` while you were working, sync your branch to resolve potential conflicts early:
```bash
git checkout main
git pull origin main
git checkout feature/your-feature-name
git merge main
```

### 5. Pushing & Pull Requests
Push your local branch to the remote repository:
```bash
git push -u origin feature/your-feature-name

```
* Navigate to the online repository.
* Open a **Pull Request (PR)** comparing the `main` branch with your feature branch.
* Provide a brief description of your fixes/additions.
* Once approved, your code will be merged into `main`.

---

## 🐳 Using Docker

Manage your local containers using the following environment commands:

```bash
# Stop and remove existing containers
docker compose down

# Rebuild the environment without using old cache
docker compose build --no-cache

# Boot up the environment containers
docker compose up

```

---

## 📊 Class Diagram

The architectural whiteboard layout and UML class diagram can be accessed via Canva:

👉 **[Access the Canva Whiteboard Workspace](https://canva.link/sg8856046tombjx)** 

---

## 📂 Folder Structure

```
my-servlet-app/
├── pom.xml                                (build and dependency config)
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── yourdomain/
│   │   │           └── app/
│   │   │               ├── controllers/   (servlets: handle http requests)
│   │   │               ├── models/        (data objects: represent state)
│   │   │               ├── services/      (business logic: rules and processing)
│   │   │               └── dao/           (data access: database queries)
│   │   ├── resources/
│   │   │   ├── application.properties     (database credentials, settings)
│   │   │   └── data/
│   │   │       └── internal_data.csv      (static files read internally by java)
│   │   └── webapp/
│   │       ├── index.jsp                  (public views like html/jsp)
│   │       ├── assets/
│   │       │   └── templates/
│   │       │       └── template.csv       (public files users can download)
│   │       └── WEB-INF/
│   │           └── web.xml                (protected server configuration)
│   └── test/
│       ├── java/                          (unit tests mirroring main packages)
│       └── resources/                     (test-specific configurations)
```
