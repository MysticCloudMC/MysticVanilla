package net.mysticcloud.spigot.survival.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import net.mysticcloud.spigot.core.commands.listeners.CommandTabCompleter;
import net.mysticcloud.spigot.core.utils.CoreUtils;
import net.mysticcloud.spigot.survival.MysticVanilla;
import net.mysticcloud.spigot.survival.utils.VanillaUtils;

public class RandomTeleportCommand implements CommandExecutor {

	public RandomTeleportCommand(MysticVanilla plugin, String... cmd) {
		for (String comd : cmd) {
			PluginCommand com = plugin.getCommand(comd);
			com.setExecutor(this);
			com.setTabCompleter(new CommandTabCompleter());
		}

	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Location loc = ((Player) sender).getLocation().clone();
			sender.sendMessage(CoreUtils.prefixes("rtp") + "Don't move. You will teleport in 10 seconds.");
			Bukkit.getScheduler().runTaskLater(VanillaUtils.getPlugin(), new Runnable() {

				@Override
				public void run() {
					if (((Player) sender).getLocation().getBlockX() == loc.getBlockX()
							&& ((Player) sender).getLocation().getBlockZ() == loc.getBlockZ()) {

						Location loc2 = new Location(Bukkit.getWorld("survival"),
								Math.cos(Math.toRadians(((CoreUtils.getRandom().nextInt(25132)) * (360 / 25132))))
										* (CoreUtils.getRandom().nextInt(4000)),
								0, Math.sin(Math.toRadians(((CoreUtils.getRandom().nextInt(25132)) * (360 / 25132)))));

						((Player) sender).teleport(loc2.getWorld().getHighestBlockAt(loc2).getLocation().add(0, 2, 0));
						sender.sendMessage(CoreUtils.prefixes("rtp")
								+ "You've been teleported to a random location in the survival world! Good luck! :)");
					} else {
						sender.sendMessage(CoreUtils.prefixes("rtp") + "You moved. Teleportation cancelled.");
					}
				}

			}, 10 * 20);

		} else {
			sender.sendMessage(CoreUtils.prefixes("rtp") + "You must be a player to use that command.");
		}
		return false;
	}
}
