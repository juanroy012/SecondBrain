# Dependency Catalog (Recommended)

This list favors stable, well-supported dependencies over custom implementations.

## Keep from current project

- `org.springframework.boot:spring-boot-starter-data-jpa`
- `org.springframework.boot:spring-boot-starter-webmvc`
- `org.projectlombok:lombok` (optional)

## Fix/replace test dependencies

Current `pom.xml` includes non-standard starters:

- `spring-boot-starter-data-jpa-test` (replace)
- `spring-boot-starter-webmvc-test` (replace)

Use instead:

- `org.springframework.boot:spring-boot-starter-test`
- `org.springframework.security:spring-security-test` (when security is enabled)
- `org.testcontainers:junit-jupiter`
- `org.testcontainers:postgresql` (or mysql module if needed)

## Core application dependencies to add

- `org.springframework.boot:spring-boot-starter-validation`
- `org.springframework.boot:spring-boot-starter-security`
- `org.springframework.boot:spring-boot-starter-actuator`
- `org.springframework.boot:spring-boot-starter-cache`
- `org.flywaydb:flyway-core`

## Database drivers (choose profile)

- PostgreSQL profile: `org.postgresql:postgresql`
- MySQL profile: `com.mysql:mysql-connector-j`

## API quality and docs

- `org.springdoc:springdoc-openapi-starter-webmvc-ui`

## Resilience and utility

- `io.github.resilience4j:resilience4j-spring-boot3`
- `com.github.ben-manes.caffeine:caffeine`

## Logging and observability

- `net.logstash.logback:logstash-logback-encoder`
- `io.micrometer:micrometer-registry-prometheus`

## Mapping and boilerplate reduction

- `org.mapstruct:mapstruct`
- `org.mapstruct:mapstruct-processor`

## Development-only helpers

- `org.springframework.boot:spring-boot-devtools` (dev scope)

## Optional future integrations

- GitHub API client (official GraphQL or REST via WebClient)
- OAuth client if external providers are used (`spring-boot-starter-oauth2-client`)

## Why dependency-first here

Using mature libraries for validation, migrations, observability, API docs, retries, and testing improves reliability and velocity versus ad-hoc implementations.

