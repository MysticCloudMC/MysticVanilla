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
import net.mysticcloud.spigot.core.utils.warps.Warp;
import net.mysticcloud.spigot.core.utils.warps.WarpBuilder;
import net.mysticcloud.spigot.core.utils.warps.WarpUtils;
import net.mysticcloud.spigot.survival.MysticVanilla;
import net.mysticcloud.spigot.survival.utils.HomeUtils;

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
				if (args.length <= 1 && CoreUtils.LookupUUID(args.length > 0 ? args[0] : "") == null) {
					if (homes.size() == 0) {
						sender.sendMessage(CoreUtils.prefixes("homes") + "You must set a home first! /sethome");
						return true;
					}

					Warp thome = homes.get(0);
					boolean choosen = false;
					if (homes.size() > 1 && args.length > 0) {
						for (Warp home : homes) {
							if (home.name().equalsIgnoreCase(args[0])) {
								thome = home;
								choosen = true;
								break;
							}
						}
					}

					
					sender.sendMessage(CoreUtils.prefixes("homes") + "Teleporting to home " + thome.name() + ".");
					if (!choosen) {
						String s = "";
						for (Warp home : homes)
							s = s == "" ? home.name() : s + ", " + home.name();
						sender.sendMessage(CoreUtils.prefixes("homes") + "Here's a list of your homes: " + s);
					}
					TeleportUtils.teleport(((Player) sender), thome.location(), false);

				} else {
					UUID owner = CoreUtils.LookupUUID(args[0]);
					String home = args.length >= 2 ? args[1] : "";

					if (owner != null) {
						List<Warp> ohomes = HomeUtils.getHomes(owner);
						for (Warp ohome : ohomes) {
							if (ohome.name().equalsIgnoreCase(home) || home.equals("")) {
								if (CoreUtils.getMysticPlayer((Player) sender).isFriends(CoreUtils.LookupUUID(args[0]))
										|| sender.hasPermission("mysticcloud.admin.homes.override")) {

									if (sender.hasPermission("mysticcloud.admin.homes.override")) {
										sender.sendMessage(CoreUtils.prefixes("homes")
												+ "You must be friends to go to their home.");
										sender.sendMessage(CoreUtils.prefixes("admin") + "Overriding");
									}

									TeleportUtils.teleport(((Player) sender), ohome.location(), false);
									sender.sendMessage(CoreUtils.prefixes("homes") + "You have teleported to "
											+ formatUsername(args[0]) + " home " + ohome.name() + ".");
								} else {
									sender.sendMessage(
											CoreUtils.prefixes("homes") + "You must be friends to go to their home.");
								}

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
						sender.sendMessage(CoreUtils.prefixes("homes") + "Home (" + name + ") set!");
					else
						sender.sendMessage(CoreUtils.prefixes("homes") + "There was an error setting you home.");

				} else {
					sender.sendMessage(CoreUtils.prefixes("homes") + "You must be a player to use that command.");
				}
			}

			if (cmd.getName().equalsIgnoreCase("deletehome")) {
				if (sender instanceof Player) {
					for (Warp home : HomeUtils.getHomes(((Player) sender).getUniqueId())) {
						WarpUtils.removeWarp("home~" + ((Player) sender).getUniqueId(), home);
						sender.sendMessage(CoreUtils.prefixes("homes") + "DELETED");
						return true;
					}
					sender.sendMessage(CoreUtils.prefixes("homes") + "ERROR");

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
