package org.github.MrCrime.More_Dangers.burning_netherrack;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.github.MrCrime.More_Dangers.Plugin;

/**
 * Listens for players walking on netherrack, and
 * if the player isn't wearing any form of boots,
 * burns the player.
 */
public class NetherrackWalkListener implements Listener {

	/**
	 * Creates a new netherrack walk listener and registers it.
	 * 
	 * @param plugin
	 * 		the plugin that this server is using
	 */
	public NetherrackWalkListener(Plugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler (priority = EventPriority.MONITOR)
	public void onPlayerMoved(PlayerMoveEvent event) {
		Player p = event.getPlayer();
		
		if (p.getEquipment().getBoots() != null) return;

		Location loc = p.getLocation();
		Block playerBlock = loc.getBlock();
		
		loc.setY(loc.getY() - 1);
		if (loc.getBlock().getType().equals(Material.NETHERRACK)) {
			playerBlock.setType(Material.FIRE);
		}
	}
	
}
