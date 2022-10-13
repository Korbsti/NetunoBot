package me.korbsti.netuno.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import me.korbsti.netuno.Main;
import me.korbsti.netuno.music.GuildMusicManager;
import me.korbsti.netuno.music.PlayerManager;
import me.korbsti.netuno.util.SendMessage;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

public class Queue {
	public static void queue(User user, Guild guild, Message message) {
		if (user.isBot() || !message.getContentDisplay().toLowerCase().startsWith(Main.guildPrefix.get(guild.getId()) + "queue") && !message.getContentDisplay().toLowerCase().startsWith(Main.guildPrefix.get(guild.getId()) + "q"))
			return;
		guild.loadMembers();
		final Member mem = guild.getMember(user);
		
		final GuildVoiceState selfVoiceState = guild.getMemberById("848669793678393415").getVoiceState();
		final GuildVoiceState memVoiceState = mem.getVoiceState();

        if(!selfVoiceState.inVoiceChannel()) {
			SendMessage.sendChannelMessage(message, "Not in a channel", "", false);
			return;
        }
        if (!memVoiceState.inVoiceChannel()) {
			SendMessage.sendChannelMessage(message, "Must be in a voice channel", "", false);
            return;
        }
		if(!memVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
			SendMessage.sendChannelMessage(message, "You need to be in the same voice channel as me", "", false);
			return;
		}		
		
		final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicMangaer(guild);
		final AudioPlayer audioPlayer = musicManager.audioPlayer;
		if(audioPlayer.getPlayingTrack() == null) {
			SendMessage.sendChannelMessage(message, "No track playing", "", false);
			return;
		}
		
		String msg = "Current tracks";
		int i = 0;
		for(AudioTrack AudioTrack : musicManager.scheduler.queue) {
			i++;
			msg += "\n" + i + ". " + AudioTrack.getInfo().title;
					
		}
		
		SendMessage.sendChannelMessage(message, msg, "", false);

	}
}
