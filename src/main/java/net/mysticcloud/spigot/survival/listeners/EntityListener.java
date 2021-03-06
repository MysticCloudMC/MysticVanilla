package net.mysticcloud.spigot.survival.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import net.mysticcloud.spigot.combatlogger.utils.CombatUtils;
import net.mysticcloud.spigot.core.utils.CoreUtils;
import net.mysticcloud.spigot.core.utils.SpawnReason;
import net.mysticcloud.spigot.core.utils.warps.HomeUtils;
import net.mysticcloud.spigot.survival.MysticVanilla;
import net.mysticcloud.spigot.survival.utils.VanillaUtils;

public class EntityListener implements Listener {

	public EntityListener(MysticVanilla plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityAttackEntity(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player) {
			if (e.getEntity().hasMetadata("damager"))
				e.getEntity().removeMetadata("damager", VanillaUtils.getPlugin());
			e.getEntity().setMetadata("damager",
					new FixedMetadataValue(VanillaUtils.getPlugin(),
							(e.getDamager() instanceof Projectile) ? ((Projectile) e.getDamager()).getShooter()
									: e.getDamager()));
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
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

				String pre = "&7";
				String pl = "&3";
				String message = "";
				Entity en = (e.getEntity().hasMetadata("damager") && e.getEntity().getMetadata("damager").size() > 0)
						? (Entity) e.getEntity().getMetadata("damager").get(0).value()
						: null;
				String type = (en != null
						? (en instanceof Player) ? ((Player) en).getName()
								: en.getType().name().substring(0, 1)
										+ en.getType().name().substring(1, en.getType().name().length()).toLowerCase()
						: "");
				String who = type;
				if (en != null)
					if (!(en instanceof Player))
						who = "a " + who;

				boolean display = true;

				switch (e.getCause()) {
				case ENTITY_ATTACK:
					display = false;
					Bukkit.broadcastMessage(CoreUtils
							.colorize(pl + e.getEntity().getName() + pre + " was slain by " + pl + who + pre + "."));
					break;
				case PROJECTILE:
					message = pl + e.getEntity().getName() + pre + " was shot and killed from %x blocks away by &c"
							+ who + pre + "!";
					message = message.replaceAll("%x", pl
							+ ((int) (Math.sqrt(Math.pow(en.getLocation().getX() - e.getEntity().getLocation().getX(),
									2) + Math.pow(en.getLocation().getZ() - e.getEntity().getLocation().getZ(), 2))))
							+ pre);
					Bukkit.broadcastMessage(CoreUtils.colorize(message));
					display = false;
					break;

				case BLOCK_EXPLOSION:
				case ENTITY_EXPLOSION:
					Bukkit.broadcastMessage(CoreUtils.colorize(pl + e.getEntity().getName() + pre + " blew up!"));
					message = pre + "You were exploded by " + pl + who + pre + "! Watch out for explosives. :)";
					break;
				case DROWNING:
					Bukkit.broadcastMessage(CoreUtils.colorize(pl + e.getEntity().getName() + pre + " drowned."));
					message = pre + "You drowned. (You don't have gills, in-case you missed the memo)";
					break;
				case FIRE:
				case HOT_FLOOR:
				case LAVA:
				case MELTING:
				case FIRE_TICK:
					display = false;
					Bukkit.broadcastMessage(
							CoreUtils.colorize(pl + e.getEntity().getName() + pre + " burned to a crisp."));
					break;
				case FALL:
					message = pl + e.getEntity().getName() + pre + " fell to their death from %x blocks up.";
					message = message.replaceAll("%x", pl + ((int) e.getEntity().getFallDistance()) + pre);
					Bukkit.broadcastMessage(CoreUtils.colorize(message));
					display = false;
					break;
				case FLY_INTO_WALL:
					Vector vel = e.getEntity().getVelocity();
					message = pl + e.getEntity().getName() + pre + " smashed into a wall at %x blocks per second.";
					message = message.replaceAll("%x",
							pl + ((int) (Math.sqrt(Math.pow(vel.getX(), 2) + Math.pow(vel.getY(), 2)) * 10)) + pre);
					Bukkit.broadcastMessage(CoreUtils.colorize(message));
					display = false;
					break;
				case MAGIC:
				case POISON:
					display = false;
					Bukkit.broadcastMessage(CoreUtils.colorize(
							pl + e.getEntity().getName() + pre + " was put under a spell by " + pl + who + pre + "!"));
					break;

				case CONTACT:
				case CRAMMING:
				case CUSTOM:
				default:
					message = pre + "Careful! You died!";
					break;
				}

				message = pre + message;
				if (display)
					e.getEntity().sendMessage(CoreUtils.colorize(message));

				CombatUtils.removeFromCombat(e.getEntity().getUniqueId());

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