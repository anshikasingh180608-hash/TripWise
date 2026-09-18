# TripWise
TripWise is a centralized travel planning tool designed to solve the problem of fragmented itineraries and budget overruns. Built entirely in Java, it allows users to log destinations, schedule activities, and track every expense against a predefined budget—automatically saving all data locally via CSV file handling.
# TripWise 🌍 - Travel Planner & Budget Manager

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)
![Console App](https://img.shields.io/badge/Console-App-4D4D4D?style=for-the-badge&logo=windows-terminal&logoColor=white)

## Table of Contents
1. [Overview of the Project](#overview-of-the-project)
2. [Core Features & Modules](#core-features--modules)
3. [Technologies & Tools Used](#technologies--tools-used)
4. [Project Structure](#project-structure)
5. [Steps to Install & Run](#steps-to-install--run)
6. [Instructions for Testing](#instructions-for-testing)
7. [Screenshots](#screenshots)
8. [Author](#author)

---

## Overview of the Project
**TripWise** is a robust, Java-based console application designed to apply core software engineering and object-oriented programming concepts to a real-world problem. 

Travelers often struggle with fragmented planning—keeping itineraries in a calendar, destinations in a note app, and budgets in a spreadsheet. TripWise provides a unified, lightweight, and offline system for managing travel destinations, scheduling trip dates, organizing daily activities, and rigorously tracking expenses to prevent budget overruns. Data persistence is achieved entirely through local CSV file handling, ensuring user data is never lost between sessions.

---

## Core Features & Modules
The application is strictly modular and features the following distinct functional components:

* ✈️ **Destination Management:** 
  * Add detailed new destinations (ID, Name, Country, Description).
  * Search the repository for specific locations.
  * View all stored destinations or remove them dynamically.
* 📅 **Trip Management:** 
  * Schedule overarching trips by linking them to specific destinations.
  * Define start and end dates.
  * Allocate a strict total budget for the entire journey.
* 🗺️ **Itinerary (Activity) Management:** 
  * Plan day-to-day schedules by linking activities to specific Trip IDs.
  * Log activity dates, names, and detailed descriptions to maintain a structured timeline.
* 💰 **Expense & Budget Tracking (Financial Analytics):** 
  * Log individual transactions categorized by type (Food, Travel, Hotel).
  * Automatically calculate total accumulated expenditures for a specific trip.
  * Dynamically calculate the remaining budget and trigger status warnings (e.g., "Within Budget", "Budget Exceeded").
* 💾 **Persistent CSV Storage:** 
  * Automated saving mechanism that serializes in-memory `ArrayList` data into lightweight `.csv` files.

---

## Technologies & Tools Used
* **Programming Language:** Java (JDK 8 or higher)
* **Core Concepts:** Object-Oriented Programming (OOP), Encapsulation, Modular Design
* **Data Structures:** Java Collections Framework (`ArrayList`) for dynamic in-memory data manipulation.
* **Storage:** Local File Handling (`java.io.FileWriter`, `java.io.File`, `java.io.IOException`)
* **Input Validation:** Custom exception handling (`NumberFormatException`) and error-trapping loops.
* **Version Control:** Git & GitHub for repository management.

---

## Project Structure
```text
TripWise/
├── TripWise.java          # Main controller and application entry point
├── README.md              # Project documentation
├── statement.md           # Problem statement and scope definition
└── data/                  # Auto-generated directory upon saving
    ├── destinations.csv   # Destination data repository
    ├── trips.csv          # Trip timeline and budget data
    ├── activities.csv     # Daily itinerary logs
    └── expenses.csv       # Financial transaction records
