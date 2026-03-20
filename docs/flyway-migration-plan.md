# Flyway Migration Plan

This migration plan defines the schema rollout for MVP and assistant traceability.

## Migration principles

- One migration per schema change.
- Forward-only migrations in shared environments.
- `spring.jpa.hibernate.ddl-auto=validate` outside local throwaway setups.
- Use UTC timestamps in persisted records.

## Naming convention

- `V1__init_auth_profile.sql`
- `V2__add_tracking_and_journal.sql`
- `V3__add_insights_and_progression.sql`
- `V4__add_assistant_tables.sql`
- `V5__add_indexes_and_constraints_tuning.sql`

## Migration sequence

## `V1__init_auth_profile.sql`

Create foundational identity tables.

- `user_credential`
  - id (pk), email (unique), username (unique), password_hash, created_at, updated_at
- `refresh_token`
  - id (pk), user_id (fk), token_hash (unique), expires_at, revoked_at, created_at
- `user_profile`
  - id (pk), user_id (unique fk), timezone, visibility, preferences_json, created_at, updated_at

Indexes:

- `idx_user_credential_email`
- `idx_refresh_token_user_id`

## `V2__add_tracking_and_journal.sql`

Create core logging tables.

- `daily_log`
  - id (pk), user_id (fk), log_date, sleep_hours, work_hours, leisure_hours, exercise_minutes, focus_minutes, mood_score, created_at, updated_at
  - unique constraint: `(user_id, log_date)`
- `journal_entry`
  - id (pk), user_id (fk), entry_time, title, content, sentiment_label, created_at, updated_at
- `journal_entry_tag`
  - journal_entry_id (fk), tag_value

Indexes:

- `idx_daily_log_user_date`
- `idx_journal_entry_user_time`
- `idx_journal_tag_value`

## `V3__add_insights_and_progression.sql`

Create analytics and gamification tables.

- `daily_summary`
  - id (pk), user_id (fk), summary_date, productivity_score, balance_score, stress_signal, highlights_json, created_at
  - unique constraint: `(user_id, summary_date)`
- `progress_snapshot`
  - id (pk), user_id (fk), snapshot_date, total_xp, level_value, streak_days, longest_streak, created_at
- `badge_award`
  - id (pk), user_id (fk), badge_code, awarded_at, metadata_json

Indexes:

- `idx_daily_summary_user_date`
- `idx_progress_snapshot_user_date`
- `idx_badge_award_user_awarded_at`

## `V4__add_assistant_tables.sql`

Create assistant outputs and traceability tables.

- `assistant_suggestion`
  - id (pk), user_id (fk), suggestion_date, summary_text, recommendation_text, rationale_text, confidence_score, model_name, created_at
- `assistant_prompt_trace`
  - id (pk), user_id (fk), suggestion_id (fk), input_summary, provider_name, request_tokens, response_tokens, latency_ms, created_at

Indexes:

- `idx_assistant_suggestion_user_date`
- `idx_assistant_trace_user_created_at`

## `V5__add_indexes_and_constraints_tuning.sql`

Tighten constraints and optimize heavy queries after baseline traffic.

- Add check constraints for non-negative duration fields.
- Add check constraints for score ranges (`mood_score`, `confidence_score`).
- Add partial/composite indexes based on slow query observations.

## Rollback and safety notes

- Flyway migrations are forward-only in shared envs; use compensating migrations for fixes.
- Test each migration on clean DB and an upgraded DB state.
- Keep large data backfills out of request path; run out-of-band jobs if needed.

## Validation checklist per migration

- SQL executes in local dev and CI.
- Application starts with `ddl-auto=validate`.
- Relevant repository queries pass integration tests.
- API endpoints relying on new schema pass contract tests.

