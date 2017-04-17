package bobcatsss.music;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MusicGUICommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if(cmd.getName().equalsIgnoreCase("music")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage("You must be a player to use this command");
				return true;
			}
			Player p = (Player)sender;
			if(args.length == 0) {
				MusicGUI.open(p, 1);
				return true;
			}

			if (args[0].equalsIgnoreCase("reload")) {
			    Main.songs = Main.plugin.reloadSongs(MusicGUI.slots.size());
			    return true;
            }
			p.sendMessage("Usage: /music");
			return true;
		}
		return false;
	}
}
