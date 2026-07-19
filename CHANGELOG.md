# Changelog for EverCrops: Farmer's Delight (Forge 1.20.1)

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [2.0.0] - 2026-06-23

### Changed

- Now requires EverCrops 4.0.0 or newer.
- Rebuilt on EverCrops 4.0.0's new shared add-on framework. Everything works exactly as before — no gameplay changes.

## [1.1.4] - 2026-06-20

### Changed

- Now requires EverCrops 3.5.3 or newer.

### Fixed

- Fixed the same right-click harvesting issue (e.g. Harvest With Ease) for Farmer's Delight crops — tomatoes, rice, budding tomato seedlings, and mushroom colonies. After being away, harvesting and replanting one of these no longer lets it instantly grow back from leftover catch-up growth.

## [1.1.3] - 2026-06-06

### Changed

- Version updated to stay in step with the NeoForge version of this add-on. No gameplay changes.

## [1.1.2] - 2026-06-03

### Changed

- Now requires EverCrops 3.5.1 or newer.

### Fixed

- Rice, tomatoes, budding tomato seedlings, and mushroom colonies could lose a little of their catch-up growth in the very instant they caught up after you'd been away. They now keep all of it.
- Made sure your Farmer's Delight crops keep being tracked alongside EverCrops 3.5.1's new automatic cleanup, so they're never accidentally forgotten.

## [1.1.0] - 2026-5-26

### Added

- Catch-up growth for **organic compost** — the block now advances through its composting stages (and converts to rich soil) based on how long the chunk was unloaded. The speed depends on nearby compost activators, water, and skylight, just like normal growth does.
- Catch-up growth for **mushroom colonies** — colonies now age through their four growth stages during time away, the same as other crops. Brown and red mushroom colonies are both supported.
- Catch-up conversion on **rich soil** — if a brown or red mushroom was sitting on rich soil when the area unloaded, the mushroom converts to a colony the next time the chunk loads (as it would have during normal play).

### Fixed

- Fixed a game freeze (server deadlock) that occurred when ropes were placed above tomato
  plants and the tomato climbed onto them. The game would lock up a short time after
  the climb started and had to be force-killed. Caused by Farmer's Delight 1.3.0's new
  rope-climbing tomato block inheriting code from the base tomato block without carrying
  over all of the same block properties.
- Fixed tomato catch-up silently not working at all since 1.0.0. Tomatoes were never
  registered when placed, so every tick was a no-op. Tomato catch-up now works correctly.
- Tomatoes climbing ropes now also catch up while the area is unloaded. Each rope segment
  the tomato has grown to will advance through its age stages just like a ground tomato does.

## [1.0.0] - 2026-5-10

### Added

- Catch-up growth for **tomatoes** (`TomatoBlock`) — AGE (VINE_AGE) 0–3, requires light level ≥ 9. Ground-planted tomatoes only; rope-logged variants are excluded as their multi-block vine mechanics are handled entirely by vanilla.
- Catch-up growth for **budding tomato seedlings** (`BuddingTomatoBlock`) — AGE 0–3 via `BuddingBushBlock`, requires light level ≥ 9. Catch-up advances the seedling to max age only; the transition to a full `TomatoBlock` via `growPastMaxAge()` is left to vanilla's own `randomTick` to keep multi-block placement logic in a clean context.
- Catch-up growth for **rice** (`RiceBlock`) — AGE 0–3, requires light level ≥ 9. Catch-up advances the base rice block; placement of `RicePaniclesBlock` above at max age is left to vanilla.
- Note: **cabbage**, **onion**, and **rice panicles** (`CabbageBlock`, `OnionBlock`, `RicePaniclesBlock`) extend `CropBlock` without overriding `randomTick` and are already covered by EverCrops's `CropBlockMixin` — no additional mixins are needed for these crops.
- Uses EverCrops's shared `CropRegistry` and `CropState` persistence (`evercrops.dat` per dimension). All EverCrops debug commands (`/evercrops inspect`, `/evercrops tick`, `/evercrops simulate`) work with Farmer's Delight crops automatically.
