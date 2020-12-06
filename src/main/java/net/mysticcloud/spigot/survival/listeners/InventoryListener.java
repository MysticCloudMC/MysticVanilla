package net.mysticcloud.spigot.survival.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import net.mysticcloud.spigot.core.utils.GUIManager;
import net.mysticcloud.spigot.survival.MysticSurvival;
import net.mysticcloud.spigot.survival.utils.Division;
import net.mysticcloud.spigot.survival.utils.SurvivalPlayer;
import net.mysticcloud.spigot.survival.utils.SurvivalUtils;
import net.mysticcloud.spigot.survival.utils.inventories.InventoryUtils;
import net.mysticcloud.spigot.survival.utils.inventories.SubSkill;
import net.mysticcloud.spigot.survival.utils.items.Armor;
import net.mysticcloud.spigot.survival.utils.items.Item;
import net.mysticcloud.spigot.survival.utils.items.ItemUtils;
import net.mysticcloud.spigot.survival.utils.items.Weapon;
import net.mysticcloud.spigot.survival.utils.perks.Perks;

public class InventoryListener implements Listener {

	public InventoryListener(MysticSurvival plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onCraft(CraftItemEvent e) {

		if (!ItemUtils.getWeaponType(e.getRecipe().getResult().getType()).equals("Stick")) {
			SurvivalPlayer player = SurvivalUtils.getSurvivalPlayer((Player) e.getWhoClicked());
			Item i = new Weapon(e.getRecipe().getResult().getType(), player.getSubSkill(SubSkill.CRAFTING));
			player.gainSubSkill(SubSkill.CRAFTING, 1);

			e.getClickedInventory().setItem(e.getSlot(), i.getItem());

		}
		if (!ItemUtils.getArmorType(e.getRecipe().getResult().getType()).equals("Stick")) {
			SurvivalPlayer player = SurvivalUtils.getSurvivalPlayer((Player) e.getWhoClicked());
			Armor i = new Armor(e.getRecipe().getResult().getType(), player.getSubSkill(SubSkill.CRAFTING));
			player.gainSubSkill(SubSkill.CRAFTING, 1);

			e.getClickedInventory().setItem(e.getSlot(), i.getItem());

		}
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		if (GUIManager.getOpenInventory((Player) e.getPlayer()).equalsIgnoreCase("CraftingBench")) {
			InventoryUtils.dropItems(SurvivalUtils.getSurvivalPlayer((Player) e.getPlayer()), e.getInventory());
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {

		if (GUIManager.getOpenInventory((Player) e.getWhoClicked()).contains("Perks")) {
			e.setCancelled(true);
			if (e.getCurrentItem().getType().equals(Material.DIAMOND)) {
				ItemStack i = e.getCurrentItem();
				if (!i.hasItemMeta())
					return;
				Perks perk = Perks.getPerk(i.getItemMeta().getDisplayName().toUpperCase());
				SurvivalUtils.getSurvivalPlayer((Player) e.getWhoClicked()).activatePerk(perk);
			}
		}
		if (GUIManager.getOpenInventory((Player) e.getWhoClicked()).equalsIgnoreCase("Perk Menu")) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null)
				return;
			if (e.getCurrentItem().getType().equals(Material.AIR)
					|| e.getCurrentItem().getType().equals(Material.GRAY_STAINED_GLASS_PANE))
				return;
			Bukkit.broadcastMessage(e.getCurrentItem().getType().name());
			for (Division div : Division.values()) {
				if (e.getCurrentItem().getType().equals(div.getGUIItem())) {
					InventoryUtils.openPerksMenu((Player) e.getWhoClicked(), div);
					return;
				}
			}
		}

		if (GUIManager.getOpenInventory((Player) e.getWhoClicked()).equalsIgnoreCase("Menu")) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null)
				return;
			if (e.getCurrentItem().getType().equals(Material.AIR))
				return;
			if (e.getCurrentItem().getType().equals(Material.CRAFTING_TABLE)) {
				InventoryUtils.openCraftingBench((Player) e.getWhoClicked());
			}
			if (e.getCurrentItem().getType().equals(Material.NETHER_STAR)) {
				InventoryUtils.openPerksMenu((Player) e.getWhoClicked());
			}
		}
		if (GUIManager.getOpenInventory((Player) e.getWhoClicked()).equalsIgnoreCase("CraftingBench")) {
			if (e.getCurrentItem() == null)
				return;
			if (e.getCurrentItem().getType().equals(Material.AIR))
				return;
			if (e.getCurrentItem().getType().equals(Material.BLACK_STAINED_GLASS_PANE)) {
				e.setCancelled(true);
				return;
			}
			if (e.getCurrentItem().getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
				e.setCancelled(true);
				InventoryUtils.craft(SurvivalUtils.getSurvivalPlayer((Player) e.getWhoClicked()),
						e.getClickedInventory());
				return;
			}

		}
	}

}
