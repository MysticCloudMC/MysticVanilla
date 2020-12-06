package net.mysticcloud.spigot.survival.utils.spells;

import java.util.LinkedList;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import net.mysticcloud.spigot.core.utils.CoreUtils;
import net.mysticcloud.spigot.survival.utils.Spell;
import net.mysticcloud.spigot.survival.utils.Spells;
import net.mysticcloud.spigot.survival.utils.SurvivalPlayer;
import net.mysticcloud.spigot.survival.utils.inventories.SubSkill;

public class LightningSpell extends Spell {

	int scalar = 6;

	public LightningSpell(SurvivalPlayer caster) {
		super(caster);
		cost = 2;
	}

	@Override
	public void activate() {

		getCasterEntity().getWorld().playSound(getCasterEntity().getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 10,
				10);

		for (int f = 0; f != 2; f++) {
			LinkedList<Vector> points = new LinkedList<>();
			points.add(new Vector(getCasterEntity().getEyeLocation().getX(), getCasterEntity().getEyeLocation().getY(),
					getCasterEntity().getEyeLocation().getZ()));
			for (int i = 0; i != 10 * scalar; i++) {
				Vector point = points.get(points.size() - 1).clone();
				point.add(getCasterEntity().getEyeLocation().getDirection().multiply((double) 1 / scalar));
				point.setX(point.getX() + (CoreUtils.getRandom().nextDouble()
						* (CoreUtils.getRandom().nextBoolean() ? (double) 1 / scalar : -(double) 1 / scalar)));
				point.setY(point.getY() + (CoreUtils.getRandom().nextDouble()
						* (CoreUtils.getRandom().nextBoolean() ? (double) 1 / scalar : -(double) 1 / scalar)));
				point.setZ(point.getZ() + (CoreUtils.getRandom().nextDouble()
						* (CoreUtils.getRandom().nextBoolean() ? (double) 1 / scalar : -(double) 1 / scalar)));
				points.add(point);
			}
			for (Vector vec : points) {
				Location loc = new Location(getCasterEntity().getWorld(), vec.getX(), vec.getY(), vec.getZ());
				getCasterEntity().getWorld().spawnParticle(Particle.END_ROD, loc, 0);
				for (Entity e : getCasterEntity().getNearbyEntities(10, 10, 10)) {
					if (e instanceof LivingEntity && !getCasterEntity().equals(e)) {
						if (e.getLocation().distance(loc) <= 2) {
							((LivingEntity) e).damage(Math.floor(caster.getSpellSkill(Spells.LIGHTNING)) + 1,
									getCasterEntity());
							((LivingEntity) e)
									.setFireTicks((int) ((Math.floor(caster.getSpellSkill(Spells.LIGHTNING)) + 1) / 1));
							caster.gainSpellSkill(Spells.LIGHTNING, 0.005);
						}
					}
				}
			}
		}

	}

}
