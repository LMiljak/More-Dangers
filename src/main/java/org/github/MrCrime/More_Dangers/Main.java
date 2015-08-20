package org.github.MrCrime.More_Dangers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.github.MrCrime.More_Dangers.burning_netherrack.NetherrackWalkListener;
import org.github.MrCrime.More_Dangers.coal_explosion.CoalBreakListener;
import org.github.MrCrime.More_Dangers.coal_explosion.TorchNearCoalListener;
import org.github.MrCrime.More_Dangers.spider_mother.SpiderMotherSpawner;
import org.github.MrCrime.More_Dangers.spiderwebs.SpiderAttackListener;
import org.github.MrCrime.More_Dangers.zombie_infection.ZombieAttackListener;

/**
 * The "main" class of the plugin.
 */
public class Main extends JavaPlugin {
    
	//Coal-explosion settings
	private static double break_explosionChance;
	private static double torch_explosionChance;
	private static int torch_range;
	private static float explosion_power;
	private static long explosion_delay;
	
	//Zombie-infection settings
	private static int zombie_food_damage;
	
	//Spider-mother settings
	private static double mother_chance;
	
	private static Main instance;
	
	private FileConfiguration config;
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		readConfig();
		
		instance = this;
		
		new CoalBreakListener(this);
		new TorchNearCoalListener(this);
		new NetherrackWalkListener(this);
		new SpiderAttackListener(this);
		new ZombieAttackListener(this);
		new SpiderMotherSpawner(this);
	}
	
	private void readConfig() {
		config = getConfig();
		
		break_explosionChance = config.getDouble("CE break explosion chance");
		torch_explosionChance = config.getDouble("CE torch explosion chance");
		torch_range = config.getInt("CE torch range");
		explosion_power = (float) config.getDouble("CE explosion power");
		explosion_delay = config.getLong("CE explosion delay");
		
		zombie_food_damage = config.getInt("ZI food damage");
		
		mother_chance = config.getDouble("SM chance");
	}
	
	/**
	 * @return the instance of this plugin.
	 */
	public static Main getInstance() {
		return instance;
	}

	/**
	 * @return the chance a coal explosion will occur after breaking a coal block.
	 */
	public static double getBreakEChance() {
		return break_explosionChance;
	}
	
	/**
	 * @return the chance a coal explosion will occur after placing a torch near a coal block.
	 */
	public static double getTorchEChance() {
		return torch_explosionChance;
	}
	
	/**
	 * @return the amount of blocks a torch can be from coal ore to set of an explosion.
	 */
	public static int getTorchRange() {
		return torch_range;
	}
	
	/**
	 * @return the power of an explosion (4.0 being TNT).
	 */
	public static float getEPower() {
		return explosion_power;
	}
	
	/**
	 * @return the amount of ticks between each explosion in a chain reaction.
	 */
	public static long getEDelay() {
		return explosion_delay;
	}
	
	/**
	 * @return the amount of hunger a zombie should remove from the player if struck.
	 */
	public static int getFoodDamage() {
		return zombie_food_damage;
	}
	
	/**
	 * @return the chance for a spider to turn into a spider mother
	 * 		after spawning.
	 */
	public static double getSMChance() {
		return mother_chance;
	}
}
