package com.templebuilder.geometry;

import com.templebuilder.TempleStyle;
import com.templebuilder.palette.Palette;
import org.bukkit.Material;

public class TempleBlueprintGenerator {

    public Blueprint generate(double scale, Palette palette, TempleStyle style, double lightDensity) {
        TempleDimensions dimensions = TempleMath.computeDimensions(scale);
        Blueprint blueprint = new Blueprint();
        SymmetryWriter writer = new SymmetryWriter(blueprint);

        buildTerraces(dimensions, palette, writer);
        carveGrandHall(dimensions, palette, blueprint);
        buildRamps(dimensions, palette, writer);
        buildGoldBands(dimensions, palette, writer);
        buildEye(dimensions, palette, writer, scale);
        buildEyeFrame(dimensions, palette, writer, scale);
        buildStatues(dimensions, palette, writer, scale);
        buildGlyphPanels(dimensions, palette, writer);
        buildLighting(dimensions, palette, writer, lightDensity);
        buildLanternSpires(dimensions, palette, writer);
        return blueprint;
    }

    private void buildTerraces(TempleDimensions d, Palette palette, SymmetryWriter writer) {
        int halfWidth = d.getBaseWidth() / 2;
        int halfDepth = d.getBaseDepth() / 2;
        for (int tier = 0; tier < d.getTiers(); tier++) {
            int yBase = tier * d.getTierHeight();
            int height = d.getTierHeight();
            int width = halfWidth - tier * d.getTierStep();
            int depth = halfDepth - (int) Math.round(tier * (d.getTierStep() * 0.85));
            Material wall = tier % 2 == 0 ? palette.secondary() : palette.accent();
            Material ceiling = tier % 2 == 0 ? palette.accent() : palette.secondary();
            for (int dy = 0; dy < height; dy++) {
                int y = yBase + dy;
                writer.fillHorizontal(0, width, y, 0, depth, wall);
            }
            int roofY = yBase + height;
            writer.fillHorizontal(0, width, roofY, 0, depth, ceiling);
            // recessed balconies every other tier
            if (tier > 0) {
                int recess = Math.max(2, width / 5);
                int recessDepth = Math.max(3, depth / 6);
                writer.fillHorizontal(0, recess, yBase + height - 1, depth - recessDepth, depth, palette.trim());
            }
        }
    }

    private void carveGrandHall(TempleDimensions d, Palette palette, Blueprint blueprint) {
        int hallDepth = (int) Math.round(d.getBaseDepth() * 0.35);
        int hallWidth = (int) Math.round(d.getBaseWidth() * 0.35);
        int hallHeight = (int) Math.round(d.getTotalHeight() * 0.55);
        for (int x = -hallWidth / 2; x <= hallWidth / 2; x++) {
            for (int z = -hallDepth; z <= 0; z++) {
                for (int y = d.getTierHeight(); y < hallHeight; y++) {
                    blueprint.set(new BlockVector(x, y, z), Material.AIR);
                }
            }
        }
        // reflective floor with gold river
        for (int x = -hallWidth / 2; x <= hallWidth / 2; x++) {
            for (int z = -hallDepth; z <= 0; z++) {
                blueprint.set(new BlockVector(x, d.getTierHeight(), z), palette.trim());
                if (Math.abs(x) < hallWidth / 8) {
                    blueprint.set(new BlockVector(x, d.getTierHeight(), z), Material.LIGHT_BLUE_STAINED_GLASS);
                }
            }
        }
        // Pillars grid
        int columnSpacing = Math.max(6, hallWidth / 6);
        for (int x = -hallWidth / 2; x <= hallWidth / 2; x += columnSpacing) {
            for (int z = -hallDepth + columnSpacing; z <= -columnSpacing; z += columnSpacing) {
                buildPillar(blueprint, x, d.getTierHeight(), z, hallHeight, palette);
            }
        }
    }

    private void buildPillar(Blueprint blueprint, int x, int baseY, int z, int hallHeight, Palette palette) {
        for (int y = baseY; y <= hallHeight; y++) {
            Material mat = y % 6 == 0 ? palette.gold() : palette.primary();
            blueprint.set(new BlockVector(x, y, z), mat);
            blueprint.set(new BlockVector(x + 1, y, z), mat);
            blueprint.set(new BlockVector(x, y, z + 1), mat);
            blueprint.set(new BlockVector(x + 1, y, z + 1), mat);
        }
    }

    private void buildRamps(TempleDimensions d, Palette palette, SymmetryWriter writer) {
        int rampWidth = Math.max(6, d.getBaseWidth() / 18);
        int halfDepth = d.getBaseDepth() / 2;
        int maxY = d.getTotalHeight();
        for (int dz = 0; dz <= halfDepth; dz++) {
            int y = Math.min(maxY - 2, dz / 3);
            for (int x = 0; x <= rampWidth; x++) {
                writer.set(x, y, dz, palette.trim());
                if (dz % 4 == 0) {
                    writer.set(x, y + 1, dz, palette.trim());
                }
                if (x == 0 || x == rampWidth) {
                    writer.set(x, y + 2, dz, palette.gold());
                }
            }
        }

        // side ramps recessed along z
        int sideOffset = Math.max(10, d.getBaseWidth() / 4);
        for (int dz = 0; dz <= halfDepth; dz += 2) {
            int y = Math.min(maxY - 5, dz / 4 + d.getTierHeight());
            writer.fillHorizontal(sideOffset, sideOffset + 2, y, dz, Math.min(dz + 4, halfDepth), palette.accent());
            writer.fillHorizontal(sideOffset, sideOffset + 2, y - 1, dz, Math.min(dz + 4, halfDepth), palette.primary());
        }
    }

    private void buildGoldBands(TempleDimensions d, Palette palette, SymmetryWriter writer) {
        int halfWidth = d.getBaseWidth() / 2;
        int halfDepth = d.getBaseDepth() / 2;
        for (int tier = 0; tier < d.getTiers(); tier++) {
            int topY = (tier + 1) * d.getTierHeight();
            int width = halfWidth - tier * d.getTierStep();
            int depth = halfDepth - (int) Math.round(tier * (d.getTierStep() * 0.85));
            for (int x = 0; x <= width; x++) {
                writer.set(x, topY, depth, palette.gold());
                writer.set(x, topY, 0, palette.gold());
                writer.set(x, topY + 1, depth, palette.accent());
            }
            for (int z = 0; z <= depth; z++) {
                writer.set(width, topY, z, palette.gold());
                writer.set(0, topY, z, palette.gold());
            }
            // Gold fascia down the slope
            for (int h = 0; h <= d.getTierHeight(); h++) {
                writer.set(width, topY - h, depth - h, palette.gold());
            }
        }
    }

    private void buildEye(TempleDimensions d, Palette palette, SymmetryWriter writer, double scale) {
        int frontZ = d.getBaseDepth() / 2;
        int centerY = d.getTotalHeight() - d.getTierHeight() / 2;
        int eyeWidth = (int) Math.round(32 * scale);
        int eyeHeight = (int) Math.round(eyeWidth * 0.45);
        int pupilRadius = Math.max(2, eyeWidth / 8);
        int irisRadius = pupilRadius + 2;

        for (int x = 0; x <= eyeWidth / 2; x++) {
            for (int y = -eyeHeight; y <= eyeHeight; y++) {
                double nx = x / (eyeWidth / 2.0);
                double ny = y / (eyeHeight / 2.0);
                double dist = nx * nx + ny * ny;
                if (dist <= 1.0) {
                    Material mat;
                    double radius = Math.sqrt(nx * nx + ny * ny);
                    if (radius < pupilRadius / (eyeWidth / 2.0)) {
                        mat = Material.BLACK_CONCRETE;
                    } else if (radius < irisRadius / (eyeWidth / 2.0)) {
                        mat = palette.eyeIris();
                    } else if (Math.abs(1.0 - dist) < 0.08) {
                        mat = palette.gold();
                    } else {
                        mat = palette.trim();
                    }
                    writer.set(x, centerY + y, frontZ, mat);
                    writer.set(x, centerY + y, frontZ - 1, mat);
                }
            }
        }
    }

    private void buildEyeFrame(TempleDimensions d, Palette palette, SymmetryWriter writer, double scale) {
        int frontZ = d.getBaseDepth() / 2 + 1;
        int centerY = d.getTotalHeight() - d.getTierHeight() / 2;
        int frameWidth = (int) Math.round(40 * scale);
        int frameHeight = (int) Math.round(30 * scale);
        for (int level = 0; level <= frameHeight; level++) {
            int span = frameWidth - level;
            if (span <= 0) {
                break;
            }
            writer.setLineX(0, span, centerY + level, frontZ + level / 4, palette.accent());
        }
        // Winged sun disc impression
        int wingSpan = (int) Math.round(50 * scale);
        int wingY = centerY + frameHeight / 2;
        for (int offset = 0; offset <= wingSpan / 2; offset++) {
            int y = wingY + offset / 6;
            writer.set(offset, y, frontZ - offset / 3, palette.gold());
            if (offset % 3 == 0) {
                writer.set(offset, y + 1, frontZ - offset / 3, palette.light());
            }
        }
    }

    private void buildStatues(TempleDimensions d, Palette palette, SymmetryWriter writer, double scale) {
        int frontZ = d.getBaseDepth() / 2;
        int height = (int) Math.round(38 * scale);
        int baseY = d.getTierHeight();
        int seatWidth = Math.max(6, d.getBaseWidth() / 14);
        int offsetX = d.getBaseWidth() / 2 - seatWidth - d.getTierStep();
        buildStatue(writer, palette, offsetX, baseY, frontZ + 2, height, seatWidth);
    }

    private void buildStatue(SymmetryWriter writer, Palette palette, int anchorX, int baseY, int frontZ, int height, int seatWidth) {
        int depth = Math.max(6, seatWidth / 2);
        // Throne base
        for (int y = 0; y < depth / 2; y++) {
            writer.fillHorizontal(anchorX - seatWidth / 2, anchorX, baseY + y, frontZ - depth + y, frontZ - depth / 2 + y, palette.primary());
        }
        // Legs and torso
        int legHeight = height / 3;
        for (int y = 0; y < legHeight; y++) {
            writer.fillHorizontal(anchorX - seatWidth / 3, anchorX - 1, baseY + y + depth / 2, frontZ - depth / 2, frontZ - depth / 4, palette.statue());
            writer.fillHorizontal(anchorX - 1, anchorX, baseY + y + depth / 2, frontZ - depth / 2, frontZ - depth / 4, palette.statue());
        }
        int torsoHeight = height / 2;
        for (int y = 0; y < torsoHeight; y++) {
            int currentY = baseY + depth / 2 + legHeight + y;
            int inset = y / 6;
            writer.fillHorizontal(anchorX - seatWidth / 2 + inset, anchorX, currentY, frontZ - depth / 2, frontZ - depth / 6, palette.statue());
            if (y % 4 == 0) {
                writer.fillHorizontal(anchorX - seatWidth / 2 + inset, anchorX, currentY, frontZ - depth / 2, frontZ - depth / 6, palette.gold());
            }
        }
        // Arms holding staff
        int armY = baseY + depth / 2 + legHeight + torsoHeight / 2;
        for (int y = 0; y < torsoHeight / 3; y++) {
            writer.fillHorizontal(anchorX - seatWidth, anchorX - seatWidth / 2, armY + y, frontZ - depth / 2, frontZ - depth / 3, palette.statue());
            writer.fillHorizontal(anchorX - seatWidth / 2, anchorX - seatWidth / 2 + 1, armY + y, frontZ - depth / 3, frontZ - depth / 3 + 1, palette.gold());
        }
        // Head and crown
        int headBase = baseY + depth / 2 + legHeight + torsoHeight;
        for (int y = 0; y < height / 6; y++) {
            writer.fillHorizontal(anchorX - seatWidth / 3, anchorX - seatWidth / 6, headBase + y, frontZ - depth / 3, frontZ - depth / 6, palette.statue());
        }
        for (int y = 0; y < height / 8; y++) {
            writer.fillHorizontal(anchorX - seatWidth / 3, anchorX - seatWidth / 6, headBase + height / 6 + y, frontZ - depth / 3 - y / 2, frontZ - depth / 6 + y / 3, palette.gold());
        }
    }

    private void buildGlyphPanels(TempleDimensions d, Palette palette, SymmetryWriter writer) {
        int halfWidth = d.getBaseWidth() / 2;
        int halfDepth = d.getBaseDepth() / 2;
        for (int tier = 0; tier < d.getTiers(); tier++) {
            int y = (tier + 1) * d.getTierHeight() - 2;
            int width = halfWidth - tier * d.getTierStep();
            int depth = halfDepth - (int) Math.round(tier * (d.getTierStep() * 0.85));
            for (int segment = 2; segment < width; segment += 6) {
                writer.set(segment, y, depth - 2, palette.glyph());
                writer.set(segment, y - 1, depth - 2, palette.light());
                writer.set(segment, y, depth / 2, palette.glyph());
                writer.set(segment, y - 2, depth / 2, palette.trim());
            }
        }
    }

    private void buildLighting(TempleDimensions d, Palette palette, SymmetryWriter writer, double lightDensity) {
        int halfWidth = d.getBaseWidth() / 2;
        int halfDepth = d.getBaseDepth() / 2;
        int interval = Math.max(3, (int) Math.round(8 - lightDensity * 6));
        for (int z = 0; z <= halfDepth; z += interval) {
            writer.set(0, 1, z, palette.light());
            writer.set(halfWidth / 2, d.getTierHeight() / 2, z, palette.light());
        }
        for (int x = 0; x <= halfWidth; x += interval) {
            writer.set(x, 1, halfDepth, palette.light());
        }
    }

    private void buildLanternSpires(TempleDimensions d, Palette palette, SymmetryWriter writer) {
        int halfWidth = d.getBaseWidth() / 2;
        int halfDepth = d.getBaseDepth() / 2;
        int roof = d.getTotalHeight();
        for (int tier = 0; tier < d.getTiers(); tier++) {
            int width = halfWidth - tier * d.getTierStep();
            int depth = halfDepth - (int) Math.round(tier * (d.getTierStep() * 0.85));
            int y = (tier + 1) * d.getTierHeight();
            writer.set(width - 2, y + 2, depth - 2, palette.light());
            writer.set(width - 2, y + 1, depth - 2, palette.gold());
            writer.set(width - 1, y + 3, depth - 3, palette.trim());
        }
        writer.set(0, roof + 2, halfDepth - 4, palette.light());
        writer.set(0, roof + 1, halfDepth - 4, palette.gold());
        writer.set(0, roof, halfDepth - 4, palette.trim());
    }
}
