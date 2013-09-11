package me.malazath.advancedarmory.commands.command;

import java.util.HashMap;

import me.malazath.advancedarmory.AdvancedArmory;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveCustomItemCommand
{
	
	private static AdvancedArmory advancedArmory;
	private static HashMap<String, ItemStack> emeraldSet = new HashMap<String, ItemStack>();
	
	public GiveCustomItemCommand(Player sender, String item, Player receiver, AdvancedArmory plugin)
	{
		advancedArmory = plugin;
		setEmeraldSet();
		
		if (isItemAvailable(item))
			sendItemToUser(receiver, item);
		else
			sender.sendMessage(ChatColor.RED + "The item that you specified doesn't seem to be an AdvancedArmory item!");
	}
	
	private static boolean isItemAvailable(String item)
	{
		for (String emeraldItem : emeraldSet.keySet()) {
			if (item.equalsIgnoreCase(emeraldItem))
				return true;
		}
		
		return false;
	}
	
	private static void setEmeraldSet()
	{
		emeraldSet.put("emeraldhelmet", advancedArmory.emeraldHelmet);
		emeraldSet.put("emeraldboots", advancedArmory.emeraldBoots);
		emeraldSet.put("emeraldchest", advancedArmory.emeraldChest);
		emeraldSet.put("emeraldsword", advancedArmory.emeraldSword);
		emeraldSet.put("emeraldpickaxe", advancedArmory.emeraldPickaxe);
	}
	
	private static ItemStack getCustomItem(String item)
	{
		return emeraldSet.get(item);
	}
	
	private static void sendItemToUser(Player receiver, String item)
	{
		ItemStack itemStack = getCustomItem(item);
		
		receiver.getInventory().addItem(itemStack);
	}

}