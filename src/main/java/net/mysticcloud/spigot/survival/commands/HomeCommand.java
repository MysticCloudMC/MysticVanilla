package net.mysticcloud.spigot.survival.commands;

import java.util.List;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import net.mysticcloud.spigot.core.commands.listeners.CommandTabCompleter;
import net.mysticcloud.spigot.core.utils.CoreUtils;
import net.mysticcloud.spigot.core.utils.teleport.TeleportUtils;
import net.mysticcloud.spigot.core.utils.warps.HomeUtils;
import net.mysticcloud.spigot.core.utils.warps.Warp;
import net.mysticcloud.spigot.core.utils.warps.WarpBuilder;
import net.mysticcloud.spigot.core.utils.warps.WarpUtils;
import net.mysticcloud.spigot.survival.MysticVanilla;
import net.mysticcloud.spigot.survival.utils.VanillaUtils;

public class HomeCommand implements CommandExecutor {

	public HomeCommand(MysticVanilla plugin, String... cmd) {
		for (String comd : cmd) {
			PluginCommand com = plugin.getCommand(comd);
			com.setExecutor(this);
			com.setTabCompleter(new CommandTabCompleter());
		}

	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			if (cmd.getName().equalsIgnoreCase("home")) {
				List<Warp> homes = HomeUtils.getHomes(((Player) sender).getUniqueId());
				if (args.length <= 1) {
					if (args.length == 0) {
						if (homes.size() == 0) {
							sender.sendMessage(CoreUtils.prefixes("homes") + "You must set a home first! /sethome");
							return true;
						}
					}

					Warp home = args.length == 0 ? HomeUtils.getHome(((Player) sender).getUniqueId())
							: HomeUtils.getHome(((Player) sender).getUniqueId(), args[0]);

					TeleportUtils.teleport(((Player) sender), home.location(), false, false);

					sender.sendMessage(CoreUtils.prefixes("homes") + CoreUtils
							.colorize(VanillaUtils.formatMessage("You have teleported to home &7?&f.", home.name())));
					if (args.length == 0 && HomeUtils.getHomes(((Player) sender).getUniqueId()).size() >= 2) {
						String s = "";
						for (Warp homestr : homes)
							s = s == "" ? homestr.name() : s + ", " + homestr.name();
						sender.sendMessage(CoreUtils.prefixes("homes") + "Here's a list of your homes: " + s);
					}

				} else {
					UUID owner = CoreUtils.LookupUUID(args[0]);
					String home = args[1];

					if (owner != null) {
						List<Warp> ohomes = HomeUtils.getHomes(owner);
						for (Warp ohome : ohomes) {
							if (ohome.name().equalsIgnoreCase(home)) {
								((Player) sender).teleport(ohome.location());
								sender.sendMessage(CoreUtils.prefixes("homes")
										+ VanillaUtils.formatMessage("You have teleported to &7?&f home &7?&f.",
												formatUsername(args[0]), ohome.name()));
								return true;
							}
						}
						sender.sendMessage(CoreUtils.prefixes("homes") + "That home could not be found.");
						return true;
					}
					sender.sendMessage(CoreUtils.prefixes("homes") + "That player could not be found.");
				}
			}

			if (cmd.getName().equalsIgnoreCase("sethome")) {
				if (sender instanceof Player) {
					String name = args.length > 0 ? args[0]
							: (HomeUtils.getHomes(((Player) sender).getUniqueId()).size() + 1) + "";
					WarpBuilder warp = new WarpBuilder();
					if (warp.createWarp().setType("home~" + ((Player) sender).getUniqueId()).setName(name)
							.setLocation(((Player) sender).getLocation()).getWarp() != null)
						sender.sendMessage(CoreUtils.prefixes("homes") + "Home '" + name + "' set!");
					else
						sender.sendMessage(CoreUtils.prefixes("homes") + "There was an error setting you home.");

				} else {
					sender.sendMessage(CoreUtils.prefixes("homes") + "You must be a player to use that command.");
				}
			}

			if (cmd.getName().equalsIgnoreCase("deletehome")) {
				if (sender instanceof Player) {
					if (args.length == 1) {
						if (HomeUtils.getHome(((Player) sender).getUniqueId(), args[0]) != null) {
							WarpUtils.removeWarp("home~" + ((Player) sender).getUniqueId(),
									HomeUtils.getHome(((Player) sender).getUniqueId(), args[0]));
							sender.sendMessage(CoreUtils.prefixes("homse") + "Deleted home '" + args[0] + "'.");
							return true;
						} else {
							sender.sendMessage(CoreUtils.prefixes("homes") + "Couldn't find that home.");
						}
					} else {
						if (HomeUtils.getHome(((Player) sender).getUniqueId()) != null) {
							sender.sendMessage(CoreUtils.prefixes("homes") + "Deleted home '"
									+ HomeUtils.getHome(((Player) sender).getUniqueId()).name() + "'.");
							WarpUtils.removeWarp("home~" + ((Player) sender).getUniqueId(),
									HomeUtils.getHome(((Player) sender).getUniqueId()));
							return true;
						} else {
							sender.sendMessage(CoreUtils.prefixes("homes") + "Couldn't find a home.");
						}
					}

				} else {
					sender.sendMessage(CoreUtils.prefixes("homes") + "You must be a player to use that command.");
				}
			}

		} else {
			sender.sendMessage(CoreUtils.prefixes("homes") + "You must be a player to use that command.");
		}

		return true;

	}

	private String formatUsername(String username) {
		return username.toLowerCase().endsWith("s") ? username + "'" : username + "'s";

	}
}
