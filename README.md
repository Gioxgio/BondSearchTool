# BondSearchTool

A Spring Boot application for searching and managing bond data from Euronext markets.

## Features

- **Bond Data Scraping**: Retrieves bond listings from Euronext
- **Scheduled Updates**: Automatically updates bond information on a scheduled basis
- **REST API**: Provides endpoints to query bonds, countries, and markets
- **Yield to Maturity Calculation**: Calculates YTM for bonds
- **Correction System**: Apply manual corrections to bond data

## Tech Stack

- **Java 21**
- **Spring Boot 3.3.1**
- **Gradle**
- **MySQL** with Flyway migrations
- **JUnit 5** for testing

## Getting Started

### Prerequisites

- Java 21
- MySQL database
- Gradle (wrapper included)

### Configuration

Configure database connection via environment variables:

```
DB_HOST=localhost
DB_PORT=3306
DB_NAME=bondsearchtool
DB_USER=root
DB_PASSWORD=your_password
```

Or use the provided `.env` files:
- `.env` - Default configuration
- `.env_local` - Local development
- `.env_prod` - Production

### Build

```bash
./gradlew build
```

### Run

```bash
./gradlew bootRun
```

Or with Docker:

```bash
./start_docker.sh
```

### Test

```bash
./gradlew test
```

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/bond` | Get all valid bonds |
| GET | `/bond/countries` | Get all available countries |
| GET | `/bond/markets` | Get all available markets |
| GET | `/health` | Health check |

## Scheduled Jobs

The application runs the following scheduled jobs:

1. **UPDATE_LIST** - Fetches new bond listings from Euronext
2. **UPDATE_STATIC_FIELDS** - Updates static bond information (type, coupon, maturity)
3. **UPDATE_DYNAMIC_FIELDS** - Updates dynamic fields (price, yield)
4. **APPLY_CORRECTIONS** - Applies manual corrections to bond data

## Project Structure

```
src/
├── main/
│   ├── java/it/gagagio/bondsearchtool/
│   │   ├── BondSearchToolApplication.java
│   │   ├── controller/          # REST controllers
│   │   ├── service/             # Business logic
│   │   ├── data/
│   │   │   ├── entity/          # JPA entities
│   │   │   └── repository/      # Spring Data repositories
│   │   ├── model/               # Domain models / enums
│   │   ├── euronext/            # External API integration
│   │   ├── scheduler/           # Scheduled jobs
│   │   └── utils/               # Utility classes
│   └── resources/
│       ├── db/migration/        # Flyway migrations
│       └── application.yml      # Spring configuration
└── test/
    └── java/                    # Test classes
```

