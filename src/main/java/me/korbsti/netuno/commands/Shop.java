package me.korbsti.netuno.commands;

import me.korbsti.netuno.Main;
import me.korbsti.netuno.util.SendMessage;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

public class Shop {
	public static void shopList(User user, Guild guild, Message message) {
		if (user.isBot() || !message.getContentDisplay().toLowerCase().startsWith(Main.guildPrefix.get(guild.getId()) + "shop"))
			return;
		
		if(Main.isUserRegistered.get(user.getId()) == null) {
			SendMessage.sendChannelMessage(message, "**You are not registered in the database, type " + Main.guildPrefix.get(guild.getId()) + "register **", "", false);
			return;
		}
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle("Shop Menu");
		eb.setDescription("≫ ──── ≪•◦ ❈ ◦•≫ ──── ≪"
				+ "\n			**Nova Sapphire Miners**"
				+ "\n"
				+ "\nBronze I Collector 0⌬"
				+ "\n0.1⌬ per hour | ID = 1"
				+ "\n    •=—+—=•"
				+ "\nBronze II Collector 4⌬"
				+ "\n0.03⌬ per hour | ID = 2"
				+ "\n    •=—+—=•"
				+ "\nBronze III Collector 20⌬"
				+ "\n0.05⌬ per hour | ID = 3"
				+ "\n    •=—+—=•"
				+ "\nSilver I Collector 30⌬"
				+ "\n0.10 per hour | ID = 4"
				+ "\n    •=—+—=•"
				+ "\nSilver II Collector 40⌬ "
				+ "\n0.20 per hour | ID = 5"
				+ "\n    •=—+—=•"
				+ "\nSilver III Collector 50⌬ "
				+ "\n0.30 per hour | ID = 6"
				+ "\n    •=—+—=•"
				+ "\nGold I Collector 100⌬"
				+ "\n0.70 per hour | ID = 7"
				+ "\n    •=—+—=•"
				+ "\nGold II Collector 150⌬"
				+ "\n1.70 per hour | ID = 8"
				+ "\n    •=—+—=•"
				+ "\nGold III Collector 200⌬"
				+ "\n2.80 per hour | ID = 9"		
				+ "\n    •=—+—=•"
				+ "\nTitanium I Collector 400⌬"
				+ "\n5.0 per hour | ID = 10"	
				+ "\n    •=—+—=•"
				+ "\nTitanium II Collector 600⌬"
				+ "\n7.0 per hour ID = 11"
				+ "\n    •=—+—=•"
				+ "\nTitanium III Collector 700⌬"
				+ "\n9.0 per hour | ID = 12"	
				+ "\n    •=—+—=•"
				+ "\nPlatinum I Collector 1000⌬"
				+ "\n12.40 per hour | ID = 13"
				+ "\n    •=—+—=•"
				+ "\nPlatinum II Collector 1300⌬ "
				+ "\n13.80 per hour | ID = 14"	
				+ "\n    •=—+—=•"
				+ "\nPlatinum III Collector 3000⌬"
				+ "\n100.80 per hour | ID = 15"	
				+ "\n"
				+ "\n——— ≪ °✾° ≫ ———"
				+ "\n\n**Trophies**"
				+ "\n"
				+ "\nBronze Trophie 3000⌬ WEIGHT = 100"
				+ "\nID = 16"
				+ "\n    •=—+—=•"
				+ "\nSilver Trophie 6000⌬ WEIGHT = 300"
				+ "\nID = 17"
				+ "\n    •=—+—=•"
				+ "\nGold Trophie 9000⌬ WEIGHT = 700"
				+ "\nID = 18"
				+ "\n    •=—+—=•"
				+ "\nTitanium Trophie 11000⌬ WEIGHT = 1100"
				+ "\nID = 19"
				+ "\n    •=—+—=•"
				+ "\nPlatinum Trophie 20000⌬ WEIGHT = 4000"
				+ "\nID = 20"
				+ "\n"
				+ "\n≫ ──── ≪•◦ ❈ ◦•≫ ──── ≪");
		
		message.getChannel().sendMessage(eb.build()).queue();
		
	}
}
