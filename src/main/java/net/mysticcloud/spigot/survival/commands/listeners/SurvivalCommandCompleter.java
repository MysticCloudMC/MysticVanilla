package net.mysticcloud.spigot.survival.commands.listeners;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;


public class SurvivalCommandCompleter implements TabCompleter {

	private Map<String, String[]> subcommands = new HashMap<>();

	public SurvivalCommandCompleter() {
		

		subcommands.put("poll", new String[] { "create", "1", "2", "remove", "regenerate", "reset" });

	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> completions = new ArrayList<>();

		
		if (label.toUpperCase().equalsIgnoreCase("is") || label.toUpperCase().equalsIgnoreCase("island")) {
			if (args.length == 1) {
				StringUtil.copyPartialMatches(args[0], Arrays.asList(subcommands.get("island")), completions);
			}
			if (args.length == 2) {
				if (args[1].equalsIgnoreCase("reset")) {
					StringUtil.copyPartialMatches(args[1], Arrays.asList(subcommands.get("island/reset")), completions);
				}
			}
		}
		if (label.toUpperCase().equalsIgnoreCase("isadm") || label.toUpperCase().equalsIgnoreCase("islandadmin")) {
			if (args.length == 1) {
				StringUtil.copyPartialMatches(args[0], Arrays.asList(subcommands.get("islandadmin")), completions);
			}
			if (args.length == 2) {
				if (args[1].equalsIgnoreCase("description")) {
					StringUtil.copyPartialMatches(args[1], Arrays.asList(subcommands.get("islandadmin/description")), completions);
				}
				
			}
		}

		return completions;

	}
	

	public List<String> getOnlinePlayers() {
		List<String> players = new ArrayList<>();
		for (Player player : Bukkit.getOnlinePlayers()) {
			players.add(player.getName());
		}
		return players;
	}


}
