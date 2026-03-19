#  Chronos Engine | Core API

[![Java](https://img.shields.io/badge/Java_17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](#)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot_3-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](#)
[![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=spring-security&logoColor=white)](#)
[![Gemini API](https://img.shields.io/badge/Neural_LLM-1A73E8?style=for-the-badge&logo=google&logoColor=white)](#)

> **Enterprise Developer Telemetry & Neural Artifact Synthesis**
>
> The high-performance backend microservice powering the Chronos ecosystem. Engineered to handle stateless JWT authentication, secure telemetry ingestion, and deterministic AI parsing for SOC 2-compliant release notes.

---

## Backend Architecture

This repository contains the `chronos-engine-core`, functioning as a decoupled REST API designed to serve the Chronos Web Client.

###  1. Identity & Security Layer
* **Stateless JWT:** Implements `jjwt` for cryptographically signed, stateless session management.
* **Spring Security Filter Chain:** Custom `OncePerRequestFilter` to intercept, validate, and authorize incoming client requests across protected endpoints.
* **BCrypt Hashing:** Secure credential storage and verification.

### 2. Telemetry Ingestion Layer
* **RESTful Controllers:** Clean, versioned endpoint design (`/api/v1/logs`, `/api/v1/auth`) mapped to strict DTOs.
* **JPA / Hibernate:** ORM integration for persistent storage of daily sprint metrics and cached terminal commands.

### 3. Neural Synthesis Service (`AiSummarizationService`)
* **WebClient Integration:** Non-blocking, reactive HTTP calls to the Google Gemini API.
* **Deterministic Prompt Engineering:** Utilizes absolute zero temperature (`0.0`) and strict few-shot formatting templates to force the LLM to act as a deterministic text parser rather than a conversational agent.
* **Java Text Blocks:** Modern Java 17+ implementation for highly readable, maintainable prompt construction.

---

## Technical Stack

| Component | Technology |
| :--- | :--- |
| **Framework** | Spring Boot 3.x |
| **Language** | Java 17 |
| **Security** | Spring Security, JSON Web Tokens (JWT) |
| **Data Access** | Spring Data JPA, Hibernate |
| **External APIs** | Google Gemini Pro, Spring WebFlux (WebClient) |
| **Build Tool** | Maven |

---

##  Local Deployment Protocol

## Prerequisites
* Java Development Kit (JDK) 17+
* Maven 3.8+
* Google Gemini API Key

## Initialization

### 1. Clone the repository
git clone https://github.com/CHINTHAN03/chronos-engine-core.git

### 2. Configure environment variables (Create application-dev.yml or .env)
JWT_SECRET=your_cryptographic_secret_key
GEMINI_API_KEY=your_google_gemini_api_key

### 3. Boot the Spring application
./mvnw spring-boot:run


> **Note:** The frontend client for this API is located at the [Chronos Web Client Repository](https://github.com/CHINTHAN03/chronos-web-client).

---

*Architected and developed by Chinthan.* 