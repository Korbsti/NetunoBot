package me.korbsti.netuno.events;

import me.korbsti.netuno.mysql.DatabaseManager;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildJoin extends ListenerAdapter{

	public void onJoinGuild(GuildJoinEvent event) {
		DatabaseManager.createGuildPrefix(event.getGuild().getId(), "++");
	}
	
}
