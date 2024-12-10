## **Bajetin Personal Finance Management**

**Bajetin** is a smart, cross-platform budgeting app built with Kotlin Multiplatform and Compose, offering seamless financial management across Android, iOS, and Desktop. Track expenses, set budgets, and achieve savings goals effortlessly with an intuitive and engaging design. Simplify your finances with Bajetin! 🚀

## **Feature Checklist for Bajetin**

| Feature                     | Description                                                       | Status         |
|-----------------------------|-------------------------------------------------------------------|----------------|
| Add Transactions            | Quickly add income or expense transactions                        | 🔄 In Progress |
| Transaction History         | View a comprehensive list of all past transactions                | 🔄 In Progress |
| Offline Support             | Add transactions without an internet connection                   | 🔄 In Progress |
| Expense Tracking            | Log daily expenses with categories, descriptions, and visual insights | ⏳ Planned      |
| Budget Management           | Set monthly budgets and track spending                            | ⏳ Planned      |
| Savings Goals               | Set and track savings goals to encourage financial discipline     | ⏳ Planned      |


Legend:
- ✅ Completed: Feature is fully implemented.
- 🔄 In Progress: Feature is currently under development.
- ⏳ Planned: Feature is planned for future updates.

___


## **Architecture for Bajetin**

Bajetin is built with a Hybrid Architecture, combining:
- **Feature-based modularity**: Each feature, such as "Transaction History" or "Budget Management," is self-contained.
- **Shared logic and components**: Shared utilities, themes, and data models ensure consistency and reduce duplication.
- **MVVM (Model-View-ViewModel)**: The app follows the MVVM architectural pattern

### Directory Structure
```
app/
|- core/                     # Shared business logic and utilities
|   |- domain/               # Shared use cases and entities
|   |- utils/                # Shared utilities
|- data/                     # Shared/global data layer
|   |- repository/           # Shared repositories used across features
|   |- local/                # Shared local data sources
|   |- remote/               # Shared remote data sources (e.g., APIs)
|   |- model/                # Shared models used across features
|- di/                       # Dependency injection modules
|- features/                 # Feature-specific implementation
|   |- feature_name/         # Replace 'feature_name' with actual features
|       |- data/             # Feature-specific data layer
|       |- domain/           # Use cases and business logic for the feature
|       |- presentation/     # UI components (ViewModel, Composables)
|- navigation/               # Navigation and routing logic
|- ui/                       # Shared UI components and themes
|   |- theme/                # Colors, typography, shapes, and app-wide themes
|   |- components/           # Reusable UI components
```

### Core Folder
The `core` folder contains shared logic that can be used across features:
- **`domain/`**: Contains reusable use cases and entities that encapsulate business logic.
- **`utils/`**: Contains utility classes, helpers, or extensions.

### Data Folder
The `data` folder contains:
- **`repository/`**: Centralized repositories managing data from different sources (local or remote).
- **`local/`**: Handles database-related operations (e.g., DAO for Room).
- **`remote/`**: Manages API calls or network interactions.
- **`model/`**: Shared data models used across the application.

### Features Folder
Each feature has its own modular folder, containing:
- **`data/`**: Feature-specific data.
- **`domain/`**: Feature-specific use cases and entity.
- **`presentation/`**: UI-related components like ViewModels and Screen.

---

### **Additional Libraries**

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
