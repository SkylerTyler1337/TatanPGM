package cl.eilers.tatanpoker09.utils;


import org.bukkit.ChatColor; 
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import cl.eilers.tatanpoker09.commands.Setnext;
import cl.eilers.tatanpoker09.map.MapLoader;
import cl.eilers.tatanpoker09.match.Match;

public class Timer extends BukkitRunnable {

	private final Plugin plugin;

	private int counter;
	
	public Timer(Plugin plugin2, int counter) {
		this.plugin = plugin2;
		if (counter < 1) {
			throw new IllegalArgumentException("You must supply a number");
		} else {
			this.counter = counter;
		}
	}

	public void run() {
		// What you want to schedule goes here
		if (counter > 0) {
			if(plugin.getConfig().getBoolean("TatanPGM.CancelCountdown")==false){
				plugin.getServer().broadcastMessage(ChatColor.DARK_AQUA+"Cycling to "+ChatColor.AQUA+Setnext.nextMap+ChatColor.DARK_AQUA+" in "+ChatColor.DARK_RED+counter--+ChatColor.DARK_AQUA+" seconds!");
			} else {
				this.cancel();
			}
		} else {
			MapLoader.Load();
			Match.hasStarted=false;
			this.cancel();
		}
	}

}