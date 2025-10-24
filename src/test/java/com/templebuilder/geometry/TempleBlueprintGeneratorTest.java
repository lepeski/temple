package com.templebuilder.geometry;

import com.templebuilder.TempleStyle;
import com.templebuilder.palette.Palette;
import com.templebuilder.palette.PaletteRegistry;
import java.util.IntSummaryStatistics;
import java.util.Map;
import java.util.stream.Collectors;

final class TempleBlueprintGeneratorTest {

    private TempleBlueprintGeneratorTest() {
    }

    static void run() {
        Palette palette = PaletteRegistry.get("default");
        TempleBlueprintGenerator generator = new TempleBlueprintGenerator();
        Blueprint blueprint = generator.generate(1.0, palette, TempleStyle.GRAND, 0.6);

        // Eye placement assertions
        int maxZ = blueprint.maxZ();
        Map<Integer, Long> countsByZ = blueprint.entries().stream()
                .filter(e -> e.getValue() == palette.eyeIris())
                .collect(Collectors.groupingBy(e -> e.getKey().getZ(), Collectors.counting()));
        int dominantZ = countsByZ.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(0);
        var pupilBlocks = blueprint.entries().stream()
                .filter(e -> e.getValue() == palette.eyeIris())
                .map(Map.Entry::getKey)
                .filter(v -> Math.abs(v.getZ() - dominantZ) <= 1)
                .filter(v -> v.getY() > blueprint.maxY() * 0.25)
                .collect(Collectors.toList());
        assertFalse(pupilBlocks.isEmpty(), "Pupil must exist");
        double avgX = pupilBlocks.stream().mapToInt(BlockVector::getX).average().orElse(0.0);
        int maxEyeY = pupilBlocks.stream().mapToInt(BlockVector::getY).max().orElse(0);
        assertNear(0.0, avgX, 2.0, "Eye should be centered on X-axis");
        assertTrue(maxEyeY > blueprint.maxY() * 0.40, "Eye crest should reach upper tiers");
        long centered = pupilBlocks.stream().filter(v -> Math.abs(v.getX()) <= 24).count();
        assertTrue(centered >= pupilBlocks.size() * 0.6, "Iris should remain concentrated around the axis");

        // Tier tapering
        TempleDimensions dimensions = TempleMath.computeDimensions(1.0);
        int totalTiers = dimensions.getTiers();
        int lastWidth = Integer.MAX_VALUE;
        for (int tier = 1; tier <= totalTiers; tier++) {
            int targetY = tier * dimensions.getTierHeight();
            IntSummaryStatistics stats = blueprint.entries().stream()
                    .filter(e -> e.getKey().getY() == targetY)
                    .mapToInt(e -> Math.abs(e.getKey().getX()))
                    .summaryStatistics();
            int width = stats.getMax();
            assertTrue(width < lastWidth, "Each tier should be narrower than the previous");
            lastWidth = width;
        }

        // Statue height
        int frontZ = blueprint.maxZ();
        int highest = blueprint.entries().stream()
                .filter(e -> e.getKey().getZ() >= frontZ - 6 && (e.getValue() == palette.statue() || e.getValue() == palette.gold()))
                .mapToInt(e -> e.getKey().getY())
                .max()
                .orElse(0);
        assertTrue(highest >= 30 && highest <= 55, "Statue silhouette height should be heroic");
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    private static void assertFalse(boolean condition, String message) {
        if (condition) {
            throw new AssertionError(message);
        }
    }

    private static void assertNear(double expected, double actual, double tolerance, String message) {
        if (Math.abs(expected - actual) > tolerance) {
            throw new AssertionError(message + " expected=" + expected + " actual=" + actual);
        }
    }
}
