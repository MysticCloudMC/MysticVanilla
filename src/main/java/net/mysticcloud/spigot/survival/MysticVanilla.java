package net.mysticcloud.spigot.survival;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import net.mysticcloud.spigot.survival.commands.HomeCommand;
import net.mysticcloud.spigot.survival.commands.PlayerWarpCommand;
import net.mysticcloud.spigot.survival.commands.RandomTeleportCommand;
import net.mysticcloud.spigot.survival.listeners.EntityListener;
import net.mysticcloud.spigot.survival.listeners.InventoryListener;
import net.mysticcloud.spigot.survival.listeners.PlayerAttackListener;
import net.mysticcloud.spigot.survival.listeners.PlayerInteractListener;
import net.mysticcloud.spigot.survival.listeners.PlayerListener;
import net.mysticcloud.spigot.survival.utils.VanillaUtils;

public class MysticVanilla extends JavaPlugin {

	private int attempt = 0;
	private int maxattempts = 3;

	List<CommandExecutor> cmds = new ArrayList<>();
	List<Listener> listeners = new ArrayList<>();

	public void onEnable() {
		if (!getServer().getPluginManager().isPluginEnabled("MysticCore")) {
			Bukkit.getConsoleSender()
					.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&3&l" + getDescription().getName()
									+ " &f>&7 Can't find MysticCore. Trying again.. Attempt " + attempt + " out of "
									+ maxattempts));
			attempt += 1;
			if (attempt == maxattempts + 1) {
				Bukkit.getConsoleSender()
						.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&3&l" + getDescription().getName() + " &f>&7 Couldn't find MysticCore after "
										+ maxattempts + " tries. Plugin will not load."));
				getServer().getPluginManager().disablePlugin(this);
				return;
			}
			Bukkit.getScheduler().runTaskLater(this, new Runnable() {

				@Override
				public void run() {
					onEnable();
				}

			}, 20 * 3);
			return;
		} else {
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
					"&3&l" + getDescription().getName() + " &f>&7 Found MysticCore! Loading plugin.."));
		}

		VanillaUtils.start(this);
		new HomeCommand(this, "home", "sethome", "removehome");
		new PlayerWarpCommand(this, "playerwarp", "addplayerwarp", "removeplayerwarp");
		new RandomTeleportCommand(this, "rtp","randomteleport");

		new PlayerListener(this);
		new PlayerAttackListener(this);
		new PlayerInteractListener(this);
		new EntityListener(this);
		new InventoryListener(this);
		
	}

}
