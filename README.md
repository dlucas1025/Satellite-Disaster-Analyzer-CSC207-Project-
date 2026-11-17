# NorthWatch — Satellite Hazard Watch

## Project specification

NorthWatch is a desktop application that helps people responsible for small and remote
Canadian communities see when a flood or wildfire is coming. Users build a watchlist of
communities, check each one's flood and wildfire risk, and compare satellite pictures of
the area from before and after an event. Ratings can be viewed as of a past date to review
past events, and the watchlist is saved between sessions.

## User stories

1. **[Member A]** As an emergency coordinator, I want to search for a community by name, pick the right one
   when several places share that name, and save it to a watchlist tied to my account, so that the places
   I am responsible for are there every time I open the app on any computer, even without internet.
2. **[Member B]** As an emergency coordinator, I want to see a flood-risk rating (Low, Moderate, High or
   Severe) for each of the next 7 days for a saved community, along with the reasons for it, so that I can
   decide whether to warn residents.
3. **[Member C]** As a rural resident, I want to see active wildfires near my community on a map, how far
   away the nearest one is, and whether the wind is blowing it toward us so that I know whether to prepare
   to leave.
4. **[Member D]** As an emergency coordinator, I want to compare satellite pictures of my community from
   before and after an event and see how much land was flooded or burned, so that I can tell how much of
   the area a flood or wildfire has affected.

---

## Getting started

1. Clone the repo and open the folder in IntelliJ. It is a Maven project; accept "Load Maven project".
2. Use **JDK 17 or later** (File → Project Structure → SDK; IntelliJ can download one).
3. Copy `config.example.properties` to `config.properties` and add any API keys you need.
   `config.properties` is ignored by Git — never commit keys.
4. Run `app.Main`. You should see four tabs. Every class already exists as a stub, so each
   button shows a "not implemented yet" message until its owner fills in the `TODO`s.
5. Run the tests: right-click `src/test/java` → Run All Tests, or `mvn verify`
   (which also produces the coverage report in `target/site/jacoco/` and Checkstyle warnings).

## Architecture

The code follows Clean Architecture with the same package names as the course's ca-login starter.
Dependencies only point inward: `view` → `interface_adapter` → `use_case` → `entity`.
`data_access` implements interfaces declared in `use_case`. `app.AppBuilder` is the only class that
knows every concrete class. See **[ARCHITECTURE.md](ARCHITECTURE.md)** for the layer diagram,
the request flow and each member's class checklist, and `plantuml/architecture.puml`.

```
src/main/java/
  app/                 Main, AppBuilder                         (shared, one method pair per story)
  entity/              Community, BoundingBox, Hazard,          (shared contract)
                       RiskLevel, RiskFactor, RiskAssessment
  entity/risk/         RiskModel<T>                             (shared contract, Strategy)
  use_case/shared/     CommunityProvider                        (shared contract)
  use_case/<story>/    InputBoundary, InputData, Interactor,
                       OutputBoundary, OutputData, DataAccessInterface
  interface_adapter/   ViewModel<T>                             (shared, Observer)
  interface_adapter/<story>/  Controller, Presenter, State
  data_access/         DemoCommunityProvider, ApiConfig         (shared)
                       + each member's API / file data access objects
  view/                PlaceholderView                          (shared)
                       + each member's view
src/test/java/         entity tests for the shared contract; one InteractorTest per story
plantuml/              architecture diagram; add one sequence diagram per story
```

### Shared contract (already written — change only by a team-reviewed PR)

| Class | Purpose |
|---|---|
| `entity.Community` | A watched place; distance, bearing and surrounding area. Every story's input. |
| `entity.BoundingBox` | Lat/lon rectangle; converts coordinates to picture pixels. |
| `entity.Hazard`, `entity.RiskLevel` | FLOOD / WILDFIRE; LOW–SEVERE from a 0–100 score. |
| `entity.RiskFactor`, `entity.RiskAssessment` | A rating and the reasons behind it. |
| `entity.risk.RiskModel<T>` | Strategy interface for hazard rules (B and C implement it). |
| `use_case.shared.CommunityProvider` | Where stories 2–4 get communities from. |
| `use_case.shared.DataUnavailableException` | Thrown by data access when data can't be fetched; interactors turn it into a message. |
| `data_access.DemoCommunityProvider` | Peguis First Nation and Jasper, used until the watchlist is ready. |
| `data_access.ApiConfig` | Reads keys from env variables or `config.properties`. |
| `interface_adapter.ViewModel<T>` | View state + change listeners. |
| `app.AppBuilder`, `app.Main` | Composition root; one tab per story. |

### Who owns what

Each member builds their story as a vertical slice through every layer, in their own packages.
Each `use_case/<story>/package-info.java` lists the classes to write.

| Story | Owner | Packages | Data | Design focus |
|---|---|---|---|---|
| 1 Watchlist | A | `use_case.save_community`, `interface_adapter.save_community`, `entity.Watchlist`, `view.WatchlistView` | Place search API; JSON file + course user-data API | Dependency Inversion (file vs cloud store), Decorator (offline fallback) |
| 2 Flood risk | B | `use_case.flood_risk`, `interface_adapter.flood_risk`, `entity.risk.FloodRiskModel`, `view.FloodRiskView` | River-flow forecast/history, weather, river gauges | Strategy + Open/Closed (`RiskModel`) |
| 3 Wildfires | C | `use_case.nearby_fires`, `interface_adapter.nearby_fires`, `entity.FireDetection`, `entity.risk.WildfireRiskModel`, `view.FireMapView` | Satellite fire detections, wind | Single Responsibility (fetch / parse / score / draw) |
| 4 Satellite | D | `use_case.compare_imagery`, `interface_adapter.compare_imagery`, `entity.SatelliteImage`, `entity.risk.LandClassifier`, `view.ImageCompareView` | Satellite imagery | Decorator (image cache), Strategy (water vs burn classifier), Liskov |

### Rules that keep everyone independent

- **Only edit your own packages** and your own two methods in `AppBuilder`.
- **Interactor input data carries a community id**; look it up with `CommunityProvider`.
  Never depend on another story's classes.
- **No shared data access code.** If two stories call the same API, each writes its own small
  data access interface and class. Merging them later is a planned refactor.
- **Entities and use cases import nothing** from Swing, `java.awt`, OkHttp, org.json or `data_access`.
- **Tests use fakes, never live APIs.** Save sample responses in `src/test/resources`.
- **Keep the GUI responsive:** call controllers inside a `SwingWorker`; update components with
  `SwingUtilities.invokeLater`.

### Workflow

- Branch from `main` as `<story>/<short-description>`, e.g. `flood-risk/interactor`.
- Keep pull requests small (one layer or one thin slice, roughly under 400 lines).
- Fill in the PR template, get one review from a teammate, merge only when CI is green.
- CI (`.github/workflows/ci.yml`) runs `mvn -B verify`: compile, tests, coverage and Checkstyle.
