# RoleFit AI

## AI-Powered Job & Skill Matching for Android

RoleFit AI helps candidates compare their existing skills with a job description and understand their job fit through an AI-powered analysis.

[![Android](https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com/)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.2.10-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org/)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-Material%203-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)](https://developer.android.com/compose)
[![Supabase](https://img.shields.io/badge/Supabase-Backend-3ECF8E?style=for-the-badge&logo=supabase&logoColor=white)](https://supabase.com/)
[![Google Gemini](https://img.shields.io/badge/Google%20Gemini-AI-8E75B2?style=for-the-badge&logo=google&logoColor=white)](https://ai.google.dev/)

---

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Architecture](#architecture)
- [How It Works](#how-it-works)
- [AI Response](#ai-response)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Requirements](#requirements)
- [Setup](#setup)
- [Build](#build)
- [Security](#security)
- [Application Flow](#application-flow)
- [Testing](#testing)
- [Error Handling](#error-handling)
- [Current Android Configuration](#current-android-configuration)
- [Screenshots](#screenshots)
- [Roadmap](#roadmap)
- [Author](#author)
- [Hackathon](#hackathon)
- [License](#license)

---

## Overview

**RoleFit AI** is an Android application designed to help candidates understand how their current skill set aligns with a target job.

The user provides:

1. Their current skills
2. A target job description

RoleFit AI sends the request through a Supabase Edge Function to Google Gemini and returns a structured job-matching analysis.

### Analysis Includes

| Result | Description |
|---|---|
| **Match Score** | Overall match score from 0–100 |
| **Matched Skills** | Skills that align with the job requirements |
| **Missing User Skills** | Job-relevant skills not present in the supplied skill set |
| **Job Skills** | Important skills and requirements identified from the job description |
| **Recommendations** | Suggested areas for improvement |
| **Summary** | AI-generated overview of the candidate/job alignment |

> **Core concept:** Convert a raw job description into a practical skill-gap analysis.

---

## Features

### Authentication

- Email/password sign-up
- Email/password login
- Supabase Auth integration
- Session-aware navigation
- Logout functionality
- Friendly authentication error messages

### AI Job Analysis

- Enter a personal skill set
- Paste a complete job description
- AI-powered skill matching
- Match score generation
- Matched skill identification
- Missing skill identification
- Job requirement extraction
- Personalized recommendations
- AI-generated summary

### User Experience

- Jetpack Compose interface
- Material 3 design
- Scrollable input screen
- Scrollable results screen
- Long AI responses remain readable
- Friendly success and error messages
- Home, Analyzer, and Results navigation
- Android emulator support
- Physical Android device support

---

## Architecture

```text
┌──────────────────────────────┐
│         Android App          │
│      Kotlin + Compose        │
└──────────────┬───────────────┘
               │
               │ Authenticated Request
               ▼
┌──────────────────────────────┐
│       Supabase Auth          │
└──────────────┬───────────────┘
               │
               ▼
┌──────────────────────────────┐
│   Supabase Edge Function     │
│        analyze-job           │
└──────────────┬───────────────┘
               │
               │ Server-side API Request
               ▼
┌──────────────────────────────┐
│       Google Gemini          │
│         AI Analysis          │
└──────────────┬───────────────┘
               │
               │ Structured JSON
               ▼
┌──────────────────────────────┐
│      Android Result UI       │
│ Score • Skills • Gaps • Tips │
└──────────────────────────────┘
```

### Why This Architecture?

The Android application does **not** call Gemini directly.

Instead:

```text
Android App
     │
     ▼
Supabase Edge Function
     │
     ▼
Google Gemini
```

This keeps the Gemini API credential on the server side instead of embedding it in the Android application.

---

## How It Works

### Step 1 — User Authentication

The user creates an account or signs in through Supabase Auth.

### Step 2 — Enter Skills

The user enters their existing technical and professional skills.

Example:

```text
Python, SQL, Docker, Git, REST API, Linux, FastAPI, PostgreSQL
```

### Step 3 — Paste Job Description

The user pastes the target job description.

Example:

```text
Backend Developer requiring Python, REST APIs, SQL/PostgreSQL,
Docker, Git, Linux, and FastAPI.
```

### Step 4 — AI Analysis

The Android application sends the request to the Supabase Edge Function:

```text
analyze-job
```

The Edge Function sends the analysis request to Google Gemini.

### Step 5 — Structured Result

Gemini returns structured JSON.

The Edge Function passes the result back to the Android application.

### Step 6 — Display Results

The application displays:

- Match Score
- Summary
- Matched Skills
- Skills Not Relevant / Missing
- Job Skills & Requirements
- Recommendations

---

## AI Response

The application expects a structured response similar to:

```json
{
  "matchScore": 85,
  "matchedSkills": [
    "Python",
    "SQL",
    "Docker"
  ],
  "missingUserSkills": [
    "Kubernetes"
  ],
  "jobSkills": [
    "Python",
    "SQL",
    "Docker",
    "Kubernetes"
  ],
  "recommendations": [
    "Learn Kubernetes fundamentals",
    "Build a production-style deployment project"
  ],
  "summary": "The candidate has a strong foundation for this role..."
}
```

The actual output depends on the submitted skills and job description.

---

## Tech Stack

| Layer | Technology |
|---|---|
| **Programming Language** | Kotlin |
| **UI Framework** | Jetpack Compose |
| **Design System** | Material 3 |
| **Android** | Android SDK |
| **Serialization** | Kotlin Serialization |
| **Networking** | Ktor |
| **Authentication** | Supabase Auth |
| **Backend** | Supabase Edge Functions |
| **AI** | Google Gemini |
| **IDE** | Android Studio |
| **Build System** | Gradle |
| **Version Control** | Git |
| **Repository Hosting** | GitHub |
| **API Testing** | Postman |

---

## Project Structure

```text
RoleFit-AI/
│
├── app/
│   └── src/
│       └── main/
│           ├── java/com/example/shipatonapp/
│           │   ├── AIAnalysisResponse.kt
│           │   ├── AIService.kt
│           │   ├── AppMessage.kt
│           │   ├── AuthScreen.kt
│           │   ├── InputScreen.kt
│           │   ├── MainActivity.kt
│           │   ├── ResultScreen.kt
│           │   └── SupabaseClient.kt
│           │
│           └── res/
│               ├── drawable/
│               ├── mipmap-*/
│               └── values/
│
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
├── gradlew
├── gradlew.bat
├── .gitignore
└── README.md
```

---

## Requirements

Before building the application, install:

- Android Studio
- JDK compatible with the project
- Android SDK
- Required Android SDK platform and build tools
- A Supabase project
- A Google Gemini API key

---

## Setup

### 1. Clone the Repository

```bash
git clone https://github.com/YOUR_USERNAME/RoleFit-AI.git
cd RoleFit-AI
```

Open the project in Android Studio and allow Gradle synchronization to complete.

---

### 2. Configure Supabase

Create a Supabase project and configure:

- Supabase Authentication
- The `analyze-job` Edge Function

The Android client uses:

- Supabase project URL
- Client-side publishable/anonymous key

> **Never place a Supabase service-role key inside the Android application.**

---

### 3. Configure Gemini

Create your Gemini API credential and store it as a Supabase Edge Function secret:

```text
GEMINI_API_KEY
```

The Gemini API key should never be placed in:

```text
Kotlin source code
Android resources
GitHub
Screenshots
APK files
AAB files
```

---

### 4. Configure the Edge Function

The Android application sends:

```text
skills
jobDescription
```

to:

```text
analyze-job
```

The Edge Function:

1. Authenticates the request.
2. Validates the input.
3. Reads `GEMINI_API_KEY` from server-side secrets.
4. Sends the request to Gemini.
5. Parses the AI response.
6. Returns structured JSON to the Android application.

---

## Build

### Build Debug APK

```bash
./gradlew assembleDebug
```

Generated files are placed under:

```text
app/build/outputs/apk/debug/
```

### Build Release

```bash
./gradlew assembleRelease
```

For Google Play distribution, use a properly signed:

```text
Android App Bundle (.aab)
```

For direct device testing, a signed:

```text
APK
```

can be used.

---

## Security

RoleFit AI follows a client/server separation model for sensitive AI credentials.

```text
┌─────────────────────┐
│     Android App     │
└──────────┬──────────┘
           │
           │ Authenticated Request
           ▼
┌─────────────────────┐
│ Supabase Edge       │
│ Function            │
└──────────┬──────────┘
           │
           │ Server-side Secret
           ▼
┌─────────────────────┐
│   Google Gemini     │
└─────────────────────┘
```

### Security Checklist

| Credential / File | Commit to GitHub? |
|---|:---:|
| Gemini API key | ❌ |
| Supabase service-role key | ❌ |
| Release keystore (`.jks`) | ❌ |
| Passwords | ❌ |
| Personal access tokens | ❌ |
| `local.properties` | ❌ |
| Kotlin source | ✅ |
| Gradle configuration | ✅ |
| UI resources | ✅ |

### Ignored Files

The project `.gitignore` excludes sensitive and generated files such as:

```text
.gradle/
.idea/
build/
app/build/
local.properties
*.jks
*.keystore
*.p12
*.pfx
*.apk
*.aab
app/release/
```

> Keep the release keystore backed up securely. Losing it can prevent future signed updates for an already-published application.

---

## Application Flow

```text
┌─────────────┐
│ Login /     │
│ Sign Up     │
└──────┬──────┘
       │
       ▼
┌─────────────┐
│    Home     │
└──────┬──────┘
       │
       ▼
┌─────────────────────┐
│ Job Match Analyzer  │
├─────────────────────┤
│ Enter Skills        │
│ Paste Job           │
│ Description         │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│    Analyze Job      │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│      Results        │
├─────────────────────┤
│ Match Score         │
│ Summary             │
│ Matched Skills      │
│ Missing Skills      │
│ Job Requirements    │
│ Recommendations     │
└─────────────────────┘
```

---

## Testing

### Test Case 1 — High Match

#### Candidate Skills

```text
Python, SQL, Docker, Git, REST API, Linux, FastAPI, PostgreSQL
```

#### Job Description

```text
Backend Developer requiring Python, REST APIs, SQL/PostgreSQL,
Docker, Git, Linux, and FastAPI.
```

#### Expected Behavior

- Strong skill alignment is detected.
- Most core backend skills are identified as matched.
- Relevant recommendations are generated.

---

### Test Case 2 — Low Match

#### Candidate Skills

```text
Kotlin, Android, Jetpack Compose, Firebase, XML, Material Design, Git
```

#### Job Description

```text
Data Analyst requiring SQL, Python data analysis, Power BI,
Tableau, Excel, statistics, and data visualization.
```

#### Expected Behavior

- A lower alignment is detected.
- The application identifies the gap between the supplied skills and job requirements.
- Recommendations focus on job-relevant missing capabilities.

---

### Test Case 3 — Semantic Match

#### Candidate Skills

```text
Python, Machine Learning, Scikit-learn, SQL, Docker
```

#### Job Description

```text
ML Engineer working with predictive models, Python/Scikit-learn,
relational databases, containerized ML services, and production deployment.
```

#### Expected Behavior

- Related concepts are matched even when wording differs.
- Machine-learning and deployment concepts are identified.
- Relevant recommendations are generated.

---

## Error Handling

Technical exceptions are logged for debugging while the application displays user-friendly messages.

Examples:

```text
Network connection problem. Please check your internet and try again.

The analysis took too long. Please try again.

The AI service is temporarily busy. Please try again shortly.

The AI service is temporarily unavailable. Please try again later.

We couldn't complete the analysis. Please try again.
```

This keeps internal implementation details and raw API errors out of the user interface.

---

## Current Android Configuration

| Component | Version / Value |
|---|---|
| **Kotlin** | `2.2.10` |
| **Android Gradle Plugin** | `9.2.1` |
| **Compose BOM** | `2026.02.01` |
| **Minimum SDK** | `24` |
| **Target SDK** | `36` |
| **Compile SDK** | `37.1` |

> These values reflect the current project configuration and may change as dependencies are updated.

---

## Screenshots

Add screenshots to make the GitHub repository more visual.

Recommended structure:

```text
docs/
├── login.png
├── home.png
├── analyzer.png
└── results.png
```

Display them in Markdown:

```markdown
## Login

![Login Screen](docs/login.png)

## Home

![Home Screen](docs/home.png)

## Analyzer

![Job Analyzer](docs/analyzer.png)

## Results

![Analysis Results](docs/results.png)
```

---

## Roadmap

### Career Intelligence

- Resume upload and parsing
- ATS-oriented resume feedback
- Multi-job comparison
- Skill-learning roadmap
- Personalized career recommendations
- Job history and saved analyses
- Progress tracking

### Product Expansion

- Additional AI providers
- Improved accessibility
- Localization
- Analytics
- Monetization
- More advanced job-market insights

---

## Author

**Subham Mondal**

Computer Science Undergraduate - Python Developer - Learning ML and Android 

---

## Hackathon

RoleFit AI was developed as an Android application project for **RevenueCat Shipaton 2026**.

### Project Demonstrates

- Android application development
- Kotlin + Jetpack Compose
- Supabase authentication
- Server-side AI integration
- Structured AI responses
- Practical job and skill matching
- Secure API-key handling
- Production-oriented error handling

---

## License

This project is currently intended for:

- Educational purposes
- Hackathon development
- Portfolio demonstration

A formal open-source license should be added before accepting external contributions or redistributing the project under specific licensing terms.

---

## Project Tagline

> **RoleFit AI — Find your fit. Build your future.**
