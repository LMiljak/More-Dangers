package org.github.MrCrime.More_Dangers.spider_mother;

import java.util.List;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Spider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.Plugin;
import org.github.MrCrime.More_Dangers.Main;

public class SpiderMotherSpawner implements Listener {

	List<Spider> spider_mothers;
	
	public SpiderMotherSpawner(Plugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler (priority = EventPriority.MONITOR)
	public void onSpiderSpawn(CreatureSpawnEvent event) {
		if (!event.getEntityType().equals(EntityType.SPIDER)) return;
		Spider spider = (Spider) event.getEntity();
		
	}
	
}
