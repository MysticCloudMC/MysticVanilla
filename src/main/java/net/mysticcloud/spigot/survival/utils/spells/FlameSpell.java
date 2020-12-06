package net.mysticcloud.spigot.survival.utils.spells;

import org.bukkit.Particle;

import net.mysticcloud.spigot.core.utils.CoreUtils;
import net.mysticcloud.spigot.survival.utils.Spell;
import net.mysticcloud.spigot.survival.utils.SurvivalPlayer;

public class FlameSpell extends Spell {

	public FlameSpell(SurvivalPlayer caster) {
		super(caster);
		cost = 2;
	}

	@Override
	public void activate() {
		for (int i = 0; i != 20; i++) {
			getCasterEntity().getWorld().spawnParticle(Particle.FLAME, getCasterEntity().getEyeLocation().add(0, -0.5, 0), 0,
					getCasterEntity().getEyeLocation().getDirection().getX()
							+ ((CoreUtils.getRandom().nextDouble() * (CoreUtils.getRandom().nextBoolean() ? -1 : 1))/8),
							getCasterEntity().getEyeLocation().getDirection().getY()
							+ ((CoreUtils.getRandom().nextDouble() * (CoreUtils.getRandom().nextBoolean() ? -1 : 1))/8),
							getCasterEntity().getEyeLocation().getDirection().getZ()
							+ ((CoreUtils.getRandom().nextDouble() * (CoreUtils.getRandom().nextBoolean() ? -1 : 1))/8),
					CoreUtils.getRandom().nextDouble()/1.5);
		}
	}

}
