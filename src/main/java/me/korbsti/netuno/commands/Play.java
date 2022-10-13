package me.korbsti.netuno.commands;

import java.net.URI;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import me.korbsti.netuno.Main;
import me.korbsti.netuno.music.PlayerManager;
import me.korbsti.netuno.music.TrackScheduler;
import me.korbsti.netuno.util.SendMessage;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class Play {
	public static void play(User user, Guild guild, Message message){
		if (user.isBot() || !message.getContentDisplay().toLowerCase().startsWith(Main.guildPrefix.get(guild.getId()) + "play"))
			return;
		
		String[] args = message.getContentDisplay().split(" ");
		if(args.length == 1 || args.length == 2) {
			SendMessage.sendChannelMessage(message, "Invalid arguments, must have two arguments'", "", false);
			return;
			
		}
		
		guild.loadMembers();
		final Member mem = guild.getMember(user);
		
		final GuildVoiceState selfVoiceState = guild.getMemberById("848669793678393415").getVoiceState();
		final GuildVoiceState memVoiceState = mem.getVoiceState();

		
        if (!memVoiceState.inVoiceChannel()) {
			SendMessage.sendChannelMessage(message, "Must be in a voice channel", "", false);
            return;
        }
        boolean joinedChannel = false;
		if(!selfVoiceState.inVoiceChannel()) {
	        final AudioManager audioManager = guild.getAudioManager();
	        final VoiceChannel memberChannel = memVoiceState.getChannel();

	        audioManager.openAudioConnection(memberChannel);
			SendMessage.sendChannelMessage(message, "Joining channel...", "", false);
			joinedChannel = true;
		}
		if(!memVoiceState.getChannel().equals(selfVoiceState.getChannel()) && !joinedChannel) {
			SendMessage.sendChannelMessage(message, "You need to be in the same voice channel as me", "", false);
			return;
		}		
		String link = String.join(" ", args);
		link = link.substring(7, link.length());
		
		boolean isURL = false;
		if(!isURL(link)) {
			link = "ytsearch:" + link;
		} else {
			isURL = true;
		}
		PlayerManager.getInstance().LoadAndPlay(message.getTextChannel(), link, isURL);
		
	}
	
	
	private static boolean isURL(String url) {
		try {
			new URI(url);
			return true;
		} catch(Exception e) {
			return false;
		}
		
		
	}
}
