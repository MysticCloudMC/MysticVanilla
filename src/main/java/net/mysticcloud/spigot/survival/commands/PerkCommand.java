package net.mysticcloud.spigot.survival.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import net.mysticcloud.spigot.core.commands.listeners.CommandTabCompleter;
import net.mysticcloud.spigot.core.utils.CoreUtils;
import net.mysticcloud.spigot.survival.MysticSurvival;
import net.mysticcloud.spigot.survival.utils.Division;
import net.mysticcloud.spigot.survival.utils.SurvivalPlayer;
import net.mysticcloud.spigot.survival.utils.SurvivalUtils;
import net.mysticcloud.spigot.survival.utils.perks.Perks;

public class PerkCommand implements CommandExecutor {

	public PerkCommand(MysticSurvival plugin, String... cmd) {
		for (String comd : cmd) {
			PluginCommand com = plugin.getCommand(comd);
			com.setExecutor(this);
			com.setTabCompleter(new CommandTabCompleter());
		}

	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			if (args[0].equalsIgnoreCase("add")) {
				if (args.length == 4) {
					if (Bukkit.getPlayer(args[1]) != null) {
						SurvivalUtils.getSurvivalPlayer(Bukkit.getPlayer(args[1]))
								.addPerk(Perks.getPerk(Division.valueOf(args[2]), args[3]));
					}
				}
			} else
				sender.sendMessage(CoreUtils.prefixes("admin") + "Player only command.");
			return true;
		}
		SurvivalPlayer player = SurvivalUtils.getSurvivalPlayer(((Player) sender));
		if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
			if (args.length == 0 || args[1].equals("1")) {
				sender.sendMessage(CoreUtils.prefixes("survival") + "Here's a list of perk commands:");
				sender.sendMessage(CoreUtils.colorize("&d/perks help [page] &5- shows this list."));
				sender.sendMessage(CoreUtils
						.colorize("&d/perks list [division] &5- lists all perks or just perks in a division."));
				sender.sendMessage(CoreUtils.colorize("&d/perks m[ylist] &5- shows all perks you have."));
				sender.sendMessage(CoreUtils.colorize("&d/perks u[se] <division> <perk> &5- use or activate a perk."));
				sender.sendMessage(CoreUtils
						.colorize("&d/perks t[arget] &5- targets the next living entity you hit with a projectile."));
				return true;
			}
			if (args[1].equals("2")) {
				sender.sendMessage(CoreUtils.prefixes("survival") + "Here's some perk info");
				sender.sendMessage(CoreUtils.colorize(
						"&dTargeting&5: You can target an entity with the &e&lTargeting Wand&5 given to you when you joined, or you can use the /perk target command and hit and entity with a projectile."));
				sender.sendMessage(CoreUtils.colorize(
						"&dSwitching Worlds&5: Right click on the &a&lWorld Switcher&5 given to you at after the first quest."));
				return true;
			}
		}
		if (args[0].equalsIgnoreCase("m") || args[0].equalsIgnoreCase("mylist")) {
			sender.sendMessage(CoreUtils.prefixes("survival") + "Here's a list of your perks:");
			String p = "";
			for (Perks perk : SurvivalUtils.getSurvivalPlayer((Player) sender).getPerks())
				p = p == "" ? perk.getName() : p + ", " + perk.getName();
			sender.sendMessage(CoreUtils.colorize(p));
			return true;
		}
		if (args[0].equalsIgnoreCase("t") || args[0].equalsIgnoreCase("target")) {
			SurvivalUtils.addTargeter(player.getPlayer().getUUID());
		}
		if (args[0].equalsIgnoreCase("add")) {
			if (args.length != 3) {
				sender.sendMessage(CoreUtils.prefixes("survival")
						+ "There was an error in your syntax. /perks u[se] <division> <perk>");
				return true;
			}
			if (Division.valueOf(args[1].toUpperCase()) == null) {
				sender.sendMessage(CoreUtils.prefixes("survival") + "Unknown division.");
				// TODO list divisions
				return true;
			}
			if (Perks.getPerk(Division.valueOf(args[1].toUpperCase()), args[2]).equals(null)) {
				sender.sendMessage(CoreUtils.prefixes("survival") + "Unknown perk.");
				return true;
			}
			Division division = Division.valueOf(args[1].toUpperCase());
			Perks perks = Perks.getPerk(division, args[2]);
			if (!player.hasPerk(perks)) {
				player.addPerk(perks, 2D);
				player.sendMessage("Added perk.");
				return true;
			}
			player.sendMessage("You already have that perk.");
		}
		if (args[0].equalsIgnoreCase("list")) {
			player.sendMessage("Here's a list of perks.");
			if (args.length == 2) {
				String s = "";
				Division d = Division.valueOf(args[1].toUpperCase());
				for (Perks perk : Perks.values()) {
					if (perk.getName().toUpperCase().startsWith(d.getPerkPrefix().toUpperCase()))
						s = s == "" ? perk.getName() : s + ", " + perk.getName();
				}
				sender.sendMessage(CoreUtils.colorize("&5" + d.name() + "&d: " + s));
				return true;
			}
			for (Division d : Division.values()) {
				String s = "";
				for (Perks perk : Perks.values()) {
					if (perk.getName().toUpperCase().startsWith(d.getPerkPrefix().toUpperCase()))
						s = s == "" ? perk.getName() : s + ", " + perk.getName();
				}
				sender.sendMessage(CoreUtils.colorize("&5" + d.name() + "&d: " + s));

			}
			return true;

		}
		if (args[0].equalsIgnoreCase("u") || args[0].equalsIgnoreCase("use")) {
			if (args.length != 3) {
				sender.sendMessage(CoreUtils.prefixes("survival")
						+ "There was an error in your syntax. /perks u[se] <division> <perk>");
				return true;
			}
			if (Division.valueOf(args[1].toUpperCase()).equals(null)) {
				sender.sendMessage(CoreUtils.prefixes("survival") + "Unknown division.");
				// TODO list divisions
				return true;
			}
			if (Perks.getPerk(Division.valueOf(args[1].toUpperCase()), args[2]).equals(null)) {
				sender.sendMessage(CoreUtils.prefixes("survival") + "Unknown perk.");
				return true;
			}
			Division division = Division.valueOf(args[1].toUpperCase());
			Perks perks = Perks.getPerk(division, args[2]);
			if (!player.hasPerk(perks)) {
				sender.sendMessage(CoreUtils.prefixes("survival") + "You don't have that perk.");
				return true;
			}
			player.activatePerk(perks);
		}

		return true;

	}
}
