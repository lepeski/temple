package com.templebuilder.palette;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

public class Palette {
    private final Material primary;
    private final Material secondary;
    private final Material accent;
    private final Material trim;
    private final Material gold;
    private final Material light;
    private final Material glyph;
    private final Material statue;
    private final Material eyeIris;

    public Palette(Material primary, Material secondary, Material accent, Material trim, Material gold,
                   Material light, Material glyph, Material statue, Material eyeIris) {
        this.primary = primary;
        this.secondary = secondary;
        this.accent = accent;
        this.trim = trim;
        this.gold = gold;
        this.light = light;
        this.glyph = glyph;
        this.statue = statue;
        this.eyeIris = eyeIris;
    }

    public Material primary() {
        return primary;
    }

    public Material secondary() {
        return secondary;
    }

    public Material accent() {
        return accent;
    }

    public Material trim() {
        return trim;
    }

    public Material gold() {
        return gold;
    }

    public Material light() {
        return light;
    }

    public Material glyph() {
        return glyph;
    }

    public Material statue() {
        return statue;
    }

    public Material eyeIris() {
        return eyeIris;
    }

    public BlockData blockData(Material material) {
        return Bukkit.createBlockData(material);
    }
}
