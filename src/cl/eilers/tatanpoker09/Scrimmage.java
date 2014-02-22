package cl.eilers.tatanpoker09;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import cl.eilers.tatanpoker09.commands.Cancel;
import cl.eilers.tatanpoker09.commands.Cycle;
import cl.eilers.tatanpoker09.commands.End;
import cl.eilers.tatanpoker09.commands.Join;
import cl.eilers.tatanpoker09.commands.Lobby;
import cl.eilers.tatanpoker09.commands.SetServer;
import cl.eilers.tatanpoker09.commands.Setnext;
import cl.eilers.tatanpoker09.commands.Start;
import cl.eilers.tatanpoker09.listeners.BlockListener;
import cl.eilers.tatanpoker09.listeners.ChatListener;
import cl.eilers.tatanpoker09.listeners.CommandsListener;
import cl.eilers.tatanpoker09.utils.ScoreboardUtils;
import cl.eilers.tatanpoker09.utils.Timer;




public final class Scrimmage extends JavaPlugin implements Listener {
	private File DontModify = new File("plugins/TatanPGM/DontModify.yml");
	
	public static List<Timer> tList = new ArrayList<Timer>();
	public static String[][] teamInfo;
	
	@Override
	public void onEnable(){
		//Commands
		getCommand("setserver").setExecutor(new SetServer());
		getCommand("setnext").setExecutor(new Setnext(this));
		getCommand("cycle").setExecutor(new Cycle(this));
		getCommand("cancel").setExecutor(new Cancel(this));
		getCommand("lobby").setExecutor(new Lobby());
		getCommand("join").setExecutor(new Join());
		getCommand("start").setExecutor(new Start());
		getCommand("end").setExecutor(new End());
		
		//Config Thingies
		createYML(DontModify);
		this.getConfig().addDefault("TatanPGM.serverName", "A TatanPGM Server!");
		//Listeners
		PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.registerEvents(this, this);
		pm.registerEvents(new BlockListener(), this);
		pm.registerEvents(new CommandsListener(), this);
		pm.registerEvents(new ChatListener(), this);
		
		//Registers main teams.
		if(ScoreboardUtils.mainTeamsExist()==false){
		Bukkit.getServer().getScoreboardManager().getMainScoreboard().registerNewTeam("FirstTeam");
		Bukkit.getServer().getScoreboardManager().getMainScoreboard().registerNewTeam("SecondTeam");
		Bukkit.getServer().getScoreboardManager().getMainScoreboard().registerNewTeam("Observers");
		getConfig().set("TatanPGM.HasCreatedTeams", true);
		loadConfiguration();
		this.saveDefaultConfig();
		}	
	}
	@Override
	public void onDisable(){
	}
	//More config Thingies
	public void loadConfiguration(){
	     getConfig().options().copyDefaults(true);
	     saveConfig();
	     reloadConfig();
	}

	public void createYML(File name){
		if (!name.exists()) {
			try {
				name.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@EventHandler //JoinMessage, also teleports the player to the spawn.
	public void onJoin(PlayerJoinEvent event){
		System.out.println("OnJoin Has been triggered!");
		World spawn = new WorldCreator(Bukkit.getServer().getWorlds().get(0).getName()).createWorld();
		event.getPlayer().teleport(spawn.getSpawnLocation());
		if(ScoreboardUtils.teamExists("Observers")){
		ScoreboardUtils.joinTeam(event.getPlayer(), "Observers");
		}
		String serverName = getConfig().getString("TatanPGM.serverName");
		event.getPlayer().sendMessage(ChatColor.BLUE+"########################");
		event.getPlayer().sendMessage(ChatColor.RED+"Welcome to " + serverName);
		event.getPlayer().sendMessage(ChatColor.BLUE+"########################");
	}
}