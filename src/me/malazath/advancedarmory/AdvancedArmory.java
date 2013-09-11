package me.malazath.advancedarmory;

import me.malazath.advancedarmory.armorsets.CreateArmor;
import me.malazath.advancedarmory.commands.AdvancedArmoryCommandExecutor;
import me.malazath.advancedarmory.enchants.InvertireEnchantment;
import me.malazath.advancedarmory.enchants.LifeStealEnchantment;
import me.malazath.advancedarmory.enchants.MultiBreakEnchantment;
import me.malazath.advancedarmory.enchants.SkullStealEnchantment;
import me.malazath.advancedarmory.enchants.SkyRocketingEnchantment;
import me.malazath.advancedarmory.events.AdvancedArmoryListener;
import net.milkbowl.vault.permission.Permission;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.rit.sucy.EnchantPlugin;
import com.rit.sucy.EnchantmentAPI;

public final class AdvancedArmory extends EnchantPlugin
{
	
	public Permission perms;

	public ItemStack emeraldSword   = CreateArmor.initiateArmorCreation(new ItemStack(Material.DIAMOND_SWORD, 1), "Emerald Sword", EMERALD_SWORD_LORE);
	public ItemStack emeraldPickaxe = CreateArmor.initiateArmorCreation(new ItemStack(Material.DIAMOND_PICKAXE, 1), "Emerald Pickaxe", EMERALD_PICKAXE_LORE);
	public ItemStack emeraldChest   = CreateArmor.initiateArmorCreation(new ItemStack(Material.DIAMOND_CHESTPLATE, 1), "Emerald Chestplate", EMERALD_CHEST_LORE);
	public ItemStack emeraldHelmet  = CreateArmor.initiateArmorCreation(new ItemStack(Material.DIAMOND_HELMET, 1), "Emerald Helmet", EMERALD_HELMET_LORE);
	public ItemStack emeraldBoots   = CreateArmor.initiateArmorCreation(new ItemStack(Material.DIAMOND_BOOTS, 1), "Emerald Boots", EMERALD_BOOTS_LORE);
	
	public final static Logger logger = Logger.getLogger("Minecraft");
	public final static List<String> EMERALD_SWORD_LORE   = Arrays.asList("Built from the strongest natural ore available.");
	public final static List<String> EMERALD_PICKAXE_LORE = Arrays.asList("Pickaxe that is awezomes!");
	public final static List<String> EMERALD_CHEST_LORE   = Arrays.asList("Amazing chestpiece withou awesome powerz!");
	public final static List<String> EMERALD_HELMET_LORE  = Arrays.asList("Look at my helmet! :D");
	public final static List<String> EMERALD_BOOTS_LORE   = Arrays.asList("Skyrocketing Boots!");
	
	/**
	 *  Currently used for a tiny bit of house keeping
	 */
	@Override
	public void onDisable()
	{
		// Go ahead and cancel any currently running tasks
		Bukkit.getScheduler().cancelTasks(this);
		logger.info("AdvancedArmory: House keeping has been finished!  Disabling.");
	}
	
	/**
	 *  Used to instantiate the creation of all of the new armory items.
	 */
	@Override
	public void onEnable()
	{
		setupPermissions();
		getCommand("advancedarmory").setExecutor(new AdvancedArmoryCommandExecutor(this));
		getServer().getPluginManager().registerEvents(new AdvancedArmoryListener(this), this);
		
		
		// Emerald Sword
		EnchantmentAPI.getEnchantment("Lifesteal").addToItem(emeraldSword, 1);
		
		ShapedRecipe emeraldSwordRecipe = new ShapedRecipe(emeraldSword);
		emeraldSwordRecipe.shape("A", "A", "B");
		emeraldSwordRecipe.setIngredient('A', Material.EMERALD);
		emeraldSwordRecipe.setIngredient('B', Material.STICK);
	
		getServer().addRecipe(emeraldSwordRecipe);
		
		
		// Emerald Pickaxe
		EnchantmentAPI.getEnchantment("MultiBreak").addToItem(emeraldPickaxe, 1);
		
		ShapedRecipe emeraldPickaxeRecipe = new ShapedRecipe(emeraldPickaxe);
		emeraldPickaxeRecipe.shape("AAA", " B ", " B ");
		emeraldPickaxeRecipe.setIngredient('A', Material.EMERALD);
		emeraldPickaxeRecipe.setIngredient('B', Material.STICK);

		getServer().addRecipe(emeraldPickaxeRecipe);
		
		
		// Emerald Chest
		EnchantmentAPI.getEnchantment("Invertire").addToItem(emeraldChest, 1);
		
		ShapedRecipe emeraldChestRecipe = new ShapedRecipe(emeraldChest);
		emeraldChestRecipe.shape("A A", "AAA", "AAA");
		emeraldChestRecipe.setIngredient('A', Material.EMERALD);
		
		getServer().addRecipe(emeraldChestRecipe);
		
		
		// Emerald Helmet
		EnchantmentAPI.getEnchantment("SkullSteal").addToItem(emeraldHelmet, 1);
		
		ShapedRecipe emeraldHelmetRecipe = new ShapedRecipe(emeraldHelmet);
		emeraldHelmetRecipe.shape("AAA", "A A", "   ");
		emeraldHelmetRecipe.setIngredient('A', Material.EMERALD);
		
		getServer().addRecipe(emeraldHelmetRecipe);
		
		
		// Emerald Boots
		EnchantmentAPI.getEnchantment("SkyRocketing").addToItem(emeraldBoots, 1);
		
		ShapedRecipe emeraldBootsRecipe = new ShapedRecipe(emeraldBoots);
		emeraldBootsRecipe.shape("   ", "A A", "A A");
		emeraldBootsRecipe.setIngredient('A',  Material.EMERALD);
		
		getServer().addRecipe(emeraldBootsRecipe);
	}
    
    public void doesNotHavePermission(Player player)
	{
		player.sendMessage(ChatColor.RED + "You do not have the right permissions to do this!");
	}
	
    public boolean doesPlayerHavePermission(Player sender, String permission)
	{
		return perms.has(sender, permission);
	}
	
	public Player[] onlinePlayers()
	{
		return getServer().getOnlinePlayers();
	}
	
	public Player getPlayerByName(String player)
	{
		return getServer().getPlayer(player);
	}
	
	/**
	 *  Handles calling the registerCustomEnchantment method on all of our custom enchants!
	 */
	@Override
	public void registerEnchantments()
	{
		EnchantmentAPI.registerCustomEnchantment(new LifeStealEnchantment());
		EnchantmentAPI.registerCustomEnchantment(new MultiBreakEnchantment());
		EnchantmentAPI.registerCustomEnchantment(new InvertireEnchantment());
		EnchantmentAPI.registerCustomEnchantment(new SkullStealEnchantment());
		EnchantmentAPI.registerCustomEnchantment(new SkyRocketingEnchantment());
	}
	
    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        
        return perms != null;
    }
	
}
