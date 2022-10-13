package me.korbsti.netuno.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import me.korbsti.netuno.Main;
import me.korbsti.netuno.mysql.DatabaseManager;
import me.korbsti.netuno.util.SendMessage;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.internal.requests.Route.Guilds;

public class Leaderboard {

	public static void leaderboard(User user, Guild guild, Message message) {
		if (user.isBot() || !message.getContentDisplay().toLowerCase()
				.startsWith(Main.guildPrefix.get(guild.getId()) + "leaderboard"))
			return;

		String[] list = message.getContentDisplay().toLowerCase().split(" ");

		if (list.length < 2) {
			SendMessage.sendChannelMessage(message, "**Invalid arguments, type " + Main.guildPrefix.get(guild.getId())
					+ "help to see the proper arguments**", "", false);
			return;
		}
		if (!list[1].equals("local") && !list[1].equals("global")) {
			SendMessage.sendChannelMessage(message, "**Invalid arguments, type " + Main.guildPrefix.get(guild.getId())
					+ "help to see the proper arguments**", "", false);
			return;
		}

		if (!list[2].equals("balance") && !list[2].equals("trophies")) {
			SendMessage.sendChannelMessage(message, "**Invalid arguments, type " + Main.guildPrefix.get(guild.getId())
					+ "help to see the proper arguments**", "", false);
			return;
		}

		DatabaseManager.updateTrophiesUsername(user.getId(), user.getName());
		
		// id,username,userID,bronze,silver,gold,titanium,platinum
		if (list[2].equals("trophies") && list[1].equals("global")) {
			ArrayList<Integer> weights = new ArrayList<Integer>();
			ArrayList<String> userID = new ArrayList<String>();

			ArrayList<String> names = new ArrayList<String>();
			ArrayList<String> bronzeT = new ArrayList<String>();
			ArrayList<String> silverT = new ArrayList<String>();
			ArrayList<String> goldT = new ArrayList<String>();
			ArrayList<String> titaniumT = new ArrayList<String>();

			ArrayList<String> platinumT = new ArrayList<String>();
			for (String str : DatabaseManager.returnTrophies()) {
				String[] line = str.split("AaAaDqQqQqQqQq");
				System.out.println(Arrays.toString(line));
				int b = 100 * Integer.valueOf(line[3]);
				int s = 300 * Integer.valueOf(line[4]);
				int g = 700 * Integer.valueOf(line[5]);
				int t = 1100 * Integer.valueOf(line[6]);
				int p = 4000 * Integer.valueOf(line[7]);
				names.add(line[1]);
				weights.add(b + g + p + s + t);
				userID.add(line[2]);
				bronzeT.add(line[3]);
				silverT.add(line[4]);
				goldT.add(line[5]);
				titaniumT.add(line[6]);
				platinumT.add(line[7]);
			}

			for (int x = 0; x < weights.size(); x++) {
				if (x + 1 != weights.size()) {
					if (weights.get(x + 1) > weights.get(x)) {
						names.add(names.get(x));
						weights.add(weights.get(x));
						userID.add(userID.get(x));
						bronzeT.add(bronzeT.get(x));
						silverT.add(silverT.get(x));
						goldT.add(goldT.get(x));
						titaniumT.add(titaniumT.get(x));
						platinumT.add(platinumT.get(x));

						names.remove(x);
						bronzeT.remove(x);
						silverT.remove(x);
						goldT.remove(x);
						titaniumT.remove(x);
						platinumT.remove(x);

						weights.remove(x);
						userID.remove(x);
						x = -1;

					}
				} else {
					break;
				}
			}
			EmbedBuilder eb = new EmbedBuilder();
			eb.setTitle("Leaderboard Global Trophies\n");
			String description = "";
			eb.setThumbnail("https://cdn.discordapp.com/attachments/811761680996368387/851612669139877918/trophd.png");
			String namesT = "";
			String bronzeField = "";
			String silverField = "";
			String goldField = "";
			String titaniumField = "";
			String platinumField = "";
			String place = "";
			for (int x = 0; x != 10; x++) {
				if (x == weights.size()) {
					break;
				}
				place += "\n#" + (x + 1);
				namesT += "\n" + names.get(x);
				bronzeField += "\n" + bronzeT.get(x);
				silverField += "\n" + silverT.get(x);
				goldField += "\n" + goldT.get(x);
				titaniumField += "\n" + titaniumT.get(x);
				platinumField += "\n" + platinumT.get(x);
				/*
				description += "\nName: " + names.get(x) + " Bronze:   " + bronzeT.get(x) + " Silver:   "
						+ silverT.get(x) + " Gold:   " + goldT.get(x) + " Titanium:   " + titaniumT.get(x)
						+ " Platinum:   " + platinumT.get(x);*/
			}
			eb.addField("Rank", place, true);
			eb.addField("Name", namesT, true);
			eb.addField("Bronze", bronzeField, true);
			eb.addField("Rank", place, true);
			eb.addField("Name", namesT, true);
			eb.addField("Silver", silverField, true);
			eb.addField("Rank", place, true);
			eb.addField("Name", namesT, true);
			eb.addField("Gold", goldField, true);
			eb.addField("Rank", place, true);
			eb.addField("Name", namesT, true);
			eb.addField("Titanium", titaniumField, true);
			eb.addField("Rank", place, true);
			eb.addField("Name", namesT, true);
			eb.addField("Platinum", platinumField, true);
			description += "\n\n";
			eb.setDescription(description);
			message.getChannel().sendMessage(eb.build()).queue();
		}

		if (list[2].equals("trophies") && list[1].equals("local")) {
			ArrayList<Integer> weights = new ArrayList<Integer>();
			ArrayList<String> userID = new ArrayList<String>();

			ArrayList<String> names = new ArrayList<String>();
			ArrayList<String> bronzeT = new ArrayList<String>();
			ArrayList<String> silverT = new ArrayList<String>();
			ArrayList<String> goldT = new ArrayList<String>();
			ArrayList<String> titaniumT = new ArrayList<String>();
			ArrayList<String> platinumT = new ArrayList<String>();

			ArrayList<String> ids = new ArrayList<String>();
			guild.loadMembers();
			for (Member mem : guild.getMembers()) {
				ids.add(mem.getId());
			}
			for (String str : DatabaseManager.returnTrophies()) {
				String[] line = str.split("AaAaDqQqQqQqQq");
				if (ids.contains(line[2])) {
					int b = 100 * Integer.valueOf(line[3]);
					int s = 300 * Integer.valueOf(line[4]);
					int g = 700 * Integer.valueOf(line[5]);
					int t = 1100 * Integer.valueOf(line[6]);
					int p = 4000 * Integer.valueOf(line[7]);
					names.add(line[1]);
					weights.add(b + g + p + s + t);
					userID.add(line[2]);
					bronzeT.add(line[3]);
					silverT.add(line[4]);
					goldT.add(line[5]);
					titaniumT.add(line[6]);
					platinumT.add(line[7]);
				}
			}

			for (int x = 0; x < weights.size(); x++) {
				if (x + 1 != weights.size()) {
					if (weights.get(x + 1) > weights.get(x)) {
						names.add(names.get(x));
						weights.add(weights.get(x));
						userID.add(userID.get(x));
						bronzeT.add(bronzeT.get(x));
						silverT.add(silverT.get(x));
						goldT.add(goldT.get(x));
						titaniumT.add(titaniumT.get(x));
						platinumT.add(platinumT.get(x));

						names.remove(x);
						bronzeT.remove(x);
						silverT.remove(x);
						goldT.remove(x);
						titaniumT.remove(x);
						platinumT.remove(x);

						weights.remove(x);
						userID.remove(x);
						x = -1;

					}
				} else {
					break;
				}
			}
			EmbedBuilder eb = new EmbedBuilder();
			eb.setTitle("Leaderboard Local Trophies\n");
			String description = "";
			eb.setThumbnail("https://cdn.discordapp.com/attachments/811761680996368387/851612669139877918/trophd.png");
			String namesT = "";
			String bronzeField = "";
			String silverField = "";
			String goldField = "";
			String titaniumField = "";
			String platinumField = "";
			String place = "";
			for (int x = 0; x != 10; x++) {
				if (x == weights.size()) {
					break;
				}
				place += "\n#" + (x + 1);
				namesT += "\n" + names.get(x);
				bronzeField += "\n" + bronzeT.get(x);
				silverField += "\n" + silverT.get(x);
				goldField += "\n" + goldT.get(x);
				titaniumField += "\n" + titaniumT.get(x);
				platinumField += "\n" + platinumT.get(x);
				/*
				description += "\nName: " + names.get(x) + " Bronze:   " + bronzeT.get(x) + " Silver:   "
						+ silverT.get(x) + " Gold:   " + goldT.get(x) + " Titanium:   " + titaniumT.get(x)
						+ " Platinum:   " + platinumT.get(x);*/
			}
			eb.addField("Rank", place, true);
			eb.addField("Name", namesT, true);
			eb.addField("Bronze", bronzeField, true);
			eb.addField("Rank", place, true);
			eb.addField("Name", namesT, true);
			eb.addField("Silver", silverField, true);
			eb.addField("Rank", place, true);
			eb.addField("Name", namesT, true);
			eb.addField("Gold", goldField, true);
			eb.addField("Rank", place, true);
			eb.addField("Name", namesT, true);
			eb.addField("Titanium", titaniumField, true);
			eb.addField("Rank", place, true);
			eb.addField("Name", namesT, true);
			eb.addField("Platinum", platinumField, true);
			description += "\n\n";
			eb.setDescription(description);
			message.getChannel().sendMessage(eb.build()).queue();
		}

		// id,username,userID,bronze,silver,gold,titanium,platinum
		if (list[2].equals("balance") && list[1].equals("global")) {
			ArrayList<String> username = new ArrayList<String>();
			ArrayList<Double> balance = new ArrayList<Double>();
			ArrayList<String> userID = new ArrayList<String>();
			for (Map.Entry<String, String> set : Main.userBalance.entrySet()) {
				for (String str : DatabaseManager.returnTrophies()) {
					String[] li = str.split("AaAaDqQqQqQqQq");
					if (li[2].equals(set.getKey())) {
						username.add(li[1]);
						balance.add(Double.valueOf(Main.userBalance.get(set.getKey())));
						userID.add(Main.userID.get(set.getKey()));
					}
				}
			}
			for (int x = 0; x != balance.size(); x++) {
				if (x + 1 != balance.size()) {
					if (balance.get(x + 1) > balance.get(x)) {
						username.add(username.get(x));
						username.remove(username.get(x));
						balance.add(balance.get(x));
						balance.remove(balance.get(x));
						userID.add(userID.get(x));
						userID.remove(x);
						x = -1;
					}
				} else {
					break;
				}
			}
			EmbedBuilder eb = new EmbedBuilder();
			eb.setTitle("Leaderboard Global Balance\n");
			eb.setThumbnail("https://cdn.discordapp.com/attachments/811761680996368387/851613296477208606/bene.png");
			String description = "";
			String namesT = "";
			String balanceT = "";
			String rank = "";
			for (int x = 0; x != 11; x++) {
				try {
					namesT += "\n" + username.get(x);
					balanceT += "\n" + balance.get(x) + "⌬";
					rank += "\n#" + (x + 1);
				} catch (Exception e) {
					break;
				}
			}
			eb.addField("Rank", rank, true);
			eb.addField("Name", namesT, true);
			eb.addField("Balance", balanceT, true);
			description += "\n\n";
			eb.setDescription(description);
			message.getChannel().sendMessage(eb.build()).queue();
		}

		if (list[2].equals("balance") && list[1].equals("local")) {
			ArrayList<String> username = new ArrayList<String>();
			ArrayList<Double> balance = new ArrayList<Double>();
			ArrayList<String> userID = new ArrayList<String>();
			ArrayList<String> ids = new ArrayList<String>();
			guild.loadMembers();
			for (Member mem : guild.getMembers()) {
				ids.add(mem.getId());
			}
			for (Map.Entry<String, String> set : Main.userBalance.entrySet()) {
				if(ids.contains(set.getKey())) {
					balance.add(Double.valueOf(Main.userBalance.get(set.getKey())));
					userID.add(Main.userID.get(set.getKey()));
				}
				for (String str : DatabaseManager.returnTrophies()) {
					String[] li = str.split("AaAaDqQqQqQqQq");
					if (ids.contains(li[2])) {
						if (li[2].equals(set.getKey())) {
							username.add(li[1]);
						}
					}
				}
			}
			try {
			for (int x = 0; x < 10; x++) {
				if (x + 1 <= balance.size()) {
					if (balance.get(x + 1) > balance.get(x)) {
						if (username.get(x) != null) {
							username.add(username.get(x));
							username.remove(username.get(x));
							balance.add(balance.get(x));
							balance.remove(balance.get(x));
							userID.add(userID.get(x));
							userID.remove(x);
							x = -1;
						}

					}
				} else {
					break;
				}
			}
			} catch (Exception e) {
				;
			}
			EmbedBuilder eb = new EmbedBuilder();
			eb.setTitle("Leaderboard Local Balance\n");
			String description = "";
			eb.setThumbnail("https://cdn.discordapp.com/attachments/811761680996368387/851613296477208606/bene.png");
			String namesT = "";
			String balanceT = "";
			String rank = "";
			for (int x = 0; x != balance.size(); x++) {
				try {
					if(x == 11) {
						break;
					}
					namesT += "\n" + username.get(x);
					balanceT += "\n" + balance.get(x) + "⌬";
					rank += "\n#" + (x + 1);
				} catch (Exception e) {
					break;
				}
			}
			eb.addField("Rank", rank, true);
			eb.addField("Name", namesT, true);
			eb.addField("Balance", balanceT, true);
			description += "\n\n";
			eb.setDescription(description);
			message.getChannel().sendMessage(eb.build()).queue();
		}

	}
}
