package com.minehorizon.prestigeperks.util;

import com.minehorizon.prestigeperks.PrestigePerks;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

public class FortuneUtil {
    private final PrestigePerks prestigePerks;

    public FortuneUtil(PrestigePerks prestigePerks) {
        this.prestigePerks = prestigePerks;
    }

    public int getAmount(Block block, ItemStack itemStack) {
        if(itemStack != null || (itemStack.getType() != Material.AIR)) {
            if (itemStack.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
                int fortuneLevel = itemStack.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
                int bonus = 0;
                if (fortuneLevel <= 3) {
                    bonus = (ThreadLocalRandom.current().nextInt(fortuneLevel) * 2) + 1;
                } else {
                    bonus = (ThreadLocalRandom.current().nextInt(fortuneLevel - 2) * (fortuneLevel - 1));
                }

                if (bonus < 0) {
                    bonus = 1;
                }

                Collection<ItemStack> drop = block.getDrops(itemStack);
                int baseDrop = 1;
                for (ItemStack dropStack : drop) {
                    if (dropStack != null || dropStack.getType() != Material.AIR) {
                        baseDrop = dropStack.getAmount();
                    }
                }

                int toDrop = 1;

                if (block.getType() == Material.REDSTONE_ORE) {
                    toDrop = (baseDrop + bonus);
                } else if (block.getType() == Material.COAL_ORE) {
                    toDrop = (baseDrop + bonus) - (bonus / 2);
                } else if (block.getType() == Material.LAPIS_ORE) {
                    toDrop = baseDrop - (bonus * 3);
                } else if (block.getType() == Material.DIAMOND_ORE) {
                    toDrop = (baseDrop + (bonus * 2)) / 3;
                } else if (block.getType() == Material.EMERALD_ORE) {
                    toDrop = (baseDrop + (bonus * 2)) / 3;
                } else if (block.getType().name().contains("ORE")) {
                    toDrop = baseDrop * (bonus + 1);
                }

                if (toDrop < 0) {
                    toDrop = 1;
                }

                return toDrop;
            } else {
                Collection<ItemStack> collection = block.getDrops(itemStack);
                for (ItemStack itemStack1 : collection) {
                    if (itemStack1 != null || itemStack1.getType() != Material.AIR) {
                        return itemStack1.getAmount();
                    }
                }
            }
        }

        return 1;
    }
}
