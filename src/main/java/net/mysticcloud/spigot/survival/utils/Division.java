package net.mysticcloud.spigot.survival.utils;

import org.bukkit.Material;

import net.mysticcloud.spigot.core.utils.CoreUtils;

public enum Division {

	ARCHER("&a&lArcher", Material.BOW), MAGE("&b&lMage", Material.STICK), WARRIOR("&7&lWarrior", Material.IRON_SWORD);

	Material guiItem;
	String displayName;

	Division(String displayName, Material guiItem) {
		this.guiItem = guiItem;
		this.displayName = CoreUtils.colorize(displayName);
	}

	public Material getGUIItem() {
		return guiItem;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getPerkPrefix() {
		switch (this) {
		case ARCHER:
			return "Archery";
		case MAGE:
		case WARRIOR:
			return name();
		default:
			return "";
		}
	}

}
