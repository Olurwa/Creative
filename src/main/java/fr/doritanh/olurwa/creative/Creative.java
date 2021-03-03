package fr.doritanh.olurwa.creative;

import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.plugin.java.JavaPlugin;

import fr.doritanh.olurwa.creative.listeners.BungeeMessageListener;
import fr.doritanh.olurwa.creative.listeners.CoreMessageListener;
import fr.doritanh.olurwa.creative.listeners.PlayerListener;

public class Creative extends JavaPlugin {

	private static Creative instance;

	private World world;

	public Creative() {
		instance = this;
	}

	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);

		// Register channels
		this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new BungeeMessageListener());
		this.getServer().getMessenger().registerOutgoingPluginChannel(this, "olurwa:core");
		this.getServer().getMessenger().registerIncomingPluginChannel(this, "olurwa:core", new CoreMessageListener());

		for (World w : this.getServer().getWorlds()) {
			if (w.getEnvironment() == Environment.NORMAL) {
				this.world = w;
			}
		}
	}

	@Override
	public void onDisable() {
	}

	public static Creative get() {
		return Creative.instance;
	}

	public World getWorld() {
		return this.world;
	}
}
