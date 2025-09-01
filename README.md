# Expense Manager Android App

A **full-featured Expense Manager Android app** built with **modern Android development best practices**.  
This app helps users manage daily expenses, categorize them, and keep track of totals — all with offline support.

---

## Features

This project follows the latest **Android development best practices** with a clean and scalable architecture.  
It is designed to be **maintainable, testable, and performant**.

---

## Architecture & Design Patterns

- **Clean Architecture**: Proper separation with `data/`, `domain/`, and `ui/` layers
- **MVVM Pattern**: Model–View–ViewModel with Repository pattern
- **Dependency Injection**: [Hilt (Dagger)](https://developer.android.com/training/dependency-injection/hilt-android) for clean dependency management
- **Reactive Programming**: [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) & [Flow](https://kotlinlang.org/docs/flow.html) for async operations

---

## Core Technologies

### Language & Platform
- **Kotlin**: `2.0.21` (latest stable)
- **Min SDK**: `24` (Android 7.0) – covers **94%+ of devices**
- **Target/Compile SDK**: `35` (Android 15) – always up-to-date

### UI Framework
- **Jetpack Compose**: Modern declarative UI (`BOM 2024.09.00`)
- **Material 3**: Latest Material Design system
- **Navigation Compose**: Type-safe navigation (`2.7.7`)
- **Extended Icons**: Material icons library

### Data Layer
- **Room Database**: `2.6.1` for local storage with offline support
- **KSP**: Kotlin Symbol Processing (faster than KAPT)
- **Repository Pattern**: Clean data access abstraction

### Build System
- **Android Gradle Plugin**: `8.9.0` (latest)
- **Version Catalog**: Modern dependency management with `libs.versions.toml`
- **Core Library Desugaring**: Java 8+ API support for older devices

---

## Strengths

- **Modern Stack** → Using the latest Android development tools & practices
- **Clean Architecture** → Well-structured, maintainable codebase
- **Offline-First** → Room database ensures app works without internet
- **Type Safety** → Kotlin + Compose provide excellent type safety
- **Performance** → KSP instead of KAPT for faster builds
- **Version Management** → Centralized dependency versions

---

## Tech Stack

- **Language:** Kotlin
- **UI:** Jetpack Compose + Material 3
- **Architecture:** MVVM + Repository
- **Database:** Room
- **Async/Reactive:** Kotlin Coroutines & Flow
- **Dependency Injection:** Hilt
- **Navigation:** Navigation Component (Compose)

---

## Getting Started

1. Clone the repository
   ```bash
   https://github.com/sanjeevkumar11141/ExpenseManagerMaster.git

