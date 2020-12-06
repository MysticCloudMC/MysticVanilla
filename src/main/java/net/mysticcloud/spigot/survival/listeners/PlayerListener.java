package net.mysticcloud.spigot.survival.listeners;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.metadata.FixedMetadataValue;

import net.mysticcloud.spigot.core.Main;
import net.mysticcloud.spigot.core.utils.CoreUtils;
import net.mysticcloud.spigot.survival.MysticSurvival;
import net.mysticcloud.spigot.survival.runnables.SeekerArrowRunnable;
import net.mysticcloud.spigot.survival.utils.SurvivalPlayer;
import net.mysticcloud.spigot.survival.utils.SurvivalUtils;
import net.mysticcloud.spigot.survival.utils.items.Item;
import net.mysticcloud.spigot.survival.utils.items.ItemUtils;
import net.mysticcloud.spigot.survival.utils.perks.Perks;

public class PlayerListener implements Listener {

	public PlayerListener(MysticSurvival plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		SurvivalUtils.getSurvivalPlayer(e.getPlayer()).save();
	}

	@EventHandler
	public void onItemDrop(PlayerDropItemEvent e) {
		if (e.getItemDrop().getItemStack().hasItemMeta()) {
			if (e.getItemDrop().getItemStack().getItemMeta().hasLore()) {
				for (String s : e.getItemDrop().getItemStack().getItemMeta().getLore()) {
					if (ChatColor.stripColor(s).equalsIgnoreCase("Soulbound")) {
						e.setCancelled(true);
					}
				}
			}
		}
	}

	

	@EventHandler
	public void onProjectileShoot(ProjectileLaunchEvent e) {
		if (e.getEntity().getShooter() instanceof Player) {
			if (SurvivalUtils.isTargeting(((Player) e.getEntity().getShooter()).getUniqueId())) {
				e.getEntity().setMetadata("targeting", new FixedMetadataValue(SurvivalUtils.getPlugin(),
						((Player) e.getEntity().getShooter()).getUniqueId()));
			}
			SurvivalPlayer player = SurvivalUtils.getSurvivalPlayer((Player) e.getEntity().getShooter());
			if (ItemUtils
					.hasSeekers(Bukkit.getPlayer(player.getPlayer().getUUID()).getEquipment().getItemInMainHand())) {

				try {
					LivingEntity target = player.getPerk(Perks.ARCHERY_SEEKER).getTarget();
					if (target == null || target.isDead()) {
						String s = "";
						for (String a : player.getPerk(Perks.ARCHERY_SEEKER).getRequirements()) {
							s = s == "" ? a : s + ", " + a;
						}
						player.sendMessage("You don't meet the requirements to activate this perk: " + s);
					}
					ItemUtils.removeSeeker(
							Bukkit.getPlayer(player.getPlayer().getUUID()).getEquipment().getItemInMainHand());

					Bukkit.getScheduler().runTaskLater(SurvivalUtils.getPlugin(),
							new SeekerArrowRunnable((Arrow) e.getEntity(), target), 1);
				} catch (NullPointerException ex) {
					String s = "";
					for (String a : player.getPerk(Perks.ARCHERY_SEEKER).getRequirements()) {
						s = s == "" ? a : s + ", " + a;
					}
					player.sendMessage("You don't meet the requirements to activate this perk: " + s);
				}
			}
			if (((Player) e.getEntity().getShooter()).getItemInHand().getItemMeta().hasLore()) {
				List<String> lore = ((Player) e.getEntity().getShooter()).getItemInHand().getItemMeta().getLore();
				for (String s : lore) {
					if (ChatColor.stripColor(s).contains("Fire Damage:")) {
						e.getEntity().setMetadata("fire", new FixedMetadataValue(Main.getPlugin(),
								Integer.parseInt(ChatColor.stripColor(s).split(":")[1].replaceAll(" ", ""))));
					}
					if (ChatColor.stripColor(s).contains("Frost Damage:")) {
						e.getEntity().setMetadata("frost", new FixedMetadataValue(Main.getPlugin(),
								Integer.parseInt(ChatColor.stripColor(s).split(":")[1].replaceAll(" ", ""))));
					}
					if (ChatColor.stripColor(s).contains("Vampirism Chance:")) {
						e.getEntity().setMetadata("vampirism", new FixedMetadataValue(Main.getPlugin(),
								Integer.parseInt(ChatColor.stripColor(s).split(":")[1].replaceAll(" ", ""))));
					}
				}
			}
		}
	}

}
