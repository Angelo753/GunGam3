package cz.angelo.angelgungame;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface PlayerInterface {

	//For future features

	void respawn();
	void increaseLevel();
	void decreaseLevel();
	void getPlayer();
	void createFile();
	void saveFile();
	FileConfiguration getFile();
	int getKills();
	int getDeaths();
	int getLevel();
	double getKD();


}
