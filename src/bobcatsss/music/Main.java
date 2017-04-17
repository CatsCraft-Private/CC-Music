package bobcatsss.music;

import java.io.*;
import java.util.*;

import com.xxmicloxx.NoteBlockAPI.*;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import simple.brainsynder.utils.ObjectPager;

public class Main extends JavaPlugin {
    static Main plugin;
    static ObjectPager<Song> songs;
    Map<Player, SongPlayer> songPlayerMap = new HashMap<>();

    public void onEnable() {
        plugin = this;
        Bukkit.getPluginManager().registerEvents(new GUIClickEvent(), this);
        getCommand("music").setExecutor(new MusicGUICommand());
        songs = reloadSongs(MusicGUI.slots.size());
    }
 
    ObjectPager<Song> reloadSongs(int size) {
        File folder = new File(getDataFolder().toString() + "/songs/");
        if (!folder.exists()) try {
            folder.createNewFile();
        } catch (IOException ignored) {}
        File[] files = folder.listFiles();
        if (files.length == 0) {
            saveResource("/songs/Boom Clap.nbs", true);
            saveResource("/songs/Cat_s In the Cradle.nbs", true);
            saveResource("/songs/Dynamite.nbs", true);
            saveResource("/songs/GangnamStyle.nbs", true);
            saveResource("/songs/Payphone.nbs", true);
            saveResource("/songs/Problem.nbs", true);
        }

        ArrayList<Song> songs = new ArrayList<>();
        for (File file : files) {
            if (!file.getName().endsWith("nbs")) continue;
            songs.add(NBSDecoder.parse(file));
        }

        return new ObjectPager<>(size, songs);
    }
}
