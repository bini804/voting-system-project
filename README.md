# Voting System

A Java-based voting application with a GUI built using **Swing** and **MySQL** as the database backend.

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Installation](#installation)
- [Database Schema](#database-schema)
- [Usage](#usage)
- [Screenshots](#screenshots)
- [Contributing](#contributing)
- [License](#license)

## Introduction
This project is a **voting system** where users can enter their details and vote for a political party. The system stores voter information and counts votes securely using **MySQL**.

## Features
- User-friendly **GUI** with an intuitive voting process.
- Stores voter details securely in **MySQL**.
- Prevents duplicate voting using CNIC validation.
- Displays a **confirmation screen** after voting.

## Technologies Used
- **Java** (Swing for GUI)
- **MySQL** (Database Management System)
- **JDBC** (Database Connectivity)

## Installation
1. **Clone the repository**:
   ```sh
   git clone https://github.com/your-username/voting-system.git
   cd voting-system
   ```
2. **Set up the database**:
   - Install **MySQL** and create a database.
   - Use the provided **SQL schema** to create required tables.
3. **Configure Database Connection**:
   - Open `DatabaseConnection.java` and update the `URL`, `USER`, and `PASSWORD` fields.
4. **Compile and Run**:
   ```sh
   javac VotingApp.java
   java VotingApp
   ```

## Database Schema
```sql
CREATE DATABASE election_system;
USE election_system;

CREATE TABLE voters_info (
    id INT PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(255) NOT NULL,
    cnic_number VARCHAR(15) UNIQUE NOT NULL,
    gender_type ENUM('Male', 'Female') NOT NULL,
    city_name VARCHAR(100) NOT NULL,
    selected_party VARCHAR(50)
);

CREATE TABLE vote_party_count (
    party_name VARCHAR(50) PRIMARY KEY,
    party_votes INT DEFAULT 0
);

INSERT INTO vote_party_count (party_name, party_votes) VALUES
('Party X', 0),
('Party Y', 0);
```

## Usage
1. **Launch the Application** by running `VotingApp.java`.
2. **Enter Voter Information** including Name, CNIC, Gender, and City.
3. **Select a Party** and cast your vote.
4. **View Confirmation** of successful voting.

## Screenshots
_(Add screenshots of the application here)_

## Contributing
Feel free to contribute by submitting issues or pull requests.

## License
This project is licensed under the **MIT License**.
