package org.github.MrCrime.More_Dangers.coal_explosion;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.github.MrCrime.More_Dangers.Main;

public class TorchNearCoalListener implements Listener {

	private ExplosionCauser explosionCauser = new ExplosionCauser();
	
	/**
	 * Registers this class to the server.
	 * 
	 * @param plugin
	 * 		The plugin that the server is using
	 */
	public TorchNearCoalListener(Main plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	/**
	 * Calls the coalExplosion method in the explosionCauser class
	 * when a player torch near coal ore and the chance of
	 * torch_explosion succeeds.
	 * 
	 * @param event
	 * 		The event being thrown after a block is placed
	 */
	@EventHandler (priority = EventPriority.MONITOR)
	public void onBlockPlace(BlockPlaceEvent event) {
		Material material = event.getBlock().getType();
		if (material.equals(Material.TORCH) || material.equals(Material.WALL_TORCH)) {
			Location loc = event.getBlock().getLocation();
			World world = loc.getWorld();
			
			int x = (int) loc.getX();
			int y = (int) loc.getY();
			int z = (int) loc.getZ();
			int range = Main.getTorchRange();
			Random r = new Random();
			
			for (int i = x - range; i <= x + range; i++) {
				for (int j = y - range; j <= y + range; j++) {
					for (int k = z - range; k <= z + range; k++) {
						Block b = world.getBlockAt(i, j, k);
						if (b.getType().equals(Material.COAL_ORE)) {
							if (Main.getTorchEChance() > r.nextDouble()) {
								explosionCauser.coalExplosion(b);
								return;
							}
						}
					}
				}
			}
		}
	}
	
}
