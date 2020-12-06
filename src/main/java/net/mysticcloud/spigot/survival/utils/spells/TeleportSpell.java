package net.mysticcloud.spigot.survival.utils.spells;

import org.bukkit.Location;
import org.bukkit.Particle;

import net.mysticcloud.spigot.core.utils.particles.ParticleFormat;
import net.mysticcloud.spigot.core.utils.particles.formats.RandomFormat;
import net.mysticcloud.spigot.survival.utils.Spell;
import net.mysticcloud.spigot.survival.utils.SurvivalPlayer;

public class TeleportSpell extends Spell {
	
	
	public TeleportSpell(SurvivalPlayer caster, Location loc) {
		super(caster);
		this.loc = loc;
		cost = 50;
	}
	
	@Override
	public void activate() {
		ParticleFormat format = new RandomFormat();
		format.particle(Particle.SPELL_WITCH);
		for(int i=0;i!=60;i++) {
			format.setLifetime(i);
			format.display(getCasterEntity().getLocation());
			format.display(loc);
		}
		getCasterEntity().teleport(loc);
	}

}
