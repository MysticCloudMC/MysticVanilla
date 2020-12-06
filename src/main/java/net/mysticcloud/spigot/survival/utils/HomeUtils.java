package net.mysticcloud.spigot.survival.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;

import net.mysticcloud.spigot.core.utils.warps.Warp;
import net.mysticcloud.spigot.core.utils.warps.WarpUtils;

public class HomeUtils {

	public static List<Warp> getHomes(UUID uid) {
		List<Warp> homes = new ArrayList<>();
		for (Warp home : WarpUtils.getWarps("home~" + uid.toString())) {
			homes.add(home);
		}

		return homes;
	}

}
