package cz.angelo.angelgungame;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Collections;
import java.util.List;

public class Runnables extends BukkitRunnable {

	Main plugin;
	private GameManager gmanager;

	Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
	Objective obj = sb.registerNewObjective("aaa", "bbb");
	Team agungame = sb.registerNewTeam("agungame");

	public Runnables(Main plugin){
		this.plugin = plugin;
	}

	@Override
	public void run() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			setScoreboard(p);
		}
	}

	public void setScoreboard(Player p) {
		agungame.setPrefix("");
		agungame.setSuffix("");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		agungame.addPlayer(p);
		String title = Config.get("config").getString("scoreboard.title");
		obj.setDisplayName(Main.instance.color(title));
		List<String> data = Config.get("config").getStringList("scoreboard.data");
		Collections.reverse(data);
		String text = " ";
		for (int i = 0; i < data.size(); i++) {
			agungame.addEntry(Main.instance.color(data.get(i) + text));
			obj.getScore(Main.instance.color(data.get(i) + text)).setScore(i);
			text = text + " ";
		}
		p.setScoreboard(sb);
	}

}
