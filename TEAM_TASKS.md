# Team tasks — who works with which classes

Every class below already exists in the repo and compiles. **Status** says what is left:

- **Done** — finished plumbing; use it, don't rewrite it.
- **Partial** — works, but has `TODO Member X` items to finish.
- **TODO** — the main logic is yours to write.
- **New** — a file you create.

Search the code for `TODO Shreyas` (or Lucas, Hamza, Youssef) to jump straight to your work.
Paths are under `src/main/java/` unless they start with `src/test/`.

---

## Shared code (everyone uses it; change only by a team-reviewed PR)

| Layer | Class | What it is | Used by |
|---|---|---|---|
| Entity | `entity/Community` | A watched place: name, province, lat/lon, `distanceKmTo`, `bearingTo`, `areaAround` | Everyone |
| Entity | `entity/BoundingBox` | Lat/lon rectangle; `contains`, `toPixel` for maps | Hamza, Youssef |
| Entity | `entity/Hazard` | `FLOOD`, `WILDFIRE` | Lucas, Hamza, Youssef |
| Entity | `entity/RiskLevel` | `LOW`–`SEVERE` from a 0–100 score | Lucas, Hamza |
| Entity | `entity/RiskFactor` | One reason behind a rating + its points | Lucas, Hamza |
| Entity | `entity/RiskAssessment` | A rating: score, level, reasons, date | Lucas, Hamza |
| Entity | `entity/risk/RiskModel<T>` | Strategy interface for hazard rules | Lucas, Hamza |
| Use case | `use_case/shared/CommunityProvider` | Where stories 2–4 get communities from | Lucas, Hamza, Youssef (Shreyas implements it) |
| Use case | `use_case/shared/DataUnavailableException` | Thrown when data can't be fetched; interactors turn it into a message | Everyone |
| Adapter | `interface_adapter/ViewModel<T>` | Holds a view's State, notifies the view | Everyone |
| Data access | `data_access/DemoCommunityProvider` | Peguis + Jasper until the watchlist is ready | Lucas, Hamza, Youssef |
| Data access | `data_access/ApiConfig` | Reads keys from `config.properties` / env vars | Shreyas, Hamza |
| App | `app/Main`, `app/AppBuilder` | Starts the app; wires every story (already done) | everyone |

---

## Shreyas — Story 1: Save communities to a watchlist

Package names: `save_community`

| Layer | Class | Kind | What it does | Status | Your job |
|---|---|---|---|---|---|
| Entity | `entity/Watchlist` | class | A user's saved communities | TODO | `add` (reject duplicates), `remove`, `rename`, `find` |
| Use case | `use_case/save_community/SaveCommunityInputData` | record | What the user asked: search / save / remove / rename | Done | — |
| Use case | `SaveCommunityInputBoundary` | interface | `load`, `search`, `save`, `remove`, `rename` | Done | — |
| Use case | `SaveCommunityInteractor` | class | The story's logic | TODO | All five methods; catch `DataUnavailableException` → `prepareFailView` |
| Use case | `SaveCommunityOutputData` | record | Search results, watchlist, message | Done | — |
| Use case | `SaveCommunityOutputBoundary` | interface | Search results / watchlist / fail views | Done | — |
| Use case | `PlaceSearchDataAccessInterface` | interface | `searchPlaces(query)` | Done | — |
| Use case | `WatchlistDataAccessInterface` | interface | `load(owner)`, `save(watchlist)` | Done | — |
| Adapter | `interface_adapter/save_community/SaveCommunityController` | class | Button clicks → interactor | Done | Later: real sign-in instead of `demo-user` |
| Adapter | `SaveCommunityPresenter` | class | Output → State | Partial | Format "name, province" labels if needed |
| Adapter | `SaveCommunityState` | class | What the screen shows | Done | — |
| Data access | `data_access/GeocodingDataAccessObject` | class | Place search API (Canada only) | TODO | HTTP call + JSON parsing → `List<Community>` |
| Data access | `FileWatchlistDataAccessObject` | class | `~/.northwatch/watchlist.json` | TODO | Read/write JSON; empty watchlist if no file |
| Data access | `CloudWatchlistDataAccessObject` | class | Course user-data API | TODO | Load/save the watchlist in the user's cloud info |
| Data access | `FallbackWatchlistDataAccessObject` | class | **Decorator**: cloud first, file when offline | Partial | Try primary, fall back to backup, sync later |
| Data access | `WatchlistCommunityProvider` | class | Lets stories 2–4 read the watchlist | Done | — |
| View | `view/WatchlistView` | JPanel | Search box, results, watchlist | Partial | Rename button, "name, province" renderer, `SwingWorker` |
| Test | `src/test/.../SaveCommunityInteractorTest` | test | 3 disabled placeholders | TODO | Write fakes; enable tests |
| Test | `src/test/java/entity/WatchlistTest` | test | — | New | Duplicates, remove, rename |
| Test | `src/test/java/data_access/GeocodingDataAccessObjectTest` | test | — | New | Parse a saved sample response from `src/test/resources` |

**Integration (last week):** in `AppBuilder`, switch `communityProvider` to `WatchlistCommunityProvider`.
**Design to present:** Dependency Inversion (file vs cloud store), Decorator (fallback), Interface Segregation.

---

## Lucas — Story 2: Flood risk for the coming week

Package names: `flood_risk`

| Layer | Class | Kind | What it does | Status | Your job |
|---|---|---|---|---|---|
| Entity | `entity/FlowForecast` | record | Forecast daily river flow | Partial | `peak()`, `peakDay()` |
| Entity | `entity/FlowHistory` | record | Past flows by day of year | TODO | `percentileOf(day, flow)`, `median(day)` |
| Entity | `entity/WeatherOutlook` | record | Daily rain, snow, temperature | Partial | `maxRainOver(days)`, `warmDays(threshold)` |
| Entity | `entity/RiverGauge` | record | A river station; `distanceKmTo` | Done | — |
| Entity | `entity/GaugeReading` | record | One gauge measurement | Done | — |
| Entity | `entity/risk/FloodConditions` | record | Everything the rules look at | Done | — |
| Entity | `entity/risk/FloodRiskModel` | class | **Strategy**: the flood rules | TODO | Flow, rain, snowmelt, gauge-rise rules → `RiskAssessment` |
| Use case | `use_case/flood_risk/FloodRiskInputData` | record | Community id + as-of date | Done | — |
| Use case | `FloodRiskInputBoundary` | interface | `execute(input)` | Done | — |
| Use case | `FloodRiskInteractor` | class | The story's logic | TODO | Gather data, rate 7 days, "Cannot assess" on missing data |
| Use case | `FloodRiskOutputData` | record | 7 ratings + chart data | Done | — |
| Use case | `FloodRiskOutputBoundary` | interface | Success / fail views | Done | — |
| Use case | `RiverDataAccessInterface` | interface | Flow forecast, history, nearest gauge, readings | Done | — |
| Use case | `FloodWeatherDataAccessInterface` | interface | `getWeather(...)` | Done | — |
| Adapter | `interface_adapter/flood_risk/FloodRiskController` | class | Assess button → interactor | Done | — |
| Adapter | `FloodRiskPresenter` | class | Output → State | TODO | Overall level, daily labels, reasons, chart data, no-gauge note |
| Adapter | `FloodRiskState` | class | What the screen shows | Done | — |
| Data access | `data_access/HydrologyDataAccessObject` | class | River flow + gauges | TODO | 4 methods; cache flow history to disk |
| Data access | `FloodWeatherDataAccessObject` | class | Rain, snow, temperature | TODO | Forecast for today, historical for past dates |
| View | `view/FloodRiskView` | JPanel | Rating, reasons, chart | Partial | Date picker, JFreeChart, `SwingWorker` |
| Test | `src/test/.../FloodRiskInteractorTest` | test | 3 disabled placeholders | TODO | Write fakes; enable tests |
| Test | `src/test/java/entity/risk/FloodRiskModelTest` | test | — | New | One test per rule |
| Test | parsing tests for both data access classes | test | — | New | Saved sample responses |

**Design to present:** Strategy + Open/Closed (`RiskModel` is injected; a new rule is a new class).

---

## Hamza — Story 3: Nearby wildfires

Package names: `nearby_fires`

| Layer | Class | Kind | What it does | Status | Your job |
|---|---|---|---|---|---|
| Entity | `entity/FireDetection` | record | One satellite fire hot spot; `distanceKmTo` | Done | — |
| Entity | `entity/risk/FireConditions` | record | Fires, wind, radius | Done | — |
| Entity | `entity/risk/WildfireRiskModel` | class | **Strategy**: the wildfire rules | TODO | Distance, wind-toward-town, count, intensity → `RiskAssessment` |
| Use case | `use_case/nearby_fires/NearbyFiresInputData` | record | Community id, radius, days, date | Done | — |
| Use case | `NearbyFiresInputBoundary` | interface | `execute(input)` | Done | — |
| Use case | `NearbyFiresInteractor` | class | The story's logic | TODO | Find fires, filter by radius, nearest + bearing, wind, rate |
| Use case | `NearbyFiresOutputData` | record | Fires, nearest, wind, rating | Done | — |
| Use case | `NearbyFiresOutputBoundary` | interface | Success / fail views | Done | — |
| Use case | `FireDataAccessInterface` | interface | `findFires(area, lastDay, days)` | Done | — |
| Use case | `WindDataAccessInterface` | interface | `getWindFromDegrees(...)` | Done | — |
| Adapter | `interface_adapter/nearby_fires/NearbyFiresController` | class | Check button → interactor | Done | — |
| Adapter | `NearbyFiresPresenter` | class | Output → State | TODO | Summary ("12 fires, nearest 18 km NW"), level, reasons, points, wind |
| Adapter | `NearbyFiresState` | class | What the screen shows | Done | — |
| Data access | `data_access/FirmsDataAccessObject` | class | NASA FIRMS fire detections | TODO | Build URL, fetch CSV, hand to parser |
| Data access | `FirmsCsvParser` | class | CSV → `List<FireDetection>` | TODO | Parse by header name |
| Data access | `WindDataAccessObject` | class | Wind direction | TODO | Forecast or historical wind (own class, not Lucas's) |
| View | `view/FireMapView` | JPanel | Controls, map, summary | Partial | Pass data to `MapPanel`, `SwingWorker` |
| View | `view/MapPanel` | JPanel | Draws picture + markers | TODO | Background picture, community, fire dots, wind arrow |
| Test | `src/test/.../NearbyFiresInteractorTest` | test | 3 disabled placeholders | TODO | Write fakes; enable tests |
| Test | `src/test/java/entity/risk/WildfireRiskModelTest` | test | — | New | One test per rule |
| Test | `src/test/java/data_access/FirmsCsvParserTest` | test | — | New | Saved sample CSV |

**Setup:** get a free FIRMS key and put it in your own `config.properties` (never commit it).
**Design to present:** Single Responsibility (fetch / parse / score / present / draw are separate classes).

---

## Youssef — Story 4: Compare satellite pictures

Package names: `compare_imagery`

| Layer | Class | Kind | What it does | Status | Your job |
|---|---|---|---|---|---|
| Entity | `entity/ImageType` | enum | Natural colour, false colour, flood map | Done | — |
| Entity | `entity/SatelliteImage` | class | Picture as raw pixels (no Swing) | Partial | `affectedFraction`, `unusableFraction` |
| Entity | `entity/risk/LandClassifier` | interface | **Strategy**: is a pixel affected / unusable? | Done | — |
| Entity | `entity/risk/WaterClassifier` | class | Finds flood water | TODO | Match flood-map legend colours |
| Entity | `entity/risk/BurnScarClassifier` | class | Finds burned land | TODO | Red-brown in false colour; clouds unusable |
| Use case | `use_case/compare_imagery/CompareImageryInputData` | record | Community, hazard, two dates | Done | — |
| Use case | `CompareImageryInputBoundary` | interface | `execute(input)` | Done | — |
| Use case | `CompareImageryInteractor` | class | The story's logic | TODO | Fetch both pictures, fractions, cloud check |
| Use case | `CompareImageryOutputData` | record | Pictures, fractions, reliability | Done | — |
| Use case | `CompareImageryOutputBoundary` | interface | Success / fail views | Done | — |
| Use case | `CompareImageryDataAccessInterface` | interface | `getImage(area, type, date, w, h)` | Done | — |
| Adapter | `interface_adapter/compare_imagery/CompareImageryController` | class | Compare button → interactor | Done | — |
| Adapter | `CompareImageryPresenter` | class | Output → State | TODO | Pixels, captions ("14% water"), change summary |
| Adapter | `CompareImageryState` | class | What the screen shows | Done | — |
| Data access | `data_access/GibsImageryDataAccessObject` | class | NASA GIBS pictures | TODO | Request PNG, decode with ImageIO, build `SatelliteImage` |
| Data access | `CachingImageryDataAccessObject` | class | **Decorator**: disk cache | Partial | Load from `~/.northwatch/cache/images/` or fetch + save |
| View | `view/ImageCompareView` | JPanel | Two pictures side by side | Partial | Pixels → pictures, date validation, `SwingWorker` |
| Test | `src/test/.../CompareImageryInteractorTest` | test | 3 disabled placeholders | TODO | Fake with tiny pixel arrays; enable tests |
| Test | classifier tests in `src/test/java/entity/risk/` | test | — | New | Known colours → affected / unusable |
| Test | imagery contract test | test | — | New | GIBS, cache and fake behave the same (Liskov) |

**Design to present:** Decorator (cache), Strategy (water vs burn classifier), Liskov (contract test).

---

## Order of work (same for everyone)

1. Entities → 2. Interactor + tests with fakes → 3. Presenter → 4. Real data access + parsing test → 5. View polish

Steps 1–3 need no API and no GUI, so all four members can start the same day.
