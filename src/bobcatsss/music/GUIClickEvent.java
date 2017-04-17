package bobcatsss.music;

import modded.NoteBlockAPI.*;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class GUIClickEvent implements Listener {

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
        int page = 0;
        if (MusicGUI.pageMap.containsKey(p.getUniqueId()))
            page = MusicGUI.pageMap.get(p.getUniqueId());
		if (e.getView().getTopInventory().getHolder() instanceof InvHolder) {
		    if (e.getCurrentItem() == null) return;
			e.setCancelled(true);
 			if (e.getCurrentItem().isSimilar(Main.plugin.getStopItem())) {
				if (Main.plugin.songPlayerMap.containsKey(p.getUniqueId())) {
                    Main.plugin.songPlayerMap.get(p.getUniqueId()).destroy();
					Main.plugin.songPlayerMap.remove(p.getUniqueId());
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&aMusic&8] &3Music has been stopped"));
					p.closeInventory();
					return;
				}
				p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&8[&aMusic&8] &3You're not playing any Music right now"));
				return;
			}

            if (e.getSlot() == 45) {
                if (e.getCurrentItem().getType() == Material.ARROW) {
                    if (page > 1) {
                        MusicGUI.open(p, (page - 1));
                        return;
                    }
                }
            }

            if (e.getSlot() == 53) {
                if (e.getCurrentItem().getType() == Material.ARROW) {
                    if (page < Main.songs.totalPages()) {
                        MusicGUI.open(p, (page + 1));
                        return;
                    }
                }
            }
            if (!MusicGUI.playerSongMap.containsKey (p.getUniqueId())) return;
            Map<ItemStack, Song> songMap = MusicGUI.playerSongMap.get(p.getUniqueId());
            if (songMap.containsKey(e.getCurrentItem())) {
                if (Main.plugin.songPlayerMap.containsKey(p.getUniqueId())) {
                    Main.plugin.songPlayerMap.get(p.getUniqueId()).destroy();
                    Main.plugin.songPlayerMap.remove(p.getUniqueId());
                }
                Song song = songMap.get(e.getCurrentItem());
                SongPlayer player = new RadioSongPlayer(song);
                p.sendMessage("§8[§aMusic§8] §3Now Playing: §7" + song.getTitle());
                player.addPlayer(p);
                player.setPlaying(true);
                player.setAutoDestroy(true);
                Main.plugin.songPlayerMap.put(p.getUniqueId(), player);
            }
		}
	}
}
