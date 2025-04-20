# 📚 Bookstore Management System

A Java-based Bookstore Management System developed as part of the **OOP2 module**. It simulates real-world bookstore operations with a focus on modern **Java 21 features** and industry-level coding practices.

---

## 🚀 Features

- Add, update, and manage books and customers  
- Handle different membership types: Regular, Silver, Gold  
- Apply tier-based benefits and discounts  
- Sort and filter collections using Streams and Lambdas  
- Group and partition data using Collectors  
- Perform concurrent book searches  
- Load book data from files using NIO2  
- Format output according to locale settings  

---

## 🛠️ Tech Stack

- **Language:** Java 21 (LTS)  
- **Architecture:** Modular, layered (models, services, members, main)  
- **Concurrency:** ExecutorService  
- **File Handling:** NIO2 (`Paths`, `Files`)  
- **Date Handling:** `LocalDate`, `DateTimeFormatter`  
- **Functional Programming:** Lambdas, Streams, Functional Interfaces  
- **OOP Enhancements:** Sealed classes, Records, Pattern Matching  
- **Localization:** Locale-aware formatting for prices and dates  

---

## 💡 Java 21 Highlights Used

- ✅ Sealed classes for controlled inheritance (Member types)  
- ✅ Records for immutable data structures (e.g., `ImmutableAddress`)  
- ✅ Pattern Matching and Switch Expressions for cleaner control flow  
- ✅ Functional Interfaces (`Consumer`, `Predicate`, `Function`) for streamlined operations  
- ✅ Stream API with operations like `filter()`, `map()`, `sorted()`, `collect()`  
- ✅ `Collectors.groupingBy()` and `partitioningBy()` for data aggregation  
- ✅ Concurrency via `ExecutorService`  
- ✅ File I/O with NIO2 API  

---

## 🧪 Sample Use Cases

- Filter books by category or price  
- Display books sorted by title, author, or date  
- Automatically apply discounts based on membership level  
- Display membership expiry and validate access  
- Support localized views (e.g., date/price formatting)  

---

## 📂 Project Structure

```plaintext
bookstore-management-system
├── models        → Data models: Book, Customer, etc.
├── services      → Business logic and services
├── members       → Membership hierarchy (Regular, Silver, Gold)
├── utils         → Helper utilities (e.g., comparators, file handlers)
└── Main.java     → Application entry point
