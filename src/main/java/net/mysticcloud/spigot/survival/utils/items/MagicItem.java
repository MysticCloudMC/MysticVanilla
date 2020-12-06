package net.mysticcloud.spigot.survival.utils.items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.mysticcloud.spigot.core.utils.CoreUtils;
import net.mysticcloud.spigot.survival.utils.Enhancement;
import net.mysticcloud.spigot.survival.utils.Spell;
import net.mysticcloud.spigot.survival.utils.Spells;
import net.mysticcloud.spigot.survival.utils.Tier;
import net.mysticcloud.spigot.survival.utils.spells.FireballSpell;
import net.mysticcloud.spigot.survival.utils.spells.FlameSpell;
import net.mysticcloud.spigot.survival.utils.spells.HealSpell;
import net.mysticcloud.spigot.survival.utils.spells.InvisibilitySpell;
import net.mysticcloud.spigot.survival.utils.spells.LightningSpell;
import net.mysticcloud.spigot.survival.utils.spells.TeleportSpell;

public class MagicItem extends Item {

	public static final int SPELL = 0;
	public static final int BOOK = 1;

	int type;
	Tier tier;
	List<Spell> spells = new ArrayList<Spell>();

	public MagicItem(int type, int level) {
		switch (type) {
		case SPELL:
			item = new ItemStack(Material.PAPER);
			generateSpellInfo(level);
			break;
		case BOOK:
			item = new ItemStack(Material.BOOK);
			generateBookInfo(level);
			break;
		default:
			break;
		}
	}

	private void generateBookInfo(int level) {
		setName("&a&lEnhancement Book");
		List<Enhancement> enhancements = new ArrayList<>();
		for (Enhancement en : Enhancement.values()) {
			enhancements.add(en);
		}
		Collections.shuffle(enhancements);
		for (Enhancement en : enhancements) {
			if (CoreUtils.getRandom().nextDouble() < en.getChance() && level >= en.getMinLevel()) {
				enhance(en, en.randomizePower(level));
			}
		}
	}

	private void generateSpellInfo(int level) {
		setName("&7Spell Paper");
		int spell = new Random().nextInt(5);
		ItemMeta meta = item.getItemMeta();
		String sname = "";
		switch(spell) {
		case 0:
			sname = Spells.LIGHTNING.getColorizedName();
			break;
		case 1:
			sname = Spells.FLAME.getColorizedName();
			break;
		case 2:
			sname = Spells.HEAL.getColorizedName();
			break;
		case 3:
			sname = Spells.FIREBALL.getColorizedName();
			break;
		case 4:
			sname = Spells.INVISIBILITY.getColorizedName();
			break;
		case 5:
			sname = Spells.TELEPORT.getColorizedName();
			break;
		default: break;
		}
		List<String> lore = new ArrayList<>();
		lore.add(CoreUtils.colorize(sname + "&7 Spell"));
		meta.setLore(lore);
		item.setItemMeta(meta);
	}

	public MagicItem(int type, ItemStack item) {
		super(item);
		this.item = item;
		if (type == SPELL) {
			if (!item.hasItemMeta()) {
				generateSpellInfo(1);
				return;
			}

			if (!item.getItemMeta().hasLore()) {
				generateSpellInfo(1);
				return;
			}
			if (!item.hasItemMeta()) {
				generateSpellInfo(1);
				return;
			}
			setName(item.getItemMeta().getDisplayName());
			String a = "";
			for (String s : item.getItemMeta().getLore()) {
				a = ChatColor.stripColor(s);
				Spell spell = null;
				if (a.equalsIgnoreCase(Spells.FLAME.getStrippedName() + " Spell")) {
					spell = new FlameSpell(null);
				}
				if (a.equalsIgnoreCase(Spells.TELEPORT.getStrippedName() + " Spell")) {
					spell = new TeleportSpell(null, null);
				}
				if (a.equalsIgnoreCase(Spells.HEAL.getStrippedName() + " Spell")) {
					spell = new HealSpell(null);
				}
				if (a.equalsIgnoreCase(Spells.FIREBALL.getStrippedName() + " Spell")) {
					spell = new FireballSpell(null);
				}
				if (a.equalsIgnoreCase(Spells.INVISIBILITY.getStrippedName() + " Spell")) {
					spell = new InvisibilitySpell(null);
				}
				if (a.equalsIgnoreCase(Spells.LIGHTNING.getStrippedName() + " Spell")) {
					spell = new LightningSpell(null);
				}

				if (spell != null) {
					spells.add(spell);
				}
			}

		}
		if (type == BOOK) {
			if (!item.hasItemMeta()) {
				generateBookInfo(1);
				return;
			}

			if (!item.getItemMeta().hasLore()) {
				generateBookInfo(1);
				return;
			}
			if (!item.hasItemMeta()) {
				generateBookInfo(1);
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

	}

}
