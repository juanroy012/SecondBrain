# SecondBrain Documentation

This folder contains the product and technical blueprint for the first implementation phase.

## Suggested reading order

1. `product-vision.md`
2. `system-structure.md`
3. `domain-model.md`
4. `api-design.md`
5. `tech-stack-options.md`
6. `dependency-catalog.md`
7. `configuration-guide.md`
8. `implementation-roadmap.md`

## Scope of this docs phase

- Define the product concept and MVP boundaries.
- Decide architecture, persistence, and integration points.
- Choose robust dependencies over custom half-baked implementations.
- Create coding instructions for consistent, production-minded contributions.

## Current project baseline (from repository)

- Runtime: Java 25
- Framework: Spring Boot 4.0.4
- Build tool: Maven Wrapper (`mvnw`)
- Existing libraries in `pom.xml`: Spring Data JPA, Spring Web MVC, Lombok, MySQL connector

## Non-goals for this phase

- No business logic implementation.
- No controller/service/entity code generation.
- No UI implementation.

This docs set is intentionally implementation-ready so coding can start module by module.

