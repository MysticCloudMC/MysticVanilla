package net.mysticcloud.spigot.survival.utils.perks;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;

import net.mysticcloud.spigot.core.utils.particles.formats.RandomFormat;
import net.mysticcloud.spigot.survival.utils.SurvivalPlayer;
import net.mysticcloud.spigot.survival.utils.SurvivalUtils;

public class MagePerkManaBoost extends MagePerk {

	public MagePerkManaBoost(UUID uid) {
		super(uid);
		reqs = new String[] {"Cooldown"};
	}

	@Override
	public void activate() {
		SurvivalPlayer player = SurvivalUtils.getSurvivalPlayer(getPlayer());
		int mana = player.getMaxMana();
		player.setMaxMana(mana+(15*(player.getLevel())));
		RandomFormat format = new RandomFormat();
		format.particle(Particle.REDSTONE);
		format.setDustOptions(new DustOptions(Color.fromRGB(30, 203, 225), 2));
		for (int i = 0; i != 50; i++) {
			format.setLifetime(i);
			format.display(getPlayer().getLocation());
		}
		ready = false;
		Bukkit.getScheduler().runTaskLater(SurvivalUtils.getPlugin(), new Runnable() {
			@Override
			public void run() {
				player.setMaxMana(mana);
				ready = true;
			}
		}, 20*20);
	}

}
