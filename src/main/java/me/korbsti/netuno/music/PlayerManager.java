package me.korbsti.netuno.music;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import me.korbsti.netuno.util.SendMessage;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

public class PlayerManager {

	private static PlayerManager INSTANCE;

	private final Map<Long, GuildMusicManager> musicManagers;
	private final AudioPlayerManager audioPlayerManager;

	public PlayerManager() {
		this.musicManagers = new HashMap<>();
		this.audioPlayerManager = new DefaultAudioPlayerManager();

		AudioSourceManagers.registerRemoteSources(this.audioPlayerManager);
		AudioSourceManagers.registerLocalSource(this.audioPlayerManager);

	}

	public GuildMusicManager getMusicMangaer(Guild guild) {
		return this.musicManagers.computeIfAbsent(guild.getIdLong(), (guildID) -> {
			final GuildMusicManager guildMusicManager = new GuildMusicManager(this.audioPlayerManager);

			guild.getAudioManager().setSendingHandler(guildMusicManager.getSendHandler());
			return guildMusicManager;
		});
	}

	public void LoadAndPlay(TextChannel channel, String trackURL, boolean isURL) {
		final GuildMusicManager musicManager = this.getMusicMangaer(channel.getGuild());
		this.audioPlayerManager.loadItemOrdered(musicManager, trackURL, new AudioLoadResultHandler() {

			@Override
			public void trackLoaded(AudioTrack track) {
				musicManager.scheduler.queue(track);
				EmbedBuilder eb = new EmbedBuilder();
				eb.setDescription("Added to queue \n" + track.getInfo().title + "\nBy " + track.getInfo().author);
				channel.sendMessage(eb.build()).queue();

			}

			@Override
			public void playlistLoaded(AudioPlaylist playlist) {
				final List<AudioTrack> tracks = playlist.getTracks();
				if(isURL) {
					for (AudioTrack track : tracks) {
						if(track.getDuration()/1000 > 3600 * 7) {
							EmbedBuilder eb = new EmbedBuilder();
							eb.setDescription("Cannot play a track that is more than 7 hours long");
							channel.sendMessage(eb.build()).queue();
							return;
						}
						musicManager.scheduler.queue(track);
						
					}	
					EmbedBuilder eb = new EmbedBuilder();
					eb.setDescription("Added to queue \n" + String.valueOf(tracks.size()) + " tracks \n " + playlist.getName());
					channel.sendMessage(eb.build()).queue();
				} else {
					
					if(tracks.get(0).getDuration()/1000 > 3600 * 7) {
						EmbedBuilder eb = new EmbedBuilder();
						eb.setDescription("Cannot play a track that is more than 7 hours long");
						channel.sendMessage(eb.build()).queue();
						
						return;
					}
					
					musicManager.scheduler.queue(tracks.get(0));		
					EmbedBuilder eb = new EmbedBuilder();
					AudioTrack track = tracks.get(0);
					eb.setDescription("Added to queue \n" + track.getInfo().title + "\nBy " + track.getInfo().author);
					channel.sendMessage(eb.build()).queue();
				}
				


			}

			@Override
			public void noMatches() {
				// TODO Auto-generated method stub

			}

			@Override
			public void loadFailed(FriendlyException exception) {
				// TODO Auto-generated method stub

			}

		});

	}

	public static PlayerManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new PlayerManager();
		}

		return INSTANCE;
	}
}
