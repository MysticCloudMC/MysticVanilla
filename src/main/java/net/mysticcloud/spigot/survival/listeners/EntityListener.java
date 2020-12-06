package net.mysticcloud.spigot.survival.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import net.mysticcloud.spigot.core.utils.CoreUtils;
import net.mysticcloud.spigot.core.utils.MysticPlayer;
import net.mysticcloud.spigot.core.utils.SpawnReason;
import net.mysticcloud.spigot.survival.MysticSurvival;
import net.mysticcloud.spigot.survival.utils.HomeUtils;
import net.mysticcloud.spigot.survival.utils.SurvivalUtils;
import net.mysticcloud.spigot.survival.utils.items.ItemUtils;

public class EntityListener implements Listener {

	public EntityListener(MysticSurvival plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onEntitySpawn(EntitySpawnEvent e) {
		if (e.getEntity() instanceof Monster) {
			MysticPlayer player = null;
			for (Entity entity : e.getEntity().getNearbyEntities(50, 50, 50)) {
				if (entity instanceof Player) {
					player = CoreUtils.getMysticPlayer(entity.getUniqueId());
					break;
				}
			}
			if (player == null) {
				e.setCancelled(true);
				return;
			}
			String color = "&f";
			int level = CoreUtils.getRandom().nextInt(7) + (player.getLevel() - 5);
			if (level < 1)
				level = 1;

			switch (e.getEntity().getType()) {
			case ZOMBIE:
				color = "&a";
				break;
			case SKELETON:
				color = "&c";
			case DROWNED:
				color = "&a";
				break;
			case CREEPER:
				color = "&2";
				break;
			case BLAZE:
				color = "&6";
				break;
			case ENDERMAN:
				color = "&5";
				break;
			case CAVE_SPIDER:
			case SPIDER:
				color = "&1";
				break;
			default:

				break;
			}
			String name = CoreUtils.colorize(
					"&7[" + level + "] " + color + "&l" + e.getEntity().getName().substring(0, 1).toUpperCase()
							+ e.getEntity().getName().substring(1, e.getEntity().getName().length()).toLowerCase());
			Monster mon = (Monster) e.getEntity();
			mon.setMetadata("level", new FixedMetadataValue(SurvivalUtils.getPlugin(), level));
			mon.setMaxHealth(mon.getHealth() + (player.getLevel() * 1.5));
			mon.setHealth(mon.getMaxHealth());
			e.getEntity().setCustomName(name);

		}
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {

			if (((Player) e.getEntity()).getHealth() - e.getDamage() <= 0) {
				e.setCancelled(true);
				List<ItemStack> adds = new ArrayList<>();
				for (ItemStack i : ((Player) e.getEntity()).getInventory().getContents()) {
					if (i != null) {
						if (i.hasItemMeta()) {
							if (i.getItemMeta().hasLore()) {
								for (String s : i.getItemMeta().getLore()) {
									if (ChatColor.stripColor(s).equalsIgnoreCase("Soulbound")) {
										adds.add(i);
										continue;
									}
								}
							}
						}
						if (!adds.contains(i))
							e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), i);
					}

				}
				((Player) e.getEntity()).getInventory().clear();
				e.setCancelled(true);
				Bukkit.getScheduler().runTaskLater(SurvivalUtils.getPlugin(), new Runnable() {
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

	@EventHandler
	public void onEntityDeath(EntityDeathEvent e) {
		if (e.getEntity() instanceof Monster && e.getEntity().getKiller() != null
				&& e.getEntity().hasMetadata("level")) {
			int level = (int) e.getEntity().getMetadata("level").get(0).value();
			CoreUtils.getMysticPlayer(e.getEntity().getKiller())
					.gainXP(CoreUtils.getMoneyFormat(((double) level / 100) * CoreUtils.getRandom().nextInt(25)));
			// Drops?

			ItemUtils.handleDrops(level, e.getEntity().getLocation());
		}
	}
}
