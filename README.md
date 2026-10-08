# Life Hack or Urban Myth?

A native Android flashcard quiz app built with Kotlin in Android Studio.

**Author:** Kagiso Lionel Musuko
**Module:** IMAD5112

> Video demonstration: **[PASTE YOUR UNLISTED YOUTUBE LINK HERE]**

---

## Table of contents
1. [Purpose of the app](#purpose-of-the-app)
2. [Features](#features)
3. [Screenshots](#screenshots)
4. [Design considerations](#design-considerations)
5. [How the app works](#how-the-app-works)
6. [Use of GitHub](#use-of-github)
7. [Use of GitHub Actions](#use-of-github-actions)
8. [Testing](#testing)
9. [How to run the app](#how-to-run-the-app)
10. [References](#references)

---

## Purpose of the app

Life hacks and internet rumours are everywhere, and it is hard to tell a genuine productivity tip from an urban myth that has gone viral. This app is a "Life Hack or Urban Myth?" flashcard game that helps users test their common sense and learn useful (and safe) real-world shortcuts.

The app shows the user one statement at a time and asks them to decide whether it is a real **Hack (True)** or a **Myth (False)**. After each answer it explains why, keeps a running score, and finishes with a personalised result and a review of every statement.

## Features

- **Welcome screen** with a short description of the app, a welcome message and a **Start** button.
- **Flashcard question screen** that shows one statement with **Hack (True)** and **Myth (False)** buttons.
- **Instant feedback** after every answer ("Correct!" or "Wrong!") together with an explanation.
- **Next button** that appears once the question has been answered, and changes to **See Results** on the last question.
- **Score screen** showing the total score and personalised feedback ("Master Hacker!", "Great job!" or "Stay Safe Online! Keep practising!").
- **Review screen** listing every statement, the correct answer, the user's answer and the explanation.
- **Play Again** button that returns to the welcome screen with a fresh quiz.

## Screenshots

| Welcome | Question | Feedback |
|---|---|---|
| ![Welcome screen](screenshots/welcome.png) | ![Question screen](screenshots/question.png) | ![Feedback after answering](screenshots/feedback.png) |

| Score | Review |
|---|---|
| ![Score screen](screenshots/score.png) | ![Review screen](screenshots/review.png) |

**Logging output (Logcat):**

![Logcat output](screenshots/logcat.png)

## Design considerations

- **Simple three-screen flow.** The app follows the structure in the brief: Welcome → Question → Score, with Review as an extra screen. Each screen has a single clear purpose, so users never have to guess what to do next.
- **Large, clear buttons.** The Hack and Myth buttons are full width and stacked so they are easy to tap on any phone size, and the two choices are always in the same place.
- **Immediate feedback.** Users see whether they were right straight after answering, together with a short explanation, so they actually learn something from every card.
- **Preventing mistakes.** After an answer is chosen, both answer buttons are disabled so a question cannot be answered twice or scored twice. The Next button stays hidden until the question has been answered.
- **Progress indicator.** A "Question X of Y" label shows how far through the quiz the user is.
- **Portrait orientation.** The Activities are locked to portrait so that rotating the phone does not reset the quiz.
- **Separation of data and screens.** The flashcards live in their own `Hack` data class and `HackRepository`, so questions can be added or edited without touching the screen code.
- **Layouts.** Each screen uses a `LinearLayout` with centred content and consistent padding and text sizes so the screens look consistent.

## How the app works

### Project structure

| File | Purpose |
|---|---|
| `Hack.kt` | Data class for one flashcard: `statement`, `isTrue`, `explanation` |
| `HackRepository.kt` | Holds the list of flashcards the quiz loops through |
| `MainActivity.kt` | Welcome screen; the Start button opens the quiz |
| `QuestionActivity.kt` | Quiz loop: shows each card, checks the answer, updates the score |
| `ScoreActivity.kt` | Shows the total score, personalised feedback, Review and Play Again |
| `ReviewActivity.kt` | Lists every statement with its correct answer and explanation |
| `res/layout/activity_*.xml` | One layout per screen |

### Application logic

1. **Welcome screen:** when the user taps **Start**, an `Intent` opens `QuestionActivity`.
2. **Question screen:** the app keeps a `currentIndex` and a `score`. It displays `hacks[currentIndex]`, and when the user taps Hack or Myth it compares the choice with `isTrue`. A correct answer increases `score`. The user's choices are stored in an array so the Review screen can show them later.
3. **Looping through questions:** tapping **Next** increases `currentIndex` and loads the next card. When the last card has been answered, the score and answers are passed to `ScoreActivity` through an `Intent`.
4. **Score screen:** a `when` expression picks the feedback message from the percentage score (80% or more: "Master Hacker!", 50% or more: "Great job!", otherwise: "Stay Safe Online! Keep practising!").
5. **Review screen:** a `for` loop builds the list of statements, correct answers, the user's answers and explanations.

### Logging

The app uses `Log.d` with a tag for each Activity to show what the code is doing, for example when the Start button is clicked, when a question is shown, whether an answer was correct and the updated score, and when the quiz finishes. In Android Studio's Logcat these can be filtered with `tag:QuestionActivity`.

## Use of GitHub

GitHub was used for version control throughout the project:

1. A new repository was created with a README file.
2. The repository was cloned to the computer with GitHub Desktop, and the Android Studio project was created inside the cloned folder.
3. The project files were committed and pushed to GitHub.
4. Work was committed and pushed regularly as each part was completed (project setup, data model, layouts, quiz logic, workflow, documentation), so the commit history shows how the app was built step by step.

Repository: **[PASTE YOUR GITHUB REPO LINK HERE]**

## Use of GitHub Actions

A GitHub Actions workflow (`.github/workflows/build.yml`) builds the app automatically so it is proven to work on a clean machine and not just on my computer. It runs when code is pushed to the `main` or `release` branch, and it can also be started manually from the Actions tab.

The workflow:

1. Checks out the code.
2. Sets up JDK 17.
3. Makes `gradlew` executable.
4. Runs the Gradle tests (`./gradlew test`).
5. Builds the project (`./gradlew build`).
6. Builds the debug APK, release APK and release App Bundle.
7. Uploads the generated files as artifacts so they can be downloaded from the run.

![GitHub Actions successful run](screenshots/actions.png)

The workflow is based on the sample provided in the module guide (see References) and adapted for this project.

## Testing

### Manual testing

| # | Test | Expected result | Result |
|---|---|---|---|
| 1 | Open the app | Welcome screen with description and Start button | |
| 2 | Tap **Start** | First question appears as "Question 1 of 10" | |
| 3 | Tap the correct answer | "Correct!" with explanation, score goes up, Next appears | |
| 4 | Tap the wrong answer | "Wrong!" with explanation, score does not change | |
| 5 | Try to tap an answer button twice | Second tap is ignored (buttons disabled) | |
| 6 | Tap **Next** | Next question loads and feedback is cleared | |
| 7 | Answer the last question | Button changes to **See Results** | |
| 8 | Tap **See Results** | Score screen shows total and feedback message | |
| 9 | Answer everything correctly | "Master Hacker!" | |
| 10 | Answer everything incorrectly | "Stay Safe Online! Keep practising!" | |
| 11 | Tap **Review** | All statements, correct answers, user answers and explanations are listed | |
| 12 | Tap **Back to Score** | Returns to the Score screen | |
| 13 | Tap **Play Again** | Welcome screen opens and a new quiz starts at 0 | |

*(Fill in the Result column with Pass or Fail after testing on your device.)*

### Automated testing

GitHub Actions runs the Gradle tests and builds the app on every push to `main`, which confirms the project compiles and builds successfully on a clean machine.

## How to run the app

1. Clone the repository:
   ```
   git clone [PASTE YOUR GITHUB REPO LINK HERE]
   ```
2. Open the project in Android Studio and let Gradle sync.
3. Connect an Android phone with USB debugging turned on, or start an emulator.
4. Click **Run**.

Alternatively, download the debug APK from the **Actions** tab (open the latest successful run and download the artifact), extract it and install the APK on an Android device.

## References

- Android Developers. *Intents and intent filters.* https://developer.android.com/guide/components/intents-filters
- Android Developers. *Log class.* https://developer.android.com/reference/android/util/Log
- Android Developers. *LinearLayout.* https://developer.android.com/reference/android/widget/LinearLayout
- Kotlin documentation. *Data classes.* https://kotlinlang.org/docs/data-classes.html
- GitHub Docs. *GitHub Actions.* https://docs.github.com/en/actions
- GitHub Marketplace. *Automated build Android app with GitHub action.* https://github.com/marketplace/actions/automated-build-android-app-with-github-action
- IMAD5112. *Sample build.yml workflow.* https://github.com/IMAD5112/Github-actions/blob/main/.github/workflows/build.yml