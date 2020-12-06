package net.mysticcloud.spigot.survival.utils.spells;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.mysticcloud.spigot.core.utils.particles.ParticleFormat;
import net.mysticcloud.spigot.core.utils.particles.formats.RandomFormat;
import net.mysticcloud.spigot.survival.utils.Spell;
import net.mysticcloud.spigot.survival.utils.SurvivalPlayer;
import net.mysticcloud.spigot.survival.utils.SurvivalUtils;

public class InvisibilitySpell extends Spell {

	public InvisibilitySpell(SurvivalPlayer caster) {
		super(caster);
		cost = 200;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void activate() {
		ParticleFormat format = new RandomFormat();
		format.particle(Particle.ASH);
		for (int i = 0; i != 100; i++) {
			format.setLifetime(i);
			format.display(getCasterEntity().getLocation());
		}
		if (getCasterEntity() instanceof Player) {
			Player player = (Player) getCasterEntity();
			player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,15*20,200), false);
//			for (Player p : Bukkit.getOnlinePlayers()) {
//				if(!p.equals(player))p.hidePlayer(player);
//			}
			Bukkit.getScheduler().runTaskLater(SurvivalUtils.getPlugin(), new Runnable() {

				@Override
				public void run() {
					for (int i = 0; i != 100; i++) {
						format.setLifetime(i);
						format.display(getCasterEntity().getLocation());
					}
				}
			}, 15 * 20);
		}

	}

}
