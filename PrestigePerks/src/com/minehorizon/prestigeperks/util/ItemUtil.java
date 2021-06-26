package com.minehorizon.prestigeperks.util;

import com.minehorizon.prestigeperks.PrestigePerks;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemUtil {
    private final PrestigePerks prestigePerks;
    public ItemUtil(PrestigePerks prestigePerks) { this.prestigePerks = prestigePerks; }

    public ItemStack createPerkItem(String string) {
        ItemStack itemStack = new ItemStack(Material.valueOf(prestigePerks.getConfig().getString("gui.items." + string + ".material")));
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(prestigePerks.getUtility().color(prestigePerks.getConfig().getString("gui.items." + string + ".name")));
        itemMeta.setLore(prestigePerks.getUtility().color(prestigePerks.getConfig().getStringList("gui.items." + string + ".lore")));

        itemStack.setItemMeta(itemMeta);
        itemStack.setAmount(prestigePerks.getConfig().getInt("gui.items." + string + ".amount"));

        return itemStack;
    }

    public ItemStack createBlocked(String string) {
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

    public ItemStack createToken(Player player) {
        ItemStack itemStack = new ItemStack(Material.valueOf(prestigePerks.getConfig().getString("token.material")));
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(prestigePerks.getUtility().color(prestigePerks.getConfig().getString("token.name")));
        List<String> lore = new ArrayList<>();
        for(String str : prestigePerks.getConfig().getStringList("token.lore")) {
            str = str.replace("{uuid}", player.getUniqueId().toString());
            lore.add(prestigePerks.getUtility().color(str));
        }
        itemMeta.setLore(lore);

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

}
