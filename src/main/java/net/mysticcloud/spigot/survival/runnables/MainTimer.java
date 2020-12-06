package net.mysticcloud.spigot.survival.runnables;

import org.bukkit.Bukkit;

import net.mysticcloud.spigot.core.Main;
import net.mysticcloud.spigot.core.runnables.DateChecker;
import net.mysticcloud.spigot.survival.utils.SurvivalPlayer;
import net.mysticcloud.spigot.survival.utils.SurvivalUtils;

public class MainTimer implements Runnable {

	int counter;

	public MainTimer(int counter) {
		this.counter = counter;
	}

	public MainTimer() {
		counter = 0;
	}

	@Override
	public void run() {
		for(SurvivalPlayer player : SurvivalUtils.getAllSurvivalPlayers()) {
			if(player.getMana() < player.getMaxMana() || player.getStamina() < player.getMaxStamina()) {
				player.replenishMana();
				player.replenishStamina();
				player.showStats();
			}
		}
		
		Bukkit.getScheduler().runTaskLater(SurvivalUtils.getPlugin(), new MainTimer(counter), 2);
	}

}
