package org.github.MrCrime.More_Dangers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

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
	
}
