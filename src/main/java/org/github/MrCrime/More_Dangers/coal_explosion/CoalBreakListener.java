package org.github.MrCrime.More_Dangers.coal_explosion;

import java.util.Random;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;
import org.github.MrCrime.More_Dangers.Main;

/**
 * Listens for players breaking coal ore and after a player
 * breaks a coal ore, has a chance of causing a coal explosion.
 */
public class CoalBreakListener implements Listener {

	private ExplosionCauser explosionCauser = new ExplosionCauser();
	
	/**
	 * Creates a new coal break listener and registers it.
	 * 
	 * @param plugin
	 * 		The plugin that the server is using
	 */
	public CoalBreakListener(Plugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	/**
	 * Calls the coalExplosion method in the explosionCauser class
	 * when a player breaks a coal ore block and the chance of
	 * break_explosion succeeds.
	 * 
	 * @param event
	 * 		The event being thrown after a block is broken
	 */
	@EventHandler (priority = EventPriority.MONITOR)
	public void onBlockBreak(BlockBreakEvent event) {
		if (event.getBlock().getType().equals(Material.COAL_ORE) 
				&& !event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
			if (Main.getBreakEChance() > new Random().nextDouble()) {
				explosionCauser.coalExplosion(event.getBlock());
			}
		}
	}
	
}
