package me.korbsti.netuno;

import java.util.HashMap;

import javax.security.auth.login.LoginException;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;

import me.korbsti.netuno.currency.Timer;
import me.korbsti.netuno.events.GuildJoin;
import me.korbsti.netuno.events.GuildVoiceLeave;
import me.korbsti.netuno.events.MessageRecieve;
import me.korbsti.netuno.events.ShutdownEvent;
import me.korbsti.netuno.mysql.Connect;
import me.korbsti.netuno.mysql.DatabaseManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.managers.AudioManager;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class Main {

	public static JDA builder;
	public static HashMap<String, String> guildPrefix = new HashMap<>();
	public static HashMap<String, String> guildID = new HashMap<>();
	public static HashMap<String, String> guildDB = new HashMap<>();

	public static HashMap<String, Boolean> isUserRegistered = new HashMap<>();
	public static HashMap<String, String> userBalance = new HashMap<>();
	public static HashMap<String, String> userID = new HashMap<>();
	public static HashMap<String, String> userNovaMultiplier = new HashMap<>();
	public static HashMap<String, Double> generatedRandomCash = new HashMap<>();
	public static HashMap<String, String> trophiesID = new HashMap<>();
	public static AudioPlayerManager playerManager;	
	
	public static void main(String[] args) {
		String token = "ODQ4NjY5NzkzNjc4MzkzNDE1.YLP_Vg.HKVgnEJLJhxj6G32JcHnQEkG6lI";
		try {
			builder = JDABuilder.createDefault(token)
			          .setChunkingFilter(ChunkingFilter.ALL) 
			          .setMemberCachePolicy(MemberCachePolicy.ALL) 
			          .enableIntents(GatewayIntent.GUILD_MEMBERS)
			          .enableIntents(GatewayIntent.GUILD_MESSAGE_REACTIONS)
			          .enableIntents(GatewayIntent.GUILD_VOICE_STATES)
			          .enableCache(CacheFlag.VOICE_STATE)
			          .build();
			builder.addEventListener(new MessageRecieve());
			builder.addEventListener(new GuildJoin());
			builder.addEventListener(new ShutdownEvent());
			builder.addEventListener(new GuildVoiceLeave());
			builder.getPresence().setActivity(Activity.playing("++help"));
			// Currently undergoing maintenance
			
		} catch (LoginException e) {
			e.printStackTrace();
		}
		// //jdbc:mysql://u119_Sc1h2qipRm:82BGD=sR=if6dVUaTT@Cw@y6@127.0.0.1:3306/s119_netuno
		// "jdbc:mysql://localhost/netuno", "u119_Sc1h2qipRm", "",
		// "com.mysql.cj.jdbc.Driver"

		
		//mysql://customer_205127_netuno:JugJsj!zp!rsLkisA5pT@na05-sql.pebblehost.com/customer_205127_netuno
		
		
		Connect.connectMySQL("jdbc:mysql://na05-sql.pebblehost.com/customer_205127_netuno", "customer_205127_netuno",
				"JugJsj!zp!rsLkisA5pT", "com.mysql.cj.jdbc.Driver");
		DatabaseManager.createGuildsTable("guilds");
		DatabaseManager.createUsersTable("users");
		DatabaseManager.createTrophiesTable("trophies");
		
		
		for (String str : DatabaseManager.retrieveGuildID()) {
			String[] list = str.split("AaAaDqQqQqQqQq");
			guildID.put(list[2], list[1]);
			guildPrefix.put(list[0], list[1]);
			guildDB.put(list[0], list[2]);
			generatedRandomCash.put(list[0], 0.0);
		}
		for (String str : DatabaseManager.retrieveUsersInfo()) {
			String[] list = str.split("AaAaDqQqQqQqQq");
			isUserRegistered.put(list[1], true);
			userID.put(list[1], list[0]);
			userBalance.put(list[1], list[2]);
			userNovaMultiplier.put(list[1], list[3]);
		}

		for (Guild guild : builder.getGuilds()) {
			if (guildPrefix.get(guild.getId()) == null) {
				guildPrefix.put(guild.getId(), "**");
				DatabaseManager.updateGuildPrefix(Main.guildDB.get(guild.getId()), "**");
			}
		}

		Timer.timer();
	}

}
