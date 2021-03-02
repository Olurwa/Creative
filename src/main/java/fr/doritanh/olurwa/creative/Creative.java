package fr.doritanh.olurwa.creative;

import org.bukkit.plugin.java.JavaPlugin;

import fr.doritanh.olurwa.creative.listeners.PlayerListener;

public class Creative extends JavaPlugin {

	private static Creative instance;
	
	public Creative() {
		instance = this;
	}
	
	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
	}
	
	@Override
	public void onDisable() {}
	
	public static Creative get() {
		return Creative.instance;
	}
}
