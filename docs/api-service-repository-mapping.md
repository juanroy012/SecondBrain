# API to Service to Repository Mapping

This document maps each planned endpoint to controller, service, repository, and DTO contracts.

## Conventions

- Base path: `/api/v1`
- All user-specific endpoints require authenticated user context.
- Controllers validate DTOs and delegate to services.
- Services enforce ownership and transaction boundaries.

## Auth

- `POST /api/v1/auth/register`
  - Controller: `AuthController.register`
  - Service: `AuthService.registerUser`
  - Repository: `UserAuthRepository`
  - Request DTO: `RegisterRequest`
  - Response DTO: `AuthTokenResponse`

- `POST /api/v1/auth/login`
  - Controller: `AuthController.login`
  - Service: `AuthService.login`
  - Repository: `UserAuthRepository`, `RefreshTokenRepository`
  - Request DTO: `LoginRequest`
  - Response DTO: `AuthTokenResponse`

- `POST /api/v1/auth/refresh`
  - Controller: `AuthController.refresh`
  - Service: `AuthService.refreshToken`
  - Repository: `RefreshTokenRepository`
  - Request DTO: `RefreshTokenRequest`
  - Response DTO: `AuthTokenResponse`

## Profile

- `GET /api/v1/profile`
  - Controller: `ProfileController.getProfile`
  - Service: `ProfileService.getProfile`
  - Repository: `UserProfileRepository`
  - Response DTO: `UserProfileResponse`

- `PUT /api/v1/profile`
  - Controller: `ProfileController.updateProfile`
  - Service: `ProfileService.updateProfile`
  - Repository: `UserProfileRepository`
  - Request DTO: `UpdateProfileRequest`
  - Response DTO: `UserProfileResponse`

## Tracking

- `POST /api/v1/logs/daily`
  - Controller: `DailyLogController.upsert`
  - Service: `DailyLogService.upsertDailyLog`
  - Repository: `DailyLogRepository`
  - Request DTO: `UpsertDailyLogRequest`
  - Response DTO: `DailyLogResponse`

- `GET /api/v1/logs/daily/{date}`
  - Controller: `DailyLogController.getByDate`
  - Service: `DailyLogService.getDailyLog`
  - Repository: `DailyLogRepository`
  - Response DTO: `DailyLogResponse`

- `GET /api/v1/logs/daily?from=&to=&page=&size=`
  - Controller: `DailyLogController.list`
  - Service: `DailyLogService.listDailyLogs`
  - Repository: `DailyLogRepository`
  - Response DTO: `DailyLogPageResponse`

## Journal

- `POST /api/v1/journal`
  - Controller: `JournalController.create`
  - Service: `JournalService.createJournalEntry`
  - Repository: `JournalEntryRepository`
  - Request DTO: `CreateJournalEntryRequest`
  - Response DTO: `JournalEntryResponse`

- `PUT /api/v1/journal/{id}`
  - Controller: `JournalController.update`
  - Service: `JournalService.updateJournalEntry`
  - Repository: `JournalEntryRepository`
  - Request DTO: `UpdateJournalEntryRequest`
  - Response DTO: `JournalEntryResponse`

- `DELETE /api/v1/journal/{id}`
  - Controller: `JournalController.delete`
  - Service: `JournalService.deleteJournalEntry`
  - Repository: `JournalEntryRepository`

- `GET /api/v1/journal?from=&to=&tag=&page=&size=`
  - Controller: `JournalController.search`
  - Service: `JournalService.searchJournalEntries`
  - Repository: `JournalEntryRepository`
  - Response DTO: `JournalEntryPageResponse`

## Insights

- `GET /api/v1/insights/daily/{date}`
  - Controller: `InsightController.getDailySummary`
  - Service: `InsightService.buildDailySummary`
  - Repository: `DailySummaryRepository`, `DailyLogRepository`, `JournalEntryRepository`
  - Response DTO: `DailySummaryResponse`

- `GET /api/v1/insights/weekly?week=`
  - Controller: `InsightController.getWeeklySummary`
  - Service: `InsightService.buildWeeklySummary`
  - Repository: `DailySummaryRepository`, `DailyLogRepository`
  - Response DTO: `WeeklySummaryResponse`

- `GET /api/v1/insights/trends?from=&to=`
  - Controller: `InsightController.getTrendSignals`
  - Service: `InsightService.computeTrendSignals`
  - Repository: `DailySummaryRepository`
  - Response DTO: `TrendSignalResponse`

## Progression

- `GET /api/v1/progress`
  - Controller: `ProgressionController.getCurrent`
  - Service: `ProgressionService.getCurrentProgress`
  - Repository: `ProgressSnapshotRepository`
  - Response DTO: `ProgressSnapshotResponse`

- `GET /api/v1/progress/history?from=&to=`
  - Controller: `ProgressionController.getHistory`
  - Service: `ProgressionService.getProgressHistory`
  - Repository: `ProgressSnapshotRepository`
  - Response DTO: `ProgressSnapshotResponse`

- `GET /api/v1/progress/badges`
  - Controller: `ProgressionController.getBadges`
  - Service: `ProgressionService.getBadges`
  - Repository: `BadgeAwardRepository`
  - Response DTO: `BadgeResponse`

## Assistant

- `POST /api/v1/assistant/daily-review/{date}`
  - Controller: `AssistantController.generateDailyReview`
  - Service: `AssistantService.generateDailyReview`
  - Repository: `AssistantSuggestionRepository`, `DailyLogRepository`, `JournalEntryRepository`
  - Provider: `AiProviderClient`
  - Request DTO: `GenerateDailyReviewRequest` (optional override fields)
  - Response DTO: `AssistantSuggestionResponse`

- `GET /api/v1/assistant/history?from=&to=`
  - Controller: `AssistantController.getHistory`
  - Service: `AssistantService.getSuggestionHistory`
  - Repository: `AssistantSuggestionRepository`
  - Response DTO: `AssistantSuggestionResponse`

## Ownership/security checks by module

- `profile`: only current user profile can be read/updated.
- `tracking`: user can access only own `DailyLog` rows.
- `journal`: user can edit/delete only own entries.
- `progression`: read only own progression data.
- `assistant`: generate/read only own suggestions and traces.

