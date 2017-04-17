package bobcatsss.music;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

class MusicGUI implements Listener {
			
		static Inventory getInventory() {
			Inventory GUI = Bukkit.createInventory(null, 27, ChatColor.translateAlternateColorCodes('*', "*2Music"));

			ItemStack Thirteen = new ItemStack(Material.GOLD_RECORD);
			ItemMeta ThirteenMeta = Thirteen.getItemMeta();
			ThirteenMeta.setDisplayName(ChatColor.translateAlternateColorCodes('*', "*8[*613*8]"));
			Thirteen.setItemMeta(ThirteenMeta);
			GUI.addItem(Thirteen);
			
			ItemStack Eleven = new ItemStack(Material.RECORD_11);
			ItemMeta ElevenMeta = Eleven.getItemMeta();
			ElevenMeta.setDisplayName(ChatColor.translateAlternateColorCodes('*', "*8[*611*8]"));
			Eleven.setItemMeta(ElevenMeta);
			GUI.addItem(Eleven);
			
			
			ItemStack StopMusic = new ItemStack(Material.BARRIER);
			ItemMeta StopMusicMeta = StopMusic.getItemMeta();
			StopMusicMeta.setDisplayName(ChatColor.translateAlternateColorCodes('*', "*8[*cStop Music*8]"));
			StopMusic.setItemMeta(StopMusicMeta);
			GUI.setItem(26, StopMusic);
			return GUI;
			
		}
}

