package net.mysticcloud.spigot.survival.listeners;

import org.bukkit.event.Listener;

import net.mysticcloud.spigot.survival.MysticVanilla;

public class InventoryListener implements Listener {

	public InventoryListener(MysticVanilla plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}


}
