package com.minehorizon.prestigeperks.listener.perks.potion.cl;

import com.SirBlobman.combatlogx.event.PlayerUntagEvent;
import com.minehorizon.prestigeperks.PrestigePerks;
import com.minehorizon.prestigeperks.util.handler.Perks;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;

public class CLUntag implements Listener {
    private final PrestigePerks prestigePerks;
    public CLUntag(PrestigePerks prestigePerks) {
        this.prestigePerks = prestigePerks;
    }

    @EventHandler
    public void onUntag(PlayerUntagEvent event) {
        for(Perks perks : Perks.values()) {
            if(event.getPlayer().hasPermission(perks.getPermission()) &&
            perks.hasGiveableEffect()) {
                if (event.getPlayer().hasPotionEffect(perks.getPotionEffectType()))
                    event.getPlayer().removePotionEffect(perks.getPotionEffectType());
                event.getPlayer().addPotionEffect(new PotionEffect(perks.getPotionEffectType(), Integer.MAX_VALUE, perks.getPower()));
            }
        }
    }
}
