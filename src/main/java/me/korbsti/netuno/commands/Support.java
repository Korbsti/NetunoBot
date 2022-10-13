package me.korbsti.netuno.commands;

import me.korbsti.netuno.Main;
import me.korbsti.netuno.util.SendMessage;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

public class Support {
	public static void support(User user, Guild guild, Message message) {
		if (user.isBot() || !message.getContentDisplay().startsWith(Main.guildPrefix.get(guild.getId()) + "support"))
			return;

		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle("Support Menu");
		
		eb.setDescription("If you require support or have any questions you can join the discord below" + "\n\nhttps://discord.gg/MuS9yDr7wz\n\n");
		message.getChannel().sendMessage(eb.build()).queue();;
	}
		
		
}
