package me.malazath.advancedarmory.enchants;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;

import com.rit.sucy.CustomEnchantment;

public class MultiBreakEnchantment extends CustomEnchantment {

	private static Block leftBlock;
	private static Block rightBlock;
	
	private static final Material[] MULTIBREAK_ITEMS = new Material[] { Material.DIAMOND_PICKAXE };
	private static final Material[] UNBREAKABLE = new Material[] {
			Material.BEDROCK, Material.BOAT, Material.BOOKSHELF,
			Material.DRAGON_EGG, Material.LAVA, Material.LAVA_BUCKET, Material.WATER, Material.WATER_BUCKET,
			Material.ACTIVATOR_RAIL, Material.BURNING_FURNACE, Material.FURNACE, Material.FLOWER_POT, Material.BED,
			Material.RAILS, Material.WALL_SIGN, Material.BREWING_STAND, Material.ENCHANTMENT_TABLE, Material.ENDER_CHEST,
			Material.ENDER_PORTAL, Material.ENDER_PORTAL_FRAME, Material.PORTAL, Material.POWERED_RAIL,
			Material.CHEST, Material.CAKE, Material.CAKE_BLOCK, Material.CAULDRON, Material.ANVIL, Material.WORKBENCH,
			Material.AIR, Material.DIODE_BLOCK_OFF, Material.DIODE_BLOCK_ON, Material.TORCH, Material.TNT,
			Material.DISPENSER, Material.FENCE, Material.FENCE_GATE, Material.JUKEBOX
		};
	
	/**
	 *  Let's take the time set a few default values of our new enchantment.
	 */
	public MultiBreakEnchantment() {
		super("Multibreak", MULTIBREAK_ITEMS, 0);
		
		setMaxLevel(1);
		setBase(900);
	}

	@Override
	public void applyToolEffect(Player user, Block block, int level, BlockEvent event) {
		if (event instanceof BlockBreakEvent && ! ((BlockBreakEvent) event).isCancelled()) {
			setAdjacentBlocks(user, block);
			
			if (! isUnbreakable(leftBlock.getType()))
				leftBlock.breakNaturally(user.getItemInHand());
			
			if (! isUnbreakable(rightBlock.getType()))
				rightBlock.breakNaturally(user.getItemInHand());
		}
	}
	
	private static void setAdjacentBlocks(Player player, Block brokenBlock) {
        double rotation = (player.getLocation().getYaw()) % 360;

        if (rotation < 0)
            rotation += 360.0;
        
        if ((67.5 <= rotation && rotation < 112.5) || (247.5 <= rotation && rotation < 292.5)) {
        	leftBlock = brokenBlock.getRelative(BlockFace.NORTH, 1);
			rightBlock = brokenBlock.getRelative(BlockFace.SOUTH, 1);
        } else {
        	leftBlock = brokenBlock.getRelative(BlockFace.WEST, 1);
			rightBlock = brokenBlock.getRelative(BlockFace.EAST, 1);
        }
    }
	
	private static boolean isUnbreakable(Material blockType) {
		for (Material material : UNBREAKABLE) {
			if (material.equals(blockType))
				return true;
		}
		
		return false;
	}
 	
}
