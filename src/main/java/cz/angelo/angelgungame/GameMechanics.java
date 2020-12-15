package cz.angelo.angelgungame;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class GameMechanics implements Listener {

	PlayerManager pmanager;
	GameManager gmanager;
	Main instance;

	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		Player p = e.getPlayer();

		//Register player
		PlayerManager playerManager = new PlayerManager(p,0, 0, 0, 0);
		Main.instance.phash.put(p.getUniqueId(), playerManager);

		//Configuration file
		playerManager.createFile();
		if (!playerManager.getFile().contains("kills") || !playerManager.getFile().contains("deaths")) {
			playerManager.getFile().set("kills", 0);
			playerManager.getFile().set("deaths", 0);
		}
		playerManager.saveFile();

		//Gamemode
		if (Config.get("config").getBoolean("force-gamemode.enabled")){
			GameMode gameMode = GameMode.valueOf(Config.get("config").getString("force-gamemode.gamemode"));
			p.setGameMode(gameMode);
		}

	}

	@EventHandler
	public void onLeave(PlayerQuitEvent e){
		Player p = e.getPlayer();

	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e){
		Player p = e.getEntity();
		e.setDroppedExp(0);
		e.getDrops().clear();
		new BukkitRunnable(){
			@Override
			public void run() {
				p.spigot().respawn();
				this.cancel();
			}
		}.runTaskTimer(Main.instance, 5, 0);
		instance.phash.get(p.getUniqueId()).decreaseLevel();
		if (p.getKiller() instanceof Player) {
			Player killer = p.getKiller();
			instance.phash.get(killer).increaseLevel();
		}
	}

	@EventHandler
	public void playerMove(PlayerMoveEvent e){
		Player p = e.getPlayer();
		Block b = p.getLocation().getBlock();
		if (p.getGameMode() != GameMode.CREATIVE && !p.isDead() && b.getType() == Material.WATER
				|| b.getType() == Material.WATER_BUCKET || b.getType() == Material.LEGACY_WATER
				|| b.getType() == Material.LEGACY_WATER_BUCKET){
			p.damage(p.getHealth());
		}
	}


}
