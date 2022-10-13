package me.korbsti.netuno.commands;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import me.korbsti.netuno.Main;
import me.korbsti.netuno.mysql.DatabaseManager;
import me.korbsti.netuno.util.SendMessage;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

public class Competition {

	public static HashMap<String, Boolean> initiatingCompetition = new HashMap<>();
	public static HashMap<String, Boolean> startedCompetition = new HashMap<>();
	public static HashMap<String, Boolean> startQuestions = new HashMap<>();
	public static HashMap<String, Integer> questionCounter = new HashMap<>();
	public static HashMap<String, String> currentAnswer = new HashMap<>();
	public static HashMap<String, String> difficulty = new HashMap<>();
	public static HashMap<String, ArrayList<String>> isInComp = new HashMap<>();
	public static HashMap<String, Integer> points = new HashMap<>();
	public static HashMap<String, String> name = new HashMap<>();
	public static HashMap<String, String> currentCompetitionGuild = new HashMap<>();

	public static void competition(User user, Guild guild, Message message) {
		if (startedCompetition.get(guild.getId()) != null) {
			if (startQuestions.get(guild.getId()) != null) {
				if (isInComp.get(guild.getId()).contains(user.getId())) {
					if (message.getContentDisplay().equalsIgnoreCase(currentAnswer.get(guild.getId()))
							&& difficulty.get(guild.getId()).equals("easy")
							&& currentCompetitionGuild.get(user.getId()).equals(guild.getId())) {
						SendMessage.sendChannelMessage(message,
								user.getAsTag() + " has gotten the answer correct!\nMoving onto the next question", "",
								false);
						points.put(user.getId(), points.get(user.getId()) + 1);
						pickEasyAnswer(user, guild, message);
					}
					if (message.getContentDisplay().equalsIgnoreCase(currentAnswer.get(guild.getId()))
							&& difficulty.get(guild.getId()).equals("medium")
							&& currentCompetitionGuild.get(user.getId()).equals(guild.getId())) {
						SendMessage.sendChannelMessage(message,
								user.getAsTag() + " has gotten the answer correct!\nMoving onto the next question", "",
								false);
						points.put(user.getId(), points.get(user.getId()) + 1);
						pickEasyAnswer(user, guild, message);
					}
					if (message.getContentDisplay().equalsIgnoreCase(currentAnswer.get(guild.getId()))
							&& difficulty.get(guild.getId()).equals("hard")
							&& currentCompetitionGuild.get(user.getId()).equals(guild.getId())) {
						SendMessage.sendChannelMessage(message,
								user.getAsTag() + " has gotten the answer correct!\nMoving onto the next question", "",
								false);
						points.put(user.getId(), points.get(user.getId()) + 1);
						pickEasyAnswer(user, guild, message);
					}
				}
				return;
			}

			if (message.getContentDisplay().equalsIgnoreCase("compete")) {
				isInComp.get(guild.getId()).add(user.getId());
				name.put(user.getId(), user.getAsTag());
				points.put(user.getId(), 0);
				currentCompetitionGuild.put(user.getId(), guild.getId());
				SendMessage.sendChannelMessage(message, user.getAsTag() + " has been added to the competition list", "",
						false);
				return;
			}
		}
		if (initiatingCompetition.get(user.getId()) != null) {
			if (message.getContentDisplay().equalsIgnoreCase("easy")) {
				EmbedBuilder eb = new EmbedBuilder();
				eb.setTitle("Initiated Competition");
				eb.setAuthor(user.getAsTag(), null, user.getAvatarUrl());
				eb.setDescription(
						"\n≻─── ⋆✩⋆ ───≺\nYou have chosen **EASY** mode for the competition\nI shall explain the rules first, the fastest to answer the questions gets a single point\nThe user with the highest points gets the most sapphires\nThe more players participating in this competition, the higher the reward\n\nTo enter this competition please type 'Compete'\nYou have 20 seconds before the questions start!\n≻─── ⋆✩⋆ ───≺\n");
				message.getChannel().sendMessage(eb.build()).queue();
				startedCompetition.put(guild.getId(), true);
				isInComp.put(guild.getId(), new ArrayList<String>());
				difficulty.put(guild.getId(), "easy");
				questionCounter.put(guild.getId(), 0);
				initiatingCompetition.put(user.getId(), null);
				ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
				Runnable toRun = new Runnable() {
					public void run() {
						if (isInComp.get(guild.getId()).size() == 0) {
							SendMessage.sendChannelMessage(message,
									"No players are participating... cancelling competition", null, false);
							startedCompetition.put(guild.getId(), null);
							isInComp.put(guild.getId(), new ArrayList<String>());

							return;
						}
						if (isInComp.get(guild.getId()).size() == 1) {
							SendMessage.sendChannelMessage(message, "Two or more users have to participate", null,
									false);
							startedCompetition.put(guild.getId(), null);
							isInComp.put(guild.getId(), new ArrayList<String>());

							return;
						}

						SendMessage.sendChannelMessage(message, "Amount of players detected: "
								+ isInComp.get(guild.getId()).size()
								+ "\nEach player adds 10 sapphires to first place\nFirst place gets "
								+ 10 * isInComp.get(guild.getId()).size()
								+ " sapphires\nYou must have over 3 points to recieve the first place award\nA total of 6 questions will be asked!",
								"", false);
						startQuestions.put(guild.getId(), true);
						pickEasyAnswer(user, guild, message);
					}
				};

				ScheduledFuture<?> handle = scheduler.schedule(toRun, 10, TimeUnit.SECONDS);

				ScheduledExecutorService sch = Executors.newScheduledThreadPool(1);
				Runnable rr = new Runnable() {
					public void run() {
						if (startedCompetition.get(guild.getId()) != null) {
							SendMessage.sendChannelMessage(message,
									"Time exceeded for game to continue!\nMy apologies to anyone still trying to figure out answers!",
									null, false);
							startedCompetition.put(guild.getId(), null);
							isInComp.put(guild.getId(), new ArrayList<String>());
							for (Map.Entry<String, Integer> set : points.entrySet()) {
								try {
									if (currentCompetitionGuild.get(set.getKey()) != null) {
										if (currentCompetitionGuild.get(set.getKey()).equals(guild.getId())) {
											name.remove(set.getKey());
											points.remove(set.getKey());
											currentCompetitionGuild.remove(set.getKey());
											startedCompetition.put(guild.getId(), null);
											initiatingCompetition.put(user.getId(), null);
											questionCounter.put(guild.getId(), 0);
											startQuestions.put(guild.getId(), null);
										}
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							return;
						}
					}
				};

				ScheduledFuture<?> dd = sch.schedule(rr, 10, TimeUnit.MINUTES);
				return;
			} else if (message.getContentDisplay().equalsIgnoreCase("medium")) {
				EmbedBuilder eb = new EmbedBuilder();
				eb.setTitle("Initiated Competition");
				eb.setAuthor(user.getAsTag(), null, user.getAvatarUrl());
				eb.setDescription(
						"\n≻─── ⋆✩⋆ ───≺\nYou have chosen **MEDIUM** mode for the competition\nI shall explain the rules first, the fastest to answer the questions gets a single point\nThe user with the highest points gets the most sapphires\nThe more players participating in this competition, the higher the reward\n\nTo enter this competition please type 'Compete'\nYou have 10 seconds before the questions start!\n≻─── ⋆✩⋆ ───≺\n");
				message.getChannel().sendMessage(eb.build()).queue();
				startedCompetition.put(guild.getId(), true);
				isInComp.put(guild.getId(), new ArrayList<String>());
				difficulty.put(guild.getId(), "medium");
				initiatingCompetition.put(user.getId(), null);
				ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
				Runnable toRun = new Runnable() {
					public void run() {
						if (isInComp.get(guild.getId()).size() == 0) {
							SendMessage.sendChannelMessage(message,
									"No players are participating... cancelling competition", null, false);
							startedCompetition.put(guild.getId(), null);
							isInComp.put(guild.getId(), new ArrayList<String>());
							for (Map.Entry<String, Integer> set : points.entrySet()) {
								try {
									if (currentCompetitionGuild.get(set.getKey()) != null) {
										if (currentCompetitionGuild.get(set.getKey()).equals(guild.getId())) {
											name.remove(set.getKey());
											points.remove(set.getKey());
											currentCompetitionGuild.remove(set.getKey());
											startedCompetition.put(guild.getId(), null);
											initiatingCompetition.put(user.getId(), null);
											questionCounter.put(guild.getId(), 0);
											startQuestions.put(guild.getId(), null);
										}
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}

							return;
						}
						if (isInComp.get(guild.getId()).size() == 1) {
							SendMessage.sendChannelMessage(message, "Two or more users have to participate", null,
									false);
							startedCompetition.put(guild.getId(), null);
							isInComp.put(guild.getId(), new ArrayList<String>());
							for (Map.Entry<String, Integer> set : points.entrySet()) {
								try {
									if (currentCompetitionGuild.get(set.getKey()) != null) {
										if (currentCompetitionGuild.get(set.getKey()).equals(guild.getId())) {
											name.remove(set.getKey());
											points.remove(set.getKey());
											currentCompetitionGuild.remove(set.getKey());
											startedCompetition.put(guild.getId(), null);
											initiatingCompetition.put(user.getId(), null);
											questionCounter.put(guild.getId(), 0);
											startQuestions.put(guild.getId(), null);
										}
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}

							return;
						}

						SendMessage.sendChannelMessage(message, "Amount of players detected: "
								+ isInComp.get(guild.getId()).size()
								+ "\nEach player adds 10 sapphires to first place\nFirst place gets "
								+ 10 * isInComp.get(guild.getId()).size()
								+ " sapphires\nYou must have over 3 points to recieve the first place award\nA total of 6 questions will be asked!",
								"", false);
						startQuestions.put(guild.getId(), true);
						pickEasyAnswer(user, guild, message);
					}
				};

				ScheduledFuture<?> handle = scheduler.schedule(toRun, 10, TimeUnit.SECONDS);

				ScheduledExecutorService sch = Executors.newScheduledThreadPool(1);
				Runnable rr = new Runnable() {
					public void run() {
						if (startedCompetition.get(guild.getId()) != null) {
							SendMessage.sendChannelMessage(message,
									"Time exceeded for game to continue!\nMy apologies to anyone still trying to figure out answers!",
									null, false);
							startedCompetition.put(guild.getId(), null);
							isInComp.put(guild.getId(), new ArrayList<String>());
							for (Map.Entry<String, Integer> set : points.entrySet()) {
								try {
									if (currentCompetitionGuild.get(set.getKey()) != null) {
										if (currentCompetitionGuild.get(set.getKey()).equals(guild.getId())) {
											name.remove(set.getKey());
											points.remove(set.getKey());
											currentCompetitionGuild.remove(set.getKey());
											startedCompetition.put(guild.getId(), null);
											initiatingCompetition.put(user.getId(), null);
											questionCounter.put(guild.getId(), 0);
											startQuestions.put(guild.getId(), null);
										}
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							return;
						}
					}
				};

				ScheduledFuture<?> dd = sch.schedule(rr, 10, TimeUnit.MINUTES);
				return;
			} else if (message.getContentDisplay().equalsIgnoreCase("hard")) {
				EmbedBuilder eb = new EmbedBuilder();
				eb.setTitle("Initiated Competition");
				eb.setAuthor(user.getAsTag(), null, user.getAvatarUrl());
				eb.setDescription(
						"\n≻─── ⋆✩⋆ ───≺\nYou have chosen **HARD** mode for the competition\nI shall explain the rules first, the fastest to answer the questions gets a single point\nThe user with the highest points gets the most sapphires\nThe more players participating in this competition, the higher the reward\n\nTo enter this competition please type 'Compete'\nYou have 10 seconds before the questions start!\n≻─── ⋆✩⋆ ───≺\n");
				message.getChannel().sendMessage(eb.build()).queue();
				startedCompetition.put(guild.getId(), true);
				isInComp.put(guild.getId(), new ArrayList<String>());
				difficulty.put(guild.getId(), "hard");
				initiatingCompetition.put(user.getId(), null);
				ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
				Runnable toRun = new Runnable() {
					public void run() {
						if (isInComp.get(guild.getId()).size() == 0) {
							SendMessage.sendChannelMessage(message,
									"No players are participating... cancelling competition", null, false);
							startedCompetition.put(guild.getId(), null);
							isInComp.put(guild.getId(), new ArrayList<String>());
							for (Map.Entry<String, Integer> set : points.entrySet()) {
								try {
									if (currentCompetitionGuild.get(set.getKey()) != null) {
										if (currentCompetitionGuild.get(set.getKey()).equals(guild.getId())) {
											name.remove(set.getKey());
											points.remove(set.getKey());
											currentCompetitionGuild.remove(set.getKey());
											startedCompetition.put(guild.getId(), null);
											initiatingCompetition.put(user.getId(), null);
											questionCounter.put(guild.getId(), 0);
											startQuestions.put(guild.getId(), null);
										}
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}

							return;
						}
						if (isInComp.get(guild.getId()).size() == 1) {
							SendMessage.sendChannelMessage(message, "Two or more users have to participate", null,
									false);
							startedCompetition.put(guild.getId(), null);
							isInComp.put(guild.getId(), new ArrayList<String>());
							for (Map.Entry<String, Integer> set : points.entrySet()) {
								try {
									if (currentCompetitionGuild.get(set.getKey()) != null) {
										if (currentCompetitionGuild.get(set.getKey()).equals(guild.getId())) {
											name.remove(set.getKey());
											points.remove(set.getKey());
											currentCompetitionGuild.remove(set.getKey());
											startedCompetition.put(guild.getId(), null);
											initiatingCompetition.put(user.getId(), null);
											questionCounter.put(guild.getId(), 0);
											startQuestions.put(guild.getId(), null);
										}
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}

							return;
						}

						SendMessage.sendChannelMessage(message, "Amount of players detected: "
								+ isInComp.get(guild.getId()).size()
								+ "\nEach player adds 10 sapphires to first place\nFirst place gets "
								+ 10 * isInComp.get(guild.getId()).size()
								+ " sapphires\n",
								"", false);
						startQuestions.put(guild.getId(), true);
						pickEasyAnswer(user, guild, message);
					}
				};

				ScheduledFuture<?> handle = scheduler.schedule(toRun, 10, TimeUnit.SECONDS);

				ScheduledExecutorService sch = Executors.newScheduledThreadPool(1);
				Runnable rr = new Runnable() {
					public void run() {
						if (startedCompetition.get(guild.getId()) != null) {
							SendMessage.sendChannelMessage(message,
									"Time exceeded for game to continue!\nMy apologies to anyone still trying to figure out answers!",
									null, false);
							startedCompetition.put(guild.getId(), null);
							isInComp.put(guild.getId(), new ArrayList<String>());
							for (Map.Entry<String, Integer> set : points.entrySet()) {
								try {
									if (currentCompetitionGuild.get(set.getKey()) != null) {
										if (currentCompetitionGuild.get(set.getKey()).equals(guild.getId())) {
											name.remove(set.getKey());
											points.remove(set.getKey());
											currentCompetitionGuild.remove(set.getKey());
											startedCompetition.put(guild.getId(), null);
											initiatingCompetition.put(user.getId(), null);
											questionCounter.put(guild.getId(), 0);
											startQuestions.put(guild.getId(), null);
										}
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							return;
						}
					}
				};

				ScheduledFuture<?> dd = sch.schedule(rr, 10, TimeUnit.MINUTES);
				return;
			} else {
				SendMessage.sendChannelMessage(message, "Unknown choice, cancelling competition...", "", false);
				initiatingCompetition.put(user.getId(), null);
				return;
			}
		}

		if (user.isBot() || !message.getContentDisplay().toLowerCase()
				.startsWith(Main.guildPrefix.get(guild.getId()) + "competition"))
			return;
		if(currentCompetitionGuild.get(user.getId()) != null) {
			SendMessage.sendChannelMessage(message, "You are already in a competition!", "",
					false);
			return;
		}
		if (startedCompetition.get(guild.getId()) != null) {
			if (startedCompetition.get(guild.getId()) == true) {
				SendMessage.sendChannelMessage(message, "A competition has already been initiated in this server!", "",
						false);
				return;
			}

		}

		if (Main.isUserRegistered.get(user.getId()) == null) {
			DatabaseManager.createUserInfo(user.getId(), "0.0", "0.0", user.getName());
			Main.isUserRegistered.put(user.getId(), true);
			DatabaseManager.insertTrophiesTable(user.getId(), user.getName());
			EmbedBuilder eb = new EmbedBuilder();
			eb.setTitle("Registering into the database...");
			eb.setDescription("Successfully registered into the database");
			eb.setThumbnail("https://cdn.discordapp.com/attachments/811761680996368387/851620940320473118/check.png");
			message.getChannel().sendMessage(eb.build()).queue();

			for (String str : DatabaseManager.retrieveUsersInfo()) {
				String[] list = str.split("AaAaDqQqQqQqQq");
				Main.isUserRegistered.put(list[1], true);
				Main.userID.put(list[1], list[0]);
				Main.userBalance.put(list[1], list[2]);
				Main.userNovaMultiplier.put(list[1], list[3]);
			}
		}

		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle("Initiating Competition");
		eb.setAuthor(user.getAsTag(), null, user.getAvatarUrl());
		eb.setDescription(
				"\n≻─── ⋆✩⋆ ───≺\nYou have initiated a competition!\nChoose which category you desire to have a competition on!\n\nType either, easy, medium or hard after this message!\n≻─── ⋆✩⋆ ───≺\n");
		message.getChannel().sendMessage(eb.build()).queue();
		initiatingCompetition.put(user.getId(), true);

	}

	public static void pickEasyAnswer(User u, Guild g, Message m) {
		Random randomGenerator = new Random();
		int x = (int) randomGenerator.nextInt(24) + 1;
		questionCounter.put(g.getId(), questionCounter.get(g.getId()) + 1);
		if (questionCounter.get(g.getId()) == 7) {
			SendMessage.sendChannelMessage(m,
					"Game has ended! First place and second place gets rewards! Calculating results...", "", false);
			ArrayList<String> names = new ArrayList<>();
			ArrayList<Integer> point = new ArrayList<>();
			ArrayList<String> ids = new ArrayList<>();
			for (Map.Entry<String, Integer> set : points.entrySet()) {
				try {
					if (currentCompetitionGuild.get(set.getKey()) != null) {
						if (currentCompetitionGuild.get(set.getKey()).equals(g.getId())) {
							names.add(name.get(set.getKey()));
							point.add(points.get(set.getKey()));
							ids.add(set.getKey());
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			for (int q = 0; q != point.size(); q++) {
				if (q + 1 != point.size() - 1) {
					if (point.get(q) < point.get(q + 1)) {
						point.add(point.get(q));
						point.remove(q);
						ids.add(ids.get(q));
						ids.remove(q);
						names.add(names.get(q));
						names.remove(q);
						q = -1;
					}
				} else {
					break;
				}
			}
			
			DatabaseManager.updateUserBalance(Main.userID.get(ids.get(0)), String
					.valueOf(Double.valueOf(Main.userBalance.get(ids.get(0))) + isInComp.get(g.getId()).size() * 3));
			Main.userBalance.put(ids.get(0), String.valueOf(Double.valueOf(Main.userBalance.get(ids.get(0))) * 3));

			DatabaseManager.updateUserBalance(Main.userID.get(ids.get(1)), String.valueOf(
					Double.valueOf(Main.userBalance.get(ids.get(1))) + ((isInComp.get(g.getId()).size() * 3) / 2)));
			Main.userBalance.put(ids.get(1), String.valueOf(
					Double.valueOf(Main.userBalance.get(ids.get(1))) + ((isInComp.get(g.getId()).size() * 3) / 2)));
			String send = "";
			for (int d = 0; d != point.size(); d++) {
				send += "#" + (d+ 1) + ": " + names.get(d) + " | points: " + point.get(d) + "\n";
				
			}
			SendMessage.sendChannelMessage(m, send, "", false);
			startedCompetition.put(g.getId(), null);
			isInComp.put(g.getId(), new ArrayList<String>());
			for (Map.Entry<String, Integer> set : points.entrySet()) {
				try {
					if (currentCompetitionGuild.get(set.getKey()) != null) {
						if (currentCompetitionGuild.get(set.getKey()).equals(g.getId())) {
							name.remove(set.getKey());
							points.remove(set.getKey());
							currentCompetitionGuild.remove(set.getKey());
							startedCompetition.put(g.getId(), null);
							initiatingCompetition.put(u.getId(), null);
							questionCounter.put(g.getId(), 0);
							startQuestions.put(g.getId(), null);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return;
		}
		String question = "this is the question";
		switch (x) {
		case 1:
			question = "Calculate the following equation: 1.25 + 4.25";
			currentAnswer.put(g.getId(), "5.50");
			break;
		case 2:
			question = "Calculate the following equation: 4 + 4";
			currentAnswer.put(g.getId(), "8");
			break;
		case 3:
			question = "Copy the sentence: .,.,.,..,.";
			currentAnswer.put(g.getId(), ".,.,.,..,.");
			break;
		case 4:
			question = "Where is the Great Barrier Reef located? [Country]";
			currentAnswer.put(g.getId(), "Australia");
			break;
		case 5:
			question = "What is the rarest blood type? \nA: AB-Negative\nB: O\nC: B";
			currentAnswer.put(g.getId(), "A");
			break;
		case 6:
			question = "Who is the owner of this bot? \n\nA: Korbsti\nB: DerpMan432\nC: LonelyMaster\nD: Dovalin";
			currentAnswer.put(g.getId(), "A");
			break;
		case 7:
			question = "Quick! Copy the following sentence: But why do we have to copy this long sentence?";
			currentAnswer.put(g.getId(), "But why do we have to copy this long sentence?");
			break;
		case 8:
			question = "What’s the total number of dots on a pair of dice?";
			currentAnswer.put(g.getId(), "42");
			break;
		case 9:
			question = "Which planet is the closest to Earth?";
			currentAnswer.put(g.getId(), "Venus");
			break;
		case 10:
			question = "What is the name of the fairy in Peter Pan?";
			currentAnswer.put(g.getId(), "Tinkerbell");
			break;
		case 11:
			question = "Quick! You have to uh... :think: what does Peter Piper pick again?";
			currentAnswer.put(g.getId(), "Pickled Peppers");
			break;
		case 12:
			question = "Copy the following sentence: DERPPPPPPP";
			currentAnswer.put(g.getId(), "DERPPPPPPP");
			break;
		case 13:
			question = "Copy the following sentence: But wait theres more!";
			currentAnswer.put(g.getId(), "But wait theres more!");
			break;
		case 14:
			question = "Copy the following sentence: But do pickles taste good?";
			currentAnswer.put(g.getId(), "But do pickles taste good?");
			break;
		case 15:
			question = "Copy the following sentence: does pineapple belong on pizza?";
			currentAnswer.put(g.getId(), "does pineapple belong on pizza?");
			break;
		case 16:
			question = "Copy the following sentence: Rainbows and rainbows, may the force be with water";
			currentAnswer.put(g.getId(), "Rainbows and rainbows, may the force be with water");
			break;
		case 17:
			question = "Copy the following sentence: So much copying, why does one have to do this?";
			currentAnswer.put(g.getId(), "So much copying, why does one have to do this?");
			break;
		case 18:
			question = "Copy the following sentence: difficult words come with mawuawdiudwa";
			currentAnswer.put(g.getId(), "difficult words come with mawuawdiudwa");
			break;
		case 19:
			question = " How many strings does a violin have?";
			currentAnswer.put(g.getId(), "4");
			break;
		case 20:
			question = "What color is the circle on the Japanese national flag?";
			currentAnswer.put(g.getId(), "red");
			break;
		case 21:
			question = "What is the chemical symbol for Hydrogen?";
			currentAnswer.put(g.getId(), "H");
			break;
		case 22:
			question = "How many sides does an octagon have?";
			currentAnswer.put(g.getId(), "8");
			break;
		case 23:
			question = "Which fictional detective lived at 221B Baker Street, London?";
			currentAnswer.put(g.getId(), "Sherlock Holmes");
			break;
		case 24:
			question = "Calculate the following equation: (10 * 10)^2";
			currentAnswer.put(g.getId(), "10000");
			break;
		default:
			question = "Calculate the following equation: 10 / 10";
			currentAnswer.put(g.getId(), "1");
			break;
		}
		SendMessage.sendChannelMessage(m, question, "", false);
	}

}
