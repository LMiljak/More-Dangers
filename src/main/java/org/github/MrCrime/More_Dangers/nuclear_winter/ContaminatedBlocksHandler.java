package org.github.MrCrime.More_Dangers.nuclear_winter;

import java.util.HashSet;

import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.plugin.Plugin;

/**
 * Responsible for keeping track of all air blocks that have been contaminated.
 */
public class ContaminatedBlocksHandler implements Listener {

	private HashSet<Block> sourceBlocks; // The very first blocks that have been
											// contaminated.
	private HashSet<Block> activeBlocks; // Contaminated blocks that want to
											// spread their contamination.
	private HashSet<Block> contaminatedBlocks; // Contaminated blocks that are
												// not in sourceBlocks and
												// activeBlocks.
	
	private ContaminatedBlocksVisualizer visualizer;

	public ContaminatedBlocksHandler(Plugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		
		this.sourceBlocks = new HashSet<>();
		this.activeBlocks = new HashSet<>();
		this.contaminatedBlocks = new HashSet<>();
		
		this.visualizer = new ContaminatedBlocksVisualizer(this);
	}

	/**
	 * Called when a chunk has been loaded. This method adds all the air blocks
	 * on the top of the world to the source blocks.
	 * 
	 * @param event
	 *            The event thrown.
	 */
	@EventHandler(priority = EventPriority.MONITOR)
	public void onChunkLoaded(ChunkLoadEvent event) {
		Chunk chunk = event.getChunk();

		final int maxX = 15;
		final int maxZ = 15;
		final int maxY = chunk.getWorld().getMaxHeight() - 1;

		for (int x = 0; x < maxX; x++) {
			for (int z = 0; z < maxZ; z++) {
				Block block = chunk.getBlock(x, maxY, z);

				// Contaminating the block and making it a source block.
				if (contaminate(block)) {
					sourceBlocks.add(block);
				}
			}
		}
	}

	/**
	 * Contaminates the given block if it's an empty block. In case the block
	 * was already contaminated, the block is considered active.
	 * 
	 * @param block
	 *            The block to contaminate.
	 * @return True iff the block has been successfully contaminated. False if
	 *         the block was already actively contaminated or is an AIR block.
	 */
	public boolean contaminate(Block block) {
		if (block.getType().isSolid())
			return false;

		activeBlocks.add(block);
		contaminatedBlocks.remove(block);

		return true;
	}

	public HashSet<Block> getSourceBlocks() {
		return sourceBlocks;
	}

	public HashSet<Block> getActiveBlocks() {
		return activeBlocks;
	}

	public HashSet<Block> getContaminatedBlocks() {
		return contaminatedBlocks;
	}

}
