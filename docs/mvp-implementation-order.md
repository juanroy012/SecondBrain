# MVP Implementation Order

This document gives a practical class-by-class implementation sequence so you can build `SecondBrain` in small, testable vertical slices.

## Build strategy

- Use vertical slices by feature, but keep class creation order inside each slice consistent.
- Recommended order inside each feature:
  1. `model`
  2. `repository`
  3. `dto`
  4. `mapper`
  5. `service`
  6. `controller`
  7. tests (unit + integration)

## Phase A: Foundation and shared infrastructure

1. `shared.config.JacksonConfig`
2. `shared.exception.ErrorCode`
3. `shared.exception.GlobalExceptionHandler`
4. `shared.util.TimeProvider`
5. `shared.util.PaginationUtil`
6. `shared.security.CurrentUserFacade`
7. `shared.security.SecurityConfig`

Reason: all feature modules rely on consistent error format, time handling, pagination, and auth context.

## Phase B: Auth + profile (required for ownership checks)

### `auth`

1. `auth.model.UserCredential`
2. `auth.model.RefreshToken`
3. `auth.repository.UserAuthRepository`
4. `auth.repository.RefreshTokenRepository`
5. `auth.dto.request.RegisterRequest`
6. `auth.dto.request.LoginRequest`
7. `auth.dto.request.RefreshTokenRequest`
8. `auth.dto.response.AuthTokenResponse`
9. `auth.dto.response.UserAuthView`
10. `auth.mapper.AuthMapper`
11. `auth.validator.PasswordStrengthValidator`
12. `auth.service.PasswordPolicyService`
13. `auth.service.JwtTokenService`
14. `auth.service.AuthService`
15. `auth.controller.AuthController`

### `profile`

1. `profile.model.UserProfile`
2. `profile.repository.UserProfileRepository`
3. `profile.dto.request.UpdateProfileRequest`
4. `profile.dto.request.UpdateNotificationPreferencesRequest`
5. `profile.dto.response.UserProfileResponse`
6. `profile.mapper.ProfileMapper`
7. `profile.service.ProfileService`
8. `profile.controller.ProfileController`

## Phase C: Core logging flow (tracking + journal)

### `tracking`

1. `tracking.model.DailyLog`
2. `tracking.repository.DailyLogRepository`
3. `tracking.dto.request.UpsertDailyLogRequest`
4. `tracking.dto.response.DailyLogResponse`
5. `tracking.dto.response.DailyLogPageResponse`
6. `tracking.mapper.DailyLogMapper`
7. `tracking.event.DailyLogUpdatedEvent`
8. `tracking.service.DailyLogValidationService`
9. `tracking.service.DailyLogService`
10. `tracking.controller.DailyLogController`

### `journal`

1. `journal.model.JournalEntry`
2. `journal.repository.JournalEntryRepository`
3. `journal.dto.request.CreateJournalEntryRequest`
4. `journal.dto.request.UpdateJournalEntryRequest`
5. `journal.dto.response.JournalEntryResponse`
6. `journal.dto.response.JournalEntryPageResponse`
7. `journal.mapper.JournalEntryMapper`
8. `journal.service.JournalService`
9. `journal.controller.JournalController`

## Phase D: User value loop (insights + progression)

### `insights`

1. `insights.model.DailySummary`
2. `insights.repository.DailySummaryRepository`
3. `insights.dto.response.DailySummaryResponse`
4. `insights.dto.response.WeeklySummaryResponse`
5. `insights.dto.response.TrendSignalResponse`
6. `insights.mapper.InsightMapper`
7. `insights.service.TrendAnalysisService`
8. `insights.service.InsightService`
9. `insights.controller.InsightController`

### `progression`

1. `progression.model.ProgressSnapshot`
2. `progression.model.BadgeAward`
3. `progression.repository.ProgressSnapshotRepository`
4. `progression.repository.BadgeAwardRepository`
5. `progression.dto.response.ProgressSnapshotResponse`
6. `progression.dto.response.BadgeResponse`
7. `progression.rule.ProgressionRule`
8. `progression.service.BadgeRuleService`
9. `progression.service.ProgressionService`
10. `progression.controller.ProgressionController`

## Phase E: Assistant MVP

1. `assistant.model.AssistantSuggestion`
2. `assistant.model.AssistantPromptTrace`
3. `assistant.repository.AssistantSuggestionRepository`
4. `assistant.dto.request.GenerateDailyReviewRequest`
5. `assistant.dto.response.AssistantSuggestionResponse`
6. `assistant.provider.AiProviderClient`
7. `assistant.provider.MockAiProviderClient`
8. `assistant.provider.OpenAiProviderClient`
9. `assistant.config.AssistantProperties`
10. `assistant.service.PromptBuilderService`
11. `assistant.service.AssistantFallbackService`
12. `assistant.service.AssistantService`
13. `assistant.controller.AssistantController`

## Minimum test checkpoints

- After Phase B: auth + profile ownership and JWT tests.
- After Phase C: daily log and journal integration tests.
- After Phase D: summary/progression consistency tests.
- After Phase E: assistant fallback and trace persistence tests.

## Definition of done per phase

- Endpoints documented in OpenAPI.
- DTO validation active.
- No entity exposure in API responses.
- Flyway migration updated for new schema.
- Passing tests for new feature slice.

