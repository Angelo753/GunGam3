package cz.angelo.angelgungame;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;

public final class Main extends JavaPlugin {

	HashMap<UUID, PlayerManager> phash = new HashMap<>();

	static Main instance;

	@Override
	public void onEnable() {
		instance = this;
		eventRegister();
		Config.reload();
		new PlaceholderAPI(this).register();
		for (Player p : Bukkit.getOnlinePlayers()){
			PlayerManager playerManager = new PlayerManager(p, 0, 0, 0);
			phash.put(p.getUniqueId(), playerManager);
			phash.get(p.getUniqueId()).createFile();
		}
		BukkitTask runnables = new Runnables(this).runTaskTimer(this, 0L, 20L);
		System.out.println("Plugin AngelGunGame was enabled.");
	}

	@Override
	public void onDisable() {
		System.out.println("Plugin AngelGunGame was disabled.");
	}

	public void eventRegister(){
		this.getServer().getPluginManager().registerEvents(new GameMechanics(), this);
	}

	public String color(String text){
		return ChatColor.translateAlternateColorCodes('&', text);
	}

}
