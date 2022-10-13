package me.korbsti.netuno.util;

import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

public class SendMessage {
	public static void sendChannelMessage(Message message, String content, String img, boolean useImage) {
		EmbedBuilder eb = new EmbedBuilder();
		eb.setColor(new Color(61, 237, 169));
		eb.setDescription(content);
		if (useImage) {
			eb.setThumbnail(img);
		}
		message.getChannel().sendMessage(eb.build()).queue();
	}
}
