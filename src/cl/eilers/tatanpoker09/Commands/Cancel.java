package cl.eilers.tatanpoker09.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import cl.eilers.tatanpoker09.Scrimmage;
import cl.eilers.tatanpoker09.utils.Timer;

@SuppressWarnings("unused")
public class Cancel implements CommandExecutor{
	private Scrimmage plugin;
	public Cancel(Scrimmage instance) {
		plugin = instance;
	}
	public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
			plugin.getConfig().set("TatanPGM.CancelCountdown", true);
			plugin.saveConfig();
			sender.sendMessage(ChatColor.GREEN+"Countdowns Cancelled");
		return true;
	}
}