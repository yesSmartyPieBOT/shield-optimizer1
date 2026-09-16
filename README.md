# Novex Client

Novex is a single client-side Fabric mod for Minecraft 1.21.11 that groups PvP/QoL visual features into one configurable client instead of requiring a separate jar for every feature.

## Built-in feature plan

1. Shield Optimizer — client-side responsiveness work; it cannot remove real server/network latency.
2. Fire Overlay — adjustable visibility/opacity and disable option.
3. Totem Pop — compact configurable visual animation and duration.
4. In-game Config GUI — one place to toggle and configure features.
5. Performance Optimizer — client-side rendering/performance options.
6. No Fog — dimension-aware fog controls.
7. Armor HUD — movable, scalable armor/durability display.
8. Inventory HUD — movable, scalable inventory display.
9. Scoreboard HUD — movable/scalable scoreboard visibility and keybind.
10. Full Brightness — configurable brightness toggle/keybind.
11. Effect HUD — movable/scalable active-effect display.

## Current build

The repository now contains the Fabric 1.21.11 project foundation, persistent configuration, keybinds, the first config screen, HUD rendering foundation, and a GitHub Actions build pipeline.

The feature implementations are being built as separate internal modules so each can be independently enabled or disabled.

## Build

Use Java 21 and run:

```text
gradle build
```

The GitHub Actions workflow also builds the project and uploads the generated jars as an artifact.

## License

MIT
