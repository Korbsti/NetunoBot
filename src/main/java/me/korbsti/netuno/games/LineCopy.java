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
import net.dv8tion.jda.api.entities.User;;

public class LineCopy {
	
	public static HashMap<String, String> setter = new HashMap<>();
	public static HashMap<String, Instant> timings = new HashMap<>();

	
	public static void copy(User user, Guild guild, Message message) {

		if (setter.get(user.getId()) != null) {
			Random randomGenerator = new Random();
			double r = randomGenerator.nextDouble();
			
			if (message.getContentDisplay().equals(String.valueOf(setter.get(user.getId())))) {
				
				if(Instant.now().toEpochMilli() - timings.get(user.getId()).toEpochMilli() > 7000) {
					SendMessage.sendChannelMessage(message, "Took too long to type the message, must be less than 7 seconds", "", false);
					setter.put(user.getId(), null);
					timings.put(user.getId(), null);
					return;
				}
				
				EmbedBuilder eb = new EmbedBuilder();
				eb.setTitle("Sentence Copied successfully!");
				eb.setAuthor(user.getAsTag(), null, user.getAvatarUrl());
				eb.setDescription("\n≻─── ⋆✩⋆ ───≺\n"+ "Correct sentence inputted"+"\n\nAdding " + r +"⌬ to your account balance!\n≻─── ⋆✩⋆ ───≺\n");
				message.getChannel().sendMessage(eb.build()).queue();
				
				//SendMessage.sendChannelMessage(message,"Correct sentence inputted...\n adding " + r + "⌬ to your account balance", "", false);

				DatabaseManager.updateUserBalance(Main.userID.get(user.getId()), String.valueOf(Double.valueOf(Main.userBalance.get(user.getId())) + r));
				Main.userBalance.put(user.getId(), String.valueOf(Double.valueOf(Main.userBalance.get(user.getId())) + r));
				setter.put(user.getId(), null);
				return;
			}
			SendMessage.sendChannelMessage(message, "Incorrect sentence :c", "", false);
			setter.put(user.getId(), null);
			return;
		}
		if (user.isBot()
				|| !message.getContentDisplay().toLowerCase().startsWith(Main.guildPrefix.get(guild.getId()) + "copy"))
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
		String sen = chooseRandomSentence();
		SendMessage.sendChannelMessage(message, "Copy the following sentence: " + sen+"", "", false);
		setter.put(user.getId(), sen);
		timings.put(user.getId(), Instant.now());
		
		
	}
	
	
	public static String chooseRandomSentence() {
		Random randomGenerator = new Random();
		int x = randomGenerator.nextInt(16) + 1;
		switch(x) {
		case 1:
			return "Its cap sensitive apparently";
		case 2:
			return "Copying is fun... not really";
		case 3:
			return "Send help I'm being held hostage";
		case 4:
			return "Peter picked some peppers";
		case 5:
			return "Another line here is nice";
		case 6:
			return "Don't you like CAPITALS?";
		case 7:
			return "They were excited to see their first slot";
		case 8:
			return "FUS RO DA";
		case 9:
			return "The rainbow over the hill";
		case 10:
			return "Another random sentence...";
		case 11:
			return "Are you in the need of hugs?";
		case 12:
			return "An apple a day keeps the banana away";
		case 13:
			return "An apple a day keeps the banana away";
		case 14:
			return "An apple a day keeps the banana away";
		case 15:
			return "An apple a day keeps the banana away";
		case 16:
			return "An apple a day keeps the banana away";
		
		}
		return "null...?";
	}
	
	
}
