package me.korbsti.netuno.commands;

import java.nio.channels.Channel;
import java.util.concurrent.ExecutionException;

import me.korbsti.netuno.Main;
import me.korbsti.netuno.mysql.DatabaseManager;
import me.korbsti.netuno.util.SendMessage;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.requests.RestAction;

public class Prefix {

	public static void ifChangePrefix(User user, Guild guild, Message message){
		if (user.isBot() || !message.getContentDisplay().toLowerCase().startsWith(Main.guildPrefix.get(guild.getId()) + "prefix"))
			return;
			if (message.getMember().getPermissions().toString().contains("ADMINISTRATOR")) {
				String[] list = message.getContentDisplay().split(" ");
				if (list.length < 2) {
					SendMessage.sendChannelMessage(message, "Invalid arguments, add a prefix onto the command", "", false);
					System.out.println("Invalid args");
					return;
				}
				if (list[1].length() >= 7) {
					SendMessage.sendChannelMessage(message,
							"Prefix is too large, only 7 characters in length are allowed", "", false);
					System.out.println("Too large");
					return;
				}
				System.out.println("Tried to update prefix, prefix was " + list[1]);
				Main.guildPrefix.put(guild.getId(), list[1]);
				DatabaseManager.updateGuildPrefix(Main.guildDB.get(guild.getId()), list[1]);
				SendMessage.sendChannelMessage(message,
						"Successfully updated guilds prefix, prefix is now " + list[1] + "", "", false);
				return;
			} else {
				System.out.println("No permission");
				SendMessage.sendChannelMessage(message, "No Permission, you need the permission ADMINISTRATOR to change my prefix!", "", false);
			} 
	}
}
