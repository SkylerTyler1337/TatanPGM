package cl.eilers.tatanpoker09.utils;


import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import cl.eilers.tatanpoker09.map.MapLoader;
import cl.eilers.tatanpoker09.match.Match;

public class Timer extends BukkitRunnable {

	private final Plugin plugin;

	private int counter;
	
	private World world;
	
	public Timer(Plugin plugin2, int counter, World world) {
		this.plugin = plugin2;
		if (counter < 1) {
			throw new IllegalArgumentException("You must supply a number");
		} else {
			this.counter = counter;
		}
		this.world = world;
	}

	public void run() {
		// What you want to schedule goes here
		if (counter > 0) {
			if(plugin.getConfig().getBoolean("TatanPGM.CancelCountdown")==false){
				plugin.getServer().broadcastMessage(ChatColor.DARK_AQUA+"Cycling to "+ChatColor.AQUA+plugin.getConfig().getString("TatanPGM.NextMap")+ChatColor.DARK_AQUA+" in "+ChatColor.DARK_RED+counter--+ChatColor.DARK_AQUA+" seconds!");
			} else {
				this.cancel();
			}
		} else {
			MapLoader.Load(plugin.getConfig().getString("TatanPGM.NextMap"), this.world);
			Match.hasStarted=false;
			this.cancel();
		}
	}

}