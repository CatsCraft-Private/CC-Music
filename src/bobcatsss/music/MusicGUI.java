package bobcatsss.music;

import com.xxmicloxx.NoteBlockAPI.Song;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
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

    static void open(Player player, int page) {
        pageMap.put(player.getUniqueId(), page);
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

        for (Song song : Main.songs.getPage(page)) {
            ItemMaker maker = new ItemMaker(Material.GOLD_RECORD);
            maker.setName(song.getTitle());
            ItemStack item = maker.create();
            songMap.put(item, song);
        }

        inv.setItem(49, Main.plugin.getStopItem());
    }
}

