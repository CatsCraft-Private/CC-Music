package bobcatsss.music;

import com.xxmicloxx.NoteBlockAPI.Song;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import simple.brainsynder.api.ItemMaker;
import simple.brainsynder.storage.*;

import java.util.*;

class MusicGUI implements Listener {
    static Map<ItemStack, Song> songMap = new HashMap<>();
    static Map<UUID, Integer> pageMap = new HashMap<>();
    static List<Integer> slots = Arrays.asList(
            0, 1, 2, 3, 4, 5, 6, 7, 8,
            9, 10, 11, 12, 13, 14, 15, 16, 17,
            18, 19, 20, 21, 22, 23, 24, 25, 26,
            27, 28, 29, 30, 31, 32, 33, 34, 35,
            36, 37, 38, 39, 40, 41, 42, 43, 44
    );

    static Inventory open(int page) {
        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.translateAlternateColorCodes('&', "&2Music"));
        int placeHolder = inv.getSize();
        while (placeHolder > 0) {
            if (!slots.contains((placeHolder - 1))) {
                inv.setItem(placeHolder - 1, new ItemMaker(Material.STAINED_GLASS_PANE, (byte) 7).setName(" ").create());
            }
            placeHolder--;
        }

        if (Main.songs.totalPages() > (page)) {
            ItemMaker maker = new ItemMaker(Material.ARROW);
            maker.setName("§6§l§m-----§6§l>");
            inv.setItem(53, maker.create());
        }

        if (page > 1) {
            ItemMaker maker = new ItemMaker(Material.ARROW);
            maker.setName("§6§l<§m-----");
            inv.setItem(45, maker.create());
        }

        ItemStack StopMusic = new ItemStack(Material.BARRIER);
        ItemMeta StopMusicMeta = StopMusic.getItemMeta();
        StopMusicMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8[&cStop Music&8]"));
        StopMusic.setItemMeta(StopMusicMeta);
        inv.setItem(49, StopMusic);
        return inv;
    }
}

