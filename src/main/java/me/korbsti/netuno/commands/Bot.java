package me.korbsti.netuno.commands;

import me.korbsti.netuno.Main;
import me.korbsti.netuno.util.SendMessage;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

public class Bot {
	public static void bot(User user, Guild guild, Message message){
		if (user.isBot() || !message.getContentDisplay().toLowerCase().startsWith(Main.guildPrefix.get(guild.getId()) + "bot"))
			return;

		SendMessage.sendChannelMessage(message, "Available core processors: " +  Runtime.getRuntime().availableProcessors() + "\n" + "Free memory: " + 
	            Runtime.getRuntime().freeMemory() + "", "", false);
	}
}
