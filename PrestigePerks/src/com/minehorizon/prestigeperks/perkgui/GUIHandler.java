package com.minehorizon.prestigeperks.perkgui;

import com.minehorizon.prestigeperks.PrestigePerks;
import com.minehorizon.prestigeperks.util.handler.Perks;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

public class GUIHandler {
    private final PrestigePerks prestigePerks;
    private Inventory inventory;
    private Player player;
    public GUIHandler(PrestigePerks prestigePerks, Player player) {
        this.prestigePerks = prestigePerks;
        this.player = player;

        inventory = Bukkit.createInventory(null,prestigePerks.getConfig().getInt("gui.size"), "Prestige Perks");
        populateInventory();
        fill();
    }

    private void populateInventory() {
        for(Perks perks : Perks.values()) {
            setupPerk(perks);
        }
        fill();
    }

    private void fill() {
        for(int i = 0; i < inventory.getSize(); i++) {
            if(inventory.getItem(i) == null || (inventory.getItem(i).getType() == Material.AIR)) {
                inventory.setItem(i,createPane());
            }
        }
    }

    private void setupPerk(Perks perks) {
        if(!player.hasPermission(perks.getPermission())) {
            inventory.setItem(getSlot(perks.getConfigIdentifier()), createItem(perks.getConfigIdentifier()));
        } else {
            inventory.setItem(getSlot(perks.getConfigIdentifier()), setBlocked(perks.getConfigIdentifier()));
        }
    }

    private int getSlot(String string) {
        return prestigePerks.getConfig().getInt("gui.items." + string + ".slot");
    }

    public void openInv() {
        player.openInventory(inventory);
    }

    public void purchasePerk(Perks perk) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set " + perk.getPermission());

        player.closeInventory();

        new BukkitRunnable() {
            @Override
            public void run() {
                GUIHandler guiHandler = new GUIHandler(prestigePerks,player);
                guiHandler.openInv();
            }
        }.runTaskLater(prestigePerks,1L);

        player.sendMessage(prestigePerks.getUtility().color(prestigePerks.getConfig().getString("messages.successfulPurchase").replace("{perk}",
                prestigePerks.getConfig().getString("gui.items." + perk.getConfigIdentifier() + ".blockedName"))));

        if(perk == Perks.NOFOODLOSS) {
            if(player.getFoodLevel() < 20) {
                player.setFoodLevel(20);
            }
        }

        if(perk.hasGiveableEffect()) {
            if(player.hasPotionEffect(perk.getPotionEffectType())) {
                player.removePotionEffect(perk.getPotionEffectType());
            }
            player.addPotionEffect(new PotionEffect(perk.getPotionEffectType(), Integer.MAX_VALUE, perk.getPower()));
        }
    }

    public void refundPerk(int slot) {
        for(Perks perks : Perks.values()) {
            if(player.hasPermission(perks.getPermission())) {
                int toCheck = prestigePerks.getConfig().getInt("gui.items." + perks.getConfigIdentifier() + ".slot");

                if(slot == toCheck) {

                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission unset " + perks.getPermission());

                    player.sendMessage(prestigePerks.getUtility().color(prestigePerks.getConfig().getString("messages.perkRefunded").replace("{perk}",
                            prestigePerks.getConfig().getString("gui.items." + perks.getConfigIdentifier() + ".blockedName"))));

                    if(!prestigePerks.getTokenHandler().isFull(player)) {
                        prestigePerks.getTokenHandler().giveToken(player);
                    }

                    if(perks.hasGiveableEffect()) {
                        if (player.hasPotionEffect(perks.getPotionEffectType())) {
                            player.removePotionEffect(perks.getPotionEffectType());
                        }
                    }

                    if(perks == Perks.AUTOPICKUP) {
                        if(prestigePerks.getToggledAutoPickup().contains(player.getUniqueId())) {
                            player.sendMessage(prestigePerks.getUtility().color(prestigePerks.getConfig().getString("messages.autoPickupDisabled")));
                            prestigePerks.getToggledAutoPickup().remove(player.getUniqueId());
                        }
                    } else if(perks == Perks.AUTOSMELT) {
                        if(prestigePerks.getToggledAutoSmelt().contains(player.getUniqueId())) {
                            player.sendMessage(prestigePerks.getUtility().color(prestigePerks.getConfig().getString("messages.autosmeltDisabled")));
                            prestigePerks.getToggledAutoSmelt().remove(player.getUniqueId());
                        }
                    }

                    player.closeInventory();
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            GUIHandler guiHandler = new GUIHandler(prestigePerks,player);
                            guiHandler.openInv();
                        }
                    }.runTaskLater(prestigePerks,1L);
                }
            }
        }
    }

    private ItemStack setBlocked(String string) {
        ItemStack itemStack = new ItemStack(Material.valueOf(prestigePerks.getConfig().getString("gui.items.blocked.material")));
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(prestigePerks.getUtility().color(prestigePerks.getConfig().getString("gui.items.blocked.name").replace("{perk}",
                prestigePerks.getConfig().getString("gui.items." + string + ".blockedName"))));

        itemMeta.setLore(prestigePerks.getUtility().color(prestigePerks.getConfig().getStringList("gui.items.blocked.lore")));
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(itemMeta);

        itemStack.addUnsafeEnchantment(Enchantment.DIG_SPEED, 1);

        return itemStack;
    }

    private ItemStack createPane() {
        ItemStack itemStack = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        itemStack.setDurability((short) prestigePerks.getConfig().getInt("gui.panecolor"));
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("");
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    private ItemStack createItem(String string) {
        ItemStack itemStack = new ItemStack(Material.valueOf(prestigePerks.getConfig().getString("gui.items." + string + ".material")));
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(prestigePerks.getUtility().color(prestigePerks.getConfig().getString("gui.items." + string + ".name")));
        itemMeta.setLore(prestigePerks.getUtility().color(prestigePerks.getConfig().getStringList("gui.items." + string + ".lore")));

        itemStack.setItemMeta(itemMeta);
        itemStack.setAmount(prestigePerks.getConfig().getInt("gui.items." + string + ".amount"));

        return itemStack;
    }




}
