package net.mysticcloud.spigot.survival.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.mysticcloud.spigot.core.utils.CoreUtils;
import net.mysticcloud.spigot.core.utils.MysticPlayer;
import net.mysticcloud.spigot.survival.MysticSurvival;
import net.mysticcloud.spigot.survival.runnables.MainTimer;
import net.mysticcloud.spigot.survival.utils.inventories.InventoryUtils;
import net.mysticcloud.spigot.survival.utils.items.ItemUtils;

public class SurvivalUtils {
	private static MysticSurvival plugin;

	private static List<UUID> targeters = new ArrayList<>();

	static Map<UUID, SurvivalPlayer> splayers = new HashMap<>();

//	static String[] descriptors = new String[] {"Xelphor's", "Shiny", "Swift", "Dull", "Chipped", "Hardy", "Sharp", "Hellish"};

	static Inventory bench = null;

	public static void start(MysticSurvival main) {
		plugin = main;
		CoreUtils.addPrefix("homes", "&a&lHome &7>&e ");
		CoreUtils.addPrefix("survival", "&d&lOlympus &7>&e ");
		CoreUtils.addPrefix("olympus", "&d&lOlympus &7>&e ");
		CoreUtils.addPrefix("division", "&2&lDivision &7>&a ");
		CoreUtils.addPrefix("skills", "&a&lSkills &f>&7 ");
		CoreUtils.coreHandleDamage(false);

		InventoryUtils.start();
		ItemUtils.start();

		Bukkit.getScheduler().runTaskLater(SurvivalUtils.getPlugin(), new MainTimer(), 2);

	}

	public static MysticSurvival getPlugin() {
		return plugin;
	}

	public static SurvivalPlayer getSurvivalPlayer(UUID uid) {
		return getSurvivalPlayer(CoreUtils.getMysticPlayer(uid));
	}

	public static SurvivalPlayer getSurvivalPlayer(Player player) {
		return getSurvivalPlayer(CoreUtils.getMysticPlayer(player));
	}

	public static SurvivalPlayer getSurvivalPlayer(MysticPlayer player) {

		if (splayers.containsKey(player.getUUID())) {
			return splayers.get(player.getUUID());
		}
		SurvivalPlayer splayer = new SurvivalPlayer(player);

		splayers.put(player.getUUID(), splayer);

		return splayer;
	}

	public static Collection<SurvivalPlayer> getAllSurvivalPlayers() {
		return splayers.values();
	}

	public static void addTargeter(UUID uid) {
		if (isTargeting(uid))
			Bukkit.getPlayer(uid).sendMessage(net.md_5.bungee.api.ChatColor.RED + "You are already targeting.");
		else
			targeters.add(uid);
	}

	public static boolean isTargeting(UUID uid) {
		return targeters.contains(uid);
	}

	public static void removeTargeter(UUID uid) {
		targeters.remove(uid);
	}

}
