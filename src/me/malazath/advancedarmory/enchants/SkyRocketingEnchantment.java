package me.malazath.advancedarmory.enchants;

import java.util.ArrayList;
import java.util.List;

import me.malazath.advancedarmory.utils.FireworkEffectCodeB;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.GameMode;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

import com.rit.sucy.CustomEnchantment;

public class SkyRocketingEnchantment extends CustomEnchantment
{
	
	static Plugin advancedArmory;
	static List<Player> noFlying = new ArrayList<Player>();
	static FireworkEffectCodeB firework = new FireworkEffectCodeB();
	
	static final Material[] SKYROCKETING_ITEMS = new Material[] { Material.DIAMOND_BOOTS };
	
	/**
	 *  Let's take the time set a few default values of our new enchantment.
	 */
	public SkyRocketingEnchantment()
	{
		super("SkyRocketing", SKYROCKETING_ITEMS, 0);
		
		setMaxLevel(1);
		setBase(900);
		
		// Now that the default values are set let's go ahead and set our plugin for future runnable task calls
		advancedArmory = Bukkit.getServer().getPluginManager().getPlugin("AdvancedArmory");
	}
	
	/**
	 * Handles checking if the user can fly or not as well as activating and deactivating flight
	 * @param Player user
	 * @param int level
	 * @param PlayerInteractEvent event
	 */
	@Override
	public void applyMiscEffect(Player user, int level, PlayerInteractEvent event)
	{
		if (user.getGameMode() == GameMode.CREATIVE)
			return;
		
		// If the user is already flying then we want to turn off the effect for them.
		if (user.isFlying()) {
			removeUserFlight(user);
			sendUserMessage(user, "Deactivating the SkyRocketing effect!");
			addUserCooldown(user);
		} else {
			if (noFlying.contains(user) || ! user.isSprinting() || user.isSneaking())
				return;
			
			if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK)
				return;
			
			allowUserFlight(user);
			flyingFireworkEffect(user);
			allowFlight(user.getServer(), user);
		}
	}
	
	/**
	 * Send the user a message letting them know that they can fly when wearing the boots!
	 * @param Player user
	 * @param int level
	 */
	@Override
	public void applyEquipEffect(Player user, int level)
	{
		sendUserMessage(user, "The emerald boots allow you to fly for up to 10 seconds!  Run and right click to give it a try!");
		sendUserMessage(user, "In order to fly you must sprint and jump.");
	}
	
	/**
	 * Fixes a little bug that allows players to continue to fly indefinitely if the user unequips the boots while flying
	 * @param Player user
	 * @param int level
	 */
	public void applyUnequipEffect(Player user, int level)
	{		
		removeUserCooldown(user);
	}
	
	/**
	 * Useful quick snippet for sending a message to a user.
	 * TODO: Add to a utilities package
	 * @param Player user
	 * @param String message
	 */
	public void sendUserMessage(Player user, String message)
	{
		user.sendMessage(ChatColor.AQUA + message);
	}
	
	/**
	 * Allow the user to take flight
	 * @param Player user
	 */
	public void allowUserFlight(Player user)
	{
		if (! user.getAllowFlight())
			user.setAllowFlight(true);
		
		if (! user.isFlying())
			user.setFlying(true);
	}
	
	/**
	 * Remove the user's ability to fly
	 * @param Player user
	 */
	public void removeUserFlight(Player user)
	{
		if (user.isFlying())
			user.setFlying(false);
		
		if (user.getAllowFlight())
			user.setAllowFlight(false);
	}
	
	/**
	 * Add the user to the no fly cooldown list.
	 * @param Player user
	 */
	public void addUserCooldown(Player user)
	{
		if (! noFlying.contains(user))
			noFlying.add(user);
	}
	
	/**
	 * Remove the user from the no fly cooldown list.
	 * @param Player user
	 */
	public void removeUserCooldown(Player user)
	{
		if (noFlying.contains(user))
			noFlying.remove(user);
	}
	
	/**
	 * Runs the firework effect when the user starts a new flying session
	 * @param Player user
	 */
	public void flyingFireworkEffect(Player user)
	{
		FireworkEffect fireworkfx = FireworkEffect.builder().with(Type.BALL).withColor(Color.RED, Color.AQUA).withFade(Color.GREEN).build();
		
		try {
			firework.playFirework(user.getWorld(), user.getLocation(), fireworkfx);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Allow the user to fly for only 10 seconds. Once finished set them on a 30 second cooldown.
	 * @param Server server
	 * @param Player user
	 */
	public void allowFlight(final Server server, final Player user)
	{		
		server.getScheduler().runTaskLater(advancedArmory, new Runnable() 
		{

			@Override
			public void run()
			{
				removeUserFlight(user);
				sendUserMessage(user, "Your boots have run out of power, you must wait 30 seconds!");
				addUserCooldown(user);
				setUsersCooldown(server, user);
			}
			
		}, 200);
	}
	
	/**
	 * Handle the timing for the cooldown list.  After 30 seconds we remove the user from the cooldown list.
	 * @param server
	 * @param user
	 */
	public void setUsersCooldown(final Server server, final Player user)
	{
		server.getScheduler().runTaskLater(advancedArmory, new Runnable()
		{
			
			@Override
			public void run()
			{
				sendUserMessage(user, "Your boots are now fueled up again!");
				removeUserCooldown(user);
			}
			
		}, 600);
	}
	
}