package net.mysticcloud.spigot.survival.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import net.mysticcloud.spigot.core.utils.CoreUtils;
import net.mysticcloud.spigot.core.utils.MysticPlayer;
import net.mysticcloud.spigot.core.utils.afk.AFKRunnable;
import net.mysticcloud.spigot.survival.MysticVanilla;

public class VanillaUtils {
	private static MysticVanilla plugin;

	static Map<UUID, VanillaPlayer> vplayers = new HashMap<>();

//	static String[] descriptors = new String[] {"Xelphor's", "Shiny", "Swift", "Dull", "Chipped", "Hardy", "Sharp", "Hellish"};

	static Inventory bench = null;

	public static void start(MysticVanilla main) {
		plugin = main;
		CoreUtils.addPrefix("vanilla", "&3&lSurvival &7>&f ");
		CoreUtils.addPrefix("rtp", "&e&lRandom Teleport &f>&7 ");
		CoreUtils.coreHandleDamage(false);

		Bukkit.getScheduler().runTaskLater(plugin, new AFKRunnable(), 5 * 20);
	}

	public static MysticVanilla getPlugin() {
		return plugin;
	}

	public static VanillaPlayer getVanillaPlayer(UUID uid) {
		return getVanillaPlayer(CoreUtils.getMysticPlayer(uid));
	}

	public static VanillaPlayer getVanillaPlayer(Player player) {
		return getVanillaPlayer(CoreUtils.getMysticPlayer(player));
	}

	public static String formatMessage(String message, String... values) {
		int i = 0;
		String string = message;
		while (string.contains("?")) {
			string = string.replaceFirst("?", values[i]);
			i = i + 1;
		}
		return string;
	}

	public static VanillaPlayer getVanillaPlayer(MysticPlayer player) {

		if (vplayers.containsKey(player.getUUID())) {
			return vplayers.get(player.getUUID());
		}
		VanillaPlayer vplayer = new VanillaPlayer(player);

		vplayers.put(player.getUUID(), vplayer);

		return vplayer;
	}

	public static Collection<VanillaPlayer> getAllVanillaPlayers() {
		return vplayers.values();
	}

}
