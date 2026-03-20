# API Design (First Pass)

## API conventions

- Base path: `/api/v1`
- Date format: ISO-8601
- Error payload: RFC 9457 style (`type`, `title`, `status`, `detail`, `instance`)
- Validation errors include field-level details

## Endpoint groups

### Auth

- `POST /auth/register`
- `POST /auth/login`
- `POST /auth/refresh`

### Tracking

- `POST /logs/daily`
- `GET /logs/daily/{date}`
- `PUT /logs/daily/{date}`
- `GET /logs/daily?from=...&to=...`

### Journal

- `POST /journal`
- `GET /journal?date=...`
- `PUT /journal/{id}`
- `DELETE /journal/{id}`

### Insights

- `GET /insights/daily/{date}`
- `GET /insights/weekly?week=...`
- `GET /insights/trends?from=...&to=...`

### Progression

- `GET /progress`
- `GET /progress/history`
- `GET /progress/badges`

### Assistant

- `POST /assistant/daily-review/{date}`
- `GET /assistant/history?from=...&to=...`

## Example response shape (daily dashboard)

```json
{
  "date": "2026-03-20",
  "activity": {
    "sleepHours": 7.5,
    "workHours": 8.0,
    "leisureHours": 2.5,
    "focusMinutes": 180
  },
  "summary": {
    "productivityScore": 78,
    "balanceScore": 71,
    "trend": "improving"
  },
  "progression": {
    "totalXp": 540,
    "level": 4,
    "streakDays": 6,
    "newBadges": ["FOCUS_5_DAYS"]
  },
  "assistant": {
    "comment": "Good consistency this week. Increase sleep by 30 minutes for better focus.",
    "suggestions": [
      "Block two 90-minute deep work sessions tomorrow",
      "Limit social media to 45 minutes"
    ],
    "rationale": "Sleep dipped while work intensity increased over 3 days"
  }
}
```

## Versioning strategy

- URI-based versioning (`/v1`), move to `/v2` only for breaking changes.
- Non-breaking additions allowed within same version.

