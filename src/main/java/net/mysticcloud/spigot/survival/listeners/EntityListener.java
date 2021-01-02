package net.mysticcloud.spigot.survival.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

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
	public void onEntityAttackEntity(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player) {
			Bukkit.broadcastMessage("Damager: " + (e.getDamager() instanceof Projectile ? "Projectile" : "Entity"));
			e.getEntity().setMetadata("damager",
					new FixedMetadataValue(VanillaUtils.getPlugin(),
							(e.getDamager() instanceof Projectile) ? ((Projectile) e.getDamager()).getShooter()
									: e.getDamager()));
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
						if (!adds.contains(i))
							e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), i);
					}

				}
				((Player) e.getEntity()).getInventory().clear();
				e.setCancelled(true);

				String pre = "&e";
				String message = "";
				Entity en = (Entity) e.getEntity().getMetadata("damager").get(0).value();
				String type = (en instanceof Player) ? ((Player) en).getName()
						: en.getType().name().substring(0, 1)
								+ en.getType().name().substring(1, en.getType().name().length()).toLowerCase();
				String who = type;
				if (!(en instanceof Player))
					who = "a " + who;

				switch (e.getCause()) {
				case ENTITY_ATTACK:
					message = "You were slain by &c" + who + pre + ".";
					break;
				case PROJECTILE:
					message = "You were shot and killed from %x blocks away by &c" + who + pre + "!";
					message = message.replaceAll("%x",
							"&c" + ((int) (Math.sqrt(Math.pow(e.getEntity().getLocation().getX()
									- ((Entity) e.getEntity().getMetadata("damager").get(0).value()).getLocation()
											.getX(),
									2)))) + pre);
					break;
				case BLOCK_EXPLOSION:
				case ENTITY_EXPLOSION:
					message = "You were exploded by &c" + who + pre + "! Watch out for explosives. :)";
					break;
				case DROWNING:
					message = "You drowned. (You don't have gills, in-case you missed the memo)";
					break;
				case FIRE:
				case HOT_FLOOR:
				case LAVA:
				case MELTING:
				case FIRE_TICK:
					message = "You burned to a crisp!";
					break;
				case FALL:
					message = "You fell to your death from %x blocks up.";
					message = message.replaceAll("%x", "&c" + ((int) e.getEntity().getFallDistance()) + pre);
					break;
				case FLY_INTO_WALL:
					Vector vel = e.getEntity().getVelocity();
					message = "You smashed into a wall at %x blocks per second.";
					message = message.replaceAll("%x",
							"&c" + ((int) (Math.sqrt(Math.pow(vel.getX(), 2) + Math.pow(vel.getY(), 2)) * 10)) + pre);
					break;
				case POISON:
					message = "You were poisoned by &c" + who + pre + "!";
					break;
				case CONTACT:
				case CRAMMING:
				case CUSTOM:
				default:
					message = "Careful! You died!";
					break;
				}

				message = pre + message;
				e.getEntity().sendMessage(CoreUtils.colorize(message));

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