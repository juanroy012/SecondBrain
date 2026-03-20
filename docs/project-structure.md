# Project Structure and Functions

This document defines the package structure and the key functions each module should provide, with the reason each function exists.

## Base package

- `com.juanroy.secondbrain`

## Package-by-feature structure

```text
com.juanroy.secondbrain
|- auth
|  |- controller
|  |  |- AuthController
|  |- service
|  |  |- AuthService
|  |  |- JwtTokenService
|  |  |- PasswordPolicyService
|  |- repository
|  |  |- UserAuthRepository
|  |  |- RefreshTokenRepository
|  |- dto
|  |  |- request
|  |  |  |- RegisterRequest
|  |  |  |- LoginRequest
|  |  |  |- RefreshTokenRequest
|  |  |- response
|  |     |- AuthTokenResponse
|  |     |- UserAuthView
|  |- model
|  |  |- UserCredential
|  |  |- RefreshToken
|  |- mapper
|  |  |- AuthMapper
|  |- validator
|     |- PasswordStrengthValidator
|- profile
|  |- controller
|  |  |- ProfileController
|  |- service
|  |  |- ProfileService
|  |- repository
|  |  |- UserProfileRepository
|  |- dto
|  |  |- request
|  |  |  |- UpdateProfileRequest
|  |  |  |- UpdateNotificationPreferencesRequest
|  |  |- response
|  |     |- UserProfileResponse
|  |- model
|  |  |- UserProfile
|  |- mapper
|     |- ProfileMapper
|- tracking
|  |- controller
|  |  |- DailyLogController
|  |- service
|  |  |- DailyLogService
|  |  |- DailyLogValidationService
|  |- repository
|  |  |- DailyLogRepository
|  |- dto
|  |  |- request
|  |  |  |- UpsertDailyLogRequest
|  |  |- response
|  |     |- DailyLogResponse
|  |     |- DailyLogPageResponse
|  |- model
|  |  |- DailyLog
|  |- mapper
|  |  |- DailyLogMapper
|  |- event
|     |- DailyLogUpdatedEvent
|- journal
|  |- controller
|  |  |- JournalController
|  |- service
|  |  |- JournalService
|  |- repository
|  |  |- JournalEntryRepository
|  |- dto
|  |  |- request
|  |  |  |- CreateJournalEntryRequest
|  |  |  |- UpdateJournalEntryRequest
|  |  |- response
|  |     |- JournalEntryResponse
|  |     |- JournalEntryPageResponse
|  |- model
|  |  |- JournalEntry
|  |- mapper
|     |- JournalEntryMapper
|- progression
|  |- controller
|  |  |- ProgressionController
|  |- service
|  |  |- ProgressionService
|  |  |- BadgeRuleService
|  |- repository
|  |  |- ProgressSnapshotRepository
|  |  |- BadgeAwardRepository
|  |- dto
|  |  |- response
|  |     |- ProgressSnapshotResponse
|  |     |- BadgeResponse
|  |- model
|  |  |- ProgressSnapshot
|  |  |- BadgeAward
|  |- rule
|     |- ProgressionRule
|- insights
|  |- controller
|  |  |- InsightController
|  |- service
|  |  |- InsightService
|  |  |- TrendAnalysisService
|  |- repository
|  |  |- DailySummaryRepository
|  |- dto
|  |  |- response
|  |     |- DailySummaryResponse
|  |     |- WeeklySummaryResponse
|  |     |- TrendSignalResponse
|  |- model
|  |  |- DailySummary
|  |- mapper
|     |- InsightMapper
|- assistant
|  |- controller
|  |  |- AssistantController
|  |- service
|  |  |- AssistantService
|  |  |- PromptBuilderService
|  |  |- AssistantFallbackService
|  |- provider
|  |  |- AiProviderClient
|  |  |- OpenAiProviderClient
|  |  |- MockAiProviderClient
|  |- repository
|  |  |- AssistantSuggestionRepository
|  |- dto
|  |  |- request
|  |  |  |- GenerateDailyReviewRequest
|  |  |- response
|  |     |- AssistantSuggestionResponse
|  |- model
|  |  |- AssistantSuggestion
|  |  |- AssistantPromptTrace
|  |- config
|     |- AssistantProperties
|- integration (phase 3+)
|  |- github
|  |  |- service
|  |  |- client
|  |  |- dto
|  |- screentime
|  |  |- service
|  |  |- client
|  |  |- dto
|  |- calendar
|     |- service
|     |- client
|     |- dto
|- shared
   |- exception
   |  |- GlobalExceptionHandler
   |  |- ErrorCode
   |- config
   |  |- JacksonConfig
   |  |- OpenApiConfig
   |  |- CacheConfig
   |- security
   |  |- SecurityConfig
   |  |- JwtAuthenticationFilter
   |  |- CurrentUserFacade
   |- util
      |- TimeProvider
      |- PaginationUtil
```

## What goes inside each directory

This section is the practical rulebook for what files should live in each folder.

## `auth`

- `controller`: auth-only endpoints (`register`, `login`, `refresh`, `logout`).
- `service`: token issuance, password checks, and auth orchestration.
- `repository`: user credential lookups and refresh token persistence.
- `dto/request`: inbound auth payloads with validation annotations.
- `dto/response`: token payloads and minimal auth user views.
- `model`: auth-specific entities (`UserCredential`, `RefreshToken`).
- `mapper`: model-to-response mapping to keep controllers thin.
- `validator`: custom credential/password validators.

## `profile`

- `controller`: profile read/update endpoints.
- `service`: timezone, visibility, and user preference rules.
- `repository`: profile persistence.
- `dto/request`: profile update contracts.
- `dto/response`: sanitized profile responses.
- `model`: persisted profile entity.
- `mapper`: transformation between profile entity and DTOs.

## `tracking`

- `controller`: daily log CRUD endpoints by date.
- `service`: create/update logic, ownership checks, and transaction boundaries.
- `repository`: `DailyLog` retrieval and range queries.
- `dto/request`: daily log write payloads (`sleepHours`, `workHours`, etc.).
- `dto/response`: daily log and paged history views.
- `model`: `DailyLog` and related value objects.
- `mapper`: request->model and model->response mappings.
- `event`: internal events such as `DailyLogUpdatedEvent` for progression recalculation.

## `journal`

- `controller`: create/edit/delete/search journal endpoints.
- `service`: ownership, content rules, and tag/mood normalization.
- `repository`: journal persistence and filtered search.
- `dto/request`: create/update journal payloads.
- `dto/response`: journal view contracts.
- `model`: `JournalEntry` entity and related tag structures.
- `mapper`: model/DTO conversions.

## `progression`

- `controller`: progress dashboard and badge endpoints.
- `service`: XP calculations, streak updates, and milestone checks.
- `repository`: snapshots and badge awards persistence.
- `dto/response`: progress, level, streak, and badge payloads.
- `model`: `ProgressSnapshot`, `BadgeAward`.
- `rule`: isolated progression rules to avoid hardcoded logic in services.

## `insights`

- `controller`: daily/weekly summary and trend endpoints.
- `service`: analytical computations from tracking + journal data.
- `repository`: persisted summaries and precomputed read models.
- `dto/response`: summary/trend API contracts.
- `model`: `DailySummary` and optional trend projection records.
- `mapper`: conversion from analytical records to response DTOs.

## `assistant`

- `controller`: AI review endpoints.
- `service`: assistant orchestration, retries, fallback, and trace persistence.
- `provider`: provider-specific API clients behind a common interface.
- `repository`: assistant suggestion and prompt trace persistence.
- `dto/request`: AI generation input contracts.
- `dto/response`: suggestion payloads and rationale fields.
- `model`: `AssistantSuggestion`, `AssistantPromptTrace`.
- `config`: assistant config (`timeouts`, `retry`, `model`, `feature flags`).

## `integration` (phase 3+)

- `github/service`: sync orchestration and scheduling logic.
- `github/client`: GitHub REST/GraphQL HTTP clients.
- `github/dto`: external payload adapters.
- `screentime/service`: sync and normalization rules.
- `screentime/client`: source-specific connectors.
- `screentime/dto`: incoming source payloads.
- `calendar/service`: import and block classification logic.
- `calendar/client`: calendar provider connectors.
- `calendar/dto`: event mapping contracts.

## `shared`

- `exception`: cross-module errors and RFC 9457 mapping.
- `config`: shared app configuration beans.
- `security`: auth filters, user resolution, and access helpers.
- `util`: stateless utility classes (`TimeProvider`, pagination helpers).

## Layer responsibilities

- `controller`
  - Accept request DTOs, validate input, return response DTOs.
  - Reason: keep transport concerns separate and controllers thin.
- `service`
  - Own business rules, transactions, and orchestration.
  - Reason: centralize domain behavior and prevent logic leakage.
- `repository`
  - Persist and fetch entities with no business rules.
  - Reason: maintain clean persistence boundaries.
- `dto`
  - Input/output contracts for API endpoints.
  - Reason: avoid exposing JPA internals and control schema changes.
- `model`
  - Persistence entities and domain state.
  - Reason: explicit domain-first language (`DailyLog`, `ProgressSnapshot`).

## Module functions and rationale

## `auth`

- `registerUser(request)`
  - Reason: create new account with validated credentials and secure password hashing.
- `login(request)`
  - Reason: authenticate users and issue access/refresh tokens.
- `refreshToken(request)`
  - Reason: keep sessions secure without forcing frequent logins.
- `revokeSession(userId, sessionId)`
  - Reason: allow explicit logout and incident response for compromised sessions.

## `profile`

- `getProfile(userId)`
  - Reason: fetch user preferences needed for personalized summaries.
- `updateProfile(userId, request)`
  - Reason: manage timezone, visibility, and preference updates in one place.
- `updateNotificationPreferences(userId, request)`
  - Reason: future-proof reminders and nudges without touching tracking logic.

## `tracking`

- `upsertDailyLog(userId, date, request)`
  - Reason: idempotent create/update for daily activity tracking.
- `getDailyLog(userId, date)`
  - Reason: power daily dashboard and editing workflows.
- `listDailyLogs(userId, from, to, page)`
  - Reason: support history views and trend calculations.
- `validateDailyLogCompleteness(userId, date)`
  - Reason: gate progression rewards by minimum data quality.

## `journal`

- `createJournalEntry(userId, request)`
  - Reason: capture qualitative context alongside numeric logs.
- `updateJournalEntry(userId, entryId, request)`
  - Reason: allow corrections while enforcing ownership.
- `deleteJournalEntry(userId, entryId)`
  - Reason: support privacy and cleanup needs.
- `searchJournalEntries(userId, filters, page)`
  - Reason: allow retrospective reflection by date/tags/mood.

## `progression`

- `recalculateDailyProgress(userId, date)`
  - Reason: compute XP and streaks whenever relevant data changes.
- `getCurrentProgress(userId)`
  - Reason: provide immediate sense of accomplishment.
- `getProgressHistory(userId, from, to)`
  - Reason: show trajectory instead of only current state.
- `evaluateBadgeAwards(userId, date)`
  - Reason: trigger milestones for motivation loops.

## `insights`

- `buildDailySummary(userId, date)`
  - Reason: convert raw logs into interpretable metrics.
- `buildWeeklySummary(userId, week)`
  - Reason: highlight patterns and reduce day-to-day noise.
- `computeTrendSignals(userId, from, to)`
  - Reason: detect improving/declining behaviors for assistant usage.
- `calculateBalanceIndex(userId, date)`
  - Reason: quantify work-rest-leisure distribution.

## `assistant`

- `generateDailyReview(userId, date)`
  - Reason: produce actionable feedback from current day and short history.
- `buildPromptContext(userId, date)`
  - Reason: isolate prompt assembly and keep provider input deterministic.
- `saveSuggestionTrace(userId, date, request, response)`
  - Reason: provide auditability and explanation fields for trust.
- `fallbackSuggestion(userId, date)`
  - Reason: preserve UX when AI provider times out or fails.

## `integration` (future)

- `syncGithubActivity(userId, dateRange)`
  - Reason: enrich productivity signals with objective coding activity.
- `syncScreenTime(userId, dateRange)`
  - Reason: reduce manual logging and improve accuracy.
- `syncCalendarBlocks(userId, dateRange)`
  - Reason: infer meeting load vs deep-work capacity.

## `shared`

- `toProblemDetail(exception, request)`
  - Reason: enforce RFC 9457-compliant error responses globally.
- `resolveCurrentUser()`
  - Reason: standardize authenticated user retrieval.
- `clockProvider()`
  - Reason: ensure deterministic date/time behavior in business logic and tests.
- `paginationMapper(pageRequest)`
  - Reason: keep pagination format consistent across endpoints.

## Naming and design rules

- Use domain terms in public methods (`DailyLog`, `ProgressSnapshot`, `AssistantSuggestion`).
- Keep methods small and single-purpose.
- Use constructor injection only.
- Validate all request DTOs with Jakarta Validation.
- Keep repositories free of business logic.
- Set transaction boundaries in services.

## How to use this document while coding

- Start with one feature module at a time.
- Add controller, DTOs, service, repository, and tests in the same feature folder.
- Implement only listed core functions first, then iterate.
- Keep API contracts aligned with `docs/api-design.md`.

