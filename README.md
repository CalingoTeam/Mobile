# Calingo - Mobile App

The **Calingo** mobile app brings regional Brazilian expressions and quizzes to your pocket! This version allows users to learn, play, and explore the rich diversity of Brazilian Portuguese anywhere.

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
