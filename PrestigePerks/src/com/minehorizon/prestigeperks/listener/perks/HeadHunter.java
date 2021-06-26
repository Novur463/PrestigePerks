package com.minehorizon.prestigeperks.listener.perks;

import com.minehorizon.prestigeperks.PrestigePerks;
import com.minehorizon.prestigeperks.util.handler.Perks;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.concurrent.ThreadLocalRandom;

public class HeadHunter implements Listener {
    private PrestigePerks prestigePerks;
    public HeadHunter(PrestigePerks prestigePerks) {
        this.prestigePerks = prestigePerks;
    }

    @EventHandler
    public void onKill(PlayerDeathEvent event) {
        Player player = event.getEntity();

        if(player.getKiller() == null) return;

        if(player.getKiller().hasPermission(Perks.HEADHUNTER.getPermission())) {
            float random = ThreadLocalRandom.current().nextFloat();
            if (random < Float.parseFloat(prestigePerks.getConfig().getString("chances.headhunter"))) {
                player.getWorld().dropItemNaturally(player.getLocation(), createSkull(player));
            }
        }
    }

    private ItemStack createSkull(Player killed) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta skullMeta = (SkullMeta) head.getItemMeta();
        skullMeta.setOwner(killed.getName());
        head.setItemMeta(skullMeta);
        return head;
    }




}
