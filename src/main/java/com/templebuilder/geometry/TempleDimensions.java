package com.templebuilder.geometry;

public class TempleDimensions {
    private final int baseWidth;
    private final int baseDepth;
    private final int totalHeight;
    private final int tiers;
    private final int tierHeight;
    private final int tierStep;

    public TempleDimensions(int baseWidth, int baseDepth, int totalHeight, int tiers, int tierHeight, int tierStep) {
        this.baseWidth = baseWidth;
        this.baseDepth = baseDepth;
        this.totalHeight = totalHeight;
        this.tiers = tiers;
        this.tierHeight = tierHeight;
        this.tierStep = tierStep;
    }

    public int getBaseWidth() {
        return baseWidth;
    }

    public int getBaseDepth() {
        return baseDepth;
    }

    public int getTotalHeight() {
        return totalHeight;
    }

    public int getTiers() {
        return tiers;
    }

    public int getTierHeight() {
        return tierHeight;
    }

    public int getTierStep() {
        return tierStep;
    }
}
