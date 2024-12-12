## **Bajetin Personal Finance Management**

**Bajetin** is a smart, cross-platform budgeting app built with Kotlin Multiplatform and Compose, offering seamless financial management across Android, iOS, and Desktop. Track expenses, set budgets, and achieve savings goals effortlessly with an intuitive and engaging design. Simplify your finances with Bajetin! 🚀

## **Feature Checklist for Bajetin**

| Feature                     | Description                                                       | Status |
|-----------------------------|-------------------------------------------------------------------|--------|
| Add Transactions            | Quickly add income or expense transactions                        | 🔄     |
| Transaction History         | View a comprehensive list of all past transactions                | 🔄     |
| Offline Support             | Add transactions without an internet connection                   | 🔄     |
| Expense Tracking            | Log daily expenses with categories, descriptions, and visual insights | ⏳      |
| Budget Management           | Set monthly budgets and track spending                            | ⏳      |
| Savings Goals               | Set and track savings goals to encourage financial discipline     | ⏳      |


Legend:
- ✅ Completed: Feature is fully implemented.
- 🔄 In Progress: Feature is currently under development.
- ⏳ Planned: Feature is planned for future updates.

___


## **Architecture for Bajetin**

This document outlines the overall architecture of the project, following a Clean Architecture approach with feature-based organization. The goal is to maintain a clear separation of concerns, improve testability, and ensure the codebase is easy to maintain and scale.

### Layers Overview

A common setup for Clean Architecture involves four main layers, arranged in a hierarchy that dictates dependency direction:

1. **UI/Feature Layer**
2. **Domain Layer**
3. **Data Layer**
4. **Core Layer**

Additionally, a **Navigation** module can be introduced to handle all the navigation logic between features without tying it to a specific layer.

The general rule is that each layer should depend only on the layers below it. No layer should depend on layers above it. This creates a one-way dependency flow, making it easier to change or replace components without breaking the entire codebase.

### Layers Description

#### UI/Feature Layer
- **Responsibility:** Displays information to the user, handles user input, and manages UI state.
- **Typical Contents:**
   - ViewModels
   - Activities/Fragments/Composables (or other UI components)
   - Feature-specific navigation calls (invoking the Navigation module)

**Depends On:** The Domain Layer for executing business logic via use cases, and the Navigation module for coordinating navigation.  
**Does Not Depend On:** Data or Core layers directly.

#### Domain Layer
- **Responsibility:** Encapsulates the application’s business logic and rules.
- **Typical Contents:**
   - Entities (plain Kotlin classes representing core data of the domain)
   - Use Cases (interactors) defining the operations the app performs
   - Interfaces (e.g., repositories) describing contracts for data retrieval or other operations

**Depends On:** May use Core utilities for generic functions.  
**Does Not Depend On:** UI, Data implementations, or Navigation modules directly.

#### Data Layer
- **Responsibility:** Provides concrete implementations of the domain interfaces (e.g., repositories).
- **Typical Contents:**
   - Repositories that implement the domain’s repository interfaces
   - Data sources (local database, network services, etc.)
   - Mappers and adapters converting data between domain entities and data transfer objects

**Depends On:**
- Domain Layer (for the interfaces it implements)
- Core Layer (for shared utilities, if needed)

**Does Not Depend On:** UI Layer or Navigation module

#### Core Layer
- **Responsibility:** Offers generic, reusable utilities and helpers with no app-specific knowledge.
- **Typical Contents:**
   - Utility classes (e.g., parsers, formatters, extension functions)
   - Helpers that do not contain business logic or refer to domain models

**Depends On:** Nothing above.  
**No Other Layers Depend On:** Not strictly correct—Domain and Data may use Core, but Core doesn’t know about them.

#### Navigation
- **Responsibility:** Centralizes navigation logic and directions between features.
- **Typical Contents:**
   - Navigation graphs or directions
   - Route definitions for each feature
   - Interfaces or classes that handle cross-feature navigation logic

**Depends On:** Usually, Navigation might know about feature modules’ entry points but tries to remain as independent as possible. Ideally, it should not depend on Domain or Data. It might be a separate module at the same level as UI/Feature modules or even top-level if it orchestrates navigation.

**UI/Feature Layers** depend on Navigation to move between screens. Navigation should avoid depending directly on Domain or Data—its job is just routing.


### Feature-Based Structure
With feature-based organization, each feature might have its own UI, domain, and data:

```plaintext
    app/
    ├─ core/
    │   ├─ utils/
    │   └─ ...
    ├─ domain/
    │   ├─ usecase/
    │   ├─ model/
    │   └─ ...
    ├─ data/
    │   ├─ repository/
    │   │   └─ ... (implements domain repository)
    │   ├─ local/
    │   │   └─ ... (Room DAOs, local data sources)
    │   ├─ network/
    │   │   └─ ... (API services, remote data sources)
    │   └─ ... (other data implementations)
    |- di/  Dependency injection modules
    ├─ features/
    │   └─ ... (features)
    └─ navigation/
        ├─ NavGraph.kt
        └─ ... (other navigation logic)
 ```
---

## **Additional Libraries**

1. **Jetpack Compose Navigation**:
    - Handles navigation between screens in a declarative Compose UI framework.
2. **Material3**:
    - Implements the latest Material Design guidelines with modern UI components.
3. **Koin**:
    - A lightweight dependency injection framework for managing dependencies easily.
4. **SQLDelight**:
    - A multiplatform database library for type-safe and efficient database interactions.
5. **Detekt**:
    - For static code analysis and quality checks.
