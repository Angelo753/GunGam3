package cz.angelo.angelgungame;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collection;

public class PlaceholderAPI extends PlaceholderExpansion {

	Main instance;

	public PlaceholderAPI(Main instance){
		this.instance = instance;
	}

	@Override
	public boolean canRegister(){
		return true;
	}

	@Override
	public String getIdentifier() {
		return "agungame";
	}

	@Override
	public String getAuthor() {
		return "Angelo753";
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

	@Override
	public String onRequest(OfflinePlayer player, String identifier) {
		if (identifier.equals("kills")){
			return String.valueOf(instance.phash.get(player.getUniqueId()).getKills());
		}
		if (identifier.equals("deaths")){
			return String.valueOf(instance.phash.get(player.getUniqueId()).getDeaths());
		}
		if (identifier.equals("kd")){
			return String.valueOf(instance.phash.get(player.getUniqueId()).kd());
		}
		return null;
	}
}
