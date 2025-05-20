<h1 align="center">Calingo - Mobile App</h1>

<p align="center"> <img alt="Status" src="https://img.shields.io/badge/status-in%20development-yellow" /> <img alt="Java" src="https://img.shields.io/badge/Java-007396?logo=java&logoColor=white" /> <img alt="Android" src="https://img.shields.io/badge/Android-3DDC84?logo=android&logoColor=white" /> <img alt="Retrofit" src="https://img.shields.io/badge/Retrofit-6E7C7C?logo=retrofit&logoColor=white" /> <img alt="Room" src="https://img.shields.io/badge/Room-3DDC84?logo=android&logoColor=white" /> <img alt="JUnit" src="https://img.shields.io/badge/JUnit-25A162?logo=junit5&logoColor=white" /> <img alt="Tests" src="https://img.shields.io/badge/tests-passing-brightgreen" /> <img alt="Repo size" src="https://img.shields.io/github/repo-size/CalingoTeam/Mobile" /> <img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/CalingoTeam/Mobile" /> <img alt="CodeQL" src="https://github.com/CalingoTeam/Mobile/actions/workflows/codeql.yml/badge.svg" /> <img alt="Dependabot enabled" src="https://img.shields.io/badge/Dependabot-enabled-brightgreen?logo=dependabot" /> </p>

<p align="center">
  The <strong>Calingo</strong> mobile app brings regional Brazilian expressions and quizzes to your pocket! This version allows users to learn, play, and explore the rich diversity of Brazilian Portuguese anywhere.
</p>

## 📲 Main Features

- Expression search with filters
- View meanings, examples, and origins
- Regional map-based browsing
- Take quizzes and track your score
- Login to save your progress and suggestions

## 🛠️ Tech Stack

- **Android Studio**
- **Java**
- **Retrofit** for API calls
- **Room** (optional, for local cache)
- **Jetpack Components**
- **JUnit**

## 🚀 Setup

1. Clone the repo:
```bash
git clone https://github.com/CalingoTeam/Mobile.git
````

2. Open in **Android Studio**

3. Sync Gradle and Run on emulator or device.

> ⚠️ Ensure the Calingo API is running and the base URL is correctly configured in your networking setup (e.g., Retrofit).

## 🔐 Authentication

* Uses JWT tokens from the Back-End
* Login is optional but required for:

  * Submitting suggestions
  * Viewing user-specific data

## 📁 Structure Overview

```

Mobile/
├── .github/                    # CI/CD and GitHub Actions (optional)
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/calingo  # Main source code
│   │   │   ├── res/                      # Layouts, drawables, strings
│   │   │   └── AndroidManifest.xml
│   │   ├── test/                         # Unit tests
│   │   └── androidTest/                 # Instrumentation tests
├── build.gradle                # Project-level build script
├── gradle.properties
├── settings.gradle
├── proguard-rules.pro
├── gradlew / gradlew\.bat       # Gradle wrappers

````gradlew / gradlew.bat # Gradle wrappers
```

## 🤝 Contributors

Developed by [CalingoTeam](https://github.com/CalingoTeam).
