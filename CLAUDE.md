# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Spring Boot template implementing Clean Architecture (Robert C. Martin). The project demonstrates proper layer separation with strict dependency rules enforced by ArchUnit tests.

**Tech Stack**: Java 21, Spring Boot 3.5.5, jOOQ, MySQL 9.x, Thymeleaf, Spring Security, Flyway

## Build & Run Commands

```bash
# Build the project
./gradlew build

# Run the application
./gradlew bootRun

# Generate jOOQ classes (run after database migrations)
./gradlew generateJooq
```

**Application URL**: http://localhost:8080/samples
**Login credentials**: test@example.com / password

## Testing

```bash
# Unit tests only (excludes E2E)
./gradlew test

# Architecture compliance tests
./gradlew archTest

# E2E tests (Selenium)
./gradlew e2eTest

# Run all tests (CI pipeline)
./gradlew ciTest

# Run a single test
./gradlew test --tests "ClassName.methodName"

# Performance tests
./gradlew test --tests "SamplePerformanceTest"
```

## Database Management

```bash
# Run Flyway migrations
./gradlew flywayMigrate

# View migration status
./gradlew flywayInfo

# Clean database (WARNING: destructive)
./gradlew flywayClean
```

**Database connection**: jdbc:mysql://localhost:3306/template (root/root)

## Code Quality & Reports

```bash
# Generate coverage report
./gradlew jacocoTestReport

# Generate all quality reports
./gradlew qualityReport

# Dependency analysis
./gradlew dependencyReport

# Code metrics
./gradlew codeMetricsReport
```

## Architecture

### Layer Structure

```
com.ngnmsn.template/
├── domain/              # Core business logic (no dependencies)
│   ├── model/          # Entities, value objects
│   ├── service/        # Domain services
│   ├── repository/     # Repository ports (interfaces)
│   └── exception/      # Domain exceptions
├── application/        # Use cases (depends only on domain)
│   ├── service/        # Application services (orchestration)
│   ├── command/        # Command objects
│   ├── query/          # Query objects
│   └── response/       # Response objects
├── infrastructure/     # Technical implementation
│   ├── repository/     # Repository adapters (jOOQ implementations)
│   ├── config/         # Framework configuration
│   └── adapter/        # External system adapters
└── presentation/       # User interface layer
    ├── web/            # Thymeleaf controllers
    ├── api/            # REST API controllers
    ├── form/           # Form objects
    └── request/        # API request objects
```

### Dependency Rules (Enforced by ArchUnit)

- **Domain**: Cannot depend on any other layer (pure Java only)
- **Application**: Depends only on domain
- **Infrastructure**: Can depend on domain and application
- **Presentation**: Depends only on application

**Critical**: Always follow the dependency inversion principle. Domain defines interfaces (ports), infrastructure provides implementations (adapters).

### Naming Conventions

- Domain services: `*DomainService`
- Application services: `*ApplicationService`
- Repository ports: `*RepositoryPort` (interface in domain)
- Repository adapters: `*RepositoryAdapter` (implementation in infrastructure)
- Controllers: `*Controller`
- Exceptions: `*Exception`

### Annotation Usage

- Application services: `@Service` + `@Transactional`
- Domain services: No annotations (pure Java)
- Repository implementations: `@Repository`
- Web controllers: `@Controller`
- REST controllers: `@RestController`

## Working with jOOQ

After modifying database schema:
1. Create Flyway migration in `src/main/resources/db/migration/`
2. Run `./gradlew flywayMigrate`
3. Regenerate jOOQ classes: `./gradlew generateJooq`

Generated jOOQ code is excluded from test coverage.

## Important Notes

- ArchUnit tests validate architecture compliance - they will fail if dependency rules are violated
- Domain layer must remain framework-agnostic (only `java.util.*`, `java.time.*` allowed)
- Business logic belongs in domain services, not application services
- Application services orchestrate use cases and manage transactions
- Never put business logic in controllers or repository implementations
