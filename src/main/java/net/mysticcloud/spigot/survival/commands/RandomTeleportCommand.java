package net.mysticcloud.spigot.survival.commands;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import net.mysticcloud.spigot.core.commands.listeners.CommandTabCompleter;
import net.mysticcloud.spigot.core.utils.CoreUtils;
import net.mysticcloud.spigot.core.utils.teleport.TeleportUtils;
import net.mysticcloud.spigot.survival.MysticVanilla;

public class RandomTeleportCommand implements CommandExecutor {

	private int rad = 4000;
	private int circ = 25132;

	public RandomTeleportCommand(MysticVanilla plugin, String... cmd) {
		for (String comd : cmd) {
			PluginCommand com = plugin.getCommand(comd);
			com.setExecutor(this);
			com.setTabCompleter(new CommandTabCompleter());
		}

	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {

			Location loc = new Location(Bukkit.getWorld("survival"),
					new Random().nextInt(rad)
							* Math.cos(((new Random().nextInt(circ - 1)) * (circ / 360)) * (180 / Math.PI)),
					0, new Random().nextInt(rad)
							* Math.sin(((new Random().nextInt(circ - 1)) * (circ / 360)) * (180 / Math.PI)));

			sender.sendMessage(CoreUtils.prefixes("vanilla") + "You're being teleported to a random location. Good luck!");
			
			TeleportUtils.teleport((Player) sender,
					loc.getWorld().getHighestBlockAt(loc).getLocation().add(0.5, 2, 0.5), true);

		} else {
			sender.sendMessage(CoreUtils.prefixes("rtp") + "You must be a player to use that command.");
		}
		return true;
	}
}
