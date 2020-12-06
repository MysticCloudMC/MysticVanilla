package net.mysticcloud.spigot.survival.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.mysticcloud.spigot.survival.MysticSurvival;
import net.mysticcloud.spigot.survival.utils.Enhancement;
import net.mysticcloud.spigot.survival.utils.Spell;
import net.mysticcloud.spigot.survival.utils.Spells;
import net.mysticcloud.spigot.survival.utils.SurvivalPlayer;
import net.mysticcloud.spigot.survival.utils.SurvivalUtils;
import net.mysticcloud.spigot.survival.utils.spells.FireballSpell;
import net.mysticcloud.spigot.survival.utils.spells.FlameSpell;
import net.mysticcloud.spigot.survival.utils.spells.HealSpell;
import net.mysticcloud.spigot.survival.utils.spells.InvisibilitySpell;
import net.mysticcloud.spigot.survival.utils.spells.LightningSpell;
import net.mysticcloud.spigot.survival.utils.spells.TeleportSpell;

public class PlayerInteractListener implements Listener {

	public PlayerInteractListener(MysticSurvival plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if ((e.getPlayer()).getEquipment().getItemInMainHand() == null)
				return;
			if (!(e.getPlayer()).getEquipment().getItemInMainHand().hasItemMeta())
				return;
			if (!(e.getPlayer()).getEquipment().getItemInMainHand().getItemMeta().hasLore())
				return;
			if (!e.getPlayer().getEquipment().getItemInMainHand().getType().equals(Material.STICK))
				return;

			ItemStack s = (e.getPlayer()).getEquipment().getItemInMainHand();
//			List<String> lore = new ArrayList<>();
			for (String a : s.getItemMeta().getLore()) {
				if (ChatColor.stripColor(a).contains(Spells.TELEPORT.getStrippedName() + " Spell")) {
					Spell spell = new TeleportSpell(SurvivalUtils.getSurvivalPlayer(e.getPlayer()), e.getPlayer().getTargetBlock(null, 200).getLocation()
							.add(0, 1, 0).setDirection(e.getPlayer().getEyeLocation().getDirection()));
					SurvivalPlayer player = SurvivalUtils.getSurvivalPlayer(e.getPlayer());
					if (player.getMana() >= spell.getCost()) {
						spell.activate();
						player.useMana(spell.getCost());
					} else {
						player.useMana(0);
					}
				}
				if (ChatColor.stripColor(a).contains(Spells.HEAL.getStrippedName() + " Spell")) {
					Spell spell = new HealSpell(SurvivalUtils.getSurvivalPlayer(e.getPlayer()));
					SurvivalPlayer player = SurvivalUtils.getSurvivalPlayer(e.getPlayer());
					if (player.getMana() >= spell.getCost()) {
						spell.activate();
						player.useMana(spell.getCost());
					} else {
						player.useMana(0);
					}
				}
				if (ChatColor.stripColor(a).contains(Spells.FIREBALL.getStrippedName() + " Spell")) {
					Spell spell = new FireballSpell(SurvivalUtils.getSurvivalPlayer(e.getPlayer()));
					SurvivalPlayer player = SurvivalUtils.getSurvivalPlayer(e.getPlayer());
					if (player.getMana() >= spell.getCost()) {
						spell.activate();
						player.useMana(spell.getCost());
					} else {
						player.useMana(0);
					}
				}

				if (ChatColor.stripColor(a).contains(Spells.FLAME.getStrippedName() + " Spell")) {
					Spell spell = new FlameSpell(SurvivalUtils.getSurvivalPlayer(e.getPlayer()));
					SurvivalPlayer player = SurvivalUtils.getSurvivalPlayer(e.getPlayer());
					if (player.getMana() >= spell.getCost()) {
						spell.activate();
						player.useMana(spell.getCost());
					} else {
						player.useMana(0);
					}
				}

				if (ChatColor.stripColor(a).contains(Spells.LIGHTNING.getStrippedName() + " Spell")) {
					Spell spell = new LightningSpell(SurvivalUtils.getSurvivalPlayer(e.getPlayer()));
					SurvivalPlayer player = SurvivalUtils.getSurvivalPlayer(e.getPlayer());
					if (player.getMana() >= spell.getCost()) {
						spell.activate();
						player.useMana(spell.getCost());
					} else {
						player.useMana(0);
					}
				}
				if (ChatColor.stripColor(a).contains(Spells.INVISIBILITY.getStrippedName() + " Spell")) {
					Spell spell = new InvisibilitySpell(SurvivalUtils.getSurvivalPlayer(e.getPlayer()));
					SurvivalPlayer player = SurvivalUtils.getSurvivalPlayer(e.getPlayer());
					if (player.getMana() >= spell.getCost()) {
						spell.activate();
						player.useMana(spell.getCost());
					} else {
						player.useMana(0);
					}
				}

//				lore.add(a);
			}
			return;
//			ItemMeta im = s.getItemMeta();
//			im.setLore(lore);
//			s.setItemMeta(im);

		}
		if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if ((e.getPlayer()).getEquipment().getItemInMainHand() != null) {
				if ((e.getPlayer()).getEquipment().getItemInMainHand().hasItemMeta()) {
					if ((e.getPlayer()).getEquipment().getItemInMainHand().getItemMeta().hasLore()) {
						ItemStack s = (e.getPlayer()).getEquipment().getItemInMainHand();
						List<String> lore = new ArrayList<>();
						for (String a : s.getItemMeta().getLore()) {
							if (a.contains(":")) {
								if (ChatColor.stripColor(a).split(":")[0].equals("Power Attack")) {
									if (ChatColor.stripColor(a).split(": ")[1].equalsIgnoreCase("false")) {
										lore.add(Enhancement.POWER_ATTACK.getName() + "true");
										continue;
									} else {
										lore.add(Enhancement.POWER_ATTACK.getName() + "false");
										continue;
									}
								}
							}
							lore.add(a);
						}
						ItemMeta im = s.getItemMeta();
						im.setLore(lore);
						s.setItemMeta(im);
					}
				}
			}
		}
	}

	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
		if (e.getPlayer().getEquipment().getItemInMainHand() == null)
			return;
		if (e.getPlayer().getEquipment().getItemInMainHand().getType().equals(Material.AIR))
			return;

		ItemStack i = e.getPlayer().getEquipment().getItemInMainHand();

		if (i.getType().equals(Material.STICK)) {
			if (ChatColor.stripColor(i.getItemMeta().getDisplayName()).equalsIgnoreCase("Targeting Wand")) {
				SurvivalUtils.getSurvivalPlayer(e.getPlayer()).target((LivingEntity) e.getRightClicked());
			}
		}
	}
}
