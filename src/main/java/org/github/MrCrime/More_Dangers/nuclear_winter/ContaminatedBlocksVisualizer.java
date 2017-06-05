package org.github.MrCrime.More_Dangers.nuclear_winter;

import java.util.HashSet;

import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;
import org.github.MrCrime.More_Dangers.Main;

/**
 * Uses particle effects to visualize contaminated blocks.
 */
public class ContaminatedBlocksVisualizer {

	private static final double DISPLAY_CHANCE = 0.1;
	
	private HashSet<Block> sourceBlocks;
	private HashSet<Block> activeBlocks;
	private HashSet<Block> contaminatedBlocks;

	private BukkitRunnable activeBlockDisplayer = new BukkitRunnable() {
		@Override
		public void run() {
			for (Block activeBlock : activeBlocks) {
				visualize(activeBlock);
			}
		}
	};
	
	private BukkitRunnable contaminatedBlockDisplayer = new BukkitRunnable() {
		@Override
		public void run() {
			for (Block block : sourceBlocks) {
				if (Math.random() < DISPLAY_CHANCE) {
					visualize(block);
				}
			}
			for (Block block : contaminatedBlocks) {
				if (Math.random() < DISPLAY_CHANCE) {
					visualize(block);
				}
			}
		}
	};
	
	public ContaminatedBlocksVisualizer(ContaminatedBlocksHandler contaminatedBlockStorage) {
		this.sourceBlocks = contaminatedBlockStorage.getSourceBlocks();
		this.activeBlocks = contaminatedBlockStorage.getActiveBlocks();
		this.contaminatedBlocks = contaminatedBlockStorage.getContaminatedBlocks();
		
		start();
	}
	
	/**
	 * Starts visualizing the blocks.
	 */
	public void start() {
		activeBlockDisplayer.runTaskTimerAsynchronously(Main.getInstance(), 0, 15);
		contaminatedBlockDisplayer.runTaskTimerAsynchronously(Main.getInstance(), 0, 15);
	}
	
	/**
	 * Cancels visualizing the blocks.
	 */
	public void cancel() {
		activeBlockDisplayer.cancel();
		contaminatedBlockDisplayer.cancel();
	}
	
	/**
	 * Displays the appropriate particle effect.
	 * 
	 * @param block
	 *            The block that should be visualized.
	 */
	public void visualize(Block block) {
		final Particle particle = Particle.SMOKE_NORMAL;
		final int count = 1;
		
		block.getWorld().spawnParticle(particle, block.getLocation(), count);
	}

}
