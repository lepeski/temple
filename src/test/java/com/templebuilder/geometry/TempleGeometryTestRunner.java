package com.templebuilder.geometry;

public final class TempleGeometryTestRunner {

    private TempleGeometryTestRunner() {
    }

    public static void main(String[] args) {
        SymmetryWriterTest.run();
        TempleMathTest.run();
        TempleBlueprintGeneratorTest.run();
        System.out.println("Temple geometry tests passed");
    }
}
