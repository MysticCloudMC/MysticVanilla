package net.mysticcloud.spigot.survival.utils.afk;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.mysticcloud.spigot.core.utils.CoreUtils;

public class AFKUtils {

	static Map<UUID, AFKPacket> afkers = new HashMap<>();

	public static void afk(Player player, boolean afk) {
		if (afkers.containsKey(player.getUniqueId()) ? !(afk == afkers.get(player.getUniqueId()).isAFK()) : false)
			Bukkit.broadcastMessage(CoreUtils.prefixes("afk")
					+ CoreUtils.colorize("&f" + player.getName() + "&7 is " + (afk ? "now" : "no longer") + " afk"));
		afkers.put(player.getUniqueId(), new AFKPacket(player.getUniqueId(), player.getLocation(), afk));
	}

	public static boolean isAFK(Player player) {
		return afkers.containsKey(player.getUniqueId()) ? afkers.get(player.getUniqueId()).isAFK() : false;
	}

}
