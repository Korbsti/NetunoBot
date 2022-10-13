package me.korbsti.netuno.events;

import java.sql.SQLException;

import me.korbsti.netuno.Main;
import me.korbsti.netuno.mysql.Connect;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ShutdownEvent extends ListenerAdapter {

	public void shutdown(ShutdownEvent event) {
		try {
			Connect.connection.close();
			Main.builder.shutdown();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
