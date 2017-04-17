package bobcatsss.music;

import java.io.*;
import java.util.*;

import modded.NoteBlockAPI.*;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import simple.brainsynder.api.ItemMaker;
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

    public ObjectPager<Song> reloadSongs(int size) {
        File folder = new File(getDataFolder().toString() + "/songs/");
        if (!folder.exists()) folder.mkdir();
        if (!folder.isDirectory()) folder.mkdir();
        try {
            if (folder.listFiles().length == 0) {
                saveResource("songs/Boom Clap.nbs", true);
                saveResource("songs/Cat_s In the Cradle.nbs", true);
                saveResource("songs/Dynamite.nbs", true);
                saveResource("songs/GangnamStyle.nbs", true);
                saveResource("songs/Payphone.nbs", true);
                saveResource("songs/Problem.nbs", true);
            }
        } catch (Exception e) {
            saveResource("songs/Boom Clap.nbs", true);
            saveResource("songs/Cat_s In the Cradle.nbs", true);
            saveResource("songs/Dynamite.nbs", true);
            saveResource("songs/GangnamStyle.nbs", true);
            saveResource("songs/Payphone.nbs", true);
            saveResource("songs/Problem.nbs", true);
        }
        ArrayList<Song> songs = new ArrayList<>();
        for (File file : folder.listFiles()) {
            if (!file.getName().endsWith("nbs")) continue;
            Song song = NBSDecoder.parse(file);
            if (song == null) {
                System.out.println("Could not load Song data for file: " + file.getName());
                continue;
            }
            songs.add(song);
        }

        return new ObjectPager<>(size, songs);
    }

    public ItemStack getStopItem(String current) {
        ItemMaker maker = new ItemMaker(Material.BARRIER);
        maker.setName("&8[&cStop Music&8]");
        if (current != null) {
            maker.addLoreLine("Currently Playing:");
            maker.addLoreLine(current);
        }
        return maker.create();
    }

    public ItemStack getStopItem() {
        return getStopItem(null);
    }


    public HashMap<String, ArrayList<SongPlayer>> playingSongs = new HashMap<>();
    public HashMap<String, Byte> playerVolume = new HashMap<>();

    public static boolean isReceivingSong(Player p) {
        return plugin.playingSongs.get(p.getName()) != null && !((ArrayList) plugin.playingSongs.get(p.getName())).isEmpty();
    }

    public static void stopPlaying(Player p) {
        if (plugin.playingSongs.get(p.getName()) != null) {

            for (SongPlayer s : (plugin.playingSongs.get(p.getName()))) {
                s.removePlayer(p);
            }

        }
    }

    public static void setPlayerVolume(Player p, byte volume) {
        plugin.playerVolume.put(p.getName(), volume);
    }

    public static byte getPlayerVolume(Player p) {
        Byte b = (Byte) plugin.playerVolume.get(p.getName());
        if (b == null) {
            b = (byte) 100;
            plugin.playerVolume.put(p.getName(), b);
        }

        return b;
    }
}
