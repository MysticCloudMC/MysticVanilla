package net.mysticcloud.spigot.survival.utils.afk;

import java.util.UUID;

import org.bukkit.Location;

public class AFKPacket {

	UUID uid;
	Location loc;
	boolean afk;

	public AFKPacket(UUID uid, Location loc, boolean afk) {
		this.uid = uid;
		this.loc = loc;
		this.afk = afk;
	}

	public boolean isAFK() {
		return afk;
	}

	public Location getLocation() {
		return loc;
	}

	public UUID getUUID() {
		return uid;
	}

}
