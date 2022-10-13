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

public class Collect {

	
	public static HashMap<String, Instant> collectAmount = new HashMap<>();

	
	public static void collect(User user, Guild guild, Message message) {
		if (user.isBot() || !message.getContentDisplay().toLowerCase().startsWith(Main.guildPrefix.get(guild.getId()) + "collect")) return;
		
		
		
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
		
		
		if(collectAmount.get(user.getId()) != null) {
			if(Instant.now().toEpochMilli() -  collectAmount.get(user.getId()).toEpochMilli() < 5000) {
				SendMessage.sendChannelMessage(message, "You are still on cooldown! Time remaining is "+(5000-(Instant.now().toEpochMilli() - collectAmount.get(user.getId()).toEpochMilli()))/1000.0 + " seconds!", "", false);
				return;
			}
		}
		
		Random randomGenerator = new Random();
		double r = randomGenerator.nextDouble();
		if(r > 0.5) {
			r -= 0.45;
		}
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle("Collecting Nova Sapphires!");
		eb.setAuthor(user.getAsTag(), null, user.getAvatarUrl());
		eb.setDescription("\n≻─── ⋆✩⋆ ───≺\n"+ returnLine()+"\n\nAdding " + r +"⌬ to your account balance!\n≻─── ⋆✩⋆ ───≺\n");
		message.getChannel().sendMessage(eb.build()).queue();
		
		//SendMessage.sendChannelMessage(message, "" + returnLine() + "\nAdding "+r+" to your account balance...", null, false);
		DatabaseManager.updateUserBalance(Main.userID.get(user.getId()),
				String.valueOf(Double.valueOf(Main.userBalance.get(user.getId())) + r));
		Main.userBalance.put(user.getId(), String.valueOf(Double.valueOf(Main.userBalance.get(user.getId())) + r));
		collectAmount.put(user.getId(), Instant.now());
	}
	
	
	public static String returnLine() {
		Random randomGenerator = new Random();
		int x = randomGenerator.nextInt(22) + 1;
		switch(x) {
		case 1:
			return "You went out and cosplayed... and earned some ⌬⌬";
		case 2:
			return "After a hard day of mining you collected some Nova Sapphires";
		case 3:
			return "Someone pitied you...";
		case 4:
			return "You went out farming... PHEW that was hard work";
		case 5:
			return "After chopping many trees down, you sold them all";
		case 6:
			return "You robbed a bank! >:[";
		case 7:
			return "Hey you found a lucky coin on the ground!";
		case 8:
			return "You robbed a old couple...";
		case 9:
			return "You mugged someone and took their Nova Sapphires!";
		case 10:
			return "Lots of cleaning later...";
		case 11:
			return "Someone payed you to hug them!";
		case 12:
			return "Hey you won the lottery! Um... yeah for less than 1 ⌬";
		case 13:
			return "You played some music in the park and got some tips!";
		case 14:
			return "You decide to um... yeah 0-0";
		case 15:
			return "Oh look a wallet on the ground! Finders Keepers!";
		case 16:
			return "You sold some apples!";
		case 17:
			return "You sold some bananas!";
		case 18:
			return "You stole a car!";
		case 19:
			return "You worked at a local store!";
		case 20:
			return "... what have you done?";
		case 21:
			return "Its a shooting star! Make a wish!";
		case 22:
			return "Hey you, you're finally awake...";
		
		}
		return "null...?";
		
	}
	
}
