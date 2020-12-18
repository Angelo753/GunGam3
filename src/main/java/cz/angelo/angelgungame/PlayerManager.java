package cz.angelo.angelgungame;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class PlayerManager implements PlayerInterface {

	GameManager gmanager;

	FileConfiguration config;
	File file;
	File directory = null;
	Player player;
	int kills;
	int deaths;
	int level;
	double kd;

	public PlayerManager (Player player, int level, int kills, int deaths){
		this.player = player;
		this.kills = kills;
		this.deaths = deaths;
		this.level = level;
	}

	@Override
	public void respawn() {
		if (player.isDead()) {
			player.spigot().respawn();
		}
	}

	@Override
	public void increaseLevel() {
		this.level = this.level++;
		player.setLevel(this.level);
	}

	@Override
	public void decreaseLevel() {
		Bukkit.broadcastMessage("1");
		if (level > 0) {
			Bukkit.broadcastMessage("2");
			level = level--;
			player.setLevel(level);
		}
	}


	@Override
	public void getPlayer() {

	}

	@Override
	public void createFile() {
		if (config == null){
			file = new File(Main.instance.getDataFolder(), "userdata/" + player.getUniqueId() + ".yml");
			directory = new File(Main.instance.getDataFolder(), "userdata");
		}

		if (!directory.exists()){
			directory.mkdirs();
		}

		if (!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		config = YamlConfiguration.loadConfiguration(file);
	}

	@Override
	public void saveFile() {
		if (config == null || file == null){
			return;
		}
		try {
			this.getFile().save(file);
		}catch (IOException e){
		}
	}

	@Override
	public FileConfiguration getFile() {
		if (config == null){
			saveFile();
		} else {
			return config;
		}
		return null;
	}

	@Override
	public int getKills() {
		return kills;
	}

	@Override
	public int getDeaths() {
		return deaths;
	}

	@Override
	public void addDeath() {
		deaths++;
	}

	@Override
	public void addKill() {
		kills++;
	}

	@Override
	public int getLevel() {
		return this.level;
	}

	@Override
	public double kd() {
		if (deaths == 0){ ;
			return kills;
		}else {
			kd = kills / deaths;
			return Math.round(kd * 100 ) / 100;
		}
	}

}
