
# Planner App

This is a Task Management Android application written using MVVM Architecture and Jetpack Compose with a multi-module setup.



### Features

- Home screen showing task summary with due-today and overdue counts
- Kanban-style Projects board with TODO, IN PROGRESS, and COMPLETED columns
- Calendar view to browse and filter tasks by date
- Full-text search across all tasks
- Add and manage work items with title, description, due date, priority, and status
- See-all and due-today list views for focused task browsing
- Task priority levels — Low, Medium, High — with colour-coded indicators
- Smooth horizontal slide navigation transitions between screens




### Lessons Learned

While building this project I have read various blogs to understand the below concepts.

- MVVM Architecture
- Multi-module Android project setup
- Jetpack Compose UI
- Jetpack Navigation Compose
- Dagger Hilt for dependency injection
- Room Database with type converters
- Kotlin Coroutines and Flow
- StateFlow for UI state management
- Repository pattern as a single source of truth
- KSP (Kotlin Symbol Processing) for annotation processing




### Dependency Used

- Room for local database
```
implementation("androidx.room:room-runtime:2.8.4")
ksp("androidx.room:room-compiler:2.8.4")
implementation("androidx.room:room-ktx:2.8.4")
```
- Dagger Hilt for dependency injection
```
implementation("com.google.dagger:hilt-android:2.59.1")
ksp("com.google.dagger:hilt-android-compiler:2.59.1")
implementation("androidx.hilt:hilt-navigation-compose:1.3.0")
```
- Jetpack Navigation for Compose
```
implementation("androidx.navigation:navigation-compose:2.9.7")
```
- Material Icons Extended
```
implementation("androidx.compose.material:material-icons-extended:1.7.8")
```
- Compose BOM for version alignment
```
implementation(platform("androidx.compose:compose-bom:2026.02.00"))
```
- Android Lifecycle aware components
```
implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.10.0")
```




### Run Locally

Clone the project

```bash
  git clone https://github.com/Tanya-Jain99/Task.git
```

Go to the project directory

```bash
  cd Task
```

Now you just have to import the project in Android Studio and Build it and Run.




### Complete Project Structure

```
├── app
│   └── java/com/tanya/planner
│       ├── design
│       ├── model
│       ├── repository
│       ├── ui
│       │   ├── calendar
│       │   ├── home
│       │   ├── projects
│       │   └── theme
│       └── viewmodel
└── database
    └── java/com/tanya/planner/database
        ├── dao
        └── entity
```

## 🚀 About Me
Hi there! My name is Tanya Jain, I work as a Software Developer and like to expand my skill set in my spare time.

If you have any questions or want to connect, feel free to reach out to me on :

- [LinkedIn](https://www.linkedin.com/in/tanyajain06)
- [GitHub](https://github.com/Tanya-Jain99)
