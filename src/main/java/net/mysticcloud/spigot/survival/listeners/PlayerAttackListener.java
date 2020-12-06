package net.mysticcloud.spigot.survival.listeners;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.mysticcloud.spigot.core.Main;
import net.mysticcloud.spigot.core.utils.CoreUtils;
import net.mysticcloud.spigot.core.utils.particles.formats.RandomFormat;
import net.mysticcloud.spigot.survival.MysticSurvival;
import net.mysticcloud.spigot.survival.utils.Enhancement;
import net.mysticcloud.spigot.survival.utils.SurvivalUtils;
import net.mysticcloud.spigot.survival.utils.items.ItemUtils;
import net.mysticcloud.spigot.survival.utils.items.Weapon;

public class PlayerAttackListener implements Listener {

	public PlayerAttackListener(MysticSurvival plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onItemDamage(PlayerItemDamageEvent e) {
		if (!e.getItem().hasItemMeta())
			return;
		if (!e.getItem().getItemMeta().hasLore())
			return;
		net.mysticcloud.spigot.survival.utils.items.Item i = new net.mysticcloud.spigot.survival.utils.items.Item(
				e.getItem());
		i.damage(1);
		e.setDamage(0);

	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerAttack(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Projectile) {
			if (e.getDamager().hasMetadata("targeting") && e.getEntity() instanceof LivingEntity) {
				SurvivalUtils
						.getSurvivalPlayer(
								Bukkit.getPlayer((UUID) e.getDamager().getMetadata("targeting").get(0).value()))
						.target((LivingEntity) e.getEntity());
				SurvivalUtils.removeTargeter((UUID) e.getDamager().getMetadata("targeting").get(0).value());
			}
			if (e.getDamager().hasMetadata("fire")) {
				e.getEntity()
						.setFireTicks(Integer.parseInt("" + e.getDamager().getMetadata("fire").get(0).value()) * 20);
			}
			if (e.getDamager().hasMetadata("vampirism")) {
				if (CoreUtils.getRandom().nextInt(
						100) <= Integer.parseInt("" + e.getDamager().getMetadata("vampirism").get(0).value()) * 20) {
					((LivingEntity) ((Projectile) e.getDamager()).getShooter())
							.setHealth(((LivingEntity) ((Projectile) e.getDamager()).getShooter()).getHealth()
									+ (e.getDamage() / 2));
				}
			}
			if (e.getDamager().hasMetadata("frost")) {
				if (e.getEntity() instanceof LivingEntity) {
					((LivingEntity) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.SLOW,
							Integer.parseInt("" + e.getDamager().getMetadata("frost").get(0).value()) * 20, 1));
					RandomFormat format = new RandomFormat();
					format.particle(Particle.REDSTONE);
					format.setDustOptions(new DustOptions(Color.AQUA, 1));
					CoreUtils.particles.put(e.getEntity().getUniqueId(), format);
					Bukkit.getScheduler().runTaskLater(Main.getPlugin(), new Runnable() {

						@Override
						public void run() {
							CoreUtils.particles__remove.add(e.getEntity().getUniqueId());
						}

					}, Integer.parseInt("" + e.getDamager().getMetadata("frost").get(0).value()) * 20);
				}
			}
			return;
		}
		if (((Player) e.getDamager()).getEquipment().getItemInMainHand() == null)
			return;
		if (!((Player) e.getDamager()).getEquipment().getItemInMainHand().hasItemMeta())
			return;
		if (!((Player) e.getDamager()).getEquipment().getItemInMainHand().getItemMeta().hasLore())
			return;
		if (e.getEntity() instanceof LivingEntity) {
			if (e.getEntity() instanceof Player) {
				net.mysticcloud.spigot.survival.utils.items.Item i = new net.mysticcloud.spigot.survival.utils.items.Item(
						((Player) e.getEntity()).getEquipment().getItemInMainHand());
				if (i.hasEnhancement(Enhancement.DODGE)) {
					e.setCancelled(true);
					((Player) e.getEntity()).sendMessage(CoreUtils.colorize("&a&lDodge!"));
					if (e.getDamager() instanceof Player)
						((Player) e.getDamager()).sendMessage(CoreUtils.colorize("&c&lMiss!"));
				}
			}
			if (e.getDamager() instanceof Player) {
				net.mysticcloud.spigot.survival.utils.items.Item i = new net.mysticcloud.spigot.survival.utils.items.Item(
						((Player) e.getDamager()).getEquipment().getItemInMainHand());
				Player player = (Player) e.getDamager();
				if (!ItemUtils.getWeaponType(((Player) e.getDamager()).getEquipment().getItemInMainHand().getType())
						.equals("Stick")) {
					Weapon weapon = new Weapon(((Player) e.getDamager()).getEquipment().getItemInMainHand());
					if (SurvivalUtils.getSurvivalPlayer(player).getStamina() >= weapon.getWeight()) {
						SurvivalUtils.getSurvivalPlayer(player).useStamina(weapon.getWeight());
					} else {
						e.setCancelled(true);
						return;
					}
				}

				if (e.getEntity() instanceof Monster) {
					e.setDamage(
							(e.getDamage() + CoreUtils.getMysticPlayer(((Player) e.getDamager())).getLevel() * 0.3));
				}

				if (i.hasEnhancement(Enhancement.DISARM)) {
					if (((LivingEntity) e.getEntity()).getEquipment().getItemInMainHand() != null) {
						if (CoreUtils.getRandom().nextInt(100) < i.getEnhancementPower(Enhancement.DISARM)) {
							if (e.getEntity() instanceof Player) {
								Item it = e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(),
										((LivingEntity) e.getEntity()).getEquipment().getItemInMainHand());
								it.setPickupDelay(40);
								((LivingEntity) e.getEntity()).getEquipment().getItemInMainHand().setAmount(0);
							}

						}

					}
				}
				if (i.hasEnhancement(Enhancement.FIRE)) {
					e.getEntity().setFireTicks(i.getEnhancementPower(Enhancement.FIRE) * 20);
				}
				if (i.hasEnhancement(Enhancement.FROST)) {
					((LivingEntity) e.getEntity()).addPotionEffect(
							new PotionEffect(PotionEffectType.SLOW, i.getEnhancementPower(Enhancement.FROST) * 20, 1));
					RandomFormat format = new RandomFormat();
					format.particle(Particle.REDSTONE);
					format.setDustOptions(new DustOptions(Color.AQUA, 1));
					CoreUtils.particles.put(e.getEntity().getUniqueId(), format);
					Bukkit.getScheduler().runTaskLater(Main.getPlugin(), new Runnable() {

						@Override
						public void run() {
							CoreUtils.particles__remove.add(e.getEntity().getUniqueId());
						}

					}, i.getEnhancementPower(Enhancement.FROST) * 20);
				}
				if (i.hasEnhancement(Enhancement.VAMPIRISM)) {
					if (e.getEntity() instanceof Player) {
						if (CoreUtils.getRandom().nextInt(100) <= i.getEnhancementPower(Enhancement.VAMPIRISM)) {
							RandomFormat format = new RandomFormat();
							format.particle(Particle.REDSTONE);
							format.setDustOptions(new DustOptions(Color.RED, 2));
							for (int f = 0; f != 10; f++) {
								format.setLifetime(f);
								format.display(e.getEntity().getLocation());
							}

							try {
								((LivingEntity) e.getDamager())
										.setHealth(((LivingEntity) e.getDamager()).getHealth() + (e.getDamage() / 4));
							} catch (IllegalArgumentException ex) {
								((LivingEntity) e.getDamager())
										.setHealth(((LivingEntity) e.getDamager()).getMaxHealth());
							}

						}
					} else {
						RandomFormat format = new RandomFormat();
						format.particle(Particle.REDSTONE);
						format.setDustOptions(new DustOptions(Color.RED, 2));
						for (int f = 0; f != 10; f++) {
							format.setLifetime(f);
							format.display(e.getEntity().getLocation());
							format.display(e.getDamager().getLocation());
						}
						((LivingEntity) e.getDamager()).setHealth(((LivingEntity) e.getDamager()).getHealth()
								+ (e.getDamage() * (i.getEnhancementPower(Enhancement.VAMPIRISM)) / 10));
					}
				}
			}
		}

	}
}
