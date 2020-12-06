package net.mysticcloud.spigot.survival.utils.spells;

import org.bukkit.Particle;

import net.mysticcloud.spigot.core.utils.particles.formats.RandomFormat;
import net.mysticcloud.spigot.survival.utils.Spell;
import net.mysticcloud.spigot.survival.utils.SurvivalPlayer;

public class HealSpell extends Spell {
	
	
	public HealSpell(SurvivalPlayer caster) {
		super(caster);
		cost = 100;
	}
	
	@Override
	public void activate() {
		getCasterEntity().setHealth(getCasterEntity().getMaxHealth());
		RandomFormat format = new RandomFormat();
		format.particle(Particle.COMPOSTER);
		for(int i=0;i!=20;i++) {
			format.setLifetime(i);
			format.display(getCasterEntity().getLocation());
		}
	}

}
