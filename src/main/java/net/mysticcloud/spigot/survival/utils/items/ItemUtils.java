package net.mysticcloud.spigot.survival.utils.items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.mysticcloud.spigot.core.utils.CoreUtils;
import net.mysticcloud.spigot.survival.utils.Enhancement;
import net.mysticcloud.spigot.survival.utils.Tier;

public class ItemUtils {

	static Map<Tier, Material[]> weaponTiers = new HashMap<>();
	static Map<Tier, String[]> weaponDescriptors = new HashMap<>();
	static List<Enhancement> weaponEnhancements = new ArrayList<>();
	static ItemStack[] foods = new ItemStack[] { new ItemStack(Material.BREAD) };
	static Map<Tier, Material[]> armorTiers = new HashMap<>();
	static Map<Tier, String[]> armorDescriptors = new HashMap<>();
	static List<Enhancement> armorEnhancements = new ArrayList<>();
	static String[] slowDescritors = new String[] {"Slow", "Sluggish"};
	static String[] heavyDescritors = new String[] {"Heavy"};
	static String[] fastDescritors = new String[] {"Fast", "Swift","Quick","Lightning"};
	static String[] lightDescritors = new String[] {"Light"};

	public static void start() {

		for (Enhancement en : Enhancement.values()) {
			if (en.isWeapon()) {
				weaponEnhancements.add(en);
			} else {
				armorEnhancements.add(en);
			}
		}

		weaponTiers.put(Tier.FIRST,
				new Material[] { Material.WOODEN_AXE, Material.WOODEN_SWORD, Material.BOW, Material.STONE_AXE });
		weaponDescriptors.put(Tier.FIRST, new String[] { "Dull", "Chipped", "Slow", "" });

		weaponTiers.put(Tier.SECOND, new Material[] { Material.STONE_AXE, Material.IRON_SWORD, Material.IRON_AXE,
				Material.GOLDEN_SWORD, Material.IRON_PICKAXE });
		weaponDescriptors.put(Tier.SECOND, new String[] { "Swift", "Shiny", "Dented" });

		weaponTiers.put(Tier.THIRD, new Material[] { Material.DIAMOND_AXE, Material.DIAMOND_SWORD, Material.CROSSBOW });
		weaponDescriptors.put(Tier.THIRD, new String[] { "Sharp", "Flashing", "Powerful" });

		weaponTiers.put(Tier.FOURTH, new Material[] { Material.DIAMOND_AXE, Material.DIAMOND_SWORD, Material.CROSSBOW,
				Material.TRIDENT, Material.DIAMOND_AXE, Material.IRON_SWORD });
		weaponDescriptors.put(Tier.FOURTH, new String[] { "Hellish", "Heavenly", "Xelphor's", "Satan's" });

		weaponTiers.put(Tier.FIFTH, weaponTiers.get(Tier.FOURTH));
		weaponDescriptors.put(Tier.FIFTH, weaponDescriptors.get(Tier.FOURTH));

		weaponTiers.put(Tier.SIXTH, weaponTiers.get(Tier.FOURTH));
		weaponDescriptors.put(Tier.SIXTH, weaponDescriptors.get(Tier.FOURTH));

		weaponTiers.put(Tier.SEVENTH, weaponTiers.get(Tier.FOURTH));
		weaponDescriptors.put(Tier.SEVENTH, weaponDescriptors.get(Tier.FOURTH));

		armorTiers.put(Tier.FIRST, new Material[] { Material.LEATHER_BOOTS, Material.LEATHER_CHESTPLATE,
				Material.LEATHER_HELMET, Material.LEATHER_LEGGINGS });
		armorDescriptors.put(Tier.FIRST, new String[] { "Ripped", "Torn", "Dirty", "" });

		armorTiers.put(Tier.SECOND, new Material[] { Material.CHAINMAIL_BOOTS, Material.IRON_CHESTPLATE,
				Material.CHAINMAIL_LEGGINGS, Material.CHAINMAIL_CHESTPLATE, Material.CHAINMAIL_HELMET });
		armorDescriptors.put(Tier.SECOND, new String[] { "Protective", "Shiny", "Dented" });

		armorTiers.put(Tier.THIRD, new Material[] { Material.DIAMOND_HELMET, Material.IRON_CHESTPLATE,
				Material.IRON_HELMET, Material.IRON_LEGGINGS, Material.IRON_BOOTS });
		armorDescriptors.put(Tier.THIRD, new String[] { "Strong", "Glistening", "Holy" });

		armorTiers.put(Tier.FOURTH, new Material[] { Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE,
				Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS, Material.NETHERITE_BOOTS });
		armorDescriptors.put(Tier.FOURTH, new String[] { "Hellish", "Heavenly", "Xelphor's", "Satan's" });

		armorTiers.put(Tier.FIFTH, armorTiers.get(Tier.FOURTH));
		armorDescriptors.put(Tier.FIFTH, armorDescriptors.get(Tier.FOURTH));

		armorTiers.put(Tier.SIXTH, armorTiers.get(Tier.FOURTH));
		armorDescriptors.put(Tier.SIXTH, armorDescriptors.get(Tier.FOURTH));

		armorTiers.put(Tier.SEVENTH, armorTiers.get(Tier.FOURTH));
		armorDescriptors.put(Tier.SEVENTH, armorDescriptors.get(Tier.FOURTH));

	}

	public static String getDurabilityString(int dur, int max) {
		double percent = (dur + 0.0) / (max + 0.0) * 100.0;
		String sdur = "";
		if (percent >= 66.66)
			sdur = "&a&l" + dur;
		if (percent < 66.66 && percent >= 33.33)
			sdur = "&e&l" + dur;
		if (percent < 33.33)
			sdur = "&c&l" + dur;

		return CoreUtils.colorize("&7Durability: " + sdur + "&7/" + max);
	}

	public static ItemStack soulbind(Player sender, ItemStack item) {
		ItemMeta a = item.getItemMeta();
		List<String> lore = a.hasLore() ? a.getLore() : new ArrayList<String>();
		lore.add(CoreUtils.colorize("&6&lSoulbound"));
		a.setLore(lore);
		item.setItemMeta(a);

		return item;
	}

	public static void removeSeeker(ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		List<String> lore = new ArrayList<>();
		int arrows = 0;
		if (meta.hasLore())
			for (String s : meta.getLore()) {
				if (ChatColor.stripColor(s).contains("Seeker Arrows:")) {
					arrows = Integer.parseInt(ChatColor.stripColor(s).split(": ")[1]);
					continue;
				}
				lore.add(s);
			}
		if ((arrows - 1 >= 1))
			lore.add(CoreUtils.colorize("&e&lSeeker Arrows&7: &e" + (arrows - 1)));
		meta.setLore(lore);
		item.setItemMeta(meta);
	}

	public static void addSeeker(ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		List<String> lore = new ArrayList<>();
		int arrows = 0;

		if (meta.hasLore())
			for (String s : meta.getLore()) {
				if (ChatColor.stripColor(s).contains("Seeker Arrows:")) {
					arrows = Integer.parseInt(ChatColor.stripColor(s).split(": ")[1]);
					continue;
				}
				lore.add(s);
			}
		lore.add(CoreUtils.colorize("&e&lSeeker Arrows&7: &e" + (arrows + 1)));
		meta.setLore(lore);
		item.setItemMeta(meta);
	}

	public static boolean hasSeekers(ItemStack item) {
		if (item.getItemMeta().hasLore())
			for (String s : item.getItemMeta().getLore())
				if (ChatColor.stripColor(s).contains("Seeker Arrows:"))
					return true;
		return false;
	}

	public static String getWeaponDescriptor(Tier tier) {
		return weaponDescriptors.get(tier)[new Random().nextInt(weaponDescriptors.get(tier).length)];
	}
	public static String getWeaponDescriptor(Tier tier, Integer[] info) {
		int speed = 0;
		int weight = 0;
		int damage = 0;
		if(info.length == 3) {
			weight = info[0];
			speed = info[1];
			damage = info[2];
		}
		
		return null;
	}

	public static String getArmorDescriptor(Tier tier) {
		return armorDescriptors.get(tier)[new Random().nextInt(armorDescriptors.get(tier).length)];
	}

	public static String getWeaponType(Material weapon) {
		if (weapon.name().contains("SWORD"))
			return "Sword";
		if (weapon.name().contains("PICKAXE"))
			return "Pickaxe";
		if (weapon.name().contains("_AXE"))
			return "Axe";
		if (weapon.name().equalsIgnoreCase("TRIDENT"))
			return "Trident";
		if (weapon.name().equalsIgnoreCase("BOW"))
			return "Bow";
		if (weapon.name().equalsIgnoreCase("CROSSBOW"))
			return "Cross Bow";
		return "Stick";
	}

	public static String getArmorType(Material armor) {
		if (armor.name().contains("HELMET"))
			return "Helmet";
		if (armor.name().contains("CHESTPLATE"))
			return "Chestplate";
		if (armor.name().contains("LEGGINGS"))
			return "Pants";
		if (armor.name().contains("BOOTS"))
			return "Boots";

		return "Stick";
	}

	public static ItemStack randomizeWeaponEnhancements(ItemStack item, int level) {
		boolean enhanced = false;

		Collections.shuffle(weaponEnhancements);
		for (Enhancement en : weaponEnhancements) {

			if (new Random().nextBoolean()) {
				if (en.equals(Enhancement.DISARM) && level > Tier.THIRD.getMaxLevel()) {
					ItemMeta a = item.getItemMeta();
					List<String> lore = a.hasLore() ? a.getLore() : new ArrayList<String>();
					lore.add(CoreUtils.colorize(en.getName()
							+ ((int) ((level * CoreUtils.getRandom().nextDouble()) + CoreUtils.getRandom().nextInt(5)))
							+ "&7"));
					a.setLore(lore);
					a.setDisplayName(CoreUtils
							.colorize(a.getDisplayName() + "&f " + (enhanced ? "and" : "of") + " &1Disarming&f"));
					item.setItemMeta(a);
					enhanced = true;
				}
				if (en.equals(Enhancement.POWER_ATTACK)) {
					ItemMeta a = item.getItemMeta();
					List<String> lore = a.hasLore() ? a.getLore() : new ArrayList<String>();
					lore.add(CoreUtils.colorize(en.getName() + ("false") + "&7"));
					a.setLore(lore);
					a.setDisplayName(
							CoreUtils.colorize(a.getDisplayName() + "&f " + (enhanced ? "and" : "of") + " &cPower&f"));
					item.setItemMeta(a);
					enhanced = true;
				}
				if (en.equals(Enhancement.DODGE)) {
					ItemMeta a = item.getItemMeta();
					List<String> lore = a.hasLore() ? a.getLore() : new ArrayList<String>();
					lore.add(CoreUtils.colorize(en.getName()
							+ ((int) ((level * CoreUtils.getRandom().nextDouble()) + CoreUtils.getRandom().nextInt(5)))
							+ "&7"));
					a.setLore(lore);
					a.setDisplayName(CoreUtils
							.colorize(a.getDisplayName() + "&f " + (enhanced ? "and" : "of") + " &eDodging&f"));
					item.setItemMeta(a);
					enhanced = true;
				}
				if (en.equals(Enhancement.VAMPIRISM) && level > Tier.FIRST.getMaxLevel()) {
					ItemMeta a = item.getItemMeta();
					List<String> lore = a.hasLore() ? a.getLore() : new ArrayList<String>();
					lore.add(CoreUtils.colorize(en.getName()
							+ ((int) ((level * CoreUtils.getRandom().nextDouble()) + CoreUtils.getRandom().nextInt(5)))
							+ "&7"));
					a.setLore(lore);
					a.setDisplayName(CoreUtils
							.colorize(a.getDisplayName() + "&f " + (enhanced ? "and" : "of") + " &4Vampirism&f"));
					item.setItemMeta(a);
					enhanced = true;
				}
				if (en.equals(Enhancement.FIRE) && level > Tier.SECOND.getMaxLevel()) {
					ItemMeta a = item.getItemMeta();
					List<String> lore = a.hasLore() ? a.getLore() : new ArrayList<String>();
					lore.add(CoreUtils.colorize(en.getName()
							+ ((int) ((level * CoreUtils.getRandom().nextDouble()) + CoreUtils.getRandom().nextInt(5)))
							+ "&7"));
					a.setLore(lore);
					a.setDisplayName(
							CoreUtils.colorize(a.getDisplayName() + "&f " + (enhanced ? "and" : "of") + " &cFlame&f"));
					item.setItemMeta(a);
					enhanced = true;
				}
				if (en.equals(Enhancement.FROST) && level > Tier.FIRST.getMaxLevel()) {
					ItemMeta a = item.getItemMeta();
					List<String> lore = a.hasLore() ? a.getLore() : new ArrayList<String>();
					lore.add(CoreUtils.colorize(en.getName()
							+ ((int) ((level * CoreUtils.getRandom().nextDouble()) + CoreUtils.getRandom().nextInt(5)))
							+ "&7"));
					a.setLore(lore);
					a.setDisplayName(
							CoreUtils.colorize(a.getDisplayName() + "&f " + (enhanced ? "and" : "of") + " &bFrost&f"));
					item.setItemMeta(a);
					enhanced = true;
				}
			}
		}
		return item;
	}

	private static ItemStack randomizeArmorEnhancements(ItemStack item, int level) {
		Armor armor = new Armor(item);

		Collections.shuffle(armorEnhancements);
		for (Enhancement en : armorEnhancements) {

			if (new Random().nextBoolean()) {
				if (en.equals(Enhancement.SPEED)) {
					armor.enhance(en,
							(int) ((level * CoreUtils.getRandom().nextDouble()) + CoreUtils.getRandom().nextInt(5)));
				}
				if (en.equals(Enhancement.PROTECTION)) {
					armor.enhance(en,
							(int) ((level * CoreUtils.getRandom().nextDouble()) + CoreUtils.getRandom().nextInt(5)));
				}
			}
		}
		return armor.getItem();
	}

	public static ItemStack weaponGenerator(int level) {

		Tier tier = Tier.getTier(level);

		return new Weapon(weaponTiers.get(tier)[new Random().nextInt(weaponTiers.get(tier).length)], level).getItem();
	}

	public static ItemStack armorGenerator(int level) {
		Material[] tier = armorTiers.get(Tier.getTier(level));
		Armor armor = new Armor(tier[CoreUtils.getRandom().nextInt(tier.length)], level);

		return randomizeArmorEnhancements(armor.getItem(), level);
	}

	public static ItemStack foodGenerator() {
		ItemStack item = foods[new Random().nextInt(foods.length)];
		ItemMeta a = item.getItemMeta();
		a.addItemFlags(ItemFlag.values());
		item.setItemMeta(a);
		return item;
	}
	
	public static List<ItemStack> magicItemGenerator(int level) {
		List<ItemStack> items = new ArrayList<>();
		for(int i=0;i<=new Random().nextInt(3)+1;i++) {
			items.add(new MagicItem(new Random().nextInt(1), level).getItem());
		}
		return items;
	}

	public static ItemStack bookGenerator(int level) {

		return new Book(level).getItem();
		
	}

	public static void handleDrops(int level, Location location) {
		List<ItemStack> drops = new ArrayList<>();
		if (CoreUtils.getRandom().nextDouble() <= 0.3) {
			drops.add(armorGenerator(level));
		}
		if (CoreUtils.getRandom().nextDouble() <= 0.7) {
			drops.add(weaponGenerator(level));
		}
		if (CoreUtils.getRandom().nextDouble() <= 0.1) {
			drops.add(foodGenerator());
		}
		if (CoreUtils.getRandom().nextDouble() <= 0.05) {
			for(ItemStack i : magicItemGenerator(level)) {
				drops.add(i);
			}
			
		}

		for (ItemStack i : drops) {
			location.getWorld().dropItemNaturally(location, i);
		}
	}

	public static ItemStack enhanceInInventory(ItemStack tool, ItemStack book) {
		ItemMeta tm = tool.getItemMeta();
		List<String> blore = new ArrayList<>();
		List<String> tlore = new ArrayList<>();
		if (book.hasItemMeta()) {
			if (book.getItemMeta().hasLore()) {
				for (String s : book.getItemMeta().getLore()) {
					blore.add(s);
				}
			}
		}

		if (tool.hasItemMeta()) {
			if (tm.hasLore()) {
				for (String s : tm.getLore()) {
					tlore.add(s);
				}
			}
		}

		Map<String, String> chgs = new HashMap<>();

		for (int i = 0; i != tlore.size(); i++) {
			for (int j = 0; j != blore.size(); j++) {
				if (tlore.get(i).contains(":")) {
					if (ChatColor.stripColor(tlore.get(i).split(":")[0])
							.equals(ChatColor.stripColor(blore.get(j).split(":")[0]))) {
						chgs.put(tlore.get(i), blore.get(j));
					}
				}
			}
		}
		for (Entry<String, String> en : chgs.entrySet()) {
			blore.remove(en.getValue());

			tlore.set(tlore.indexOf(en.getKey()), en.getValue());

//			tlore.remove(en.getKey());
//			tlore.add(en.getValue());

		}

		for (String s : blore) {
			tlore.add(s);
		}

		tm.setLore(tlore);
		tool.setItemMeta(tm);

		finalizeEnhancement(tool, tlore);

		return tool;
	}

	private static ItemStack finalizeEnhancement(ItemStack tool, List<String> lore) {
		if (lore == null) {
			if (tool.hasItemMeta()) {
				if (tool.getItemMeta().hasLore()) {
					lore = tool.getItemMeta().getLore();
				} else {
					lore = new ArrayList<>();
				}
			} else {
				lore = new ArrayList<>();
			}
		}

		ItemMeta tm = tool.getItemMeta();
		for (String l : lore) {
			double n = 0;
			if (l.contains(": ")) {
				try {
					n = ((double) Integer.parseInt(ChatColor.stripColor(l).split(":")[1].replaceAll(" ", "")) / 100);
				} catch (NumberFormatException ex) {
					// Not a number?
				}
			}

			if (ChatColor.stripColor(l).contains("Speed Modifier:")) {
				tm.removeAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED);
				AttributeModifier am = new AttributeModifier(UUID.randomUUID(),
						getArmorType(tool.getType()) + " Movement Speed", n, Operation.ADD_NUMBER);
				tm.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, am);

			}
			if (ChatColor.stripColor(l).contains("Armor Modifier:")) {
				tm.removeAttributeModifier(Attribute.GENERIC_ARMOR);
				AttributeModifier am = new AttributeModifier(UUID.randomUUID(),
						getArmorType(tool.getType()) + " Protection", ((double) n / 100), Operation.ADD_NUMBER);
				tm.addAttributeModifier(Attribute.GENERIC_ARMOR, am);
			}
			if (ChatColor.stripColor(l).contains("Damage:")) {
				tm.removeAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE);
				AttributeModifier at = new AttributeModifier(UUID.randomUUID(), "Attack Damage", n,
						Operation.ADD_NUMBER, EquipmentSlot.HAND);
				tm.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, at);

			}
			if (ChatColor.stripColor(l).contains("Speed:")) {
				tm.removeAttributeModifier(Attribute.GENERIC_ATTACK_SPEED);
				AttributeModifier sp = new AttributeModifier(UUID.randomUUID(), "Attack Speed", n, Operation.ADD_NUMBER,
						EquipmentSlot.HAND);
				tm.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, sp);
			}

		}
		tool.setItemMeta(tm);
		return tool;
	}

	

}
