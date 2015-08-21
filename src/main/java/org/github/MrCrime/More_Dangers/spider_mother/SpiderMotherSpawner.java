package org.github.MrCrime.More_Dangers.spider_mother;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Spider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.github.MrCrime.More_Dangers.Main;
import org.github.MrCrime.More_Dangers.Util;

import com.darkblade12.particleeffect.ParticleEffect;

/**
 * Turns a fraction of the spiders that spawn into spider mothers.
 * 
 * Spider mothers can be recognised by the particle effect around them.
 * When they die, cobweb will appear all over the place and some cave
 * spiders will spawn around the spider mother (spider nest).
 * 
 * The spider nest will disappear when all the cave spiders that spawned
 * with the spider are gone.
 */
public class SpiderMotherSpawner implements Listener {

	private List<Spider> spider_mothers;
	private List<SpiderNest> nests;
	
	/**
	 * Registers the SpiderMotherSpawner.
	 * 
	 * @param plugin
	 * 		The plugin the server uses.
	 */
	public SpiderMotherSpawner(Plugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		spider_mothers = new ArrayList<Spider>();
		nests = new ArrayList<SpiderNest>();
	}
	
	/**
	 * When a spider spawns, it has a chance to be added to the list
	 * of spider_mothers and particle effects will appear all around it.
	 * 
	 * @param event
	 * 		The event thrown by the server.
	 */
	@EventHandler (priority = EventPriority.MONITOR)
	public void onSpiderSpawn(CreatureSpawnEvent event) {
		if (!event.getEntityType().equals(EntityType.SPIDER) || 
				new Random().nextDouble() > Main.getSMChance()) return;
		Spider spider = (Spider) event.getEntity();
		
		spider_mothers.add(spider);
		
		Util.displayParticleEntity(ParticleEffect.EXPLOSION_LARGE, spider);
	}
	
	/**
	 * Creates a SpiderNest around the corpse of a
	 * spider mother after the mother dies.
	 * 
	 * @param event
	 * 		The event thrown by the server
	 */
	@EventHandler (priority = EventPriority.MONITOR) 
	public void onSpiderMotherDeath(EntityDeathEvent event) {
		if (!spider_mothers.contains(event.getEntity())) return;
		
		final SpiderNest nest = new SpiderNest(event.getEntity().getLocation());
		nests.add(nest);
		
		new BukkitRunnable() {

			@Override
			public void run() {
				nests.remove(nest);
			}
			
		}.runTaskLater(Main.getInstance(), Main.getWebDespawnTime() + 100);
	}
	
	/**
	 * Destroys a nest when the last child of that nest dies.
	 * 
	 * @param event
	 * 		The event thrown by the server
	 */
	@EventHandler (priority = EventPriority.MONITOR)
	public void onChildDeath(EntityDeathEvent event) {
		
		for (SpiderNest nest : nests) {
			boolean[] bools = nest.removeChild(event.getEntity());
			if (!bools[0]) continue;
			if (bools[1]) {
				nests.remove(nest);
				return;
			}
		}
		
		
	}
	
}
