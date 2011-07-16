package me.KillerSmurf.DroponDeath;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

public class DroponDeath extends JavaPlugin {
	private static int itemDrop;
	private static int itemAmount;
	Logger log = Logger.getLogger("Minecraft");
	
	
	@Override
	public void onDisable() {
		log.info("[DroponDeath] Disabled");
		
	}

	@Override
	public void onEnable() {
		new File("plugins" + File.separator + "DroponDeath").mkdir();
		File configFile = new File("plugins/DroponDeath/config.yml");
    	if(!configFile.exists()){
            log.info("Config file not found. Creating config file...");
            try {
                configFile.createNewFile(); //Create the config file if it's not here already
            } catch (Exception ex) {
                log.info("Error creating the config file. >:O");
            }
        }
        Configuration config = new Configuration(configFile);
        config.load();
        if (config.getInt("settings.drop", 0) == 0) {
        	config.setProperty("settings.drop", 352);
        }
        if (config.getInt("settings.amount", 0) == 0) {
        	config.setProperty("settings.amount", 3);
        }
        config.save();
        config.load();
        itemDrop = config.getInt("settings.drop", 352);
        itemAmount = config.getInt("settings.amount",3);
        config.save();
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvent(Event.Type.ENTITY_DEATH, new DroponDeathEL(), Event.Priority.Normal, this);
		log.info("[DroponDeath] Enabled");
	}
	
	public class DroponDeathEL extends EntityListener { public void onEntityDeath(EntityDeathEvent event) { if (event.getEntity() instanceof Player) event.getEntity().getLocation().getWorld().dropItemNaturally(event.getEntity().getLocation(), new ItemStack(DroponDeath.itemDrop, itemAmount)); }}
}
