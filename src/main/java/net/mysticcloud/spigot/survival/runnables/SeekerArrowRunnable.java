package net.mysticcloud.spigot.survival.runnables;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import net.mysticcloud.spigot.survival.utils.SurvivalUtils;

public class SeekerArrowRunnable implements Runnable {

	Arrow arrow;
	LivingEntity target;

	public SeekerArrowRunnable(Arrow arrow, LivingEntity target) {
		this.arrow = arrow;
		this.target = target;
	}

	public void run() {
		Vector newVelocity;
		double speed = this.arrow.getVelocity().length();
		if (this.arrow.isOnGround() || this.arrow.isDead() || this.target.isDead()) {
			return;
		}
		if (isDistance(this.arrow, this.target)) {
			return;
		}
		Vector toTarget = this.target.getLocation().clone().add(new Vector(0.0D, 0.5D, 0.0D))
				.subtract(this.arrow.getLocation()).toVector();
		Vector dirVelocity = this.arrow.getVelocity().clone().normalize();
		Vector dirToTarget = toTarget.clone().normalize();
		double angle = dirVelocity.angle(dirToTarget);
		double newSpeed = 0.9D * speed + 0.14D;
		if (this.target instanceof Player && this.arrow.getLocation().distance(this.target.getLocation()) < 8D) {
			Player player = (Player) this.target;
			if (player.isBlocking())
				newSpeed = speed * 0.6D;
		}
		if (angle < 0.12D) {
			newVelocity = dirVelocity.clone().multiply(newSpeed);
		} else {
			Vector newDir = dirVelocity.clone().multiply((angle - 0.12) / angle)
					.add(dirToTarget.clone().multiply(0.12 / angle));
			newDir.normalize();
			newVelocity = newDir.clone().multiply(newSpeed);
		}

		this.arrow.setVelocity(newVelocity.add(new Vector(0.0D, 0.03D, 0.0D)));

		Bukkit.getScheduler().runTaskLater(SurvivalUtils.getPlugin(), new SeekerArrowRunnable(arrow, target), 1);

	}

	public boolean isDistance(Arrow aw, LivingEntity le) {
		Location loc1 = aw.getLocation();
		Location loc2 = le.getLocation();
		double distance = loc1.distance(loc2);
		return (distance >= 60);
	}
}
