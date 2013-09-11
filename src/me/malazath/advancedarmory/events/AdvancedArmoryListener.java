package me.malazath.advancedarmory.events;

import java.util.List;
import java.util.Arrays;

import me.malazath.advancedarmory.AdvancedArmory;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class AdvancedArmoryListener implements Listener
{
	
	public static AdvancedArmory advancedArmory;
	public static List<ItemStack> customItemStack;
	
	public AdvancedArmoryListener(AdvancedArmory plugin)
	{
		advancedArmory  = plugin;
		customItemStack = Arrays.asList(
			advancedArmory.emeraldSword,
			advancedArmory.emeraldHelmet,
			advancedArmory.emeraldChest,
			advancedArmory.emeraldBoots,
			advancedArmory.emeraldPickaxe
		);
	}
	
	@EventHandler
	public void onItemPickup(PlayerPickupItemEvent event)
	{
		ItemStack itemStack      = event.getItem().getItemStack();
		String itemDisplayName   = itemStack.getItemMeta().getDisplayName();
		
		if (! isCurrentItemCustom(itemStack))
			return;
		
		String permissionString = getPermissionString(itemDisplayName);
		Player player = event.getPlayer();
		
		if (! userHasEmeraldPermissions(player, permissionString))
			event.setCancelled(true);
	}
	
	@EventHandler
	public void onCraftingCreate(CraftItemEvent event)
	{
		ItemStack itemStack    = event.getCurrentItem();
		String itemDisplayName = itemStack.getItemMeta().getDisplayName();

		if (! isCurrentItemCustom(itemStack) || ! (event.getViewers().get(0) instanceof Player))
			return;
		
		String permissionString = getPermissionString(itemDisplayName);
		Player player = (Player) event.getViewers().get(0);
		
		if (userHasEmeraldPermissions(player, permissionString))
			return;
		
		advancedArmory.doesNotHavePermission(player);
		event.setCancelled(true);
	}
	
	private boolean isCurrentItemCustom(ItemStack eventItemStack)
	{
		for (ItemStack customItem : customItemStack) {
			if (customItem.equals(eventItemStack))
				return true;
		}
		
		return false;
	}
	
	private String getPermissionString(String itemDisplayName)
	{
		String lowerCase = itemDisplayName.toLowerCase();
		
		if (isItemFromEmeraldSet(itemDisplayName)) {
			if (lowerCase.contains("helmet"))
				return "advancedarmory.emerald.helmet";
			
			if (lowerCase.contains("chestplate"))
				return "advancedarmory.emerald.chest";
			
			if (lowerCase.contains("sword"))
				return "advancedarmory.emerald.sword";
			
			if (lowerCase.contains("boots"))
				return "advancedarmory.emerald.boots";
			
			if (lowerCase.contains("pickaxe"))
				return "advancedarmory.emerald.pickaxe";
		}
		
		return "";
	}
	
	private boolean isItemFromEmeraldSet(String eventItemDisplayName)
	{
		return eventItemDisplayName.startsWith("Emerald");
	}
	
	private boolean userHasEmeraldPermissions(Player player, String permissionString)
	{
		if (! advancedArmory.doesPlayerHavePermission(player, permissionString) && ! advancedArmory.doesPlayerHavePermission(player, "advancedarmory.emerald.*") && ! advancedArmory.doesPlayerHavePermission(player, "advancedarmory.*"))
			return false;
		
		return true;
	}
	
}
