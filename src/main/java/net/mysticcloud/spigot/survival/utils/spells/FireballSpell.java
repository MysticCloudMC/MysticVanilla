package net.mysticcloud.spigot.survival.utils.spells;

import org.bukkit.entity.DragonFireball;

import net.mysticcloud.spigot.survival.utils.Spell;
import net.mysticcloud.spigot.survival.utils.SurvivalPlayer;

public class FireballSpell extends Spell {

	public FireballSpell(SurvivalPlayer caster) {
		super(caster);
		cost = 20;
	}

	@Override
	public void activate() {
		DragonFireball f = getCasterEntity().getWorld().spawn(getCasterEntity().getEyeLocation().add(0, 1, 0), DragonFireball.class);
		f.setVelocity(getCasterEntity().getEyeLocation().getDirection());
	}

}
