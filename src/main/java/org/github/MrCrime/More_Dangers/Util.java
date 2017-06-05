package org.github.MrCrime.More_Dangers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Contains some useful static methods.
 */
public class Util {

	/**
	 * Returns a list of all six blocks right next to the given block. If the
	 * neighbouring blocks are in unloaded chunks, then they're not included in
	 * the list.
	 * 
	 * @param block
	 *            The block to get the neighbours from
	 * @return The six blocks next to the given block
	 */
	public static List<Block> directNeighbours(Block block) {
		World world = block.getWorld();
		int maxY = world.getMaxHeight();

		int x = block.getX();
		int y = block.getY();
		int z = block.getZ();

		List<Block> directNeighbours = new ArrayList<Block>(6);
		if (world.isChunkLoaded((x + 1) >> 4, z >> 4)) {
			directNeighbours.add(world.getBlockAt(x + 1, y, z));
		}
		if (world.isChunkLoaded((x - 1) >> 4, z >> 4)) {
			directNeighbours.add(world.getBlockAt(x - 1, y, z));
		}
		if (y + 1 < maxY) {
			directNeighbours.add(world.getBlockAt(x, y + 1, z));
		}
		if (y - 1 >= 0) {
			directNeighbours.add(world.getBlockAt(x, y - 1, z));
		}
		if (world.isChunkLoaded(x, (z + 1) >> 4)) {
			directNeighbours.add(world.getBlockAt(x, y, z + 1));
		}
		if (world.isChunkLoaded(x, (z - 1) >> 4)) {
			directNeighbours.add(world.getBlockAt(x, y, z - 1));
		}

		return directNeighbours;
	}

	/**
	 * Puts particles all around an entity until the entity dies.
	 * 
	 * @param effect
	 *            The ParticleEffect type that should keep appearing
	 * @param entity
	 *            The entity that should get the particles around it
	 */
	public static void displayParticleEntity(final Particle effect, final int count, final Entity entity) {

		new BukkitRunnable() {
			@Override
			public void run() {
				if (entity.isDead())
					cancel();
				// Displaying the particle.
				entity.getWorld().spawnParticle(effect, entity.getLocation(), count);
			}
		}.runTaskTimer(Main.getInstance(), 0, 15);

	}

	/**
	 * Returns a list of locations that form a sphere.
	 * 
	 * @param center
	 *            The center location of the sphere
	 * @param radius
	 *            The radius of the sphere
	 * @param hollow
	 *            If the sphere is hollow or not
	 * @return a list of location that form a sphere
	 */
	public static List<Location> getSphere(Location center, int radius, boolean hollow) {
		List<Location> sphere = new ArrayList<Location>();

		int cX = center.getBlockX();
		int cY = center.getBlockY();
		int cZ = center.getBlockZ();

		for (int x = cX - radius; x <= cX + radius; x++) {
			for (int y = cY - radius; y <= cY + radius; y++) {
				for (int z = cZ - radius; z <= cZ + radius; z++) {

					int distanceSq = (cX - x) * (cX - x) + (cY - y) * (cY - y) + (cZ - z) * (cZ - z);

					if (distanceSq < radius * radius && !(hollow && distanceSq < (radius - 1) * (radius - 1))) {
						Location l = new Location(center.getWorld(), x, y, z);
						sphere.add(l);
					}

				}
			}
		}

		return sphere;
	}
}
