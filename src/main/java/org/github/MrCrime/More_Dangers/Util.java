package org.github.MrCrime.More_Dangers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Contains some useful static methods.
 */
public class Util {

	/**
	 * Returns a list of all six blocks 
	 * right next to the given block.
	 * 
	 * @param block
	 * 		The block to get the neighbours from
	 * @return
	 * 		The six blocks next to the given block
	 */
	public static List<Block> directNeighbours(Block block) {
		
		World world = block.getWorld();
		Location loc = block.getLocation();
		
		int x = (int) loc.getX();
		int y = (int) loc.getY();
		int z = (int) loc.getZ();
		
		List<Block> directNeighbours = new ArrayList<Block>();
		directNeighbours.add(world.getBlockAt(x + 1, y, z));
		directNeighbours.add(world.getBlockAt(x - 1, y, z));
		directNeighbours.add(world.getBlockAt(x, y + 1, z));
		directNeighbours.add(world.getBlockAt(x, y - 1, z));
		directNeighbours.add(world.getBlockAt(x, y, z + 1));
		directNeighbours.add(world.getBlockAt(x, y, z - 1));
		
		return directNeighbours;
	}
	
	/**
	 * Puts particles all around an entity until the entity dies.
	 * 
	 * @param effect
	 * 		The ParticleEffect type that should keep appearing
	 * @param entity
	 * 		The entity that should get the particles around it
	 */
	public static void displayParticleEntity(final Effect effect, final Entity entity) {
		
		new BukkitRunnable() {
			@Override
			public void run() {
				if (entity.isDead()) cancel();
				// Displaying the effect.
				entity.getWorld().playEffect(entity.getLocation(), effect, -1);
			}
		}.runTaskTimer(Main.getInstance(), 0, 15);
		
	}
	
	/**
	 * Returns a list of locations that form a sphere.
	 * 
	 * @param center
	 * 		The center location of the sphere
	 * @param radius
	 * 		The radius of the sphere
	 * @param hollow
	 * 		If the sphere is hollow or not
	 * @return
	 * 		a list of location that form a sphere
	 */
	public static List<Location> getSphere(Location center, int radius, boolean hollow) {
		List<Location> sphere = new ArrayList<Location>();
		
		int cX = center.getBlockX();
		int cY = center.getBlockY();
		int cZ = center.getBlockZ();
		
		for (int x = cX - radius; x <= cX + radius; x++) {
			for (int y = cY - radius; y <= cY + radius; y++) {
				for (int z = cZ - radius; z <= cZ + radius; z++) {
					
					int distanceSq = (cX-x)*(cX-x) + (cY-y)*(cY-y) + (cZ-z)*(cZ-z);
					
					if (distanceSq < radius * radius && 
							!(hollow && distanceSq < (radius-1)*(radius-1))) {
						Location l = new Location(center.getWorld(), x, y, z);
						sphere.add(l);
					}
					
				}
			}
		}
		
		return sphere;
	}
}
