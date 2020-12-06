package net.mysticcloud.spigot.survival.utils.perks;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class WarriorPerkFury extends WarriorPerk {

	public WarriorPerkFury(UUID uid) {
		super(uid);
	}

	@Override
	public void activate() {
		Firework fw = (Firework) getPlayer().getWorld().spawnEntity(getPlayer().getLocation().add(0, 1, 0),
				EntityType.FIREWORK);
		FireworkMeta fwm = fw.getFireworkMeta();

		fwm.setPower(2);
		fwm.addEffect(FireworkEffect.builder().with(Type.BALL_LARGE).withColor(Color.RED).flicker(false).build());

		fw.setFireworkMeta(fwm);
		fw.detonate();

		getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 5 * 20, 20));

		for (Entity entity : getPlayer().getNearbyEntities(9, 9, 9)) {
			if (entity instanceof LivingEntity) {
				if (!entity.equals(getPlayer())) {
					Firework fw2 = (Firework) getPlayer().getWorld().spawnEntity(entity.getLocation().add(0, 1, 0),
							EntityType.FIREWORK);
					FireworkMeta fwm2 = fw2.getFireworkMeta();

					fwm2.setPower(2);
					fwm2.addEffect(
							FireworkEffect.builder().withColor(Color.RED).with(Type.BURST).flicker(false).build());

					fw2.setFireworkMeta(fwm2);
					fw2.detonate();
					((LivingEntity) entity).damage(2, getPlayer());
				}
			}
		}

	}

}
