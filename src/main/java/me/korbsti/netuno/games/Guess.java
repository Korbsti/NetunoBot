package me.korbsti.netuno.games;

import java.util.HashMap;
import java.util.Random;

import me.korbsti.netuno.Main;
import me.korbsti.netuno.mysql.DatabaseManager;
import me.korbsti.netuno.util.SendMessage;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

public class Guess {

	public static HashMap<String, Integer> setter = new HashMap<>();

	public static void guess(User user, Guild guild, Message message) {

		if (setter.get(user.getId()) != null) {
			Random randomGenerator = new Random();
			double r = randomGenerator.nextDouble();
			
			if (message.getContentDisplay().equals(String.valueOf(setter.get(user.getId())))) {
				
				
				EmbedBuilder eb = new EmbedBuilder();
				eb.setTitle("Guessed the number!");
				eb.setAuthor(user.getAsTag(), null, user.getAvatarUrl());
				eb.setDescription("\n≻─── ⋆✩⋆ ───≺\n"+ "Correct number inputted"+"\n\nAdding " + r +"⌬ to your account balance!\n≻─── ⋆✩⋆ ───≺\n");
				message.getChannel().sendMessage(eb.build()).queue();
				
				//SendMessage.sendChannelMessage(message,"Correct number inputted... \nAdding " + r + "⌬ to your account balance", "", false);

				
				
				DatabaseManager.updateUserBalance(Main.userID.get(user.getId()),
						String.valueOf(Double.valueOf(Main.userBalance.get(user.getId())) + r));
				Main.userBalance.put(user.getId(), String.valueOf(Double.valueOf(Main.userBalance.get(user.getId())) + r));
				setter.put(user.getId(), null);
				return;
			}
			SendMessage.sendChannelMessage(message, "Wrong number, number was " + setter.get(user.getId()) + "", "", false);
			setter.put(user.getId(), null);
			return;
		}
		if (user.isBot()
				|| !message.getContentDisplay().toLowerCase().startsWith(Main.guildPrefix.get(guild.getId()) + "guess"))
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
		
		Random randomGenerator = new Random();
		SendMessage.sendChannelMessage(message, "Guess a number between 1 - 15", "", false);
		setter.put(user.getId(), randomGenerator.nextInt(15) + 1);
		System.out.println(setter.get(user.getId()));
	}
}
