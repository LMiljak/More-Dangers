package org.github.MrCrime.More_Dangers.zombie_infection;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.github.MrCrime.More_Dangers.Main;

/**
 * Listens for zombie attacking players.
 * When a player gets hit by a zombie, the player's
 * hunger gets lower. If the player's hunger is 50% or under
 * of the maximum health, the player gets the Zombie_Infection effect.
 * See the ZombieInfection class.
 */
public class ZombieAttackListener implements Listener {
	
	/**
	 * Creates a new zombie attack listener and registers it
	 * to the server.
	 * 
	 * @param plugin
	 * 		The plugin the server uses
	 */
	public ZombieAttackListener(Plugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	/**
	 * Does what is supposed to happen when a zombie attacks a player.
	 * 
	 * @param event
	 * 		The event thrown by the server
	 */
	@EventHandler (priority = EventPriority.MONITOR)
	public void onZombieAttack(EntityDamageByEntityEvent event) {
		if (!event.getDamager().getType().equals(EntityType.ZOMBIE)
				&& !(event.getEntity() instanceof Player)) return;
		
		Player player = (Player) event.getEntity();
		player.setFoodLevel(Math.max(0, player.getFoodLevel() - Main.getFoodDamage()));
		
		PotionEffect effect = new PotionEffect();
	}
	
}
