package bobcatsss.music;

import modded.NoteBlockAPI.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.*;
import simple.brainsynder.api.ItemMaker;
import simple.brainsynder.math.MathUtils;

import java.util.*;

class MusicGUI implements Listener {
    public static Map<UUID, Map<ItemStack, Song>> playerSongMap = new HashMap<>();
    public static Map<UUID, Integer> pageMap = new HashMap<>();
    public static List<Integer> slots = Arrays.asList(
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
            if (song == null) {
                ItemMaker error = new ItemMaker(Material.BARRIER);
                error.setName("Could not load data for ");
                inv.addItem(error.create());
                continue;
            }
            ItemMaker maker = new ItemMaker(getRandomRecord ());
            try {
                maker.setName("&7&l" + song.getTitle());
            }catch (Exception e) {
                try {
                    maker.setName("&7&l" + song.getPath().getName().replace(".nbs", "").replace(".NBS", ""));
                }catch (Exception ex) {
                    maker.setName("&c&lMissing Song Name");
                }
            }
            int seconds = (song.getLength()/20);
            maker.addLoreLine("&aDuration&8: " + formatHHMMSS(seconds));
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

    private static String formatHHMMSS(long secondsCount){
        int seconds = (int) (secondsCount %60);
        secondsCount -= seconds;
        long minutesCount = secondsCount / 60;
        long minutes = minutesCount % 60;
        minutesCount -= minutes;
        long hoursCount = minutesCount / 60;
        StringBuilder builder = new StringBuilder();
        if (hoursCount > 0)
            builder.append(hoursCount).append(":");
        if (minutes > 0)
            builder.append(minutes).append(":");
        builder.append((seconds < 10) ? "0" + seconds : seconds);
        return builder.toString();
    }

    private static Material getRandomRecord () {
        int rand = MathUtils.random(0, 11);
        if (rand == 0) return Material.GOLD_RECORD;
        if (rand == 1) return Material.GREEN_RECORD;
        if (rand == 2) return Material.RECORD_3;
        if (rand == 3) return Material.RECORD_4;
        if (rand == 4) return Material.RECORD_5;
        if (rand == 5) return Material.RECORD_6;
        if (rand == 6) return Material.RECORD_7;
        if (rand == 7) return Material.RECORD_8;
        if (rand == 8) return Material.RECORD_9;
        if (rand == 9) return Material.RECORD_10;
        if (rand == 10) return Material.RECORD_11;
        if (rand == 11) return Material.RECORD_12;
        return Material.GOLD_RECORD;
    }
}

