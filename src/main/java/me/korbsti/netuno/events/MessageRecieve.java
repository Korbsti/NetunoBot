package me.korbsti.netuno.events;

import java.nio.channels.Channel;
import java.util.Random;

import me.korbsti.netuno.Main;
import me.korbsti.netuno.commands.Balance;
import me.korbsti.netuno.commands.Bot;
import me.korbsti.netuno.commands.Buy;
import me.korbsti.netuno.commands.Competition;
import me.korbsti.netuno.commands.Daily;
import me.korbsti.netuno.commands.Help;
import me.korbsti.netuno.commands.Host;
import me.korbsti.netuno.commands.Leaderboard;
import me.korbsti.netuno.commands.Leave;
import me.korbsti.netuno.commands.Nova;
import me.korbsti.netuno.commands.NowPlaying;
import me.korbsti.netuno.commands.Ping;
import me.korbsti.netuno.commands.Play;
import me.korbsti.netuno.commands.Prefix;
import me.korbsti.netuno.commands.Profile;
import me.korbsti.netuno.commands.Queue;
import me.korbsti.netuno.commands.Register;
import me.korbsti.netuno.commands.Shop;
import me.korbsti.netuno.commands.Skip;
import me.korbsti.netuno.commands.Stop;
import me.korbsti.netuno.commands.Support;
import me.korbsti.netuno.games.Collect;
import me.korbsti.netuno.games.Grab;
import me.korbsti.netuno.games.Guess;
import me.korbsti.netuno.games.LineCopy;
import me.korbsti.netuno.games.Maths;
import me.korbsti.netuno.mysql.DatabaseManager;
import me.korbsti.netuno.util.SendMessage;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageRecieve extends ListenerAdapter {

	public void checkCommands(User user, Guild guild, Message message) {
		Prefix.ifChangePrefix(user, guild, message);
		Balance.checkBalance(user, guild, message);
		Register.registerUser(user, guild, message);
		Help.helpMessage(user, guild, message);
		Shop.shopList(user, guild, message);
		Buy.buyItem(user, guild, message);
		Nova.nova(user, guild, message);
		Host.host(user, guild, message);
		Ping.ping(user, guild, message);
		Bot.bot(user, guild, message);
		Support.support(user, guild, message);
		Profile.profile(user, guild, message);
		Leaderboard.leaderboard(user, guild, message);
		Guess.guess(user, guild, message);
		LineCopy.copy(user, guild, message);
		Maths.math(user, guild, message);
		Collect.collect(user, guild, message);
		Play.play(user, guild, message);
		Skip.skip(user, guild, message);
		Stop.stop(user, guild, message);
		NowPlaying.nowplaying(user, guild, message);
		Queue.queue(user, guild, message);
		Leave.leave(user, guild, message);
		Grab.grab(user, guild, message);
		Daily.daily(user, guild, message);
		Competition.competition(user, guild, message);
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if (event.isFromType(ChannelType.TEXT)) {
			String guildID = event.getGuild().getId();
			if (Main.guildID.get(event.getGuild().getId()) != null && !event.getAuthor().isBot()) {
				checkCommands(event.getAuthor(), event.getGuild(), event.getMessage());

				
				Random randomGenerator = new Random();
				int x = randomGenerator.nextInt(200) + 1;
				double y = randomGenerator.nextInt(30) + 1;
				if(x == 16 && Main.generatedRandomCash.get(event.getGuild().getId()) != 0.0) {
					String prefix = Main.guildPrefix.get(event.getGuild().getId());
					EmbedBuilder eb = new EmbedBuilder();
					eb.setTitle("A chest appeared!");
					eb.setDescription("\n≻─── ⋆✩⋆ ───≺\nThere are "+y +"⌬ in the chest!\n\nType "+prefix+"grab to claim it!\n≻─── ⋆✩⋆ ───≺\n");
					event.getChannel().sendMessage(eb.build()).queue();
					
					//SendMessage.sendChannelMessage(event.getMessage(), "**A chest full of Nova Sapphires have appeared!\n Type ++grab to claim them!\n\nAmount: " +y+"⌬**", "", false);
					
					
					Main.generatedRandomCash.put(event.getGuild().getId(), y);
				}
				
				
			} else {
				for (String str : DatabaseManager.retrieveGuildID()) {
					if (str != null) {
						if (str.contains(guildID)) {

							for (String str3 : DatabaseManager.retrieveGuildID()) {
								String[] list = str3.split("AaAaDqQqQqQqQq");
								Main.guildPrefix.put(list[2], list[1]);
								Main.guildID.put(list[0], list[1]);
								Main.guildDB.put(list[0], list[2]);
							}

							checkCommands(event.getAuthor(), event.getGuild(), event.getMessage());

							return;
						}
					}
				}
				DatabaseManager.createGuildPrefix(event.getGuild().getId(), "++");
			}
		}

		/*if (event.isFromType(ChannelType.TEXT)) {
			System.out.printf("[%s][%s] %#s: %s%n", event.getGuild().getName(), event.getChannel().getName(),
					event.getAuthor(), event.getMessage().getContentDisplay());
		} else {
			System.out.printf("[PM] %#s: %s%n", event.getAuthor(), event.getMessage().getContentDisplay());
		}*/
	}

}
