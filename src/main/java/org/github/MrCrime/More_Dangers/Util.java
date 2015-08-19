package org.github.MrCrime.More_Dangers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

import com.darkblade12.particleeffect.ParticleEffect;

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
	public static void displayParticleEntity(final ParticleEffect effect, final Entity entity) {
		
		new BukkitRunnable() {
			@Override
			public void run() {
				if (entity.isDead()) cancel();
				effect.display(0, 0, 0, 1, 1, entity.getLocation().add(0, 1, 0), 30);
			}
		}.runTaskTimer(Main.getInstance(), 0, 15);
		
	}
}
