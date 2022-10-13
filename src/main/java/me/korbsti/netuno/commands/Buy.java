package me.korbsti.netuno.commands;

import me.korbsti.netuno.Main;
import me.korbsti.netuno.mysql.DatabaseManager;
import me.korbsti.netuno.util.SendMessage;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

public class Buy {
	
	
	public static void checker(User user, double price, double multiplier, String collector, Message message) {
		if(Double.valueOf( Main.userBalance.get(user.getId()) ) < price) {
			SendMessage.sendChannelMessage(message, "**Not enough Nova Sapphires for " + collector + "**", "", false);
			return;
		}
		double newBalance = Double.parseDouble(Main.userBalance.get(user.getId())) - price;
		String novaMultipler = "" + (Double.parseDouble(Main.userNovaMultiplier.get(user.getId())) + multiplier);
		DatabaseManager.updateUserNova(user, Main.userID.get(user.getId()), novaMultipler);
		DatabaseManager.updateUserBalance(Main.userID.get(user.getId()), String.valueOf(newBalance));
		Main.userBalance.put(user.getId(), String.valueOf(newBalance));
		SendMessage.sendChannelMessage(message, "**You have bought " + collector + " for " + price + "⌬, your new hourly earnings are " + Main.userNovaMultiplier.get(user.getId()) + "⌬**\n**Your new account balance is " + newBalance + "⌬**", "", false);

	}
	
	public static void updateleaderboard(User user, double price, String trophy, Message message) {
		if(Double.valueOf( Main.userBalance.get(user.getId()) ) < price) {
			SendMessage.sendChannelMessage(message, "**Not enough Nova Sapphires for " + trophy + "**", "", false);
			return;
		}
		double newBalance = Double.parseDouble(Main.userBalance.get(user.getId())) - price;
		DatabaseManager.updateUserBalance(Main.userID.get(user.getId()), String.valueOf(newBalance));
		Main.userBalance.put(user.getId(), String.valueOf(newBalance));
		for(String str : DatabaseManager.returnTrophies()) {
			String[] list = str.split("AaAaDqQqQqQqQq");
			if(list[2].equals(user.getId())) {
				switch(trophy.toLowerCase()) {
				case "bronze":
					String newValue = String.valueOf(Integer.valueOf(list[3]) + 1);
					DatabaseManager.updateTrophiesTable(user.getId(), trophy.toLowerCase(), newValue);
					DatabaseManager.updateTrophiesTable(user.getId(), "username", user.getName());
					SendMessage.sendChannelMessage(message, "**You have bought " + trophy + " trophy for " + price + "⌬**\n**Your new account balance is " + newBalance + "⌬**", "", false);
					break;
				case "silver":
					String newValue1 = String.valueOf(Integer.valueOf(list[4]) + 1);
					DatabaseManager.updateTrophiesTable(user.getId(), trophy.toLowerCase(), newValue1);
					DatabaseManager.updateTrophiesTable(user.getId(), "username", user.getName());

					SendMessage.sendChannelMessage(message, "**You have bought " + trophy + " trophy for " + price + "⌬**\n**Your new account balance is " + newBalance + "⌬**", "", false);
					break;
				case "gold":
					String newValue11 = String.valueOf(Integer.valueOf(list[5]) + 1);
					DatabaseManager.updateTrophiesTable(user.getId(), trophy.toLowerCase(), newValue11);
					DatabaseManager.updateTrophiesTable(user.getId(), "username", user.getName());

					SendMessage.sendChannelMessage(message, "**You have bought " + trophy + " trophy for " + price + "⌬**\n**Your new account balance is " + newBalance + "⌬**", "", false);
					break;
				case "titanium":
					String newValue111 = String.valueOf(Integer.valueOf(list[6]) + 1);
					DatabaseManager.updateTrophiesTable(user.getId(), trophy.toLowerCase(), newValue111);
					DatabaseManager.updateTrophiesTable(user.getId(), "username", user.getName());

					SendMessage.sendChannelMessage(message, "**You have bought " + trophy + " trophy for " + price + "⌬**\n**Your new account balance is " + newBalance + "⌬**", "", false);
					break;
				case "platinum":
					String newValue1111 = String.valueOf(Integer.valueOf(list[7]) + 1);
					DatabaseManager.updateTrophiesTable(user.getId(), trophy.toLowerCase(), newValue1111);
					DatabaseManager.updateTrophiesTable(user.getId(), "username", user.getName());

					SendMessage.sendChannelMessage(message, "**You have bought " + trophy + " trophy for " + price + "⌬**\n**Your new account balance is " + newBalance + "⌬**", "", false);
					break;
				
				
				
				}
			}
		}

	}
	
	
	public static void buyItem(User user, Guild guild, Message message) {
		if (user.isBot() || !message.getContentDisplay().startsWith(Main.guildPrefix.get(guild.getId()) + "buy"))
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

		String[] msg = message.getContentDisplay().split(" ");

		if (msg.length < 2) {
			SendMessage.sendChannelMessage(message, "**Invalid arguments, add an ID onto the command**", "", false);
			return;
		}

		switch (msg[1]) {
		case "1":
			if(!Main.userNovaMultiplier.get(user.getId()).equals("0.0")) {
				SendMessage.sendChannelMessage(message, "**You may only get this once!**", "", false);
				return;
			}
			DatabaseManager.updateUserNova(user, Main.userID.get(user.getId()), "0.1");
			SendMessage.sendChannelMessage(message, "**Your Nova Sapphire multiplier has increased by 0.1!**\n**You now recieve 0.1⌬ per hour**\n**The collecting has begun!**", "", false);
			break;
		case "2":
			checker(user, 4, 0.03, "Bronze II Collector", message);
			break;
		case "3":
			checker(user, 20, 0.05, "Bronze III Collector", message);
			break;
		case "4":
			checker(user, 30, 0.10, "Silver I Collector", message);

			break;
		case "5":
			checker(user, 40, 0.20, "Silver II Collector", message);

			break;
		case "6":
			checker(user, 50, 0.30, "Silver III Collector", message);

			break;
		case "7":
			checker(user, 100, 0.70, "Gold I Collector", message);

			break;
		case "8":
			checker(user, 150, 1.70, "Gold II Collector", message);

			break;
		case "9":
			checker(user, 200, 2.80, "Gold III Collector", message);

			break;
		case "10":
			checker(user, 400, 5.0, "Titanium I Collector", message);

			break;
		case "11":
			checker(user, 600, 7.0, "Titanium II Collector", message);

			break;
		case "12":
			checker(user, 700, 9.0, "Titanium III Collector", message);

			break;
		case "13":
			checker(user, 1000, 12.40, "Platinum I Collector", message);

			break;
		case "14":
			checker(user, 1300, 13.80, "Platinum II Collector", message);

			break;
		case "15":
			checker(user, 3000, 100.80, "Platinum III Collector", message);
			break;
		case "16":
			updateleaderboard(user, 3000.0, "Bronze", message);
			break;
		case "17":
			updateleaderboard(user,6000.0, "Silver", message);
			break;
		case "18":
			updateleaderboard(user,9000.0, "Gold", message);
			break;
		case "19":
			updateleaderboard(user,11000, "Titanium", message);
			break;
		case "20":
			updateleaderboard(user,20000, "Platinum", message);
			break;
		}

	}
}
