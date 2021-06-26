package com.minehorizon.prestigeperks.util.handler;

import com.minehorizon.prestigeperks.PrestigePerks;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class TokenHandler {
    private final PrestigePerks prestigePerks;

    public TokenHandler(PrestigePerks prestigePerks) {
        this.prestigePerks = prestigePerks;
    }

    public ItemStack createToken(Player player) {
        ItemStack itemStack = new ItemStack(Material.valueOf(prestigePerks.getConfig().getString("token.material")));
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(prestigePerks.getUtility().color(prestigePerks.getConfig().getString("token.name")));
        List<String> lore = new ArrayList<>();
        for (String str : prestigePerks.getConfig().getStringList("token.lore")) {
            str = str.replace("{uuid}", player.getUniqueId().toString());
            lore.add(prestigePerks.getUtility().color(str));
        }
        itemMeta.setLore(lore);

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public void giveToken(Player player) {

        ItemStack itemStack = new ItemStack(createToken(player));

        if (player.getInventory().contains(itemStack)) {
            int slotLoc = player.getInventory().first(itemStack);
            int amount = player.getInventory().getItem(slotLoc).getAmount();

            player.getInventory().getItem(slotLoc).setAmount(amount + 1);
            return;
        }

        if (isFull(player)) {
            player.sendMessage(prestigePerks.getUtility().color(prestigePerks.getConfig().getString("messages.inventoryFull")));
            return;
        }

        player.getInventory().addItem(itemStack);
    }


    public boolean isFull(Player player) {
        return !player.getInventory().contains(createToken(player)) && player.getInventory().firstEmpty() == -1;
    }

    public boolean isToken(ItemStack itemStack, Player player) {
        ItemStack toCompare = prestigePerks.getItemUtil().createToken(player);
        return toCompare.isSimilar(itemStack);
    }


}
