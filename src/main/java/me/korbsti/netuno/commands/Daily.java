package me.korbsti.netuno.commands;

import java.time.Instant;
import java.util.HashMap;
import java.util.Random;

import me.korbsti.netuno.Main;
import me.korbsti.netuno.mysql.DatabaseManager;
import me.korbsti.netuno.util.SendMessage;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

public class Daily {
	public static HashMap<String, Instant> daily = new HashMap<>();

	public static void daily(User user, Guild guild, Message message) {
		if (user.isBot()
				|| !message.getContentDisplay().toLowerCase().startsWith(Main.guildPrefix.get(guild.getId()) + "daily"))
			return;

		
		if (Main.isUserRegistered.get(user.getId()) == null) {
			DatabaseManager.createUserInfo(user.getId(), "0.0", "0.0", user.getName());
			Main.isUserRegistered.put(user.getId(), true);
			DatabaseManager.insertTrophiesTable(user.getId(), user.getName());
			EmbedBuilder eb = new EmbedBuilder();
			eb.setTitle("Registering into the database...");
			eb.setDescription("Successfully registered into the database");
			eb.setThumbnail("https://cdn.discordapp.com/attachments/811761680996368387/851620940320473118/check.png");
			message.getChannel().sendMessage(eb.build()).queue();
			
			for(String str : DatabaseManager.retrieveUsersInfo()) {
				String[] list = str.split("AaAaDqQqQqQqQq");
				Main.isUserRegistered.put(list[1], true);
				Main.userID.put(list[1], list[0]);
				Main.userBalance.put(list[1], list[2]);
				Main.userNovaMultiplier.put(list[1], list[3]);
			}
		}
		
		
		
		if (daily.get(user.getId()) == null) {
			Random randomGenerator = new Random();
			int x = randomGenerator.nextInt(200) + 1;
			DatabaseManager.updateUserBalance(Main.userID.get(user.getId()),
					String.valueOf(Double.valueOf(Main.userBalance.get(user.getId())) + x));
			Main.userBalance.put(user.getId(), String.valueOf(Double.valueOf(Main.userBalance.get(user.getId())) + x));
			
			
			EmbedBuilder eb = new EmbedBuilder();
			eb.setTitle("Collecting Daily Sapphires!");
			eb.setAuthor(user.getAsTag(), null, user.getAvatarUrl());
			eb.setDescription("\n≻─── ⋆✩⋆ ───≺\n"+ ""+"Adding " + x +"⌬ to your account balance!\n≻─── ⋆✩⋆ ───≺\n");
			message.getChannel().sendMessage(eb.build()).queue();
			
			daily.put(user.getId(), Instant.now());
		} else {
			boolean d = (Instant.now().toEpochMilli() - daily.get(user.getId()).toEpochMilli())/1000/60/60 < 24;
			
			if(d) {
				SendMessage.sendChannelMessage(message, "You are still on cooldown!\n\nTime remaining till next daily: " + (24 - (Instant.now().toEpochMilli() - daily.get(user.getId()).toEpochMilli())/1000/60/60) + " hours", null, d);
			} else {
				daily.put(user.getId(), null);
				
				Random randomGenerator = new Random();
				int x = randomGenerator.nextInt(200) + 1;
				DatabaseManager.updateUserBalance(Main.userID.get(user.getId()),
						String.valueOf(Double.valueOf(Main.userBalance.get(user.getId())) + x));
				Main.userBalance.put(user.getId(), String.valueOf(Double.valueOf(Main.userBalance.get(user.getId())) + x));
				
				
				EmbedBuilder eb = new EmbedBuilder();
				eb.setTitle("Collecting Daily Sapphires!");
				eb.setAuthor(user.getAsTag(), null, user.getAvatarUrl());
				eb.setDescription("\n≻─── ⋆✩⋆ ───≺\n"+ ""+"Adding " + x +"⌬ to your account balance!\n≻─── ⋆✩⋆ ───≺\n");
				message.getChannel().sendMessage(eb.build()).queue();
				
				daily.put(user.getId(), Instant.now());
			}
			
		}
	}

}
