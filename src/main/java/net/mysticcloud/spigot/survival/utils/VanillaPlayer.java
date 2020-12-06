package net.mysticcloud.spigot.survival.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import net.mysticcloud.spigot.core.utils.MysticPlayer;

public class VanillaPlayer {
	

	MysticPlayer player;
	File file;

	protected VanillaPlayer(MysticPlayer player) {
		this.player = player;

		file = new File(VanillaUtils.getPlugin().getDataFolder().getPath() + "/players/" + player.getUUID() + ".yml");
		if (!file.exists()) {
//			file.mkdirs();
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		FileConfiguration fc = YamlConfiguration.loadConfiguration(file);
	}

	public void sendMessage(String key, String message) {
		player.sendMessage(key, message);
	}

	public void sendMessage(String message) {
		sendMessage("vanilla", message);
	}

	public int getLevel() {
		return player.getLevel();
	}

	public void save() {
		FileConfiguration fc = YamlConfiguration.loadConfiguration(file);

		try {
			fc.save(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public MysticPlayer getPlayer() {
		return player;
	}

}
