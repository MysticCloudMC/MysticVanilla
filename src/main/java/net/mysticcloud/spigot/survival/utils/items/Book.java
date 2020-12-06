package net.mysticcloud.spigot.survival.utils.items;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import net.mysticcloud.spigot.core.utils.CoreUtils;
import net.mysticcloud.spigot.survival.utils.Enhancement;
import net.mysticcloud.spigot.survival.utils.Tier;

public class Book extends Item {

	Tier tier;

	public Book(int level) {
		tier = Tier.getTier(level);
		item = new ItemStack(Material.BOOK);
		generateInfo(Material.BOOK, level);
	}

	public Book(ItemStack item) {
		this.item = item;
		if (!item.hasItemMeta()) {
			generateInfo(item.getType(), 1);
			return;
		}

		if (!item.getItemMeta().hasLore()) {
			generateInfo(item.getType(), 1);
			return;
		}
		if (!item.hasItemMeta()) {
			generateInfo(item.getType(), 1);
			return;
		}
		setName(item.getItemMeta().getDisplayName());
		String a = "";
		for (String s : item.getItemMeta().getLore()) {
			a = ChatColor.stripColor(s);
			if (a.contains(":")) {
				if (a.contains("Disarm Chance:"))
					enhancements.put(Enhancement.DISARM, Integer.parseInt(a.split(": ")[1]));
				if (a.contains("Fire Damage:"))
					enhancements.put(Enhancement.FIRE, Integer.parseInt(a.split(": ")[1]));
				if (a.contains("Frost Damage:"))
					enhancements.put(Enhancement.FROST, Integer.parseInt(a.split(": ")[1]));
				if (a.contains("Dodge Chance:"))
					enhancements.put(Enhancement.DODGE, Integer.parseInt(a.split(": ")[1]));
				if (a.contains("Vampirism Chance:"))
					enhancements.put(Enhancement.VAMPIRISM, Integer.parseInt(a.split(": ")[1]));
				if (a.contains("Speed Modifier:"))
					enhancements.put(Enhancement.SPEED, Integer.parseInt(a.split(": ")[1]));
				if (a.contains("Armor Modifier:"))
					enhancements.put(Enhancement.PROTECTION, Integer.parseInt(a.split(": ")[1]));

				if (a.contains("Tier:")) {
					for (Tier tier : Tier.values()) {
						if (ChatColor.stripColor(tier.getName()).equals(a.split(": ")[1])) {
							this.tier = tier;
						}
					}
				}
				if (tier == null) {
					tier = Tier.FIRST;
				}
			}
		}
		finalizeEnhancements();

	}

	@Override
	public void generateInfo(Material type, int level) {
		setName("&a&lEnhancement Book");
		List<Enhancement> enhancements = new ArrayList<>();
		for (Enhancement en : Enhancement.values()) {
			enhancements.add(en);
		}
		Collections.shuffle(enhancements);
		for (Enhancement en : enhancements) {
			if(CoreUtils.getRandom().nextDouble() < en.getChance() && level >= en.getMinLevel()) {
				enhance(en, en.randomizePower(level));
			}
		}
	}
}
