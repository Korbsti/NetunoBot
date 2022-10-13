package me.korbsti.netuno.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import me.korbsti.netuno.Main;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;

public class DatabaseManager {

	public static void createGuildsTable(String table) {
		try {
			Connect.connection.prepareStatement("CREATE TABLE IF NOT EXISTS " + table + "(id int NOT NULL AUTO_INCREMENT, guildID varchar(255), guildPrefix varchar(255), PRIMARY KEY(id))")
					.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void createGuildPrefix(String id, String prefix) {
		try {
			Connect.connection
					.prepareStatement(
							"INSERT INTO guilds (guildID, guildPrefix) VALUES ('" + id + "', '" + prefix + "')")
					.executeUpdate();
			Main.guildPrefix.put(id, prefix);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public static void updateGuildPrefix(String id, String prefix) {
		try {
			// UPDATE `guilds` SET `guildPrefix` = 'test' WHERE `guilds`.`id` = 17;

			Connect.connection
					.prepareStatement("UPDATE guilds SET guildPrefix = '" + prefix + "' WHERE guilds.id = " + id)
					.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static ArrayList<String> retrieveGuildID() {
		try {
			PreparedStatement stmt = Connect.connection.prepareStatement("SELECT guildID,guildPrefix,id from guilds");

			ResultSet set = stmt.executeQuery();
			ArrayList<String> returning = new ArrayList<String>();
			while (set.next()) {
				returning.add(set.getString(1) + "AaAaDqQqQqQqQq" + set.getString(2) + "AaAaDqQqQqQqQq" + set.getString(3));
			}
			return returning;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	/////////////

	public static void createTrophiesTable(String table) {
		try {
			Connect.connection.prepareStatement("CREATE TABLE IF NOT EXISTS " + table + "(id int NOT NULL AUTO_INCREMENT, userID varchar(255), username varchar(255) , bronze varchar(255), silver varchar(255), gold varchar(255), titanium varchar(255), platinum varchar(255), PRIMARY KEY(id))")
					.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void insertTrophiesTable(String id, String name) {
		try {
			Connect.connection
					.prepareStatement("INSERT INTO trophies (userID, username, bronze, silver, gold, titanium, platinum) VALUES ('" + id + "', '"+name+"', '0', '0', '0', '0', '0')")
					.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void updateTrophiesTable(String id, String typeOfTrophie, String newNum) {
		try {
			// UPDATE `guilds` SET `guildPrefix` = 'test' WHERE `guilds`.`id` = 17;

			Connect.connection
					.prepareStatement(
							"UPDATE trophies SET " + typeOfTrophie + " = '" + newNum + "' WHERE trophies.userID = " + id)
					.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void updateTrophiesUsername(String id, String username) {
		try {
			// UPDATE `guilds` SET `guildPrefix` = 'test' WHERE `guilds`.`id` = 17;

			Connect.connection
					.prepareStatement(
							"UPDATE trophies SET " + "username" + " = '" + username + "' WHERE trophies.userID = " + id)
					.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static ArrayList<String> returnTrophies() {
		try {
			PreparedStatement stmt = Connect.connection.prepareStatement("SELECT id,username,userID,bronze,silver,gold,titanium,platinum from trophies");

			ResultSet set = stmt.executeQuery();
			ArrayList<String> returning = new ArrayList<String>();
			while (set.next()) {
				returning.add(set.getString(1) + "AaAaDqQqQqQqQq" + set.getString(2) + "AaAaDqQqQqQqQq" + set.getString(3) + "AaAaDqQqQqQqQq" + set.getString(4) + "AaAaDqQqQqQqQq" + set.getString(5)+ "AaAaDqQqQqQqQq" + set.getString(6)+ "AaAaDqQqQqQqQq" + set.getString(7) + "AaAaDqQqQqQqQq" + set.getString(8));
			}
			return returning;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	
	/////////////

	public static void createUsersTable(String table) {
		try {
			Connect.connection.prepareStatement("CREATE TABLE IF NOT EXISTS " + table
					+ "(id int NOT NULL AUTO_INCREMENT, userID varchar(255), balance varchar(255), novasapphires varchar(255), PRIMARY KEY(id))")
					.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<String> retrieveUsersInfo() {
		try {
			PreparedStatement stmt = Connect.connection
					.prepareStatement("SELECT id,userID,balance,novasapphires from users");

			ResultSet set = stmt.executeQuery();
			ArrayList<String> returning = new ArrayList<String>();
			while (set.next()) {
				returning.add(
						set.getString(1) + "AaAaDqQqQqQqQq" + set.getString(2) + "AaAaDqQqQqQqQq" + set.getString(3) + "AaAaDqQqQqQqQq" + set.getString(4));
			}
			return returning;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void updateUserBalance(String id, String newBalance) {
		try {

			Connect.connection
					.prepareStatement("UPDATE users SET balance = '" + newBalance + "' WHERE users.id = " + id)
					.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void updateUserNova(User user, String id, String novasapphires) {
		try {

			Connect.connection
					.prepareStatement("UPDATE users SET novasapphires = '" + novasapphires + "' WHERE users.id = " + id)
					.executeUpdate();

			Main.userNovaMultiplier.put(user.getId(), novasapphires);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		}
	}

	public static void createUserInfo(String id, String balance, String nova, String username) {
		try {
			Connect.connection.prepareStatement("INSERT INTO users (userID, balance, novasapphires) VALUES ('" + id
					+ "', '" + balance + "', '" + nova + "')").executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
