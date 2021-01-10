package net.mysticcloud.spigot.survival.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import net.mysticcloud.spigot.core.commands.listeners.CommandTabCompleter;
import net.mysticcloud.spigot.core.utils.CoreUtils;
import net.mysticcloud.spigot.survival.MysticVanilla;
import net.mysticcloud.spigot.survival.utils.afk.AFKUtils;

public class AFKCommand implements CommandExecutor {

	public AFKCommand(MysticVanilla plugin, String... cmd) {
		for (String comd : cmd) {
			PluginCommand com = plugin.getCommand(comd);
			com.setExecutor(this);
			com.setTabCompleter(new CommandTabCompleter());
		}

	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args.length == 1 && sender.hasPermission("mysticcloud.admin.afkother")) {
				if (Bukkit.getPlayer(args[0]) != null) {
					player = Bukkit.getPlayer(args[0]);
				} else {
					sender.sendMessage(CoreUtils.prefixes("afk") + "That player isn't online.");
				}
			}
			AFKUtils.afk(player, !AFKUtils.isAFK(player));
		} else {
			if (args.length == 1) {
				if (Bukkit.getPlayer(args[0]) != null) {
					AFKUtils.afk(Bukkit.getPlayer(args[0]), !AFKUtils.isAFK(Bukkit.getPlayer(args[0])));
				} else {
					sender.sendMessage(CoreUtils.prefixes("afk") + "That player isn't online.");
				}
			} else {
				sender.sendMessage(CoreUtils.prefixes("afk") + "Try /afk <player>");
			}

		}
		return true;
	}
}
