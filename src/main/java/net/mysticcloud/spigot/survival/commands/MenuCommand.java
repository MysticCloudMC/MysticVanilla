package net.mysticcloud.spigot.survival.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import net.mysticcloud.spigot.core.commands.listeners.CommandTabCompleter;
import net.mysticcloud.spigot.survival.MysticSurvival;
import net.mysticcloud.spigot.survival.utils.inventories.InventoryUtils;

public class MenuCommand implements CommandExecutor {

	public MenuCommand(MysticSurvival plugin, String... cmd) {
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
		InventoryUtils.openMenu(((Player)sender));
		
		return true;
	}

//	private String formatUsername(String username) {
//		return username.toLowerCase().endsWith("s") ? username + "'" : username + "'s";
//
//	}
}
