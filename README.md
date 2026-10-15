# CipherVault 🔐

A command-line password manager written in Java, built as an independent project in spring 2026 to practice secure authentication design.

## Features
- **Account management** – create, search, and delete accounts
- **Secure login** – masked password input to prevent shoulder-surfing
- **Brute-force protection** – limits failed login attempts
- **Password strength rules** – enforces length, letters, numbers, and special characters
- **Persistent storage** – saves and loads account data between runs using file I/O
- **Input validation** – modular, multi-class design with error handling for invalid input

## How to Run
Requires Java (JDK 17 or later).

```bash
javac *.java
java PasswordManager
```

## Security Notes & Planned Improvements
This is a learning project. Account data is currently stored locally in plain text, which means anyone with access to the file could read it. Planned improvements:
- [ ] Hash stored passwords with a salted algorithm (e.g., bcrypt or PBKDF2)
- [ ] Encrypt the account data file at rest
- [ ] Add a lockout timer after repeated failed logins

## Built With
Java · VS Code
