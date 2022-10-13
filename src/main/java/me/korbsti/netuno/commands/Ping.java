package me.korbsti.netuno.commands;

import java.awt.Color;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import me.korbsti.netuno.Main;
import me.korbsti.netuno.mysql.DatabaseManager;
import me.korbsti.netuno.util.SendMessage;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

public class Ping {

	
	public static void ping(User user, Guild guild, Message message) {
		if (user.isBot() || !message.getContentDisplay().toLowerCase().startsWith(Main.guildPrefix.get(guild.getId()) + "ping")) return;
		Instant d = Instant.now();		
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		Runnable toRun = new Runnable() {
			public void run() {
				//EmbedBuilder eb = new EmbedBuilder();
				//eb.setColor(new Color(61, 237, 169));
				//eb.setDescription("Pong! "+ (Instant.now().toEpochMilli() - d.toEpochMilli())+ "ms");
				//System.out.println("Ran");
				//message.editMessage(eb.build()).queue();
				SendMessage.sendChannelMessage(message, "Pong! "+ (Instant.now().toEpochMilli() - d.toEpochMilli())+ "ms", null, false);
				
			}
		};

		ScheduledFuture<?> handle = scheduler.schedule(toRun, 1, TimeUnit.MILLISECONDS);
		
	}
}
