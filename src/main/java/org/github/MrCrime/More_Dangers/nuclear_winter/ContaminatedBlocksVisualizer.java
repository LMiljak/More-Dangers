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

	private static final double DISPLAY_CHANCE_NORMAL = 0.01;
	private static final double DISPLAY_CHANCE_ACTIVE = 0.1;

	private HashSet<Block> activeBlocks;
	private HashSet<Block> contaminatedBlocks;

	private BukkitRunnable activeBlockDisplayer = new BukkitRunnable() {
		@Override
		public void run() {
			for (Block activeBlock : activeBlocks) {
				if (Math.random() < DISPLAY_CHANCE_ACTIVE) {
					visualize(activeBlock);
				}
			}
		}
	};

	private BukkitRunnable contaminatedBlockDisplayer = new BukkitRunnable() {
		@Override
		public void run() {
			for (Block block : contaminatedBlocks) {
				if (Math.random() < DISPLAY_CHANCE_NORMAL) {
					visualize(block);
				}
			}
		}
	};

	public ContaminatedBlocksVisualizer(ContaminatedBlocksHandler contaminatedBlockStorage) {
		this.activeBlocks = contaminatedBlockStorage.getActiveBlocks();
		this.contaminatedBlocks = contaminatedBlockStorage.getContaminatedBlocks();

		start();
	}

	/**
	 * Starts visualizing the blocks.
	 */
	public void start() {
		activeBlockDisplayer.runTaskTimer(Main.getInstance(), 0, 30);
		contaminatedBlockDisplayer.runTaskTimer(Main.getInstance(), 0, 30);
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
		final Particle particle = Particle.SMOKE_LARGE;
		final int count = 1;

		block.getWorld().spawnParticle(particle, block.getLocation(), count);
	}

}
