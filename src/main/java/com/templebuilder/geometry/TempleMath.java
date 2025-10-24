package com.templebuilder.geometry;

public final class TempleMath {

    private TempleMath() {
    }

    public static TempleDimensions computeDimensions(double scale) {
        double clamped = Math.max(0.6, Math.min(2.0, scale));
        int baseWidth = (int) Math.round(200 * clamped);
        baseWidth = ensureEven(baseWidth);
        int baseDepth = (int) Math.round(baseWidth * 1.05);
        baseDepth = ensureEven(baseDepth);
        int tiers = 6;
        int totalHeight = (int) Math.round(75 * clamped);
        int tierHeight = Math.max(3, totalHeight / tiers);
        int tierStep = Math.max(4, baseWidth / (tiers * 3));
        return new TempleDimensions(baseWidth, baseDepth, tierHeight * tiers, tiers, tierHeight, tierStep);
    }

    private static int ensureEven(int value) {
        return value % 2 == 0 ? value : value + 1;
    }

    public static int mirrorCoordinate(int coordinate) {
        return coordinate == 0 ? 0 : -coordinate;
    }
}
