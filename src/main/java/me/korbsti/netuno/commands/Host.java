package me.korbsti.netuno.commands;

import me.korbsti.netuno.Main;
import me.korbsti.netuno.util.SendMessage;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

public class Host {
	public static void host(User user, Guild guild, Message message) {
		if (user.isBot() || !message.getContentDisplay().toLowerCase().startsWith(Main.guildPrefix.get(guild.getId()) + "host")) return;
		SendMessage.sendChannelMessage(message, "Proudly sponsored by... no one", "", false);
	}
}
