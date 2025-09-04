package de.ivcx.mgms.all;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

import org.bukkit.Location;

public class Main extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("MGMS Activated!");
    }
    
    @Override
    public void onDisable() {
        getLogger().info("MGMS Deactivated!");
    }
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        ItemStack tool = player.getInventory().getItemInMainHand();
        Location blockLocation = block.getLocation();
        //check for pickaxe
        if (tool == null || tool.getType() != Material.GOLDEN_PICKAXE) {
            return;
        }
        boolean hasSilkTouch = tool.getEnchantments().containsKey(org.bukkit.enchantments.Enchantment.SILK_TOUCH);
        int fortuneLevel = tool.getEnchantmentLevel(Enchantment.FORTUNE);
        
        //gold block
        if (block.getType() == Material.GOLD_BLOCK) {
        	ItemStack goldBlock = new ItemStack(Material.GOLD_BLOCK, 1);
        	blockLocation.getWorld().dropItemNaturally(blockLocation, goldBlock);
        	return;
        }
        
        //gold ore
        if (block.getType() == Material.GOLD_ORE) {
        	if (hasSilkTouch) {
        		ItemStack goldBlock = new ItemStack(Material.GOLD_ORE, 1);
            	blockLocation.getWorld().dropItemNaturally(blockLocation, goldBlock);
            	return;
        	} else {
        		ItemStack goldBlock = new ItemStack(Material.RAW_GOLD, calculateDrop(fortuneLevel, 1));
            	blockLocation.getWorld().dropItemNaturally(blockLocation, goldBlock);
            	return;
        	}
        	
        }
        
        //deepslate gold ore
        if (block.getType() == Material.DEEPSLATE_GOLD_ORE) {
        	if (hasSilkTouch) {
        		ItemStack goldBlock = new ItemStack(Material.DEEPSLATE_GOLD_ORE, 1);
            	blockLocation.getWorld().dropItemNaturally(blockLocation, goldBlock);
            	return;
        	} else {
        		ItemStack goldBlock = new ItemStack(Material.RAW_GOLD, calculateDrop(fortuneLevel, 1));
            	blockLocation.getWorld().dropItemNaturally(blockLocation, goldBlock);
            	return;
        	}
        }
        
        
        //raw gold block
        if (block.getType() == Material.RAW_GOLD_BLOCK) {
        	ItemStack goldBlock = new ItemStack(Material.RAW_GOLD_BLOCK, 1);
        	blockLocation.getWorld().dropItemNaturally(blockLocation, goldBlock);
        	return;
        }
        
        return;
    }
    
    //fortune logic
    private static final Random random = new Random();
    public static int calculateDrop(int fortuneLevel, int baseDrop) {
        if (fortuneLevel <= 0) {
            return baseDrop;
        }

        int bonus = random.nextInt(fortuneLevel + 2) - 1;
        if (bonus < 0) bonus = 0;

        return baseDrop * (1 + bonus);
    }
    

    
}