package me.korbsti.netuno.games;

import me.korbsti.netuno.Main;
import me.korbsti.netuno.mysql.DatabaseManager;
import me.korbsti.netuno.util.SendMessage;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

public class Grab {
	public static void grab(User user, Guild guild, Message message) {
		if (user.isBot() || !message.getContentDisplay().toLowerCase().startsWith(Main.guildPrefix.get(guild.getId()) + "grab")) return;

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
		
		if(Main.generatedRandomCash.get(guild.getId()) == 0.0) {
			SendMessage.sendChannelMessage(message, "Nothing to grab ;~;", "", false);
			return;
		}
		
		double r = Main.generatedRandomCash.get(guild.getId());
		Main.generatedRandomCash.put(guild.getId(), 0.0);
		
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle("Chest Grabbed!");
		eb.setAuthor(user.getAsTag(), null, user.getAvatarUrl());
		eb.setDescription("\n≻─── ⋆✩⋆ ───≺\nAdding " + r +"⌬ to your account balance!\n≻─── ⋆✩⋆ ───≺\n");
		message.getChannel().sendMessage(eb.build()).queue();
		//SendMessage.sendChannelMessage(message, "You grabbed the chest!\n Adding "+r+"⌬ to your account balance...", null, false);
		DatabaseManager.updateUserBalance(Main.userID.get(user.getId()),
				String.valueOf(Double.valueOf(Main.userBalance.get(user.getId())) + r));
		Main.userBalance.put(user.getId(), String.valueOf(Double.valueOf(Main.userBalance.get(user.getId())) + r));
		
		
		
	}
}
