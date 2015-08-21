package org.github.MrCrime.More_Dangers.spiderwebs;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.github.MrCrime.More_Dangers.Main;

/**
 * Listens for attacking spiders and places cobweb
 * on an entity after a spider hits it.
 */
public class SpiderAttackListener implements Listener {

	/**
	 * Creates a new spider attack listener and registers it.
	 * 
	 * @param plugin
	 * 		The plugin the server uses
	 */
	public SpiderAttackListener(Plugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	/**
	 * Places cobweb on an entity when it gets attacked
	 * by a spider.
	 */
	@EventHandler (priority = EventPriority.MONITOR)
	public void onSpiderAttack(EntityDamageByEntityEvent event) {
		EntityType damager = event.getDamager().getType();
		if (!damager.equals(EntityType.CAVE_SPIDER) && !damager.equals(EntityType.SPIDER)) return;
		if (new Random().nextDouble() > Main.getWebChance()) return;
		
		final Block b = event.getEntity().getLocation().getBlock();
		b.setType(Material.WEB);
		
		new BukkitRunnable() {
			@Override
			public void run() {
				if (b.getType().equals(Material.WEB)) b.breakNaturally();
			}
		}.runTaskLater(Main.getInstance(), Main.getWebDespawnTime());
	}
	
}
