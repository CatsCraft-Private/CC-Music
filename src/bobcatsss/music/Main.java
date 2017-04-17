package bobcatsss.music;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	static Main plugin;
    HashMap<Player, Sound> pmusic = new HashMap<>();

    // This is a Test
	public void onEnable() {
		plugin = this;
		Bukkit.getPluginManager().registerEvents(new GUIClickEvent(), this);
		getCommand("music").setExecutor(new MusicGUICommand());
	}
}
