package net.mysticcloud.spigot.survival.listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

import net.mysticcloud.spigot.survival.MysticVanilla;
import net.mysticcloud.spigot.survival.utils.VanillaUtils;
import net.mysticcloud.spigot.survival.utils.afk.AFKUtils;

public class PlayerInteractListener implements Listener {

	public PlayerInteractListener(MysticVanilla plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerSleep(PlayerBedEnterEvent e) {
		boolean timeChange = true;
		List<UUID> uids = new ArrayList<>();
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (!player.getWorld().equals(e.getPlayer().getWorld()))
				continue;
			if (!player.isSleeping() && !AFKUtils.isAFK(player)) {
				if (!player.equals(e.getPlayer())) {
					timeChange = false;
				}
			} else {
				if (player.isSleeping() && AFKUtils.isAFK(player))
					uids.add(player.getUniqueId());

			}
		}
		if (timeChange) {
			Bukkit.getScheduler().runTaskLater(VanillaUtils.getPlugin(), new Runnable() {
				public void run() {
					boolean check2 = true;
					for (UUID uid : uids) {
						if (Bukkit.getPlayer(uid) != null) {
							if (!Bukkit.getPlayer(uid).isSleeping()) {
								check2 = false;
							}
						}
					}
					if (check2)
						e.getPlayer().getWorld().setTime(0);
				}
			}, 5 * 20);

		}
	}

}