# Voting System Application

A Java-based voting system with a graphical user interface using Swing and MySQL for data storage.

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Setup Instructions](#setup-instructions)
- [Database Schema](#database-schema)
- [Project Structure](#project-structure)
- [Usage](#usage)
- [Screenshots](#screenshots)
- [License](#license)

## Introduction
This project is a simple electronic voting system where users can cast votes securely. It allows voters to register, select their preferred party, and store votes in a database.

## Features
- User-friendly GUI built with Swing
- Voter authentication and registration
- Voting for political parties
- Secure MySQL database storage
- Vote confirmation screen

## Technologies Used
- Java (Swing for GUI)
- MySQL (Database)
- JDBC (Database Connectivity)

## Setup Instructions
1. **Clone the Repository**
   ```bash
   git clone https://github.com/yourusername/voting-system.git
   cd voting-system
   ```
2. **Setup the Database**
   - Install MySQL and create a database named `election_system`
   - Run the following SQL queries to create necessary tables:
     ```sql
     CREATE TABLE voters_info (
         id INT AUTO_INCREMENT PRIMARY KEY,
         full_name VARCHAR(255) NOT NULL,
         cnic_number VARCHAR(20) UNIQUE NOT NULL,
         gender_type VARCHAR(10) NOT NULL,
         city_name VARCHAR(100) NOT NULL,
         selected_party VARCHAR(50)
     );
     
     CREATE TABLE vote_party_count (
         party_name VARCHAR(50) PRIMARY KEY,
         party_votes INT DEFAULT 0
     );
     ```
3. **Configure Database Connection**
   - Update `DatabaseConnection` class with your MySQL credentials:
     ```java
     private static final String URL = "jdbc:mysql://localhost:3306/election_system";
     private static final String USER = "root";
     private static final String PASSWORD = "yourpassword";
     ```
4. **Compile and Run the Application**
   ```bash
   javac VotingApp.java
   java VotingApp
   ```

## Project Structure
```
/voting-system
│── src/
│   ├── VotingApp.java
│   ├── DatabaseConnection.java
│   ├── WelcomeScreen.java
│   ├── VoterInfoScreen.java
│   ├── PartyVoteScreen.java
│   ├── VoteConfirmationScreen.java
│── assets/
│   ├── vote1.jpg
│   ├── g2.jpg
│   ├── vote7.jpg
│── README.md
│── LICENSE
```

## Usage
1. Launch the application.
2. Enter voter details (Name, CNIC, Gender, City).
3. Select and cast a vote for a political party.
4. Receive a confirmation message after voting.

## Screenshots
(Add screenshots of the application here)

## License
This project is licensed under the MIT License. See [LICENSE](LICENSE) for details.

