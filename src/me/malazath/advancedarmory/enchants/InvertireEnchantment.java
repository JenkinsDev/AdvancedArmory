package me.malazath.advancedarmory.enchants;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import com.rit.sucy.CustomEnchantment;

public class InvertireEnchantment extends CustomEnchantment
{

	static final Material[] INVERTIRE_ITEMS = new Material[] { Material.DIAMOND_CHESTPLATE };
	
	/**
	 *  Let's take the time set a few default values of our new enchantment.
	 */
	public InvertireEnchantment()
	{
		super("Invertire", INVERTIRE_ITEMS, 0);
		
		setMaxLevel(1);
		setBase(900);
	}
	
	@Override
	public void applyDefenseEffect(LivingEntity user, LivingEntity target, int level, EntityDamageEvent event)
	{
		if (target == null || event instanceof EntityDamageByBlockEvent)
			return;
		
		double psuedoRandom = Math.random();
		
		if (psuedoRandom > 0.2 && psuedoRandom < 0.7) {
			double damageDealing = (double) (1.0 + ((psuedoRandom * 1.243) * target.getLastDamage()));
			
			if (! (damageDealing >= .5))
				damageDealing = .5;
			
			target.damage(damageDealing, user);
		}
	}
	
}
