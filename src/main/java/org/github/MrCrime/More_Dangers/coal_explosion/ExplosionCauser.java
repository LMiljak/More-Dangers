package org.github.MrCrime.More_Dangers.coal_explosion;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;
import org.github.MrCrime.More_Dangers.Main;
import org.github.MrCrime.More_Dangers.Util;

/**
 * Has the ability to cause coal explosions.
 */
public class ExplosionCauser {

	/**
	 * Causes the given block to explode and any
	 * coal ore next to this block will also explode.
	 * 
	 * @param block
	 * 		the first block to explode
	 */
	public void coalExplosion(Block source) {
		World world = source.getWorld();
		
		LinkedList<Block> vein = getNeighbourCoal(source);
		
		world.playSound(source.getLocation(), Sound.FUSE, 1F, 1F);
		
		(new BukkitRunnable() {

			@Override
			public void run() {
				Block b = vein.poll();
				world.createExplosion(b.getLocation(), Main.getEPower());
				
				if (vein.isEmpty())
					cancel();
				
				for (Block block : vein) block.setType(Material.COAL_ORE);
			}
			
		}).runTaskTimer(Main.getInstance(), 60, Main.getEDelay());
		
	}
	
	/**
	 * Gets a list of all coal ore blocks
	 * in a coal ore vein.
	 * 
	 * @param source
	 * 		one of the coal ores of the vein
	 * @return
	 * 		all the coal ores in the vein of the given coal ore
	 */
	private LinkedList<Block> getNeighbourCoal(Block source) {
		LinkedList<Block> res = new LinkedList<Block>();
		
		Queue<Block> q = new LinkedList<Block>();
		q.add(source);
		res.add(source);
		
		while (!q.isEmpty()) {
			Block b = q.poll();

			List<Block> directNeighbours = Util.directNeighbours(b);
			
			for (Block n : directNeighbours) {
				if (!res.contains(n) && n.getType().equals(Material.COAL_ORE)) {
					q.add(n);
					res.add(n);
				}
			}
		}
		
		return res;
	}
	
}
