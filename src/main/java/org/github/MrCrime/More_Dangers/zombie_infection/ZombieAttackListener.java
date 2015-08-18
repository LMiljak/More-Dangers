package org.github.MrCrime.More_Dangers.zombie_infection;

import java.util.Collection;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.github.MrCrime.More_Dangers.Main;

/**
 * Listens for zombie attacking players.
 * When a player gets hit by a zombie, the player's
 * hunger gets lower. If the player's hunger is 50% or under
 * of the maximum health, the player gets the Zombie_Infection effect.
 * See the ZombieInfection class.
 */
public class ZombieAttackListener implements Listener {
	
	PotionEffect z_infection = 
			new PotionEffect(PotionEffectType.POISON, Integer.MAX_VALUE,0, false, true);
	
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
	 * @param enitity
	 * @return true if the given enitity has the z_infection.
	 */
	private boolean isInfected(LivingEntity entity) {
		if (entity.getType().equals(EntityType.ZOMBIE)) return true;
		
		Collection<PotionEffect> effects = entity.getActivePotionEffects();
		for (PotionEffect effect : effects) {
			if (effect.getType().equals(PotionEffectType.POISON) && effect.getDuration() > 10000) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Infects an entity using a z_infection.
	 * 
	 * @param entity
	 * 		The entity to be infected
	 */
	private void infect(LivingEntity entity) {
		entity.addPotionEffect(z_infection);
	}
	
	/**
	 * Does what is supposed to happen when a zombie attacks a player.
	 * 
	 * @param event
	 * 		The event thrown by the server
	 */
	@EventHandler (priority = EventPriority.MONITOR)
	public void onInfectedAttackEntity(EntityDamageByEntityEvent event) {
		if (!(event.getDamager() instanceof LivingEntity)
				|| !(event.getEntity() instanceof LivingEntity)) return;
		
		LivingEntity damager = (LivingEntity) event.getDamager();
		if (!isInfected(damager)) return;
		
		LivingEntity entity = (LivingEntity) event.getEntity();
		if (isInfected(entity)) return;
		
		if (entity instanceof Player) {
			Player player = (Player) entity;
			if (player.getFoodLevel() <= 10) {
				infect(player);
			}
			player.setFoodLevel(Math.max(0, player.getFoodLevel() - Main.getFoodDamage()));
		} else {
			infect(entity);
		}

	}

	/**
	 * Removes the z_infection from players that get full hunger.
	 * 
	 * @param event
	 * 		The event thrown by the server after a food level changes
	 */
	@EventHandler (priority = EventPriority.MONITOR)
	public void onHungerFilled(FoodLevelChangeEvent event) {
		if (!(event.getEntity() instanceof Player)) return;
		
		Player player = (Player) event.getEntity();
		
		if (isInfected(player) && event.getFoodLevel() >= 20) {
			player.removePotionEffect(PotionEffectType.POISON);
		}
			
	}
	
}
