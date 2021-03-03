package fr.doritanh.olurwa.creative.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import fr.doritanh.olurwa.creative.Creative;

public class CoreMessageListener implements PluginMessageListener {

	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
		if (!channel.equals("olurwa:core")) {
			return;
		}
		ByteArrayDataInput in = ByteStreams.newDataInput(message);
		String subchannel = in.readUTF();
		if (subchannel.equals("PlayerList")) {
			String server = in.readUTF();
			String players = in.readUTF();
			String[] playerList = players.split(", ");
			if (server.equalsIgnoreCase("lobby")) {
				Creative.get().getTabList().updateLobby(playerList);
			}
			for (Player p : Bukkit.getOnlinePlayers()) {
				Creative.get().getTabList().send(p);
			}
		}
	}

}