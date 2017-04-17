package bobcatsss.music;

import modded.NoteBlockAPI.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.*;
import simple.brainsynder.api.ItemMaker;

import java.util.*;

class MusicGUI implements Listener {
    public static Map<UUID, Map<ItemStack, Song>> playerSongMap = new HashMap<>();
    public static Map<UUID, Integer> pageMap = new HashMap<>();
    public static List<Integer> slots = Arrays.asList(
            0, 1, 2, 3, 4, 5, 6, 7, 8,
            9, 10, 11, 12, 13, 14, 15, 16, 17,
            18, 19, 20, 21, 22, 23, 24, 25, 26,
            27, 28, 29, 30, 31, 32, 33, 34, 35,
            36, 37, 38, 39, 40, 41, 42, 43, 44
    );

    public static void open(Player player, int page) {
        pageMap.put(player.getUniqueId(), page);
        Inventory inv = Bukkit.createInventory(new InvHolder(), 54, ChatColor.translateAlternateColorCodes('&', "&2Music"));
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

        Map<ItemStack, Song> songMap = new HashMap<>();
        for (Song song : Main.songs.getPage(page)) {
            ItemMaker maker = new ItemMaker(Material.GOLD_RECORD);
            try {
                maker.setName(song.getTitle());
            }catch (Exception e) {
                maker.setName(song.getPath().getName().replace(".nbs", "").replace(".NBS", ""));
            }
            maker.addLoreLine("Length: " + song.getLength() + " seconds");
            maker.setFlags(
                    ItemFlag.HIDE_ATTRIBUTES,
                    ItemFlag.HIDE_DESTROYS,
                    ItemFlag.HIDE_ENCHANTS,
                    ItemFlag.HIDE_PLACED_ON,
                    ItemFlag.HIDE_POTION_EFFECTS,
                    ItemFlag.HIDE_UNBREAKABLE
            );
            ItemStack item = maker.create();
            songMap.put(item, song);
            inv.addItem(item);
        }

        playerSongMap.put(player.getUniqueId(), songMap);
        inv.setItem(49, Main.plugin.getStopItem());
        player.openInventory(inv);
    }
}

