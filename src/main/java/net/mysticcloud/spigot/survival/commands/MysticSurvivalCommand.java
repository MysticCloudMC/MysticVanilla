package net.mysticcloud.spigot.survival.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.mysticcloud.spigot.core.commands.listeners.CommandTabCompleter;
import net.mysticcloud.spigot.core.utils.CoreUtils;
import net.mysticcloud.spigot.survival.MysticSurvival;
import net.mysticcloud.spigot.survival.utils.Division;
import net.mysticcloud.spigot.survival.utils.Enhancement;
import net.mysticcloud.spigot.survival.utils.SurvivalPlayer;
import net.mysticcloud.spigot.survival.utils.SurvivalUtils;
import net.mysticcloud.spigot.survival.utils.inventories.InventoryUtils;
import net.mysticcloud.spigot.survival.utils.items.Item;
import net.mysticcloud.spigot.survival.utils.items.ItemUtils;
import net.mysticcloud.spigot.survival.utils.perks.MagePerkSwap;
import net.mysticcloud.spigot.survival.utils.perks.Perks;

public class MysticSurvivalCommand implements CommandExecutor {

	public MysticSurvivalCommand(MysticSurvival plugin, String... cmd) {
		for (String comd : cmd) {
			PluginCommand com = plugin.getCommand(comd);
			com.setExecutor(this);
			com.setTabCompleter(new CommandTabCompleter());
		}

	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.hasPermission("mysticcloud.admin")) {
			if (args.length == 0) {
				sender.sendMessage(
						CoreUtils.colorize(CoreUtils.prefixes("admin") + "Usage: /msurvival <subcommand> [args]"));
				sender.sendMessage(CoreUtils.colorize(CoreUtils.prefixes("admin") + "Here is a list of sub-commands:"));
				sender.sendMessage(CoreUtils.colorize("&3giveRandomArmor [level]"));
				sender.sendMessage(CoreUtils.colorize("&3giveRandomWeapon [level]"));
				sender.sendMessage(CoreUtils.colorize("&3soulbind"));
				sender.sendMessage(CoreUtils.colorize("&3giveRandomBook"));
				sender.sendMessage(CoreUtils.colorize("&3enhance <enhancement> <level>"));

				return true;
			}
			if (args[0].equalsIgnoreCase("giveRandomBook")) {
				if (sender instanceof Player) {
					sender.sendMessage(CoreUtils.prefixes("admin") + "Generating random book...");
					((Player) sender).getInventory()
							.addItem(ItemUtils.bookGenerator(args.length == 2 ? Integer.parseInt(args[1])
									: CoreUtils.getMysticPlayer(((Player) sender)).getLevel()));
				}
			}
			if (args[0].equalsIgnoreCase("color")) {
				Bukkit.broadcastMessage(ChatColor.of(args[1]) + "Test!");
			}
			if (args[0].equalsIgnoreCase("perk")) {
				if (sender instanceof Player) {
					SurvivalPlayer player = SurvivalUtils.getSurvivalPlayer(((Player) sender));
					if (player.hasPerk(Perks.MAGE_SWAP)) {
						LivingEntity target = null;
						for (Entity entity : Bukkit.getPlayer(player.getPlayer().getUUID()).getNearbyEntities(50, 50,
								50)) {
							if (entity instanceof LivingEntity) {
								if (!entity.equals(Bukkit.getPlayer(player.getPlayer().getUUID())))
									if ((Bukkit.getPlayer(player.getPlayer().getUUID())).hasLineOfSight(entity)) {
										target = (LivingEntity) entity;
										break;
									}
							}
						}
						if (target != null) {
							MagePerkSwap perk = (MagePerkSwap) player.getPerk(Perks.MAGE_SWAP);
							perk.setTarget(target);
							player.activatePerk(Perks.MAGE_SWAP);
						}

					} else {
						player.addPerk(Perks.MAGE_SWAP, 0.3);
					}
				}
			}
			if (args[0].equalsIgnoreCase("wand")) {
				if (sender instanceof Player) {
					if (args.length == 2) {
						ItemStack wand = new ItemStack(Material.PAPER);
						ItemMeta wm = wand.getItemMeta();
						List<String> lore = new ArrayList<>();
						wm.setDisplayName(CoreUtils.colorize("&7Spell Paper"));
						if (args[1].equalsIgnoreCase("1")) {
							lore.add(CoreUtils.colorize("&5&lTeleportation &7Spell"));
						}
						if (args[1].equalsIgnoreCase("2")) {
							lore.add(CoreUtils.colorize("&a&lHeal &7Spell"));
						}
						if (args[1].equalsIgnoreCase("3")) {
							lore.add(CoreUtils.colorize("&6&lFireball &7Spell"));
						}
						if (args[1].equalsIgnoreCase("4")) {
							lore.add(CoreUtils.colorize("&1&lInvisibility &7Spell"));
						}
						if (args[1].equalsIgnoreCase("5")) {
							lore.add(CoreUtils.colorize("&c&lFlame &7Spell"));
						}
						if (args[1].equalsIgnoreCase("6")) {
							lore.add(CoreUtils.colorize("&f&o&lLightning &7Spell"));
						}
						if (args[1].equalsIgnoreCase("99")) {
							lore.add(CoreUtils.colorize("&7You can target"));
							lore.add(CoreUtils.colorize("&7an entity by right"));
							lore.add(CoreUtils.colorize("&7clicking them with"));
							lore.add(CoreUtils.colorize("&7this in your hand."));
						}
						wm.setLore(lore);
						wand.setItemMeta(wm);
						((Player) sender).getInventory().addItem(wand);
					}

				}
			}
			if (args[0].equalsIgnoreCase("division")) {
				if (sender instanceof Player) {
					SurvivalPlayer player = SurvivalUtils.getSurvivalPlayer(((Player) sender).getUniqueId());
					if (args.length == 2) {

						player.setDivision(Division.valueOf(args[1].toUpperCase()));
					}
					sender.sendMessage("Your division is: " + player.getDivision());

				}
			}

			if (args[0].equalsIgnoreCase("craft")) {
				if (sender instanceof Player) {
					InventoryUtils.openCraftingBench(((Player) sender));
				}
			}
			if (args[0].equalsIgnoreCase("enhance")) {
				if (sender instanceof Player) {
					if (args.length == 2) {
						String s = "";
						for (Enhancement en : Enhancement.values()) {
							s = (s == "" ? "&f" : "&7,&f ") + en.name().toLowerCase();
						}
						s = CoreUtils.colorize(s);
						sender.sendMessage(CoreUtils.prefixes("admin")
								+ "Unknown enhancement. Here's a list of enhancements: " + s);
					}
					if (args.length == 3) {
						if (args[1].equalsIgnoreCase("remove") || args[1].equalsIgnoreCase("delete")) {
							try {
								ItemStack hand = ((Player) sender).getEquipment().getItemInMainHand();
								Item item = new Item(hand);
								item.removeEnhancement(Enhancement.valueOf(args[2].toUpperCase()));
							} catch (IllegalArgumentException ex) {
								String s = "";
								for (Enhancement en : Enhancement.values()) {
									s = (s == "" ? "&f" : "&7,&f ") + en.name().toLowerCase();
								}
								s = CoreUtils.colorize(s);
								sender.sendMessage(CoreUtils.prefixes("admin")
										+ "Unknown enhancement. Here's a list of enhancements: " + s);
							}
						}
						try {
							try {
								ItemStack hand = ((Player) sender).getEquipment().getItemInMainHand();
								Item item = new Item(hand);
								item.enhance(Enhancement.valueOf(args[1].toUpperCase()), Integer.parseInt(args[2]));
								item.updateItem(hand);
							} catch (NumberFormatException ex) {
								sender.sendMessage(
										CoreUtils.prefixes("admin") + "You didn't use a number for the level.");
								sender.sendMessage(CoreUtils.prefixes("admin")
										+ "Usage: /msurvival enhance <enhancement> <level>");
							}
						} catch (IllegalArgumentException ex) {
							String s = "";
							for (Enhancement en : Enhancement.values()) {
								s = (s == "" ? "&f" : "&7,&f ") + en.name().toLowerCase();
							}
							s = CoreUtils.colorize(s);
							sender.sendMessage(CoreUtils.prefixes("admin")
									+ "Unknown enhancement. Here's a list of enhancements: " + s);
						}

					} else {
						sender.sendMessage(
								CoreUtils.prefixes("admin") + "Usage: /msurvival enhance <enhancement> <level>");
					}

				}
			}
			if (args[0].equalsIgnoreCase("giveRandomArmor")) {
				if (sender instanceof Player) {
					sender.sendMessage(CoreUtils.prefixes("admin") + "Generating random armor...");
					((Player) sender).getInventory()
							.addItem(ItemUtils.armorGenerator(args.length == 2 ? Integer.parseInt(args[1])
									: CoreUtils.getMysticPlayer(((Player) sender)).getLevel()));
				}
			}
			if (args[0].equalsIgnoreCase("giveRandomWeapon")) {
				sender.sendMessage(CoreUtils.prefixes("admin") + "Generating random weapon...");
				if (sender instanceof Player) {
					((Player) sender).getInventory()
							.addItem(ItemUtils.weaponGenerator(args.length == 2 ? Integer.parseInt(args[1])
									: (CoreUtils.getMysticPlayer(((Player) sender)).getLevel())));
				}
			}
			if (args[0].equalsIgnoreCase("soulbind")) {
				sender.sendMessage(CoreUtils.prefixes("admin") + "Binding...");
				if (sender instanceof Player && ((Player) sender).getItemInHand() != null) {
					ItemUtils.soulbind((Player) sender, ((Player) sender).getItemInHand());
				}
			}

		}

		return true;

	}

//	private String formatUsername(String username) {
//		return username.toLowerCase().endsWith("s") ? username + "'" : username + "'s";
//
//	}
}
