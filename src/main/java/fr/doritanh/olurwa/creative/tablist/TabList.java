package fr.doritanh.olurwa.creative.tablist;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import fr.doritanh.olurwa.creative.Creative;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_16_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_16_R3.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;
import net.minecraft.server.v1_16_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_16_R3.PlayerConnection;

public class TabList {

	private final String header;
	private final String footer;

	private final ScoreboardTab sTab;

	public TabList() {

		this.header = "§bOlurwa\\n ";
		this.footer = " \\n§cCréatif";

		this.sTab = new ScoreboardTab();
	}

	/**
	 * Send the tablist header and footer to player.
	 * 
	 * @param p - The player that will receive the packet.
	 */
	public void sendHeaderFooter(Player p) {
		IChatBaseComponent titleHeader = ChatSerializer.a("{\"text\": \"" + this.header + "\"}");
		IChatBaseComponent titleFooter = ChatSerializer.a("{\"text\": \"" + this.footer + "\"}");
		PacketPlayOutPlayerListHeaderFooter pHeaderFooter = new PacketPlayOutPlayerListHeaderFooter();
		try {
			pHeaderFooter.header = titleHeader;
			pHeaderFooter.footer = titleFooter;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(pHeaderFooter);
		}
	}

	/**
	 * Ask bungeecord to send a player list for each servers
	 */
	public void requestUpdateServers() {
		Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
		if (player == null)
			return;

		// Creative
		ByteArrayDataOutput lobby = ByteStreams.newDataOutput();
		lobby.writeUTF("PlayerList");
		lobby.writeUTF("lobby");
		player.sendPluginMessage(Creative.get(), "BungeeCord", lobby.toByteArray());
		// Survival
		ByteArrayDataOutput survival = ByteStreams.newDataOutput();
		survival.writeUTF("PlayerList");
		survival.writeUTF("survival");
		player.sendPluginMessage(Creative.get(), "BungeeCord", survival.toByteArray());
	}

	/**
	 * Update this server
	 */
	public void updateLocal() {
		this.sTab.updateLocal();
	}

	/**
	 * Update the creative player list
	 * 
	 * @param playersNames - List of player names
	 */
	public void updateLobby(String[] playersNames) {
		this.sTab.updateLobby(playersNames);
	}

	/**
	 * Send the player list
	 * 
	 * @param packetReceiver - The player who will receive the packet
	 */
	public void send(Player packetReceiver) {
		PlayerConnection pc = ((CraftPlayer) packetReceiver).getHandle().playerConnection;
		// Remove old players
		pc.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.REMOVE_PLAYER, this.sTab.getEntityRemoved()));
		// Set scoreboard to the packetreceiver
		packetReceiver.setScoreboard(this.sTab.getScoreboard());
		// Get all players and send them
		pc.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, this.sTab.getEntityPlayers()));
	}

}
