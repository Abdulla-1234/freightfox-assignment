# Transporter Assignment on Lanes - FreightFox Assignment

## Overview
This is a Spring Boot application that solves the "Transporter Assignment on Lanes" problem. It optimizes logistics by assigning the most cost-effective transporters to specific lanes while adhering to a maximum transporter limit.

## Features
* **Cost Minimization:** Uses a combinatorial optimization algorithm to find the absolute lowest cost.
* **REST API:** Exposes endpoints to ingest data and retrieve optimized assignments.
* **H2 Database:** Uses an in-memory database for easy setup and testing.
* **Modularity:** Clean separation of concerns (Controller, Service, Repository, Entity, DTO).

## Tech Stack
* Java 17
* Spring Boot 3.x
* Spring Data JPA
* H2 Database
* Maven

## How to Run
1.  Clone the repository.
2.  Run the application using Maven:
    ```bash
    ./mvnw spring-boot:run
    ```
3.  The application will start on `http://localhost:8080`.

## API Endpoints

### 1. Submit Input Data
* **URL:** `POST /api/v1/transporters/input`
* **Body:** JSON containing Lanes and Transporters.

### 2. Get Optimized Assignment
* **URL:** `POST /api/v1/transporters/assignment`
* **Body:**
    ```json
    {
      "maxTransporters": 3
    }
    ```
## Contact
* **Name:** Doodakula Mohammad Abdulla
* **Email:** mohammadabdulla20march@gmail.com
* **Phone:** +91 9110418102
* **Project Link:** [Insert your GitHub Link Here once created](https://www.linkedin.com/in/doodakula-mohammad-abdulla-8a3307258/)
