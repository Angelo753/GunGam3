package cz.angelo.angelgungame;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GameManager {

	Main instance;

	public void setLevel(Player p){
		p.getInventory().clear();
		PlayerManager playerManager = instance.phash.get(p.getUniqueId());
		ConfigurationSection section = Config.get("levels").getConfigurationSection("levels");
		ItemStack itemStack = null;
		if (!(playerManager.getLevel() < section.getKeys(false).size())) {
			ConfigurationSection types = Config.get("levels." + playerManager.getLevel());
			for (String type : types.getKeys(false)){
				String[] split = type.split(":");
				Material material = Material.valueOf(split[0]);
				itemStack = new ItemStack(material);
				ItemMeta meta = itemStack.getItemMeta();
				if (!split[1].isEmpty()){
					Enchantment enchantment = Enchantment.getByName(split[1]);
					if (split[2].isEmpty()){
						meta.addEnchant(enchantment, 1, false);
					}else {
						meta.addEnchant(enchantment, Integer.parseInt(split[2]), false);
					}
				}
				itemStack.setItemMeta(meta);
				switch (type){
					case "weapon":
						p.getInventory().addItem(itemStack);
					case "helmet":
						p.getInventory().setHelmet(itemStack);
					case "chestplate":
						p.getInventory().setChestplate(itemStack);
					case "leggings":
						p.getInventory().setLeggings(itemStack);
					case "boots":
						p.getInventory().setBoots(itemStack);
				}
			}

		}
	}

}
