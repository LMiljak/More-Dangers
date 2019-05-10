package org.github.MrCrime.More_Dangers.spider_mother;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;
import org.github.MrCrime.More_Dangers.Main;
import org.github.MrCrime.More_Dangers.Util;

/**
 * A nest consisting of cave spiders surrounded by
 * a sphere of cobweb. The cobweb will disappear when
 * all the cave spiders are gone.
 * 
 * A nest gets automatically destroyed after 60 seconds.
 */
public class SpiderNest {

	private List<CaveSpider> children;
	private List<Block> web;
	
	private boolean destroyed;
	
	/**
	 * Creates a new spider nest.
	 * 
	 * @param children
	 * 		The cave spiders inhabitants of the nest
	 * @param center
	 * 		The center of the nest
	 */
	public SpiderNest(Location center) {
		web = new ArrayList<Block>();
		children = new ArrayList<CaveSpider>();
		
		for (Location loc : Util.getSphere(center, Main.getNestRadius(), true)) {
			Block b = loc.getBlock();
			if (!b.getType().isSolid()){
				b.setType(Material.COBWEB);
				web.add(b);
			}
		}
		
		for (int i = 0; i < Main.getChildrenAmount(); i++) {
			children.add((CaveSpider) center.getWorld().spawnEntity(center, EntityType.CAVE_SPIDER));
		}
		
		destroyed = false;
		
		new BukkitRunnable() {

			@Override
			public void run() {
				if (!destroyed) destroy();
			}
			
		}.runTaskLater(Main.getInstance(), Main.getNestDespawnTime());
	}
	
	/**
	 * Deletes the child from the nest.
	 * If the child was the last, the nest gets destroyed.
	 * 
	 * @param entity
	 * 		The entity to remove
	 * @return
	 * 		An array of booleans where the first index is true iff
	 * 		the given entity was a child from this nest. The second 
	 * 		index is true if the web got destroyed.
	 */
	public boolean[] removeChild(Entity entity) {
		boolean[] res = new boolean[2];
		
		res[0] = children.remove(entity);
		if (!res[0]) return res;
		
		if (!children.isEmpty()) return res;
		
		destroy();
		res[1] = true;
		return res;
	}
	
	/**
	 * Destroys the web.
	 */
	private void destroy() {
		for (Block b : web) {
			if (b.getType().equals(Material.COBWEB)) {
				b.breakNaturally();
			}
		}
		
		destroyed = true;
	}
	
	
}
