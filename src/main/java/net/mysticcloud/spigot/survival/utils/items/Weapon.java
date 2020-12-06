package net.mysticcloud.spigot.survival.utils.items;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.mysticcloud.spigot.core.utils.CoreUtils;
import net.mysticcloud.spigot.survival.utils.Enhancement;
import net.mysticcloud.spigot.survival.utils.Tier;

public class Weapon extends Item {

	Tier tier;

	int weight;
	public Weapon(Material material, int level) {
		tier = Tier.getTier(level);
		item = new ItemStack(material);
		generateInfo(material, level);
	}

	public Weapon(ItemStack item) {
		super(item);
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
				if (a.contains("Durability:")) {
					durability = Integer.parseInt(a.split(": ")[1].split("/")[0]);
					maxDurability = Integer.parseInt(a.split(": ")[1].split("/")[1]);
				}
				if (a.contains("Speed:"))
					speed = Integer.parseInt(a.split(": ")[1]);
				if (a.contains("Weight:"))
					weight = Integer.parseInt(a.split(": ")[1]);
				if (a.contains("Damage:"))
					damage = Integer.parseInt(a.split(": ")[1]);
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
	
	public int getWeight() {
		return weight;
	}

	@Override
	public void generateInfo(Material type, int level) {
		damage = (int) ((level * CoreUtils.getRandom().nextDouble()) + CoreUtils.getRandom().nextInt(5));
		speed = (int) ((level * CoreUtils.getRandom().nextDouble()) + CoreUtils.getRandom().nextInt(5));
		weight = (int) ((level * CoreUtils.getRandom().nextDouble()) + CoreUtils.getRandom().nextInt(5));
		maxDurability = (int) (((level * CoreUtils.getRandom().nextDouble()) + CoreUtils.getRandom().nextInt(5)) * 10.0);
		durability = maxDurability;
		setName(ItemUtils.getWeaponDescriptor(tier) + " " + ItemUtils.getWeaponType(item.getType()));
		ItemMeta a = item.getItemMeta();
		List<String> lore = a.hasLore() ? a.getLore() : new ArrayList<String>();

		
		AttributeModifier at = new AttributeModifier(UUID.randomUUID(), "Attack Damage", damage, Operation.ADD_NUMBER,
				EquipmentSlot.HAND);
		a.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, at);

		AttributeModifier sp = new AttributeModifier(UUID.randomUUID(), "Attack Speed", speed, Operation.ADD_NUMBER,
				EquipmentSlot.HAND);
		a.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, sp);

		lore.add(CoreUtils.colorize("&7Tier: " + tier.getName()));

		lore.add(CoreUtils.colorize("&7Damage: " + ((int) damage)));
		lore.add(CoreUtils.colorize("&7Weight: " + ((int) weight)));
		lore.add(CoreUtils.colorize("&7Speed: " + ((int) speed)));
		lore.add(ItemUtils.getDurabilityString(((int) durability), ((int) durability)));
		lore.add(CoreUtils.colorize("&7------------------"));
		a.setLore(lore);

		a.setDisplayName(CoreUtils.colorize("&f" + name));

		a.addItemFlags(ItemFlag.values());
		item.setItemMeta(a);

	}

}
