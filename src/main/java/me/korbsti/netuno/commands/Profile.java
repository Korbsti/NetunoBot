package me.korbsti.netuno.commands;

import me.korbsti.netuno.Main;
import me.korbsti.netuno.mysql.DatabaseManager;
import me.korbsti.netuno.util.SendMessage;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

public class Profile {
	public static void profile(User user, Guild guild, Message message) {
		if (user.isBot() || !message.getContentDisplay().toLowerCase().startsWith(Main.guildPrefix.get(guild.getId()) + "profile")) return;
		
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
		EmbedBuilder eb = new EmbedBuilder();
		
		for(String str : DatabaseManager.returnTrophies()) {
			String[] list = str.split("AaAaDqQqQqQqQq");
			if(list[2].equals(user.getId())) {
				eb.setTitle("Your Profile");
				eb.setThumbnail(user.getAvatarUrl());
				eb.setDescription(
						
						"\nTrophy Database ID: " + list[0]
						+"\nBalance Database ID: " + Main.userID.get(user.getId())
						+"\nUser ID: " + user.getId()
						+"\nBalance: " + Main.userBalance.get(user.getId())+ "⌬"
						+"\nNova Multiplier: " + Main.userNovaMultiplier.get(user.getId()) + "⌬"
						+"\nBronze Trophies: " + list[3]
						+"\nSilver Trophies: "+ list[4]
						+"\nGold Trophies: "+ list[5]
						+"\nTitanium Trophies: "+ list[6]
						+"\nPlatinum Trophies: "+ list[7]
						+ "\n"
						
						);
				message.getChannel().sendMessage(eb.build()).queue();
			}
		}
		
		
		
		
	}
}
