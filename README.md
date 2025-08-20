# Expense Manager Android App

A **full-featured Expense Manager Android app** built with **modern Android development best practices**.  
This app helps users manage daily expenses, categorize them, and keep track of totals — all with offline support.

---

## Features

- Add, Edit, and Delete expenses  
- Categorize expenses (Food, Travel, Shopping, Bills, Other)  
- View all expenses in a list with **total at the top**  
- Search & filter expenses by category  
- Modern UI using **Material 3 & Jetpack Compose**  
- Clean architecture: **MVVM + Repository pattern**  
- Offline support with **Room Database**  
- Reactive updates using **Kotlin Coroutines & Flow**  
- Input validation (amount > 0, title not empty)  
- Pick expense date with a **Date Picker**  
- Empty state UI when no expenses exist  

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

## Project Structure

com.example.expensemanager
│── data
│ ├── local # Room database, DAO, entities
│ ├── mapper # DTO ↔ Domain model mappers
│ └── repository # Data layer repository implementations
│
│── di
│ └── AppModule.kt # Hilt dependency injection setup
│
│── domain
│ ├── model # Domain models
│ └── repository # Repository interfaces (abstraction layer)
│
│── ui
│ ├── navigation # Compose Navigation setup
│ ├── screens # All UI screens (List, Add/Edit, Detail)
│ └── theme # Compose Material Theme setup
│
│── util # Utility/helper classes
│
│── ExpenseApp.kt # Application class with Hilt setup
│── MainActivity.kt # Main entry point of the app
