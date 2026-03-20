# System Structure

## Architecture style

Use modular monolith first (Spring Boot REST API), with clear boundaries to evolve to services if needed.

For concrete package layout and function responsibilities, use `project-structure.md` as the implementation reference.

## Suggested module boundaries (package-level)

- `auth`: user identity, login/session/JWT, roles.
- `profile`: user preferences, timezone, privacy settings.
- `tracking`: daily entries for sleep/work/leisure/exercise.
- `journal`: free-form diary entries, tags, mood markers.
- `progression`: XP, streaks, badges, milestones.
- `insights`: analytics and summary generation.
- `assistant`: AI prompts, recommendations, explanation logs.
- `integration`: GitHub/screen-time/calendar connectors (future).
- `shared`: common DTOs, exceptions, constants, utilities.

## Data flow (MVP)

1. User submits daily logs and optional notes.
2. Tracking + journal persist data.
3. Insight engine computes aggregates (daily/weekly).
4. Progression engine updates XP/streaks.
5. Assistant module generates a reflection and suggestions.
6. API returns timeline + metrics + assistant response.

## API pattern

- REST + JSON.
- Validation at boundary (DTO validation annotations).
- Service layer keeps business logic.
- Repository layer only handles persistence.

## Security baseline

- Spring Security + JWT for stateless API auth.
- Role model for future admin/moderator capabilities.
- Audit fields on critical entities.
- Rate limiting on AI endpoints.

## Deploy baseline

- Start with single app + single DB instance.
- Add Redis when caching/queues become necessary.
- Add message queue if async assistant jobs become expensive.

