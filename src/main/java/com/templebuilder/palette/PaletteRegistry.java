package com.templebuilder.palette;

import org.bukkit.Material;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class PaletteRegistry {

    private static final Map<String, Palette> REGISTRY = new HashMap<>();

    static {
        register("default", new Palette(
                Material.BLACK_CONCRETE,
                Material.BLACKSTONE_BRICKS,
                Material.POLISHED_BLACKSTONE_BRICKS,
                Material.DEEPSLATE_TILES,
                Material.GOLD_BLOCK,
                Material.SEA_LANTERN,
                Material.CHISELED_DEEPSLATE,
                Material.POLISHED_BLACKSTONE,
                Material.SEA_LANTERN
        ));

        register("alt", new Palette(
                Material.OBSIDIAN,
                Material.POLISHED_BASALT,
                Material.CUT_COPPER,
                Material.WAXED_EXPOSED_COPPER,
                Material.WAXED_WEATHERED_COPPER,
                Material.SHROOMLIGHT,
                Material.CHISELED_POLISHED_BLACKSTONE,
                Material.POLISHED_BASALT,
                Material.SHROOMLIGHT
        ));
    }

    private PaletteRegistry() {
    }

    public static Palette get(String key) {
        return REGISTRY.getOrDefault(key.toLowerCase(Locale.ROOT), REGISTRY.get("default"));
    }

    public static void register(String key, Palette palette) {
        REGISTRY.put(key.toLowerCase(Locale.ROOT), palette);
    }
}
