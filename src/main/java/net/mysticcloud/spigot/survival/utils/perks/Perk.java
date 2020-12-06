package net.mysticcloud.spigot.survival.utils.perks;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class Perk {

	UUID uid;
	boolean ready = true;
	String[] reqs = new String[] {};
	LivingEntity target = null;

	public Perk(UUID uid) {
		this.uid = uid;
	}
	
	public void setTarget(LivingEntity target) {
		this.target = target;
	}

	public void activate() {

	}

	public boolean canActivate() {
		return ready;
	}
	
	public String[] getRequirements() {
		return reqs;
	}

	protected Player getPlayer() {
		return (Bukkit.getPlayer(uid));
	}

	public LivingEntity getTarget() {
		return target;
	}

}
