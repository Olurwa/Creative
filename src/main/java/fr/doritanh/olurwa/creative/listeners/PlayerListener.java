package fr.doritanh.olurwa.creative.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import fr.doritanh.olurwa.creative.Creative;

public class PlayerListener implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		e.setJoinMessage(null);

		Creative.get().getTabList().sendHeaderFooter(e.getPlayer());
		Creative.get().getTabList().updateLocal();

		for (Player p : Bukkit.getOnlinePlayers()) {
			Creative.get().getTabList().send(p);
		}

		new BukkitRunnable() {
			@Override
			public void run() {
				Creative.get().getTabList().requestUpdateServers();

			}
		}.runTaskLater(Creative.get(), 20);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		e.setQuitMessage(null);
		new BukkitRunnable() {
			@Override
			public void run() {
				Creative.get().getTabList().updateLocal();

			}
		}.runTaskLater(Creative.get(), 20);

		for (Player p : Bukkit.getOnlinePlayers()) {
			Creative.get().getTabList().send(p);
		}

		new BukkitRunnable() {
			@Override
			public void run() {
				Creative.get().getTabList().requestUpdateServers();

			}
		}.runTaskLater(Creative.get(), 20);
	}

}
