package org.github.MrCrime.More_Dangers.spiderwebs;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;

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
		if (!damager.equals(EntityType.CAVE_SPIDER) && !damager.equals(EntityType.SPIDER) ) return;
		
		event.getEntity().getLocation().getBlock().setType(Material.WEB);
	}
	
}
