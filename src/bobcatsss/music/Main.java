package bobcatsss.music;

import java.util.*;

import com.xxmicloxx.NoteBlockAPI.SongPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	static Main plugin;
    Map<Player, SongPlayer> songPlayerMap = new HashMap<>();

	public void onEnable() {
		plugin = this;
		Bukkit.getPluginManager().registerEvents(new GUIClickEvent(), this);
		getCommand("music").setExecutor(new MusicGUICommand());
	}
}
