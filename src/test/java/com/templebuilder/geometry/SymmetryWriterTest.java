package com.templebuilder.geometry;

import org.bukkit.Material;

final class SymmetryWriterTest {

    private SymmetryWriterTest() {
    }

    static void run() {
        Blueprint blueprint = new Blueprint();
        SymmetryWriter writer = new SymmetryWriter(blueprint);
        writer.set(5, 10, 7, Material.GOLD_BLOCK);

        assertEquals(Material.GOLD_BLOCK, blueprint.get(new BlockVector(5, 10, 7)));
        assertEquals(Material.GOLD_BLOCK, blueprint.get(new BlockVector(-5, 10, 7)));
        assertEquals(Material.GOLD_BLOCK, blueprint.get(new BlockVector(5, 10, -7)));
        assertEquals(Material.GOLD_BLOCK, blueprint.get(new BlockVector(-5, 10, -7)));
    }

    private static void assertEquals(Object expected, Object actual) {
        if ((expected == null && actual != null) || (expected != null && !expected.equals(actual))) {
            throw new AssertionError("Expected " + expected + " but got " + actual);
        }
    }
}
