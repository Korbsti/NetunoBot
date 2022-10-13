package me.korbsti.netuno.games;

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

public class Maths {
	
	public static HashMap<String, String> setter = new HashMap<>();
	public static HashMap<String, Instant> timings = new HashMap<>();

	
	public static void math(User user, Guild guild, Message message) {

		if (setter.get(user.getId()) != null) {
			Random randomGenerator = new Random();
			double r = randomGenerator.nextDouble();
			
			if (message.getContentDisplay().equals(String.valueOf(setter.get(user.getId())))) {
				
				if(Instant.now().toEpochMilli() - timings.get(user.getId()).toEpochMilli() > 7000) {
					SendMessage.sendChannelMessage(message, "Took too long to type the answer, must be less than 7 seconds", "", false);
					setter.put(user.getId(), null);
					timings.put(user.getId(), null);
					return;
				}
				
				EmbedBuilder eb = new EmbedBuilder();
				eb.setTitle("Correct number!");
				eb.setAuthor(user.getAsTag(), null, user.getAvatarUrl());
				eb.setDescription("\n≻─── ⋆✩⋆ ───≺\n"+ "Correct number inputted"+"\n\nAdding " + r +"⌬ to your account balance!\n≻─── ⋆✩⋆ ───≺\n");
				message.getChannel().sendMessage(eb.build()).queue();
				
				
				//SendMessage.sendChannelMessage(message,"Correct number inputted... \nAdding " + r + "⌬ to your account balance", "", false);

				DatabaseManager.updateUserBalance(Main.userID.get(user.getId()), String.valueOf(Double.valueOf(Main.userBalance.get(user.getId())) + r));
				Main.userBalance.put(user.getId(), String.valueOf(Double.valueOf(Main.userBalance.get(user.getId())) + r));
				setter.put(user.getId(), null);
				return;
			}
			SendMessage.sendChannelMessage(message, "Incorrect number :c", "", false);
			setter.put(user.getId(), null);
			return;
		}
		if (user.isBot()
				|| !message.getContentDisplay().toLowerCase().startsWith(Main.guildPrefix.get(guild.getId()) + "math"))
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
		String[] ans = chooseRandomNum().split(",");
		
		SendMessage.sendChannelMessage(message, "Calculate the following: " + ans[1]+"", "", false);
		setter.put(user.getId(), ans[0]);
		timings.put(user.getId(), Instant.now());
		
		
	}
	
	
	public static String chooseRandomNum() {
		Random randomGenerator = new Random();
		int x = randomGenerator.nextInt(40) + 1;
		int y = randomGenerator.nextInt(40) + 1;
		int z = randomGenerator.nextInt(15) + 1;
		if(z < 6) {
			return (x - y) + "," + x + " - " + y;
		} else {
			return (x + y) + "," + x + " + " + y;
		}
	}
}
