package me.malazath.advancedarmory.enchants;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.rit.sucy.CustomEnchantment;

public class LifeStealEnchantment extends CustomEnchantment
{

	static final Material[] LIFESTEAL_ITEMS = new Material[] { Material.DIAMOND_SWORD };

	/**
	 *  Let's take the time set a few default values of our new enchantment.
	 */
	public LifeStealEnchantment()
	{
		super("Lifesteal", LIFESTEAL_ITEMS, 0);

		setMaxLevel(3);
		setBase(900);
	}

	@Override
	public void applyEffect(LivingEntity user, LivingEntity target, int level, EntityDamageByEntityEvent event)
	{
		float healBy = (float) (Math.random() * (level + .5));
		double userHealth = user.getHealth();

		if (! (healBy >= .5))
			healBy = (float) .5;

		if (user.getMaxHealth() > (userHealth + healBy))
			user.setHealth(userHealth + healBy);
		else
			user.setHealth(user.getMaxHealth());
	}

}
