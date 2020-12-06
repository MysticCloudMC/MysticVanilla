package net.mysticcloud.spigot.survival.commands;

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

public class DivisionCommand implements CommandExecutor {

	public DivisionCommand(MysticSurvival plugin, String... cmd) {
		for (String comd : cmd) {
			PluginCommand com = plugin.getCommand(comd);
			com.setExecutor(this);
			com.setTabCompleter(new CommandTabCompleter());
		}

	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage("Player only command.");
			return true;
		}
		SurvivalPlayer player = SurvivalUtils.getSurvivalPlayer(((Player) sender).getUniqueId());
		if(args.length == 0) {
			player.sendMessage("division","Here's some Division commands:");
			sender.sendMessage(CoreUtils.colorize("&a/d[ivision] join <division>"));
			sender.sendMessage(CoreUtils.colorize("&a/d list"));
			sender.sendMessage(CoreUtils.colorize("&c/d chat &a- Coming soon"));
			return true;
		}
		if(args[0].equalsIgnoreCase("join")) {
			for(Division div : Division.values()) {
				if(div.name().equalsIgnoreCase(args[1])) {
					player.setDivision(div);return true;
				}
			}
			player.sendMessage("division","That division does not exist.");
			return true;
		}
		if(args[0].equalsIgnoreCase("list")) {
			String s = "";
			for(Division div : Division.values()) {
				s = s == "" ? "&a" + div.getDisplayName() : s + "&2, &a" + div.getDisplayName();
			}
			player.sendMessage("division","Here's a list of Divisions:");
			sender.sendMessage(CoreUtils.colorize(s));
		}
		
		return true;
	}

//	private String formatUsername(String username) {
//		return username.toLowerCase().endsWith("s") ? username + "'" : username + "'s";
//
//	}
}
