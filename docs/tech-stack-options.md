# Tech Stack Options

## Backend (fixed baseline)

- Spring Boot (REST)
- Spring Data JPA
- Maven
- Java (current project is Java 25)

## Database decision: PostgreSQL vs MySQL

### PostgreSQL strengths

- Better advanced SQL features (CTEs, window functions).
- Strong JSON/JSONB support for flexible metadata and AI context payloads.
- Great fit for analytics-like queries for trends and insights.

### MySQL strengths

- Familiar setup and broad hosting support.
- Strong performance for straightforward OLTP workloads.
- Existing project currently has MySQL connector configured.

### Recommendation

Choose PostgreSQL for this product type because you will likely need:

- richer time-series and trend queries,
- flexible JSON metadata for assistant context,
- stronger analytical SQL capabilities.

Keep MySQL profile available if you later need portability.

## Caching and async options

- Redis: cache frequently requested summaries and rate-limit AI endpoints.
- Queue options (later): RabbitMQ or Kafka for async assistant generation.

## AI integration options

- Start simple with provider SDK over HTTP client.
- Wrap AI calls in dedicated `assistant` service interface.
- Persist prompt, model, response, token usage, and rationale for observability.

## Frontend options (for later)

- React + TypeScript (high ecosystem support).
- Next.js if you want SSR and integrated app shell.
- Mobile later: React Native or Flutter.

## Observability

- Micrometer + Prometheus metrics.
- Structured logging with Logback JSON encoder.
- OpenTelemetry tracing when distributed components are introduced.

