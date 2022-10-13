package me.korbsti.netuno.events;

import java.nio.channels.Channel;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import me.korbsti.netuno.music.GuildMusicManager;
import me.korbsti.netuno.music.PlayerManager;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

public class GuildVoiceLeave extends ListenerAdapter {
	
	@Override
	public void onGuildVoiceLeave(GuildVoiceLeaveEvent event) {
		int x = 0;
		event.getChannelLeft().getGuild().loadMembers();
		for(Member mem : event.getChannelLeft().getMembers()) {
			if(!mem.getUser().isBot()) {
				x++;
			}
			if(mem.getId().equals("848669793678393415")) {
				if(x == 0) {
					
			        final AudioManager audioManager = event.getChannelLeft().getGuild().getAudioManager();

			        audioManager.closeAudioConnection();
				}
				
			}
		}
		System.out.println(x);
		
	}
}
