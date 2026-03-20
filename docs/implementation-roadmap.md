# Implementation Roadmap

Use these companion docs while executing this roadmap:

- `mvp-implementation-order.md` for class-by-class build sequence.
- `api-service-repository-mapping.md` for endpoint-to-layer ownership.
- `flyway-migration-plan.md` for schema rollout order.

## Phase 0 - Foundation

- Clean up `pom.xml` dependency set (use standard test starter).
- Add validation, security, flyway, actuator, openapi.
- Establish profile-based configuration.
- Add migration baseline script.

## Phase 1 - Core tracking MVP

- Implement auth and user profile basics.
- Implement daily logs and journal entries.
- Provide daily/weekly summary endpoints.
- Add progression engine (XP + streaks + badges).

## Phase 2 - Assistant MVP

- Add assistant prompt composition from daily data.
- Add AI provider integration abstraction.
- Store suggestions and rationale.
- Add rate limiting and fallback behavior.

## Phase 3 - Integration adapters

- GitHub activity ingestion.
- Screen-time ingestion.
- Calendar ingestion.
- Normalize external data into internal metrics.

## Phase 4 - Intelligence and personalization

- Trend-based personalized recommendations.
- Goal planning and adaptive nudges.
- More advanced progression and challenge systems.

## Done criteria for early versions

- API documented with OpenAPI.
- Migration scripts reproducible in clean environment.
- Integration tests for core user flows.
- Basic operational metrics and health checks available.

