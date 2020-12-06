package net.mysticcloud.spigot.survival.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import net.mysticcloud.spigot.core.commands.listeners.CommandTabCompleter;
import net.mysticcloud.spigot.core.utils.CoreUtils;
import net.mysticcloud.spigot.core.utils.warps.Warp;
import net.mysticcloud.spigot.core.utils.warps.WarpBuilder;
import net.mysticcloud.spigot.core.utils.warps.WarpUtils;
import net.mysticcloud.spigot.survival.MysticVanilla;

public class PlayerWarpCommand implements CommandExecutor {

	public PlayerWarpCommand(MysticVanilla plugin, String... cmd) {
		for (String comd : cmd) {
			PluginCommand com = plugin.getCommand(comd);
			com.setExecutor(this);
			com.setTabCompleter(new CommandTabCompleter());
		}

	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			if (cmd.getName().equalsIgnoreCase("playerwarp")) {

				if (args.length == 0) {
					sender.sendMessage(CoreUtils.prefixes("playerwarps") + "Here is a list of avalible Player Warps:");
					String warps = "";
					for (Warp warp : WarpUtils.getWarps("playerwarps"))
						warps = warps == "" ? warp.name() : warps + ", " + warp.name();
					sender.sendMessage(warps);

					return false;
				}
				String type = "playerwarps";
				String name = args[0];
				int sel = args.length >= 2 ? Integer.parseInt(args[1]) - 1 : 0;
				List<Warp> warps = WarpUtils.getWarps(type, name);

				if (warps.size() == 0) {
					sender.sendMessage(CoreUtils.prefixes("playerwarps") + "Can't find that Player Warp...");
					return false;
				}

				if (warps.size() >= 2 && sel == 0) {
					sender.sendMessage(
							CoreUtils.prefixes("playerwarps") + "There are more than one Player Warp with that name.");
					sender.sendMessage(CoreUtils
							.colorize("&3To specify type \"&f/playerwarp " + name + " <1-" + warps.size() + ">&3\""));
					return true;
				}

				sender.sendMessage(CoreUtils.prefixes("playerwarps")
						+ "There was an error. If you see this message please contact an admin. (Error Code: SUR-PWCMD101)");
			}

			if (cmd.getName().equalsIgnoreCase("addplayerwarp")) {
				String name = args.length >= 1 ? args[0] : ((Player) sender).getName();
				WarpBuilder warp = new WarpBuilder();
				if(warp.createWarp().setType("playerwarps").setName(name).setLocation(((Player) sender).getLocation())
						.setMetadata("Owner", ((Player) sender).getUniqueId().toString()).getWarp() != null) {
					sender.sendMessage(CoreUtils.prefixes("playerwarps") + "Player Warp (" + name + ") set!");
				} else {
					sender.sendMessage(CoreUtils.prefixes("playerwarps") + "There was an error setting the player warp");
					return false;
				}
				

			}
		} else {
			sender.sendMessage(CoreUtils.prefixes("warps") + "You must be a player to use that command.");
		}
		return false;
	}
}
