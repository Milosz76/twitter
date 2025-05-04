# 🧩 Spring Boot User Management Application

This is a full-stack Java Spring Boot application for user management. It supports features such as user registration, login, profile editing, password changes, account deletion, and message publishing with detailed message information. For enhanced security, the IP address of the device is saved with each message. The backend is connected to a PostgreSQL database and uses Spring Security for authentication and authorization.

## ✨ Features

- User registration and login
- Edit profile (including name, email, birth date, roles, etc.)
- Password update with confirmation
- Account deletion with redirect
- Spring Security-based authentication
- PostgreSQL as the backend database
- Docker support for local and production deployment

## 🛠 Tech Stack

- Java 21
- Spring Boot
- Spring Security
- PostgreSQL
- Maven
- Docker

## 🚀 Getting Started (Local Run)

### 1. Prerequisites

- Java 21
- Maven 3.9+
- Docker and Docker Compose

### 2. Build the Project

```bash
mvn clean package -DskipTests
