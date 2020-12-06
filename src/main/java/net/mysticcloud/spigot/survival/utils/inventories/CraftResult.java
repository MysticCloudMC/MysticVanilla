package net.mysticcloud.spigot.survival.utils.inventories;

import org.bukkit.inventory.ItemStack;

public class CraftResult {
	
	private SubSkill skill;
	private ItemStack result;
	private int amount;
	
	public CraftResult(ItemStack result, SubSkill skill, int amount) {
		this.skill = skill;
		this.result = result;
		this.amount = amount;
	}
	
	public ItemStack result() {
		return result;
	}
	public SubSkill subSkill() {
		return skill;
	}

	public int amount() {
		return amount;
	}
	
	

}
