package com.minehorizon.prestigeperks.perkgui;

import com.minehorizon.prestigeperks.PrestigePerks;
import com.minehorizon.prestigeperks.util.handler.Perks;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GUIInteract implements Listener {
    private final PrestigePerks prestigePerks;

    public GUIInteract(PrestigePerks prestigePerks) {
        this.prestigePerks = prestigePerks;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!event.isCancelled()) {
            if (event.getWhoClicked() instanceof Player) {
                Player player = (Player) event.getWhoClicked();

                if (event.getView().getTitle().equalsIgnoreCase("Prestige Perks")) {

                    if (event.getCurrentItem() == null || (event.getCurrentItem().getType() == Material.AIR)) return;

                    if (event.getCurrentItem().getType().name().contains("STAINED_GLASS_PANE")) {
                        event.setCancelled(true);
                    }

                    GUIHandler guiHandler = new GUIHandler(prestigePerks, player);

                    if (event.isLeftClick()) {
                            if (isRegisteredPerk(event.getCurrentItem())) {
                                for (Perks perks : Perks.values()) {
                                    ItemStack toCompare = prestigePerks.getRegisteredPerkMats().get(perks.getConfigIdentifier());

                                    if (event.getCurrentItem().isSimilar(toCompare)) {

                                        if (!hasBought(perks, player)) {

                                            if (!isToken(player.getInventory().getItemInMainHand(), player)) {
                                                event.setCancelled(true);
                                                player.sendMessage(prestigePerks.getUtility().color(prestigePerks.getConfig().getString("messages.itemNotToken")));
                                                return;
                                            }

                                            if (player.getInventory().getItemInMainHand().getAmount() > 1) {
                                                player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
                                            } else {
                                                player.getInventory().getItemInMainHand().setAmount(0);
                                            }
                                            guiHandler.purchasePerk(perks);
                                            event.setCancelled(true);
                                        } else {
                                            player.sendMessage(prestigePerks.getUtility().color(prestigePerks.getConfig().getString("messages.perkAlreadyBought")));
                                            event.setCancelled(true);
                                        }
                                    }
                                }
                            } else if (event.getCurrentItem().getType().name().contains("BARRIER") && (event.getCurrentItem().hasItemMeta() && (event.getCurrentItem().getItemMeta().hasDisplayName() &&
                                    event.getCurrentItem().getItemMeta().hasLore()))) {

                                player.sendMessage(prestigePerks.getUtility().color(prestigePerks.getConfig().getString("messages.perkAlreadyBought")));
                                event.setCancelled(true);
                            }
                        } else if(event.isRightClick()) {
                            if(player.isOp()) {
                                player.sendMessage(prestigePerks.getUtility().color(prestigePerks.getConfig().getString("messages.cannotRefundOP")));
                                event.setCancelled(true);
                            } else {
                                int slot = event.getSlot();
                                event.setCancelled(true);
                                guiHandler.refundPerk(slot);
                            }
                    }

                }
            }
        }
    }

    private boolean isToken(ItemStack itemStack, Player player) {
        ItemStack token = prestigePerks.getTokenHandler().createToken(player);
        return itemStack.isSimilar(token);
    }

    private boolean hasBought(Perks perks, Player player) {
        return player.hasPermission(perks.getPermission());
    }

    private boolean isRegisteredPerk(ItemStack itemStack) {
        return prestigePerks.getRegisteredPerkMats().containsValue(itemStack);
    }
}
