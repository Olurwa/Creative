package fr.doritanh.olurwa.creative.listeners;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class CoreMessageListener implements PluginMessageListener {

	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
		if (!channel.equals("olurwa:core")) {
			return;
		}
	}

}