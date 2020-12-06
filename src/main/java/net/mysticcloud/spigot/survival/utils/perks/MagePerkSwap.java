package net.mysticcloud.spigot.survival.utils.perks;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;

import net.mysticcloud.spigot.core.utils.CoreUtils;
import net.mysticcloud.spigot.core.utils.particles.ParticleFormat;
import net.mysticcloud.spigot.core.utils.particles.formats.DoubleHelixFormat;

public class MagePerkSwap extends MagePerk {

	public MagePerkSwap(UUID uid) {
		super(uid);
		reqs = new String[] { "Target (LivingEntity)" };
		ready = false;
	}

	@Override
	public void setTarget(LivingEntity target) {
		this.target = target;
		ready = true;
	}

	@Override
	public String[] getRequirements() {
		List<String> req = new ArrayList<>();
		if (target == null) {
			req.add("Target (LivingEntity)");
		}
		String str[] = new String[req.size()];
		for (int j = 0; j < req.size(); j++)
			str[j] = req.get(j);
		return str;
	}

	@Override
	public void activate() {
		if(target.isDead()) {
			ready = false;
			target = null;
			getPlayer().sendMessage(CoreUtils.colorize("&cTarget died. You need to select a new one."));
			return;
		}
		Location loc = target.getLocation().clone();
		ParticleFormat format = new DoubleHelixFormat();
		format.particle(Particle.SPELL_WITCH);
		target.teleport(getPlayer());
		getPlayer().teleport(loc);
		for (int i = 0; i != 80; i++) {
			format.setLifetime(i);
			format.display(getPlayer().getLocation());
			format.display(loc);
		}
		
	}

}
