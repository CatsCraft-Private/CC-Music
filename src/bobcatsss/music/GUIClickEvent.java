package bobcatsss.music;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GUIClickEvent implements Listener {

	@EventHandler
	public void onClick(InventoryClickEvent e) {

		Player p = (Player) e.getWhoClicked();

		if (e.getInventory().getTitle().equals(MusicGUI.getInventory().getTitle())) {
			if (e.isShiftClick()) {
				e.setCancelled(true);
			}
			e.setCancelled(true);

			ItemStack clicked = e.getCurrentItem();
			ItemMeta clickedMeta = clicked.getItemMeta();

			if (!clickedMeta.hasDisplayName()) {
				return;
			}
			
			if (clickedMeta.getDisplayName().equals(ChatColor.translateAlternateColorCodes('*', "*8[*613*8]"))) {
				if (Main.plugin.pmusic.containsKey(p)) {
					p.stopSound(Main.plugin.pmusic.get(p));
					p.playSound(p.getLocation(), Sound.RECORD_13, Integer.MAX_VALUE, 1);
					Main.plugin.pmusic.put(p, Sound.RECORD_13);
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&aMusic&8] &3Now playing &613"));
					p.closeInventory();
					return;
				}
				p.playSound(p.getLocation(), Sound.RECORD_13, Integer.MAX_VALUE, 1);
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&aMusic&8] &3Now playing &613"));
				Main.plugin.pmusic.put(p, Sound.RECORD_13);
				p.closeInventory();
				return;
			}
			
			if (clickedMeta.getDisplayName().equals(ChatColor.translateAlternateColorCodes('*', "*8[*611*8]"))) {
				if (Main.plugin.pmusic.containsKey(p)) {
					p.stopSound(Main.plugin.pmusic.get(p));
					p.playSound(p.getLocation(), Sound.RECORD_11, Integer.MAX_VALUE, 1);
					Main.plugin.pmusic.put(p, Sound.RECORD_11);
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&aMusic&8] &3Now playing &611"));
					p.closeInventory();
					return;
				}
				p.playSound(p.getLocation(), Sound.RECORD_11, Integer.MAX_VALUE, 1);
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&aMusic&8] &3Now playing &611"));
				Main.plugin.pmusic.put(p, Sound.RECORD_11);
				p.closeInventory();
				return;
			}
			
			if (clickedMeta.getDisplayName()
					.equals(ChatColor.translateAlternateColorCodes('*', "*8[*cStop Music*8]"))) {
				if (Main.plugin.pmusic.containsKey(p)) {
					p.stopSound(Main.plugin.pmusic.get(p));
					Main.plugin.pmusic.remove(p);
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&aMusic&8] &3Music has been stopped"));
					p.closeInventory();
					return;
				}
				p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&8[&aMusic&8] &3You're not playing any Music right now"));
				return;
			}
		}
	}
}
