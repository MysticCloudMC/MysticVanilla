package net.mysticcloud.spigot.survival.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.mysticcloud.spigot.core.utils.afk.AFKUtils;
import net.mysticcloud.spigot.survival.MysticVanilla;
import net.mysticcloud.spigot.survival.utils.VanillaUtils;

public class PlayerListener implements Listener {

	public PlayerListener(MysticVanilla plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		if (AFKUtils.isAFK(e.getPlayer())) {
			if (!(AFKUtils.getAFKPacket(e.getPlayer()) == null)) {
				if (!AFKUtils.getAFKPacket(e.getPlayer()).getLocation().getBlock().getLocation()
						.equals(e.getPlayer().getLocation().getBlock().getLocation())) {
					AFKUtils.afk(e.getPlayer(), false);
				}
			}

		}
	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		VanillaUtils.getVanillaPlayer(e.getPlayer()).save();
	}

}
