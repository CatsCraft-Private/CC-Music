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
				sender.sendMessage("§8[§aMusic§8] §3You must be a player to use this command");
				return true;
			}
			Player p = (Player)sender;
			if(args.length == 0) {
				MusicGUI.open(p, 1);
				return true;
			}

			if (args[0].equalsIgnoreCase("reload")) {
			    if (!p.isOp()) {
                    MusicGUI.open(p, 1);
			        return true;
                }
			    Main.songs = Main.plugin.reloadSongs(MusicGUI.slots.size());
			    p.sendMessage("§8[§aMusic§8] §3Songs have been reloaded.");
			    return true;
            }
			p.sendMessage("§8[§aMusic§8] §3Usage: §7/music");
			return true;
		}
		return false;
	}
}
