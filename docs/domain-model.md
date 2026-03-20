# Domain Model (MVP + Extension)

## Core entities

- `User`
  - id, email, username, passwordHash, timezone, createdAt, updatedAt
- `DailyLog`
  - id, userId, date, sleepHours, workHours, leisureHours, exerciseMinutes, focusMinutes, moodScore
- `JournalEntry`
  - id, userId, dateTime, title, content, sentimentLabel, tags
- `DailySummary`
  - id, userId, date, productivityScore, balanceScore, stressSignal, highlights
- `ProgressSnapshot`
  - id, userId, totalXp, level, streakDays, longestStreak, badges
- `AssistantSuggestion`
  - id, userId, date, summaryText, recommendationText, rationale, confidence

## Future integration entities

- `GithubActivity`
  - id, userId, date, commitCount, reposTouched
- `ScreenTimeRecord`
  - id, userId, date, productiveMinutes, distractingMinutes, source
- `CalendarRecord`
  - id, userId, date, meetingsCount, deepWorkBlocks

## Relationship sketch

- User 1..N DailyLog
- User 1..N JournalEntry
- User 1..N DailySummary
- User 1..N AssistantSuggestion
- User 1..N ProgressSnapshot (or one rolling latest snapshot + history table)

## Derived metrics

- `consistencyScore`: based on completion rate over N days.
- `focusQuality`: focusMinutes adjusted by sleep and interruptions.
- `balanceIndex`: distribution of work/leisure/exercise/rest.
- `weeklyTrend`: slope of metrics over rolling 7-day windows.

## Progression design

- XP rules
  - +10 complete daily log
  - +5 journal entry
  - +bonus for 7-day streak
- Levels
  - level thresholds (e.g., logarithmic growth)
- Badges
  - first week complete, 30-day consistency, improved sleep trend

## Data retention and privacy

- Hard delete account data on user request.
- Soft delete where analytics integrity is needed.
- Keep assistant prompts/responses auditable and redactable.

