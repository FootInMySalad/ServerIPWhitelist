package io.github.footinmysalad.SpigotIPWhitelist;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class IPWhitelist extends JavaPlugin implements Listener {

	public static String kickMessage;

	@Override //This means use this void instead of the default spigot one (which is empty)
	public void onEnable() {
		saveDefaultConfig(); //This tells the server to save the default config.yml for this plugin if it doesn't exist yet
		if (getConfig().getString("kick-message") != null) kickMessage = ChatColor.translateAlternateColorCodes('&', getConfig().getString("kick-message"));

		System.out.println(ChatColor.GOLD + "IP is " + getConfig().getString("ip"));

		getServer().getPluginManager().registerEvents(this, this); //This tells the server to send events like the PlayerLoginEvent to this file
	}

	@EventHandler
	public void onPlayerLoginEvent(final PlayerLoginEvent event) { //This activates when a player joins
		getServer().broadcastMessage(ChatColor.GOLD + event.getHostname());
		if (!event.getHostname().equals(getConfig().getString("ip"))) {
			event.setKickMessage(ChatColor.GRAY + "You are trying to connect to " + ChatColor.GOLD + getConfig().getString("ip") + ChatColor.RESET.toString() + ChatColor.GRAY + " with the wrong ip.");
			this.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
				public void run() {
					event.getPlayer().kickPlayer(kickMessage); //This kicks the player with a specific message if they connected to the wrong ip
				}
			}, 2);
		}
	}
}
