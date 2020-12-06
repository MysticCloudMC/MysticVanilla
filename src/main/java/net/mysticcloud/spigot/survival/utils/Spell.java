package net.mysticcloud.spigot.survival.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import net.mysticcloud.spigot.core.utils.particles.formats.RandomFormat;

public class Spell {
	
	protected SurvivalPlayer caster;
	protected int cost = 0;
	
	protected LivingEntity target;
	protected Location loc;
	
	public Spell(SurvivalPlayer caster) {
		this.caster = caster;
	}
	
	
	
	
	public int getCost() {
		return cost;
	}
	
	public Player getCasterEntity() {
		return Bukkit.getPlayer(caster.getPlayer().getUUID());
	}
	
	public void activate() {
		RandomFormat format = new RandomFormat();
		for(int i=0;i!=20;i++) {
			format.setLifetime(i);
			format.display(loc == null ? target.getLocation() : loc);
		}
	}
}
