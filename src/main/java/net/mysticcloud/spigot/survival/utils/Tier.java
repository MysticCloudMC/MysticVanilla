package net.mysticcloud.spigot.survival.utils;

public enum Tier {
	FIRST(0, 9), SECOND(10, 19), THIRD(20, 29), FOURTH(30, 39), FIFTH(40, 49), SIXTH(50, 59),
	SEVENTH(60, Integer.MAX_VALUE);

	int minLevel;
	int maxLevel;

	Tier(int minLevel, int maxLevel) {
		this.minLevel = minLevel;
		this.maxLevel = maxLevel;

	}

	public static Tier getTier(int level) {
		for (Tier t : values()) {
			if (t.minLevel <= level && t.maxLevel >= level)
				return t;
		}
		return FIRST;
	}
	
	public int getMaxLevel() {
		return maxLevel;
	}
	public int getMinimumLevel() {
		return minLevel;
	}

	public String getName() {
		switch (name()) {
		case "FIRST":
			return "&e&lPeasant";
		case "SECOND":
			return "&5&lNoble";
		case "THIRD":
			return "&2&lSoldier";
		case "FOURTH":
			return "&7&lKnight";
		case "FIFTH":
			return "&d&lWizard";
		case "SIXTH":
			return "&b&lDemi-God";
		case "SEVENTH":
			return "&c&lCelestial";
		default:
			return name();
		}
	}

}
