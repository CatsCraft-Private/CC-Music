package bobcatsss.music;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	public static Main plugin;
	
	
	public HashMap<Player, Sound> pmusic = new HashMap<Player, Sound>();
	
	public void onEnable() {
		
		plugin = this;
		
		Bukkit.getPluginManager().registerEvents(new GUIClickEvent(), this);
		
		getCommand("music").setExecutor(new MusicGUICommand());
		
	}
	
	public void onDisable() {
		
		plugin = null;
		
	}

}
