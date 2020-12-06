package net.mysticcloud.spigot.survival.utils;

import org.bukkit.ChatColor;

import net.mysticcloud.spigot.core.utils.CoreUtils;
import net.mysticcloud.spigot.survival.utils.inventories.SubSkill;

public enum Spells {
	
	FIREBALL("&d&lFireball"),
	FLAME("&6&lFlame"),
	HEAL("&a&lHealing"),
	INVISIBILITY("&9&lInvisibility"),
	LIGHTNING("&7&o&lLightning"),
	TELEPORT("&d&lTeleportation");
	
	String cname;
	
	Spells(String cname){
		this.cname = CoreUtils.colorize(cname);
		
	}

	SubSkill fromName(String spell) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getColorizedName() {
		return cname;
	}
	
	public String getStrippedName() {
		return ChatColor.stripColor(cname);
	}
	

}
