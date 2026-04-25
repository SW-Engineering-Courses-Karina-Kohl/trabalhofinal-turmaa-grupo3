style guide implementation

this is a subset of google's java style guide

-> indentation: 2 spaces (not tabs)
-> collumn limit: 100 characters to prevent horizontal scrolling
-> naming:
  -> packages: lowercase, no underscore (com.assignment.utils)
  -> classes: UpperCamelCase (noun) (StudentManager)
  -> methods: lowerCamelCase (verb) (calculateGrade())
  -> variables: lowerCamelCase (studentName)
  -> constants: CONSTANT_CASE (MAX_CREDITS)
-> braces:
  -> mandatory even when statements are empty or contain only one line (as with if, else, for, do and while statements)
  -> the opening brace is at the end of the line (after a space) and the closing brace is on a new line
-> parenthesis: a space is placed between the keyword and the opening parenthesis for if, else, for, switch, while and catch statements

version control guidelines

-> before starting any work, always make sure your local main branch is up to date with the remote repository
  -> git checkout main
  -> git pull origin main
-> create a new branch for your feature or bug fix
  -> features: feature/short-description
  -> bugs: bugfix/short-description
-> switching to your new branch: git checkout -b feature/your-feature-name
-> use imperative mode in commit messages ("add user login" as opposed to "added" or "adding")
  -> git add .
  -> git commit -m "add feature description here"
-> if other team members merged code into main while you were working, you need to update your branch to avoid conflicts
  -> git checkout main
  -> git pull origin main
  -> git checkout feature/your-feature-name
  -> git merge main
-> pushing your branch:
  -> git push -u origin feature/your-feature-name
-> creating a pull request:
  -> go to the online repository
  -> open a pull request comparing the main branch with your feature branch
  -> add a brief description of the fixes
  -> once approved, your code will be merged into main
