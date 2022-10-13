package me.korbsti.netuno.currency;

import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import me.korbsti.netuno.Main;
import me.korbsti.netuno.mysql.DatabaseManager;
import net.dv8tion.jda.api.entities.Guild;

public class Timer {
	public static void timer() {
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		Runnable toRun = new Runnable() {
			public void run() {
				for (Map.Entry<String, String> set : Main.userNovaMultiplier.entrySet()) {
					String key = set.getKey();
					if (Double.valueOf(Main.userNovaMultiplier.get(key)) != 0.0) {
						double newBalance = Double.valueOf(Main.userBalance.get(key))
								+ Double.valueOf(Main.userNovaMultiplier.get(key));
						DatabaseManager.updateUserBalance(Main.userID.get(key), String.valueOf(newBalance));
						Main.userBalance.put(key, String.valueOf(newBalance));
					}
				}
				for(Guild guild : Main.builder.getGuilds()) {
					guild.loadMembers();
				}

			}
		};

		ScheduledFuture<?> handle = scheduler.scheduleAtFixedRate(toRun, 1, 1, TimeUnit.HOURS);
	}
}
