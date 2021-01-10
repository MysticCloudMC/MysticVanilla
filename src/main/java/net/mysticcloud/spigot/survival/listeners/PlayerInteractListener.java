package net.mysticcloud.spigot.survival.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

import net.mysticcloud.spigot.survival.MysticVanilla;
import net.mysticcloud.spigot.survival.utils.afk.AFKUtils;

public class PlayerInteractListener implements Listener {

	public PlayerInteractListener(MysticVanilla plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerSleep(PlayerBedEnterEvent e) {
		boolean timeChange = true;
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (!player.isSleeping() && !AFKUtils.isAFK(player))
				if (!player.equals(e.getPlayer()))
					timeChange = false;
		}
		if (timeChange) {
			e.getPlayer().getWorld().setTime(0);
		}
	}

}