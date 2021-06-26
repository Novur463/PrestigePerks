package com.minehorizon.prestigeperks.listener;

import com.minehorizon.prestigeperks.PrestigePerks;
import com.minehorizon.prestigeperks.perkgui.GUIHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ClickToken implements Listener {
    private final PrestigePerks prestigePerks;

    public ClickToken(PrestigePerks prestigePerks) {
        this.prestigePerks = prestigePerks;
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || (event.getAction() == Action.RIGHT_CLICK_AIR)) {

            ItemStack itemStack = event.getPlayer().getInventory().getItemInMainHand();


            if (itemStack.hasItemMeta() && (itemStack.getItemMeta().hasDisplayName() && (itemStack.getItemMeta().hasLore()))) {
                if (prestigePerks.getTokenHandler().isToken(itemStack, event.getPlayer())) {
                    if (canRedeem(itemStack, event.getPlayer())) {
                        GUIHandler guiHandler = new GUIHandler(prestigePerks, event.getPlayer());
                        guiHandler.openInv();
                    } else {
                        event.getPlayer().sendMessage(prestigePerks.getUtility().color(prestigePerks.getConfig().getString("messages.tokenUUIDLocked")));
                    }
                }
            }
        }
    }

    private boolean canRedeem(ItemStack itemStack, Player player) {
        for (String string : itemStack.getItemMeta().getLore()) {
            if (string.contains(player.getUniqueId().toString())) {
                return true;
            }
        }
        return false;
    }
}
