# 📅 Plan Your Event App (Android - Jetpack Compose)

## 📌 Overview

This is a simple Android application built using **Kotlin + Jetpack Compose**.
The app allows users to **add, view, update, and delete events**.

The main goal of this project is to learn and implement:

* Modern Android development (Compose)
* Clean Architecture basics
* State management using Flow & ViewModel
* UI/UX improvements like loading states

---

## 🚀 Features

* ➕ Add new event
* 📋 View list of events
* ✏️ Update existing event
* ❌ Delete event
* ⏳ Loading indicator 
* 🧠 Smart state handling using ViewModel

---

## 🏗️ Tech Stack

* **Language:** Kotlin
* **UI:** Jetpack Compose
* **Architecture:** MVVM
* **Database:** Room Database
* **State Management:** StateFlow + collectAsStateWithLifecycle
* **Async Work:** Kotlin Coroutines

---

## 🧠 Key Concepts Used

### 1. MVVM Architecture

* **Model:** Room DB + Repository
* **View:** Compose UI
* **ViewModel:** Handles logic and state

---

### 2. State Management

We use a **sealed class** to manage UI states:

```kotlin
sealed class EventUiState {
    object Loading : EventUiState()
    data class Success(val events: List<Event>) : EventUiState()
    data class Error(val message: String) : EventUiState()
}
```

This helps us clearly handle:

* Loading state
* Data loaded
* Error case

---

### 3. Flow → Compose State

```kotlin
val uiState by viewModel.uiState.collectAsStateWithLifecycle()
```

* Flow emits data from Room
* Compose converts it into State
* UI automatically updates

---

### 4. Loading Overlay

We implemented a **grey background loader** so users clearly see loading state:

```kotlin
Box(
    modifier = Modifier
        .fillMaxSize()
        .background(Color.Black.copy(alpha = 0.3f)),
    contentAlignment = Alignment.Center
) {
    CircularProgressIndicator(color = Color.White)
}
```

---

### 5. Avoiding Unnecessary Recomposition

We used:

* `remember()` → to cache calculations
* `LaunchedEffect()` → for side effects

Example:

```kotlin
val existingEvent = remember(events, eventId) {
    events.find { it.id == eventId }
}
```

---

### 6. Clean Dependency Management

Instead of initializing database in `MainActivity`, we used:

```kotlin
class PlanYourEventApp : Application() {
    lateinit var container: AppContainer
}
```

This makes the app:

* Scalable
* Clean
* Easy to maintain

---

## 📱 Screens

### 🏠 Event List Screen

* Shows all events
* FAB to add new event
* Click item → update

---

### ✍️ Add / Edit Screen

* Add new event
* Edit existing event
* Pre-filled data for update

---

## ⚡ How to Run

1. Clone the repo
2. Open in Android Studio
3. Build the project
4. Run on emulator or device

---

## 🎯 Learning Outcomes

From this project, I learned:

* How Jetpack Compose works with state
* How to use Room with Flow
* How ViewModel manages UI data
* How recomposition works in Compose
* How to improve UI with loaders and overlays

---

## 🚀 Future Improvements

* 🔍 Search events
* 🏷️ Category filter
* 📅 Date picker UI
* 🎨 Better UI design (Material 3)
* ☁️ Cloud sync

---

## 🙌 Conclusion

This project helped me understand **modern Android development** using Compose and MVVM.
It also improved my understanding of **state management, UI behavior, and clean architecture**.

---


<img src="https://github.com/user-attachments/assets/339eb7f7-bf8a-481c-8b02-979ed5896a69" width="300" height="auto">
<img src="https://github.com/user-attachments/assets/27db7efa-077f-4fe9-a8f7-5923f03e059b" width="300" height="auto">
<img src="https://github.com/user-attachments/assets/3be9b720-5b5b-4682-9d92-2d72ab0ff7e1" width="300" height="auto">
<img src="https://github.com/user-attachments/assets/9d34f523-772f-41a2-874c-d44b681c8d9e" width="300" height="auto">

⭐ Thank you!
