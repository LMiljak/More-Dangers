package org.github.MrCrime.More_Dangers.nuclear_winter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.github.MrCrime.More_Dangers.Main;
import org.github.MrCrime.More_Dangers.Util;

/**
 * Responsible for keeping track of all air blocks that have been contaminated.
 */
public class ContaminatedBlocksHandler implements Listener {

	private HashSet<Block> sourceBlocks; // The very first blocks that have been
											// contaminated.
	private HashSet<Block> activeBlocks; // Contaminated blocks that want to
											// spread their contamination.
	private HashSet<Block> contaminatedBlocks; // Contaminated blocks that are
												// not activeBlocks.

	private ContaminatedBlocksVisualizer visualizer;

	private BukkitRunnable spreader = new BukkitRunnable() {
		@Override
		public void run() {
			spread();
		}
	};

	public ContaminatedBlocksHandler(Plugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);

		this.sourceBlocks = new HashSet<>();
		this.activeBlocks = new HashSet<>();
		this.contaminatedBlocks = new HashSet<>();

		this.visualizer = new ContaminatedBlocksVisualizer(this);
		this.spreader.runTaskTimer(plugin, 0, 15);
	}

	/**
	 * Called when a chunk has been loaded. This method adds all the air blocks
	 * on the top of the world to the source blocks.
	 * 
	 * @param event
	 *            The event thrown.
	 */
	int test = 0;
	@EventHandler(priority = EventPriority.MONITOR)
	public void onChunkLoaded(ChunkLoadEvent event) {
		test++;
		Main.getInstance().getServer().broadcastMessage("Chunk Loaded " + test);
		for (Block block : getTopBlocksInChunk(event.getChunk())) {
			if (contaminate(block)) {
				sourceBlocks.add(block);
			}
		}
	}

	/**
	 * Called when a chunk has been unloaded. Does the opposite of the
	 * onChunkLoaded method.
	 * 
	 * @param event
	 *            The event thrown.
	 */
	public void onChunkUnloaded(ChunkUnloadEvent event) {
		for (Block block : getTopBlocksInChunk(event.getChunk())) {
			sourceBlocks.remove(block);
			activeBlocks.remove(block);
			contaminatedBlocks.remove(block);
		}
	}

	/**
	 * Finds the highest blocks of a chunk.
	 * 
	 * @param chunk
	 * @return The top blocks of the given chunk.
	 */
	private Collection<Block> getTopBlocksInChunk(Chunk chunk) {
		final int maxX = 15;
		final int maxZ = 15;
		final int maxY = chunk.getWorld().getMaxHeight() - 1;

		Collection<Block> result = new ArrayList<>((maxX + 1) * (maxZ + 1));

		for (int x = 0; x < maxX; x++) {
			for (int z = 0; z < maxZ; z++) {
				result.add(chunk.getBlock(x, maxY, z));
			}
		}

		return result;
	}

	/**
	 * Spreads all activeBlocks to create more contaminated blocks.
	 */
	public void spread() {
		Main.getInstance().getServer().broadcastMessage("Active blocks: " + activeBlocks.size());
		Main.getInstance().getServer().broadcastMessage("Contaminated blocks: " + contaminatedBlocks.size());
		HashSet<Block> copyActiveBlocks = new HashSet<>(activeBlocks);
		for (Block block : copyActiveBlocks) {
			for (Block neighbour : Util.directNeighbours(block)) {
				if (!contaminatedBlocks.contains(neighbour) && !activeBlocks.contains(neighbour)) {
					contaminate(neighbour);
				}
			}
		}
		activeBlocks.removeAll(copyActiveBlocks);
		contaminatedBlocks.addAll(copyActiveBlocks);
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
		if (block.getType().isSolid() || !block.getChunk().isLoaded() || block.getY() > block.getWorld().getMaxHeight())
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
