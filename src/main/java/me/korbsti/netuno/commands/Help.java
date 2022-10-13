package me.korbsti.netuno.commands;

import me.korbsti.netuno.Main;
import me.korbsti.netuno.util.SendMessage;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

public class Help {
	public static void helpMessage(User user, Guild guild, Message message) {
		if (user.isBot() || !message.getContentDisplay().toLowerCase().startsWith(Main.guildPrefix.get(guild.getId()) + "help")) return;
		String prefix = Main.guildPrefix.get(guild.getId());
		
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle("Help Menu");
		
		
		String[] msg = message.getContentDisplay().toLowerCase().split(" ");
		if(msg.length == 1) {
			eb.setAuthor("                   "+user.getAsTag(), null, user.getAvatarUrl());
			eb.setDescription("⊱ ───ஓ๑♡๑ஓ ─── ⊰"
					+"\nPlease describe which category you want info on!\n"
					+ "\n" + prefix + "help economy"
					+ "\n"+ prefix + "help music"
					+ "\n"+ prefix + "help minigame"
					+ "\n"+ prefix + "help utility"
					+ "\n"+ prefix + "help leaderboards"
					+ "\n"+ prefix + "help nsfw\n"
					+ "\n⊱ ───ஓ๑♡๑ஓ ─── ⊰"
					);
			message.getChannel().sendMessage(eb.build()).queue();
			return;
		} else if (msg[1].equalsIgnoreCase("utility")){
			eb.setAuthor("                   "+user.getAsTag(), null, user.getAvatarUrl());
			eb.setDescription("⊱ ───ஓ๑♡๑ஓ ─── ⊰"
					+"\nThese are the utility commands!!\n"
					+ "\n" + prefix + "ping"
					+ "\n" + prefix + "bot"
					+ "\n" + prefix + "host"
					+ "\n" + prefix + "prefix\n"
					+ "\n⊱ ───ஓ๑♡๑ஓ ─── ⊰"
					);
			message.getChannel().sendMessage(eb.build()).queue();
			return;
		}else if (msg[1].equalsIgnoreCase("nsfw")){
			eb.setAuthor("                   "+user.getAsTag(), null, user.getAvatarUrl());
			eb.setDescription("⊱ ───ஓ๑♡๑ஓ ─── ⊰"
					+"\nThese are the nsfw commands! Please note the storage system does not have a lot of nsfw yet!!\n"
					+ "\nCurrently there are no nsfw commands! My apologies!"
					+ "\n⊱ ───ஓ๑♡๑ஓ ─── ⊰"
					);
			
			/*					+ "\n" + prefix + "ass"
					+ "\n" + prefix + "pussy"
					+ "\n" + prefix + "hentai"
					+ "\n" + prefix + "random"
					+ "\n" + prefix + "dick"
					+ "\n" + prefix + "link"
					+ "\n" + prefix + "gif\n"*/
			message.getChannel().sendMessage(eb.build()).queue();
			return;
		} else if (msg[1].equalsIgnoreCase("economy")) {
			eb.setAuthor("                   "+user.getAsTag(), null, user.getAvatarUrl());
			eb.setDescription("⊱ ───ஓ๑♡๑ஓ ─── ⊰"
					+"\nThese are the commands for economy!\n"
					+ "\n" + prefix + "balance"
					+ "\n" + prefix + "shop"
					+ "\n" + prefix + "buy [id]"
					+ "\n" + prefix + "nova"
				    + "\n" + prefix + "profile\n"		
				    + "\n" + prefix + "daily"
					+ "\n⊱ ───ஓ๑♡๑ஓ ─── ⊰"
					);
			
			
			message.getChannel().sendMessage(eb.build()).queue();
			return;
		}else if (msg[1].equalsIgnoreCase("music")) {
			eb.setAuthor("                   "+user.getAsTag(), null, user.getAvatarUrl());
			eb.setDescription("⊱ ───ஓ๑♡๑ஓ ─── ⊰"
					+"\nThese are the commands for music!\n"
					+ "\n" + prefix + "play"
					+ "\n" + prefix + "skip"
					+ "\n" + prefix + "stop"
					+ "\n" + prefix + "leave"
					+ "\n" + prefix + "queue"
					+ "\n" + prefix + "nowplaying\n"
					+ "\n⊱ ───ஓ๑♡๑ஓ ─── ⊰"
					);
			message.getChannel().sendMessage(eb.build()).queue();
		}else if (msg[1].equalsIgnoreCase("minigame")) {
			eb.setAuthor("                   "+user.getAsTag(), null, user.getAvatarUrl());
			eb.setDescription("⊱ ───ஓ๑♡๑ஓ ─── ⊰"
					+"\nThese are the commands for minigame!\n"
					+ "\n" + prefix + "guess"
					+ "\n" + prefix + "math"
					+ "\n" + prefix + "copy"
					+ "\n" + prefix + "collect"
					+ "\n" + prefix + "grab\n"	
					//+ "\n" + prefix + "competition\n"
					+ "\n⊱ ───ஓ๑♡๑ஓ ─── ⊰"
					);
			message.getChannel().sendMessage(eb.build()).queue();
		}else if (msg[1].equalsIgnoreCase("leaderboards")) {
			eb.setAuthor("                   "+user.getAsTag(), null, user.getAvatarUrl());
			eb.setDescription("⊱ ───ஓ๑♡๑ஓ ─── ⊰"
					+"\nThese are the commands for economy!\n"
			        + "\n" + prefix + "leaderboard global balance"
					+ "\n" + prefix + "leaderboard local balance"
					+ "\n" + prefix + "leaderboard global trophies"
					+ "\n" + prefix + "leaderboard local trophies\n"		
					+ "\n⊱ ───ஓ๑♡๑ஓ ─── ⊰"
					);
			message.getChannel().sendMessage(eb.build()).queue();
		} else {
			eb.setAuthor("                   "+user.getAsTag(), null, user.getAvatarUrl());
			eb.setDescription("⊱ ───ஓ๑♡๑ஓ ─── ⊰"
					+"\nPlease describe which category you want info on!\n"
					+ "\n" + prefix + "economy"
					+ "\n"+ prefix + "music"
					+ "\n"+ prefix + "minigame"
					+ "\n"+ prefix + "utility"
					+ "\n"+ prefix + "leaderboards\n"
					+ "\n⊱ ───ஓ๑♡๑ஓ ─── ⊰"
					);
			message.getChannel().sendMessage(eb.build()).queue();
			return;
		}
		
		
		
		
		
		
		
		
		
		/*eb.setDescription("--------------------------"
				+ "\nEconomy & User Commands"
				+ "\n" + prefix + "balance"
				+ "\n" + prefix + "shop"
				+ "\n" + prefix + "buy [id]"
				+ "\n" + prefix + "nova"
			    + "\n" + prefix + "profile"		
			    + "\n"
				+ "\n--------------------------"
				+ "\nUtility Commands"
				+ "\n" + prefix + "ping"
				+ "\n" + prefix + "bot"
				+ "\n" + prefix + "host"
				+ "\n"
				+ "\n--------------------------"
				+ "\nMinigame commands"
				+ "\n" + prefix + "guess"
				+ "\n" + prefix + "math"
				+ "\n" + prefix + "copy"
				+ "\n" + prefix + "collect"
				+ "\n" + prefix + "grab"
				+ "\n"
				+ "\n--------------------------"
				+ "\nMusic commands"
				+ "\n" + prefix + "play"
				+ "\n" + prefix + "skip"
				+ "\n" + prefix + "stop"
				+ "\n" + prefix + "leave"
				+ "\n" + prefix + "queue"
				+ "\n" + prefix + "nowplaying"
				+ "\n"
				+ "\n--------------------------"
				+ "\nOther commands"
		        + "\n" + prefix + "leaderboard global balance"
				+ "\n" + prefix + "leaderboard local balance"
				+ "\n" + prefix + "leaderboard global trophies"
				+ "\n" + prefix + "leaderboard local trophies"
				+ "\n"
				+ "\n--------------------------");
		
		message.getChannel().sendMessage(eb.build()).queue();
		
		*/
		
		
		/*SendMessage.sendChannelMessage(message,
				"> --------------------------"
				+ "\n> Economy Commands"
				+ "\n> " + prefix + "balance"
				+ "\n> " + prefix + "shop"
				+ "\n> " + prefix + "leaderboard global"
				+ "\n> " + prefix + "leaderboard local"
				+ "\n> " + prefix + "buy {id}"
				+ "\n> "
				+ "\n> "
				+ "\n> --------------------------"
				+ "\n> Utility Commands"
				+ "\n> " + prefix + "ping"
				+ "\n> "
				+ "\n> "
				+ "\n> "
				+ "\n> "
				+ "\n> --------------------------");*/

	}
}
