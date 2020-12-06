package net.mysticcloud.spigot.survival.utils;

import net.mysticcloud.spigot.core.utils.CoreUtils;

public enum Enhancement {
	DISARM("&1Disarm&7 Chance: &1&l", 		0.3, 	Tier.FIRST.getMinimumLevel(), true),
	SPEED("&aSpeed&7 Modifier: &a&l",		0.3, 	Tier.FIRST.getMinimumLevel(), false),
	VAMPIRISM("&4Vampirism&7 Chance: &4&l", 0.1, 	Tier.FIFTH.getMinimumLevel(), true),
	DODGE("&eDodge&7 Chance: &e&l",	 		0.3, 	Tier.FIRST.getMinimumLevel(), true),
	FIRE("&cFire&7 Damage: &c&l", 			0.5, 	Tier.FIRST.getMinimumLevel(), true),
	FROST("&bFrost&7 Damage: &b&l", 		0.5, 	Tier.FIRST.getMinimumLevel(), true),
	POWER_ATTACK("&cPower Attack&7: &c&l",	0.01, 	Tier.FIRST.getMinimumLevel(), true),
	PROTECTION("&dArmor&7 Modifier: &d&l", 	0.4, 	Tier.FIRST.getMinimumLevel(), false);
	
	String name;
	boolean weapon;
	double chance;
	int level;
	
	Enhancement(String name, double chance, int level, boolean weapon){
		this.name = CoreUtils.colorize(name);
		this.weapon = weapon;
		this.chance = chance;
		this.level = level;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isWeapon() {
		return weapon;
	}

	public double getChance() {
		return chance;
	}
	
	public int getMinLevel() {
		return level;
	}

	public int randomizePower(int level) {
		switch (name()) {
		case "SPEED":
			return CoreUtils.getRandom().nextInt(4) + 1;
		default:
			return (int) ((level * CoreUtils.getRandom().nextDouble()) + CoreUtils.getRandom().nextInt(5));
		}
	}
	
	

}
