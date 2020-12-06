package net.mysticcloud.spigot.survival.listeners;

import org.bukkit.event.Listener;

import net.mysticcloud.spigot.survival.MysticVanilla;

public class PlayerAttackListener implements Listener {

	public PlayerAttackListener(MysticVanilla plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

}
