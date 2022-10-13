package me.korbsti.netuno.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import me.korbsti.netuno.Main;
import me.korbsti.netuno.music.GuildMusicManager;
import me.korbsti.netuno.music.PlayerManager;
import me.korbsti.netuno.util.SendMessage;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

public class NowPlaying {
	public static void nowplaying(User user, Guild guild, Message message) {
		if (user.isBot() || !message.getContentDisplay().toLowerCase().startsWith(Main.guildPrefix.get(guild.getId()) + "np") && !message.getContentDisplay().toLowerCase().startsWith(Main.guildPrefix.get(guild.getId()) + "nowplaying"))
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
		
		
		SendMessage.sendChannelMessage(message, "Track playing: " + musicManager.scheduler.player.getPlayingTrack().getInfo().title + "\nBy: " +  musicManager.scheduler.player.getPlayingTrack().getInfo().author + "\nTime: " + (musicManager.scheduler.player.getPlayingTrack().getPosition()/1000) + "s/" + (musicManager.scheduler.player.getPlayingTrack().getDuration()/1000) + "s", "", false);

	}
}
