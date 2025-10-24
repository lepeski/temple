# TempleBuilder

TempleBuilder is a Paper 1.21.10 plugin that procedurally constructs an enormous, cinematic pyramid-temple inspired by Ancient Egyptian megastructures. The generated build features seven stepped terraces, thick gold inlays, recessed ramps, an immense glowing Eye of Judgement, glyph relief panels, balcony bands, and a pair of colossal Pharaoh statues that flank the monumental entrance. The structure is generated deterministically by building one quadrant and mirroring it across both axes to guarantee perfect symmetry and clean motifs.

## Commands

| Command | Description |
|---------|-------------|
| `/temple build <x> <y> <z> [scale] [palette]` | Generates the temple with its front centre anchored at the supplied coordinates. `scale` defaults to `1.0` and controls the footprint (150–240 blocks wide at scale `1.0`). `palette` accepts `default` (blackstone + gold) or `alt` (obsidian/basalt with copper trims). |
| `/temple undo` | Restores the previous build by replaying saved block snapshots. |
| `/temple schem <name>` | Serialises the most recent build plan into `plugins/TempleBuilder/schematics/<name>.yml` for reuse. |

## Building Philosophy

* **Symmetry first** – geometry is authored for one quadrant and mirrored on the X/Z axes so the pyramid remains perfectly balanced.
* **Tiered megatomb** – six to seven tiers taper evenly, each crowned with a gold fascia and recessed balcony band.
* **Dramatic focal point** – the front facade carries a 30-block wide Eye of Judgement with gold lashes and a glowing sea-lantern iris.
* **Heroic guardians** – two 40-block statues sit on thrones beside the entrance, sculpted from deepslate, blackstone, and gilded highlights.
* **Interior grandeur** – a reflective-floor hypostyle hall with light shafts and double-thick pillars is carved behind the gate.
* **Lighting control** – block placements are queued in batches per tick with configurable density for the internal glow channels.

## Development

TempleBuilder ships with a Gradle build that targets Java 21. Lightweight Bukkit API stubs are included so the project can compile and run its geometry tests in isolation; when wiring the plugin into a real Paper server, swap the stubs for the official Paper API dependency (for example `io.papermc.paper:paper-api:1.21.3-R0.1-SNAPSHOT`). Unit tests exercise the deterministic geometry routines for mirroring, tier progression, statue proportions, and eye placement.

### Build & Test

```bash
gradle build
gradle test
```

The resulting plugin jar (found in `build/libs`) can be dropped into a Paper 1.21.10 server.
