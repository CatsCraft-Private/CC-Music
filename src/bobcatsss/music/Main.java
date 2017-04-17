package bobcatsss.music;

import java.io.*;
import java.util.*;

import modded.NoteBlockAPI.*;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import simple.brainsynder.utils.ObjectPager;

public class Main extends JavaPlugin {
    public static Main plugin;
    public static ObjectPager<Song> songs;
    public Map<UUID, SongPlayer> songPlayerMap = new HashMap<>();

    public void onEnable() {
        plugin = this;
        getServer().getPluginManager().registerEvents(new GUIClickEvent(), this);
        getCommand("music").setExecutor(new MusicGUICommand());
        songs = reloadSongs(MusicGUI.slots.size());
    }

    /**
     * This method makes sure that an infinite number of NBS songs can be added.
     */
    public ObjectPager<Song> reloadSongs(int size) {
        File folder = new File(getDataFolder().toString() + "/songs/");
        if (!folder.exists()) try {
            folder.createNewFile();
        } catch (IOException ignored) {
        }
        File[] files = folder.listFiles();
        if (files.length == 0) {
            saveResource("songs/Boom Clap.nbs", true);
            saveResource("songs/Cat_s In the Cradle.nbs", true);
            saveResource("songs/Dynamite.nbs", true);
            saveResource("songs/GangnamStyle.nbs", true);
            saveResource("songs/Payphone.nbs", true);
            saveResource("songs/Problem.nbs", true);
        }

        ArrayList<Song> songs = new ArrayList<>();
        assert files != null;
        for (File file : files) {
            if (!file.getName().endsWith("nbs")) continue;
            songs.add(NBSDecoder.parse(file));
        }

        return new ObjectPager<>(size, songs);
    }

    public ItemStack getStopItem() {
        ItemStack StopMusic = new ItemStack(Material.BARRIER);
        ItemMeta StopMusicMeta = StopMusic.getItemMeta();
        StopMusicMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8[&cStop Music&8]"));
        StopMusic.setItemMeta(StopMusicMeta);
        return StopMusic;
    }


    public HashMap<String, ArrayList<modded.NoteBlockAPI.SongPlayer>> playingSongs = new HashMap<>();
    public HashMap<String, Byte> playerVolume = new HashMap<>();

    public static boolean isReceivingSong(Player p) {
        return plugin.playingSongs.get(p.getName()) != null && !((ArrayList)plugin.playingSongs.get(p.getName())).isEmpty();
    }

    public static void stopPlaying(Player p) {
        if(plugin.playingSongs.get(p.getName()) != null) {

            for (modded.NoteBlockAPI.SongPlayer s : (plugin.playingSongs.get(p.getName()))) {
                s.removePlayer(p);
            }

        }
    }

    public static void setPlayerVolume(Player p, byte volume) {
        plugin.playerVolume.put(p.getName(), volume);
    }

    public static byte getPlayerVolume(Player p) {
        Byte b = (Byte)plugin.playerVolume.get(p.getName());
        if(b == null) {
            b = (byte)100;
            plugin.playerVolume.put(p.getName(), b);
        }

        return b;
    }
}
