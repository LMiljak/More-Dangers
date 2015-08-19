package org.github.MrCrime.More_Dangers.spider_mother;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Spider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.Plugin;
import org.github.MrCrime.More_Dangers.Main;
import org.github.MrCrime.More_Dangers.Util;

import com.darkblade12.particleeffect.ParticleEffect;

/**
 * Turns a fraction of the spiders that spawn into spider mothers.
 * 
 * Spider mothers can be recognised by the particle effect around them.
 * When they die, cobweb will appear all over the place and some cave
 * spiders will spawn around the spider mother.
 */
public class SpiderMotherSpawner implements Listener {

	private List<Spider> spider_mothers;
	
	/**
	 * Registers the SpiderMotherSpawner.
	 * 
	 * @param plugin
	 * 		The plugin the server uses.
	 */
	public SpiderMotherSpawner(Plugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		spider_mothers = new ArrayList<Spider>();
	}
	
	/**
	 * When a spider spawns, it has a chance to be added to the list
	 * of spider_mothers and particle effects will appear all around it.
	 * 
	 * @param event
	 */
	@EventHandler (priority = EventPriority.MONITOR)
	public void onSpiderSpawn(CreatureSpawnEvent event) {
		if (!event.getEntityType().equals(EntityType.SPIDER)) return;
		Spider spider = (Spider) event.getEntity();
		
		spider_mothers.add(spider);
		
		Util.displayParticleEntity(ParticleEffect.EXPLOSION_LARGE, spider);
	}
	
}
