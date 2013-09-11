package me.malazath.advancedarmory.enchants;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.rit.sucy.CustomEnchantment;

public class SkullStealEnchantment extends CustomEnchantment
{

	public static final Material[] SKULLSTEAL_ITEMS = new Material[] { Material.DIAMOND_HELMET };
	
	/**
	 *  Let's take the time set a few default values of our new enchantment.
	 */
	public SkullStealEnchantment()
	{
		super("SkullSteal", SKULLSTEAL_ITEMS, 0);
		
		setMaxLevel(1);
		setBase(900);
	}
	
	@Override
	public void applyEffect(LivingEntity user, LivingEntity target, int level, EntityDamageByEntityEvent event)
	{
		if (event.getDamager() != user || event.getEntityType() != EntityType.PLAYER || ! (user instanceof Player))
			return;
		
		Player targetPlayer = (Player) target;
		Player userPlayer   = (Player) user;
		
		String targetName = targetPlayer.getName();
		String userName   = userPlayer.getName();
		
		if (! (targetPlayer.getHealth() - event.getDamage() <= 0.0) )
			return;
		
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setOwner(targetName);
 
        skullMeta.setDisplayName(ChatColor.AQUA + targetName + "'s Head");
        
        skull.setItemMeta(skullMeta);
        
        Location targetLocation = target.getLocation();
        targetLocation.getWorld().dropItemNaturally(targetLocation, skull);
        
        // Fixes weird issue where the item would drop, but the player didn't fully die.  Let's kill the player!
        event.setDamage(20);
        
        targetPlayer.getServer().broadcastMessage(ChatColor.DARK_AQUA + userName + " Has Beheaded " + targetName);
	}
	
}
