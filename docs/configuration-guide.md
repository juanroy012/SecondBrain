# Configuration Guide

## Environment profiles

Use profile-based config files:

- `application.yml` (shared defaults)
- `application-local.yml`
- `application-dev.yml`
- `application-prod.yml`
- `application-postgres.yml` and `application-mysql.yml` (optional DB-specific overlays)

## Minimum config groups

### App settings

- `spring.application.name`
- timezone defaults
- feature flags (`assistant.enabled`, `integrations.github.enabled`)

### Database settings

- `spring.datasource.url`
- `spring.datasource.username`
- `spring.datasource.password`
- `spring.jpa.hibernate.ddl-auto=validate`
- `spring.jpa.properties.hibernate.jdbc.time_zone=UTC`

### Migration settings

- `spring.flyway.enabled=true`
- `spring.flyway.locations=classpath:db/migration`

### Security settings

- JWT secret and expiration
- refresh token policy
- password policy thresholds

### AI provider settings

- provider base URL
- API key from environment variable or secret manager
- model id
- timeout + retry limits

### Observability settings

- management endpoint exposure
- metrics export config
- structured logging format

## Secrets management

Never commit secrets. Use:

- local `.env` (ignored),
- CI/CD secret store,
- cloud secret manager for production.

## Suggested env var naming

- `SB_DB_URL`
- `SB_DB_USER`
- `SB_DB_PASSWORD`
- `SB_JWT_SECRET`
- `SB_AI_API_KEY`
- `SB_AI_MODEL`

## Data and timezone conventions

- Store timestamps in UTC.
- Store user timezone in profile for display and day-boundary calculations.
- Use ISO-8601 in API payloads.

