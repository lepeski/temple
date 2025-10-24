package com.templebuilder.geometry;

final class TempleMathTest {

    private TempleMathTest() {
    }

    static void run() {
        TempleDimensions dimensions = TempleMath.computeDimensions(1.0);
        assertTrue(dimensions.getBaseWidth() >= 150 && dimensions.getBaseWidth() <= 240, "Base width");
        assertTrue(dimensions.getTotalHeight() >= 60 && dimensions.getTotalHeight() <= 90, "Height range");
        assertEquals(6, dimensions.getTiers(), "Tier count");
        assertTrue(dimensions.getTierHeight() >= 3, "Tier height minimum");

        assertEquals(-12, TempleMath.mirrorCoordinate(12), "Mirror positive");
        assertEquals(0, TempleMath.mirrorCoordinate(0), "Mirror zero");
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("Assertion failed: " + message);
        }
    }

    private static void assertEquals(int expected, int actual, String message) {
        if (expected != actual) {
            throw new AssertionError(message + " expected=" + expected + " actual=" + actual);
        }
    }
}
