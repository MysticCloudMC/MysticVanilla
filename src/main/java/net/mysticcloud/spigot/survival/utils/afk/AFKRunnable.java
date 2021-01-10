package net.mysticcloud.spigot.survival.utils.afk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.mysticcloud.spigot.survival.utils.VanillaUtils;

public class AFKRunnable implements Runnable {

	Map<UUID, Location> locs;

	public AFKRunnable() {
		locs = new HashMap<>();
	}

	public AFKRunnable(Map<UUID, Location> locs) {
		this.locs = locs;
	}

	@Override
	public void run() {
		List<UUID> tmp = new ArrayList<>();
		for (Player player : Bukkit.getOnlinePlayers()) {
			tmp.add(player.getUniqueId());
			if (locs.containsKey(player.getUniqueId())) {
				if (locs.get(player.getUniqueId()).getBlock().getLocation()
						.equals(player.getLocation().getBlock().getLocation()))
					AFKUtils.afk(player, true);
			}
			locs.put(player.getUniqueId(), player.getLocation());
		}

		List<UUID> tmp2 = new ArrayList<>();

		for (Entry<UUID, Location> entry : locs.entrySet()) {
			if (!tmp.contains(entry.getKey()))
				tmp2.add(entry.getKey());
		}
		for (UUID uid : tmp2) {
			locs.remove(uid);
		}
		
		Bukkit.getScheduler().runTaskLater(VanillaUtils.getPlugin(), new AFKRunnable(locs), 5*20);
	}

}
