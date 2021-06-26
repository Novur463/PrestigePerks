package com.minehorizon.prestigeperks.listener.perks;

import com.minehorizon.prestigeperks.PrestigePerks;
import com.minehorizon.prestigeperks.util.handler.Perks;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BlockEvents implements Listener {
    private final PrestigePerks prestigePerks;
    public BlockEvents(PrestigePerks prestigePerks) {
        this.prestigePerks = prestigePerks;
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onBreak(BlockBreakEvent event) {
        if (!prestigePerks.isInApplicableRegion(event.getBlock())) return;
        if(event.getBlock() == null || event.getBlock().getType() == Material.AIR) return;

        Player player = event.getPlayer();
        Block block = event.getBlock();
        ItemStack inHand = player.getInventory().getItemInMainHand();

        if(inHand == null || (inHand.getType() == Material.AIR)) return;

        //event not cancelled
        if(!event.isCancelled()) {

            /*if(isExplosivePickaxe(inHand)) {

                if(isSmeltable(block.getType())) {
                    if(prestigePerks.getToggledAutoSmelt().contains(player.getUniqueId())) {
                        handleExplosiveSmelted(player, getAffectedExplosive(block,getExplosiveLevel(inHand)));
                    } else {
                        handleExplosiveUnsmelted(player,getAffectedExplosive(block,getExplosiveLevel(inHand)));
                    }
                    event.setDropItems(false);
                } else {
                    handleExplosiveUnsmelted(player, getAffectedExplosive(block, getExplosiveLevel(inHand)));
                    event.setDropItems(false);
                }

            } else {*/
            if (block != null || (block.getType() != Material.AIR)) {
                if (isSmeltable(block.getType())) {
                    if (prestigePerks.getToggledAutoSmelt().contains(player.getUniqueId())) {
                        handleSmeltedItem(player, block);
                    } else {
                        handleUnsmeltedItem(player, block);
                    }
                    event.setDropItems(false);
                } else {
                    handleUnsmeltedItem(player, block);
                    event.setDropItems(false);
                }
            }
        }
        //}
    }

    private boolean isExplosivePickaxe(ItemStack itemStack) {
        if(itemStack.hasItemMeta()) {
            if(itemStack.getItemMeta().hasDisplayName()) {
                if(itemStack.getType().name().contains("PICKAXE")) {
                    return itemStack.getItemMeta().getDisplayName().contains("Explosive");
                }
            }
        }
        return false;
    }

    private int getExplosiveLevel(ItemStack itemStack) {
        if(itemStack.hasItemMeta()) {
            if (itemStack.getItemMeta().hasDisplayName()) {
                if (itemStack.getType().name().contains("PICKAXE")) {
                    /*for (String string : itemStack.getItemMeta().getLore()) {
                        if(string.contains("Explosive 1")) {
                            return 1;
                        } else if(string.contains("Explosive 2")) {
                            return 2;
                        } else if(string.contains("Explosive 3")) {
                            return 3;
                        }
                    }*/

                    if(itemStack.getItemMeta().getDisplayName().contains("Explosive 1")) {
                        return 1;
                    } else if(itemStack.getItemMeta().getDisplayName().contains("Explosive 2")) {
                        return 2;
                    } else if(itemStack.getItemMeta().getDisplayName().contains("Explosive 3")) {
                        return 3;
                    }
                }
            }
        }
        return 0;
    }

    private void handleExplosiveSmelted(Player player, List<Block> blockList) {
        for(Block block : blockList) {

            if(block != null || (!block.getType().name().contains("AIR"))) {

                if (prestigePerks.isInApplicableRegion(block)) {
                    handleSmeltedItem(player, block);
                    block.setType(Material.AIR);
                }
            }
        }
    }

    private void handleExplosiveUnsmelted(Player player, List<Block> blockList) {
        for(Block block : blockList) {

            if(block != null || (!block.getType().name().contains("AIR"))) {
                if (prestigePerks.isInApplicableRegion(block)) {
                    handleUnsmeltedItem(player, block);
                    block.setType(Material.AIR);
                }
            }
        }
    }

    private List<Block> getAffectedExplosive(Block centreBlock, int tier) {
        List<Block> blockList = new ArrayList<>();

        if(tier == 1) {
            Block eastBlock = centreBlock.getRelative(BlockFace.EAST);
            Block westBlock = centreBlock.getRelative(BlockFace.WEST);

            if(nullCheck(eastBlock) && (nullCheck(westBlock))) {

                blockList.add(eastBlock);
                blockList.add(westBlock);

                return blockList;
            }
        }

        else if(tier == 2) {
            Block northBlock = centreBlock.getRelative(BlockFace.NORTH);
            Block southBlock = centreBlock.getRelative(BlockFace.SOUTH);
            Block eastBlock = centreBlock.getRelative(BlockFace.EAST);
            Block westBlock = centreBlock.getRelative(BlockFace.WEST);

            if(nullCheck(northBlock) && nullCheck(eastBlock) && nullCheck (southBlock) && nullCheck(westBlock)) {
                blockList.add(northBlock);
                blockList.add(southBlock);
                blockList.add(eastBlock);
                blockList.add(westBlock);

                return blockList;
            }


        }

        else if(tier == 3) {
            Block northBlock = centreBlock.getRelative(BlockFace.NORTH);
            Block southBlock = centreBlock.getRelative(BlockFace.SOUTH);
            Block eastBlock = centreBlock.getRelative(BlockFace.EAST);
            Block westBlock = centreBlock.getRelative(BlockFace.WEST);

            Block northEast = centreBlock.getRelative(BlockFace.NORTH_EAST);
            Block southEast = centreBlock.getRelative(BlockFace.SOUTH_EAST);
            Block southWest = centreBlock.getRelative(BlockFace.SOUTH_WEST);
            Block northWest = centreBlock.getRelative(BlockFace.NORTH_WEST);

            if(nullCheck(northBlock) && nullCheck(eastBlock) && nullCheck (southBlock) && nullCheck(westBlock)
            && nullCheck(northEast) && (nullCheck(northWest) && nullCheck(southEast) && nullCheck(southWest))) {
                blockList.add(northBlock);
                blockList.add(southBlock);
                blockList.add(eastBlock);
                blockList.add(westBlock);

                blockList.add(northEast);
                blockList.add(southEast);
                blockList.add(southWest);
                blockList.add(northWest);

                return blockList;
            }


        }


        return blockList;
    }


    private void handleUnsmeltedItem(Player player, Block block) {
        if(block != null || (block.getType() != Material.AIR)) {
            ItemStack inHand = player.getInventory().getItemInMainHand();

            if (inHand != null || inHand.getType() != Material.AIR) {

                int amount = 1;

                if (prestigePerks.getUtility().isFortuneApplicable(block)) {
                    amount = fortuneAmount(block, inHand);
                }

                if (player.hasPermission(Perks.DOUBLEDROPS.getPermission())) {
                    if (isDoubleable(block)) {
                        if (randomChance() < getDDChance()) {
                            int beforeAmount = amount;
                            amount = amount * 2;
                            if(player.getName().equalsIgnoreCase("GHOSTS")) {
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6DoubleDrops &7&l>>  &7Drops doubled from &6" + beforeAmount + " &7to &6" + amount + "&7."));
                            }
                        }
                    }
                }

                Collection<ItemStack> collection = block.getDrops(inHand);
                for (ItemStack itemStack : collection) {
                    if (itemStack.getType() != Material.AIR || (itemStack != null)) {
                        if (amount == 0) {
                            amount = 1;
                        }
                        itemStack.setAmount(amount);

                        if (prestigePerks.getToggledAutoPickup().contains(player.getUniqueId())) {
                            player.getInventory().addItem(itemStack);
                        } else {
                            if (itemStack.getType() != Material.AIR || (itemStack != null) || itemStack.getAmount() > 0) {
                                player.getWorld().dropItemNaturally(block.getLocation(), itemStack);
                            }
                        }
                    }
                }
            }
        }
    }

    private void handleSmeltedItem(Player player, Block block) {
        ItemStack inHand = player.getInventory().getItemInMainHand();
        if (block.getType() != Material.AIR || (block != null)) {

            int amount = fortuneAmount(block, inHand);

            if (player.hasPermission(Perks.DOUBLEDROPS.getPermission())) {
                if (isDoubleable(block)) {
                    if (randomChance() < getDDChance()) {
                        int beforeAmount = amount;
                        amount = amount * 2;
                        if(player.getName().equalsIgnoreCase("GHOSTS")) {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6DoubleDrops &7&l>>  &7Drops doubled from &6" + beforeAmount + " &7to &6" + amount + "&7."));
                        }
                    }
                }
            }

            ItemStack itemStack = returnSmeltedItem(block.getType());
            if (itemStack.getType() != Material.AIR || (itemStack != null)) {
                itemStack.setAmount(amount);
                if(itemStack.getAmount() == 0) {
                    itemStack.setAmount(1);
                }
                if (prestigePerks.getToggledAutoPickup().contains(player.getUniqueId())) {
                    player.getInventory().addItem(itemStack);
                } else {
                    player.getWorld().dropItemNaturally(block.getLocation(),itemStack);
                }
            }
        }
    }

    private ItemStack returnSmeltedItem(Material material) {
        switch(material) {
            case IRON_ORE:
                return new ItemStack(Material.IRON_INGOT);
            case GOLD_ORE:
                return new ItemStack(Material.GOLD_INGOT);
            case COBBLESTONE:
            case STONE:
                return new ItemStack(Material.STONE);
        }

        return new ItemStack(material);
    }

    private int fortuneAmount(Block block, ItemStack itemStack) {
        return prestigePerks.getFortuneUtil().getAmount(block,itemStack);
    }

    private float getDDChance() {
        return Float.parseFloat(prestigePerks.getConfig().getString("chances.doubledrops"));
    }

    private float randomChance() {
        return ThreadLocalRandom.current().nextFloat();
    }

    private boolean isDoubleable(Block block) {
        Material blockType = block.getType();

        for(String string : prestigePerks.getConfig().getStringList("doubledrops.affected")) {
            Material mat = Material.matchMaterial(string);

            if(blockType == mat) {
                return true;
            }
        }
        return false;
    }

    private boolean isSmeltable(Material material) {
        switch (material) {
            case IRON_ORE:
            case GOLD_ORE:
            case STONE:
            case COBBLESTONE:
                return true;

        }
        return false;
    }

    private boolean nullCheck(Block block) {
        return block != null || !block.getType().name().contains("AIR");
    }
}
