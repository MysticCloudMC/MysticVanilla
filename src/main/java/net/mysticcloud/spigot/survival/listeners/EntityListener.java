package net.mysticcloud.spigot.survival.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import net.mysticcloud.spigot.core.utils.CoreUtils;
import net.mysticcloud.spigot.core.utils.SpawnReason;
import net.mysticcloud.spigot.survival.MysticVanilla;
import net.mysticcloud.spigot.survival.utils.HomeUtils;
import net.mysticcloud.spigot.survival.utils.VanillaUtils;

public class EntityListener implements Listener {

	public EntityListener(MysticVanilla plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {

			if (((Player) e.getEntity()).getHealth() - e.getDamage() <= 0) {
				e.setCancelled(true);
				List<ItemStack> adds = new ArrayList<>();
				for (ItemStack i : ((Player) e.getEntity()).getInventory().getContents()) {
					if (i != null) {
						if (!adds.contains(i))
							e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), i);
					}

				}
				((Player) e.getEntity()).getInventory().clear();
				e.setCancelled(true);
				Bukkit.getScheduler().runTaskLater(VanillaUtils.getPlugin(), new Runnable() {
					public void run() {
						if (HomeUtils.getHomes(e.getEntity().getUniqueId()).size() > 0) {

							e.getEntity().teleport(
									HomeUtils.getHomes(((Player) e.getEntity()).getUniqueId()).get(0).location());
						} else {
							CoreUtils.teleportToSpawn((Player) e.getEntity(), SpawnReason.DEATH);
						}
						((Player) e.getEntity()).setHealth(((Player) e.getEntity()).getMaxHealth());
						((Player) e.getEntity()).setFoodLevel(20);
						for (ItemStack i : adds) {
							((Player) e.getEntity()).getInventory().addItem(i);
						}

					}
				}, 1);

			}

		}
	}

}