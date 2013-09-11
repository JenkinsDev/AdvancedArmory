package me.malazath.advancedarmory.armorsets;

import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class CreateArmor
{
	
	public final static ItemStack initiateArmorCreation(ItemStack item, String itemName, List<String> itemLore)
	{
		// Retrieve the item's meta data so we can alter it.
		ItemMeta emeraldSwordMeta = item.getItemMeta();
		
		emeraldSwordMeta.setDisplayName(itemName);
		emeraldSwordMeta.setLore(itemLore);
		
		item.setItemMeta(emeraldSwordMeta);
		
		return item;
	}
	
	public final static void createArmorRecipe()
	{
		
	}
	
}
