package bobcatsss.music;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

class GUIClickEvent implements Listener {

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();

		if (e.getInventory().getTitle().equals(MusicGUI.open().getTitle())) {
			if (e.isShiftClick()) {
				e.setCancelled(true);
			}
			e.setCancelled(true);

			ItemStack clicked = e.getCurrentItem();
			ItemMeta clickedMeta = clicked.getItemMeta();

			if (!clickedMeta.hasDisplayName()) {
				return;
			}

			if (clickedMeta.getDisplayName()
					.equals(ChatColor.translateAlternateColorCodes('*', "*8[*cStop Music*8]"))) {
				if (Main.plugin.songPlayerMap.containsKey(p)) {
					//p.stopSound(Main.plugin.pmusic.get(p));
					Main.plugin.songPlayerMap.remove(p);
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
