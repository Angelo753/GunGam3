package cz.angelo.angelgungame;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;

public class Config {

	public static FileConfiguration config = null;
	public static File file = null;
	public static FileConfiguration levelsConfig = null;
	public static File levelsFile = null;

	public static void reload() {
		if (config == null){
			file = new File(Main.instance.getDataFolder(), "config.yml");
		}
		if (levelsConfig == null){
			levelsFile = new File(Main.instance.getDataFolder(), "levels.yml");
		}

		config = YamlConfiguration.loadConfiguration(file);
		levelsConfig = YamlConfiguration.loadConfiguration(levelsFile);

		Reader defConfigStream = null;
		Reader defConfigStreamLevels = null;

		if (!file.exists()){
			Main.instance.saveResource("config.yml", false);
		}
		if (!levelsFile.exists()){
			Main.instance.saveResource("levels.yml", false);
		}

		try {
			defConfigStream = new InputStreamReader(Main.instance.getResource("config.yml"), "UTF8");
			defConfigStreamLevels = new InputStreamReader(Main.instance.getResource("levels.yml"), "UTF8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (defConfigStream != null){
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			config.setDefaults(defConfig);
		}
		if (defConfigStreamLevels != null){
			YamlConfiguration defConfigLevels = YamlConfiguration.loadConfiguration(defConfigStreamLevels);
			levelsConfig.setDefaults(defConfigLevels);
		}
	}

	public static FileConfiguration get(String cfg) {
		if (config == null || levelsConfig == null){
			reload();
		} switch (cfg){
			case "config":
				return config;
			case "levels":
				return levelsConfig;
		}
		return null;
	}

	public static void save(){
		if (config == null || file == null){
			return;
		}
		try {
			get("config").save(file);
			get("levels").save(file);
		}catch (IOException e){
		}
	}

}
