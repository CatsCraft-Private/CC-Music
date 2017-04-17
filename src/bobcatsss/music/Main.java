package bobcatsss.music;

import java.io.File;
import java.util.*;

import com.xxmicloxx.NoteBlockAPI.*;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import simple.brainsynder.utils.ObjectPager;

public class Main extends JavaPlugin {
	public static Main plugin;
	ObjectPager<Song> musicList;
        Map<Player, SongPlayer> songPlayerMap = new HashMap<>();

	public void onEnable() {
		plugin = this;
		Bukkit.getPluginManager().registerEvents(new GUIClickEvent(), this);
		getCommand("music").setExecutor(new MusicGUICommand());
	}

	public ObjectPager<Song> reloadSongs () {
        File folder = new File(getDataFolder().toString() + "/songs/");

        Song s = NBSDecoder.parse(new File(CatsCraftMain.getPlugin().getDataFolder(), "songs/Problem.nbs"));
        sp = new RadioSongPlayer(s);
        sp.addPlayer(p);
        sp.setPlaying(true);
    }
}
